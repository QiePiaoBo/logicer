package com.dylan.licence.common;

import com.dylan.framework.utils.AesUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * @author Dylan
 * @Description StringTest
 * @Date : 2022/5/11 - 23:08
 */
@Slf4j
public class StringTest {

    @Test
    public void subStringBeforeTest(){
        String allUrl = "http://www.baidu.com/**/asdasd";
        System.out.println(StringUtils.substringBefore(allUrl, "**"));
    }

    @Test
    public void aesTest(){

        String encrypt = AesUtil.encrypt("HelloWorld", "dylan");
        log.info(encrypt);

        String decrypt = AesUtil.decrypt(encrypt, "dylan");
        log.info(decrypt);

    }

}
