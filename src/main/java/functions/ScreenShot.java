package functions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

        for (int i = 0; i < files.length; i++) {
            File pic = files[i];
            BufferedImage sourceImg = ImageIO.read(new FileInputStream(pic.getPath()));
//            System.out.println(String.format("%.1f",picture.length()/1024.0));// 源图大小
            //3.recognize the screenshots
            if (recognize(sourceImg.getWidth(), sourceImg.getHeight())) {
                //4.rename the files to the directory
                pic.renameTo(new File(src + "/screenshot" + getCreateTime(pic.getPath() + pic.getName()) + pic.getName()));
            }


        }


    }

    static SimpleDateFormat sdf = new SimpleDateFormat("YYYYmmDD");

    private static String getCreateTime(String fullFileName) {
        Path path = Paths.get(fullFileName);
        BasicFileAttributeView basicview = Files.getFileAttributeView(path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
        BasicFileAttributes attr;
        try {
            attr = basicview.readAttributes();
            Date createDate = new Date(attr.creationTime().toMillis());
            return sdf.format(createDate);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return "";
    }

//    {828+1792,750+1334};

    private static boolean recognize(int width, int height) {

        int size = width + height;
        if (size == 828 + 1792 || size == 750 + 1334)
            return true;
        return false;
    }
}
