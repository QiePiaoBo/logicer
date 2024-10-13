package com.dylan.blog.service.impl;

import com.dylan.file.model.FileStorageModel;
import com.dylan.file.service.FileStorageService;
import com.dylan.file.service.QiNiuService;
import com.dylan.blog.transfer.FileStorageTransfer;
import com.dylan.framework.utils.QiNiuUtil;
import com.dylan.framework.constant.FileConstant;
import com.dylan.framework.utils.Safes;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import com.qiniu.storage.model.FileInfo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

/**
 * @Classname QiNiuServiceImpl
 * @Description QiNiuServiceImpl
 * @Date 9/15/2022 5:37 PM
 */
@Service
public class QiNiuServiceImpl implements QiNiuService {

    private static final MyLogger log = MyLoggerFactory.getLogger(QiNiuServiceImpl.class);

    @Resource
    private FileStorageService fileStorageService;

    @Value("${qiniu.accesskey:}")
    String accessKey;
    @Value("${qiniu.secretkey:}")
    String secretKey;
    @Value("${qiniu.bucketname:}")
    String bucketName;



    /**
     * 将七牛云的文件同步到数据库中
     */
    @Override
    public void getQiNiuLog(){
        List<FileInfo> fileInfos = queryFileList();
        List<FileStorageModel> uploadModels = Safes.of(fileInfos)
                .stream()
                .map(m -> FileStorageTransfer.getFileUploadModel(bucketName, FileConstant.QINIU_FILE_PREFIX, m))
                .collect(Collectors.toList());
        fileStorageService.batchInsert(uploadModels);
    }

    @Override
    public List<FileInfo> queryFileList() {
        return QiNiuUtil.queryFileList(bucketName, "", accessKey, secretKey, "");
    }
}
