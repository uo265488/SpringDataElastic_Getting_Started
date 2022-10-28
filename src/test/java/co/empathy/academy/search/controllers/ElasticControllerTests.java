package co.empathy.academy.search.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class ElasticControllerTests {

    @Autowired
    private MockMvc mvc;

    @InjectMocks
    private ElasticController elasticController;

    @Test
    void givenQuery_whenSearch_thenReturnsSearchAndElasticVersion() throws Exception {
        //JUST CONTROLLER, NOT GENERAL RESPONSE
        mvc.perform(MockMvcRequestBuilders.get("/search?query=algo"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect((MockMvcResultMatchers.content().string("Hello mariano")));
    }
}
