package com.dylan.file.service;

import com.dylan.file.dto.FileStorageDTO;
import com.dylan.framework.model.result.DataResult;
import com.qiniu.storage.model.FileInfo;

import java.util.List;

/**
 * @Classname QiNiuService
 * @Description QiNiuService
 * @Date 9/15/2022 5:36 PM
 */
public interface QiNiuService {

    DataResult upload2QiNiu(FileStorageDTO dto);

    List<FileInfo> queryFileList();

    void getQiNiuLog();
}
