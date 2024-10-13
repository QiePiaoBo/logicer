package com.dylan.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dylan.file.entity.FileStorage;
import com.dylan.file.model.FileStorageModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Classname FileUploadMapper
 * @Description FileUploadMapper
 * @Date 9/19/2022 6:48 PM
 */
@Mapper
@Component
public interface FileStorageMapper extends BaseMapper<FileStorage> {


    void insertStorages(@Param("fileStorageModels") List<FileStorageModel> fileStorageModels);


}
