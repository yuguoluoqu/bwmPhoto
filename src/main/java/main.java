import functions.ClearDuplicate;
import functions.ScreenShot;

import java.io.IOException;

public class main {

    /**
     * @param args
     * @auther zhc
     * 操作 原始路径  [目标路径]    java main srcdirectory [dstdirectory]
     * 1.去重
     * 2.识别截图
     * 3.识别小图
     * 4.按日期重命名
     * 5.按年分文件夹
     * 6.按月分文件夹
     */
    public static void main(String[] args) throws IOException {
        String opt = "0";
        String src = "";
        String dst = "";
        if (args.length < 2)
            return;
        opt = args[0];
        src = args[1];
        if (args.length > 2)
            dst = args[2];

        if ("1".equals(opt)) {
            ClearDuplicate.clearDuplicate(src);
        } else if ("2".equals(opt)) {
            ScreenShot.screenShot(src);
        } else if ("3".equals(opt)) {//...;
        } else if ("4".equals(opt)) {//...;
        } else if ("5".equals(opt)) {//...;
        } else if ("6".equals(opt)) {//...;
        }

    }
}
