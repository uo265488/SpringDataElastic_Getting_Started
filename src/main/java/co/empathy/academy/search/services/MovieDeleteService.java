package co.empathy.academy.search.services;

import co.empathy.academy.search.documents.Movie;
import co.empathy.academy.search.repositories.deleting.DeleteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MovieDeleteService {

    @Autowired
    private DeleteRepository<Movie> deleteRepository;

    public String deleteMovie(UUID id) {
        return deleteRepository.deleteDocument(id.toString());
    }

    public boolean deleteIndex() {
        return deleteRepository.deleteIndex();
    }
}
