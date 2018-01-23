package com.demo.dao;

import com.demo.com.demo.dto.SearchQueryDTO;
import com.demo.model.Product;
import com.demo.model.ProductDTO;
import com.demo.utils.MyResultExtractor;
import com.google.common.collect.Lists;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.queryparser.xml.FilterBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.search.MatchQuery;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.DefaultResultMapper;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import static org.elasticsearch.index.query.QueryBuilders.*;
import static org.elasticsearch.search.aggregations.AggregationBuilders.filter;
import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductRepositoryImpl implements ProductRepositoryCustom {
    @Autowired
    ElasticsearchOperations elasticsearchTemplate;
    @Override
    public ProductDTO getAllMatchedProducts(String content) {

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(multiMatchQuery(content,"category","subCategory","name"))
                .withSearchType(SearchType.DEFAULT)
                .withIndices("products").withTypes("products")
                .addAggregation(terms("color").field("color"))
                .addAggregation(terms("brand").field("brand"))
                .addAggregation(terms("size").field("size"))
                 .build();

        SearchQuery searchQuery1 = new NativeSearchQueryBuilder()
                .withQuery(multiMatchQuery(content,"category","subCategory","name","size"))
                .withSearchType(SearchType.DEFAULT)
                .withIndices("products").withTypes("products")
                .build();
        List<Product> products = elasticsearchTemplate
                .queryForList(searchQuery1, Product.class);
         ProductDTO result =null;
        // when
       // return elasticsearchTemplate.query(searchQuery,new Re);
         result = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<ProductDTO>() {
            @Override
            public ProductDTO extract(SearchResponse response) {
                ProductDTO productDTO = new ProductDTO();
                Map<String,List<String>> attributes = new HashMap<String,List<String>>();
                List<String> attributeValuues  = new ArrayList<String>();
               org.elasticsearch.search.aggregations.bucket.terms.Terms termsColours  = response.getAggregations().get("color");
                org.elasticsearch.search.aggregations.bucket.terms.Terms termsBrands  = response.getAggregations().get("brand");
                org.elasticsearch.search.aggregations.bucket.terms.Terms termsSize  = response.getAggregations().get("size");

                setIntoMap(attributes, termsColours, "color");
                setIntoMap(attributes, termsBrands, "brand");
                setIntoMap(attributes, termsSize, "size");

                List<Product> alProduct =  new DefaultResultMapper().
                          mapResults(response, Product.class, new PageRequest( 0, 15)).getContent();
                productDTO.setAttributes(attributes);
                productDTO.setResult(alProduct);
                return productDTO;
            }
        });

        return result;
    }

    @Override
    public ProductDTO getAllProductsByCriteria(SearchQueryDTO searchQueryDTO) {
        boolean isAddedAggreagtions =  false;
        NativeSearchQueryBuilder nativeQuery = new NativeSearchQueryBuilder();
        QueryBuilder multiMatchQueryContent = null;
        QueryBuilder matchQueryBrand = null;
        QueryBuilder matchQueryColor = null;
        QueryBuilder matchQuerySize = null;
        List<FilterBuilder> filters = Lists.newArrayList();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
                if(null!=searchQueryDTO.getContent()){
            multiMatchQueryContent = matchQuery("category",searchQueryDTO.getContent());
                    boolQueryBuilder.must(multiMatchQueryContent);
          }
        if(!StringUtils.isEmpty(searchQueryDTO.getBrand())){
           boolQueryBuilder.must(matchQuery("brand",searchQueryDTO.getBrand()));
           }
        if(!StringUtils.isEmpty(searchQueryDTO.getSize())) {
             boolQueryBuilder.must(matchQuery( "size",searchQueryDTO.getSize()));
           }
        if (!StringUtils.isEmpty(searchQueryDTO.getColor())) {
            boolQueryBuilder.must(matchQuery( "color",searchQueryDTO.getColor()));
        }
        nativeQuery.withQuery(boolQueryBuilder);
        nativeQuery.withSearchType(SearchType.DEFAULT).withIndices("products").withTypes("products");
            if (StringUtils.isEmpty(searchQueryDTO.getColor()))
            {
                isAddedAggreagtions =true;
                nativeQuery.addAggregation(terms("color").field("color"));
            }
                if (StringUtils.isEmpty(searchQueryDTO.getBrand()))
            {
                isAddedAggreagtions =true;
                nativeQuery.addAggregation(terms("brand").field("brand"));
            }
            if (StringUtils.isEmpty(searchQueryDTO.getSize()))
            {
                isAddedAggreagtions =true;
                nativeQuery.addAggregation(terms("size").field("size"));

            }
           SearchQuery searchQuery =  nativeQuery.build();
        MyResultExtractor resultsExtractor = new MyResultExtractor();
        resultsExtractor.setAddedAggreagtions(isAddedAggreagtions);
        ProductDTO result = elasticsearchTemplate.query(searchQuery, resultsExtractor);

            /*ProductDTO result = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<ProductDTO>() {
            @Override
            public ProductDTO extract(SearchResponse response) {
                ProductDTO productDTO = new ProductDTO();
                Map<String,List<String>> attributes = new HashMap<String,List<String>>();
                List<String> attributeValuues  = new ArrayList<String>();
                //if(isAddedAggreagtions)
                {
                    org.elasticsearch.search.aggregations.bucket.terms.Terms termsColours = response.getAggregations().get("color");
                    org.elasticsearch.search.aggregations.bucket.terms.Terms termsBrands = response.getAggregations().get("brand");
                    org.elasticsearch.search.aggregations.bucket.terms.Terms termsSize = response.getAggregations().get("size");

                    setIntoMap(attributes, termsColours, "color");
                    setIntoMap(attributes, termsBrands, "brand");
                    setIntoMap(attributes, termsSize, "size");
                }
                List<Product> alProduct =  new DefaultResultMapper().
                        mapResults(response, Product.class, new PageRequest( 0, 15)).getContent();
                productDTO.setAttributes(attributes);
                productDTO.setResult(alProduct);
                return productDTO;
            }
        });*/

        return result;


    }
            private void setIntoMap(Map<String,List<String>> attributesMap , org.elasticsearch.search.aggregations.bucket.terms.Terms terms,String attributeName) {
        List<String> attributeValueList = new ArrayList<String>();
        if(null!=terms && null!=terms.getBuckets() && terms.getBuckets().size()>1) {
            for (org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket bucket : terms.getBuckets()) {
                attributeValueList.add((String) bucket.getKey());
            }
            attributesMap.put(attributeName,attributeValueList);

        }
        }
}
