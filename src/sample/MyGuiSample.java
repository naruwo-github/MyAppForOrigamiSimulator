package sample;

import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MyGuiSample extends JFrame implements MouseListener, ActionListener, MouseMotionListener, ChangeListener{
	int width = 1000;
	int height = 800;
	JLabel label;
	public static void main(String[] args) {
		//MyGuiSample app = new MyGuiSample();
		new MyGuiSample();
	}

	MyGuiSample(){
		super("Drawing Application Sample");
		init();
	}

	//Constructor
	private void init(){
		setBounds(200,100,1200,800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container contentPane = getContentPane();

		JButton select = new JButton("");
		select.setIcon(new ImageIcon("/Users/naruchan/アプリ素材の画像/カーソル画像8080.png"));
		JButton sline = new JButton("");
		sline.setIcon(new ImageIcon("/Users/naruchan/アプリ素材の画像/直線画像8080.jpg"));
		JButton cline = new JButton("");
		cline.setIcon(new ImageIcon("/Users/naruchan/アプリ素材の画像/曲線画像8080.jpg"));
		JButton rline = new JButton("");
		rline.setIcon(new ImageIcon("/Users/naruchan/アプリ素材の画像/集中線画像8080.jpg"));
		JButton undo = new JButton("");
		undo.setIcon(new ImageIcon("/Users/naruchan/アプリ素材の画像/アンドゥ画像8080.png"));

		JButton color = new JButton("Color");
		JButton stroke = new JButton("Stroke");
		JButton writeOut = new JButton("Write Out");

		select.setBounds(0,0,100,100);
		sline.setBounds(100,0,100,100);
		cline.setBounds(100,100,100,100);
		rline.setBounds(100,200,100,100);
		undo.setBounds(0,100,100,100);

		color.setBounds(0,400,200,100);
		stroke.setBounds(0,500,200,100);
		writeOut.setBounds(0,600,200,100);

		JPanel p1 = new JPanel();
		p1.setLayout(null);
		//p1.setLayout(new FlowLayout(FlowLayout.LEFT));
		//p2.setLayout(new FlowLayout(FlowLayout.RIGHT));
		p1.add(select);
		p1.add(sline);
		p1.add(cline);
		p1.add(rline);
		p1.add(undo);

		p1.add(color);
		p1.add(stroke);
		p1.add(writeOut);


		color.setActionCommand("Color");
		color.addActionListener(this);
		writeOut.setActionCommand("Write Out");
		writeOut.addActionListener(this);

		JMenuBar menuBar = new JMenuBar();
		JMenu menu1 = new JMenu("File");
		JMenu menu2 = new JMenu("Edit");

		JMenuItem menuitem1 = new JMenuItem("New");
		JMenuItem menuitem2 = new JMenuItem("Open");
		JMenuItem menuitem3 = new JMenuItem("Close");
		menuitem2.setActionCommand("Open");
		menuitem2.addActionListener(this);

		JMenuItem menuitem4 = new JMenuItem("Undo");
		JMenuItem menuitem5 = new JMenuItem("Save");
		JMenuItem menuitem6 = new JMenuItem("Save as ...");

		menuBar.add(menu1);
		menuBar.add(menu2);
		menu1.add(menuitem1);
		menu1.add(menuitem2);
		menu1.add(menuitem3);

		menu2.add(menuitem4);
		menu2.add(menuitem5);
		menu2.add(menuitem6);

		setJMenuBar(menuBar);

		//contentPane.add(p1, BorderLayout.PAGE_START);
		label = new JLabel(new ImageIcon("/Users/naruchan/javaworks/processingcodes/sample_images/test9.jpg"));
		label.setBounds(200, 0, width, height);
		p1.add(label);
		contentPane.add(p1);
		setVisible(true);
		addMouseListener(this);
        addMouseMotionListener(this);
	}

	ImageIcon resizeIcon(ImageIcon preicon) {
		int hei = 800;
		int wid = 1000;
		double a = 1.0;
		ImageIcon posticon;
		Image postImg = preicon.getImage().getScaledInstance((int) (preicon.getIconWidth() * a), -1,
		        Image.SCALE_SMOOTH);
		//縦が800、横が1000として比べて
		int newWidth = preicon.getIconWidth();
		int newHeight = preicon.getIconHeight();
		System.out.println(newHeight);
		//画像が大きいとき
		if(newWidth > wid && newHeight > hei) {
			//縦横共に大きい時
			int saWidth = newWidth - wid;
			int saHeight = newHeight - hei;
			if(saWidth > saHeight) {
				a = wid/newWidth;
				postImg = preicon.getImage().getScaledInstance((int) (preicon.getIconWidth() * a), -1,
				        Image.SCALE_SMOOTH);
			}else {
				a = hei/newHeight;
				postImg = preicon.getImage().getScaledInstance((int) (preicon.getIconHeight() * a), -1,
				        Image.SCALE_SMOOTH);
			}
		}else if(newWidth > wid && newHeight <= hei) {
			//横が大きく、縦は同じか小さい場合
			a = wid/newWidth;
			postImg = preicon.getImage().getScaledInstance((int) (preicon.getIconWidth() * a), -1,
			        Image.SCALE_SMOOTH);
		}else if(newHeight > hei && newWidth <= wid) {
			//縦が大きく、横は同じか小さい場合
			a = hei/newHeight;
			postImg = preicon.getImage().getScaledInstance((int) (preicon.getIconHeight() * a), -1,
			        Image.SCALE_SMOOTH);
		}else {
			//両方とも小さい時
			int saWidth = wid - newWidth;
			int saHeight = hei - newHeight;
			if(saWidth < saHeight) {
				a = wid/newWidth;
				postImg = preicon.getImage().getScaledInstance((int) (preicon.getIconWidth() * a), -1,
				        Image.SCALE_SMOOTH);
			}else {
				a = hei/newHeight;
				postImg = preicon.getImage().getScaledInstance((int) (preicon.getIconHeight() * a), -1,
				        Image.SCALE_SMOOTH);
			}
		}

		postImg = preicon.getImage().getScaledInstance((int) (preicon.getIconWidth() * a), -1,
		        Image.SCALE_SMOOTH);
		posticon = new ImageIcon(postImg);
		return posticon;
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
	public void actionPerformed(ActionEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		if(e.getActionCommand() == "Color") {
			JColorChooser colorchooser = new JColorChooser();
			Color color = JColorChooser.showDialog(this, "色の選択", Color.white);
			//
		}else if(e.getActionCommand() == "Write Out") {
			JOptionPane.showMessageDialog(null, "Output was clearly completed.", "Message", getDefaultCloseOperation());
			//
		}else if(e.getActionCommand() == "Open") {
			JFileChooser fc = new JFileChooser();
			//画像ファイルの拡張子を設定
			fc.setFileFilter(new FileNameExtensionFilter("画像ファイル","png","jpg","Jpeg","GIF","bmp"));
			//ファイル選択ダイアログを表示
			if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				File f = fc.getSelectedFile();
				//アイコンをラベルに設定
				ImageIcon icon = new ImageIcon(f.getPath());
				//int newWidth = icon.getIconWidth();
				//int newHeight = icon.getIconHeight();
				//ここでiconのサイズを小さくすればいいんじゃないかな
				ImageIcon resized = resizeIcon(icon);
				label.setIcon(resized);
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

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}
}