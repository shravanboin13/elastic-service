package com.demo.utils;

import com.demo.model.Product;
import com.demo.model.ProductDTO;
import org.elasticsearch.action.search.SearchResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.DefaultResultMapper;
import org.springframework.data.elasticsearch.core.ResultsExtractor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyResultExtractor implements ResultsExtractor<ProductDTO> {
    public boolean isAddedAggreagtions() {
        return isAddedAggreagtions;
    }

    public void setAddedAggreagtions(boolean addedAggreagtions) {
        isAddedAggreagtions = addedAggreagtions;
    }

    boolean isAddedAggreagtions;

    @Override
    public ProductDTO extract(SearchResponse response) {
        ProductDTO productDTO = new ProductDTO();
        Map<String, List<String>> attributes = new HashMap<String, List<String>>();
        List<String> attributeValuues = new ArrayList<String>();
        if (isAddedAggreagtions) {
            org.elasticsearch.search.aggregations.bucket.terms.Terms termsColours = response.getAggregations().get("color");
            org.elasticsearch.search.aggregations.bucket.terms.Terms termsBrands = response.getAggregations().get("brand");
            org.elasticsearch.search.aggregations.bucket.terms.Terms termsSize = response.getAggregations().get("size");

            setIntoMap(attributes, termsColours, "color");
            setIntoMap(attributes, termsBrands, "brand");
            setIntoMap(attributes, termsSize, "size");
        }
        List<Product> alProduct = new DefaultResultMapper().
                mapResults(response, Product.class, new PageRequest(0, 15)).getContent();
        productDTO.setAttributes(attributes);
        productDTO.setResult(alProduct);
        return productDTO;
    }

    private void setIntoMap(Map<String, List<String>> attributesMap, org.elasticsearch.search.aggregations.bucket.terms.Terms terms, String attributeName) {
        List<String> attributeValueList = new ArrayList<String>();
        if (null != terms && null != terms.getBuckets() && terms.getBuckets().size() > 0) {
            for (org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket bucket : terms.getBuckets()) {
                attributeValueList.add((String) bucket.getKey());
            }
            attributesMap.put(attributeName, attributeValueList);

        }
    }
}

