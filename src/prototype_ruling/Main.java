package prototype_ruling;

import java.io.IOException;

import javax.swing.JPanel;

//アプリケーションのmain関数
//入力線に囲まれたパネルにrulingを描画するプログラム

public class Main {
	//static String path = "/Users/naruchan/javaworks/processingcodes/sample_images/test4.jpg";
    //static String path = "/Users/naruchan/javaworks/processingcodes/sample_images/test5.jpg";
    //static String path = "/Users/naruchan/javaworks/processingcodes/sample_images/test6.jpg";
	//static String path = "/Users/naruchan/javaworks/processingcodes/sample_images/test7.jpg";
    //static String path = "/Users/naruchan/javaworks/processingcodes/sample_images/test8.jpg";
    //static String path = "/Users/naruchan/javaworks/processingcodes/sample_images/test9.jpg";
	//static String path = "/Users/naruchan/javaworks/processingcodes/sample_images/test10.jpg";
	static String path = "/Users/naruchan/javaworks/processingcodes/sample_images/test12.jpg";
	//static String path = "/Users/naruchan/javaworks/processingcodes/sample_images/三谷先生折り紙作品No1-3.jpg";
    static JPanel p = new JPanel();
	public static void main(String[] args) {
		App app;
		try {
			app = new App(path,p);
			app.setVisible(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

