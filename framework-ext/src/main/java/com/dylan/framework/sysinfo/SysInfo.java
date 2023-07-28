package com.dylan.framework.sysinfo;

/**
 * 系统信息类
 * @author Dylan
 */
public class SysInfo {
    /**
     * 公共类，判断当前运行平台是否是windows
     * @return
     */
    public static boolean isWindows() {
        return System.getProperties().getProperty("os.name").toUpperCase().contains("WINDOWS");
    }
}
