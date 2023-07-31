package com.dylan.blog.service;

import com.dylan.blog.model.ConfettiInsertModel;
import com.dylan.blog.model.ConfettiMergeModel;
import com.dylan.blog.model.ConfettiQueryModel;
import com.dylan.framework.model.result.HttpResult;

public interface ConfettiService {
    /**
     * 添加纸屑
     *
     * @param model
     * @return
     */
    HttpResult addConfetti(ConfettiInsertModel model);

    /**
     * 查询用户Id下的纸屑
     *
     * @return
     */
    HttpResult getConfettiForUser(ConfettiQueryModel queryModel);

    /**
     * 将两个小纸屑进行合并
     *
     * @param model
     * @return
     */
    HttpResult mergeConfetti(ConfettiMergeModel model);
}
