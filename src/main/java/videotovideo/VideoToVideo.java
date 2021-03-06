package videotovideo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author sunwx
 * @description:
 * @date 2019/5/1711:51
 */
public class VideoToVideo {


    public void getVideo(GraphicsContext g, FFmpegFrameGrabber f) throws Exception {

        FFmpegFrameGrabber fFmpegFrameGrabber = new FFmpegFrameGrabber("C:/Users/sunwx/Pictures/2.mp4");
        fFmpegFrameGrabber.start();
        //获取视频总帧数
        int ftp = fFmpegFrameGrabber.getLengthInFrames();
        int x = fFmpegFrameGrabber.getImageWidth();
        int y = fFmpegFrameGrabber.getImageHeight();
        System.out.println(x + ":" + y);

        System.out.println("时长：" + ftp);

        System.out.println("时长 " + ftp / fFmpegFrameGrabber.getFrameRate() / 60);
        //标识
        int flag = 0;
        //Frame对象
        Frame frame;
        while (flag <= ftp) {
            frame = fFmpegFrameGrabber.grabImage();
				/*
				对视频的第五帧进行处理
				 */

            if (frame != null && flag == 5) {

                //文件储存对象
                ByteArrayOutputStream out = new ByteArrayOutputStream();


                ImageIO.write(FrameToBufferedImage(frame), "jpg", out);

                ByteArrayInputStream input = new ByteArrayInputStream(out.toByteArray());
                BufferedImage temp = ImageIO.read(input);
                showImgAscii(temp, g);
                System.out.println("在循环");
                continue;
            }
            flag++;
        }

    }


    public void showImgAscii(BufferedImage temp, GraphicsContext gc) throws IOException {
        //String base = "@#&$%*o!;.";// 字符串由复杂到简单
        String base = "MNHQ$OC?7>!:-;,.";// 字符串由复杂到简单


        // 创建图片
        BufferedImage image = new BufferedImage(temp.getWidth(), temp.getHeight(), BufferedImage.TYPE_INT_BGR);

        Graphics gh = image.getGraphics();

        gh.setColor(Color.white);

        gh.fillRect(0, 0, temp.getWidth(), temp.getHeight());

        gh.setColor(Color.black);

        for (int y = 0; y < temp.getHeight(); y += 4) {
            for (int x = 0; x < temp.getWidth(); x += 4) {
                int pixel = temp.getRGB(x, y);
                int r = (pixel & 0xff0000) >> 16, g = (pixel & 0xff00) >> 8, b = pixel & 0xff;
                float gray = 0.299f * r + 0.578f * g + 0.114f * b;
                int index = Math.round(gray * (base.length() + 1) / 255);
                gh.drawString(index >= base.length() ? " " : String.valueOf(base.charAt(index)), x, y);
            }
        }
        gh.drawImage(temp, 0, 0, temp.getWidth() / 5, temp.getHeight() / 5, null);
        gh.dispose();

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        ImageIO.write(image, "jpg", out);
        //ImageIO.write(image, "jpg", new File("C:/Users/sunwx/Pictures/3.jpg"));

        ByteArrayInputStream input = new ByteArrayInputStream(out.toByteArray());


        Image image1 = new Image(input);


        gc.drawImage(image1, 0, 0);

    }

    public static BufferedImage FrameToBufferedImage(Frame frame) {
        //创建BufferedImage对象
        Java2DFrameConverter converter = new Java2DFrameConverter();
        BufferedImage bufferedImage = converter.getBufferedImage(frame);
        return bufferedImage;
    }
}
