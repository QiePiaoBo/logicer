package com.dylan.licence.service;

import com.alibaba.fastjson2.JSON;
import com.dylan.licence.model.es.Book;
import com.dylan.licence.service.es.BookService;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsBookTest {

    private static final MyLogger logger = MyLoggerFactory.getLogger(EsBookTest.class);

    @Autowired
    private RestHighLevelClient esRestClient;

    @Resource
    BookService bookService;

    @Test
    public void add(){
        // 创建索引请求 向book下存进行数据操作
        IndexRequest indexRequest = new IndexRequest("book");
        indexRequest.id("1");

        Book book = new Book();
        book.setId("000001");
        book.setAuthor("Dylan");
        book.setTitle("西游记");
        book.setPrice(99.9);
        book.setCreateTime(new Date());
        String jsonString = JSON.toJSONString(book);
        // 设置数据内容的形式为JSON
        indexRequest.source(jsonString, XContentType.JSON);

        try {
            IndexResponse indexResponse = esRestClient.index(indexRequest, RequestOptions.DEFAULT);
            logger.info("res of add: {}", indexResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
