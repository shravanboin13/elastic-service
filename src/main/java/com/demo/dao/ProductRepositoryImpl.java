package com.demo.dao;

import com.demo.com.demo.dto.SearchQueryDTO;
import com.demo.constants.AttributeMaps;
import com.demo.model.Product;
import com.demo.model.ProductDTO;
import com.demo.utils.MyResultExtractor;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
//import org.elasticsearch.search.aggregations.bucket.filters.FiltersAggregator.KeyedFilter;
import org.elasticsearch.search.aggregations.bucket.nested.Nested;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.DefaultResultMapper;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import static org.elasticsearch.index.query.QueryBuilders.*;
import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;


import java.util.*;

public class ProductRepositoryImpl implements ProductRepositoryCustom {
    private static final String ATTRIBUTES = "attributes";
    @Autowired
    ElasticsearchOperations elasticsearchTemplate;

    @Override
    public ProductDTO getAllProductsByCriteria(Map searchQueryDTO) {
        boolean isAddedAggreagtions =  false;
        AttributeMaps.intializeMaps();
        NativeSearchQueryBuilder nativeQuery = new NativeSearchQueryBuilder();
        QueryBuilder multiMatchQueryContent = null;
        BoolQueryBuilder boolQueryBuilder = boolQuery();
       createQueries(searchQueryDTO,boolQueryBuilder);
        nativeQuery.withQuery(boolQueryBuilder);
        nativeQuery.withSearchType(SearchType.DEFAULT).withIndices("products").withTypes("products");
        isAddedAggreagtions =  createAggregations(nativeQuery,searchQueryDTO);
         /*   if (StringUtils.isEmpty(searchQueryDTO.getColor())){
                isAddedAggreagtions =true;
                nativeQuery.addAggregation(AggregationBuilders.nested("Color").path("attributes").
                        subAggregation(terms("Color").field("attributes.Color")));
            }
                if (StringUtils.isEmpty(searchQueryDTO.getBrand())){
                isAddedAggreagtions =true;
                nativeQuery.addAggregation(AggregationBuilders.nested("Brand").path("attributes").subAggregation(terms("Brand").field("attributes.Brand")));
            }
            if (StringUtils.isEmpty(searchQueryDTO.getSize()))
            {
                isAddedAggreagtions =true;
                nativeQuery.addAggregation(AggregationBuilders.nested("Size").path("attributes").subAggregation(terms("Size").field("attributes.Size")));
            }*/
        SearchQuery searchQuery =  nativeQuery.build();
        MyResultExtractor resultsExtractor = new MyResultExtractor();
        resultsExtractor.setAddedAggreagtions(isAddedAggreagtions);
        elasticsearchTemplate.putMapping(Product.class);
         ProductDTO result = elasticsearchTemplate.query(searchQuery, resultsExtractor);
        return result;
    }

    @Override
    public ProductDTO getAllProductsByCriteria(SearchQueryDTO searchQueryDTO) {
        boolean isAddedAggreagtions =  false;
        AttributeMaps.intializeMaps();
        NativeSearchQueryBuilder nativeQuery = new NativeSearchQueryBuilder();
        QueryBuilder multiMatchQueryContent = null;
        BoolQueryBuilder boolQueryBuilder = boolQuery();
        createQueries(searchQueryDTO,boolQueryBuilder);
                 nativeQuery.withQuery(boolQueryBuilder);
        nativeQuery.withSearchType(SearchType.DEFAULT).withIndices("products").withTypes("products");
        isAddedAggreagtions =  createAggregations(nativeQuery,searchQueryDTO);
                 SearchQuery searchQuery =  nativeQuery.build();
        MyResultExtractor resultsExtractor = new MyResultExtractor();
        resultsExtractor.setAddedAggreagtions(isAddedAggreagtions);
        elasticsearchTemplate.putMapping(Product.class);
        ProductDTO result = elasticsearchTemplate.query(searchQuery, resultsExtractor);
        return result;
    }

    @Override
    public Product saveProduct(Product product) {
        elasticsearchTemplate.createIndex(Product.class);
        elasticsearchTemplate.putMapping(Product.class);
        IndexQuery indexQuery = new IndexQueryBuilder()
                .withId(product.getId())
                .withIndexName("products").withObject(product)
                .withType("products").build();;
        elasticsearchTemplate.index(indexQuery);
        return product;
    }

    @Override
    public List<String> getAllProductTypes() {
        NativeSearchQueryBuilder nativeQuery = new NativeSearchQueryBuilder();
        nativeQuery.addAggregation(AggregationBuilders.terms("type").field("type"));
        SearchQuery searchQuery =  nativeQuery.build();
        List<String> productResult = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<List<String>>() {

                    @Override
                    public List<String> extract(SearchResponse response) {
                        List<String> productTypes= new ArrayList<String>();
                        Map<String,Aggregation> oMap =response.getAggregations().getAsMap();
                        Set<String> entries = oMap.keySet();
                        for(String key:entries){
                            Aggregation agg  = (Aggregation) oMap.get(key);
                            final Terms terms = (Terms) agg;
                            if (null != terms && null != terms.getBuckets() && terms.getBuckets().size() > 0) {
                                for (org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket bucket : terms.getBuckets()) {
                                    if(null!=bucket.getKey()&& bucket.getDocCount()>0)
                                        productTypes.add((String)bucket.getKey());
                                }

                            }

                            //
                        }

                        return productTypes;
                    }
                });
       return productResult;
    }

    private boolean createAggregations(NativeSearchQueryBuilder nativeQuery, Map searchQueryDTO) {
        //Set<String> keys = searchQueryDTO.keySet();
        boolean isAddedAggragations = false;
       Map<String,Integer> attribiteesMap = getAttributesMap((String)searchQueryDTO.get("category"));
        Set<String> keys = attribiteesMap.keySet();

        //below code is useful for converational ui
       /* Map<Integer,String> orderMap = getOrderMap((String)searchQueryDTO.get("category"));

        int highestOrder = getHighestOrder(searchQueryDTO,attribiteesMap);
        String attributeToAggregate = orderMap.get(highestOrder+1);
        if(null!=attributeToAggregate) {
            nativeQuery.addAggregation(AggregationBuilders.nested(attributeToAggregate).path(ATTRIBUTES).
                    subAggregation(terms(attributeToAggregate).field(ATTRIBUTES + "." + attributeToAggregate)));
            isAddedAggragations = true;
        }
*/
        for(String key:keys) {
            if (!"Category".equalsIgnoreCase(key)) {

                nativeQuery.addAggregation(AggregationBuilders.nested(key).path(ATTRIBUTES).
                        subAggregation(terms(key).field(ATTRIBUTES + "." + key)));
                isAddedAggragations = true;
            }
        }
        return isAddedAggragations;

    }
    private boolean createAggregations(NativeSearchQueryBuilder nativeQuery, SearchQueryDTO searchQueryDTO) {
        //Set<String> keys = searchQueryDTO.keySet();
        boolean isAddedAggragations = false;
        Map<String,Integer> attribiteesMap = getAttributesMap((String)searchQueryDTO.getProductType());
        Set<String> keys = attribiteesMap.keySet();

        //below code is useful for converational ui
       /* Map<Integer,String> orderMap = getOrderMap((String)searchQueryDTO.get("category"));

        int highestOrder = getHighestOrder(searchQueryDTO,attribiteesMap);
        String attributeToAggregate = orderMap.get(highestOrder+1);
        if(null!=attributeToAggregate) {
            nativeQuery.addAggregation(AggregationBuilders.nested(attributeToAggregate).path(ATTRIBUTES).
                    subAggregation(terms(attributeToAggregate).field(ATTRIBUTES + "." + attributeToAggregate)));
            isAddedAggragations = true;
        }
*/
        for(String key:keys) {
            if (!"Category".equalsIgnoreCase(key)) {

                nativeQuery.addAggregation(AggregationBuilders.nested(key).path(ATTRIBUTES).
                        subAggregation(terms(key).field(ATTRIBUTES + "." + key)));
                isAddedAggragations = true;
            }
        }
        return isAddedAggragations;

    }

    private Map<Integer,String> getOrderMap(String category) {
        if("Furniture".equalsIgnoreCase(category)){
            return AttributeMaps.furnitureOrderMap;
        }else if("Clothing".equalsIgnoreCase(category)){
            return AttributeMaps.clothingOrderMap;
        }else if("Wearable".equalsIgnoreCase(category)){
            return AttributeMaps.wearableOrderMap;
        }
        return null;
    }

    private int getHighestOrder(Map searchQuery, Map<String, Integer> map) {
        int max = 0;
        if(null!=searchQuery && null!=map){
            Set<String> keys = searchQuery.keySet();
            for (String key : keys) {
                if (null != map.get(key)) {
                    if (max < map.get(key)) {
                        max = map.get(key);
                    }
                }
            }
        }
        return max;

    }

    private Map<String,Integer> getAttributesMap(String category) {
        if("Furniture".equalsIgnoreCase(category)){
            return AttributeMaps.furnitureAttributeMap;
        }else if("Clothing".equalsIgnoreCase(category)){
            return AttributeMaps.clothingAttributeMap;
        }else if("Wearable".equalsIgnoreCase(category)){
            return AttributeMaps.wearableAttributeMap;
        }if("Tv's".equalsIgnoreCase(category)){
            return AttributeMaps.tvAttributeMap;
        }else if("TV Mounts".equalsIgnoreCase(category)){
            return AttributeMaps.tvMountAttributeMap;
        }else if("Blu-Ray & DVD Players".equalsIgnoreCase(category)){
            return AttributeMaps.bluRayDVDPlayers;
        }if("Media Streaming".equalsIgnoreCase(category)){
            return AttributeMaps.mediaStreaminAttributeMap;
        }else if("Movies & TV Shows".equalsIgnoreCase(category)){
            return AttributeMaps.movieTvShowsAttributeMap;
        }else if("Laptops".equalsIgnoreCase(category)){
            return AttributeMaps.laptopsAttributeMap;
        }if("Software".equalsIgnoreCase(category)){
            return AttributeMaps.softwaresAttributeMap;
        }else if("Printing and Scanning".equalsIgnoreCase(category)){
            return AttributeMaps.printersAttributeMap;
        }else if("Cameras".equalsIgnoreCase(category)){
            return AttributeMaps.camerasAttributeMap;
        }if("Refrigerator".equalsIgnoreCase(category)){
            return AttributeMaps.refrigeratorsAttributeMap;
        }else if("Fans".equalsIgnoreCase(category)){
            return AttributeMaps.fansAttributeMap;
        }
        return null;
    }

    private void createQueries(Map<String,String> searchQueryMap, BoolQueryBuilder boolQueryBuilder) {
        Set<String> keys = searchQueryMap.keySet();
        for(String key:keys){
            if("category".equalsIgnoreCase(key)){
            boolQueryBuilder.must(matchQuery("type",searchQueryMap.get(key)));
            }else{
                //nestedQuery("attributes",matchQuery("attributes.Brand",searchQueryDTO.getBrand()));
                //
                boolQueryBuilder.must(nestedQuery(ATTRIBUTES,matchQuery(ATTRIBUTES+"."+key,searchQueryMap.get(key))));

            }
        }

    }
    private void createQueries(SearchQueryDTO searchQueryDTO, BoolQueryBuilder boolQueryBuilder) {
        String productType = searchQueryDTO.getProductType();
        boolQueryBuilder.must(matchQuery("type", productType));

        Set<String> keys = searchQueryDTO.getAttributes().keySet();
        for (String key : keys) {
            List<String> attributeValuesList = searchQueryDTO.getAttributes().get(key);
            BoolQueryBuilder valueVuilder = boolQuery();
            if (null != attributeValuesList && searchQueryDTO.getAttributes().size() > 0) {
                for (String value : attributeValuesList) {
                    valueVuilder.should(nestedQuery(ATTRIBUTES, matchQuery(ATTRIBUTES + "." + key, value)));
                }
                boolQueryBuilder.must(valueVuilder);
            }

        }
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
