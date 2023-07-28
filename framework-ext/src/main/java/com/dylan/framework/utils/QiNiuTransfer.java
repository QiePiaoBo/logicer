package com.dylan.framework.utils;


import com.dylan.framework.model.dto.QiNiuFileInfo;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

/**
 * @Classname QiNiuTransfer
 * @Description QiNiuTransfer
 * @Date 9/19/2022 5:13 PM
 */
public class QiNiuTransfer {

    private static final MyLogger log = MyLoggerFactory.getLogger(QiNiuTransfer.class);

    public static QiNiuFileInfo getInfoFromQiNiuResponse(String resp) {

        QiNiuFileInfo result = null;

        String[] split = resp.split("\n");
        List<String> strings = Arrays.asList(split);
        if (split.length != 3){
            log.error("Error, can not parse response:{}.", strings);
            return result;
        }
        String aimStr = strings.get(strings.size() - 1);
        log.info("aimString: {}", aimStr);
        try {
            result = new ObjectMapper().readValue(aimStr, QiNiuFileInfo.class);
        } catch (JsonProcessingException e) {
            log.error("Error parsing aimString: {}", aimStr);
            return result;
        }
        return result;
    }

}
