package com.dylan.file.service;


import com.dylan.file.entity.FileStorage;
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
     * 根据id查找文件保存信息
     * @param fileStorageId
     * @return
     */
    FileStorage selectById(Integer fileStorageId);
    /**
     * 插入
     * @param model
     * @return
     */
    DataResult insert(FileStorageModel model);

    /**
     * 根据七牛云返回值插入
     * @param qiNiuResponse
     * @return
     */
    FileStorage insertByQiNiuResponse(String qiNiuResponse);

    /**
     * 批量插入
     * @param entities
     * @return
     */
    DataResult batchInsert(List<FileStorageModel> entities);

    /**
     * 根据阿里云OSS文件对象保存文件信息插入
     * @param fileOssName
     * @return
     */
    FileStorage insertByOSSFileName(String fileOssName);

    /**
     * 根据服务器文件全路径插入
     * @param serverFilePath
     * @return
     */
    FileStorage insertByOSFilePath(String serverFilePath);

    /**
     * 根据文件保存id查找对应的url
     * @param fileId
     * @return
     */
    String selectFileUrlById(Integer fileId);
}
