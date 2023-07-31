package com.dylan.licence.common;

import com.dylan.framework.utils.AesUtil;
import com.dylan.licence.mapper.UserMapperTest;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * @author Dylan
 * @Description StringTest
 * @Date : 2022/5/11 - 23:08
 */
public class StringTest {

    private static final MyLogger logger = MyLoggerFactory.getLogger(UserMapperTest.class);
    @Test
    public void subStringBeforeTest(){
        String allUrl = "http://www.baidu.com/**/asdasd";
        System.out.println(StringUtils.substringBefore(allUrl, "**"));
    }

    @Test
    public void aesTest(){

        String encrypt = AesUtil.encrypt("HelloWorld", "dylan");
        logger.info(encrypt);

        String decrypt = AesUtil.decrypt(encrypt, "dylan");
        logger.info(decrypt);

    }

}
