package com.demo.dao;

import com.demo.model.Product;
import com.demo.model.ProductDTO;
import org.apache.lucene.index.Terms;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.DefaultResultMapper;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import static org.elasticsearch.index.query.QueryBuilders.*;
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

    private void setIntoMap(Map<String,List<String>> attributesMap , org.elasticsearch.search.aggregations.bucket.terms.Terms terms,String attributeName) {
        List<String> attributeValueList = new ArrayList<String>();
        for(org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket bucket:terms.getBuckets()){
            attributeValueList.add((String)bucket.getKey());
        }
        attributesMap.put(attributeName,attributeValueList);
    }
}
