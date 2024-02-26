package com.dylan.licence.controller;

import com.dylan.framework.model.result.DataResult;
import com.dylan.framework.model.result.HttpResult;
import com.dylan.licence.model.es.Book;
import com.dylan.licence.service.es.BookService;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Classname UserInfoController
 * @Description UserInfoController
 * @Date 5/7/2022 4:45 PM
 */
@RestController
@RequestMapping("book")
public class BookController {

    @Resource
    private BookService bookService;

    private static final MyLogger logger = MyLoggerFactory.getLogger(BookController.class);

    /**
     * 添加一本书籍
     * @param book
     * @return
     */
    @PostMapping("add-book")
    public HttpResult addBook(@RequestBody Book book){
        return DataResult.success().data(bookService.createDoc(book)).build();
    }

    /**
     * 添加一本书籍
     * @param keyword
     * @return
     */
    @GetMapping("get-book-by-title")
    public HttpResult getBookByTitleKeyword(@Param("keyword") String keyword){
        return DataResult.success().data(bookService.searchBookByTitle(keyword)).build();
    }
}
