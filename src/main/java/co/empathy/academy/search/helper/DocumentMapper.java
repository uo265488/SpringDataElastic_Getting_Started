package co.empathy.academy.search.helper;


import org.springframework.data.elasticsearch.core.document.Document;

import javax.json.Json;

public class DocumentMapper {

    /**
     * Receives:
     * {
     * 	"_index": "movies",
     * 	"_type": "_doc",
     * 	"_id": "tt0000001",
     * 	"_version": 2,
     * 	"result": "deleted",
     * 	"_shards": {
     * 		"total": 2,
     * 		"successful": 1,
     * 		"failed": 0
     *        },
     * 	"_seq_no": 151,
     * 	"_primary_term": 1
     * }
     * @param json
     * @return
     */
    public static Document toMovie(String json) {
        throw new RuntimeException("Not yet implemented exception. ");
    }
}
