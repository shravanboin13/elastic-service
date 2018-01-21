package com.demo.dao;

import com.demo.model.Product;
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

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;


import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

public class ProductRepositoryImpl implements ProductRepositoryCustom {
    @Autowired
    ElasticsearchOperations elasticsearchTemplate;
    @Override
    public List<Product> getAllMatchedProducts(String content) {

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
              //  .withFilter(termQuery("category",content))
                .withQuery(matchAllQuery())
                .withSearchType(SearchType.DEFAULT)
                .withIndices("products").withTypes("products")
                //.addAggregation(terms("name").field("name"))
               .addAggregation(AggregationBuilders.filter("test").filter(QueryBuilders.termQuery("category",content)))//.
                        //addAggregation(terms("name").field("name"))
                .build();
         List<Product> al =null;
        // when
       // return elasticsearchTemplate.query(searchQuery,new Re);
         al = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<List<Product>>() {
            @Override
            public List<Product> extract(SearchResponse response) {
                 return new DefaultResultMapper().mapResults(response, Product.class, new PageRequest( 0, 15)).getContent();
                 //return al;
                //return response.getAggregations();
            }
        });

return al;
    }
}
