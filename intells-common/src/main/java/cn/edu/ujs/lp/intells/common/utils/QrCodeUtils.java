package cn.edu.ujs.lp.intells.common.utils;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

/**
 * 二维码生成工具类
 */
public class QrCodeUtils {

    private static final String CHARSET = "utf-8";
    public static final String FORMAT = "JPG";
    // 二维码尺寸
    private static final int QRCODE_SIZE = 300;
    // LOGO宽度
    private static final int LOGO_WIDTH = 60;
    // LOGO高度
    private static final int LOGO_HEIGHT = 60;

    /**
     * 生成二维码
     *
     * @param content      二维码内容
     * @param logo     logo图片
     * @param needCompress 是否压缩logo
     * @return 图片
     * @throws Exception
     */
    private static BufferedImage createImage(String content, BufferedImage logo, boolean needCompress) throws Exception {
        BufferedImage image = null;

        try {
            Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
            hints.put(EncodeHintType.MARGIN, 1);
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE,
                    hints);

            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }

            //插入内嵌图片
            if (logo != null) QrCodeUtils.insertImage(image, logo, needCompress);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("生成二维码失败:"+e.getMessage());
        }

        return image;
    }

    /**
     * 插入LOGO
     *
     * @param source       二维码图片
     * @param logo     LOGO图片
     * @param needCompress 是否压缩
     * @throws IOException
     */
    private static void insertImage(BufferedImage source, BufferedImage logo, boolean needCompress) throws Exception {

        try {
            Image logoimage = logo.getSubimage(0,0,logo.getWidth(),logo.getHeight());

            int width = logoimage.getWidth(null);
            int height = logoimage.getHeight(null);
            if (needCompress) { // 压缩LOGO
                if (width > LOGO_WIDTH) {
                    width = LOGO_WIDTH;
                }
                if (height > LOGO_HEIGHT) {
                    height = LOGO_HEIGHT;
                }
                Image image = logoimage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                g.drawImage(image, 0, 0, null); // 绘制缩小后的图
                g.dispose();
                logoimage = image;
            }

            // 插入LOGO
            Graphics2D graph = source.createGraphics();
            int x = (QRCODE_SIZE - width) / 2;
            int y = (QRCODE_SIZE - height) / 2;
            graph.drawImage(logoimage, x, y, width, height, null);
            Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
            graph.setStroke(new BasicStroke(3f));
            graph.draw(shape);
            graph.dispose();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("二维码生成中插入内嵌图片失败："+e.getMessage());
        }
    }

    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content      内容
     * @param logoPath     LOGO地址
     * @param needCompress 是否压缩LOGO
     * @throws Exception
     */
    public static BufferedImage encode(String content, String logoPath, boolean needCompress)
            throws Exception {

        BufferedImage image = null,logoimage = null;

        //读取logo文件
        try {
            if ((logoPath != null)&&(logoPath != "")) {
                File logofile = new File(logoPath);
                if (logofile.exists())
                    logoimage = ImageIO.read(logofile);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logoimage = null; //读取失败插入null
        }

        //产生二维码图像流
        try {
            image = QrCodeUtils.createImage(content, logoimage, needCompress);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("产生二维码图片失败:"+e.getMessage());
        }

        return image;
    }

}
