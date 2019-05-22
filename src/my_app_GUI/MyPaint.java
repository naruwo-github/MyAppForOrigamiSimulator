package my_app_GUI;

import java.awt.Color;
import java.awt.Graphics;

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
}
