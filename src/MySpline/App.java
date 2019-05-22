package MySpline;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class App extends JFrame  implements MouseListener,MouseMotionListener,ActionListener{
	JPanel p;
	List<Integer> linex = new ArrayList<Integer>();
	List<Integer> liney = new ArrayList<Integer>();

	List<Integer> tmpx = new ArrayList<Integer>();
	List<Integer> tmpy = new ArrayList<Integer>();

	App(){
		super("spline sample");
		init();
	}

	void init(){
		setBounds(0,0,1000,1000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addMouseMotionListener(this);
		addMouseListener(this);
		setVisible(true);
   }

	public void paint(Graphics g) {
		super.paint(g);
		if(linex.size() > 0) {
			for(int i = 0; i < linex.size(); i++) {
				g.drawOval(linex.get(i), liney.get(i), 3, 3);
			}
		}
		System.out.println(linex.size());
	}

	void splineCP(List<Integer> a, List<Integer> b, List<Integer> c, List<Integer> d) {
		//a,bに、c,dから求めたcpを格納する(8個とする)
		int i = c.size();
		double sum = 0;
		for(int j = 1; j < i; j++) {
			sum += Math.sqrt(Math.pow(c.get(j) - c.get(j-1), 2) + Math.pow(d.get(j) - d.get(j-1), 2));
		}

		int dist = (int)sum/7;
		int counter = 0;
		double sum2 = 0;

		for(int k = 1; k < i; k++) {
			if((int)dist*counter+5 > (int)sum2 && (int)dist*counter-5 < (int)sum2){
				counter++;
				a.add(c.get(k));
				b.add(d.get(k));
			}
			sum2 += Math.sqrt(Math.pow(c.get(k) - c.get(k-1), 2) + Math.pow(d.get(k) - d.get(k-1), 2));
		}
	}

@Override
public void actionPerformed(ActionEvent e) {
	// TODO 自動生成されたメソッド・スタブ

}

@Override
public void mouseDragged(MouseEvent e) {
	// TODO 自動生成されたメソッド・スタブ
	tmpx.add(e.getX());
	tmpy.add(e.getY());
}

@Override
public void mouseMoved(MouseEvent e) {
	// TODO 自動生成されたメソッド・スタブ

}

@Override
public void mouseClicked(MouseEvent e) {
	// TODO 自動生成されたメソッド・スタブ

}

@Override
public void mousePressed(MouseEvent e) {
	// TODO 自動生成されたメソッド・スタブ
	tmpx.add(e.getX());
	tmpy.add(e.getY());
}

@Override
public void mouseReleased(MouseEvent e) {
	// TODO 自動生成されたメソッド・スタブ
	tmpx.add(e.getX());
	tmpy.add(e.getY());
	splineCP(linex,liney,tmpx,tmpy);
	repaint();

	tmpx = new ArrayList<Integer>();
	tmpy = new ArrayList<Integer>();
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