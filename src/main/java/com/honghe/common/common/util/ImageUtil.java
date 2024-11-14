package com.honghe.common.common.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.honghe.common.common.result.ImgUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageUtil {
    // 宽度
    private static final int WIDTH = 500;
    // 高度
    private static final int HEIGHT = 300;
    // 双字节字符字体大小 32
    private static final int DOUBLE_FONT_SIZE_32 = 32;
    // 双字节字符字体大小 32
    private static final int DOUBLE_FONT_SIZE_18 = 18;
    // 双字节字符字体大小
    private static final int DOUBLE_FONT_SIZE = 16;
    // 单字节字符字体大小
    private static final int SINGLE_FONT_SIZE = 8;

    // x轴初始位置
    private static final int X_POSITION = 32;

    // 右侧图片宽度
    private static final int RIGHT_WIDTH = 150;

    // 行间距
    private static final int LINE_HEIGHT = 40;
    // 水印透明度
    private static float alpha = 1.0f;
    // 水印横向位置
    private static int positionWidth = 53;
    // 水印纵向位置
    private static int positionHeight = 317;
    // 水印文字字体
    private static Font font = new Font("宋体", Font.BOLD, 115);
    // 水印文字颜色
    private static Color color = new Color(230, 230, 230);

    // 微软雅黑
    private static final String FONT_Microsoft = "Microsoft YaHei";
    /**
     * 生成图片
     * @throws IOException
     */
    public static void createImage(String[] strList, int[] sizes, String basePath, String targetPath) throws IOException {
        OutputStream os = null;
        // 1.创建空白图片
        BufferedImage image = new BufferedImage(
                WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        // 2.获取图片画笔
        Graphics graphic = image.getGraphics();
        // 3.设置画笔颜色
        graphic.setColor(Color.white);
        // 4.绘制矩形背景
        graphic.fillRect(0, 0, WIDTH, HEIGHT);
        // 5.绘制矩形边框
        graphic.setColor(Color.lightGray);
        graphic.drawRect(0, 0, WIDTH-1, HEIGHT-1);

        /**
         * strList：字符串数组，里面存储的是需要显示的文字，每个元素换行显示
         * sizes：长度数组，对应上面的字符串数组，对于每个字符串，记录标题长度，需要加粗显示
         */
        int height = 0;//记录文字行数
        int sumLength = 0;
        String regex = "[^\\x00-\\xff]";//匹配双字节字符
        Matcher matcher = null;
        Pattern pattern = Pattern.compile(regex);
        boolean rs = false;//记录当前字符的匹配情况
        boolean flag = false;//记录前一字符的匹配情况
        for(int j = 0; j<strList.length; j++){
            height = height + 1;
            sumLength = 0;//记录当前字符的横坐标位置
            String stroutput = strList[j];//每个字符串元素
            int size = stroutput.length();//每个字符串数组元素的长度

            for(int num = 0; num < size; num ++){
                String schar = stroutput.charAt(num) + "";
                matcher = pattern.matcher(schar);
                // 字符串是否与正则表达式相匹配
                rs = matcher.matches();
                if(num < sizes[j]) {    //标题显示格式
                    // 设置随机颜色
                    graphic.setColor(Color.black);
                    // 设置字体大小，标题需要加粗显示
                    graphic.setFont(new Font(null, Font.BOLD, DOUBLE_FONT_SIZE));
                }else {                 //正文显示格式
                    // 设置随机颜色
                    graphic.setColor(Color.darkGray);
                    // 设置字体大小，正文普通字体显示
                    graphic.setFont(new Font(null, Font.PLAIN, DOUBLE_FONT_SIZE));
                }
                /**
                 * 单字节字符，正常情况下应该是占用8个位置的，但是如果前面1个字符是双字节字符的话，
                 * 如果加8的话，会和前面的字符重叠，因为字符本身是要占用空间的，sumLength只是横坐标的位置，
                 * 而并没有考虑占位的问题，所以，如果前面是双字节字符的话，横坐标应该加15；
                 * 前面如果是单字节字符，加8就可以了。
                 */
                if(rs){
                    //双字节字符
                    sumLength = sumLength + DOUBLE_FONT_SIZE;
                }else{
                    //单字节字符
                    if(flag){
                        //前一个字符是双字节字符
                        sumLength = sumLength + DOUBLE_FONT_SIZE;
                    }else{
                        sumLength = sumLength + SINGLE_FONT_SIZE;
                    }
                }
                flag = rs;
                if(sumLength > WIDTH - DOUBLE_FONT_SIZE*2){
                    //每一行的前后都要留有一定空白，横坐标已经超出长度，需要折行
                    height = height + 1;
                    if(rs){ //前面是双字节字符，横坐标设置为15
                        sumLength = DOUBLE_FONT_SIZE;
                    }else{  //前面是单字节字符，横坐标设置为8
                        sumLength = SINGLE_FONT_SIZE;
                    }
                }
                // 画字符
                graphic.drawString(schar, sumLength, height*LINE_HEIGHT);
            }
        }
        // 6.返回图片
        os = new FileOutputStream(targetPath);
        ImageIO.write(image, "JPG", os);
        os.close();

        System.out.println("返回图片。。。。。。");
    }

    //添加文字水印
    public static void markImageByText(String logoText, String srcImgPath,
                                       String targerPath, Integer degree) {

        InputStream is = null;
        OutputStream os = null;
        try {
            // 1、源图片
            Image srcImg = ImageIO.read(new File(srcImgPath));
            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);

            // 2、得到画笔对象
            Graphics2D g = buffImg.createGraphics();
            // 3、设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);
            // 4、设置水印旋转
            if (null != degree) {
                g.rotate(Math.toRadians(degree),(double) buffImg.getWidth() / 2, (double) buffImg.getHeight() / 2);
            }
            // 5、设置水印文字颜色
            g.setColor(color);
            // 6、设置水印文字Font
            g.setFont(font);
            // 7、设置水印文字透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha));
            // 8、第一参数->设置的内容，后面两个参数->文字在图片上的坐标位置(x,y)
            g.drawString(logoText, positionWidth, positionHeight);
            // 9、释放资源
            g.dispose();
            // 10、生成图片
            os = new FileOutputStream(targerPath);
            ImageIO.write(buffImg, "JPG", os);

            System.out.println("图片完成添加水印文字");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != is)
                    is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (null != os)
                    os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //添加图片水印
    public static void markImageByIcon(String iconPath, String srcImgPath,
                                       String targerPath, Integer degree) {
        OutputStream os = null;
        try {
            Image srcImg = ImageIO.read(new File(srcImgPath));

            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),
                    srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);

            // 得到画笔对象
            Graphics2D g = buffImg.createGraphics();

            // 设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg
                    .getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);

            if (null != degree) {
                // 设置水印旋转
                g.rotate(Math.toRadians(degree),
                        (double) buffImg.getWidth() / 2, (double) buffImg
                                .getHeight() / 2);
            }

            // 水印图象的路径 水印一般为gif或者png的，这样可设置透明度
            ImageIcon imgIcon = new ImageIcon(iconPath);

            // 得到Image对象。
            Image img = imgIcon.getImage();


            //float alpha = 0.1f; // 透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    alpha));

            // 表示水印图片的位置
            g.drawImage(img, positionWidth-1, positionHeight-1, null);

            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

            g.dispose();

            os = new FileOutputStream(targerPath);

            // 生成图片
            ImageIO.write(buffImg, "JPG", os);

            System.out.println("图片完成添加Icon印章。。。。。。");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 生成图片
     * @throws IOException
     */
    public static void createImageCard(String[] strList, int[] sizes, String QRContent, String QRLogoPath, String targetPath) throws IOException {
        OutputStream os = null;
        // 1.创建空白图片
        BufferedImage image = new BufferedImage(
                WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        // 2.获取图片画笔
        Graphics graphic = image.getGraphics();
        // 3.设置画笔颜色
        graphic.setColor(Color.white);
        // 4.绘制矩形背景
        graphic.fillRect(0, 0, WIDTH, HEIGHT);
        // 5.绘制矩形边框
        graphic.setColor(Color.lightGray);
        graphic.drawRect(0, 0, WIDTH-1, HEIGHT-1);

        /**
         * strList：字符串数组，里面存储的是需要显示的文字，每个元素换行显示
         * sizes：长度数组，对应上面的字符串数组，对于每个字符串，记录标题长度，需要加粗显示
         */
        // 1
        // 设置随机颜色
        graphic.setColor(Color.black);
        // 设置字体大小，标题需要加粗显示
        graphic.setFont(new Font(FONT_Microsoft, Font.BOLD, DOUBLE_FONT_SIZE_32));
        // 画字符
        graphic.drawString(strList[0], X_POSITION, sizes[0]*LINE_HEIGHT);

        // 2
        // 设置随机颜色
        graphic.setColor(Color.GRAY);
        // 设置字体大小，标题需要加粗显示
        graphic.setFont(new Font(FONT_Microsoft, Font.BOLD, DOUBLE_FONT_SIZE_18));
        // 画字符
        graphic.drawString(strList[1], X_POSITION, sizes[1]*LINE_HEIGHT);

        // 3
        // 设置随机颜色
        graphic.setColor(Color.GRAY);
        // 设置字体大小，标题需要加粗显示
        graphic.setFont(new Font(FONT_Microsoft, Font.BOLD, DOUBLE_FONT_SIZE));
        // 画字符
        graphic.drawString(strList[2], X_POSITION, sizes[2]*LINE_HEIGHT);

        // 4
        // 设置随机颜色
        graphic.setColor(Color.black);
        // 设置字体大小，标题需要加粗显示
        graphic.setFont(new Font(FONT_Microsoft, Font.BOLD, DOUBLE_FONT_SIZE_18));
        // 画字符
        graphic.drawString(strList[3], X_POSITION, sizes[3]*LINE_HEIGHT);

        // 5
        // 设置随机颜色
        graphic.setColor(Color.red);
        // 设置字体大小，标题需要加粗显示
        graphic.setFont(new Font(FONT_Microsoft, Font.BOLD, DOUBLE_FONT_SIZE_32));
        // 画字符
        graphic.drawString(strList[4], WIDTH-RIGHT_WIDTH, sizes[4]*LINE_HEIGHT+10);

        // 生成二维码
        Graphics2D rng = image.createGraphics();
        try {
            int width = 150; // 图像宽度
            int height = 150; // 图像高度

            Map<EncodeHintType, Object> hints = new HashMap<>();

            // 内容编码格式
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            // 指定纠错等级
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            // 设置二维码边的空度，非负数
            hints.put(EncodeHintType.MARGIN, 1);
            BitMatrix bitMatrix = new MultiFormatWriter().encode(QRContent, BarcodeFormat.QR_CODE, width, height, hints);

            // 生成带Logo的二维码
            BufferedImage img = null;
            if (null != QRLogoPath && !"".equals(QRLogoPath)){
                File imageFile = new File(QRLogoPath);
                if (null != imageFile && imageFile.exists()){
                    // 二维码上添加Logo
                    MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(0xFF000001, 0xFFFFFFFF);
                    img = ImgUtil.LogoMatrix(MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig), imageFile);
                }
            }
            // 若未生成二维码，则生成普通二维码
            if (null == img){
                img = MatrixToImageWriter.toBufferedImage(bitMatrix);
            }

            //距离背景图片x边的距离，居中显示
            int disx=330;
            //距离y边距离 * * * *
            int disy=110;
//            Graphics2D rng = image.createGraphics();
            rng.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP));
            rng.drawImage(img,disx,disy,150,150,null);
            rng.dispose();
            image.flush();
            // 6.返回图片
            os = new FileOutputStream(targetPath);
            ImageIO.write(image, "JPG", os);
            os.close();
        } catch (WriterException e) {
            e.printStackTrace();
        }finally {
            // 关闭画布
            rng.dispose();
        }



        System.out.println("返回图片。。。。。。");
    }

    public static void main(String[] args) {
        String[] contents = new String[10];
        contents[0] = "武笑";
        contents[1] = "鸿合科技";
        contents[2] = "有效时间";
        contents[3] = "2020/05/01 08:30-20:30";
        contents[4] = "通行证";
        int[] sizes = new int[24];
        sizes[0] = 2;
        sizes[1] = 3;
        sizes[2] = 5;
        sizes[3] = 6;
        sizes[4] = 1;
        try {
            createImageCard(contents, sizes,"https://www.baidu.com",
                    "",
                    "C:/Users/Administrator/Desktop/test/createImage.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }

//        markImageByText("测试水印","C:/Users/Administrator/Desktop/test/createImage.jpg","C:/Users/Administrator/Desktop/test/createImage_text.jpg",90);

//        markImageByIcon("C:/Users/Administrator/Desktop/test/test.jpg",
//                "C:/Users/Administrator/Desktop/test/createImage.jpg", "C:/Users/Administrator/Desktop/test/createImage.jpg", 0);
    }

}