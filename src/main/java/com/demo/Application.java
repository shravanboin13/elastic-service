package com.demo;

import com.demo.model.Product;
import com.demo.service.ProductService;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import java.util.Map;

@SpringBootApplication
public class Application implements CommandLineRunner{
    @Autowired
    private ElasticsearchOperations es;

    @Autowired
    private ProductService productService;

    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        printElasticSearchInfo();

       /* productService.save(new Product("1001", "Elasticsearch Basics", "Rambabu Posa", "23-FEB-2017"));
        productService.save(new Product("1002", "Apache Lucene Basics", "Rambabu Posa", "13-MAR-2017"));
        productService.save(new Product("1003", "Apache Solr Basics", "Rambabu Posa", "21-MAR-2017"));
*/
        //fuzzey search


    }

    //useful for debug, print elastic search details
    private void printElasticSearchInfo() {

        System.out.println("--ElasticSearch--");
        Client client = es.getClient();
        Map<String, String> asMap = client.settings().getAsMap();

        asMap.forEach((k, v) -> {
            System.out.println(k + " = " + v);
        });
        System.out.println("--ElasticSearch--");
    }

}
