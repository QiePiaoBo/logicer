package com.dylan.licence.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dylan.framework.model.exception.MyException;
import com.dylan.framework.model.page.MyPage;
import com.dylan.framework.model.result.DataResult;
import com.dylan.framework.model.result.HttpResult;
import com.dylan.framework.model.result.PageDataResult;
import com.dylan.framework.utils.Safes;
import com.dylan.licence.entity.Access;
import com.dylan.licence.mapper.AccessMapper;
import com.dylan.licence.model.dto.AccessDTO;
import com.dylan.licence.model.vo.AccessVO;
import com.dylan.licence.service.IAccessService;
import com.dylan.licence.transformer.AccessTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Dylan
 * @Description AccessService
 * @Date : 2022/5/9 - 23:45
 */
@Slf4j
@Service
public class AccessServiceImpl implements IAccessService {

    @Resource
    private AccessMapper mapper;

    @Override
    public HttpResult getPagedAccess(MyPage myPage) {
        myPage.checkValid();
        List<Access> accesses = mapper.selectAccessList(myPage);

        List<AccessVO> accessVOS = Safes
                .of(accesses)
                .stream()
                .map(AccessTransformer::access2AccessVO)
                .collect(Collectors.toList());

        return PageDataResult
                .success()
                .page(myPage.getPageNo())
                .size(myPage.getPageSize())
                .data(accessVOS)
                .total(mapper.selectAccessTotal())
                .build();
    }

    @Override
    public HttpResult createAccess(AccessDTO accessDTO) {
        if (Objects.isNull(accessDTO)) {
            throw new MyException("Error, create obj is null.");
        }
        if (Objects.nonNull(accessDTO.getId())) {
            throw new MyException("Error, id found in create obj.");
        }
        if (Objects.isNull(accessDTO.getAccessCode())) {
            throw new MyException("Error, access code must be not null");
        }
        QueryWrapper<Access> query = Wrappers.query(new Access()).eq("access_code", accessDTO.getAccessCode());
        if (mapper.selectCount(query) > 0) {
            throw new MyException("Duplicate error, record already exists.");
        }
        Access access = AccessTransformer.accessDTO2Access(accessDTO);
        int inserted = mapper.insert(access);
        if (inserted <= 0) {
            log.error("Error insert access: {}", access);
        }
        Access returnAccess = mapper
                .selectOne(query.eq("access_code", accessDTO.getAccessCode()));
        return DataResult.success().data(AccessTransformer.access2AccessVO(returnAccess)).build();
    }

    @Override
    public HttpResult getById(Integer id) {
        if (Objects.isNull(id)) {
            throw new MyException("Error, id in getById must not be null.");
        }
        Access access = mapper.selectById(id);
        return DataResult
                .success()
                .data(AccessTransformer.access2AccessVO(access))
                .build();
    }

    @Override
    public HttpResult deleteById(Integer id) {
        if (Objects.isNull(id)) {
            throw new MyException("Error, id in deleteById must not be null.");
        }
        mapper.logicalDeletionById(id);
        Access access = mapper.selectById(id);
        return DataResult
                .success()
                .data(AccessTransformer.access2AccessVO(access))
                .build();
    }

    /**
     * 根据id更新权限信息
     *
     * @param accessDTO
     * @return
     */
    @Override
    public HttpResult updateById(AccessDTO accessDTO) {
        if (Objects.isNull(accessDTO.getId())) {
            throw new MyException("Error, id in updateById must not be null.");
        }
        Access access = AccessTransformer.accessDTO2Access(accessDTO);
        mapper.updateById(access);
        return DataResult
                .success()
                .data(accessDTO)
                .build();
    }

    /**
     * 根据id列表查询权限列表
     *
     * @param ids
     * @return
     */
    @Override
    public HttpResult selectAccessListByIds(List<Integer> ids) {
        List<Access> accesses = mapper.selectAccessListByIds(ids);
        List<AccessVO> accessVOS = Safes
                .of(accesses)
                .stream()
                .map(AccessTransformer::access2AccessVO)
                .collect(Collectors.toList());
        return DataResult
                .success()
                .data(accessVOS)
                .build();
    }
}
