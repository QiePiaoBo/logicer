package com.dylan.chat.controller;


import com.dylan.chat.model.ConfettiInsertModel;
import com.dylan.chat.model.ConfettiMergeModel;
import com.dylan.chat.model.ConfettiQueryModel;
import com.dylan.chat.service.ConfettiService;
import com.dylan.framework.model.result.DataResult;
import com.dylan.framework.model.result.HttpResult;
import com.dylan.licence.model.UserNameIdModel;
import com.dylan.licence.service.UserService;
import lombok.NonNull;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("confetti")
public class ConfettiController {

    @Autowired
    private UserService userService;

    @Resource
    private ConfettiService confettiService;


    /**
     * 添加或查询纸屑需要是登陆状态 第一步需要传入userName获取id
     * @param userName
     * @return
     */
    @RequestMapping(value = "get-voucher", method = RequestMethod.GET)
    public HttpResult getVoucherForUser(@Param("userName") @NonNull String userName){
        List<UserNameIdModel> res = userService.getUserNameId(Arrays.asList(userName));
        if (res.size() == 1){
            return DataResult.success().data(res.get(0)).build();
        }
        return DataResult.fail().build();
    }

    @RequestMapping(value = "get-confetti", method = RequestMethod.POST)
    public HttpResult getConfettiForUser(@RequestBody ConfettiQueryModel queryModel){
        return confettiService.getConfettiForUser(queryModel);
    }

    @RequestMapping(value = "add-confetti", method = RequestMethod.POST)
    public HttpResult addConfettiForUser(@RequestBody ConfettiInsertModel insertModel){
        return confettiService.addConfetti(insertModel);
    }

    @RequestMapping(value = "merge-confetti", method = RequestMethod.POST)
    public HttpResult mergeConfettiForUser(@RequestBody ConfettiMergeModel model) {
        return confettiService.mergeConfetti(model);
    }

    @RequestMapping(value = "test-feign", method = RequestMethod.GET)
    public HttpResult getUserNameIdMap() {
        return null;
    }
}
