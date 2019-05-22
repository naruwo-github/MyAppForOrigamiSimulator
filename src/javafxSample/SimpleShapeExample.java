import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class SimpleShapeExample extends Application {
	public void start(Stage stage) {
		//(1)メインウィンドゥの設定
		stage.setWidth(300);
		stage.setHeight(200);

		//(2)シェイプの生成
		Rectangle rect = new Rectangle(10, 20, 60, 40);
		rect.setFill(Color.RED);

		Circle circle = new Circle(120, 40, 25);
		circle.setFill(Color.BLUE);

		//(3)グループの作成
		Group root = new Group();
		root.getChildren().addAll(rect, circle);

		//(4)グループをもとにシーンを作成
		Scene scene = new Scene(root);

		//(5)ステージにシーンを配置
		stage.setScene(scene);

		//(6)アプリケーションウィンドゥを表示
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}
