package my_app_GUI;

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
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



public class MyGui extends JFrame implements MouseListener, ActionListener, MouseMotionListener, ChangeListener{
	//状態を記憶する変数
	//0から4の5種類で行こう
	int state = 0;
	JLabel label; //imageicon用のやつ
	Polygon myPolygon = new Polygon(); //ハッチング用のポリゴン

	//スライダーの宣言
    JSlider rSlider = new JSlider(10,70,40); //ruling用のやつ
    JSlider wSlider = new JSlider(1,9,1); //太さのやつ
    int sw = 1;

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

	//直線ツール
	//2点結んだら、それを結んでくれるってやあつ
  	List<List<Integer>> straightx = new ArrayList<List<Integer>>(); //確定後x座標
  	List<List<Integer>> straighty = new ArrayList<List<Integer>>(); //確定後y座標
  	List<Integer> stx = new ArrayList<Integer>(); //確定前x座標
  	List<Integer> sty = new ArrayList<Integer>(); //確定前y座標
  	List<Color> straightColor = new ArrayList<Color>(); //直線の際の色を記憶するリスト
  	List<Integer> stLineWidth = new ArrayList<Integer>(); //直線の太さを記憶するリスト

  	//自由曲線ツール
    List<List<Integer>> freex = new ArrayList<List<Integer>>(); //確定後x座標
    List<List<Integer>> freey = new ArrayList<List<Integer>>(); //確定後y座標
    List<Integer> fx = new ArrayList<Integer>(); //確定前x座標
    List<Integer> fy = new ArrayList<Integer>(); //確定前y座標
    List<Color> freeColor = new ArrayList<Color>(); //自由曲線の色を記憶するリスト
    List<Integer> fLineWidth = new ArrayList<Integer>(); //自由曲線の太さを記憶するリスト


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
	List<Integer> orix = new ArrayList<Integer>();
	List<Integer> oriy = new ArrayList<Integer>();

	int bnum = 30; //rulingスタート地点の分割数
	List<Double> heikatsu = new ArrayList<Double>(); //平滑化の値を格納するリスト

    //ハッチング の際に描画した部分を格納する2重リスト
  	List<List<Integer>> hatchx = new ArrayList<List<Integer>>(); //確定後x座標
  	List<List<Integer>> hatchy = new ArrayList<List<Integer>>(); //確定後y座標
  	List<Integer> hx = new ArrayList<Integer>(); //確定前x座標
  	List<Integer> hy = new ArrayList<Integer>(); //確定前y座標
  	List<Color> hatchColor = new ArrayList<Color>(); //ハッチング の際の色を記憶するリスト

	MyGui(){
		super("Drawing Application");
		init();
	}

	void init() {
		setBounds(200,100,1300,970);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container contentPane = getContentPane();
		contentPane.setBackground(Color.BLACK);
		JPanel p = new JPanel();
		p.setLayout(null);

		JButton straightLine = new JButton("");
		straightLine.setBounds(0,0,100,100);
		straightLine.setIcon(new ImageIcon("/Users/naruchan/desktop/ドローツールの元/アプリに使う(サイズ8080)/直線ツールの元8080.png"));
		straightLine.setActionCommand("sline");
		straightLine.addActionListener(this);

		JButton freeLine = new JButton("");
		freeLine.setBounds(0,100,100,100);
		freeLine.setIcon(new ImageIcon("/Users/naruchan/desktop/ドローツールの元/アプリに使う(サイズ8080)/自由曲線ツールの元8080.png"));
		freeLine.setActionCommand("fline");
		freeLine.addActionListener(this);

		JButton rulingLine = new JButton("");
		rulingLine.setBounds(0,200,100,100);
		rulingLine.setIcon(new ImageIcon("/Users/naruchan/desktop/ドローツールの元/アプリに使う(サイズ8080)/rulingの元8080.png"));
		rulingLine.setActionCommand("rline");
		rulingLine.addActionListener(this);

		JButton hatchLine = new JButton("");
		hatchLine.setBounds(0,300,100,100);
		hatchLine.setIcon(new ImageIcon("/Users/naruchan/desktop/ドローツールの元/アプリに使う(サイズ8080)/万線ハッチングの元8080.png"));
		hatchLine.setActionCommand("hline");
		hatchLine.addActionListener(this);

		JButton addButton = new JButton("Add");
		addButton.setBounds(0,400,100,100);
		addButton.setActionCommand("add");
		addButton.addActionListener(this);

		JButton deleteButton = new JButton("Delete");
		deleteButton.setBounds(100,400,100,100);
		deleteButton.setActionCommand("delete");
		deleteButton.addActionListener(this);

		JButton colorChange = new JButton("Color");
		colorChange.setBounds(0,500,200,100);
		colorChange.setActionCommand("color");
		colorChange.addActionListener(this);

		JButton output = new JButton("Out");
		output.setBounds(0,600,200,100);
		output.setActionCommand("out");
		output.addActionListener(this);

		JButton outline = new JButton("OutLine");
		outline.setBounds(0,850,150,50);
		JButton cp = new JButton("ControlPoint");
		cp.setBounds(0,900,150,50);

		//太さを変更するスライダー
		wSlider.setBounds(0,730,200,50);
		wSlider.addChangeListener(this);
		JLabel wtext = new JLabel("Stroke Width");
		wtext.setBounds(60,700,200,50);
		//rulingの本数を変更するスライダー
		rSlider.setBounds(0,800,200,50);
		rSlider.addChangeListener(this);
		JLabel rtext = new JLabel("Ruling Num");
		rtext.setBounds(60,770,200,50);

		//rulingの表示、非表示を切り替えるボタン
		onoff = new JButton("ON");
		onoff.setBounds(150,850,50,50);
		onoff.setActionCommand("onoff");
		onoff.addActionListener(this);

		//制御点の表示、非表示を切り替えるボタン
		displayCP = new JButton("ON");
		displayCP.setBounds(150,900,50,50);
		displayCP.setActionCommand("displaycp");
		displayCP.addActionListener(this);

		p.add(straightLine);
		p.add(freeLine);
		p.add(rulingLine);
		p.add(hatchLine);
		p.add(deleteButton);
		p.add(colorChange);
		p.add(output);

		p.add(addButton);
		p.add(wSlider);
		p.add(wtext);
		p.add(rSlider);
		p.add(rtext);

		p.add(onoff);
		p.add(displayCP);

		p.add(outline);
		p.add(cp);

		//rulingスタート地点分割の準備
		for(int i = 0; i < bnum; i++) {
			heikatsu.add(1.0/(bnum+1.0));
		}


		/*
		label = new JLabel(new ImageIcon("/Users/naruchan/javaworks/processingcodes/sample_images/test9.jpg"));
		label.setBounds(350,50,800,800);
		p.add(label);
		*/

		/*
		//単純な曲線を折った展開図
		label = new JLabel(new ImageIcon("/Users/naruchan/desktop/折り紙シミュレータ/単純な曲線を折ったやつ/展開図.png"));
		label.setBounds(350,50,800,800);
		p.add(label);
		*/

		/*
		label = new JLabel(new ImageIcon("/Users/naruchan/desktop/蛇行展開図.png"));
		label.setBounds(350,50,800,800);
		p.add(label);
		*/

		label = new JLabel(new ImageIcon("/Users/naruchan/desktop/蛇行2本.png"));
		label.setBounds(350,50,800,800);
		p.add(label);

		/*
		label = new JLabel(new ImageIcon("/Users/naruchan/desktop/曲線折り紙展開図(取扱注意)/chap01_曲線を折るということ/01_08_単純な曲線を折った様子.jpg"));
		label.setBounds(350,50,800,800);
		p.add(label);
		*/

		/*
		label = new JLabel(new ImageIcon("/Users/naruchan/javaworks/processingcodes/sample_images/mitanis_work1.jpg"));
		//label = new JLabel(new ImageIcon("/Users/naruchan/javaworks/processingcodes/sample_images/mitanis_work2.jpg"));
		label.setBounds(350,50,800,800);
		p.add(label);
		*/

		/*
		//普通のやつ
		label = new JLabel(new ImageIcon("/Users/naruchan/desktop/ドローツール入力用曲線折り紙作品の写真画像/作品の写真画像(リサイズ)/単純な曲線を折った様子.jpg"));
		label.setBounds(350,50,850,800);
		p.add(label);
		*/

		/*
		//白紙
		label = new JLabel(new ImageIcon("/Users/naruchan/desktop/白紙.png"));
		label.setBounds(350,50,850,800);
		p.add(label);
		*/

		/*
		//互い違い
		label = new JLabel(new ImageIcon("/Users/naruchan/desktop/互い違いの弧.png"));
		label.setBounds(350,50,850,800);
		p.add(label);
		*/

		/*
		//蛇行するやつ
		label = new JLabel(new ImageIcon("/Users/naruchan/desktop/ドローツール入力用曲線折り紙作品の写真画像/作品の写真画像(リサイズ)/蛇行する曲面を折った様子_表.jpg"));
		label.setBounds(350,50,850,800);
		p.add(label);
		*/

		/*
		//単純な曲線を二つ持つやつ
		label = new JLabel(new ImageIcon("/Users/naruchan/desktop/ドローツール入力用曲線折り紙作品の写真画像/作品の写真画像(リサイズ)/単純な曲線を2つ並べたもの.jpeg"));
		label.setBounds(350,50,850,800);
		p.add(label);
		*/

		/*
		//単純な曲線を二つ持つやつその2
		label = new JLabel(new ImageIcon("/Users/naruchan/desktop/ドローツール入力用曲線折り紙作品の写真画像/作品の写真画像(リサイズ)/単純な曲線を2つ並べたもの２.jpeg"));
		label.setBounds(350,50,850,800);
		p.add(label);
		*/

		/*
		//蛇行曲線を二つ持つやつ
		label = new JLabel(new ImageIcon("/Users/naruchan/desktop/ドローツール入力用曲線折り紙作品の写真画像/作品の写真画像(リサイズ)/蛇行する曲線を段折りにした様子.jpg"));
		label.setBounds(350,50,850,800);
		p.add(label);
		*/

		contentPane.add(p);
		setVisible(true);
		addMouseListener(this);
        addMouseMotionListener(this);
	}

	//Color select = new Color(255,150,150);
	Color slineColor = new Color(100,100,255);
	Color flineColor = new Color(255,100,100);
	Color rlineColor = new Color(100,255,100);
	Color hlineColor = new Color(100,100,100);

	public void paint(Graphics g){
		super.paint(g);

		Graphics2D g2 = (Graphics2D)g;
		//両端を丸く
		BasicStroke bs1 = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		g2.setStroke(bs1);

		//rulingモード時の輪郭の座標を格納するリスト
		coorinx = new ArrayList<Integer>();
    	cooriny = new ArrayList<Integer>();
    	//rulingモードの書き出しに必要なリスト
    	rux = new ArrayList<Integer>();
    	ruy = new ArrayList<Integer>();
    	orix = new ArrayList<Integer>();
    	oriy = new ArrayList<Integer>();

    	g.setColor(slineColor);
		g.fillRect(103, 26, 93, 93);
		g.setColor(flineColor);
		g.fillRect(103, 126, 93, 93);
		g.setColor(rlineColor);
		g.fillRect(103, 226, 93, 93);
		g.setColor(hlineColor);
		g.fillRect(103, 326, 93, 93);

		g.setColor(Color.BLACK);
		//g.drawRect(103, 26, 93, 93);
		g.drawRect(103, 26, 93, 93);
		g.drawRect(103, 126, 93, 93);
		g.drawRect(103, 226, 93, 93);
		g.drawRect(103, 326, 93, 93);

		BasicStroke bs2 = new BasicStroke(sw, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		g2.setStroke(bs2);

		if(state == 0) {
			//MyPaint.drawTriangle(g,198,70);
		}else if(state == 1) { //選択ツール
			MyPaint.drawTriangle(g,198,70);
		}else if(state == 2) { //直線ツール
			MyPaint.drawTriangle(g,198,170);
		}else if(state == 3) { //rulingツール
			MyPaint.drawTriangle(g,198,270);
		}else if(state == 4) { //ハッチング ツール
			MyPaint.drawTriangle(g,195,370);
		}

		//とりあえず全てを描画しよう
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

		bs2 = new BasicStroke(sw, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		g2.setStroke(bs2);
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

		bs2 = new BasicStroke(sw, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		g2.setStroke(bs2);
		//state == 3 のやつ
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

    	/*
    	//追加済みのcpからベジェ曲線を描画する部分
    	if(cpx.size()%4 == 0 && cpx.size() != 0) {
    		g.setColor(Color.BLACK);
    		MyBezier.calcBezier(cpx, cpy, g);
    	}

    	//端点表示させるやつ
    	for(int i = 0; i < rinx.size(); i++) {
    		g.setColor(Color.RED);
    		g.drawOval(rinx.get(i)-2, riny.get(i)-2, 3, 3);
    	}
    	*/

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

        	/*
        	//端点表示させるやつ
        	for(int i = 0; i < rinx.size(); i++) {
        		g.setColor(Color.RED);
        		g.drawOval(rinx.get(i)-2, riny.get(i)-2, 3, 3);
        	}
        	*/
    	}

    	//ruling描画する部分(輪郭線も考慮)
    	if(cpx.size() >= 12 && cpx.size() != 0) {
    		g.setColor(Color.YELLOW);
    		//g.setColor(Color.GREEN);
    		MyRuling.drawRuling2(rlineColor,g, cpx, cpy, coorinx, cooriny, heikatsu, rux, ruy, orix, oriy);
    	}

    	bs2 = new BasicStroke(sw, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		g2.setStroke(bs2);
    	//state == 4のやつ
    	if(hatchx.size() > 0 && hatchy.size() > 0 && hatchx.size() == hatchy.size()) {
			//記憶したハッチング を描画する
			//System.out.println("aaaaaaaaa");
			//System.out.println(hatchColor.size());
			for(int i = 0; i < hatchColor.size(); i++) {
				g.setColor((Color)hatchColor.get(i));
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
    	//
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		if(state == 2) {
			//自由曲線モード
			fx.add(e.getX());
			fy.add(e.getY());
		}else if(state == 3) {
			//rulingモード
			tmpx.add(e.getX());
			tmpy.add(e.getY());

		}else if(state == 4) {
			//ハッチング モード
			int x = e.getX();
			int y = e.getY();
			myPolygon.addPoint(x, y);
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
		}else if(cmd == "rline") {
			if(state != 3)
				state = 3;
			repaint();
		}else if(cmd == "hline") {
			if(state != 4)
				state = 4;
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
			}else if(state == 4) {
				//ハッチング モード
				//System.out.println("hx'size = "+hx.size()+" , hy's size = "+hy.size());
				if(hx.size() > 0 && hy.size() > 0 && hx.size() == hy.size()) {
					hatchx.add((ArrayList<Integer>)hx);
					hatchy.add((ArrayList<Integer>)hy);
					hatchColor.add((Color)hlineColor);
				}
				hx = new ArrayList<Integer>();
				hy = new ArrayList<Integer>();
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
				//rulingモード
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
			}else if(state == 4) {
				//ハッチング モード
				if(hatchx.size() > 0) {
					List<Color> tmpa = new ArrayList<Color>();
					List<List<Integer>> tmpb = new ArrayList<List<Integer>>();
				  	List<List<Integer>> tmpc = new ArrayList<List<Integer>>();
					for(int i = 0; i < hatchColor.size() - 1; i++) {
						tmpa.add(hatchColor.get(i));
						tmpb.add(hatchx.get(i));
						tmpc.add(hatchy.get(i));
					}
					hatchColor = tmpa;
					hatchx = tmpb;
					hatchy = tmpc;
				}
				repaint();
				/*
			  	List<List<Integer>> hatchx = new ArrayList<List<Integer>>(); //確定後x座標
			  	List<List<Integer>> hatchy = new ArrayList<List<Integer>>(); //確定後y座標
			  	List<Integer> hx = new ArrayList<Integer>(); //確定前x座標
			  	List<Integer> hy = new ArrayList<Integer>(); //確定前y座標
			  	List<Color> hatchColor = new ArrayList<Color>(); //ハッチング の際の色を記憶するリスト
			  	*/

			}
			//repaint();
		}else if(cmd == "color") {
			JColorChooser colorchooser = new JColorChooser();
			Color color = JColorChooser.showDialog(this, "Selecting Colors", Color.white);
			if(state == 0) {
				//select = color;
			}else if(state == 1) {
				slineColor = color;
			}else if(state == 2) {
				flineColor = color;
			}else if(state == 3) {
				rlineColor = color;
			}else if(state == 4) {
				hlineColor = color;
			}
			repaint();
		}else if(cmd == "swidth") {
			//repaint();
		}else if(cmd == "out") {
			//ファイル出力
			if(state == 3) {
				//rulingモード
				if(cpx.size() >= 12 && cpx.size() != 0) {
					int hnum = heikatsu.size();
					MyFileManager.MyWriteOutToSvg(hnum,cpx,cpy,rux,ruy,orix,oriy);
		    	}
			}else if(state == 4) {
				//ハッチング モード
			}
			//repaint();
		}else if(cmd == "onoff") {
			if(rdisplay == true) {
				rdisplay = false;
				onoff.setText("OFF");
			}else {
				rdisplay = true;
				onoff.setText("ON");
			}
		}else if(cmd == "displaycp") {
			if(displaycp == true) {
				displaycp = false;
				displayCP.setText("OFF");
			}else {
				displaycp = true;
				displayCP.setText("ON");
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
			//rulingモード
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
				//rulingモード
			}else if(state == 4) {
				Graphics2D g2 = (Graphics2D)g;
				BasicStroke bs = new BasicStroke(sw, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
				g2.setStroke(bs);
				//ハッチング モード
				MyHatching.polygonHatching1(hlineColor,g,myPolygon,hx,hy);
				myPolygon =  new Polygon();
			}
		}else {
			//左ボタン
			if(state == 3) {
				//rulingモード
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
					/*
					for(int i = cpx.size() - 4; i < cpx.size(); i++) {
						if(MyPaint.calcDist(e.getX(), e.getY(), cpx.get(i), cpy.get(i)) < 10) {
							determinedCP = i;
							determined = true;
						}
					}
					*/
				}
			}else if(state == 4) {
				int x = e.getX();
				int y = e.getY();
				myPolygon.addPoint(x, y);
				//g.drawRect(x, y, 3, 3);
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
			//rulingモード
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
		}else if(state == 4) {
			//ハッチング モード
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
		System.out.println("Stroke Width = "+sw);
		/*
		Graphics g = getGraphics();
		Graphics2D g2 = (Graphics2D)g;
		//両端を丸く
		BasicStroke bs1 = new BasicStroke(sw, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		g2.setStroke(bs1);
		*/

		if(state == 3) {
			//rulingモード
			bnum = rSlider.getValue();
			heikatsu = new ArrayList<Double>();
			for(int i = 0; i < bnum; i++) {
				heikatsu.add(1.0/(bnum+1.0));
			}
			System.out.println("Bunkatsu num = "+bnum);
			repaint();
		}else if(state == 4) {
			//ハッチング モード
		}
		repaint();
	}

}
