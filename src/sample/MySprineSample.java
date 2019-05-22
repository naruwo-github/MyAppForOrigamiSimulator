package sample;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MySprineSample extends JFrame  implements MouseListener,MouseMotionListener,ActionListener{
	JPanel p;

	MySprineSample(){
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

	public static void main(String[] args) {
		MySprineSample app = new MySprineSample();
	}

@Override
public void actionPerformed(ActionEvent e) {
	// TODO 自動生成されたメソッド・スタブ

}

@Override
public void mouseDragged(MouseEvent e) {
	// TODO 自動生成されたメソッド・スタブ

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