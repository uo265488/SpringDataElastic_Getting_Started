package co.empathy.academy.search.services;

import co.empathy.academy.search.config.LowRestClientConfig;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ElasticService {

    @Autowired
    private LowRestClientConfig lowClientConfig;

    public String performGETRequest(String endpoint)  {
        String report;
        try {
            Response response=
                    lowClientConfig.lowRestClient().performRequest(new Request("GET", endpoint));
            report = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            //BETTER APPROACH???? THROW EXCEPTION OR INFORM OF BAD USE
            //throw new RuntimeException("IOException from the Low Rest Client. ");
            report = "There is something wrong with your petition. ";
        }
        return report;
    }

    public String performPUTRequest(String index)  {
        String report;
        try {
            Response response=
                    lowClientConfig.lowRestClient().performRequest(new Request("PUT", index));
            report = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            //BETTER APPROACH???? THROW EXCEPTION OR INFORM OF BAD USE
            //throw new RuntimeException("IOException from the Low Rest Client. ");
            report = "There is something wrong with your petition. ";
        }
        return report;
    }
}
