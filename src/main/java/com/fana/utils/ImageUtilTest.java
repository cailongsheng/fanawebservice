package com.fana.utils;



import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


@Component
public class ImageUtilTest {

     /**
     *
     * @param originalFile  原文件
     * @param resizedFile  压缩目标文件
     * @param newWidth  压缩后的图片宽度
     * @param quality  压缩质量（0到1之间，越高质量越好）
     * @throws IOException
     */

    public static Boolean resize(File originalFile, File resizedFile,
            int newWidth, int newHight,float quality) throws IOException {


            if (quality > 1) {
                throw new IllegalArgumentException(
                        "Quality has to be between 0 and 1");
            }

            //获取图片参数
            ImageIcon ii = null;
            ii = new ImageIcon(originalFile.getCanonicalPath());

            Image i = ii.getImage();
            Image resizedImage = null;

            //获取原图片大小
            int iWidth = i.getWidth(null);
            int iHeight = i.getHeight(null);

            if (iWidth == -1) {
                return false;
            }

    try {
            //比如 新尺寸大于原尺寸
            if(iWidth < newWidth && iHeight < newHight){
                resizedImage = i.getScaledInstance(iWidth,iHeight,Image.SCALE_SMOOTH);
            } else if (iWidth > iHeight) {
                resizedImage = i.getScaledInstance((int)Math.round(iWidth/(iWidth*1.00/newWidth*1.00)), (int)Math.round(iHeight / (iWidth*1.00 / newWidth*1.00)), Image.SCALE_SMOOTH);
            } else {
                resizedImage = i.getScaledInstance((int)Math.round(iWidth / (iHeight*1.00 / newHight*1.00)), (int)Math.round(iHeight / (iHeight*1.00 / newHight*1.00)), Image.SCALE_SMOOTH);
            }

            Image temp = new ImageIcon(resizedImage).getImage();

            BufferedImage bufferedImage = new BufferedImage(newWidth,
                    newHight, BufferedImage.TYPE_INT_RGB);

            // Copy image to buffered image.
            Graphics g = bufferedImage.createGraphics();
            g.setColor(Color.white);

            // Clear background and paint the image.
            g.fillRect(0, 0, newWidth, newHight);

            g.drawImage(temp, (bufferedImage.getWidth() - temp.getWidth(null)) / 2, (bufferedImage.getHeight() - temp.getHeight(null))/ 2,null);

            g.dispose();


//
//        FileOutputStream fileOutputStream = new FileOutputStream(resizedFile.getCanonicalPath());
//        JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(fileOutputStream);
//        JPEGEncodeParam defaultJPEGEncodeParam = jpegEncoder.getDefaultJPEGEncodeParam(bufferedImage);
//        defaultJPEGEncodeParam.setQuality(quality,true);
//        jpegEncoder.encode(bufferedImage,defaultJPEGEncodeParam);
//        fileOutputStream.close();


            float softenFactor = 0.05f;
            float[] softenArray = {0, softenFactor, 0, softenFactor,
                    1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0};
            Kernel kernel = new Kernel(3, 3, softenArray);
            ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
            bufferedImage = cOp.filter(bufferedImage, null);

            // Write the jpeg to a file.
            FileOutputStream out = null;

                out = new FileOutputStream(resizedFile);

                ImageIO.write(bufferedImage, "png", out);
    } catch (Exception e) {
                e.printStackTrace();
    }

        return true;
    }

//    private static String getImages(,String imageUrl,int wight,int hight){
//
//    }



    private static String getFileName(String filename){
        int i = 0;
        char[] chars = filename.toCharArray();
        for (char aChar : chars) {
            if(aChar == '0'){
                i++;
            }else{
                break;
            }
        }

       return filename.substring(i);
    }
}