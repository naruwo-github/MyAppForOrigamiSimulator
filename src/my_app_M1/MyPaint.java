package my_app_M1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

public class MyPaint {
	static void drawTriangle(Graphics g, int cx, int cy) {
		//(cx,cy)を頂点とした左向きの三角形を描画するメソッド
		g.setColor(new Color(255,150,150));
		g.fillPolygon(new int[] {cx,cx+10,cx+10}, new int[] {cy,cy-8,cy+8}, 3);
	}

	static void fillBackGround(Graphics g, Color c, int width, int height) {
		g.fillRect(0, 0, width, height);
	}

	static void drawFilledRect(Graphics g, Color c, int x1, int y1, int x2, int y2) {
		g.setColor(c);
		g.fillRect(x1, y1, x2-x1, y2-y1);
	}

	static int calcDist(int x1, int y1, int x2, int y2) {
		int dist = (int)Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
		return dist;
	}

	static double calcDistDouble(int x1, int y1, int x2, int y2) {
		double dist = (int)Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
		return dist;
	}

	static void drawShikakuFromFourPoint(Graphics g, int[] x, int[] y) {
		g.drawLine(x[0], y[0], x[1], y[1]);
		g.drawLine(x[0], y[0], x[2], y[2]);
		g.drawLine(x[1], y[1], x[3], y[3]);
		g.drawLine(x[2], y[2], x[3], y[3]);
	}

	static void calcSquarePoint(int[] sqx, int sqy[], List<int[]> sqxlist, List<int[]> sqylist) {
		//正方形でなくても良い
		double bisector = Math.sqrt(Math.pow(sqx[0] - sqx[1], 2) + Math.pow(sqy[0] - sqy[1], 2));
		double root_two = Math.sqrt(2);
		//正方形一辺の長さ
		int side_length = (int) (bisector/root_two);

		int[] x = new int[4];
		int[] y = new int[4];

		x[0] = sqx[0];
		y[0] = sqy[0];
		x[1] = sqx[0]+side_length;
		y[1] = sqy[0];
		x[2] = sqx[0];
		y[2] = sqy[0]+side_length;
		x[3] = sqx[0]+side_length;
		y[3] = sqy[0]+side_length;

		sqxlist.add(x);
		sqylist.add(y);
	}

	static void calcQuadranglePoint(int[] sqx, int sqy[], List<int[]> sqxlist, List<int[]> sqylist) {
		//正方形でなくても良い
		int[] x = new int[4];
		int[] y = new int[4];
		int lh = sqy[1] - sqy[0];
		int lw = sqx[1] - sqx[0];

		x[0] = sqx[0];
		y[0] = sqy[0];
		x[1] = sqx[0]+lw;
		y[1] = sqy[0];
		x[2] = sqx[0];
		y[2] = sqy[0]+lh;
		x[3] = sqx[1];
		y[3] = sqy[1];

		sqxlist.add(x);
		sqylist.add(y);
	}

	static void drawQuadrangle(Graphics g, int[] x, int[] y) {
		//(x[0],y[0])が左上、(x[1],y[1])が右下の座標である四角形を描画するメソッド
		//正方形でなくてもいい。
		g.setColor(Color.BLUE);
		g.drawLine(x[0], y[0], x[1], y[0]);
		g.drawLine(x[0], y[0], x[0], y[1]);
		g.drawLine(x[1], y[0], x[1], y[1]);
		g.drawLine(x[0], y[1], x[1], y[1]);
	}

	static void drawSquare(Graphics g, int[] x, int[] y) {
		//A(x[0],y[0])が左上、D(x[1],y[1])が右下の座標である四角形を描画するメソッド
		//座標B(bx,y[0])、C(x[0],cy)
		//正方形である。
		g.setColor(Color.BLUE);
		//正方形の斜めに切った線の長さ
		double bisector = Math.sqrt(Math.pow(x[0] - x[1], 2) + Math.pow(y[0] - y[1], 2));
		//√2
		double root_two = Math.sqrt(2);
		//正方形一辺の長さ
		int side_length = (int) (bisector/root_two);

		//Bの座標を求める
		int bx = x[0] + side_length;
		int cy = y[0] + side_length;

		g.drawLine(x[0], y[0], bx, y[0]);
		g.drawLine(x[0], y[0], x[0], cy);
		//g.drawLine(bx, y[0], x[1], y[1]);
		//g.drawLine(x[0], cy, x[1], y[1]);
		g.drawLine(bx, y[0], bx, cy);
		g.drawLine(x[0], cy, bx, cy);
	}

	static void drawInfoApp(Graphics g) {
		Font font = new Font("Dialog",Font.ITALIC,10);
		g.setFont(font);
		//origami simulatorへの入力の情報を左下に書く
		g.setColor(Color.GRAY);
		g.fillRect(0, 800, 200, 200);
		g.setColor(Color.BLACK);
		g.drawString("Boundary Edges : RGB(0,0,0,X)", 10, 850);
		g.setColor(Color.RED);
		g.drawString("Mountain Fold : RGB(255,0,0,X)", 10, 875);
		g.setColor(Color.BLUE);
		g.drawString("Valley Fold : RGB(0,0,255,X)", 10, 900);
		g.setColor(Color.YELLOW);
		g.drawString("Ruling : RGB(255,255,0,X)", 10, 925);
		g.setColor(Color.GREEN);
		g.drawString("Cuts : RGB(0,255,0,X)", 10, 950);
		g.setColor(Color.MAGENTA);
		g.drawString("Undriven Creases : RGB(255,0,255,X)", 10, 975);
	}
}


