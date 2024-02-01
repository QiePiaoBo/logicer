package com.dylan.licence.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.CreateResponse;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import com.dylan.licence.model.es.Book;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsBookTest {

    private static final MyLogger logger = MyLoggerFactory.getLogger(EsBookTest.class);


    @Autowired
    private ElasticsearchClient esClient;

    @Test
    public void test() {
        System.out.println(LocalDate.now().toString());
    }


    @Test
    public void createQueryAndDeleteIndex() throws IOException {
        createIndex();
        queryIndex();
        deleteIndex();
    }

    @Test
    public void createIndex() throws IOException {
        String dateFormat = "yyyy-MM-dd || yyyy-MM-dd HH:mm:ss || yyyy-MM-dd HH:mm:ss.SSSX || yyyy-MM-dd'T'HH:mm:ss'+08:00' || strict_date_optional_time || epoch_millis";
        CreateIndexResponse create = esClient.indices().create(c -> c.index("book").mappings(typeMapping ->
                typeMapping
                        .properties("id", o -> o.text(text -> text.fielddata(true)))
                        .properties("createTime", o -> o.date(date -> date.index(false).format(dateFormat).ignoreMalformed(true)))
                        .properties("updateTime", o -> o.date(date -> date.index(false).format(dateFormat).ignoreMalformed(true)))
                        .properties("title", o -> o.text(text -> text.fielddata(true).analyzer("ik_smart")))
                        .properties("author", o -> o.text(text -> text.fielddata(true).analyzer("ik_smart")))
                        .properties("price", o -> o.double_(d -> d.nullValue(0.0)))

        ));
        logger.info("result of create index : {}", create);
    }


    @Test
    public void queryIndex() throws IOException {
        GetIndexResponse getIndexResponse = esClient.indices().get(e -> e.index("test_es_client"));
        logger.info("result of create index : {}", getIndexResponse);
    }



    @Test
    public void deleteIndex() throws IOException {
        DeleteIndexResponse deleteIndexResponse = esClient.indices().delete(e -> e.index("test_es_client"));
        logger.info("result of create index : {}", deleteIndexResponse);
    }


    @Test
    public void addDoc() throws IOException {
        Book book = new Book();
        book.setTitle("西游记");
        book.setId("4");
        book.setPrice(99.0);
        book.setAuthor("吴承恩");
        book.setCreateTime(new Date());
        CreateResponse createResponse = esClient.create(e -> e.index("book").id("4").document(book));
        logger.info("res of create doc : {}", createResponse);
    }

    @Test
    public void queryDoc() throws IOException {
        GetResponse<Book> bookGetResponse = esClient.get(e -> e.index("book").id("4"), Book.class);

        logger.info("res: {}", bookGetResponse.source());

    }

    @Test
    public void addAndQueryDoc() throws IOException {
        addDoc();
        queryDoc();
    }

}
