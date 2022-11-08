package co.empathy.academy.search.controllers;

import co.empathy.academy.search.services.ElasticService;
import org.elasticsearch.client.RestHighLevelClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ElasticController {
    @Autowired
    private ElasticService elasticService;

    @GetMapping("/greet/{name}")
    public String greet(@PathVariable String name) {
        return "Hello " + name;
    }

    @GetMapping("/search")
    public String search(@RequestParam String query) {

        String version = elasticService.performGETRequest("/");

        JSONObject json = new JSONObject(version);

        JSONObject theVersion = (JSONObject)json.get("version");

        return "{\"query\": " + query + ", \"clusterName\" : " + theVersion.get("number") + "}";
    }

    /**
     * Performs GET request to /_cat/indices and retrieves de info.
     * @return report about the indices in the cluster
     */
    @GetMapping("/index/info")
    public String getClusterInformation() {
        return "health status index       uuid   pri rep docs.count docs.deleted store.size pri.store.size\r\n"
               + elasticService.performGETRequest("/_cat/indices");
    }

    /**
     * Performs GET request for specific index info and retrieves de info.
     * @return report about the indices in the cluster
     */
    @GetMapping("/index/report/{indexName}")
    public String getIndexReport(@PathVariable String indexName) {
        return elasticService.performGETRequest("/" + indexName);
    }

    /**
     * Indexing a document in a specific index
     */
    @GetMapping("/index/{indexName}")
    public String indexAnyDocument(@PathVariable String indexName) {
        Map<String, String> document = new HashMap<>();
        document.put("randomId", UUID.randomUUID().toString());

        return elasticService.indexDocument(indexName, document);
    }

}
