package com.dylan.blog.service.impl;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dylan.blog.converter.ArticleConverter;
import com.dylan.blog.entity.Article;
import com.dylan.blog.mapper.ArticleMapper;
import com.dylan.blog.service.ArticleService;
import com.dylan.blog.vo.ArticleVO;
import com.dylan.framework.model.info.Message;
import com.dylan.framework.model.info.Status;
import com.dylan.framework.model.result.DataResult;
import com.dylan.framework.utils.CacheUtil;
import com.dylan.framework.utils.Safes;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (Article)表服务实现类
 *
 * @author Dylan
 * @since 2020-06-14 20:24:19
 */
@Slf4j
@Service
@RefreshScope
@DubboService(version = "1.0.0")
@CacheConfig(cacheManager = "lgcCacheManager", cacheNames = {"articleService"})
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private LicService licService;

    @Resource
    private CacheUtil cacheUtil;

    private static final MyLogger logger = MyLoggerFactory.getLogger(ArticleServiceImpl.class);

    /**
     * 获取全部符合条件的文章列表
     * 管理员查看所有文章
     * 目前可根据文章id 文章名 文章二级标题 文章类型来查询
     * 其中文章名和二级标题是模糊查询
     * @return
     */
    @Override
    @Cacheable(key = "#article != null ? #article.getCacheKey():T(com.dylan.blog.config.BlogConstants).CACHE_REDIS_QUERY_RIGHT", unless = "#result == null")
    public DataResult queryRight(Article article) {
        DataResult dataResult;
        article.setIsLock(0);
        article.setIsDel(0);
        List<Article> list = articleMapper.queryAll(article);
        if (list.size() > 0) {
            dataResult = DataResult.getBuilder().data(list).build();
            return dataResult;
        }
        return DataResult.getBuilder(Status.QUERY_ERROR.getStatus(), Message.QUERY_ERROR.getMsg()).build();
    }

    /**
     * 获取文章简略信息列表
     *
     * @return
     */
    @Override
    @Cacheable(key = "T(com.dylan.blog.config.BlogConstants).CACHE_REDIS_GET_ARTICLE_LIST", unless = "#result == null")
    public List<ArticleVO> getArticleList() {
        Article article = new Article();
        article.setIsDel(0);
        article.setIsLock(0);
        List<Article> list = articleMapper.queryAll(article);
        return Safes.of(list).stream().map(ArticleConverter::getArticleVO).collect(Collectors.toList());
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Article queryById(Integer id) {
        return this.articleMapper.queryById(id);
    }


    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Article> queryAllByLimit(int offset, int limit) {
        return this.articleMapper.queryAllByLimit(offset, limit);
    }


    /**
     * 新增数据
     * 注意：不提供controller使用
     * @param article 实例对象
     * @return 实例对象
     */
    @Override
    public Article insert(Article article) {
        int insert = articleMapper.saveSelective(article);
        if (insert > 0) {
            cacheUtil.deleteCacheOfArticle();
            return article;
        }
        return null;
    }

    /**
     * 修改数据
     *
     * @param article 实例对象
     * @return 实例对象
     */
    @Override
    public DataResult update(Article article) {
        // 根据id获取目标记录
       Article aimArticle = articleMapper.queryById(article.getId());
       // 修改非空的某几种属性
        if (article.getTitle() != null){
            aimArticle.setTitle(article.getTitle());
        }
        if (article.getSubTitle() != null){
            aimArticle.setSubTitle(article.getSubTitle());
        }
        if (article.getFileType() != null){
            aimArticle.setFileType(article.getFileType());
        }
        if (article.getDescription() != null){
            aimArticle.setDescription(article.getDescription());
        }
        // 修改目标对象
        if (licService.getUser().getId().equals(aimArticle.getUserId()) || licService.getUser().getUserGroup() < 1){
            // 上传者及管理员可以决定是否展示
            if (article.getIsLock()!=null){
                aimArticle.setIsLock(article.getIsLock());
            }
            int update = articleMapper.update(aimArticle,new UpdateWrapper<Article>().eq("id", article.getId()));
            if (update < 1){
                return DataResult.getBuilder(Status.UPDATE_ERROR.getStatus(), Message.UPDATE_ERROR.getMsg()).build();
            }
            return DataResult.getBuilder(Status.SUCCESS.getStatus(), Message.SUCCESS.getMsg()).data(aimArticle).build();
        }
        return DataResult.getBuilder(Status.PERMISSION_ERROR.getStatus(), Message.PERMISSION_ERROR.getMsg()).build();
    }


    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public DataResult deleteById(Integer id) {
        Article article = articleMapper.queryById(id);
        if (licService.getUser().getId().equals(article.getUserId()) || licService.getUser().getUserGroup() < 1){
            article.setIsDel(1);
            int delNum = articleMapper.update(article, new UpdateWrapper<Article>().eq("id", id));
            if (delNum < 1){
                return DataResult.getBuilder(Status.DELETE_ERROR.getStatus(), Message.DELETE_ERROR.getMsg()).build();
            }
            return DataResult.success().build();
        }
        return DataResult.getBuilder(Status.PERMISSION_ERROR.getStatus(), Message.PERMISSION_ERROR.getMsg()).build();
    }
}