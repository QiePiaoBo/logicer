package com.dylan.licence.service.es;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.CreateResponse;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.dylan.licence.model.es.Book;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.common.unit.Fuzziness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private ElasticsearchClient esClient;

    private static final MyLogger logger = MyLoggerFactory.getLogger(BookService.class);

    /**
     * 为book创建一个doc
     * @param book
     * @return
     */
    public CreateResponse createDoc(Book book) {
        if (!book.dataValid()){
            return null;
        }
        try {
            return esClient.create(e -> e.index("book").id(book.getId()).document(book));
        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }
    }


    /**
     * 根据id删除一个doc
     * @param docId
     * @return
     */
    public DeleteResponse deleteDoc(String docId){
        if (StringUtils.isBlank(docId)){
            return null;
        }
        try {
            return esClient.delete(d -> d.index("book").id(docId));
        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }
    }


    /**
     * 根据title关键字搜索Book
     * @param titleKeyWord
     * @return
     */
    public List<Book> searchBookByTitle(String titleKeyWord) {
        if (StringUtils.isBlank(titleKeyWord)){
            return null;
        }
        try {
            //
            SearchRequest searchRequest = SearchRequest.of(s -> s
                    .index("book")
                    .query(q -> q.bool(
                            b -> b.should(
                                    s1 -> s1.fuzzy(f -> f
                                            .field("title")
                                            .value(titleKeyWord)
                                            .fuzziness(Fuzziness.AUTO.asString())
                                    )
                            )
                    )));
            SearchResponse<Book> searchResponse = esClient.search(searchRequest, Book.class);
            logger.info("res : {}", searchResponse.hits().hits());
            return searchResponse.hits().hits().stream().filter(Objects::nonNull).map(Hit::source).collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("Error : {}", e.getMessage());
            return null;
        }
    }
}
