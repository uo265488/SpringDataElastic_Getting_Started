package co.empathy.academy.search.repositories.deleting;

import org.springframework.stereotype.Repository;

@Repository
public interface DeleteRepository<Document> {

    /**
     * Delete document
     * @param _id of the document
     * @return the document deleted
     */
    String deleteDocument(String _id);

    /**
     * Delete index
     * @return true if deleted, false uf not
     */
    boolean deleteIndex();

}
