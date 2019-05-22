import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Controls2 extends Application {
	CheckBox checkBox;//チェックボックス
	ChoiceBox<String> choiceBox;//チョイスボックス
	Slider slider;//スライダー
	Label label;//ラベル
	TextField textField;//テキストフィールド
	RadioButton radioButton1;//ラジオボタン1
	RadioButton radioButton2;//ラジオボタン2
	RadioButton radioButton3;//ラジオボタン3
	ToggleGroup toggleGroup;
	Button button;//ボタン
	TextArea textArea;//テキストエリア

	public void start(Stage stage) {
		stage.setWidth(500);
		stage.setHeight(350);

		checkBox = new CheckBox("チェックボックス");

		choiceBox = new ChoiceBox<>();
		choiceBox.getItems().addAll("チョイスボックス1", "チョイスボックス2", "チョイスボックス3");
		choiceBox.setValue("チョイスボックス1");

		radioButton1 = new RadioButton("ラジオボタン1");
		radioButton2 = new RadioButton("ラジオボタン2");
		radioButton3 = new RadioButton("ラジオボタン3");
		toggleGroup = new ToggleGroup();
		radioButton1.setToggleGroup(toggleGroup);
		radioButton2.setToggleGroup(toggleGroup);
		radioButton3.setToggleGroup(toggleGroup);

		label = new Label("ラベル");

		slider = new Slider(0, 50, 100);
		slider.setShowTickMarks(true);
		slider.setShowTickLabels(true);
		slider.setOnMouseClicked(event -> label.setText("スライダーの値 : "+(int)slider.getValue()));

		textField = new TextField("テキストフィールド");

		button = new Button("OK");
		button.setOnAction(event -> buttonPressed());

		textArea = new TextArea("テキストエリア");

		VBox box = new VBox(5);
		box.setPadding(new Insets(20, 25, 25, 25));

		box.getChildren().addAll(checkBox, radioButton1, radioButton2, radioButton3, choiceBox, slider, label, textField, button);

		MenuBar menuBar = new MenuBar();
		Menu fileMenu = new Menu("ファイル");
		MenuItem menuOpen = new MenuItem("開く");
		MenuItem menuExit = new MenuItem("終了");
		menuExit.setOnAction(event -> System.exit(0));
		fileMenu.getItems().add(menuOpen);
		fileMenu.getItems().add(menuExit);
		menuBar.getMenus().add(fileMenu);

		BorderPane root = new BorderPane();
		root.setLeft(box);
		root.setTop(menuBar);
		root.setCenter(textArea);

		stage.setScene(new Scene(root));
		stage.show();
	}

	void buttonPressed() {
		textArea.clear();
		textArea.appendText("チェックボックスの選択状態:"+checkBox.isSelected());
		textArea.appendText("¥nラジオボタンの選択項目:"+((RadioButton)toggleGroup.getSelectedToggle()).getText());
		textArea.appendText("¥nチョイスボックスの選択項目:"+choiceBox.getValue());
		textArea.appendText("¥nスライダーの値:"+slider.getValue());
		textArea.appendText("¥nテキストフィールドの文字列:"+textField.getText());
	}

	public static void main(String[] args) {
		launch();
	}
}
