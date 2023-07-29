package com.dylan.chat.service;

import com.dylan.chat.entity.ConfettiEntity;
import com.dylan.chat.mapper.ConfettiMapper;
import com.dylan.chat.model.ConfettiInsertModel;
import com.dylan.chat.model.ConfettiMergeModel;
import com.dylan.chat.model.ConfettiQueryModel;
import com.dylan.chat.model.converter.ConfettiConverter;
import com.dylan.chat.model.vo.ConfettiVO;
import com.dylan.framework.model.result.DataResult;
import com.dylan.framework.model.result.HttpResult;
import com.dylan.framework.utils.Safes;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
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
public class ConfettiServiceImpl implements ConfettiService {

    private static final MyLogger logger = MyLoggerFactory.getLogger(ConfettiServiceImpl.class);

    @Resource
    private ConfettiMapper confettiMapper;


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
    public HttpResult getConfettiForUser(ConfettiQueryModel queryModel) {
        if (!queryModel.isValid()){
            return DataResult.fail().data("Error param: " + queryModel).build();
        }
        List<ConfettiEntity> entities = confettiMapper.getConfettiForUser(queryModel);
        List<ConfettiVO> confettiVOList = Safes.of(entities).stream().map(ConfettiConverter::getConfettiVO).collect(Collectors.toList());
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
