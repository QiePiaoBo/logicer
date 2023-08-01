package com.dylan.comm.common;

import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import com.dylan.logicer.base.logicer.LogicerContent;
import com.dylan.logicer.base.logicer.LogicerHeader;
import com.dylan.logicer.base.logicer.LogicerMessage;
import com.dylan.logicer.base.logicer.LogicerSubProtocol;
import com.dylan.logicer.base.logicer.LogicerTalkWord;
import com.dylan.logicer.base.logicer.LogicerUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

/**
 * @Classname CommonTest
 * @Description TODO
 * @Date 4/28/2022 10:26 AM
 */
public class CommonTest {

    private final MyLogger logger = MyLoggerFactory.getLogger(CommonTest.class);

    @Test
    public void strContainTest() {
        String subStr = " : ";
        System.out.println("asds : asdad".contains(subStr));
        System.out.println("asds :asdad".contains(subStr));

        String testStr = "@asdasdsa";

        logger.info("TestStr : {}", LogicerUtil.isLoginStr(testStr));

    }

    @Test
    public void logicerMessageTransformTest() throws IOException {


        ObjectMapper objectMapper = new ObjectMapper();
        LogicerHeader logicerHeader = new LogicerHeader(LogicerSubProtocol.LOGICER, 1, 10, "asdadadsafasf");
        LogicerTalkWord logicerTalkWord = new LogicerTalkWord();
        logicerTalkWord.setMsg("msg");
        logicerTalkWord.setType("type");
        logicerTalkWord.setFrom("user");
        logicerTalkWord.setTo("group");

        LogicerMessage logicerMessage = new LogicerMessage(logicerHeader, new LogicerContent("actionName", objectMapper.writeValueAsString(logicerTalkWord)));
        byte[] bytes = objectMapper.writeValueAsBytes(logicerMessage);
        LogicerMessage res = objectMapper.readValue(bytes, LogicerMessage.class);
        LogicerContent content = res.getLogicerContent();
        logger.info("Res content is : {}", content);
        String actionWord = content.getActionWord();
        LogicerTalkWord resTalkContent = objectMapper.readValue(actionWord, LogicerTalkWord.class);
        logger.info("Res talkContent is : {}", resTalkContent);
    }
}
