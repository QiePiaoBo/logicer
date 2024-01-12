package com.dylan.licence.service.es;

import com.dylan.licence.model.es.Book;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BookService {

    private static final MyLogger logger = MyLoggerFactory.getLogger(BookService.class);

//    @Autowired
//    ESBookRepository esBookRepository;
//
//    @Resource
//    TransactionTemplate transactionTemplate;
//
//
//    public boolean addBook(Book book){
//        if (book.isValid()){
//            try {
//                esBookRepository.save(book);
//            }catch (Exception e){
//                logger.error("保存es错误 {}", book);
//                return false;
//            }
//            return true;
//        }else {
//            return false;
//        }
//    }
//
//    public List<Book> searchBookByTitleOrAuthor(String keyword){
//        return esBookRepository.findByTitleOrAuthor(keyword, keyword);
//    }
//
//    public SearchHits<Book> searchBook(String keyword){
//        return esBookRepository.find(keyword);
//    }
}
