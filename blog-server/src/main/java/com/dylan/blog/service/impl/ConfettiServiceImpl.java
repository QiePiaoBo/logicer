package com.dylan.blog.service.impl;

import com.dylan.blog.converter.ConfettiConverter;
import com.dylan.blog.entity.ConfettiEntity;
import com.dylan.blog.mapper.ConfettiMapper;
import com.dylan.blog.model.ConfettiInsertModel;
import com.dylan.blog.model.ConfettiMergeModel;
import com.dylan.blog.model.ConfettiQueryModel;
import com.dylan.blog.service.ConfettiService;
import com.dylan.blog.vo.ConfettiVO;
import com.dylan.framework.model.result.DataResult;
import com.dylan.framework.model.result.HttpResult;
import com.dylan.framework.utils.Safes;
import com.dylan.licence.model.vo.UserVO;
import com.dylan.licence.service.UserBaseInfoService;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Classname MsgRecordService
 * @Description MsgRecordService
 * @Date 6/28/2023 3:27 PM
 */
@Service
@CacheConfig(cacheManager = "myCacheManager", cacheNames = {"confettiService"})
public class ConfettiServiceImpl implements ConfettiService {

    private static final MyLogger logger = MyLoggerFactory.getLogger(ConfettiServiceImpl.class);

    @Resource
    private ConfettiMapper confettiMapper;

    @DubboReference(version = "1.0.0")
    private UserBaseInfoService userBaseInfoService;

    /**
     * 添加纸屑
     * @param model
     * @return
     */
    @Override
    public HttpResult addConfetti(ConfettiInsertModel model){
        // todo 检查参数 参数预处理
        if (!model.insertValid()){
            return DataResult.fail().data("Error param: " + model).build();
        }
        boolean inserted = confettiMapper.addConfetti(model) > 0;
        if (inserted){
            return DataResult.getBuilder().data(ConfettiConverter.getConfettiVO(model)).build();
        }else {
            return DataResult.fail().data("Insert error.").build();
        }
    }

    /**
     * 查询用户Id下的纸屑
     * @return
     */
    @Override
    @Cacheable(key = "#queryModel != null ? #queryModel.getCacheKey():T(com.dylan.blog.config.BlogConstants).CACHE_REDIS_GET_CONFETTI_FOR_USER", unless = "#result == null")
    public HttpResult getConfettiForUser(ConfettiQueryModel queryModel) {
        if (!queryModel.isValid()){
            return DataResult.fail().data("Error param: " + queryModel).build();
        }
        List<ConfettiEntity> entities;
        if(queryModel.getUserId() == 0){
            entities = confettiMapper.getConfettiForUsers(queryModel);
        }else {
            entities = confettiMapper.getConfettiForUser(queryModel);
        }
        List<ConfettiVO> confettiVOList = Safes.of(entities).stream().map(ConfettiConverter::getConfettiVO).collect(Collectors.toList());
        // 补充userName属性
        Safes.of(confettiVOList).forEach(m -> {
            UserVO userVO = userBaseInfoService.getUserVOById(m.getUserId());
            m.setUserName(userVO.getUserName());
        });
        return DataResult.getBuilder().data(confettiVOList).build();
    }

    /**
     * 将两个小纸屑进行合并
     * @param model
     * @return
     */
    @Override
    public HttpResult mergeConfetti(ConfettiMergeModel model) {
        if (!model.isRight()){
            return DataResult.fail().data("Error model: " + model).build();
        }
        Integer mergeFromId = model.getMergeFrom();
        Integer mergeToId = model.getMergeTo();
        List<ConfettiEntity> entities = confettiMapper.getConfettiOfIds(Arrays.asList(mergeFromId, mergeToId));
        List<ConfettiEntity> cleanedEntity = Safes.of(entities).stream().filter(m -> Objects.equals(m.getUserId(), model.getCurUserId())).collect(Collectors.toList());
        if (cleanedEntity.size() != 2){
            return DataResult.fail().data("Error model: " + model).build();
        }
        ConfettiEntity c1 = entities.get(0);
        ConfettiEntity c2 = entities.get(1);
        if (c1.getId().equals(mergeFromId)){
            c1.setDelFlag(1);
            String firstContent = c2.getContent();
            String secondContent = c1.getContent();
            String finalContent = firstContent + "\n------分割线------\n" + "《" + c1.getTitle() + "》\n" + secondContent;
            c2.setContent(finalContent);
        }else {
            c2.setDelFlag(1);
            String firstContent = c1.getContent();
            String secondContent = c2.getContent();
            String finalContent = firstContent + "\n------分割线------\n" + "《" + c2.getTitle() + "》\n"  + secondContent;
            c1.setContent(finalContent);
        }
        Integer changed = confettiMapper.addOrUpdateConfettiBatch(Arrays.asList(c1, c2));
        if (changed > 0){
            return DataResult.success().build();
        }else {
            return DataResult.fail().data("Error add or update.").build();
        }
    }
}
