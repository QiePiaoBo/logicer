package com.dylan.licence.service;

import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsBookTest {

    private static final MyLogger logger = MyLoggerFactory.getLogger(EsBookTest.class);


    @Test
    public void test() {
        System.out.println(LocalDate.now().toString());
    }


    @Test
    public void createQueryAndDeleteIndex() throws IOException {

    }

    @Test
    public void createIndex() throws IOException {

    }


    @Test
    public void queryIndex() throws IOException {

    }



    @Test
    public void deleteIndex() throws IOException {

    }


    @Test
    public void addDoc() throws IOException {

    }

    @Test
    public void queryDoc() throws IOException {


    }


    @Test
    public void addAndQueryDoc() throws IOException {
        addDoc();
        queryDoc();
    }

    @Test
    public void query() throws Exception {

    }

    @Test
    public void deleteDoc() throws IOException {

    }

    @Test
    public void querySource() throws IOException {

    }

}
