package application3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class App3 extends JFrame implements MouseListener, ActionListener, MouseMotionListener, ChangeListener{
	BufferedImage bimg;
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

	//
	//Origami Simulatorへの入力
	List<Integer> rux = new ArrayList<Integer>();
	List<Integer> ruy = new ArrayList<Integer>();
	List<Integer> orix = new ArrayList<Integer>();
	List<Integer> oriy = new ArrayList<Integer>();
	//

	int bnum = 30; //rulingスタート地点の分割数
	//int counter = 0; //この値が0でない時、rulingの疎密が変更されているものとする
	List<Double> heikatsu = new ArrayList<Double>(); //平滑化の値を格納するリスト

	//スライダーの宣言
    JSlider slider = new JSlider(10,70,40);
    JButton displayButton;


    // Constructor
	public App3(String path, JPanel p) throws IOException{
    	super("Drawing Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = getContentPane();
        //ボタンの宣言
        JButton addButton = new JButton("追加");
        JButton deleteButton = new JButton("削除");
        //JButton displayButton = new JButton("表示");
        displayButton = new JButton("表示");
        //ボタン押された時の有効化
        addButton.addActionListener(this);
        addButton.setActionCommand("追加");
        deleteButton.addActionListener(this);
        deleteButton.setActionCommand("削除");
        displayButton.addActionListener(this);
        displayButton.setActionCommand("表示");


        //ファイル出力用のボタン
        JButton fileButton = new JButton("ファイル出力");
        fileButton.addActionListener(this);
        fileButton.setActionCommand("ファイル出力");


        //Jパネルの宣言とボタンの追加
        //JPanel p = new JPanel();
        p.setLayout(new FlowLayout(FlowLayout.LEADING));
        p.add(addButton);
        p.add(deleteButton);
        p.add(displayButton);
        //
        p.add(slider);

        p.add(fileButton);
        //
        contentPane.add(p, BorderLayout.BEFORE_FIRST_LINE);
        //Jパネルその2
        JPanel p2 = new JPanel();
        JLabel label1 = new JLabel("Editting is Blue.");
        JLabel label2 = new JLabel("Inputted is Black.");
        p2.setLayout(new FlowLayout(FlowLayout.LEADING));
        p2.add(label1);
        p2.add(label2);
        contentPane.add(p2, BorderLayout.AFTER_LAST_LINE);
        //背景画像の表示
        ImageIcon background = new ImageIcon(path);
        JLabel background2 = new JLabel(background);

        add(background2);
        pack();
        background2.setLayout(new FlowLayout());
        setVisible(true);
        addMouseListener(this);
        addMouseMotionListener(this);
        slider.addChangeListener(this);

        //スタート地点の分割の準備
        for(int i = 0; i < bnum; i++) {
        	heikatsu.add(1.0/(bnum+1.0));
        }
    }

    public void paint(Graphics g){
    	super.paint(g);

    	coorinx = new ArrayList<Integer>();
    	cooriny = new ArrayList<Integer>();

    	rux = new ArrayList<Integer>();
    	ruy = new ArrayList<Integer>();
    	orix = new ArrayList<Integer>();
    	oriy = new ArrayList<Integer>();

    	//制御線の補助線を表示させる部分
    	Color ori = new Color(150, 255, 150, 30);
    	g.setColor(ori);
    	MyRuling3.drawHojyoSeigyo(g, cpx, cpy);

    	//確定前の入力線を描画する部分
    	if(alx.size()!=0) {
    		g.setColor(Color.BLUE);
    		MyBezier3.calcBezier(alx,aly,g);
    	}

    	if(displayButton.getActionCommand().equals("表示")) {
    		//追加済みのcpからベジェ曲線を描画する部分
        	if(cpx.size()%4 == 0 && cpx.size() != 0) {
        		g.setColor(Color.BLACK);
        		MyBezier3.calcBezier(cpx, cpy, g);
        	}

        	//端点表示させるやつ
        	for(int i = 0; i < rinx.size(); i++) {
        		g.setColor(Color.RED);
        		g.drawOval(rinx.get(i)-2, riny.get(i)-2, 3, 3);
        	}

        	/*
        	//輪郭線を表示させる部分
        	if(cpx.size() >= 12 && (cpx.size()-4) % 8 == 0) {
        		if(cpx.size() == 12) {
        			g.setColor(Color.BLACK);
        			g.drawLine(rinx.get(0), riny.get(0), rinx.get(4), riny.get(4));
        			g.drawLine(rinx.get(1), riny.get(1), rinx.get(5), riny.get(5));
        			g.drawLine(rinx.get(4), riny.get(4), rinx.get(2), riny.get(2));
        			g.drawLine(rinx.get(5), riny.get(5), rinx.get(3), riny.get(3));

        			MyBezier.saveCooxy(coorinx,cooriny,rinx.get(0),riny.get(0),rinx.get(4),riny.get(4));
        			MyBezier.saveCooxy(coorinx,cooriny,rinx.get(1),riny.get(1),rinx.get(5),riny.get(5));
        			MyBezier.saveCooxy(coorinx,cooriny,rinx.get(4),riny.get(4),rinx.get(2),riny.get(2));
        			MyBezier.saveCooxy(coorinx,cooriny,rinx.get(5),riny.get(5),rinx.get(3),riny.get(3));
        		}else {
        			g.setColor(Color.BLACK);
        			g.drawLine(rinx.get(0), riny.get(0), rinx.get(4), riny.get(4));
        			g.drawLine(rinx.get(1), riny.get(1), rinx.get(5), riny.get(5));
        			g.drawLine(rinx.get(4), riny.get(4), rinx.get(2), riny.get(2));
        			g.drawLine(rinx.get(5), riny.get(5), rinx.get(3), riny.get(3));
        			MyBezier.saveCooxy(coorinx,cooriny,rinx.get(0),riny.get(0),rinx.get(4),riny.get(4));
        			MyBezier.saveCooxy(coorinx,cooriny,rinx.get(1),riny.get(1),rinx.get(5),riny.get(5));
        			MyBezier.saveCooxy(coorinx,cooriny,rinx.get(4),riny.get(4),rinx.get(2),riny.get(2));
        			MyBezier.saveCooxy(coorinx,cooriny,rinx.get(5),riny.get(5),rinx.get(3),riny.get(3));
        			for(int i = 0; i < (cpx.size()-20)/8+1; i++) {
        				g.setColor(Color.BLACK);
            			g.drawLine(rinx.get(2+i*4), riny.get(2+i*4), rinx.get(8+i*4), riny.get(8+i*4));
            			g.drawLine(rinx.get(3+i*4), riny.get(3+i*4), rinx.get(9+i*4), riny.get(9+i*4));
            			g.drawLine(rinx.get(8+i*4), riny.get(8+i*4), rinx.get(6+i*4), riny.get(6+i*4));
            			g.drawLine(rinx.get(9+i*4), riny.get(9+i*4), rinx.get(7+i*4), riny.get(7+i*4));
            			MyBezier.saveCooxy(coorinx,cooriny,rinx.get(2+i*4),riny.get(2+i*4),rinx.get(8+i*4),riny.get(8+i*4));
            			MyBezier.saveCooxy(coorinx,cooriny,rinx.get(3+i*4), riny.get(3+i*4), rinx.get(9+i*4), riny.get(9+i*4));
            			MyBezier.saveCooxy(coorinx,cooriny,rinx.get(8+i*4),riny.get(8+i*4),rinx.get(6+i*4),riny.get(6+i*4));
            			MyBezier.saveCooxy(coorinx,cooriny,rinx.get(9+i*4), riny.get(9+i*4), rinx.get(7+i*4), riny.get(7+i*4));
        			}
        		}
        	}
        	*/
    	}
    	//輪郭線を表示させる部分
    	if(cpx.size() >= 12 && (cpx.size()-4) % 8 == 0) {
    		if(cpx.size() == 12) {
    			if(displayButton.getActionCommand().equals("表示")) {
	    			g.setColor(Color.BLACK);
	    			g.drawLine(rinx.get(0), riny.get(0), rinx.get(4), riny.get(4));
	    			g.drawLine(rinx.get(1), riny.get(1), rinx.get(5), riny.get(5));
	    			g.drawLine(rinx.get(4), riny.get(4), rinx.get(2), riny.get(2));
	    			g.drawLine(rinx.get(5), riny.get(5), rinx.get(3), riny.get(3));
    			}
    			MyBezier3.saveCooxy(coorinx,cooriny,rinx.get(0),riny.get(0),rinx.get(4),riny.get(4));
    			MyBezier3.saveCooxy(coorinx,cooriny,rinx.get(1),riny.get(1),rinx.get(5),riny.get(5));
    			MyBezier3.saveCooxy(coorinx,cooriny,rinx.get(4),riny.get(4),rinx.get(2),riny.get(2));
    			MyBezier3.saveCooxy(coorinx,cooriny,rinx.get(5),riny.get(5),rinx.get(3),riny.get(3));
    		}else {
    			if(displayButton.getActionCommand().equals("表示")) {
	    			g.setColor(Color.BLACK);
	    			g.drawLine(rinx.get(0), riny.get(0), rinx.get(4), riny.get(4));
	    			g.drawLine(rinx.get(1), riny.get(1), rinx.get(5), riny.get(5));
	    			g.drawLine(rinx.get(4), riny.get(4), rinx.get(2), riny.get(2));
	    			g.drawLine(rinx.get(5), riny.get(5), rinx.get(3), riny.get(3));
    			}
    			MyBezier3.saveCooxy(coorinx,cooriny,rinx.get(0),riny.get(0),rinx.get(4),riny.get(4));
    			MyBezier3.saveCooxy(coorinx,cooriny,rinx.get(1),riny.get(1),rinx.get(5),riny.get(5));
    			MyBezier3.saveCooxy(coorinx,cooriny,rinx.get(4),riny.get(4),rinx.get(2),riny.get(2));
    			MyBezier3.saveCooxy(coorinx,cooriny,rinx.get(5),riny.get(5),rinx.get(3),riny.get(3));
    			for(int i = 0; i < (cpx.size()-20)/8+1; i++) {
    				if(displayButton.getActionCommand().equals("表示")) {
	    				g.setColor(Color.BLACK);
	        			g.drawLine(rinx.get(2+i*4), riny.get(2+i*4), rinx.get(8+i*4), riny.get(8+i*4));
	        			g.drawLine(rinx.get(3+i*4), riny.get(3+i*4), rinx.get(9+i*4), riny.get(9+i*4));
	        			g.drawLine(rinx.get(8+i*4), riny.get(8+i*4), rinx.get(6+i*4), riny.get(6+i*4));
	        			g.drawLine(rinx.get(9+i*4), riny.get(9+i*4), rinx.get(7+i*4), riny.get(7+i*4));
    				}
        			MyBezier3.saveCooxy(coorinx,cooriny,rinx.get(2+i*4),riny.get(2+i*4),rinx.get(8+i*4),riny.get(8+i*4));
        			MyBezier3.saveCooxy(coorinx,cooriny,rinx.get(3+i*4), riny.get(3+i*4), rinx.get(9+i*4), riny.get(9+i*4));
        			MyBezier3.saveCooxy(coorinx,cooriny,rinx.get(8+i*4),riny.get(8+i*4),rinx.get(6+i*4),riny.get(6+i*4));
        			MyBezier3.saveCooxy(coorinx,cooriny,rinx.get(9+i*4), riny.get(9+i*4), rinx.get(7+i*4), riny.get(7+i*4));
    			}
    		}
    	}

    	//ruling描画する部分(輪郭線も考慮)
    	if(cpx.size() >= 12 && cpx.size() != 0) {
    		g.setColor(Color.YELLOW);
    		//g.setColor(Color.GREEN);
    		MyRuling3.drawRuling2(g, cpx, cpy, coorinx, cooriny, heikatsu, rux, ruy, orix, oriy);
    	}
    }

    public static BufferedImage reSize1(BufferedImage image, int width, int height) {
    	BufferedImage thumb = new BufferedImage(width, height, image.getType());
    	thumb.getGraphics().drawImage(image.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING), 0, 0, width, height, null);
    	return thumb;
    }

    public void actionPerformed(ActionEvent e) {
    	String cmd = e.getActionCommand();
    	if(cmd.equals("追加")){
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
		}else if(cmd.equals("削除")) {
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
		}else if(cmd.equals("表示")) {
			displayButton.setText("非表示");
			displayButton.setActionCommand("非表示");
			repaint();
		}else if(cmd.equals("非表示")) {
			displayButton.setText("表示");
			displayButton.setActionCommand("表示");
			repaint();
		}else if(cmd.equals("ファイル出力")) {
			if(cpx.size() >= 12 && cpx.size() != 0) {
				int hnum = heikatsu.size();
				MyFileManager3.MyWriteOutToSvg(hnum,cpx,cpy,rux,ruy,orix,oriy);
	    	}
		}
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		int n = bnum/2; //編集するrulingの番号を決める変数
		if((e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0) {
			//左クリック
			n = MyHeikatsuka3.returnRulingNum(e.getX(),e.getY(),heikatsu,cpx.get(0),cpy.get(0),cpx.get(1),cpy.get(1),cpx.get(2),cpy.get(2),cpx.get(3),cpy.get(3));
			heikatsu = MyHeikatsuka3.heikatsukaAdd(n,heikatsu);
			//heikatsu = MyHeikatsuka.gokoHeikinAdd(n, heikatsu);
			System.out.println("左クリックを検出しました。");
			}else if((e.getModifiers() & MouseEvent.BUTTON2_MASK) != 0) {
			//中央クリック
			System.out.println("中央クリックを検出しました。");
		}else if((e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
			//右クリック
			n = MyHeikatsuka3.returnRulingNum(e.getX(),e.getY(),heikatsu,cpx.get(0),cpy.get(0),cpx.get(1),cpy.get(1),cpx.get(2),cpy.get(2),cpx.get(3),cpy.get(3));
			heikatsu = MyHeikatsuka3.heikatsukaSub(n,heikatsu);
			//heikatsu = MyHeikatsuka.gokoHeikinSub(n, heikatsu);
			System.out.println(n);
			System.out.println("右クリックを検出しました。");
		}else {
			//その他
		}
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		tmpx = new ArrayList<Integer>();
		tmpy = new ArrayList<Integer>();

		//輪郭線についての部分
		tanx = new ArrayList<Integer>();
		tany = new ArrayList<Integer>();
		tanx.add(e.getX());
		tany.add(e.getY());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//輪郭線についての部分
		tanx.add(e.getX());
		tany.add(e.getY());

		List<Integer> cplist = MyBezier3.calcControlPoint(tmpx, tmpy, (int)MyBezier3.calcListLength(tmpx, tmpy));
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

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		tmpx.add(e.getX());
		tmpy.add(e.getY());
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		bnum = slider.getValue();
		heikatsu = new ArrayList<Double>();
		for(int i = 0; i < bnum; i++) {
			heikatsu.add(1.0/(bnum+1.0));
		}
		System.out.println(bnum);
		repaint();
	}
}