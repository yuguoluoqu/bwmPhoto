package functions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ScreenShot {

    public static void screenShot(String src) throws IOException {
        //1.get the file list
        File file = new File(src);
        if (file.isFile())
            return;
        FileFilter filter = new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.isFile() && (pathname.getName().endsWith(".heic") || pathname.getName().endsWith(".png") || pathname.getName().endsWith(".jpg") || pathname.getName().endsWith(".jpeg"));
            }
        };
        File[] files = file.listFiles(filter);
        //2.if screenshots directory is not exist, mkdir
        while (!new File(src + "/screenshot").exists()) {
            new File(src + "/screenshot").mkdir();
        }

        for (File pic : files) {
            BufferedImage sourceImg = ImageIO.read(new FileInputStream(pic.getPath()));
//            System.out.println(String.format("%.1f",picture.length()/1024.0));// 源图大小
            //3.recognize the screenshots
            if (recognize(sourceImg.getWidth(), sourceImg.getHeight())) {
                //4.rename the files to the directory
                pic.renameTo(new File(src + "/screenshot" + pic.getName()));
            }


        }


    }

//    {828+1792,750+1334};

    private static boolean recognize(int width, int height) {

        int size = width + height;
        if (size == 828 + 1792 || size == 750 + 1334)
            return true;
        return false;
    }
}
