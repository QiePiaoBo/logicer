package com.dylan.file.service;


import com.dylan.file.model.FileStorageModel;
import com.dylan.framework.model.result.DataResult;

import java.util.List;

/**
 * @Classname FileUploadService
 * @Description TODO
 * @Date 9/19/2022 6:47 PM
 */
public interface FileStorageService {

    /**
     * 插入
     * @param model
     * @return
     */
    DataResult insert(FileStorageModel model);

    /**
     * 批量插入
     * @param entities
     * @return
     */
    DataResult batchInsert(List<FileStorageModel> entities);

}
