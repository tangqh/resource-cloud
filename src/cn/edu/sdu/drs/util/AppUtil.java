package cn.edu.sdu.drs.util;

/**
 * 
 * @author Yonggang Yuan
 *
 */

public class AppUtil {

    public static String getPath() {
        String path = System.getProperty("catalina.home");
        int binindex = path.lastIndexOf("bin");
        if (binindex != -1) {
            path = path.substring(0, binindex - 1);
        }
        path = path + "\\" + "webapps";
        return path;
    }
}
