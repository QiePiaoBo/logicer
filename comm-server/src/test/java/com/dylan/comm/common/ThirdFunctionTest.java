package com.dylan.comm.common;

import com.dylan.logicer.base.logicer.LogicerContent;
import com.dylan.logicer.base.logicer.LogicerHeader;
import com.dylan.logicer.base.logicer.LogicerMessage;
import com.dylan.logicer.base.logicer.LogicerSubProtocol;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

/**
 * @author Dylan
 * @Date : 2022/4/4 - 17:44
 * @Description : 第三方依赖功能检测
 * @Function :
 */
public class ThirdFunctionTest {

    @Test
    public void jacksonTest() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        LogicerHeader logicerHeader = new LogicerHeader(LogicerSubProtocol.LOGICER, 1, 10, "asdasdas");
        LogicerContent logicerContent = new LogicerContent("HELLO", "WORLD");
        LogicerMessage beforeMessage = new LogicerMessage(logicerHeader, logicerContent);
        String jsonString = objectMapper.writeValueAsString(beforeMessage);

        LogicerMessage afterMessage = objectMapper.readValue(jsonString, LogicerMessage.class);

        System.out.println(afterMessage.toString());
    }


}
