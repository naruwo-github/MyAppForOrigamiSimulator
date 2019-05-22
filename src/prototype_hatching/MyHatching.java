package prototype_hatching;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MyHatching extends JFrame implements MouseListener, ActionListener, MouseMotionListener, ChangeListener{
	Polygon myPolygon;
	int statenum = 0;
	JButton hatching1;
	JButton hatching2;
	JButton hatching3;
	public MyHatching() {
		super("Sample Hatching Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = getContentPane();
        JPanel p = new JPanel();
        p.setLayout(null);

        myPolygon = new Polygon();
		setSize(800,800);

		hatching1 = new JButton("Hatching1");
		hatching1.setBounds(0, 0, 100, 100);
		hatching1.setActionCommand("hatch1");
		hatching1.addActionListener(this);
		p.add(hatching1);

		hatching2 = new JButton("Hatching2");
		hatching2.setBounds(100, 0, 100, 100);
		hatching2.setActionCommand("hatch2");
		hatching2.addActionListener(this);
		p.add(hatching2);

		hatching3 = new JButton("Hatching3");
		hatching3.setBounds(0, 100, 100, 100);
		hatching3.setActionCommand("hatch3");
		hatching3.addActionListener(this);
		p.add(hatching3);

		//setContentPane(new JLabel(new ImageIcon("/Users/naruchan/desktop/yubi.png")));
		//pack();
		//setContentPane(new JLabel(new ImageIcon("/Users/naruchan/desktop/underdrawing1.png")));
		//pack();
		//setContentPane(new JLabel(new ImageIcon("/Users/naruchan/desktop/万線・ハッチング の元.png")));
		//pack();
		contentPane.add(p);
		setVisible(true);
        addMouseListener(this);
        addMouseMotionListener(this);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		Graphics g = getGraphics();
		int x = e.getX();
		int y = e.getY();
		myPolygon.addPoint(x, y);
		//g.drawOval(x, y, 3, 3);
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		String cmd = e.getActionCommand();
		if(cmd.equals("hatch1")) {
			if(statenum != 1) {
				statenum = 1;
				hatching1.setBackground(Color.GREEN);
				System.out.println(statenum);
			}else {
				statenum = 0;
				hatching1.setBackground(null);
				System.out.println(statenum);
			}
		}else if(cmd.equals("hatch2")) {
			//縦線
			//
		}else if(cmd.equals("hatch3")) {
			//塗りつぶし
			if(statenum != 3) {
				statenum = 3;
				System.out.println(statenum);
			}else {
				statenum = 0;
				System.out.println(statenum);
			}
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		Graphics g = getGraphics();
		if((e.getModifiers() & MouseEvent.BUTTON2_MASK) != 0) {
			//中央ボタン
		}else if((e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {

			//repaint(); //丸を消すため
			//右ボタン
			MyGraphicGraph gGraph = new MyGraphicGraph(g);
			if(statenum == 0) {
				gGraph.polygonHatching0(myPolygon);
			}else if(statenum == 1) {
				gGraph.polygonHatching1(myPolygon);
			}else if(statenum == 2) {
				gGraph.polygonHatching2(myPolygon);
			}else if(statenum == 3) {
				gGraph.polygonHatching3(myPolygon);
			}

			//MyGraphicGraph gGraph = new MyGraphicGraph(g);
			gGraph.polygonHatching1(myPolygon);
			myPolygon =  new Polygon();
		}else {
			//左ボタン
			int x = e.getX();
			int y = e.getY();
			myPolygon.addPoint(x, y);
			//g.drawOval(x, y, 3, 3);
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}
}
