package com.dylan.licence.controller;

import com.dylan.blog.service.ArticleService;
import com.dylan.blog.vo.ArticleVO;
import com.dylan.framework.model.result.DataResult;
import com.dylan.framework.model.result.HttpResult;
//import com.dylan.licence.config.HomeDataLoader;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @Classname LogicerController
 * @Description LogicerController
 * @Date 8/28/2023 6:33 PM
 */
@RestController
@RequestMapping("logicer")
public class LogicerController {

//    @Resource
//    private HomeDataLoader homeDataLoader;

    @DubboReference(version = "1.0.0")
    private ArticleService articleService;

    /**
     * 分页获取用户组
     * @return
     */
//    @GetMapping("get-home-data")
//    public HttpResult getPagedGroup() {
//        Map<String, Object> homeDataMap = new HashMap<>();
//        List<ArticleVO> articleVOS = articleService.getArticleList();
//        String defaultTitle = "LOGICER";
//        homeDataMap.put("title", Objects.isNull(homeDataLoader.getTitle()) ? defaultTitle : homeDataLoader.getTitle());
//        homeDataMap.put("articles", articleVOS);
//        return DataResult.success().data(homeDataMap).build();
//    }

    /**
     * 根据入参获取对应长度和对应最大值的数组并以字符串的形式返回
     * @param size
     * @param max
     * @return
     */
    @GetMapping("get-nums")
    public HttpResult getNums(
            @RequestParam(name = "size", defaultValue = "7") int size,
            @RequestParam(name = "max", defaultValue = "33") int max) {
        StringBuilder strBd = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            int res = random.nextInt(max) + 1;
            strBd.append(res);
            if (!(i == size - 1)){
                strBd.append(" ");
            }
        }
        return DataResult.success().data(strBd).build();
    }

    @GetMapping("word-encode")
    public HttpResult wordEncode(
            @RequestParam(name = "word", defaultValue = "你好") String word
    ){
        String binaryStr = convertToBinaryAndToBase64(word);
        return DataResult.success().data(binaryStr).build();
    }


    @GetMapping("word-decode")
    public HttpResult wordDecode(
            @RequestParam(name = "word", defaultValue = "你好") String word
    ){
        String binaryStr = convertToOriginal(word);
        return DataResult.success().data(binaryStr).build();
    }

    /**
     * 将字符串转化为二进制字符串 使用空格分隔每个字符串
     * @param input
     * @return
     */
    public static String convertToBinaryAndToBase64(String input) {
        StringBuilder binaryStringBuilder = new StringBuilder();

        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
        for (byte b : bytes) {
            // 获取字节的二进制字符串
            String binaryRepresentation = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');

            // 将二进制字符串追加到结果中
            binaryStringBuilder.append(binaryRepresentation).append(" ");
        }

        // 删除最后一个空格
        if (binaryStringBuilder.length() > 0) {
            binaryStringBuilder.deleteCharAt(binaryStringBuilder.length() - 1);
        }

        return binaryToBase64(binaryStringBuilder.toString());
    }

    /**
     * 将二进制字符串翻译回字符串
     * @param base64Str
     * @return
     */
    public static String convertToOriginal(String base64Str) {
        String originalBinaryStr = base64ToBinary(base64Str);
        if (!originalBinaryStr.contains(" ")){
            originalBinaryStr = insertSpace(originalBinaryStr, 8);
        }
        // 将二进制字符串分割成每个字节的二进制表示
        String[] binaryArray = originalBinaryStr.split(" ");

        byte[] bytes = new byte[binaryArray.length];
        for (int i = 0; i < binaryArray.length; i++) {
            // 将二进制字符串转换为字节
            int byteValue = Integer.parseInt(binaryArray[i], 2);
            bytes[i] = (byte) byteValue;
        }

        return new String(bytes, StandardCharsets.UTF_8);
    }
    public static String insertSpace(String binaryString, int interval) {
        StringBuilder spacedBinaryStringBuilder = new StringBuilder();

        for (int i = 0; i < binaryString.length(); i++) {
            spacedBinaryStringBuilder.append(binaryString.charAt(i));

            if ((i + 1) % interval == 0 && (i + 1) < binaryString.length()) {
                spacedBinaryStringBuilder.append(" ");
            }
        }

        return spacedBinaryStringBuilder.toString();
    }

    /**
     * 将一个二进制字符串转化为Base64
     * @param binaryString
     * @return
     */
    public static String binaryToBase64(String binaryString) {
        // 去除二进制字符串中的空格
        String cleanBinaryString = binaryString.replace(" ", "");

        // 将二进制字符串转换为字节数组
        byte[] byteArray = new byte[cleanBinaryString.length() / 8];
        for (int i = 0; i < byteArray.length; i++) {
            String byteString = cleanBinaryString.substring(i * 8, (i + 1) * 8);
            byteArray[i] = (byte) Integer.parseInt(byteString, 2);
        }

        // 使用 Base64 编码字节数组
        byte[] base64Bytes = Base64.getEncoder().encode(byteArray);

        // 将 Base64 编码结果转换为字符串
        return new String(base64Bytes, StandardCharsets.UTF_8);
    }

    /**
     * 将一个base64字符串还原
     * @param base64String
     * @return
     */
    public static String base64ToBinary(String base64String) {
        // 使用 Base64 解码得到字节数组
        byte[] base64Bytes = Base64.getDecoder().decode(base64String);

        // 将字节数组转换为二进制字符串
        StringBuilder binaryStringBuilder = new StringBuilder();
        for (byte b : base64Bytes) {
            binaryStringBuilder.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
        }

        return binaryStringBuilder.toString();
    }
}
