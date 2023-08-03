package com.dylan.framework.utils;


import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

/**
 * @author Dylan
 * @Date : Created in 15:50 2020/8/21
 * @Description : 文件相关操作方法
 * @Function :
 */
public class FileUtils {

    public static File multi2File(MultipartFile multi) throws IOException {

        File f = null;
        if (Objects.isNull(multi) || StringUtils.isBlank(multi.getOriginalFilename()) || multi.getSize() <= 0) {
            multi = null;
            return null;
        } else {
            InputStream ins = multi.getInputStream();
            f = new File(multi.getOriginalFilename());
            inputStreamToFile(ins, f);
            return f;
        }
    }

    public static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
