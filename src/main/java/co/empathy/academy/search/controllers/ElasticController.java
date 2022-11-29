package co.empathy.academy.search.controllers;

import co.empathy.academy.search.services.ElasticService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ElasticController {
    @Autowired
    private ElasticService elasticService;

    @Operation(summary = "Greetings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success. "),
    })
    @Parameters(value = {
            @Parameter(name = "name", required = true, description = "Name of the user. "),
    })
    @GetMapping("/greet/{name}")
    public String greet(@PathVariable String name) {
        return "Hello " + name;
    }

    @Operation(summary = "Range query by score")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success. "),
            @ApiResponse(responseCode = "400", description = "Bad request. ")
    })
    @Parameters(value = {
            @Parameter(name = "query", required = true, description = "Primitive query. "),
    })
    @GetMapping("/search")
    public String search(@RequestParam String query) {

        String version = elasticService.performGETRequest("/");

        JSONObject json = new JSONObject(version);

        JSONObject theVersion = (JSONObject)json.get("version");

        return "{\"query\": " + query + ", \"clusterName\" : " + theVersion.get("number") + "}";
    }

    @Operation(summary = "Index request to get cluester info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success. ")
    })
    @GetMapping("/index/info")
    public String getClusterInformation() {
        return "health status index       uuid   pri rep docs.count docs.deleted store.size pri.store.size\r\n"
               + elasticService.performGETRequest("/_cat/indices");
    }

    @Operation(summary = "Performs GET request for specific index info and retrieves de info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Report with indices. "),
    })
    @Parameters(value = {
            @Parameter(name = "indexName", required = true, description = "Index name. ")
    })
    @GetMapping("/index/report/{indexName}")
    public String getIndexReport(@PathVariable String indexName) {
        return elasticService.performGETRequest("/" + indexName);
    }

    @Operation(summary = "Indexing a random document. ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "String with the response. "),
    })
    @Parameters(value = {
            @Parameter(name = "indexName", required = true, description = "Index name. ")
    })
    @GetMapping("/index/{indexName}")
    public String indexAnyDocument(@PathVariable String indexName) {
        Map<String, String> document = new HashMap<>();
        document.put("randomId", UUID.randomUUID().toString());

        return elasticService.indexDocument(indexName, document);
    }

}
