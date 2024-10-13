package com.dylan.blog.service.impl;


import com.aliyun.oss.common.utils.StringUtils;
import com.dylan.blog.mapper.FileStorageMapper;
import com.dylan.file.entity.FileStorage;
import com.dylan.file.model.FileStorageModel;
import com.dylan.file.service.FileStorageService;
import com.dylan.blog.transfer.FileStorageTransfer;
import com.dylan.framework.model.info.Message;
import com.dylan.framework.model.info.Status;
import com.dylan.framework.model.result.DataResult;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @Classname FileStorageServiceImpl
 * @Description FileStorageServiceImpl
 * @Date 9/20/2022 1:48 PM
 */
@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Resource
    private FileStorageMapper fileStorageMapper;

    @Override
    public DataResult insert(FileStorageModel model) {
        int insert = fileStorageMapper.insert(FileStorageTransfer.model2FileUpload(model));
        if (insert > 0){
            return DataResult.success(Status.SUCCESS.getStatus(), Message.SUCCESS.getMsg()).data(model.getFileUri()).build();
        }
        return DataResult.fail(Status.INSERT_ERROR.getStatus(), Message.INSERT_ERROR.getMsg()).data(model.getFileUri()).build();
    }

    @Override
    public DataResult batchInsert(List<FileStorageModel> entities){
        fileStorageMapper.insertStorages(entities);
        return DataResult.success(Status.SUCCESS.getStatus(), Message.SUCCESS.getMsg()).build();
    }

    /**
     * 根据七牛云返回值插入
     *
     * @param qiNiuResponse
     * @return
     */
    @Override
    public FileStorage insertByQiNiuResponse(String qiNiuResponse) {
        FileStorageModel fileStorageModel = FileStorageTransfer.getModelFromQiNiuRespInfo(qiNiuResponse);
        if (Objects.isNull(fileStorageModel)) {
            return null;
        }
        FileStorage fileStorage = FileStorageTransfer.model2FileUpload(fileStorageModel);
        int insert = fileStorageMapper.insert(fileStorage);
        if (insert > 0) {
            return fileStorage;
        }
        return null;
    }

    /**
     * 根据字符串为阿里云OSS文件对象保存文件信息
     *
     * @param ossFileName
     * @return
     */
    @Override
    public FileStorage insertByOSSFileName(String ossFileName) {
        if (StringUtils.isNullOrEmpty(ossFileName)) {
            return null;
        }
        FileStorageModel fileStorageModel = FileStorageTransfer.getModelFromOSSFileName(ossFileName);
        FileStorage fileStorage = FileStorageTransfer.model2FileUpload(fileStorageModel);
        int insert = fileStorageMapper.insert(fileStorage);
        if (insert > 0) {
            return fileStorage;
        }
        return null;
    }

    /**
     * 根据服务器文件全路径插入
     *
     * @param serverFilePath
     * @return
     */
    @Override
    public FileStorage insertByOSFilePath(String serverFilePath) {
        if (StringUtils.isNullOrEmpty(serverFilePath)) {
            return null;
        }
        FileStorageModel fileStorageModel = FileStorageTransfer.getModelFromServerFilePath(serverFilePath);
        FileStorage fileStorage = FileStorageTransfer.model2FileUpload(fileStorageModel);
        int insert = fileStorageMapper.insert(fileStorage);
        if (insert > 0) {
            return fileStorage;
        }
        return null;
    }
}
