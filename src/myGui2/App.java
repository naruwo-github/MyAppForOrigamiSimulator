package myGui2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



public class App extends JFrame implements MouseListener, ActionListener, MouseMotionListener, ChangeListener{
	JPanel p;
	JLabel label; //imageicon用のやつ
	int frameWidth = 1200;
	int frameHeight = 870;

	//確認用のボタン
	//JButton confirm;

	//状態を記憶する変数
	int state = 0; //スタートは選択ツール


	//直線ツール
	//2点結んだら、それを結んでくれるってやあつ
  	List<List<Integer>> straightx = new ArrayList<List<Integer>>(); //確定後x座標
  	List<List<Integer>> straighty = new ArrayList<List<Integer>>(); //確定後y座標
  	List<Integer> stx = new ArrayList<Integer>(); //確定前x座標
  	List<Integer> sty = new ArrayList<Integer>(); //確定前y座標
  	List<Color> straightColor = new ArrayList<Color>(); //直線の際の色を記憶するリスト
  	List<Integer> stLineWidth = new ArrayList<Integer>(); //直線の太さを記憶するリスト


  	//自由線ツール
  	//マウスドラッグで辿った軌跡を描画する
    List<List<Integer>> freex = new ArrayList<List<Integer>>(); //確定後x座標
    List<List<Integer>> freey = new ArrayList<List<Integer>>(); //確定後y座標
    List<Integer> fx = new ArrayList<Integer>(); //確定前x座標
    List<Integer> fy = new ArrayList<Integer>(); //確定前y座標
    List<Color> freeColor = new ArrayList<Color>(); //自由線の色を記憶するリスト
    List<Integer> fLineWidth = new ArrayList<Integer>(); //自由線の太さを記憶するリスト


    //曲線ツール
    List<Integer> curvepx = new ArrayList<Integer>();
    List<Integer> curvepy = new ArrayList<Integer>();
    //現在入力中の曲線を記憶するリスト
    List<Integer> tmpcurvex = new ArrayList<Integer>();
  	List<Integer> tmpcurvey = new ArrayList<Integer>();
  	//確定前の暫定の曲線の制御点を格納するリスト
  	List<Integer> alcurvex = new ArrayList<Integer>();
  	List<Integer> alcurvey = new ArrayList<Integer>();
  	List<Color> curveColor = new ArrayList<Color>();
  	List<Integer> curveWidth = new ArrayList<Integer>();


    //四角形ツール
    int[] sqx = new int[2];
    int[] sqy = new int[2];
    boolean shift = false;
  //確定した四角形の4点を格納するリスト
    List<int[]> sqxlist = new ArrayList<int[]>();
    List<int[]> sqylist = new ArrayList<int[]>();


    //ハッチング ツール
    Polygon myPolygon = new Polygon(); //ハッチング用のポリゴン
    //ハッチング の際に描画した部分を格納する2重リスト
  	List<List<Integer>> hatchx = new ArrayList<List<Integer>>(); //確定後x座標
  	List<List<Integer>> hatchy = new ArrayList<List<Integer>>(); //確定後y座標
  	List<Integer> hx = new ArrayList<Integer>(); //確定前x座標
  	List<Integer> hy = new ArrayList<Integer>(); //確定前y座標
  	List<Color> hatchColor = new ArrayList<Color>(); //ハッチングの際の色を記憶するリスト
  	List<Integer> hlineWidth = new ArrayList<Integer>();  //ハッチング の線の太さを記憶するリスト


  	//rulingツール
  	//確定した入力曲線(ベジェ曲線)の制御点を格納するリスト
  	List<Integer> cpx = new ArrayList<Integer>();
  	List<Integer> cpy = new ArrayList<Integer>();
  	//現在入力中の曲線の全座標を格納するリスト
  	List<Integer> tmpx = new ArrayList<Integer>();
  	List<Integer> tmpy = new ArrayList<Integer>();
  	//確定前の暫定の曲線の制御点を格納するリスト
  	List<Integer> alx = new ArrayList<Integer>();
  	List<Integer> aly = new ArrayList<Integer>();
  	//入力線の端点を一時的に保存するやつ
  	List<Integer> tanx = new ArrayList<Integer>();
  	List<Integer> tany = new ArrayList<Integer>();
  	//保存されたやつを確定するやつ
  	List<Integer> rinx = new ArrayList<Integer>();
  	List<Integer> riny = new ArrayList<Integer>();
  	//輪郭線の座標を保持するリスト
  	List<Integer> coorinx = new ArrayList<Integer>();
  	List<Integer> cooriny = new ArrayList<Integer>();
  	//Origami Simulatorへの入力
  	List<Integer> rux = new ArrayList<Integer>();
  	List<Integer> ruy = new ArrayList<Integer>();
  	//List<Integer> orix = new ArrayList<Integer>();
  	//List<Integer> oriy = new ArrayList<Integer>();
  	List<List<Integer>> orix = new ArrayList<List<Integer>>();
  	List<List<Integer>> oriy = new ArrayList<List<Integer>>();
  	int bnum = 39; //rulingスタート地点の分割数
  	List<Double> heikatsu = new ArrayList<Double>(); //平滑化の値を格納するリスト

  	//今まで描画したrulingを記憶するリスト
  	List<List<Integer>> rulingsx = new ArrayList<List<Integer>>();
  	List<List<Integer>> rulingsy = new ArrayList<List<Integer>>();
  	//今まで描画した折り線を記憶するリスト
  	List<List<List<Integer>>> orisenx = new ArrayList<List<List<Integer>>>();
  	List<List<List<Integer>>> oriseny = new ArrayList<List<List<Integer>>>();
  	//山折りか谷折りか記憶する
  	List<List<Boolean>> foldingspatern = new ArrayList<List<Boolean>>();
  	int foldnum = 0;


	//スライダーの宣言
  	JSlider wSlider = new JSlider(1,9,1); //太さのやつ
    int sw = 1;
    JButton swButton = new JButton(String.valueOf(sw));
    JSlider rSlider = new JSlider(10,70,40); //ruling用のやつ
    JButton runumButton = new JButton(String.valueOf(bnum+1));

    //追加前の制御てんを変更するやつ
    int selectnum = 0; //1~4
    boolean selected = false;

    //輪郭線の表示を制御するボタン
    JButton onoff = new JButton("ON");
    boolean rdisplay = true;
    //cpの表示を制御するボタン
    JButton displayCP = new JButton("ON");
    boolean displaycp = true;
    //追加後の直前の4つの制御点を変更するやつ
    int determinedCP = 1000;
    boolean determined = false;

    //制御ボタン
    JButton st;
    boolean std = true;
    JButton fr;
    boolean frd = true;
    JButton cu;
    boolean cud = true;
    JButton sq;
    boolean sqd = true;
    JButton ha;
    boolean had = true;
    JButton ru;
    boolean rud = true;

    //山折りか谷折りかを記憶するやあつ
    JButton crease1;
    JButton crease2;
    JButton crease3;
    JButton crease4;
    JButton crease5;
    //true = MF, false = VF
    //List<Boolean> crease = new ArrayList<Boolean>();
    boolean[] creInfo = new boolean[5];

    void clearAll() {
    	//初期化するメソッド
    	//
    	//直線ツールの初期化
    	//
    	//2点結んだら、それを結んでくれるってやあつ
      	straightx = new ArrayList<List<Integer>>(); //確定後x座標
      	straighty = new ArrayList<List<Integer>>(); //確定後y座標
      	stx = new ArrayList<Integer>(); //確定前x座標
      	sty = new ArrayList<Integer>(); //確定前y座標
      	straightColor = new ArrayList<Color>(); //直線の際の色を記憶するリスト
      	stLineWidth = new ArrayList<Integer>(); //直線の太さを記憶するリスト


      	//
      	//自由線ツールの初期化
      	//
      	//マウスドラッグで辿った軌跡を描画する
        freex = new ArrayList<List<Integer>>(); //確定後x座標
        freey = new ArrayList<List<Integer>>(); //確定後y座標
        fx = new ArrayList<Integer>(); //確定前x座標
        fy = new ArrayList<Integer>(); //確定前y座標
        freeColor = new ArrayList<Color>(); //自由線の色を記憶するリスト
        fLineWidth = new ArrayList<Integer>(); //自由線の太さを記憶するリスト


        //
        //曲線ツールの初期化
        //
        curvepx = new ArrayList<Integer>();
        curvepy = new ArrayList<Integer>();
        //現在入力中の曲線を記憶するリスト
        tmpcurvex = new ArrayList<Integer>();
      	tmpcurvey = new ArrayList<Integer>();
      	//確定前の暫定の曲線の制御点を格納するリスト
      	alcurvex = new ArrayList<Integer>();
      	alcurvey = new ArrayList<Integer>();
      	curveColor = new ArrayList<Color>();
      	curveWidth = new ArrayList<Integer>();


      	//
        //四角形ツールの初期化
      	//
        sqx = new int[2];
        sqy = new int[2];
        shift = false;
        //確定した四角形の4点を格納するリスト
        sqxlist = new ArrayList<int[]>();
        sqylist = new ArrayList<int[]>();


        //
        //ハッチング ツールの初期化
        //
        myPolygon = new Polygon(); //ハッチング用のポリゴン
        //ハッチング の際に描画した部分を格納する2重リスト
      	hatchx = new ArrayList<List<Integer>>(); //確定後x座標
      	hatchy = new ArrayList<List<Integer>>(); //確定後y座標
      	hx = new ArrayList<Integer>(); //確定前x座標
      	hy = new ArrayList<Integer>(); //確定前y座標
      	hatchColor = new ArrayList<Color>(); //ハッチングの際の色を記憶するリスト
      	hlineWidth = new ArrayList<Integer>();  //ハッチング の線の太さを記憶するリスト


      	//
      	//rulingツールの初期化
      	//
      	//確定した入力曲線(ベジェ曲線)の制御点を格納するリスト
      	cpx = new ArrayList<Integer>();
      	cpy = new ArrayList<Integer>();
      	//現在入力中の曲線の全座標を格納するリスト
      	tmpx = new ArrayList<Integer>();
      	tmpy = new ArrayList<Integer>();
      	//確定前の暫定の曲線の制御点を格納するリスト
      	alx = new ArrayList<Integer>();
      	aly = new ArrayList<Integer>();
      	//入力線の端点を一時的に保存するやつ
      	tanx = new ArrayList<Integer>();
      	tany = new ArrayList<Integer>();
      	//保存されたやつを確定するやつ
      	rinx = new ArrayList<Integer>();
      	riny = new ArrayList<Integer>();
      	//輪郭線の座標を保持するリスト
      	coorinx = new ArrayList<Integer>();
      	cooriny = new ArrayList<Integer>();
      	//Origami Simulatorへの入力
      	rux = new ArrayList<Integer>();
      	ruy = new ArrayList<Integer>();
      	orix = new ArrayList<List<Integer>>();
      	oriy = new ArrayList<List<Integer>>();
      	bnum = 39; //rulingスタート地点の分割数
      	heikatsu = new ArrayList<Double>(); //平滑化の値を格納するリスト

      	//今まで描画したrulingを記憶するリスト
      	rulingsx = new ArrayList<List<Integer>>();
      	rulingsy = new ArrayList<List<Integer>>();
      	//今まで描画した折り線を記憶するリスト
      	orisenx = new ArrayList<List<List<Integer>>>();
      	oriseny = new ArrayList<List<List<Integer>>>();
      	//山折りか谷折りか記憶する
      	foldingspatern = new ArrayList<List<Boolean>>();
      	foldnum = 0;
        //折り線情報
        creInfo = new boolean[5];

        repaint();
    }

	App(){
		super("Drawing Application");
		init();
	}

	void init() {
		setBounds(200,50,1300,1000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container contentPane = getContentPane();
		//contentPane.setBackground(Color.BLACK);
		p = new JPanel();
		//p.setBackground(new Color(30,30,30));
		p.setLayout(null);
		//setBackground(new Color(30,30,30));

		//選択ツール=0
		JButton select = new JButton("");
		select.setBounds(0,0,70,70);
		select.setIcon(new ImageIcon("/Users/naruchan/desktop/ドローツールの元/アプリに使う6060/選択ツールの元.png"));
		select.setActionCommand("select");
		select.addActionListener(this);

		//直線ツール=1
		JButton straightLine = new JButton("");
		straightLine.setBounds(0,70,70,70);
		straightLine.setIcon(new ImageIcon("/Users/naruchan/desktop/ドローツールの元/アプリに使う6060/直線ツールの元.png"));
		straightLine.setActionCommand("sline");
		straightLine.addActionListener(this);

		//自由線ツール=2
		JButton freeLine = new JButton("");
		freeLine.setBounds(0,140,70,70);
		freeLine.setIcon(new ImageIcon("/Users/naruchan/desktop/ドローツールの元/アプリに使う6060/自由曲線ツールの元.png"));
		freeLine.setActionCommand("fline");
		freeLine.addActionListener(this);

		//曲線ツール=3
		JButton curveLine = new JButton("");
		curveLine.setBounds(0,210,70,70);
		curveLine.setIcon(new ImageIcon("/Users/naruchan/desktop/ドローツールの元/アプリに使う6060/曲線ツールの元.png"));
		curveLine.setActionCommand("cline");
		curveLine.addActionListener(this);

		//四角形ツール=4
		JButton sqLine = new JButton("");
		sqLine.setBounds(0,280,70,70);
		sqLine.setIcon(new ImageIcon("/Users/naruchan/desktop/ドローツールの元/アプリに使う6060/四角形ツールの元.png"));
		sqLine.setActionCommand("sqline");
		sqLine.addActionListener(this);

		//ハッチング ツール=5
		JButton hatchLine = new JButton("");
		hatchLine.setBounds(0,350,70,70);
		hatchLine.setIcon(new ImageIcon("/Users/naruchan/desktop/ドローツールの元/アプリに使う6060/ハッチングの元.png"));
		hatchLine.setActionCommand("hline");
		hatchLine.addActionListener(this);

		//rulingツール=6
		JButton rulingLine = new JButton("");
		rulingLine.setBounds(0,420,70,70);
		rulingLine.setIcon(new ImageIcon("/Users/naruchan/desktop/ドローツールの元/アプリに使う6060/rulingの元.png"));
		rulingLine.setActionCommand("rline");
		rulingLine.addActionListener(this);

		//追加ボタン
		JButton addButton = new JButton("Add");
		addButton.setBounds(0,490,70,70);
		addButton.setActionCommand("add");
		addButton.addActionListener(this);

		getRootPane().setDefaultButton(addButton);

		//削除ボタン
		JButton deleteButton = new JButton("Delete");
		deleteButton.setBounds(70,490,70,70);
		deleteButton.setActionCommand("delete");
		deleteButton.addActionListener(this);

		//色変更ボタン
		JButton colorChange = new JButton("Color");
		colorChange.setBounds(0,560,140,70);
		colorChange.setActionCommand("color");
		colorChange.addActionListener(this);

		//ruling追加のやーつ
		JButton another = new JButton("Another");
		another.setBounds(0,630,140,70);
		another.setActionCommand("another");
		another.addActionListener(this);
		/*
		//出力ボタン
		JButton output = new JButton("Out");
		output.setBounds(0,630,140,70);
		output.setActionCommand("out");
		output.addActionListener(this);
		*/


		//rulingの詳細ですよの表示
		JButton detailsRuling = new JButton("Ruling Info ↓↓↓");
		detailsRuling.setBounds(0,700,140,35);

		//rulingツールの際に輪郭線(入力線)を表示するかどうか
		JButton outline = new JButton("OutLine");
		outline.setBounds(0,735,100,35);
		//rulingの表示、非表示を切り替えるボタン
		onoff = new JButton("ON");
		onoff.setBounds(100,735,40,35);
		onoff.setActionCommand("onoff");
		onoff.addActionListener(this);

		//rulingツールの際に制御点を表示するかどうか
		JButton cp = new JButton("ControlPoint");
		cp.setBounds(0,770,100,35);
		//制御点の表示、非表示を切り替えるボタン
		displayCP = new JButton("ON");
		displayCP.setBounds(100,770,40,35);
		displayCP.setActionCommand("displaycp");
		displayCP.addActionListener(this);

		//太さを変更するスライダー
		JButton widSlider = new JButton("Stroke Width");
		widSlider.setBounds(0,805,100,35);
		wSlider.setBounds(0,840,140,35);
		wSlider.setBackground(Color.white);
		wSlider.addChangeListener(this);
		swButton.setBounds(100, 805, 40, 35);

		//rulingの本数を変更するスライダー
		JButton runumSlider = new JButton("Ruling Num");
		runumSlider.setBounds(0,875,100,35);
		rSlider.setBounds(0,905,140,35);
		rSlider.setBackground(Color.white);
		rSlider.addChangeListener(this);
		runumButton.setBounds(100, 875, 40, 35);

		//
		//
		//出力の設定をするとこを
		//詳細
		JButton outInfo = new JButton("Display Info ↓↓↓");
		outInfo.setBounds(1100,0,200,100);
		p.add(outInfo);
		//制御ボタン
		//直線ツールのon off
		JButton stInfo = new JButton("Straight Lines");
		stInfo.setBounds(1100,100,150,50);
		p.add(stInfo);
		st = new JButton("ON");
		st.setBounds(1250,100,50,50);
		st.setActionCommand("st");
		st.addActionListener(this);
		p.add(st);
		//自由線ツールのon off
		JButton frInfo = new JButton("Free Lines");
		frInfo.setBounds(1100,150,150,50);
		p.add(frInfo);
		fr = new JButton("ON");
		fr.setBounds(1250,150,50,50);
		fr.setActionCommand("fr");
		fr.addActionListener(this);
		p.add(fr);
		//曲線tルールのon off
		JButton cuInfo = new JButton("Curves");
		cuInfo.setBounds(1100,200,150,50);
		p.add(cuInfo);
		cu = new JButton("ON");
		cu.setBounds(1250,200,50,50);
		cu.setActionCommand("cu");
		cu.addActionListener(this);
		p.add(cu);
		//四角形ツールのon off
		JButton sqInfo = new JButton("Squares");
		sqInfo.setBounds(1100,250,150,50);
		p.add(sqInfo);
		sq = new JButton("ON");
		sq.setBounds(1250,250,50,50);
		sq.setActionCommand("sq");
		sq.addActionListener(this);
		p.add(sq);
		//ハッチング ツールのon off
		JButton haInfo = new JButton("Hatch Lines");
		haInfo.setBounds(1100,300,150,50);
		p.add(haInfo);
		ha = new JButton("ON");
		ha.setBounds(1250,300,50,50);
		ha.setActionCommand("ha");
		ha.addActionListener(this);
		p.add(ha);
		//rulingツールのon off
		JButton ruInfo = new JButton("Rulings");
		ruInfo.setBounds(1100,350,150,50);
		p.add(ruInfo);
		ru = new JButton("ON");
		ru.setBounds(1250,350,50,50);
		ru.setActionCommand("ru");
		ru.addActionListener(this);
		p.add(ru);

		JButton creaseInfo = new JButton("Creases Info ↓↓↓");
		creaseInfo.setBounds(1100,450,200,100);
		p.add(creaseInfo);


		//メニューバー
		//ここに保存とか読み込みとかやろう
		JMenuBar menubar = new JMenuBar();
	    JMenu menu1 = new JMenu("File");
	    JMenu menu2 = new JMenu("Edit");
	    JMenu menu3 = new JMenu("Export");
	    menubar.add(menu1);
	    menubar.add(menu2);
	    menubar.add(menu3);
	    JMenuItem menuitem1 = new JMenuItem("Open");
	    JMenuItem menuitem2 = new JMenuItem("Save as");
	    JMenuItem menuitem3 = new JMenuItem("Clear all");
	    JMenuItem menuitem4 = new JMenuItem("Other works");
	    JMenuItem menuitem5 = new JMenuItem("svg(for simulator)");
	    JMenuItem menuitem6 = new JMenuItem("svg");
	    JMenuItem menuitem7 = new JMenuItem("png");
	    JMenuItem menuitem8 = new JMenuItem("jpg");
	    JMenuItem menuitem9 = new JMenuItem("eps");
	    menuitem1.setActionCommand("open");
	    menuitem2.setActionCommand("save");
	    menuitem3.setActionCommand("clear");
	    menuitem4.setActionCommand("other");
	    menuitem1.addActionListener(this);
	    menuitem2.addActionListener(this);
	    menuitem3.addActionListener(this);
	    menuitem4.addActionListener(this);
	    menuitem5.setActionCommand("simulator");
	    menuitem6.setActionCommand("svg");
	    menuitem7.setActionCommand("png");
	    menuitem8.setActionCommand("jpg");
	    menuitem9.setActionCommand("eps");
	    menuitem5.addActionListener(this);
	    menuitem6.addActionListener(this);
	    menuitem7.addActionListener(this);
	    menuitem8.addActionListener(this);
	    menuitem9.addActionListener(this);
	    menu1.add(menuitem1);
	    menu1.add(menuitem2);
	    menu2.add(menuitem3);
	    menu2.add(menuitem4);
	    menu3.add(menuitem5);
	    menu3.add(menuitem6);
	    menu3.add(menuitem7);
	    menu3.add(menuitem8);
	    menu3.add(menuitem9);
	    setJMenuBar(menubar);

		/*
		//svg形式で出力
		JButton outSvg = new JButton("SVG");
		outSvg.setBounds(1100,700,200,50);
		outSvg.setActionCommand("svg");
		p.add(outSvg);
		JButton outSvgSimulator = new JButton("SVG (for Suimulator)");
		outSvgSimulator.setBounds(1100,750,200,50);
		outSvgSimulator.setActionCommand("svgsimulator");
		p.add(outSvgSimulator);
		JButton outJpg = new JButton("JPG");
		outJpg.setBounds(1100,800,200,50);
		outJpg.setActionCommand("jpg");
		p.add(outJpg);
		JButton outPng = new JButton("PNG");
		outPng.setBounds(1100,850,200,50);
		outPng.setActionCommand("png");
		p.add(outPng);
		JButton outEps = new JButton("EPS");
		outEps.setBounds(1100,900,200,50);
		outEps.setActionCommand("eps");
		p.add(outEps);
		*/

		for(int i = 0; i < creInfo.length; i++) {
			creInfo[i] = true;
		}

		//折り線の情報
		crease1 = new JButton("1 : Mountain Fold");
		crease1.setForeground(Color.RED);
		crease1.setBounds(1100, 550, 200, 25);
		crease1.setActionCommand("c1");
		crease1.addActionListener(this);
		p.add(crease1);

		crease2 = new JButton("2 : Mountain Fold");
		crease2.setForeground(Color.RED);
		crease2.setBounds(1100, 575, 200, 25);
		crease2.setActionCommand("c2");
		crease2.addActionListener(this);
		p.add(crease2);

		crease3 = new JButton("3 : Mountain Fold");
		crease3.setForeground(Color.RED);
		crease3.setBounds(1100, 600, 200, 25);
		crease3.setActionCommand("c3");
		crease3.addActionListener(this);
		p.add(crease3);

		crease4 = new JButton("4 : Mountain Fold");
		crease4.setForeground(Color.RED);
		crease4.setBounds(1100, 625, 200, 25);
		crease4.setActionCommand("c4");
		crease4.addActionListener(this);
		p.add(crease4);

		crease5 = new JButton("5 : Mountain Fold");
		crease5.setForeground(Color.RED);
		crease5.setBounds(1100, 650, 200, 25);
		crease5.setActionCommand("c5");
		crease5.addActionListener(this);
		p.add(crease5);

		//全体の枠
		JButton outBox = new JButton("");
		outBox.setBounds(1100,0,200,950);
		p.add(outBox);
		//
		//
		//


		p.add(select);
		p.add(straightLine);
		p.add(freeLine);
		p.add(curveLine);
		p.add(sqLine);
		p.add(hatchLine);
		p.add(rulingLine);
		p.add(addButton);
		p.add(deleteButton);
		p.add(colorChange);
		//p.add(output);
		p.add(another);

		p.add(detailsRuling);
		p.add(outline);
		p.add(onoff);
		p.add(cp);
		p.add(displayCP);

		p.add(wSlider);
		//p.add(wtext);
		p.add(widSlider);
		p.add(swButton);

		p.add(rSlider);
		p.add(runumSlider);
		p.add(runumButton);
		//p.add(rtext);

		/*
		//確認用のボタン
		confirm = new JButton("Confirm Button");
		confirm.setBounds(200,200,300,300);
		p.add(confirm);
		*/

		//rulingスタート地点分割の準備
		for(int i = 0; i < bnum; i++) {
			heikatsu.add(1.0/(bnum+1.0));
		}

		//label = new JLabel(new ImageIcon("/Users/naruchan/desktop/白紙.png"));
		//label = new JLabel(new ImageIcon("/Users/naruchan/desktop/波形反転.png"));
		label = new JLabel(new ImageIcon("/Users/naruchan/desktop/折り紙作品/入力用曲線折り紙作品/作品の写真画像/単純な曲線を折った様子.jpg"));
		//label = new JLabel(new ImageIcon("/Users/naruchan/desktop/折り紙シミュレータ/単純な曲線を折ったやつ/展開図.png"));
		label.setBounds(225,75,800,800);
		p.add(label);

		//requestFocus();
		//p.addKeyListener(this);
		//contentPane.addKeyListener(this);
		//TextField tf1 = new TextField();
        //tf1.addKeyListener(this);
        //p.add(tf1);
		//requestFocusInWindow();
		//requestFocus();
		//contentPane.requestFocus();

		contentPane.add(p);
		setVisible(true);
		addMouseListener(this);
        addMouseMotionListener(this);
	}

	Color selectColor = new Color(100,100,100);
	Color slineColor = new Color(100,100,255);
	Color flineColor = new Color(255,100,100);
	Color clineColor = new Color(100,255,100);
	Color sqlineColor = new Color(255,255,100);
	Color hlineColor = new Color(255,100,255);
	Color rlineColor = new Color(100,255,255);

	//フレームによって変えたいよね。どうやるんだろう。
	public void paintComponent(Graphics myg){
	    //super.paintComponent(myg);
	    //pcct++;
	    //myg.drawImage(buffimg, 0, 0, this);
	    //smyg.drawImage(buffimg, 0, 0,getSize().width, getSize().height, this);
	}

	public void paint(Graphics g){
		super.paint(g);
		//rulingモード時の輪郭の座標を格納するリスト
		coorinx = new ArrayList<Integer>();
    	cooriny = new ArrayList<Integer>();
    	//rulingモードの書き出しに必要なリスト
    	rux = new ArrayList<Integer>();
    	ruy = new ArrayList<Integer>();
    	//orix = new ArrayList<Integer>();
    	//oriy = new ArrayList<Integer>();
    	orix = new ArrayList<List<Integer>>();
    	oriy = new ArrayList<List<Integer>>();

		Graphics2D g2 = (Graphics2D)g;
		//両端を丸く
		BasicStroke bs1 = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		g2.setStroke(bs1);

		//それぞれのツールが何色かを示す。
    	g.setColor(selectColor);
		g.fillRect(73, 48, 63, 63);
		g.setColor(slineColor);
		g.fillRect(73, 118, 63, 63);
		g.setColor(flineColor);
		g.fillRect(73, 188, 63, 63);
		g.setColor(clineColor);
		g.fillRect(73, 258, 63, 63);
		g.setColor(sqlineColor);
		g.fillRect(73, 328, 63, 63);
		g.setColor(hlineColor);
		g.fillRect(73, 398, 63, 63);
		g.setColor(rlineColor);
		g.fillRect(73, 468, 63, 63);
		//色の縁取り
		g.setColor(Color.BLACK);
		g.drawRect(73, 48, 63, 63);
		g.drawRect(73, 118, 63, 63);
		g.drawRect(73, 188, 63, 63);
		g.drawRect(73, 258, 63, 63);
		g.drawRect(73, 328, 63, 63);
		g.drawRect(73, 398, 63, 63);
		g.drawRect(73, 468, 63, 63);

		//何ツールが選択されているか三角形を表示する
		if(state == 0) { //選択ツール
			MyPaint.drawTriangle(g,142,79);
		}else if(state == 1) { //直線ツール
			MyPaint.drawTriangle(g,142,149);
		}else if(state == 2) { //自由線ツール
			MyPaint.drawTriangle(g,142,219);
		}else if(state == 3) { //曲線ツール
			MyPaint.drawTriangle(g,142,289);
		}else if(state == 4) { //四角形ツール
			MyPaint.drawTriangle(g,142,359);
		}else if(state == 5) { //ハッチング ツール
			MyPaint.drawTriangle(g,142,429);
		}else if(state == 6) { //rulingツール
			MyPaint.drawTriangle(g,142,499);
		}

		//swで選択した太さに変更
		BasicStroke bs2 = new BasicStroke(sw, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		g2.setStroke(bs2);

		//
		//
		//
		//以下、描画するやつ
		//
		//
		//

		//直線ツール
		if(std == true) {
			//state == 1のやつ
			if(stx.size() > 0) {
				for(int i = 0; i < stx.size(); i++) {
					g.setColor(Color.red);
					g.drawOval(stx.get(i), sty.get(i), 2, 2);
				}
			}
			if(stx.size() == 2) {
				g.setColor(Color.blue);
				g.drawLine(stx.get(0), sty.get(0), stx.get(1), sty.get(1));
			}
			if(straightColor.size() > 0) {
				for(int i = 0; i < straightColor.size(); i++) {
					bs2 = new BasicStroke(stLineWidth.get(i), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
					g2.setStroke(bs2);
					g.setColor(straightColor.get(i));
					g.drawLine(straightx.get(i).get(0), straighty.get(i).get(0), straightx.get(i).get(1), straighty.get(i).get(1));
				}
			}
		}
		//線の太さを戻す
		bs2 = new BasicStroke(sw, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		g2.setStroke(bs2);
		//
		//自由線ツール
		if(frd == true) {
			//state == 2のやつ
			if(fx.size() > 0) {
				g.setColor(flineColor);
				for(int i = 0; i < fx.size() - 1; i++) {
					g.drawLine(fx.get(i), fy.get(i), fx.get(i+1), fy.get(i+1));
				}
			}
			if(freeColor.size() > 0) {
				for(int i = 0; i < freeColor.size(); i++) {
					bs2 = new BasicStroke(fLineWidth.get(i), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
					g2.setStroke(bs2);
					g.setColor(freeColor.get(i));
					for(int j = 0; j < freex.get(i).size() - 1; j++) {
						g.drawLine(freex.get(i).get(j), freey.get(i).get(j), freex.get(i).get(j+1), freey.get(i).get(j+1));
					}
				}
			}
		}
		//線の太さ戻す
		bs2 = new BasicStroke(sw, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		g2.setStroke(bs2);
		//
		if(cud == true) {
			//state == 3 曲線ツールのやつ
			//確定前の入力線を描画する部分
	    	if(alcurvex.size()!=0) {
	    		g.setColor(Color.BLUE);
	    		MyBezier.calcBezier(alcurvex,alcurvey,g);
	    		//制御点も表示
	    		for(int i = 0; i < alcurvex.size(); i++) {
	    			g.setColor(Color.RED);
	    			g.drawOval(alcurvex.get(i), alcurvey.get(i), 3, 3);
	    		}
	    	}
	    	//追加済みのcpからベジェ曲線を描画する部分
        	if(curvepx.size()%4 == 0 && curvepx.size() != 0) {
        		//g.setColor(Color.BLACK);
        		//MyBezier.calcBezier(curvepx, curvepy, g);
        		MyBezier.calcBezierWidCol(curvepx, curvepy, g, curveColor, curveWidth, g2);
        	}
		}
		//
		//線の太さ戻す
		bs2 = new BasicStroke(sw, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		g2.setStroke(bs2);
		//
		if(sqd == true) {
			//state == 4 四角形ツールのやつ
			//確定前の四角形
			if(sqx.length == 2) {
	    		//g.setColor(sqColor);
	    		if(shift == true) {
	    			MyPaint.drawSquare(g, sqx, sqy);
	    		}else {
	    			MyPaint.drawQuadrangle(g, sqx, sqy);
	    		}
	    	}
			//確定後の四角形
	    	if(sqxlist.size() > 0) {
	    		g.setColor(sqlineColor);
	    		for(int i = 0; i < sqxlist.size(); i++) {
	    			int[] x = sqxlist.get(i);
	    			int[] y = sqylist.get(i);
	    			MyPaint.drawShikakuFromFourPoint(g, x, y);
	    		}
	    	}
		}
		//
		//線の太さ戻す
		bs2 = new BasicStroke(sw, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		g2.setStroke(bs2);
		//
		if(had = true) {
			//state == 5 ハッチング ツールのやつ
	    	if(hatchx.size() > 0 && hatchy.size() > 0 && hatchx.size() == hatchy.size()) {
				//記憶したハッチング を描画する
				//System.out.println("aaaaaaaaa");
				//System.out.println(hatchColor.size());
				for(int i = 0; i < hatchColor.size(); i++) {
					g.setColor((Color)hatchColor.get(i));
					//線の太さ戻す
					bs2 = new BasicStroke(hlineWidth.get(i), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
					g2.setStroke(bs2);
					List<Integer> tmpx = new ArrayList<Integer>();
					List<Integer> tmpy = new ArrayList<Integer>();
					tmpx = (ArrayList<Integer>)hatchx.get(i);
					tmpy = (ArrayList<Integer>)hatchy.get(i);
					for(int j = 0; j < tmpx.size() - 2; j+=2) {
						g.drawLine(tmpx.get(j), tmpy.get(j), tmpx.get(j+1), tmpy.get(j+1));
						//System.out.println(tmpx.get(j)+" , "+tmpy.get(j));
					}
				}
			}
		}
		//
    	//線の太さ戻す
    	bs2 = new BasicStroke(sw, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    	g2.setStroke(bs2);
		//
		if(rud == true) {
			//state == 6 のやつ
			//rulingツール
			//制御線の補助線を表示させる部分
	    	//Color ori = new Color(150, 255, 150, 30);
			Color ori = new Color(230, 230, 150, 30);
	    	g.setColor(ori);
	    	MyRuling.drawHojyoSeigyo(g, cpx, cpy);

	    	//確定前の入力線を描画する部分
	    	if(alx.size()!=0) {
	    		g.setColor(Color.BLUE);
	    		MyBezier.calcBezier(alx,aly,g);
	    		//制御点も表示
	    		for(int i = 0; i < alx.size(); i++) {
	    			g.setColor(Color.RED);
	    			g.drawOval(alx.get(i), aly.get(i), 3, 3);
	    		}
	    	}

	    	if(displaycp == true && cpx.size() > 8 && alx.size() == 0) {
	    		for(int i = 8; i < cpx.size(); i+=8) {
	    			g.setColor(Color.red);
	    			g.drawOval(cpx.get(i), cpy.get(i), 3, 3);
	    			g.drawOval(cpx.get(i+1), cpy.get(i+1), 3, 3);
	    			g.drawOval(cpx.get(i+2), cpy.get(i+2), 3, 3);
	    			g.drawOval(cpx.get(i+3), cpy.get(i+3), 3, 3);
	    		}
	    	}

	    	//輪郭線を表示させる部分
	    	if(cpx.size() >= 12 && (cpx.size()-4) % 8 == 0) {
	    		if(cpx.size() == 12) {
	    			/*
	    			g.setColor(Color.BLACK);
	    			g.drawLine(rinx.get(0), riny.get(0), rinx.get(4), riny.get(4));
	    			g.drawLine(rinx.get(1), riny.get(1), rinx.get(5), riny.get(5));
	    			g.drawLine(rinx.get(4), riny.get(4), rinx.get(2), riny.get(2));
	    			g.drawLine(rinx.get(5), riny.get(5), rinx.get(3), riny.get(3));

	    			if(displayButton.getActionCommand().equals("表示")) {
		    			g.setColor(Color.BLACK);
		    			g.drawLine(rinx.get(0), riny.get(0), rinx.get(4), riny.get(4));
		    			g.drawLine(rinx.get(1), riny.get(1), rinx.get(5), riny.get(5));
		    			g.drawLine(rinx.get(4), riny.get(4), rinx.get(2), riny.get(2));
		    			g.drawLine(rinx.get(5), riny.get(5), rinx.get(3), riny.get(3));
	    			}
	    			*/
	    			MyBezier.saveCooxy(coorinx,cooriny,rinx.get(0),riny.get(0),rinx.get(4),riny.get(4));
	    			MyBezier.saveCooxy(coorinx,cooriny,rinx.get(1),riny.get(1),rinx.get(5),riny.get(5));
	    			MyBezier.saveCooxy(coorinx,cooriny,rinx.get(4),riny.get(4),rinx.get(2),riny.get(2));
	    			MyBezier.saveCooxy(coorinx,cooriny,rinx.get(5),riny.get(5),rinx.get(3),riny.get(3));
	    		}else {
	    			/*
	    			g.setColor(Color.BLACK);
	    			g.drawLine(rinx.get(0), riny.get(0), rinx.get(4), riny.get(4));
	    			g.drawLine(rinx.get(1), riny.get(1), rinx.get(5), riny.get(5));
	    			g.drawLine(rinx.get(4), riny.get(4), rinx.get(2), riny.get(2));
	    			g.drawLine(rinx.get(5), riny.get(5), rinx.get(3), riny.get(3));

	    			if(displayButton.getActionCommand().equals("表示")) {
		    			g.setColor(Color.BLACK);
		    			g.drawLine(rinx.get(0), riny.get(0), rinx.get(4), riny.get(4));
		    			g.drawLine(rinx.get(1), riny.get(1), rinx.get(5), riny.get(5));
		    			g.drawLine(rinx.get(4), riny.get(4), rinx.get(2), riny.get(2));
		    			g.drawLine(rinx.get(5), riny.get(5), rinx.get(3), riny.get(3));
	    			}
	    			*/
	    			MyBezier.saveCooxy(coorinx,cooriny,rinx.get(0),riny.get(0),rinx.get(4),riny.get(4));
	    			MyBezier.saveCooxy(coorinx,cooriny,rinx.get(1),riny.get(1),rinx.get(5),riny.get(5));
	    			MyBezier.saveCooxy(coorinx,cooriny,rinx.get(4),riny.get(4),rinx.get(2),riny.get(2));
	    			MyBezier.saveCooxy(coorinx,cooriny,rinx.get(5),riny.get(5),rinx.get(3),riny.get(3));
	    			for(int i = 0; i < (cpx.size()-20)/8+1; i++) {
	    				/*
	    				g.setColor(Color.BLACK);
	        			g.drawLine(rinx.get(2+i*4), riny.get(2+i*4), rinx.get(8+i*4), riny.get(8+i*4));
	        			g.drawLine(rinx.get(3+i*4), riny.get(3+i*4), rinx.get(9+i*4), riny.get(9+i*4));
	        			g.drawLine(rinx.get(8+i*4), riny.get(8+i*4), rinx.get(6+i*4), riny.get(6+i*4));
	        			g.drawLine(rinx.get(9+i*4), riny.get(9+i*4), rinx.get(7+i*4), riny.get(7+i*4));

	    				if(displayButton.getActionCommand().equals("表示")) {
		    				g.setColor(Color.BLACK);
		        			g.drawLine(rinx.get(2+i*4), riny.get(2+i*4), rinx.get(8+i*4), riny.get(8+i*4));
		        			g.drawLine(rinx.get(3+i*4), riny.get(3+i*4), rinx.get(9+i*4), riny.get(9+i*4));
		        			g.drawLine(rinx.get(8+i*4), riny.get(8+i*4), rinx.get(6+i*4), riny.get(6+i*4));
		        			g.drawLine(rinx.get(9+i*4), riny.get(9+i*4), rinx.get(7+i*4), riny.get(7+i*4));
	    				}
	    				*/
	        			MyBezier.saveCooxy(coorinx,cooriny,rinx.get(2+i*4),riny.get(2+i*4),rinx.get(8+i*4),riny.get(8+i*4));
	        			MyBezier.saveCooxy(coorinx,cooriny,rinx.get(3+i*4), riny.get(3+i*4), rinx.get(9+i*4), riny.get(9+i*4));
	        			MyBezier.saveCooxy(coorinx,cooriny,rinx.get(8+i*4),riny.get(8+i*4),rinx.get(6+i*4),riny.get(6+i*4));
	        			MyBezier.saveCooxy(coorinx,cooriny,rinx.get(9+i*4), riny.get(9+i*4), rinx.get(7+i*4), riny.get(7+i*4));
	    			}
	    		}

	    		if(rdisplay == true) {
		    		//追加済みのcpからベジェ曲線を描画する部分
		        	if(cpx.size()%4 == 0 && cpx.size() != 0) {
		        		g.setColor(Color.BLACK);
		        		MyBezier.calcBezier(cpx, cpy, g);
		        	}
	    		}
	    	}else {
	    		//追加済みのcpからベジェ曲線を描画する部分
	        	if(cpx.size()%4 == 0 && cpx.size() != 0) {
	        		g.setColor(Color.BLACK);
	        		MyBezier.calcBezier(cpx, cpy, g);
	        	}
	    	}

	    	//ruling描画する部分(輪郭線も考慮)
	    	if(cpx.size() >= 12 && cpx.size() != 0) {
	    		MyRuling.drawRuling(rlineColor,g, cpx, cpy, coorinx, cooriny, heikatsu, rux, ruy, orix, oriy);
	    		//
	    		//制御線の延長を考慮したRuling
	    		//MyRuling.drawRulingKai(rlineColor,g, cpx, cpy, coorinx, cooriny, heikatsu, rux, ruy, orix, oriy);
	    		//MyRuling.drawRulingProto(g, cpx, cpy, heikatsu);
	    		//
	    		//
	    	}

	    	//記憶ずみのruling描画
	    	if(orisenx.size() > 0) {
				for(int i = 0; i < orisenx.size(); i++) {
					for(int j = 0; j < rulingsx.get(i).size() - 2; j+=2) {
						g.setColor(rlineColor);
						g.drawLine(rulingsx.get(i).get(j), rulingsy.get(i).get(j), rulingsx.get(i).get(j+1), rulingsy.get(i).get(j+1));
					}
				}
			}
		}
		//
		//線の太さを元に戻す
    	bs2 = new BasicStroke(sw, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		g2.setStroke(bs2);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		if(state == 0) {
			//選択ツール
		}else if(state == 1) {
			//直線ツール
		}else if(state == 2) {
			//自由線ツール
			fx.add(e.getX());
			fy.add(e.getY());
		}else if(state == 3) {
			//曲線ツール
			tmpcurvex.add(e.getX());
			tmpcurvey.add(e.getY());
		}else if(state == 4) {
			//四角形ツール
		}else if(state == 5) {
			//ハッチング ツール
			int x = e.getX();
			int y = e.getY();
			myPolygon.addPoint(x, y);
		}else if(state == 6) {
			//rulingツール
			tmpx.add(e.getX());
			tmpy.add(e.getY());
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		String cmd = e.getActionCommand();
		//repaint();
		if(cmd == "select") {
			if(state != 0)
				state = 0;
			repaint();
		}else if(cmd == "sline") {
			if(state != 1)
				state = 1;
			repaint();
		}else if(cmd == "fline") {
			if(state != 2)
				state = 2;
			repaint();
		}else if(cmd == "cline") {
			if(state != 3)
				state = 3;
			repaint();
		}else if(cmd == "sqline") {
			if(state != 4)
				state = 4;
			repaint();
		}else if(cmd == "hline") {
			if(state != 5)
				state = 5;
			repaint();
		}else if(cmd == "rline") {
			if(state != 6)
				state = 6;
			repaint();
		}else if(cmd == "add") {
			if(state == 1) {
				if(stx.size() == 2) {
					straightx.add(stx);
					straighty.add(sty);
					straightColor.add(slineColor);
					stLineWidth.add(sw);
					stx = new ArrayList<Integer>();
					sty = new ArrayList<Integer>();
				}
				repaint();
			}else if(state == 2) {
				//自由曲線モード
				if(fx.size() == fy.size() && fx.size() > 0) {
					freex.add(fx);
					freey.add(fy);
					freeColor.add(flineColor);
					fLineWidth.add(sw);
				}
				fx = new ArrayList<Integer>();
				fy = new ArrayList<Integer>();
				repaint();
			}else if(state == 3) {
				//曲線ツール
				for(int i = 0; i < alcurvex.size(); i++) {
	        		curvepx.add(alcurvex.get(i));
	        		curvepy.add(alcurvey.get(i));
	        	}
				curveColor.add(clineColor);
				curveWidth.add(sw);

	        	alcurvex = new ArrayList<Integer>();
	        	alcurvey = new ArrayList<Integer>();
	        	repaint();
			}else if(state == 4) {
				//四角形ツール
				if(sqx.length == 2) {
					if(shift == true) {
						//正方形
						MyPaint.calcSquarePoint(sqx, sqy, sqxlist, sqylist);
						sqx = new int[2];
						sqy = new int[2];
					}else {
						//長方形
						MyPaint.calcQuadranglePoint(sqx, sqy, sqxlist, sqylist);
						sqx = new int[2];
						sqy = new int[2];
					}
					repaint();
				}
			}else if(state == 5) {
				//hatchingツール
				//System.out.println("hx'size = "+hx.size()+" , hy's size = "+hy.size());
				if(hx.size() > 0 && hy.size() > 0 && hx.size() == hy.size()) {
					hatchx.add((ArrayList<Integer>)hx);
					hatchy.add((ArrayList<Integer>)hy);
					hatchColor.add((Color)hlineColor);
					hlineWidth.add(sw);
				}
				hx = new ArrayList<Integer>();
				hy = new ArrayList<Integer>();
				repaint();
			}else if(state == 6) {
				//rulingモード
				for(int i = 0; i < alx.size(); i++) {
	        		cpx.add(alx.get(i));
	        		cpy.add(aly.get(i));
	        	}
	        	alx = new ArrayList<Integer>();
	        	aly = new ArrayList<Integer>();
	        	repaint();

	        	//輪郭線についての部分
	        	for(int i = 0; i < tanx.size(); i++) {
	        		rinx.add(tanx.get(i));
	        		riny.add(tany.get(i));
	        	}
	        	tanx = new ArrayList<Integer>();
	        	tany = new ArrayList<Integer>();
	        	repaint();
			}
		} else if(cmd == "delete") {
			//削除機能
			if(state == 1) {
				//直線ツールモード
				if(straightColor.size() > 0) {
					List<List<Integer>> tmpx = new ArrayList<List<Integer>>();
				  	List<List<Integer>> tmpy = new ArrayList<List<Integer>>();
				  	List<Color> tmpc = new ArrayList<Color>();
				  	List<Integer> tmpw = new ArrayList<Integer>();
				  	for(int i = 0; i < straightColor.size() - 1; i++) {
				  		tmpx.add(straightx.get(i));
				  		tmpy.add(straighty.get(i));
				  		tmpc.add(straightColor.get(i));
				  		tmpw.add(stLineWidth.get(i));
				  	}
				  	straightx = tmpx;
				  	straighty = tmpy;
				  	straightColor = tmpc;
				  	stLineWidth = tmpw;
				}
				repaint();
			}else if(state == 2) {
				//自由曲線ツール
				if(freeColor.size() > 0) {
					List<List<Integer>> tmpx = new ArrayList<List<Integer>>();
				  	List<List<Integer>> tmpy = new ArrayList<List<Integer>>();
				  	List<Color> tmpc = new ArrayList<Color>();
				  	List<Integer> tmpw = new ArrayList<Integer>();
					for(int i = 0; i < freeColor.size() - 1; i++) {
						tmpx.add(freex.get(i));
						tmpy.add(freey.get(i));
						tmpc.add(freeColor.get(i));
						tmpw.add(fLineWidth.get(i));
					}
					freex = tmpx;
					freey = tmpy;
					freeColor = tmpc;
					fLineWidth = tmpw;
				}
				repaint();
			}else if(state == 3) {
				//曲線ツール
				if(curvepx.size() > 0) {
					ArrayList<Integer> tmpx = new ArrayList<Integer>();
					ArrayList<Integer> tmpy = new ArrayList<Integer>();
					List<Color> tmpc = new ArrayList<Color>();
				  	List<Integer> tmpw = new ArrayList<Integer>();
					for(int i = 0; i < curvepx.size() - 4; i++) {
						tmpx.add(curvepx.get(i));
						tmpy.add(curvepy.get(i));
					}
					for(int i = 0; i < curveColor.size()-1; i++) {
						tmpc.add(curveColor.get(i));
						tmpw.add(curveWidth.get(i));
					}
					curvepx = tmpx;
					curvepy = tmpy;
					curveColor = tmpc;
					curveWidth = tmpw;
					repaint();
				}
			}else if(state == 4) {
				//四角形ツール
				if(sqx.length > 0) {
					sqx = new int[2];
					sqy = new int[2];
					repaint();
				}
				if(sqxlist.size() > 0) {
					sqxlist.remove(sqxlist.size() - 1);
					sqylist.remove(sqylist.size() - 1);
					repaint();
				}
			}else if(state == 5) {
				//ハッチング ツール
				if(hatchx.size() > 0) {
					List<Color> tmpa = new ArrayList<Color>();
					List<Integer> tmpd = new ArrayList<Integer>();
					List<List<Integer>> tmpb = new ArrayList<List<Integer>>();
				  	List<List<Integer>> tmpc = new ArrayList<List<Integer>>();
					for(int i = 0; i < hatchColor.size() - 1; i++) {
						tmpa.add(hatchColor.get(i));
						tmpb.add(hatchx.get(i));
						tmpc.add(hatchy.get(i));
						tmpd.add(hlineWidth.get(i));
					}
					hatchColor = tmpa;
					hatchx = tmpb;
					hatchy = tmpc;
					hlineWidth = tmpd;
				}
				repaint();
			}else if(state == 6) {
				//rulingツール
				if(cpx.size() > 0) {
					ArrayList<Integer> tmpx = new ArrayList<Integer>();
					ArrayList<Integer> tmpy = new ArrayList<Integer>();
					for(int i = 0; i < cpx.size() - 4; i++) {
						tmpx.add(cpx.get(i));
						tmpy.add(cpy.get(i));
					}
					cpx = tmpx;
					cpy = tmpy;
					repaint();
				}
				if(rinx.size() > 0) {
					ArrayList<Integer> tmpx = new ArrayList<Integer>();
					ArrayList<Integer> tmpy = new ArrayList<Integer>();
					if(rinx.size() - 2 > 0) {
						for(int i = 0; i < rinx.size() - 2; i++) {
							tmpx.add(rinx.get(i));
							tmpy.add(riny.get(i));
						}
						rinx = tmpx;
						riny = tmpy;
					}else {
						rinx = new ArrayList<Integer>();
						riny = new ArrayList<Integer>();
					}
					repaint();
				}
				if(coorinx.size() > 0) {
					ArrayList<Integer> tmpx = new ArrayList<Integer>();
					ArrayList<Integer> tmpy = new ArrayList<Integer>();
					for(int i = 0; i < coorinx.size() - 1000; i++) {
						tmpx.add(coorinx.get(i));
						tmpy.add(cooriny.get(i));
					}
					coorinx = tmpx;
					cooriny = tmpy;
					repaint();
				}
			}
		}else if(cmd == "color") {
			JColorChooser colorchooser = new JColorChooser();
			Color color = JColorChooser.showDialog(this, "Selecting Colors", Color.white);
			if(state == 0) {
				selectColor = color;
			}else if(state == 1) {
				slineColor = color;
			}else if(state == 2) {
				flineColor = color;
			}else if(state == 3) {
				clineColor = color;
			}else if(state == 4) {
				sqlineColor = color;
			}else if(state == 5) {
				hlineColor = color;
			}else if(state == 6) {
				rlineColor = color;
			}
			repaint();
		}else if(cmd == "swidth") {
			//repaint();
		}else if(cmd == "out") {
			//ファイル出力
			if(state == 3) {
				//
			}else if(state == 4) {
				//四角形ツール
			}else if(state == 5) {
				//ハッチング ツール
			}else if(state == 6) {
				//rulingツール
				if(cpx.size() >= 12 && cpx.size() != 0) {
					int hnum = heikatsu.size();
					//MyFileManager.MyWriteOutToSvg(hnum,cpx,cpy,rux,ruy,orix,oriy);
					MyFileManager.OutputForSimulatorSvg(creInfo,sqxlist, sqylist, cpx, cpy, rux, ruy, orix, oriy, straightx, straighty);
		    	}
			}
			//repaint();
		}else if(cmd == "another") {
			if(state == 6) {
				//rulingモード
				if(rux.size() > 0) {
					foldnum++;
					rulingsx.add(rux);
					rulingsy.add(ruy);
					orisenx.add(orix);
					oriseny.add(oriy);

					List<Boolean> tmp = new ArrayList<Boolean>();
					for(int i = 0; i < orix.size(); i++) {
						tmp.add(creInfo[i]);
					}
					foldingspatern.add(tmp);
				}

				//確定した入力曲線(ベジェ曲線)の制御点を格納するリスト
			  	cpx = new ArrayList<Integer>();
			  	cpy = new ArrayList<Integer>();
			  	//現在入力中の曲線の全座標を格納するリスト
			  	tmpx = new ArrayList<Integer>();
			  	tmpy = new ArrayList<Integer>();
			  	//確定前の暫定の曲線の制御点を格納するリスト
			  	alx = new ArrayList<Integer>();
			  	aly = new ArrayList<Integer>();
			  	//入力線の端点を一時的に保存するやつ
			  	tanx = new ArrayList<Integer>();
			  	tany = new ArrayList<Integer>();
			  	//保存されたやつを確定するやつ
			  	rinx = new ArrayList<Integer>();
			  	riny = new ArrayList<Integer>();
			  	//輪郭線の座標を保持するリスト
			  	coorinx = new ArrayList<Integer>();
			  	cooriny = new ArrayList<Integer>();
				rux = new ArrayList<Integer>();
				ruy = new ArrayList<Integer>();
				orix = new ArrayList<List<Integer>>();
				oriy = new ArrayList<List<Integer>>();
				repaint();
			}
		}else if(cmd == "onoff") {
			if(rdisplay == true) {
				rdisplay = false;
				onoff.setText("OFF");
			}else {
				rdisplay = true;
				onoff.setText("ON");
			}
			repaint();
		}else if(cmd == "displaycp") {
			if(displaycp == true) {
				displaycp = false;
				displayCP.setText("OFF");
			}else {
				displaycp = true;
				displayCP.setText("ON");
			}
			repaint();
		}else if(cmd == "st") {
			//直線ツール描画結果を表示するかどうか
			if(std == true) {
				std = false;
				st.setText("OFF");
			}else {
				std = true;
				st.setText("ON");
			}
			repaint();
		}else if(cmd == "fr") {
			//直線ツール描画結果を表示するかどうか
			if(frd == true) {
				frd = false;
				fr.setText("OFF");
			}else {
				frd = true;
				fr.setText("ON");
			}
			repaint();
		}else if(cmd == "cu") {
			//直線ツール描画結果を表示するかどうか
			if(cud == true) {
				cud = false;
				cu.setText("OFF");
			}else {
				cud = true;
				cu.setText("ON");
			}
			repaint();
		}else if(cmd == "sq") {
			//直線ツール描画結果を表示するかどうか
			if(sqd == true) {
				sqd = false;
				sq.setText("OFF");
			}else {
				sqd = true;
				sq.setText("ON");
			}
			repaint();
		}else if(cmd == "ha") {
			//直線ツール描画結果を表示するかどうか
			if(had == true) {
				had = false;
				ha.setText("OFF");
			}else {
				had = true;
				ha.setText("ON");
			}
			repaint();
		}else if(cmd == "ru") {
			//直線ツール描画結果を表示するかどうか
			if(rud == true) {
				rud = false;
				ru.setText("OFF");
			}else {
				rud = true;
				ru.setText("ON");
			}
			repaint();
		}else if(cmd == "c1") {
			if(creInfo[0] == true) {
				creInfo[0] = false;
				crease1.setText("1 : Valley Fold");
				crease1.setForeground(Color.BLUE);
			}else {
				creInfo[0] = true;
				crease1.setText("1 : Mountain Fold");
				crease1.setForeground(Color.RED);
			}
		}else if(cmd == "c2") {
			if(creInfo[1] == true) {
				creInfo[1] = false;
				crease2.setText("2 : Valley Fold");
				crease2.setForeground(Color.BLUE);
			}else {
				creInfo[1] = true;
				crease2.setText("2 : Mountain Fold");
				crease2.setForeground(Color.RED);
			}
		}else if(cmd == "c3") {
			if(creInfo[2] == true) {
				creInfo[2] = false;
				crease3.setText("3 : Valley Fold");
				crease3.setForeground(Color.BLUE);
			}else {
				creInfo[2] = true;
				crease3.setText("3 : Mountain Fold");
				crease3.setForeground(Color.RED);
			}
		}else if(cmd == "c4") {
			if(creInfo[3] == true) {
				creInfo[3] = false;
				crease4.setText("4 : Valley Fold");
				crease4.setForeground(Color.BLUE);
			}else {
				creInfo[3] = true;
				crease4.setText("4 : Mountain Fold");
				crease4.setForeground(Color.RED);
			}
		}else if(cmd == "c5") {
			if(creInfo[4] == true) {
				creInfo[4] = false;
				crease5.setText("5 : Valley Fold");
				crease5.setForeground(Color.BLUE);
			}else {
				creInfo[4] = true;
				crease5.setText("5 : Mountain Fold");
				crease5.setForeground(Color.RED);
			}
		}else if(cmd == "svg") {
			//svgへ出力
		}else if(cmd == "simulator") {
			//simulator用のsvgへ出力
			if(cpx.size() >= 12 && cpx.size() != 0) {
				int hnum = heikatsu.size();
				//MyFileManager.MyWriteOutToSvg(hnum,cpx,cpy,rux,ruy,orix,oriy);
				MyFileManager.OutputForSimulatorSvg(creInfo,sqxlist, sqylist, cpx, cpy, rux, ruy, orix, oriy, straightx, straighty);
	    	}
		}else if(cmd == "jpg") {
			//jpgファイルへ出力
		}else if(cmd == "png") {
			//pngファイルへ出力
		}else if(cmd == "eps") {
			//epsファイルへ出力
		}else if(cmd == "open") {
			//ファイルから読み込む
			//System.out.println("open");
			JFileChooser filechooser = new JFileChooser();
		    int selected = filechooser.showOpenDialog(this);
		    if (selected == JFileChooser.APPROVE_OPTION){
		      File file = filechooser.getSelectedFile();
		      clearAll();
		      MyFileManager.inputting(file.getPath(),cpx,cpy);
		      repaint();
		      //label.setText(file.getName());
		    }else if (selected == JFileChooser.CANCEL_OPTION){
		      //label.setText("キャンセルされました");
		    	System.out.println("キャンセルされました");
		    }else if (selected == JFileChooser.ERROR_OPTION){
		      //label.setText("エラー又は取消しがありました");
		    	System.out.println("エラーまたは取り消しがありました");
		    }
		}else if(cmd == "save") {
			//ファイルに書き出す
			JFileChooser filechooser = new JFileChooser();
		    int selected = filechooser.showSaveDialog(this);
		    if (selected == JFileChooser.APPROVE_OPTION){
		      File file = filechooser.getSelectedFile();
		      MyFileManager.OutputForTxt(file.getName(),straightx,straighty,straightColor,stLineWidth,
		    		  freex,freey,freeColor,fLineWidth,
		    		  curvepx,curvepy,curveColor,curveWidth,
		    		  sqxlist,sqylist,
		    		  hatchx,hatchy,hatchColor,hlineWidth,
		    		  cpx,cpy,rulingsx,rulingsy,orisenx,oriseny,foldingspatern,foldnum,creInfo);
		      //System.out.println(file.getName());
		      //label.setText(file.getName());
		    }else if (selected == JFileChooser.CANCEL_OPTION){
		    	System.out.println("キャンセルされました");
		      //label.setText("キャンセルされました");
		    }else if (selected == JFileChooser.ERROR_OPTION){
		      //label.setText("エラー又は取消しがありました");
		    	System.out.println("エラーまたは取り消しがありました");
		    }
		}else if(cmd == "clear") {
			clearAll();
		}else if(cmd == "other") {
			//メニューバーから画像変えれないかな？
			JFileChooser filechooser = new JFileChooser();
		    int selected = filechooser.showOpenDialog(this);
		    if (selected == JFileChooser.APPROVE_OPTION){
		      File file = filechooser.getSelectedFile();
		      p.remove(label);
		      label = new JLabel(new ImageIcon(file.getPath()));
		      label.setBounds(225,75,800,800);
		      p.add(label);
		      p.repaint();
		      repaint();
		    }else if (selected == JFileChooser.CANCEL_OPTION){
		      //label.setText("キャンセルされました");
		    	System.out.println("キャンセルされました");
		    }else if (selected == JFileChooser.ERROR_OPTION){
		      //label.setText("エラー又は取消しがありました");
		    	System.out.println("エラーまたは取り消しがありました");
		    }
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		if(state == 1) {
			//直線ツールモード
			if(stx.size() == 0) {
				stx.add(e.getX());
				sty.add(e.getY());
			}else if(stx.size() == 1){
				//stx = new ArrayList<Integer>();
				//sty = new ArrayList<Integer>();
				stx.add(e.getX());
				sty.add(e.getY());
			}else {
				stx = new ArrayList<Integer>();
				sty = new ArrayList<Integer>();
				stx.add(e.getX());
				sty.add(e.getY());
			}
			repaint();
		}else if(state == 3) {
			//
		}else if(state == 4) {
			//
		}else if(state == 5) {
			//
		}else if(state == 6) {
			//rulingツール
			int n = bnum/2; //編集するrulingの番号を決める変数
			if((e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0) {
				//左クリック
				n = MyHeikatsuka.returnRulingNum(e.getX(),e.getY(),heikatsu,cpx.get(0),cpy.get(0),cpx.get(1),cpy.get(1),cpx.get(2),cpy.get(2),cpx.get(3),cpy.get(3));
				heikatsu = MyHeikatsuka.heikatsukaAdd(n,heikatsu);
				//heikatsu = MyHeikatsuka.gokoHeikinAdd(n, heikatsu);
				System.out.println("左クリックを検出しました。");
				}else if((e.getModifiers() & MouseEvent.BUTTON2_MASK) != 0) {
				//中央クリック
				System.out.println("中央クリックを検出しました。");
			}else if((e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
				//右クリック
				n = MyHeikatsuka.returnRulingNum(e.getX(),e.getY(),heikatsu,cpx.get(0),cpy.get(0),cpx.get(1),cpy.get(1),cpx.get(2),cpy.get(2),cpx.get(3),cpy.get(3));
				heikatsu = MyHeikatsuka.heikatsukaSub(n,heikatsu);
				//heikatsu = MyHeikatsuka.gokoHeikinSub(n, heikatsu);
				System.out.println(n);
				System.out.println("右クリックを検出しました。");
			}else {
				//その他
			}
			repaint();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		Graphics g = getGraphics();
		if((e.getModifiers() & MouseEvent.BUTTON2_MASK) != 0) {
			//中央ボタン
		}else if((e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
			//右ボタン
			if(state == 3) {
				//
			}else if(state == 4) {
				//四角形ツール
			}else if(state == 5) {
				//ハッチング ツール
				Graphics2D g2 = (Graphics2D)g;
				BasicStroke bs = new BasicStroke(sw, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
				g2.setStroke(bs);

				MyHatching.polygonHatching1(hlineColor,g,myPolygon,hx,hy);
				myPolygon =  new Polygon();
			}else if(state == 6) {
				//rulingツール
			}
		}else {
			//左ボタン
			if(state == 3) {
				//曲線ツール
				tmpcurvex = new ArrayList<Integer>();
				tmpcurvey = new ArrayList<Integer>();
			}else if(state == 4) {
				//四角形ツール
				if((e.getModifiers() & MouseEvent.SHIFT_MASK) != 0) {
					shift = true;
					System.out.println("shift key pressed");
				}else {
					shift = false;
					System.out.println("shift key unpressed");
				}
				if(sqx.length == 2) {
					sqx = new int[2];
					sqy = new int[2];
					sqx[0] = e.getX();
					sqy[0] = e.getY();
				}else {
					sqx[0] = e.getX();
					sqy[0] = e.getY();
				}
			}else if(state == 5) {
				//ハッチング ツール
				int x = e.getX();
				int y = e.getY();
				myPolygon.addPoint(x, y);
			}else if(state == 6) {
				//rullingツール
				if(alx.size() > 0 && MyPaint.calcDist(e.getX(), e.getY(), alx.get(0), aly.get(0)) < 10) {
					selectnum = 1;
					selected = true;
				}else if(alx.size() > 0 && MyPaint.calcDist(e.getX(), e.getY(), alx.get(1), aly.get(1)) < 10) {
					selectnum = 2;
					selected = true;
				}else if(alx.size() > 0 && MyPaint.calcDist(e.getX(), e.getY(), alx.get(2), aly.get(2)) < 10) {
					selectnum = 3;
					selected = true;
				}else if(alx.size() > 0 && MyPaint.calcDist(e.getX(), e.getY(), alx.get(3), aly.get(3)) < 10) {
					selectnum = 4;
					selected = true;
				}else {
					tmpx = new ArrayList<Integer>();
					tmpy = new ArrayList<Integer>();

					//輪郭線についての部分
					tanx = new ArrayList<Integer>();
					tany = new ArrayList<Integer>();
					tanx.add(e.getX());
					tany.add(e.getY());
				}

				if(displaycp == true) {
					for(int i = 8; i < cpx.size(); i+=8) {
						if(MyPaint.calcDist(e.getX(), e.getY(), cpx.get(i), cpy.get(i)) < 10) {
							determinedCP = i;
							determined = true;
						}else if(MyPaint.calcDist(e.getX(), e.getY(), cpx.get(i+1), cpy.get(i+1)) < 10) {
							determinedCP = i+1;
							determined = true;
						}else if(MyPaint.calcDist(e.getX(), e.getY(), cpx.get(i+2), cpy.get(i+2)) < 10) {
							determinedCP = i+2;
							determined = true;
						}else if(MyPaint.calcDist(e.getX(), e.getY(), cpx.get(i+3), cpy.get(i+3)) < 10) {
							determinedCP = i+3;
							determined = true;
						}
					}
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		if(state == 2) {
			//自由曲線ツール
			repaint();
		}else if(state == 3) {
			//曲線ツール
			List<Integer> cplist = MyBezier.calcControlPoint(tmpcurvex, tmpcurvey, (int)MyBezier.calcListLength(tmpcurvex, tmpcurvey));
			//マウスが離れた時の処理
			//入力曲線を確定する処理
			if(cplist.size() == 4) {
				if(alcurvex.size() == 0) {
					//制御点1
					alcurvex.add(tmpcurvex.get(0));
					alcurvey.add(tmpcurvey.get(0));
					//制御点2
					alcurvex.add(cplist.get(0));
					alcurvey.add(cplist.get(1));
					//制御点3
					alcurvex.add(cplist.get(2));
					alcurvey.add(cplist.get(3));
					//制御点4
					alcurvex.add(tmpcurvex.get(tmpcurvex.size()-1));
					alcurvey.add(tmpcurvey.get(tmpcurvey.size()-1));
				}else {
					//制御点1
					alcurvex.set(0, (tmpcurvex.get(0)+alcurvex.get(0))/2);
					alcurvey.set(0, (tmpcurvey.get(0)+alcurvey.get(0))/2);
					//制御点2
					alcurvex.set(1, (cplist.get(0)+alcurvex.get(1))/2);
					alcurvey.set(1, (cplist.get(1)+alcurvey.get(1))/2);
					//制御点3
					alcurvex.set(2, (cplist.get(2)+alcurvex.get(2))/2);
					alcurvey.set(2, (cplist.get(3)+alcurvey.get(2))/2);
					//制御点4
					alcurvex.set(3, (tmpcurvex.get(tmpcurvex.size()-1)+alcurvex.get(3))/2);
					alcurvey.set(3, (tmpcurvey.get(tmpcurvey.size()-1)+alcurvey.get(3))/2);
				}
				repaint();
			}
		}else if(state == 4) {
			//四角形ツール
			sqx[1] = e.getX();
			sqy[1] = e.getY();
			repaint();
		}else if(state == 5) {
			//
		}else if(state == 6) {
			//rulingツール
			if(selected == true && selectnum != 0) {
				alx.remove(selectnum-1);
				aly.remove(selectnum-1);
				alx.add(selectnum-1, e.getX());
				aly.add(selectnum-1, e.getY());
				selected = false;
				repaint();
				selectnum = 0;
			}else if(determined == true && determinedCP < cpx.size()) {
				//cpx.remove(determinedCP);
				//cpy.remove(determinedCP);
				//cpx.add(determinedCP, e.getX());
				//cpy.add(determinedCP, e.getY());
				cpx.set(determinedCP, e.getX());
				cpy.set(determinedCP, e.getY());
				repaint();
				determined = false;
				determinedCP = 1000;
			}else {
				//輪郭線についての部分
				tanx.add(e.getX());
				tany.add(e.getY());

				List<Integer> cplist = MyBezier.calcControlPoint(tmpx, tmpy, (int)MyBezier.calcListLength(tmpx, tmpy));
				//マウスが離れた時の処理
				//入力曲線を確定する処理
				if(cplist.size() == 4) {
					if(alx.size() == 0) {
						//制御点1
						alx.add(tmpx.get(0));
						aly.add(tmpy.get(0));
						//制御点2
						alx.add(cplist.get(0));
						aly.add(cplist.get(1));
						//制御点3
						alx.add(cplist.get(2));
						aly.add(cplist.get(3));
						//制御点4
						alx.add(tmpx.get(tmpx.size()-1));
						aly.add(tmpy.get(tmpy.size()-1));
					}else {
						//制御点1
						alx.set(0, (tmpx.get(0)+alx.get(0))/2);
						aly.set(0, (tmpy.get(0)+aly.get(0))/2);
						//制御点2
						alx.set(1, (cplist.get(0)+alx.get(1))/2);
						aly.set(1, (cplist.get(1)+aly.get(1))/2);
						//制御点3
						alx.set(2, (cplist.get(2)+alx.get(2))/2);
						aly.set(2, (cplist.get(3)+aly.get(2))/2);
						//制御点4
						alx.set(3, (tmpx.get(tmpx.size()-1)+alx.get(3))/2);
						aly.set(3, (tmpy.get(tmpy.size()-1)+aly.get(3))/2);
					}
					repaint();
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		sw = wSlider.getValue();
		swButton.setText(String.valueOf(sw));
		System.out.println("Stroke Width = "+sw);
		bnum = rSlider.getValue();
		runumButton.setText(String.valueOf(bnum));
		heikatsu = new ArrayList<Double>();
		for(int i = 0; i < bnum; i++) {
			heikatsu.add(1.0/(bnum+1.0));
		}
		System.out.println("Bunkatsu num = "+bnum);
		repaint();
	}
}