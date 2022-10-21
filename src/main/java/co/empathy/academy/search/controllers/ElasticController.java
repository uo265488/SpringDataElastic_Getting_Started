package co.empathy.academy.search.controllers;

import co.empathy.academy.search.services.ElasticService;
import org.elasticsearch.client.RestHighLevelClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.lang.reflect.Array;

@RestController
public class ElasticController {
    @Autowired
    private ElasticService elasticService;

    @GetMapping("/greet/{name}")
    public String greet(@PathVariable String name) {
        return "Hello " + name;
    }

    @GetMapping("/search")
    public String search(@RequestParam String query) {

        String version = elasticService.getElasticEndpointReport("/");

        JSONObject json = new JSONObject(version);

        JSONObject theVersion = (JSONObject)json.get("version");

        return "{\"query\": " + query + ", \"clusterName\" : " + theVersion.get("number") + "}";
    }


}
