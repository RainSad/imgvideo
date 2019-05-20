import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author sunwx
 * @description:
 * @date 2019/5/1716:46
 */
public class Main2 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Canvas canvas = new Canvas(300, 400);
        canvas.setTranslateY(0);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();

        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.scale(2, 2);

        gc.setStroke(Color.GREY);
        gc.setFill(Color.GREY);

// 样板
        gc.strokeLine(60, 0, 60, 100);
        gc.fillText("hello", 120, 140);

// 需要混合的部分
        gc.strokeLine(80, 0, 80, 100);
        gc.fillText("hello", 120, 150);

// 清除部分
        gc.clearRect(0, 0, 120, 20);

// 设置混合模式
// gc.setGlobalBlendMode(BlendMode.DARKEN);

// 重绘元件。为了更清楚的突出混合色，所以循环100遍。
        for (int i = 0; i < 100; i++)
            gc.strokeLine(80, 0, 80, 100);
        for (int i = 0; i < 100; i++)
            gc.fillText("hello", 120, 150);
    }


    public static void main(String[] args) {
        launch(args);

    }
}
