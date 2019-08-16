package com.sxzhongf.ad;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class TestTable {
    BufferedImage image;

    void createImage(String fileLocation) {
        try {
            FileOutputStream fos = new FileOutputStream(fileLocation);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
            encoder.encode(image);
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void graphicsGeneration() {
        //实际数据行数+标题+备注
        int totalrow = 10; //todo: 动态获取
        int totalcol = 8; //todo: 动态获取
        int imageWidth = 1000; //todo: 动态计算
        int imageHeight = totalrow * 40 + 20;
        int rowheight = 40;
        int startHeight = 10;
        int startWidth = 10;
        int colwidth = (int) ((imageWidth - 20) / totalcol);

        image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();

        graphics.setColor(new Color(250, 250, 250));
        graphics.fillRect(0, 0, imageWidth, imageHeight);

//        //设置字体
        Font font = new Font("PingFangSC-Thin", Font.BOLD, 16);

        graphics.setColor(new Color(51, 51, 51));
        graphics.setFont(font);
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_DEFAULT);

        //写入表头
        String[] headCells = {"尺码", "XXS", "XS", "S", "M", "L", "XL", "XXL"};
        for (int m = 0; m < headCells.length; m++) {
            graphics.drawString(headCells[m].toString(), startWidth + colwidth * m + 5, startHeight + rowheight * 2 - 10);
        }

        //设置字体
        font = new Font("PingFangSC-Thin", Font.PLAIN, 16);
        graphics.setFont(font);
        String[][] cellsValue = {
                {"肩宽", "70", "-", "-", "-", "-", "-", "-"},
                {"胸围", "-", "-", "80", "-", "-", "-", "-"},
                {"腰围", "-", "74", "-", "-", "-", "-", "-"},
                {"臀围", "-", "-", "90", "-", "-", "-", "-"},
                {"臀围", "-", "-", "90", "-", "-", "-", "-"},
                {"臀围", "-", "-", "90", "-", "-", "-", "-"},
                {"臀围", "-", "-", "90", "-", "-", "-", "-"},
                {"臀围", "-", "-", "90", "-", "-", "-", "-"},
                {"裙长", "-", "-", "-", "-", "-", "80", "-"}
        };
        //写入内容
        for (int n = 0; n < cellsValue.length; n++) {
            String[] arr = cellsValue[n];
            for (int l = 0; l < arr.length; l++) {
                graphics.drawString(cellsValue[n][l].toString(), startWidth + colwidth * l + 5, startHeight + rowheight * (n + 3) - 10);
            }
        }

        createImage("1.jpg");
    }

    public static void main(String[] args) {
        TestTable cg = new TestTable();
        try {
            cg.graphicsGeneration();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}