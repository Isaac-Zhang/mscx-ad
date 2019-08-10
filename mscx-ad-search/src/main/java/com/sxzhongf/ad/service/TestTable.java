package com.sxzhongf.ad.service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.List;

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
        int imageWidth = 500; //todo: 动态计算
        int imageHeight = totalrow * 40 + 20;
        int rowheight = 40;
        int startHeight = 10;
        int startWidth = 10;
        int colwidth = (int) ((imageWidth - 20) / totalcol);

        image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();

        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, imageWidth, imageHeight);
        graphics.setColor(new Color(221, 221, 221));

        int startH = 1;

        graphics.fillRect(startWidth + 1, startHeight + startH * rowheight + 1, imageWidth - startWidth - 5 - 1, rowheight - 1);


        //画横线
//        for (int j = 0; j < totalrow - 1; j++) {
        for (int j = 0; j < totalrow; j++) {
            graphics.setColor(Color.black);
            graphics.drawLine(startWidth, startHeight + (j + 1) * rowheight, imageWidth - 5, startHeight + (j + 1) * rowheight);
        }
        //末行
//        graphics.setColor(Color.black);
//        graphics.drawLine(startWidth, imageHeight - 90, imageWidth - 5, imageHeight - 90);


        //画竖线
//        for (int k = 0; k < totalcol; k++) {
        for (int k = 0; k < totalcol; k++) {
            graphics.setColor(Color.black);
            graphics.drawLine(startWidth + k * colwidth, startHeight + rowheight, startWidth + k * colwidth, imageHeight - 10);
        }
//        //末列
//        graphics.setColor(Color.black);
//        graphics.drawLine(imageWidth - 5, startHeight + rowheight, imageWidth - 5, imageHeight - 50);

        //设置字体
        Font font = new Font("宋体", Font.BOLD, 20);
        graphics.setFont(font);
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        //写标题
        String title = "蓝色层次设计半身裙";
        graphics.drawString(title, imageWidth / 3 + startWidth, startHeight + rowheight - 10);

        font = new Font("宋体", Font.BOLD, 20);
        graphics.setFont(font);

        //写入表头
        String[] headCells = {"尺码", "XXS", "XS", "S", "M", "L", "XL", "XXL"};
        for (int m = 0; m < headCells.length; m++) {
            graphics.drawString(headCells[m].toString(), startWidth + colwidth * m + 5, startHeight + rowheight * 2 - 10);
        }

        //设置字体
        font = new Font("宋体", Font.PLAIN, 20);
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

//        font = new Font("华文楷体", Font.BOLD, 18);
//        graphics.setFont(font);
//        graphics.setColor(Color.RED);

        //写备注
//        String remark = "备注：备注写在这里。";
//        graphics.drawString(remark, startWidth, imageHeight - 30);

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