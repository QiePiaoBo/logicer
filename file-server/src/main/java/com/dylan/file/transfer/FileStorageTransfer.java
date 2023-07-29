package com.dylan.file.transfer;

import com.dylan.file.entity.FileStorage;
import com.dylan.file.model.FileStorageModel;
import com.dylan.framework.constant.FileConstant;
import com.dylan.framework.model.dto.QiNiuFileInfo;
import com.dylan.framework.utils.QiNiuTransfer;
import com.dylan.framework.utils.Safes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qiniu.storage.model.FileInfo;

import java.util.Objects;

/**
 * @Classname FileUploadTransfer
 * @Description FileUploadTransfer
 * @Date 9/20/2022 1:50 PM
 */
public class FileStorageTransfer {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 入参转化为实体 字符串默认值为空
     * @param model
     * @return
     */
    public static FileStorage model2FileUpload(FileStorageModel model){
        FileStorage res = new FileStorage();
        res.setId(model.getId());
        res.setType(Safes.of(model.getType(), 0));
        res.setUploadInfo(Safes.of(model.getUploadInfo()));
        res.setFileName(Safes.of(model.getFileName()));
        res.setFileUri(Safes.of(model.getFileUri()));
        res.setFileSize(model.getFileSize());
        res.setHash(Safes.of(model.getHash()));
        res.setBucket(Safes.of(model.getBucket()));

        res.setDelFlag(Safes.of(model.getDelFlag(), Boolean.FALSE));
        res.setCreatedAt(model.getCreatedAt());
        res.setUpdatedAt(model.getUpdatedAt());
        return res;
    }

    /**
     * 从七牛上传结果中获取文件上传参数以便存入数据库
     * @param responseInfo
     * @return
     */
    public static FileStorageModel getModelFromQiNiuRespInfo(String responseInfo){
        QiNiuFileInfo qiNiuFileInfo = QiNiuTransfer.getInfoFromQiNiuResponse(responseInfo);
        if (Objects.isNull(qiNiuFileInfo)){
            return null;
        }
        FileStorageModel result = new FileStorageModel();
        result.setFileName(qiNiuFileInfo.getKey());
        result.setFileUri(FileConstant.QINIU_FILE_PREFIX + qiNiuFileInfo.getKey());
        result.setType(0);
        result.setFileSize(qiNiuFileInfo.getFsize());
        result.setBucket(qiNiuFileInfo.getBucket());
        result.setHash(qiNiuFileInfo.getHash());
        result.setUploadInfo(responseInfo);
        return result;
    }

    /**
     * 从七牛云查出的文件信息转化为文件上传实体类以便存入数据库
     * @param bucketName
     * @param uriPre
     * @param fileInfo
     * @return
     */
    public static FileStorageModel getFileUploadModel(String bucketName, String uriPre, FileInfo fileInfo){
        FileStorageModel result = new FileStorageModel();
        result.setBucket(bucketName);
        result.setFileName(fileInfo.key);
        result.setFileUri(uriPre + fileInfo.key);
        result.setFileSize(fileInfo.fsize);
        result.setHash(fileInfo.hash);
        result.setType(0);
        try {
            result.setUploadInfo(objectMapper.writeValueAsString(fileInfo));
        } catch (JsonProcessingException e) {
            result.setUploadInfo(bucketName + "@" + fileInfo.key);
        }
        return result;
    }


}
