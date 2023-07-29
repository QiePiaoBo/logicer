package com.dylan.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dylan.blog.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * (Article)表数据库访问层
 *
 * @author Dylan
 * @since 2020-06-14 20:24:19
 */
@Mapper
@Component
@CacheConfig(cacheManager = "myCacheManager", cacheNames = {"articles"})
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Cacheable(key = "#p0")
    Article queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Article> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param article 实例对象
     * @return 对象列表
     */
    List<Article> queryAll(Article article);

    /**
     * 新增数据
     *
     * @param article 实例对象
     * @return 影响行数
     */
    int saveSelective(Article article);


    List<Date> queryWithCreateTime(int id);

    List<Article> queryArticlesInOneDay(@Param("createTime") String createTime,@Param("authorId") int authorId);
}