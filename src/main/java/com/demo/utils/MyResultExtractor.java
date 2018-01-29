package com.demo.utils;

import com.demo.model.Product;
import com.demo.model.ProductDTO;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.bucket.nested.Nested;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.DefaultResultMapper;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext;

import java.util.*;

public class MyResultExtractor implements ResultsExtractor<ProductDTO> {
    public boolean isAddedAggreagtions() {
        return isAddedAggreagtions;
    }

    public void setAddedAggreagtions(boolean addedAggreagtions) {
        isAddedAggreagtions = addedAggreagtions;
    }

    boolean isAddedAggreagtions;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public ProductDTO extract(SearchResponse response) {
        ProductDTO productDTO = new ProductDTO();
        Map<String, Map<Object,Long>> attributes = new HashMap<String, Map<Object,Long>>();
        List<String> attributeValuues = new ArrayList<String>();
        if (isAddedAggreagtions && null!= response.getAggregations()) {
            Map<String,Aggregation> oMap =response.getAggregations().getAsMap();
           Set<String> entries = oMap.keySet();
           for(String key:entries){
               Nested agg  = (Nested) oMap.get(key);
               for(Aggregation aggregation:agg.getAggregations().asList()){
                   final Terms aggTerm = (Terms) aggregation;
                   setIntoMap(attributes,aggTerm,key);
               }
             //
           }
        }

        List<Product> alProduct = new DefaultResultMapper(new SimpleElasticsearchMappingContext()).
                mapResults(response, Product.class, new PageRequest(0, 15)).getContent();
        productDTO.setAttributes(attributes);
        productDTO.setProducts(alProduct);
        return productDTO;
    }

    private void setIntoMap(Map<String, Map<Object, Long>> attributesMap, Terms terms, String attributeName) {
        /*List<String> attributeValueList = new ArrayList<String>();
        if (null != terms && null != terms.getBuckets() && terms.getBuckets().size() > 0) {
            for (org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket bucket : terms.getBuckets()) {

                attributeValueList.add((String) bucket.getKey());
            }
            attributesMap.put(attributeName, attributeValueList);

        }*/
        Map<Object,Long> attributeValueList = new HashMap<Object, Long>();
        if (null != terms && null != terms.getBuckets() && terms.getBuckets().size() > 0) {
            for (org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket bucket : terms.getBuckets()) {
                if(null!=bucket.getKey()&& bucket.getDocCount()>0)
                attributeValueList.put( bucket.getKey(),bucket.getDocCount());
            }
            attributesMap.put(attributeName, attributeValueList);

        }
    }
}

