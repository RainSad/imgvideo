package img2img;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * @author sunwx
 * @description:
 * @date 2019/5/1715:17
 */
public class ImgToImg2 {


    public void drawCanvas() {
        Canvas canvas = new Canvas();
        Graphics g = canvas.getGraphics();

    }


    public void showImgAscii(GraphicsContext gc) throws IOException {
        //String base = "@#&$%*o!;.";// 字符串由复杂到简单
        String base = "MNHQ$OC?7>!:-;,.";// 字符串由复杂到简单

        BufferedImage temp = ImageIO.read(new File("C:/Users/sunwx/Pictures/1.jpg"));

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
        ImageIO.write(image, "jpg", new File("C:/Users/sunwx/Pictures/3.jpg"));

        ByteArrayInputStream input = new ByteArrayInputStream(out.toByteArray());


        Image image1 = new Image(input);

        ImageView imageview = new ImageView(image1);

        gc.drawImage(image1, 0, 0, temp.getWidth(), temp.getHeight());

    }


}
