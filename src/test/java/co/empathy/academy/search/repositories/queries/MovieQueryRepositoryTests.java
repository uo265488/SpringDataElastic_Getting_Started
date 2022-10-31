package co.empathy.academy.search.repositories.queries;

import co.empathy.academy.search.controllers.ElasticController;
import co.empathy.academy.search.repositories.MovieQueryRepository;
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
public class MovieQueryRepositoryTests {
    @Autowired
    private MockMvc mvc;

    @InjectMocks
    private MovieQueryRepository movieQueryRepository;

    @Test
    void givenQueryByTitle_whenExecuting_thenReturnsCorrectResults() throws Exception {

    }

}
