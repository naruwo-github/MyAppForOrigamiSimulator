package sample;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class SampleMenu extends JFrame implements ActionListener{
	static JPanel p = new JPanel();
	public static void main(String[] args) {
		BufferedImage bimg;
		//String filepath;
		//static String path = "/Users/naruchan/javaworks/processingcodes/sample_images/test4.jpg";
	    //static String path = "/Users/naruchan/javaworks/processingcodes/sample_images/test5.jpg";
	    //static String path = "/Users/naruchan/javaworks/processingcodes/sample_images/test6.jpg";
		//static String path = "/Users/naruchan/javaworks/processingcodes/sample_images/test7.jpg";
	    //static String path = "/Users/naruchan/javaworks/processingcodes/sample_images/test8.jpg";
	    //String path = "/Users/naruchan/javaworks/processingcodes/sample_images/test9.jpg";
		//JPanel p = new JPanel();
		SampleMenu frame = new SampleMenu("サンプルメニュー",p);
		frame.setVisible(true);
	}

	SampleMenu(String title, JPanel p){
		setTitle(title);
		setBounds(100, 100, 700, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//メニューバーとそのアイテムを設定
		JMenuBar menubar = new JMenuBar();
		JMenu menu1 = new JMenu("File");
		menubar.add(menu1);
		JMenuItem menuitem2 = new JMenuItem("Open");
		menu1.add(menuitem2);
		menuitem2.addActionListener(this);
		setJMenuBar(menubar);
		//ここまでメニューに関する設定

		//JPanel p = new JPanel();

		JButton button = new JButton("button");
		button.addActionListener(this);
		p.add(button);

		Container contentPane = getContentPane();
		contentPane.add(p,  BorderLayout.CENTER);
	}

	public void paint(Graphics g) {
		super.paint(g);
	}

	void setStatus(JPanel p) {
		p.setBounds(100,100,700,700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//メニューバーとそのアイテムを設定
		JMenuBar menubar = new JMenuBar();
		JMenu menu1 = new JMenu("File");
		menubar.add(menu1);
		JMenuItem menuitem2 = new JMenuItem("Open");
		menu1.add(menuitem2);
		menuitem2.addActionListener(this);
		setJMenuBar(menubar);
		//ここまでメニューに関する設定

		//JPanel p = new JPanel();

		JButton button = new JButton("button");
		button.addActionListener(this);
		p.add(button);

		Container contentPane = getContentPane();
		contentPane.add(p,  BorderLayout.CENTER);
	}
	void setBackground(JPanel p, String path){
		p.removeAll();
		setStatus(p);
		ImageIcon background = new ImageIcon(path);
	      JLabel background2 = new JLabel(background);
	      p.add(background2);
	      pack();
	      background2.setLayout(new FlowLayout());
	      p.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "New") {
			System.out.println("Pressed 'New'");
		}else if(e.getActionCommand() == "Open") {
			JFileChooser filechooser = new JFileChooser();
			int selected = filechooser.showOpenDialog(this);
			if (selected == JFileChooser.APPROVE_OPTION){
				File file = filechooser.getSelectedFile();
				setBackground(p,file.getPath());
				/*
			      File file = filechooser.getSelectedFile();
			      ImageIcon background = new ImageIcon(file.getPath());
			      JLabel background2 = new JLabel(background);
			      add(background2);
			      pack();
			      background2.setLayout(new FlowLayout());
			      setVisible(true);
			      */
			    }else if (selected == JFileChooser.CANCEL_OPTION){
			    }else if (selected == JFileChooser.ERROR_OPTION){
			    }
			//System.out.println("Pressed 'Open'");
		}else if(e.getActionCommand() == "Close") {
			System.out.println("Pressed 'Close'");
		}else if(e.getActionCommand() == "button"){
			System.out.println("ボタンが押されました。");
		}else {
			//
		}
	}
}
