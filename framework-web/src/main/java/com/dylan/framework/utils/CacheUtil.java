package com.dylan.framework.utils;

import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

@Component
public class CacheUtil {

    private static final MyLogger logger = MyLoggerFactory.getLogger(CacheUtil.class);

    @CacheEvict(value = "articleService", allEntries = true)
    public void deleteCacheOfArticle(){
        logger.info("Deleting cache of articleService...");
    }

    @CacheEvict(value = "confettiService", allEntries = true)
    public void deleteCacheOfConfetti(){
        logger.info("Deleting cache of articleService...");
    }



}
