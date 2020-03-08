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
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

public class ScreenShot {


    public static void screenShot(String src) throws IOException {
        //1.get the file list
        File file = new File(src);
        if (file.isFile())
            return;
        FileFilter filter = new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.isFile() && (pathname.getName().endsWith(".png") || pathname.getName().endsWith(".PNG"));
            }
        };
        File[] files = file.listFiles(filter);
        //2.if screenshots directory is not exist, mkdir
        while (!new File(src + "/screenshot").exists()) {
            new File(src + "/screenshot").mkdir();
        }

        //move files to a queue
        ArrayBlockingQueue<File> queue = new ArrayBlockingQueue(files.length);
        queue.addAll(Arrays.asList(files));

        for (int i = 0; i < 8; i++) {
            new Thread(() -> {
                System.out.println("thread " + Thread.currentThread() + "");
                File pic = queue.poll();
                while (pic != null) {
                    System.out.println(pic.getName());

                    try {
                        FileInputStream fileStream = new FileInputStream(pic.getPath());
                        BufferedImage  sourceImg = ImageIO.read(fileStream);
                        //System.out.println(String.format("%.1f",picture.length()/1024.0));// 源图大小
                        //3.recognize the screenshots
                        if (recognize(sourceImg.getWidth(), sourceImg.getHeight())) {
                            fileStream.close();
                            //4.rename the files to the directory
                            String dir = src + "\\screenshot\\" + getCreateTime(pic.getPath()) + pic.getName();
                            while (!new File(dir).exists()) {
                                pic.renameTo(new File(dir));
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    pic = queue.poll();
                }
            }, "tread" + i).start();
        }
    }

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    private static String getCreateTime(String fullFileName) {
        Path path = Paths.get(fullFileName);
        BasicFileAttributeView basicview = Files.getFileAttributeView(path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
        BasicFileAttributes attr;
        try {
            attr = basicview.readAttributes();
            Date createDate = new Date(attr.creationTime().toMillis());
            Date updateDate = new Date(attr.lastModifiedTime().toMillis());
            if (createDate.before(updateDate)) {
                return sdf.format(createDate);
            } else {
                return sdf.format(updateDate);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return "";
    }

//    {828+1792,750+1334};

    private static boolean recognize(int width, int height) {

        int size = width + height;
        if (size == 828 + 1792 || size == 750 + 1334||size==640+1136)
            return true;
        return false;
    }
}
