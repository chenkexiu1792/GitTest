package com.honghe.common.common.result;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.swing.*;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 图片处理工具类
 * @author: Wangzy
 * @create: 2018-11-05 15:44
 **/
@Component
public class ImgUtil {

    //支持的图片类型
    public static String[] imageTypes = {"jpeg", "jpg", "png", "bmp"};


    /**
     * image转bufferedImage
     *
     * @param image
     * @return java.awt.image.BufferedImage
     * @Create Wangzy 2018/11/12 10:55
     * @Update Wangzy 2018/11/12 10:55
     */
    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }
        // 加载所有像素
        image = new ImageIcon(image).getImage();
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            int transparency = Transparency.OPAQUE;
            // 创建buffer图像
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(
                    image.getWidth(null), image.getHeight(null), transparency);
        } catch (HeadlessException e) {
            //e.printStackTrace();
        }
        if (bimage == null) {
            int type = BufferedImage.TYPE_INT_RGB;
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        }
        // 复制
        Graphics g = bimage.createGraphics();
        // 赋值
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return bimage;
    }


    /**
     * base64图片保存本地
     */
    public static boolean base64StringSaveAsImageFile(String base64String, String imgFilePath) {
        //对字节数组字符串进行Base64解码并生成图片
        if (base64String == null) //图像数据为空
            return false;
        try {
            //这里的base64Str是含有"data:image/jpeg;base64,"开头的，需要把它去掉否则解析不出来
            byte[] b = DatatypeConverter.parseBase64Binary(base64String.split(",")[1]);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }
            //生成jpeg图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 图片转base64字符串
     *
     * @param imageFilePath 图片路径
     * @return java.lang.String
     * @Create Wangzy 2018/11/1 10:31
     * @Update Wangzy 2018/11/1 10:31
     */
    public static String imageToBase64String(String imageFilePath) {
        File f = new File(imageFilePath);
        try {
            BufferedImage bi = ImageIO.read(f);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "jpg", baos);
            byte[] bytes = baos.toByteArray();
            return new BASE64Encoder().encodeBuffer(bytes).trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 图片流转base64字符串
     *
     * @param bfImage 图片流
     * @return java.lang.String
     * @Create Wangzy 2018/11/1 10:31
     * @Update Wangzy 2018/11/1 10:31
     */
    public static String bufferedImageToBase64String(BufferedImage bfImage) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bfImage, "gif", baos);
            byte[] bytes = baos.toByteArray();
            return new BASE64Encoder().encodeBuffer(bytes).trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * base64图片转成InputStream流对象
     *
     * @param base64string
     * @return java.io.InputStream
     * @Create Wangzy 2018/11/12 9:29
     * @Update Wangzy 2018/11/12 9:29
     */
    public static InputStream baseToInputStream(String base64string) {
        ByteArrayInputStream stream = null;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes1 = decoder.decodeBuffer(base64string);
            stream = new ByteArrayInputStream(bytes1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stream;
    }

    /**
     * base64图片转成BufferedImage流对象
     *
     * @param base64string
     * @return java.awt.image.BufferedImage
     * @Create Wangzy 2018/11/12 9:29
     * @Update Wangzy 2018/11/12 9:29
     */
    public static BufferedImage getBufferedImage(String base64string) {
        BufferedImage image = null;
        try {
            InputStream stream = baseToInputStream(base64string);
            image = ImageIO.read(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * 获取图片的类型（.后面的字符串）
     *
     * @param imageName 文件路径（带文件名）
     * @return java.lang.String
     * @Create Wangzy 2018/11/1 10:55
     * @Update Wangzy 2018/11/1 10:55
     */
    public static String getImageSuffix(String imageName) {
        if (imageName != null && imageName.length() != 0) {
            String suffix = imageName.substring(imageName.lastIndexOf(".") + 1);
            Boolean isImage = false;
            for (String imageType : imageTypes) {
                if (imageType.equals(suffix.toLowerCase())) {
                    isImage = true;
                    break;
                }
            }
            if (isImage) {
                return suffix;
            }
        }
        return null;
    }

    /**
     * 获取图片的类型
     *
     * @param base64String
     * @return java.lang.String
     * @Create Wangzy 2018/11/1 10:55
     * @Update Wangzy 2018/11/1 10:55
     */
    public static String getImageType(String base64String) {
        if (base64String != null && base64String.length() >= 20) {
            String suffix = base64String.substring(0, 19);
            for (String imageType : imageTypes) {
                if (suffix.toLowerCase().contains(imageType)) {
                    return imageType;
                }
            }
        }
        return null;
    }

    public static int getImageSize(String base64ImgString) {
        int length = -1;
        // 截取base64字符串中的data部分
        base64ImgString = base64ImgString.split(";base64,")[1];

        BASE64Decoder decoder = new BASE64Decoder();
        // 解密
        byte[] b = new byte[0];
        try {
            b = decoder.decodeBuffer(base64ImgString);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            length = b.length;
        } catch (IOException e) {
            e.printStackTrace();
        }
//            51919/1024
//            1024 1k * 1024 1m * 4
        return length;
    }

    /*下方为zxing二维码生成及识别部分*/

    /**
     * 生成二维码
     */
    public static String QREncode(String content){
        return QREncode(content,null);
    }

    /**
     * 生成二维码
     */
    public static String QREncode(String content,String imagePath) {
        String result = null;

        try {
            int width = 200; // 图像宽度
            int height = 200; // 图像高度

            Map<EncodeHintType, Object> hints = new HashMap<>();

            // 内容编码格式
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            // 指定纠错等级
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            // 设置二维码边的空度，非负数
            hints.put(EncodeHintType.MARGIN, 1);
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            //MatrixToImageWriter.writeToPath(bitMatrix, "gif", new File("F:\\zxing.gif").toPath());// 输出原图片

            //二维码转base64String
            String token = "data:image/gif;base64,";

            /*
                问题：生成二维码正常,生成带logo的二维码logo变成黑白
                原因：MatrixToImageConfig默认黑白，需要设置BLACK、WHITE
                解决：https://ququjioulai.iteye.com/blog/2254382
            */
            // 生成带Logo的二维码
            BufferedImage bufferedImage = null;
            if (null != imagePath && !"".equals(imagePath)){
                File imageFile = new File(imagePath);
                if (null != imageFile && imageFile.exists()){
                    // 二维码上添加Logo
                    MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(0xFF000001, 0xFFFFFFFF);
                    //BufferedImage bufferedImage = LogoMatrix(toBufferedImage(bitMatrix), new File("D:\\logo.png"));
                    bufferedImage = LogoMatrix(MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig), imageFile);
                }
            }

            // 若未生成二维码，则生成普通二维码
            if (null == bufferedImage){
                bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            }
            result = token + bufferedImageToBase64String(bufferedImage);

//            // Test 输出二维码图
//            File f1 = new File("F:\\logoZxing.gif");
//            ImageIO.write(bufferedImage, "jpg", f1);

            System.out.println("输出成功.");
        } catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 识别二维码
     */
    public static void QRReader(File file) throws IOException, NotFoundException {
        MultiFormatReader formatReader = new MultiFormatReader();
        //读取指定的二维码文件
        BufferedImage bufferedImage = ImageIO.read(file);
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bufferedImage)));
        //定义二维码参数
        Map hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        com.google.zxing.Result result = formatReader.decode(binaryBitmap, hints);
        //输出相关的二维码信息
        System.out.println("解析结果：" + result.toString());
        System.out.println("二维码格式类型：" + result.getBarcodeFormat());
        System.out.println("二维码文本内容：" + result.getText());
        bufferedImage.flush();
    }

    /**
     * 二维码添加logo
     *
     * @param matrixImage 源二维码图片
     * @param logoFile    logo图片
     * @return 返回带有logo的二维码图片
     * 参考：https://blog.csdn.net/weixin_39494923/article/details/79058799
     */
    public static BufferedImage LogoMatrix(BufferedImage matrixImage, File logoFile) throws IOException {
        /**
         * 读取二维码图片，并构建绘图对象
         */
        Graphics2D g2 = matrixImage.createGraphics();

        int matrixWidth = matrixImage.getWidth();
        int matrixHeigh = matrixImage.getHeight();

        /**
         * 读取Logo图片
         */
        BufferedImage logo = ImageIO.read(logoFile);

        //开始绘制图片
        g2.drawImage(logo, matrixWidth / 5 * 2, matrixHeigh / 5 * 2, matrixWidth / 5, matrixHeigh / 5, null);//绘制
        BasicStroke stroke = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        g2.setStroke(stroke);// 设置笔画对象
        //指定弧度的圆角矩形
        RoundRectangle2D.Float round = new RoundRectangle2D.Float(matrixWidth / 5 * 2, matrixHeigh / 5 * 2, matrixWidth / 5, matrixHeigh / 5, 20, 20);
        g2.setColor(Color.white);
        g2.draw(round);// 绘制圆弧矩形

        //设置logo 有一道灰色边框
        BasicStroke stroke2 = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        g2.setStroke(stroke2);// 设置笔画对象
        RoundRectangle2D.Float round2 = new RoundRectangle2D.Float(matrixWidth / 5 * 2 + 2, matrixHeigh / 5 * 2 + 2, matrixWidth / 5 - 4, matrixHeigh / 5 - 4, 20, 20);
        g2.setColor(new Color(128, 128, 128));
        g2.draw(round2);// 绘制圆弧矩形

        g2.dispose();
        matrixImage.flush();
        return matrixImage;
    }

    /**
     * 生成二维码
     */
    public static String QREncodeImg(String content,String imagePath) {
        String result = null;

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
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            //MatrixToImageWriter.writeToPath(bitMatrix, "gif", new File("F:\\zxing.gif").toPath());// 输出原图片

            /*
                问题：生成二维码正常,生成带logo的二维码logo变成黑白
                原因：MatrixToImageConfig默认黑白，需要设置BLACK、WHITE
                解决：https://ququjioulai.iteye.com/blog/2254382
            */
            // 生成带Logo的二维码
            BufferedImage bufferedImage = null;
            if (null != imagePath && !"".equals(imagePath)){
                File imageFile = new File(imagePath);
                if (null != imageFile && imageFile.exists()){
                    // 二维码上添加Logo
                    MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(0xFF000001, 0xFFFFFFFF);
                    //BufferedImage bufferedImage = LogoMatrix(toBufferedImage(bitMatrix), new File("D:\\logo.png"));
                    bufferedImage = LogoMatrix(MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig), imageFile);
                }
            }

            // 若未生成二维码，则生成普通二维码
            if (null == bufferedImage){
                bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            }

            /*
             *     添加背景图片
             */
            BufferedImage backgroundImage = ImageIO.read(new File("C:/Users/Administrator/Desktop/test/createImage.jpg"));
            //距离背景图片x边的距离，居中显示
            int disx=330;
            //距离y边距离 * * * *
            int disy=110;
            Graphics2D rng = backgroundImage.createGraphics();
            rng.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP));
            rng.drawImage(bufferedImage,disx,disy,150,150,null);
            rng.dispose();
            bufferedImage=backgroundImage;
            bufferedImage.flush();
            ImageIO.write(bufferedImage, "png", new File(imagePath));

            System.out.println("输出成功.");
        } catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    public static void main(String[] args) {
        QREncodeImg("https://www.baidu.com", "C:/Users/Administrator/Desktop/test/createImage_qr.jpg");
    }
}
