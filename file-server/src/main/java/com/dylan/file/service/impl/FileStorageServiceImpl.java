package com.dylan.file.service.impl;


import com.dylan.file.mapper.FileStorageMapper;
import com.dylan.file.model.FileStorageModel;
import com.dylan.file.service.FileStorageService;
import com.dylan.file.transfer.FileStorageTransfer;
import com.dylan.framework.model.info.Message;
import com.dylan.framework.model.info.Status;
import com.dylan.framework.model.result.DataResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Classname FileUploadServiceImpl
 * @Description FileUploadServiceImpl
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
}
