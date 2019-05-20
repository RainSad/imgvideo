import img2img.ImgToImg2;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import videotovideo.VideoToVideo2;

/**
 * @author sunwx
 * @description:
 * @date 2019/5/1716:46
 */
public class Main3 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FFmpegFrameGrabber f = new FFmpegFrameGrabber("C:/Users/sunwx/Pictures/2.mp4");
        Group root = new Group();
        Canvas canvas = new Canvas(1500, 1500);
        canvas.setTranslateY(0);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root, 1500, 1500);
        primaryStage.setScene(scene);
        primaryStage.show();

        GraphicsContext g = canvas.getGraphicsContext2D();
        VideoToVideo2 v = new VideoToVideo2();
        v.getVideo(f, g);
//        ImgToImg2 a = new ImgToImg2();
//        a.showImgAscii(g);
    }


    public static void main(String[] args) {
        launch(args);

    }
}
