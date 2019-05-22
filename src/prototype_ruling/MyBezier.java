package prototype_ruling;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;


//入力曲線(ベジェ曲線)に関連したコード
public class MyBezier {
	//制御点が格納されたリストから、4つずつ引き出すメソッド、描画するメソッドに引数として渡す
	public static void calcBezier(List<Integer> cpx, List<Integer> cpy, Graphics g) {
		if(cpx.size() % 4 == 0) {
		    for(int i = 0; i < cpx.size(); i+=4) {
		    	drawBezier(g,cpx.get(i),cpy.get(i),cpx.get(i+1),cpy.get(i+1),cpx.get(i+2),cpy.get(i+2),cpx.get(i+3),cpy.get(i+3));
		    }
		  }
	}

	//ベジェ曲線を描画するメソッド、点を打っていく
	public static void drawBezier(Graphics g, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
		for(float t = 0; t <= 1.0 - 0.001; t += 0.001) {
			float tt = (float) (t + 0.001);
		    float x = (float) (Math.pow((1-t),3)*x1+3*t*Math.pow((1-t),2)*x2+3*(1-t)*Math.pow(t,2)*x3+Math.pow(t,3)*x4);
		    float y = (float) (Math.pow((1-t),3)*y1+3*t*Math.pow((1-t),2)*y2+3*(1-t)*Math.pow(t,2)*y3+Math.pow(t,3)*y4);
		    float xx = (float) (Math.pow((1-tt),3)*x1+3*tt*Math.pow((1-tt),2)*x2+3*(1-tt)*Math.pow(tt,2)*x3+Math.pow(tt,3)*x4);
		    float yy = (float) (Math.pow((1-tt),3)*y1+3*tt*Math.pow((1-tt),2)*y2+3*(1-tt)*Math.pow(tt,2)*y3+Math.pow(tt,3)*y4);
		    //g.drawRect((int)x,(int)y,1,1);
		    g.drawLine((int)x, (int)y, (int)xx, (int)yy);
		}
	}

	//引数に撮った4座標をcpとしたベジェ曲線の、全座標を取得するメソッド
	static void beziPoint(ArrayList<Integer> curvex, ArrayList<Integer> curvey, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
		for(float t = 0; t <= 1.0; t += 0.001) {
		    float x = (float) (Math.pow((1-t),3)*x1+3*t*Math.pow((1-t),2)*x2+3*(1-t)*Math.pow(t,2)*x3+Math.pow(t,3)*x4);
		    float y = (float) (Math.pow((1-t),3)*y1+3*t*Math.pow((1-t),2)*y2+3*(1-t)*Math.pow(t,2)*y3+Math.pow(t,3)*y4);
		    curvex.add((int)x);
		    curvey.add((int)y);
		}
	}

	static void bunkatsuBezi(ArrayList<Integer> rsx, ArrayList<Integer> rsy, int bunkatsunum,int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
		float num = (float) (1.0/bunkatsunum);
		for(float t = num; t <= 1.0; t += num) {
		    float x = (float) (Math.pow((1-t),3)*x1+3*t*Math.pow((1-t),2)*x2+3*(1-t)*Math.pow(t,2)*x3+Math.pow(t,3)*x4);
		    float y = (float) (Math.pow((1-t),3)*y1+3*t*Math.pow((1-t),2)*y2+3*(1-t)*Math.pow(t,2)*y3+Math.pow(t,3)*y4);
		    rsx.add((int)x);
		    rsy.add((int)y);
		}
	}

	//(ドラッグで)入力した点群同士の距離を順に加算し、距離を求めるメソッド
	public static double calcListLength(List<Integer> tmpx, List<Integer> tmpy) {
		//2点間の距離を求めるメソッド
		double dist = 0.;
		for(int i = 0; i < tmpx.size()-1; i++) {
			dist+= Math.sqrt(Math.pow(tmpx.get(i+1) - tmpx.get(i),2) + Math.pow(tmpy.get(i+1) - tmpy.get(i),2));
		}
		return dist;
	}
	//入力した点群からベジェ曲線としての制御点を算出するメソッド、距離かける0,1/3,2/3,1の点を4つの制御点とする
	public static ArrayList<Integer> calcControlPoint(List<Integer> tmpx, List<Integer> tmpy, int dist){
		//順番にcp2x,cp2y,cp3x,cp3yの値が格納されたリストを返す
		List<Integer> returnList = new ArrayList<Integer>();
		double tmp = 0.;
		for(int i = 0; i < tmpx.size()-1; i++) {
			tmp+= Math.sqrt(Math.pow(tmpx.get(i+1) - tmpx.get(i),2) + Math.pow(tmpy.get(i+1) - tmpy.get(i),2));
			//制御点2のx,y座標
			if(returnList.size() == 0) {
				if((int)tmp > ((int)dist/3 - 10) && (int)tmp < ((int)dist/3 + 10)) {
					returnList.add(tmpx.get(i+1));
					returnList.add(tmpy.get(i+1));
				}
			}
			//制御点3のx,y座標
			if(returnList.size() == 2) {
				if((int)tmp > ((int)dist*2/3 - 10) && (int)tmp < ((int)dist*2/3 + 10)) {
					returnList.add(tmpx.get(i+1));
					returnList.add(tmpy.get(i+1));
				}
			}
		}
		return (ArrayList<Integer>) returnList;
	}

	//リストに2点を通る座標を1000点、格納するプログラム(よってsize()は1000かな)
	//なので削除の時に100個消せばいい
	public static void saveCooxy(List<Integer> coorinx, List<Integer> cooriny, int x1, int y1, int x2, int y2) {
		MyVector2d vec1 = new MyVector2d(x1, y1);
		MyVector2d vec2 = new MyVector2d(x2, y2);
		MyVector2d vec3 = vec2.sub(vec1);
		vec3.div(999);
		double x = x1;
		double y = y1;
		for(int i = 0; i < 1000; i++) {
			coorinx.add((int)x);
			cooriny.add((int)y);

			x+=vec3.x;
			y+=vec3.y;
		}
	}
}