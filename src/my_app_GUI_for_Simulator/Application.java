package my_app_GUI_for_Simulator;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
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


public class Application extends JFrame implements MouseListener, ActionListener, MouseMotionListener, ChangeListener{

	//直線ツール
		//2点結んだら、それを結んでくれるってやあつ
	  	List<List<Integer>> straightx = new ArrayList<List<Integer>>(); //確定後x座標
	  	List<List<Integer>> straighty = new ArrayList<List<Integer>>(); //確定後y座標
	  	List<Integer> stx = new ArrayList<Integer>(); //確定前x座標
	  	List<Integer> sty = new ArrayList<Integer>(); //確定前y座標
	  	List<Color> straightColor = new ArrayList<Color>(); //直線の際の色を記憶するリスト

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
		//確定前の制御点を変更する
		int selectnum = 0; //1~4
	    boolean selected = false;

		//Origami Simulatorへの入力
		List<Integer> rux = new ArrayList<Integer>();
		List<Integer> ruy = new ArrayList<Integer>();
		//List<Integer> orix = new ArrayList<Integer>();
		//List<Integer> oriy = new ArrayList<Integer>();
		List<List<Integer>> orix = new ArrayList<List<Integer>>();
		List<List<Integer>> oriy = new ArrayList<List<Integer>>();

		int bnum = 30; //rulingスタート地点の分割数
		List<Double> heikatsu = new ArrayList<Double>(); //平滑化の値を格納するリスト
		//スライダーの宣言
	    JSlider rSlider = new JSlider(10,70,40); //ruling用のやつ

	    int state = 0;
	    JLabel label; //imageicon用のやつ

	    //四角形ツールのやつ、2個あればいい
	    int[] sqx = new int[2];
	    int[] sqy = new int[2];
	    boolean shift = false;
	    //確定した四角形の4点を格納するリスト
	    List<int[]> sqxlist = new ArrayList<int[]>();
	    List<int[]> sqylist = new ArrayList<int[]>();

	    /*
	    //山折、谷折りのやーつ(state = 4と5)
	    //mount folding
	    List<Integer> mx = new ArrayList<Integer>();
		List<Integer> my = new ArrayList<Integer>();
		//現在入力中の曲線の全座標を格納するリスト
		List<Integer> tmpmx = new ArrayList<Integer>();
		List<Integer> tmpmy = new ArrayList<Integer>();
		//確定前の暫定の曲線の制御点を格納するリスト
		List<Integer> almx = new ArrayList<Integer>();
		List<Integer> almy = new ArrayList<Integer>();

		//valley foldiong
		List<Integer> vx = new ArrayList<Integer>();
		List<Integer> vy = new ArrayList<Integer>();
		//現在入力中の曲線の全座標を格納するリスト
		List<Integer> tmpvx = new ArrayList<Integer>();
		List<Integer> tmpvy = new ArrayList<Integer>();
		//確定前の暫定の曲線の制御点を格納するリスト
		List<Integer> alvx = new ArrayList<Integer>();
		List<Integer> alvy = new ArrayList<Integer>();
		*/
	    //折り線を記憶する部分
	    List<Integer> fx = new ArrayList<Integer>();
		List<Integer> fy = new ArrayList<Integer>();
		//現在入力中の曲線の全座標を格納するリスト
		List<Integer> tmpfx = new ArrayList<Integer>();
		List<Integer> tmpfy = new ArrayList<Integer>();
		//確定前の暫定の曲線の制御点を格納するリスト
		List<Integer> alfx = new ArrayList<Integer>();
		List<Integer> alfy = new ArrayList<Integer>();

		//マウスドラッグで制御点を変更するため
		int selectnum_crease = 0; //1~4
	    boolean selected_crease = false;

		//折り線の情報を記憶するリスト
		//山折りはtrue、谷折りはfalseとする。
		List<Boolean> fold_state = new ArrayList<Boolean>();

	Application(){
		super("Draw App for Origami Simulator");
		init();
	}

	void init() {
		setBounds(200,100,1350,900);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPane = getContentPane();
		JPanel p = new JPanel();
		p.setLayout(null);


		//以下、ボタンの宣言
		/*
		//state = 0
		JButton select = new JButton("");
		select.setBounds(0,300,100,100);
		select.setIcon(new ImageIcon("/Users/naruchan/desktop/ドローツールの元/アプリに使う(サイズ8080)/選択ツールの元8080.png"));
		select.setActionCommand("select");
		select.addActionListener(this);
		*/

		//state = 1
		JButton straightLine = new JButton("");
		straightLine.setBounds(0,100,100,100);
		straightLine.setIcon(new ImageIcon("/Users/naruchan/desktop/ドローツールの元/アプリに使う(サイズ8080)/直線ツールの元8080.png"));
		straightLine.setActionCommand("sline");
		straightLine.addActionListener(this);

		//state = 2
		JButton rulingLine = new JButton("");
		rulingLine.setBounds(0,200,100,100);
		rulingLine.setIcon(new ImageIcon("/Users/naruchan/desktop/ドローツールの元/アプリに使う(サイズ8080)/rulingの元8080.png"));
		rulingLine.setActionCommand("rline");
		rulingLine.addActionListener(this);

		//state = 3
		JButton sqLine = new JButton("");
		sqLine.setBounds(0,0,100,100);
		sqLine.setIcon(new ImageIcon("/Users/naruchan/desktop/ドローツールの元/アプリに使う(サイズ8080)/四角形の元8080.png"));
		sqLine.setActionCommand("sqline");
		sqLine.addActionListener(this);

		//state = 4
		JButton mfold = new JButton("Mount Fold");
		mfold.setBounds(0,300,100,100);
		mfold.setActionCommand("mf");
		mfold.addActionListener(this);
		//state = 4
		JButton vfold = new JButton("Valley Fold");
		vfold.setBounds(0,400,100,100);
		vfold.setActionCommand("vf");
		vfold.addActionListener(this);

		JButton addButton = new JButton("Add");
		//addButton.setBounds(210,0,100,50);
		addButton.setBounds(0,570,100,100);
		addButton.setActionCommand("add");
		addButton.addActionListener(this);

		JButton deleteButton = new JButton("Delete");
		deleteButton.setBounds(100,570,100,100);
		deleteButton.setActionCommand("delete");
		deleteButton.addActionListener(this);

		JButton colorChange = new JButton("Color");
		colorChange.setBounds(0,670,200,100);
		colorChange.setActionCommand("color");
		colorChange.addActionListener(this);

		JButton output = new JButton("Out");
		output.setBounds(0,770,200,100);
		output.setActionCommand("out");
		output.addActionListener(this);

		//rulingの本数を変更するスライダー
		rSlider.setBounds(0,500,200,100);
		rSlider.addChangeListener(this);
		JLabel rtext = new JLabel("Ruling Num");
		rtext.setBounds(60,500,200,50);

		//p.add(select);
		p.add(straightLine);
		p.add(rulingLine);
		p.add(sqLine);
		p.add(deleteButton);
		p.add(colorChange);
		p.add(output);
		p.add(addButton);
		p.add(rSlider);
		p.add(rtext);

		p.add(mfold);
		p.add(vfold);


		//rulingスタート地点分割の準備
		for(int i = 0; i < bnum; i++) {
			heikatsu.add(1.0/(bnum+1.0));
		}

		/*
		//写真
		label = new JLabel(new ImageIcon("/Users/naruchan/javaworks/processingcodes/sample_images/test9.jpg"));
		label.setBounds(350,50,800,800);
		p.add(label);
		*/

		/*
		//白いやつ
		label = new JLabel(new ImageIcon("/Users/naruchan/desktop/ドローツールの元/背景に使えるのでは.png"));
		label.setBounds(350,50,850,800);
		p.add(label);
		*/


		/*
		label = new JLabel(new ImageIcon("/Users/naruchan/desktop/ドローツールの元/展開図テスト.png"));
		label.setBounds(350,50,850,800);
		p.add(label);
		*/

		/*
		label = new JLabel(new ImageIcon("/Users/naruchan/desktop/シミュレート結果とか/ch2一本の曲線を折る[展開図]/シミュレート結果その２/展開図なんか.png"));
		label.setBounds(350,50,850,800);
		p.add(label);
		*/

		/*
		//複雑な展開図
		label = new JLabel(new ImageIcon("/Users/naruchan/desktop/ドローツールの元/col_uf.png"));
		label.setBounds(350,50,850,800);
		p.add(label);
		*/

		/*
		//色々な展開図
		label = new JLabel(new ImageIcon("/Users/naruchan/desktop/シミュレート結果とか/ch3曲線を並べる[展開図]/あああ/ああ.png"));
		label.setBounds(350,50,850,800);
		p.add(label);
		*/

		/*
		//色々な展開図
		label = new JLabel(new ImageIcon("/Users/naruchan/desktop/シミュレート結果とか/ch5折り込む[展開図]/展開図その１/折り込みを持つ展開図.png"));
		label.setBounds(350,50,850,800);
		p.add(label);
		*/

		//色々な展開図
		label = new JLabel(new ImageIcon("/Users/naruchan/desktop/曲線2本.png"));
		label.setBounds(350,50,850,800);
		p.add(label);

		contentPane.add(p);
		setVisible(true);
		addMouseListener(this);
        addMouseMotionListener(this);
	}

	//色の宣言
	Color sqColor = new Color(0,0,0,200);
	Color selectColor = new Color(155,155,155);
	Color slineColor = new Color(155,155,155);
	Color rlineColor = new Color(155,255,155);
	Color mfColor = new Color(255,100,100,200);
	Color vfColor = new Color(100,100,255,200);
	public void paint(Graphics g) {
		super.paint(g);
		//rulingモード時の輪郭の座標を格納するリスト
		coorinx = new ArrayList<Integer>();
    	cooriny = new ArrayList<Integer>();
    	//rulingモードの書き出しに必要なリスト
    	rux = new ArrayList<Integer>();
    	ruy = new ArrayList<Integer>();
    	//orix = new ArrayList<Integer>(); //前のやつorix
    	//oriy = new ArrayList<Integer>(); //前のやつoriy

    	orix = new ArrayList<List<Integer>>();
    	oriy = new ArrayList<List<Integer>>();

    	//g.setColor(selectColor);
    	g.setColor(sqColor);
    	g.fillRect(103, 26, 93, 93);
    	g.setColor(slineColor);
		g.fillRect(103, 126, 93, 93);
		g.setColor(rlineColor);
		g.fillRect(103, 226, 93, 93);
		/*
		g.setColor(selectColor);
		g.fillRect(103, 326, 93, 93);
		g.setColor(mfColor);
		g.fillRect(103, 426, 93, 46);
		g.setColor(vfColor);
		g.fillRect(103, 473, 93, 46);
		*/
		g.setColor(mfColor);
		g.fillRect(103, 326, 93, 93);
		g.setColor(vfColor);
		g.fillRect(103, 426, 93, 93);


		g.setColor(Color.BLACK);
		g.drawRect(103, 26, 93, 93);
		g.drawRect(103, 126, 93, 93);
		g.drawRect(103, 226, 93, 93);
		g.drawRect(103, 326, 93, 93);
		g.drawRect(103, 426, 93, 93);

		if(state == 0) {
			//select mode
			//MyPaint.drawTriangle(g, 198, 370);
			//MyPaint.drawTriangle(g, 198, 70);
		}else if(state == 1) {
			//straight line mode
			MyPaint.drawTriangle(g, 198, 170);
		}else if(state == 2) {
			//ruling mode
			MyPaint.drawTriangle(g, 198, 270);
		}else if(state == 3) {
			//mount folding mode
			MyPaint.drawTriangle(g, 198, 70);
			//MyPaint.drawTriangle(g, 198, 370);
		}else if(state == 4) {
			//valley folding mode
			MyPaint.drawTriangle(g, 198, 370);
		}else if(state == 5) {
			MyPaint.drawTriangle(g, 198, 470);
		}

		//ちょい太くしておこうかな
		Graphics2D g2 = (Graphics2D)g;
        //super.paint(g2);
        BasicStroke BStroke = new BasicStroke(3);
        g2.setStroke(BStroke);
		//
		//先に書いている理由としては、下書きになるため。このうえにrulingの入力線が乗るため。
		//


        //確定前の折り線を描画
        if(alfx.size() != 0) {
        	g.setColor(Color.gray);
        	MyBezier.calcBezier(alfx, alfy, g);
        	//制御点の描画
        	g.setColor(Color.red);
        	for(int i = 0; i < alfx.size(); i++)
        		g.drawOval(alfx.get(i), alfy.get(i), 3, 3);
        }
        //確定後の折り線を描画
        if(fx.size()%4 == 0 && fx.size() != 0) {
        	for(int i = 0; i < fold_state.size(); i++) {
        		if(fold_state.get(i) == true) {
        			g.setColor(mfColor);
        		}else {
        			g.setColor(vfColor);
        		}
        		MyBezier.drawBezier(g2, fx.get(i*4+0), fy.get(i*4+0), fx.get(i*4+1), fy.get(i*4+1), fx.get(i*4+2), fy.get(i*4+2), fx.get(i*4+3), fy.get(i*4+3));
        	}
        }

        /*
		//state = 4 山折り線の描画
    	//確定前の入力線を描画する部分
    	if(almx.size()!=0) {
    		g.setColor(Color.gray);
    		MyBezier.calcBezier(almx,almy,g);
    	}
    	//確定後
    	if(mx.size()%4 == 0 && mx.size() != 0) {
    		g.setColor(mfColor);
    		MyBezier.calcBezier(mx, my, g);
    	}

    	//state = 5 谷折り線の描画
    	//確定前の入力線を描画する部分
    	if(alvx.size()!=0) {
    		g.setColor(Color.gray);
    		MyBezier.calcBezier(alvx,alvy,g);
    	}
    	//確定後
    	if(vx.size()%4 == 0 && vx.size() != 0) {
    		g.setColor(vfColor);
    		MyBezier.calcBezier(vx, vy, g);
    	}
    	*/

    	//state = 3 (確定前の)四角形の描画
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
    		g.setColor(sqColor);
    		for(int i = 0; i < sqxlist.size(); i++) {
    			int[] x = sqxlist.get(i);
    			int[] y = sqylist.get(i);
    			MyPaint.drawShikakuFromFourPoint(g, x, y);
    		}
    	}

    	BStroke = new BasicStroke(1);
        g2.setStroke(BStroke);


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
				g.setColor(straightColor.get(i));
				g.drawLine(straightx.get(i).get(0), straighty.get(i).get(0), straightx.get(i).get(1), straighty.get(i).get(1));
			}
		}

		//state = 2
		//制御線の補助線を表示させる部分
    	//Color ori = new Color(150, 255, 150, 30);
		Color ori = new Color(200, 150, 120, 50);
    	g.setColor(ori);
    	MyRuling.drawHojyoSeigyo(g, cpx, cpy);

    	//確定前の入力線とその制御点を描画する部分
    	if(alx.size()!=0) {
    		g.setColor(Color.BLUE);
    		MyBezier.calcBezier(alx,aly,g);
    		for(int i = 0; i < alx.size(); i++) {
    			g.setColor(Color.RED);
    			g.drawOval(alx.get(i), aly.get(i), 3, 3);
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
    	}else {
    		//追加済みのcpからベジェ曲線を描画する部分
        	if(cpx.size()%4 == 0 && cpx.size() != 0) {
        		g.setColor(Color.BLACK);
        		MyBezier.calcBezier(cpx, cpy, g);
        	}
    	}

    	//ruling描画する部分(輪郭線も考慮)
    	if(cpx.size() >= 12 && cpx.size() != 0) {
    		MyRuling.drawRuling2(rlineColor,g, cpx, cpy, coorinx, cooriny, heikatsu, rux, ruy, orix, oriy);
    	}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		if(state == 2) {
			//rulingモード
			bnum = rSlider.getValue();
			heikatsu = new ArrayList<Double>();
			for(int i = 0; i < bnum; i++) {
				heikatsu.add(1.0/(bnum+1.0));
			}
			System.out.println(bnum);
			repaint();
		}
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		if(state == 2) {
			//rulingモード
			tmpx.add(e.getX());
			tmpy.add(e.getY());
		}else if(state == 3) {
			//repaint();
			sqx[1] = e.getX();
			sqy[1] = e.getY();
			repaint();
		}else if(state == 4) {
			//MF mode
			//tmpmx.add(e.getX());
			//tmpmy.add(e.getY());
			tmpfx.add(e.getX());
			tmpfy.add(e.getY());
		}else if(state == 5) {
			//VF mode
			//tmpvx.add(e.getX());
			//tmpvy.add(e.getY());
			tmpfx.add(e.getX());
			tmpfy.add(e.getY());
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
		if(cmd == "select") {
			if(state != 0)
				state = 0;
			repaint();
		}else if(cmd == "sline") {
			if(state != 1)
				state = 1;
			repaint();
		}else if(cmd == "rline") {
			if(state != 2)
				state = 2;
			repaint();
		}else if(cmd == "sqline"){
			if(state != 3)
				state = 3;
			repaint();
		}else if(cmd == "mf") {
			if(state != 4)
				state = 4;
			repaint();
		}else if(cmd == "vf") {
			if(state != 5)
				state = 5;
			repaint();
		}else if(cmd == "add") {
			//if(state != 3)
				//state = 3;
			//repaint();

			if(state == 1) {
				//直線ツール
				if(stx.size() == 2) {
					straightx.add(stx);
					straighty.add(sty);
					straightColor.add(slineColor);
					//stLineWidth.add(sw);
					stx = new ArrayList<Integer>();
					sty = new ArrayList<Integer>();
				}
				repaint();
			}else if(state == 2) {
				//rulingツール
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
			}else if(state == 3) {
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
			}else if(state == 4) {
				//m f
				/*
				for(int i = 0; i < almx.size(); i++) {
	        		mx.add(almx.get(i));
	        		my.add(almy.get(i));
	        	}
	        	almx = new ArrayList<Integer>();
	        	almy = new ArrayList<Integer>();
	        	*/
				if(alfx.size() > 0) {
					for(int i = 0; i < alfx.size(); i++) {
		        		fx.add(alfx.get(i));
		        		fy.add(alfy.get(i));
		        	}
		        	alfx = new ArrayList<Integer>();
		        	alfy = new ArrayList<Integer>();
		        	fold_state.add(true);
		        	repaint();
				}
			}else if(state == 5) {
				//v f
				/*
				for(int i = 0; i < alvx.size(); i++) {
	        		vx.add(alvx.get(i));
	        		vy.add(alvy.get(i));
	        	}
	        	alvx = new ArrayList<Integer>();
	        	alvy = new ArrayList<Integer>();
	        	*/
				if(alfx.size() > 0) {
					for(int i = 0; i < alfx.size(); i++) {
		        		fx.add(alfx.get(i));
		        		fy.add(alfy.get(i));
		        	}
		        	alfx = new ArrayList<Integer>();
		        	alfy = new ArrayList<Integer>();
		        	fold_state.add(false);
		        	repaint();
				}
			}

		}else if(cmd == "delete") {
			//if(state != 4)
				//state = 4;
			//repaint();

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
				  	}
				  	straightx = tmpx;
				  	straighty = tmpy;
				  	straightColor = tmpc;
				}
				repaint();
			}else if(state == 2) {
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
			}else if(state == 3) {
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
			}else if(state == 4) {
				//m f
				/*
				if(mx.size() > 0) {
					ArrayList<Integer> tmpx = new ArrayList<Integer>();
					ArrayList<Integer> tmpy = new ArrayList<Integer>();
					for(int i = 0; i < mx.size() - 4; i++) {
						tmpx.add(mx.get(i));
						tmpy.add(my.get(i));
					}
					mx = tmpx;
					my = tmpy;

					//折り線の判定の部分
					ArrayList<Boolean> bool = new ArrayList<Boolean>();
					for(int i = 0; i < fold_state.size() - 1; i++) {
						bool.add(fold_state.get(i));
					}
					fold_state = bool;

					repaint();
				}
				*/
				if(fx.size() > 0) {
					ArrayList<Integer> tmpx = new ArrayList<Integer>();
					ArrayList<Integer> tmpy = new ArrayList<Integer>();
					for(int i = 0; i < fx.size() - 4; i++) {
						tmpx.add(fx.get(i));
						tmpy.add(fy.get(i));
					}
					fx = tmpx;
					fy = tmpy;

					//折り線の判定の部分
					ArrayList<Boolean> bool = new ArrayList<Boolean>();
					for(int i = 0; i < fold_state.size() - 1; i++) {
						bool.add(fold_state.get(i));
					}
					fold_state = bool;
					alfx = new ArrayList<Integer>();
		        	alfy = new ArrayList<Integer>();

					repaint();
				}
			}else if(state == 5) {
				//v f
				/*
				if(vx.size() > 0) {
					ArrayList<Integer> tmpx = new ArrayList<Integer>();
					ArrayList<Integer> tmpy = new ArrayList<Integer>();
					for(int i = 0; i < vx.size() - 4; i++) {
						tmpx.add(vx.get(i));
						tmpy.add(vy.get(i));
					}
					vx = tmpx;
					vy = tmpy;

					//折り線の判定の部分
					ArrayList<Boolean> bool = new ArrayList<Boolean>();
					for(int i = 0; i < fold_state.size() - 1; i++) {
						bool.add(fold_state.get(i));
					}
					fold_state = bool;

					repaint();
				}
				*/
				if(fx.size() > 0) {
					ArrayList<Integer> tmpx = new ArrayList<Integer>();
					ArrayList<Integer> tmpy = new ArrayList<Integer>();
					for(int i = 0; i < fx.size() - 4; i++) {
						tmpx.add(fx.get(i));
						tmpy.add(fy.get(i));
					}
					fx = tmpx;
					fy = tmpy;

					//折り線の判定の部分
					ArrayList<Boolean> bool = new ArrayList<Boolean>();
					for(int i = 0; i < fold_state.size() - 1; i++) {
						bool.add(fold_state.get(i));
					}
					fold_state = bool;
					alfx = new ArrayList<Integer>();
		        	alfy = new ArrayList<Integer>();

					repaint();
				}
			}
		}else if(cmd == "color") {
			JColorChooser colorchooser = new JColorChooser();
			Color color = JColorChooser.showDialog(this, "Selecting Colors", Color.white);
			if(state == 0) {
				//選択
				selectColor = color;
			}else if(state == 1) {
				//直線
				slineColor = color;
			}else if(state == 2) {
				//ruling
				rlineColor = color;
			}else if(state == 3) {
				//add??
				sqColor = color;
			}else if(state == 4) {
				//mount folding
				mfColor = color;
			}else if(state == 5) {
				//valley folding
				vfColor = color;
			}
			repaint();
		}else if(cmd == "out") {
			MyFileManager.OutputForSimulatorSvg(fold_state,sqxlist, sqylist, cpx, cpy, rux, ruy, orix, oriy, straightx, straighty);
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
		}else if(state == 2) {
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
			if(state == 2) {
			}
		}else {
			//左ボタン
			if(state == 2) {
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
			}else if(state == 3) {
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
			}else if(state == 4 || state == 5) {
				//mf mode
				/*
				tmpmx = new ArrayList<Integer>();
				tmpmy = new ArrayList<Integer>();
				*/
				if(alfx.size() > 0 && MyPaint.calcDist(e.getX(), e.getY(), alfx.get(0), alfy.get(0)) < 10) {
					selectnum_crease = 1;
					selected_crease = true;
				}else if(alfx.size() > 0 && MyPaint.calcDist(e.getX(), e.getY(), alfx.get(1), alfy.get(1)) < 10) {
					selectnum_crease = 2;
					selected_crease = true;
				}else if(alfx.size() > 0 && MyPaint.calcDist(e.getX(), e.getY(), alfx.get(2), alfy.get(2)) < 10) {
					selectnum_crease = 3;
					selected_crease = true;
				}else if(alfx.size() > 0 && MyPaint.calcDist(e.getX(), e.getY(), alfx.get(3), alfy.get(3)) < 10) {
					selectnum_crease = 4;
					selected_crease = true;
				}else {
				tmpfx = new ArrayList<Integer>();
				tmpfy = new ArrayList<Integer>();
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		if(state == 2) {
			//rulingモード
			if(selected == true && selectnum != 0) {
				alx.remove(selectnum-1);
				aly.remove(selectnum-1);
				alx.add(selectnum-1, e.getX());
				aly.add(selectnum-1, e.getY());
				selected = false;
				repaint();
				selectnum = 0;
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
		}else if(state == 3) {
			sqx[1] = e.getX();
			sqy[1] = e.getY();
			repaint();
		}else if(state == 4) {
			/*
			//mf mode
			List<Integer> cplist = MyBezier.calcControlPoint(tmpmx, tmpmy, (int)MyBezier.calcListLength(tmpmx, tmpmy));
			//マウスが離れた時の処理
			//入力曲線を確定する処理
			if(cplist.size() == 4) {
				if(almx.size() == 0) {
					//制御点1
					almx.add(tmpmx.get(0));
					almy.add(tmpmy.get(0));
					//制御点2
					almx.add(cplist.get(0));
					almy.add(cplist.get(1));
					//制御点3
					almx.add(cplist.get(2));
					almy.add(cplist.get(3));
					//制御点4
					almx.add(tmpmx.get(tmpmx.size()-1));
					almy.add(tmpmy.get(tmpmy.size()-1));
				}else {
					//制御点1
					almx.set(0, (tmpmx.get(0)+almx.get(0))/2);
					almy.set(0, (tmpmy.get(0)+almy.get(0))/2);
					//制御点2
					almx.set(1, (cplist.get(0)+almx.get(1))/2);
					almy.set(1, (cplist.get(1)+almy.get(1))/2);
					//制御点3
					almx.set(2, (cplist.get(2)+almx.get(2))/2);
					almy.set(2, (cplist.get(3)+almy.get(2))/2);
					//制御点4
					almx.set(3, (tmpmx.get(tmpmx.size()-1)+almx.get(3))/2);
					almy.set(3, (tmpmy.get(tmpmy.size()-1)+almy.get(3))/2);
				}
				repaint();
			}
			*/
			if(selected_crease == true && selectnum_crease != 0) {
				alfx.remove(selectnum_crease-1);
				alfy.remove(selectnum_crease-1);
				alfx.add(selectnum_crease-1, e.getX());
				alfy.add(selectnum_crease-1, e.getY());
				selected_crease = false;
				repaint();
				selectnum_crease = 0;
			}else {
				List<Integer> cplist = MyBezier.calcControlPoint(tmpfx, tmpfy, (int)MyBezier.calcListLength(tmpfx, tmpfy));
				//マウスが離れた時の処理
				//入力曲線を確定する処理
				if(cplist.size() == 4) {
					if(alfx.size() == 0) {
						//制御点1
						alfx.add(tmpfx.get(0));
						alfy.add(tmpfy.get(0));
						//制御点2
						alfx.add(cplist.get(0));
						alfy.add(cplist.get(1));
						//制御点3
						alfx.add(cplist.get(2));
						alfy.add(cplist.get(3));
						//制御点4
						alfx.add(tmpfx.get(tmpfx.size()-1));
						alfy.add(tmpfy.get(tmpfy.size()-1));
					}else {
						//制御点1
						alfx.set(0, (tmpfx.get(0)+alfx.get(0))/2);
						alfy.set(0, (tmpfy.get(0)+alfy.get(0))/2);
						//制御点2
						alfx.set(1, (cplist.get(0)+alfx.get(1))/2);
						alfy.set(1, (cplist.get(1)+alfy.get(1))/2);
						//制御点3
						alfx.set(2, (cplist.get(2)+alfx.get(2))/2);
						alfy.set(2, (cplist.get(3)+alfy.get(2))/2);
						//制御点4
						alfx.set(3, (tmpfx.get(tmpfx.size()-1)+alfx.get(3))/2);
						alfy.set(3, (tmpfy.get(tmpfy.size()-1)+alfy.get(3))/2);
					}
					repaint();
				}
			}
		}else if(state == 5) {
			//vf mode
			/*
			List<Integer> cplist = MyBezier.calcControlPoint(tmpvx, tmpvy, (int)MyBezier.calcListLength(tmpvx, tmpvy));
			//マウスが離れた時の処理
			//入力曲線を確定する処理
			if(cplist.size() == 4) {
				if(alvx.size() == 0) {
					//制御点1
					alvx.add(tmpvx.get(0));
					alvy.add(tmpvy.get(0));
					//制御点2
					alvx.add(cplist.get(0));
					alvy.add(cplist.get(1));
					//制御点3
					alvx.add(cplist.get(2));
					alvy.add(cplist.get(3));
					//制御点4
					alvx.add(tmpvx.get(tmpvx.size()-1));
					alvy.add(tmpvy.get(tmpvy.size()-1));
				}else {
					//制御点1
					alvx.set(0, (tmpvx.get(0)+alvx.get(0))/2);
					alvy.set(0, (tmpvy.get(0)+alvy.get(0))/2);
					//制御点2
					alvx.set(1, (cplist.get(0)+alvx.get(1))/2);
					alvy.set(1, (cplist.get(1)+alvy.get(1))/2);
					//制御点3
					alvx.set(2, (cplist.get(2)+alvx.get(2))/2);
					alvy.set(2, (cplist.get(3)+alvy.get(2))/2);
					//制御点4
					alvx.set(3, (tmpvx.get(tmpvx.size()-1)+alvx.get(3))/2);
					alvy.set(3, (tmpvy.get(tmpvy.size()-1)+alvy.get(3))/2);
				}
				repaint();
			}
			*/
			if(selected_crease == true && selectnum_crease != 0) {
				alfx.remove(selectnum_crease-1);
				alfy.remove(selectnum_crease-1);
				alfx.add(selectnum_crease-1, e.getX());
				alfy.add(selectnum_crease-1, e.getY());
				selected_crease = false;
				repaint();
				selectnum_crease = 0;
			}else {
				List<Integer> cplist = MyBezier.calcControlPoint(tmpfx, tmpfy, (int)MyBezier.calcListLength(tmpfx, tmpfy));
				//マウスが離れた時の処理
				//入力曲線を確定する処理
				if(cplist.size() == 4) {
					if(alfx.size() == 0) {
						//制御点1
						alfx.add(tmpfx.get(0));
						alfy.add(tmpfy.get(0));
						//制御点2
						alfx.add(cplist.get(0));
						alfy.add(cplist.get(1));
						//制御点3
						alfx.add(cplist.get(2));
						alfy.add(cplist.get(3));
						//制御点4
						alfx.add(tmpfx.get(tmpfx.size()-1));
						alfy.add(tmpfy.get(tmpfy.size()-1));
					}else {
						//制御点1
						alfx.set(0, (tmpfx.get(0)+alfx.get(0))/2);
						alfy.set(0, (tmpfy.get(0)+alfy.get(0))/2);
						//制御点2
						alfx.set(1, (cplist.get(0)+alfx.get(1))/2);
						alfy.set(1, (cplist.get(1)+alfy.get(1))/2);
						//制御点3
						alfx.set(2, (cplist.get(2)+alfx.get(2))/2);
						alfy.set(2, (cplist.get(3)+alfy.get(2))/2);
						//制御点4
						alfx.set(3, (tmpfx.get(tmpfx.size()-1)+alfx.get(3))/2);
						alfy.set(3, (tmpfy.get(tmpfy.size()-1)+alfy.get(3))/2);
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

	/*
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		int keycode = e.getKeyCode();
		if(keycode == KeyEvent.VK_SHIFT) {
			shift = true;
		}
		System.out.println("true");
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		int keycode = e.getKeyCode();
		if(keycode == KeyEvent.VK_SHIFT) {
			shift = false;
		}
		System.out.println("false");
	}
	*/
}
