package application3;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

//入力が確定した曲線の制御点群から、
//輪郭・折り線と制御線を判別し、
//rulingとみなす法線を描画するメソッドを含むクラス
public class MyBeziHosen3 {
	static ArrayList<Integer> start_x = new ArrayList<Integer>();//最初の入力線のx
	static ArrayList<Integer> start_y = new ArrayList<Integer>();//最初の入力のy
	static ArrayList<Integer> pre_cx = new ArrayList<Integer>();//1番目の入力線のcpのx座標
	static ArrayList<Integer> pre_cy = new ArrayList<Integer>();//1番目のy座標
	static ArrayList<Integer> post_cx = new ArrayList<Integer>();//3番目x
	static ArrayList<Integer> post_cy = new ArrayList<Integer>();//3番目ys

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
	static void drawRuling(Graphics g, List<Integer> cpx, List<Integer> cpy, int bnum) {
		start_x = new ArrayList<Integer>();
		start_y = new ArrayList<Integer>();
		pre_cx = new ArrayList<Integer>();
		pre_cy = new ArrayList<Integer>();
		post_cx = new ArrayList<Integer>();
		post_cy = new ArrayList<Integer>();
		bunkatsuBezi(start_x,start_y,bnum,cpx.get(0),cpy.get(0),cpx.get(1),cpy.get(1),cpx.get(2),cpy.get(2),cpx.get(3),cpy.get(3));
		pre_cx = start_x;
		pre_cy = start_y;
		for(int i = 0; i < (cpx.size()/4-1)/2; i++) {
			ArrayList<Integer> rx = new ArrayList<Integer>();//交点の記憶用x
			ArrayList<Integer> ry = new ArrayList<Integer>();//交点の記憶用y
			beziPoint(post_cx,post_cy,cpx.get((i+1)*8+0),cpy.get((i+1)*8+0),cpx.get((i+1)*8+1),cpy.get((i+1)*8+1),cpx.get((i+1)*8+2),cpy.get((i+1)*8+2),cpx.get((i+1)*8+3),cpy.get((i+1)*8+3));
			calcRuling(g,rx,ry,pre_cx,pre_cy,post_cx,post_cy,cpx.get((i+1)*8-4),cpy.get((i+1)*8-4),cpx.get((i+1)*8-3),cpy.get((i+1)*8-3),cpx.get((i+1)*8-2),cpy.get((i+1)*8-2),cpx.get((i+1)*8-1),cpy.get((i+1)*8-1));
			pre_cx = rx;
			pre_cy = ry;
		}
	}
	static void calcRuling(Graphics g, ArrayList<Integer> rx, ArrayList<Integer> ry, ArrayList<Integer> prex, ArrayList<Integer> prey, ArrayList<Integer> postx, ArrayList<Integer> posty, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
		float a = 0;
		for(int i = 0; i < prex.size(); i++) {
			//for(float t = 0; t <= 1.0; t += 0.005) {
			for(float t = a; t <= 1.0; t += 0.005) {
				float vx0 = (float) (Math.pow((1-t),2)*x1+2*t*(1-t)*x2+Math.pow(t,2)*x3);
			    float vy0 = (float) (Math.pow((1-t),2)*y1+2*t*(1-t)*y2+Math.pow(t,2)*y3);
			    float vx1 = (float) (Math.pow((1-t),2)*x2+2*t*(1-t)*x3+Math.pow(t,2)*x4);
			    float vy1 = (float) (Math.pow((1-t),2)*y2+2*t*(1-t)*y3+Math.pow(t,2)*y4);
			    MyVector2d3 v0 = new MyVector2d3(vx0, vy0);
			    MyVector2d3 v1 = new MyVector2d3(vx1, vy1);
			    //2本目のベジェ曲線上のtにおける接ベクトル
			    MyVector2d3 svec = v0.sub(v1);
			    MyVector2d3 hvec = new MyVector2d3(svec.y, -svec.x);
			    hvec.normalize();
			    MyVector2d3 revhvec = new MyVector2d3(-hvec.x, -hvec.y);
			    float x = (float) (Math.pow((1-t),3)*x1+3*t*Math.pow((1-t),2)*x2+3*(1-t)*Math.pow(t,2)*x3+Math.pow(t,3)*x4);
			    float y = (float) (Math.pow((1-t),3)*y1+3*t*Math.pow((1-t),2)*y2+3*(1-t)*Math.pow(t,2)*y3+Math.pow(t,3)*y4);
			    MyVector2d3 vec0 = new MyVector2d3(x,y);
			    for(int j = 0; j < 4000; j++) {
			    	x+=hvec.x*0.1;
			    	y+=hvec.y*0.1;
			    	//if(prex.get(i) == (int)x && prey.get(i) == (int)y) {
			    	if(prex.get(i) - 2 < (int)x && prex.get(i) + 2 > (int)x && prey.get(i) - 2 < (int)y && prey.get(i) + 2 > (int)y) {
			    		double sa = 100;
				    	int index = 0;
				    	for(int k = 0; k < postx.size(); k++) {
				    		MyVector2d3 cxy = new MyVector2d3(postx.get(k),posty.get(k));
				    		MyVector2d3 cxydirection = cxy.sub(vec0);
				    		cxydirection.normalize();
				    		double satmp = Math.abs(cxydirection.x-revhvec.x)+Math.abs(cxydirection.y-revhvec.y);
				    		if(sa > satmp) {
				    			sa = satmp;
				    			index = k;
				    		}
				    	}
				    	rx.add(postx.get(index));
				    	ry.add(posty.get(index));
				    	g.drawLine(prex.get(i), prey.get(i), postx.get(index), posty.get(index));
			    		i++;
			    		a+=0.2;
			    	}
			    }
			}
		}
		//pre_cx = rx;
		//pre_cy = ry;
	}
}

/*
//単純な描画メソッド
//引数に取った4つの座標をcpとしたベジェ曲線の垂線群を描画するメソッド
static void drawBeziHosenSimple(Graphics g, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
	for(float t = 0; t <= 1.0; t += 0.05) {
		float vx0 = (float) (Math.pow((1-t),2)*x1+2*t*(1-t)*x2+Math.pow(t,2)*x3);
	    float vy0 = (float) (Math.pow((1-t),2)*y1+2*t*(1-t)*y2+Math.pow(t,2)*y3);
	    float vx1 = (float) (Math.pow((1-t),2)*x2+2*t*(1-t)*x3+Math.pow(t,2)*x4);
	    float vy1 = (float) (Math.pow((1-t),2)*y2+2*t*(1-t)*y3+Math.pow(t,2)*y4);

	   //法線ベクトルを求める部分
	    MyVector2d v0 = new MyVector2d(vx0, vy0);
	    MyVector2d v1 = new MyVector2d(vx1, vy1);
	    MyVector2d svec = v1.sub(v0);
	    MyVector2d hvec = new MyVector2d(svec.y, -svec.x);
	    hvec.normalize();
	    hvec.scale(50);
	    //法線を描画する部分
	    float x = (float) (Math.pow((1-t),3)*x1+3*t*Math.pow((1-t),2)*x2+3*(1-t)*Math.pow(t,2)*x3+Math.pow(t,3)*x4);
	    float y = (float) (Math.pow((1-t),3)*y1+3*t*Math.pow((1-t),2)*y2+3*(1-t)*Math.pow(t,2)*y3+Math.pow(t,3)*y4);
	    g.drawLine((int)(x-hvec.x), (int)(y-hvec.y), (int)(x+hvec.x), (int)(y+hvec.y));
	  }
}
*/
/*
static double GetDistance (double x1, double y1, double x2, double y2) {
	double d = Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
	return d;
}
*/
/*
static boolean HanTei(double xk, double yk, double x, double y) {
	if(xk - 0.6 < x &&  x < xk + 0.6) {
		if(yk - 0.6 < y && y < yk + 0.6) {
			return true;
		}else {
			return false;
		}
	}else {
		return false;
	}
}
*/
/*
//引数に撮った4座標をcpとしたベジェ曲線の、全座標を取得するメソッド
	static void beziPoint(ArrayList<Float> curvex, ArrayList<Float> curvey, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
		for(float t = 0; t <= 1.0; t += 0.0001) {
		    float x = (float) (Math.pow((1-t),3)*x1+3*t*Math.pow((1-t),2)*x2+3*(1-t)*Math.pow(t,2)*x3+Math.pow(t,3)*x4);
		    float y = (float) (Math.pow((1-t),3)*y1+3*t*Math.pow((1-t),2)*y2+3*(1-t)*Math.pow(t,2)*y3+Math.pow(t,3)*y4);
		    curvex.add(x);
		    curvey.add(y);
		}
	}
static void bunkatsuBezi(ArrayList<Float> rsx, ArrayList<Float> rsy, int bunkatsunum,int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
	float num = (float) (1.0/bunkatsunum);
	for(float t = num; t <= 1.0; t += num) {
	    float x = (float) (Math.pow((1-t),3)*x1+3*t*Math.pow((1-t),2)*x2+3*(1-t)*Math.pow(t,2)*x3+Math.pow(t,3)*x4);
	    float y = (float) (Math.pow((1-t),3)*y1+3*t*Math.pow((1-t),2)*y2+3*(1-t)*Math.pow(t,2)*y3+Math.pow(t,3)*y4);
	    rsx.add(x);
	    rsy.add(y);
	}
}
*/
/*
static ArrayList<Integer> curvex3 = new ArrayList<Integer>();
static ArrayList<Integer> curvey3 = new ArrayList<Integer>();
//~メソッド
static void drawRuling(Graphics g, List<Integer> cpx, List<Integer> cpy, int bnum) {
	ArrayList<Float> curvex1 = new ArrayList<Float>();
	ArrayList<Float> curvey1 = new ArrayList<Float>();
	ArrayList<Float> curvex3 = new ArrayList<Float>();
	ArrayList<Float> curvey3 = new ArrayList<Float>();
	ArrayList<Float> rsx = new ArrayList<>();
	ArrayList<Float> rsy = new ArrayList<>();
	ArrayList<Float> nextbezix = new ArrayList<>();
	ArrayList<Float> nextbeziy = new ArrayList<>();
	bunkatsuBezi(nextbezix,nextbeziy,bnum,cpx.get(0),cpy.get(0),cpx.get(1),cpy.get(1),cpx.get(2),cpy.get(2),cpx.get(3),cpy.get(3));
	//ArrayList<Integer> curvex1 = new ArrayList<Integer>();
	//ArrayList<Integer> curvey1 = new ArrayList<Integer>();
	//ArrayList<Integer> curvex3 = new ArrayList<Integer>();
	//ArrayList<Integer> curvey3 = new ArrayList<Integer>();
	ArrayList<Integer> rsx = new ArrayList<Integer>();
	ArrayList<Integer> rsy = new ArrayList<Integer>();
	ArrayList<Integer> nextbezix = new ArrayList<Integer>();
	ArrayList<Integer> nextbeziy = new ArrayList<Integer>();
	bunkatsuBezi(nextbezix,nextbeziy,bnum,cpx.get(0),cpy.get(0),cpx.get(1),cpy.get(1),cpx.get(2),cpy.get(2),cpx.get(3),cpy.get(3));

	for(int i = 0; i < (cpx.size()-4)/8; i++) {
		rsx = new ArrayList<Integer>();
		rsy = new ArrayList<Integer>();
		rsx = nextbezix;
		rsy = nextbeziy;
		nextbezix = new ArrayList<Integer>();
		nextbeziy = new ArrayList<Integer>();
		//curvex1 = new ArrayList<Integer>();
		//curvey1 = new ArrayList<Integer>();
		curvex3 = new ArrayList<Integer>();
		curvey3 = new ArrayList<Integer>();

		 if(cpx.size() >= 12+i*8) {
			 //ベジェ曲線1を描いている点群を格納
			 //beziPoint(curvex1,curvey1,cpx.get(0+i*8),cpy.get(0+i*8),cpx.get(1+i*8),cpy.get(1+i*8),cpx.get(2+i*8),cpy.get(2+i*8),cpx.get(3+i*8),cpy.get(3+i*8));
			 //ベジェ曲線3を描いている点群を格納
			 beziPoint(curvex3,curvey3,cpx.get(8+i*8),cpy.get(8+i*8),cpx.get(9+i*8),cpy.get(9+i*8),cpx.get(10+i*8),cpy.get(10+i*8),cpx.get(11+i*8),cpy.get(11+i*8));
			 //描画のやつを記述
			 //rsx,rsyは分割した曲線の座法を格納している。
			 nextbezix = new ArrayList<>();
			 nextbeziy = new ArrayList<>();
			 calcruling(g,nextbezix,nextbeziy,rsx,rsy,cpx.get(4+i*8),cpy.get(4+i*8),cpx.get(5+i*8),cpy.get(5+i*8),cpx.get(6+i*8),cpy.get(6+i*8),cpx.get(7+i*8),cpy.get(7+i*8));
		}
	}
}
*/
/*
static void calcruling(Graphics g, ArrayList<Integer> nextx, ArrayList<Integer> nexty, ArrayList<Integer> rsx, ArrayList<Integer> rsy, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
	//スタートの点の数だけループ回す
	for(int i = 0; i < rsx.size(); i++) {
		for(float t = 0; t <= 1.0; t += 0.005) {
			float vx0 = (float) (Math.pow((1-t),2)*x1+2*t*(1-t)*x2+Math.pow(t,2)*x3);
		    float vy0 = (float) (Math.pow((1-t),2)*y1+2*t*(1-t)*y2+Math.pow(t,2)*y3);
		    float vx1 = (float) (Math.pow((1-t),2)*x2+2*t*(1-t)*x3+Math.pow(t,2)*x4);
		    float vy1 = (float) (Math.pow((1-t),2)*y2+2*t*(1-t)*y3+Math.pow(t,2)*y4);
		    MyVector2d v0 = new MyVector2d(vx0, vy0);
		    MyVector2d v1 = new MyVector2d(vx1, vy1);
		    //2本目のベジェ曲線上のtにおける接ベクトル
		    MyVector2d svec = v0.sub(v1);
		    svec.normalize();

		    float x = (float) (Math.pow((1-t),3)*x1+3*t*Math.pow((1-t),2)*x2+3*(1-t)*Math.pow(t,2)*x3+Math.pow(t,3)*x4);
		    float y = (float) (Math.pow((1-t),3)*y1+3*t*Math.pow((1-t),2)*y2+3*(1-t)*Math.pow(t,2)*y3+Math.pow(t,3)*y4);
		    MyVector2d vec0 = new MyVector2d(rsx.get(i),rsy.get(i));
		    MyVector2d vec1 = new MyVector2d(x,y);
		    MyVector2d rdirection = vec1.sub(vec0);
		    rdirection.normalize();
		    if(rdirection.naiseki(svec) > -0.05 && rdirection.naiseki(svec) < 0.05) {
		    	double sa = 100;
		    	int index = 0;
		    	for(int j = 0; j < curvex3.size(); j++) {
		    		MyVector2d cxy = new MyVector2d(curvex3.get(j),curvey3.get(j));
		    		MyVector2d cxydirection = cxy.sub(vec0);
		    		cxydirection.normalize();
		    		double satmp = Math.abs(cxydirection.x-rdirection.x)+Math.abs(cxydirection.y-rdirection.y);
		    		if(sa > satmp) {
		    			sa = satmp;
		    			index = j;
		    		}
		    	}
		    	nextx.add(curvex3.get(index));
		    	nexty.add(curvey3.get(index));
		    	g.drawLine(rsx.get(i), rsy.get(i), curvex3.get(index), curvey3.get(index));
		    	t+=1;
		    }
		}
	}
}
*/
