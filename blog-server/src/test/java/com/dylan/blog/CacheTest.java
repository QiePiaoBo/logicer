package com.dylan.blog;

import com.dylan.framework.utils.CacheUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import jakarta.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CacheTest {

    @Resource
    private CacheUtil cacheUtil;


    @Test
    public void testDeleteArticleCache() {
        cacheUtil.deleteCacheOfArticle();

    }

    @Test
    public void testDeleteConfettiCache() {
        cacheUtil.deleteCacheOfConfetti();

    }
}
