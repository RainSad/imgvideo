package swing;

import img2img.ImgToImg;
import javafx.scene.canvas.GraphicsContext;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class VideoToVideo extends JFrame {

    public static void main(String[] args) throws Exception {
        VideoToVideo dc = new VideoToVideo();
        dc.init();

    }


    BufferedImage image = null;
    private Canvas canvas = new Canvas();

    public void init() throws Exception {
        FFmpegFrameGrabber f = new FFmpegFrameGrabber("C:/Users/sunwx/Pictures/2.mp4");
        f.start();
        int x = f.getImageWidth();
        int y = f.getImageHeight();
        System.out.println(x + ";" + y);
        JFrame frame = new JFrame("测试");
        frame.setSize(x, y);

        frame.add(canvas);
        frame.setLayout(null);
        canvas.setBounds(0, 0, x, y);
        frame.setVisible(true);
        getVideo(f);

    }

    public void getVideo(FFmpegFrameGrabber f) throws Exception {

        //获取视频总帧数
        int ftp = f.getLengthInFrames();
        int x = f.getImageWidth();
        int y = f.getImageHeight();
        System.out.println(x + ":" + y);

        System.out.println("时长：" + ftp);

        System.out.println("时长 " + ftp / f.getFrameRate() / 60);
        //标识
        int flag = 0;
        //Frame对象
        Frame frame;
        while (flag <= ftp) {
            frame = f.grabImage();
				/*
				对视频的第五帧进行处理
				 */

            if (frame != null && flag == 5) {

                //文件储存对象
                ByteArrayOutputStream out = new ByteArrayOutputStream();


                ImageIO.write(FrameToBufferedImage(frame), "jpg", out);

                ByteArrayInputStream input = new ByteArrayInputStream(out.toByteArray());
                BufferedImage temp = ImageIO.read(input);
                showImgAscii(temp);
                System.out.println("在循环");
                continue;
            }
            flag++;
        }

    }

    public void showImgAscii(BufferedImage temp) throws IOException {
        //String base = "@#&$%*o!;.";// 字符串由复杂到简单
        String base = "MNHQ$OC?7>!:-;,.";// 字符串由复杂到简单


        // 创建图片
        image = new BufferedImage(temp.getWidth(), temp.getHeight(), BufferedImage.TYPE_INT_BGR);

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
        canvas.paint(gh);
        canvas.getGraphics().drawImage(image, 0, 0, null);
    }

    public static BufferedImage FrameToBufferedImage(Frame frame) {
        //创建BufferedImage对象
        Java2DFrameConverter converter = new Java2DFrameConverter();
        BufferedImage bufferedImage = converter.getBufferedImage(frame);
        return bufferedImage;
    }
}
