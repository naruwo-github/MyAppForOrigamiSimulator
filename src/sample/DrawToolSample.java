package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DrawToolSample extends Application {

    public VBox addVBox2() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);
        vbox.setStyle("-fx-background-color: #F5F5F5;");

        Text title = new Text("ドック");
        vbox.getChildren().add(title);

        Hyperlink options[] = new Hyperlink[] {
            new Hyperlink("カラー"),
            new Hyperlink("パレット"),
            new Hyperlink("レイヤー"),
        };

        for(int i = 0; i < options.length; i++) {
            VBox.setMargin(options[i], new Insets(0,0,0,3));
            vbox.getChildren().add(options[i]);
        }

        return vbox;
    }
    public MenuBar addMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("ファイル");
            MenuItem itemCanvas = new MenuItem("新規作成");
            MenuItem itemOpen = new MenuItem("開く");
            MenuItem itemSave = new MenuItem("保存");
            MenuItem itemSaveAsName = new MenuItem("名前をつけて保存");
            MenuItem itemPrint = new MenuItem("印刷");
            MenuItem itemClose = new MenuItem("閉じる");
            menuFile.getItems().addAll(
                    itemCanvas,itemOpen,new SeparatorMenuItem(),
                    itemSave,itemSaveAsName,new SeparatorMenuItem(),
                    itemPrint,new SeparatorMenuItem(),
                    itemClose);
            
            
        Menu menuEdit = new Menu("編集");
            MenuItem itemUndo = new MenuItem("取り消し");
            MenuItem itemCut = new MenuItem("切り取り");
            MenuItem itemCopy = new MenuItem("コピー");
            MenuItem itemPaste = new MenuItem("貼り付け");
            MenuItem itemTrim = new MenuItem("トリミング");
            MenuItem itemLeftTurn = new MenuItem("左回転");
            MenuItem itemRightTurn = new MenuItem("右回転");
            MenuItem itemReverse = new MenuItem("左右反転");
            MenuItem itemCanvasSize = new MenuItem("キャンバスサイズの変更");
            MenuItem itemGroup = new MenuItem("グループ化");
             menuEdit.getItems().addAll(
                    itemUndo,new SeparatorMenuItem(),
                    itemCut,itemCopy,itemPaste,itemTrim,new SeparatorMenuItem(),
                    itemLeftTurn,itemRightTurn,itemReverse,new SeparatorMenuItem(),
                    itemCanvasSize,itemGroup);
             
             
        Menu menuLayer = new Menu("レイヤー");
            MenuItem itemAdd = new MenuItem("追加");
            menuLayer.getItems().add(itemAdd);
            
        Menu menuFilter = new Menu("フィルター");
            MenuItem itemGaus = new MenuItem("ガウスぼかし");
            MenuItem itemGetLine = new MenuItem("線画抽出");
            menuFilter.getItems().addAll(itemGaus,itemGetLine);
            
        Menu menuSelect = new Menu("選択範囲");
            MenuItem itemSelectAll = new MenuItem("全て選択");
            MenuItem itemSelectClear = new MenuItem("解除");
            MenuItem itemSelectReverse = new MenuItem("反転");
            MenuItem itemSelectZoom = new MenuItem("拡大");
            MenuItem itemSelectZoomOut = new MenuItem("縮小");
            menuSelect.getItems().addAll(itemSelectAll,itemSelectClear,itemSelectReverse,
                    itemSelectZoom,itemSelectZoom,itemSelectZoomOut);
            
        Menu menuRuler = new Menu("定規");
            MenuItem itemHeikou = new MenuItem("平行");
            MenuItem itemZyuzi = new MenuItem("十字");
            MenuItem itemGrid = new MenuItem("グリッド");
            menuRuler.getItems().addAll(itemHeikou,itemZyuzi,itemGrid);
            
        Menu menuColor = new Menu("カラー");
            MenuItem itemColorCircle = new MenuItem("色相環");
            MenuItem itemColorPicker = new MenuItem("カラーピッカー");
            menuColor.getItems().addAll(itemColorCircle,itemColorPicker);
            
        Menu menuDisplay = new Menu("表示");
            MenuItem DisplayZoom = new MenuItem("拡大表示");
            MenuItem DisplayZoomOut = new MenuItem("縮小表示");
            menuDisplay.getItems().addAll(DisplayZoom,DisplayZoomOut);
            
        Menu menuTool = new Menu("ツール");
            MenuItem itemPencil = new MenuItem("鉛筆");
            MenuItem itemEraser = new MenuItem("消しゴム");
            MenuItem itemScal = new MenuItem("定規");
            MenuItem itemFinger = new MenuItem("指");
            MenuItem itemNuno = new MenuItem("布");
            MenuItem itemPastel = new MenuItem("パステル");
            MenuItem itemChork = new MenuItem("チョーク");
            MenuItem itemPen = new MenuItem("ペン");
            MenuItem itemHude = new MenuItem("筆");
            MenuItem itemAir = new MenuItem("エアブラシ");
            MenuItem spoit = new MenuItem("スポイト");
            menuTool.getItems().addAll(itemPencil,itemEraser,itemScal,
            itemFinger,itemNuno,itemPastel,itemChork,itemPen,itemHude,
            itemAir,spoit);
            
        Menu menuConfig = new Menu("設定");
        Menu menuWindow = new Menu("ウィンドウ");
        Menu menuHelp = new Menu("ヘルプ");

        menuBar.getMenus().addAll(menuFile, menuEdit, menuLayer, menuFilter,
                menuSelect, menuRuler, menuColor, menuDisplay, menuTool,
                menuConfig, menuWindow, menuHelp);

        return menuBar;
    }
    public VBox addToolBar() {
        VBox toolBar = new VBox();
        toolBar.setStyle("-fx-background-color: #F5F5F5;");

        //バーの表題
        TitledPane barName = new TitledPane();
        barName.setText("ツールバー");
        barName.setCollapsible(false);
        barName.setAnimated(false);

        //カラー
        TitledPane color = new TitledPane();
        color.setText("カラー");
        final ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(Color.WHITE);
        color.setContent(colorPicker);
        color.setCollapsible(false);
        color.setAnimated(false);

        //ブラシ
        /*工事中
        TitledPane burash = new TitledPane();
        color.setText("ブラシ");
        final TableView table = new TableView();


        Hyperlink options[] = new Hyperlink[] {
            new Hyperlink("鉛筆"),
            new Hyperlink("消しゴム"),
            new Hyperlink("パステル"),
            new Hyperlink("筆"),
        }; */

        toolBar.getChildren().addAll(barName,colorPicker);

        return toolBar;
    }

    @Override
    public void start(Stage stage) {

        BorderPane root = new BorderPane();

        MenuBar menuBar = addMenuBar();
        VBox toolBar = addToolBar();
        VBox paret = addVBox2();

        Pane wrapperPane = new Pane();
        wrapperPane.setStyle("-fx-background-color: #C0C0C0;");
        Canvas canvas = new Canvas(600,400);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, 600, 400);

        wrapperPane.getChildren().add(canvas);

        root.setTop(menuBar);
        root.setLeft(toolBar);
        root.setCenter(wrapperPane);
        root.setRight(paret);

        Scene scene = new Scene(root,1200,700,Color.LIGHTGREY);
        stage.setTitle("Circe Desktop");

        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}