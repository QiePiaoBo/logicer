package com.dylan.framework.logconverter;

import ch.qos.logback.classic.pattern.MessageConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.slf4j.helpers.MessageFormatter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Dylan
 * @Description LogJsonConverter
 * @Date : 2022/12/17 - 22:53
 */
public class LogJsonConverter extends MessageConverter {

    /*
    * 妈的真坑啊，logback 生成json格式的文件 存储在服务器的固定地址 然后由logstash对日志文件进行读取并发送到ES
    *   json格式 一条日志记录的内容是作为一个字段存在json对象中的一个属性 （ "strmsg" : "%strmsg" ）
    *       然而spring cloud alibaba 的nacos 在启动时会输出一些日志 这些日志中竟然包含了双引号，这就导致logstash在读取日志并解析json的时候出现异常
    *       并最终导致ES上的日志记录没有正常解析，以一堆字符串的形式出现
    *   解决方案:
    *       在将日志以json格式输出到文件中时，strmsg字段的取值从这里进行获取，由convert方法对一条日志进行加工，
    *     最终目的是使打印到日志文件中的strmsg字段不会影响到整个json的正常解析
    *       PS: 以我的了解，slf4j的日志包括两部分 原文和变量 (log.info("原文{}",变量))，这里的convert触发的时机是两部分合成最终字符串之前
    *       主要进行两个动作:
    *           1. 对日志的原文进行双引号转义 即: 将 " 修改为 \" 以避免影响整个json的解析
    *           2. 对日志的各个变量进行双引号转义 即: 将 " 修改为 \" 以避免影响整个json的解析
    * */

    @Override
    public String convert(ILoggingEvent event) {
        try {
            if (event.getLoggerName().contains("org.springframework.boot.diagnostics.LoggingFailureAnalysisReporter")) {
                String loggerName = event.getLoggerName();
            }
            // 将日志 的参数toString的结果 中的双引号改为 \"
            Object[] argumentArray = null;
            if (Objects.nonNull(event.getArgumentArray()) && event.getArgumentArray().length > 0) {
                argumentArray = Stream.of(event.getArgumentArray()).map(m -> {
                    // 所有的对象最终都要toString再写入日志 这里提前将其toString 放入参数列表中
                    String temp = m.toString();
                    String strArgument;
                    strArgument = temp.replaceAll("\"", "\\\\\"").replaceAll("\n", " ").replaceAll("\r", " ");
                    return strArgument;
                }).toArray();
            }
            // 将日志 的原文 中的双引号改为 \"
            String message = event.getMessage();
            String replacedVar = message;
            if (message.contains("\"")){
                replacedVar = message.replaceAll("\"", "\\\\\"");
            }
            if (message.contains("\r") || message.contains("\n")){
                replacedVar = replacedVar.replaceAll("\n", " ").replaceAll("\r", " ");
            }
            String res = MessageFormatter.arrayFormat(replacedVar, Objects.isNull(argumentArray) ? event.getArgumentArray() : argumentArray).getMessage();
            return res;
        }catch (Exception e){
            return e.getMessage();
        }
    }
}
