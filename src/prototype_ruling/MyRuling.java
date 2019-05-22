package prototype_ruling;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;


//入力が確定した曲線の制御点群から、
//輪郭・折り線と制御線を判別し、
//rulingとみなす法線を描画するメソッドを含むクラス
public class MyRuling {
	static ArrayList<Integer> start_x = new ArrayList<Integer>();//最初の入力線のx
	static ArrayList<Integer> start_y = new ArrayList<Integer>();//最初の入力のy
	static ArrayList<Integer> pre_cx = new ArrayList<Integer>();//1番目の入力線のcpのx座標
	static ArrayList<Integer> pre_cy = new ArrayList<Integer>();//1番目のy座標
	static ArrayList<Integer> post_cx = new ArrayList<Integer>();//3番目x
	static ArrayList<Integer> post_cy = new ArrayList<Integer>();//3番目ys

	static ArrayList<Integer> aaa_cx = new ArrayList<Integer>();
	static ArrayList<Integer> aaa_cy = new ArrayList<Integer>();

	//static ArrayList<ArrayList<Integer>> oriorix = new ArrayList<ArrayList<Integer>>();
	//static ArrayList<ArrayList<Integer>> orioriy = new ArrayList<ArrayList<Integer>>();

	//heikatsuリストによるスタート地点の分割
	static void makeStartPoint(List<Double> heikatsu, ArrayList<Integer> startx, ArrayList<Integer> starty, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
		double t = 0.0;
		for(int i = 0; i < heikatsu.size(); i++) {
			t += heikatsu.get(i);
			float x = (float) (Math.pow((1-t),3)*x1+3*t*Math.pow((1-t),2)*x2+3*(1-t)*Math.pow(t,2)*x3+Math.pow(t,3)*x4);
		    float y = (float) (Math.pow((1-t),3)*y1+3*t*Math.pow((1-t),2)*y2+3*(1-t)*Math.pow(t,2)*y3+Math.pow(t,3)*y4);
		    startx.add((int)x);
		    starty.add((int)y);
		}
	}

	//入力線1と入力線2の中点を結んだ線を補助線の候補としてオーバーレイ表示するメソッド
	static void drawHojyoSeigyo(Graphics g, List<Integer> cpx, List<Integer> cpy) {
		int size = cpx.size();
		if((size/4)%2 == 0 && size > 0) {
			if(size == 8) {
				for(float t = (float) 0.015; t <= 1.01; t+=0.001) {
					float x0 = (float) (Math.pow((1-t),3)*cpx.get(size-4-4)+3*t*Math.pow((1-t),2)*cpx.get(size-3-4)+3*(1-t)*Math.pow(t,2)*cpx.get(size-2-4)+Math.pow(t,3)*cpx.get(size-1-4));
				    float y0 = (float) (Math.pow((1-t),3)*cpy.get(size-4-4)+3*t*Math.pow((1-t),2)*cpy.get(size-3-4)+3*(1-t)*Math.pow(t,2)*cpy.get(size-2-4)+Math.pow(t,3)*cpy.get(size-1-4));
					float x1 = (float) (Math.pow((1-t),3)*cpx.get(size-4)+3*t*Math.pow((1-t),2)*cpx.get(size-3)+3*(1-t)*Math.pow(t,2)*cpx.get(size-2)+Math.pow(t,3)*cpx.get(size-1));
				    float y1 = (float) (Math.pow((1-t),3)*cpy.get(size-4)+3*t*Math.pow((1-t),2)*cpy.get(size-3)+3*(1-t)*Math.pow(t,2)*cpy.get(size-2)+Math.pow(t,3)*cpy.get(size-1));
				    float x = (x0+x1)/2;
				    float y = (y0+y1)/2;
				    g.drawOval((int)x, (int)y, 10, 10);
				}
			}else {
				for(float t = (float) 0.015; t <= 1.01; t+=0.001) {
					float x0 = (float) (Math.pow((1-t),3)*cpx.get(size-4-4-4)+3*t*Math.pow((1-t),2)*cpx.get(size-3-4-4)+3*(1-t)*Math.pow(t,2)*cpx.get(size-2-4-4)+Math.pow(t,3)*cpx.get(size-1-4-4));
				    float y0 = (float) (Math.pow((1-t),3)*cpy.get(size-4-4-4)+3*t*Math.pow((1-t),2)*cpy.get(size-3-4-4)+3*(1-t)*Math.pow(t,2)*cpy.get(size-2-4-4)+Math.pow(t,3)*cpy.get(size-1-4-4));
					float x1 = (float) (Math.pow((1-t),3)*cpx.get(size-4)+3*t*Math.pow((1-t),2)*cpx.get(size-3)+3*(1-t)*Math.pow(t,2)*cpx.get(size-2)+Math.pow(t,3)*cpx.get(size-1));
				    float y1 = (float) (Math.pow((1-t),3)*cpy.get(size-4)+3*t*Math.pow((1-t),2)*cpy.get(size-3)+3*(1-t)*Math.pow(t,2)*cpy.get(size-2)+Math.pow(t,3)*cpy.get(size-1));
				    float x = (x0+x1)/2;
				    float y = (y0+y1)/2;
				    g.drawOval((int)x, (int)y, 10, 10);
				}
			}
		}
	}

	//heiaktsu
	//rulingスタートは第三入力線(第一補助線)上を分割した点
	static void drawRuling2(Graphics g, List<Integer> cpx, List<Integer> cpy, List<Integer> coorinx, List<Integer> cooriny, List<Double> heikatsu, List<Integer> rux, List<Integer> ruy, List<Integer> orix, List<Integer> oriy) {
		start_x = new ArrayList<Integer>();
		start_y = new ArrayList<Integer>();
		pre_cx = new ArrayList<Integer>();
		pre_cy = new ArrayList<Integer>();
		post_cx = new ArrayList<Integer>();
		post_cy = new ArrayList<Integer>();

		//一時保存用のアレ
		aaa_cx = new ArrayList<Integer>();
		aaa_cy = new ArrayList<Integer>();

		//oriorix = new ArrayList<ArrayList<Integer>>();
		//oriorix = new ArrayList<ArrayList<Integer>>();

		//第一補助線は第３入力せんなのでgetの引数は8から11
		makeStartPoint(heikatsu,start_x,start_y,cpx.get(8),cpy.get(8),cpx.get(9),cpy.get(9),cpx.get(10),cpy.get(10),cpx.get(11),cpy.get(11));
		MyBezier.beziPoint(pre_cx,pre_cy,cpx.get(0),cpy.get(0),cpx.get(1),cpy.get(1),cpx.get(2),cpy.get(2),cpx.get(3),cpy.get(3));
		MyBezier.beziPoint(post_cx,post_cy,cpx.get(4),cpy.get(4),cpx.get(5),cpy.get(5),cpx.get(6),cpy.get(6),cpx.get(7),cpy.get(7));

		float t = (float) 0.0;
		for(int i = 0; i < heikatsu.size(); i++) {
			t += heikatsu.get(i);
			float x0 = (float) (Math.pow((1-t),3)*cpx.get(8)+3*t*Math.pow((1-t),2)*cpx.get(9)+3*(1-t)*Math.pow(t,2)*cpx.get(10)+Math.pow(t,3)*cpx.get(11));
		    float y0 = (float) (Math.pow((1-t),3)*cpy.get(8)+3*t*Math.pow((1-t),2)*cpy.get(9)+3*(1-t)*Math.pow(t,2)*cpy.get(10)+Math.pow(t,3)*cpy.get(11));
		    //float x1 = x0;
		    //float y1 = y0;
		    MyVector2d vec0 = new MyVector2d(x0, y0);

		    float vx0 = (float) (Math.pow((1-t),2)*cpx.get(8)+2*t*(1-t)*cpx.get(9)+Math.pow(t,2)*cpx.get(10));
		    float vy0 = (float) (Math.pow((1-t),2)*cpy.get(8)+2*t*(1-t)*cpy.get(9)+Math.pow(t,2)*cpy.get(10));
		    float vx1 = (float) (Math.pow((1-t),2)*cpx.get(9)+2*t*(1-t)*cpx.get(10)+Math.pow(t,2)*cpx.get(11));
		    float vy1 = (float) (Math.pow((1-t),2)*cpy.get(9)+2*t*(1-t)*cpy.get(10)+Math.pow(t,2)*cpy.get(11));

		    MyVector2d v0 = new MyVector2d(vx0, vy0);
		    MyVector2d v1 = new MyVector2d(vx1, vy1);
		    MyVector2d svec = v1.sub(v0);
		    MyVector2d hvec = new MyVector2d(svec.y, -svec.x);//これは第二入力線方向
		    MyVector2d hvec1 = new MyVector2d(-hvec.x, -hvec.y);
		    svec.normalize();
		    hvec.normalize();
		    hvec1.normalize();

		    double sa = 100.0;
		    int index = 0;
		    double satmp = 0.0;
		    int j;
		    for(j = 0; j < pre_cx.size(); j++) {
		    	MyVector2d vec = new MyVector2d(pre_cx.get(j),pre_cy.get(j));
		    	MyVector2d direc = vec.sub(vec0);
		    	direc.normalize();
		    	satmp = Math.sqrt((direc.x - hvec1.x)*(direc.x - hvec1.x)+(direc.y - hvec1.y)*(direc.y - hvec1.y));
		    	if(sa > satmp) {
		    		sa = satmp;
		    		index = j;
		    	}
		    }

		    sa = 100.0;
		    int index1 = 0;
		    satmp = 0.0;
		    int k;
		    for(k = 0; k < post_cx.size(); k++) {
		    	MyVector2d vec = new MyVector2d(post_cx.get(k),post_cy.get(k));
		    	MyVector2d direc = vec.sub(vec0);
		    	direc.normalize();
		    	satmp = Math.sqrt((direc.x - hvec.x)*(direc.x - hvec.x)+(direc.y - hvec.y)*(direc.y - hvec.y));
		    	if(sa > satmp) {
		    		sa = satmp;
		    		index1 = k;
		    	}
		    }

		    double sa1 = 100.0;
		    int index2 = 0;
		    satmp = 0.0;
		    int l;
		    //
		    //
		    for(l = 2000; l < 4000; l++) {
		    //for(l = coorinx.size() - 2000; l < coorinx.size(); l++) {
		    	MyVector2d vec = new MyVector2d(coorinx.get(l),cooriny.get(l));
		    	MyVector2d direc = vec.sub(vec0);
		    	direc.normalize();
		    	satmp = Math.sqrt((direc.x - hvec.x)*(direc.x - hvec.x)+(direc.y - hvec.y)*(direc.y - hvec.y));
		    	if(sa1 > satmp) {
		    		sa1 = satmp;
		    		index2 = l;
		    	}
		    }
		    if(sa < sa1) {
		    	//折り線上にruling描画
		    	g.setColor(Color.GREEN);
		    	g.drawLine(post_cx.get(index1), post_cy.get(index1), pre_cx.get(index), pre_cy.get(index));

		    	rux.add(pre_cx.get(index));
			   	ruy.add(pre_cy.get(index));
			   	rux.add(post_cx.get(index1));
			   	ruy.add(post_cy.get(index1));

			   	orix.add(post_cx.get(index1));
			   	oriy.add(post_cy.get(index1));

			   	//System.out.println("rux.size() = "+rux.size());
			   	//System.out.println("orix.size() = "+orix.size());

		    	aaa_cx.add(post_cx.get(index1));
		    	aaa_cy.add(post_cy.get(index1));
		    }else {
		    	//輪郭線上にruling描画
		    	g.setColor(Color.CYAN);
		    	g.drawLine(coorinx.get(index2), cooriny.get(index2), pre_cx.get(index), pre_cy.get(index));
		    	rux.add(pre_cx.get(index));
			   	ruy.add(pre_cy.get(index));
			   	rux.add(coorinx.get(index2));
			   	ruy.add(cooriny.get(index2));

			   	//System.out.println("rux.size() = "+rux.size());
			   	//System.out.println("orix.size() = "+orix.size());
		    }
		}
		pre_cx = aaa_cx;
		pre_cy = aaa_cy;

		/*
		for(int i = 0; i < orix.size(); i++) {
			g.setColor(Color.RED);
			g.drawRect(orix.get(i), oriy.get(i), 3, 3);
		}
		*/

		//描画の続き
		for(int i = 1; i < (cpx.size()/4-1)/2; i++) {
			ArrayList<Integer> rx = new ArrayList<Integer>();//交点の記憶用x
			ArrayList<Integer> ry = new ArrayList<Integer>();//交点の記憶用y
			MyBezier.beziPoint(post_cx,post_cy,cpx.get((i+1)*8-4),cpy.get((i+1)*8-4),cpx.get((i+1)*8-3),cpy.get((i+1)*8-3),cpx.get((i+1)*8-2),cpy.get((i+1)*8-2),cpx.get((i+1)*8-1),cpy.get((i+1)*8-1));
			calcRuling2(g,rx,ry,pre_cx,pre_cy,post_cx,post_cy,cpx.get((i+1)*8+0),cpy.get((i+1)*8+0),cpx.get((i+1)*8+1),cpy.get((i+1)*8+1),cpx.get((i+1)*8+2),cpy.get((i+1)*8+2),cpx.get((i+1)*8+3),cpy.get((i+1)*8+3),coorinx,cooriny,rux,ruy,orix,oriy);
			pre_cx = rx;
			pre_cy = ry;
		}
		/*
		//orixの最後のところの点を削除する部分
		ArrayList<Integer> weix = new ArrayList<Integer>();
		ArrayList<Integer> weiy = new ArrayList<Integer>();
		for(int i = 0; i < orix.size() - post_cx.size() + 1; i++) {
			weix.add(orix.get(i));
			weiy.add(oriy.get(i));
		}
		orix = weix;
		oriy = weiy;
		*/
	}

	static void calcRuling2(Graphics g, ArrayList<Integer> rx, ArrayList<Integer> ry, ArrayList<Integer> prex, ArrayList<Integer> prey, ArrayList<Integer> postx, ArrayList<Integer> posty, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, List<Integer> coorinx, List<Integer> cooriny, List<Integer> rux, List<Integer> ruy, List<Integer> orix, List<Integer> oriy) {
		//calcRulingと同じ内容だが、輪郭線まで描画できるように変更したい
		//float a = 0;
		for(int i = 0; i < prex.size(); i++) {
			for(float t = 0; t <= 1.0; t += 0.005) {
			//for(float t = a; t <= 1.0; t += 0.005) {
				float vx0 = (float) (Math.pow((1-t),2)*x1+2*t*(1-t)*x2+Math.pow(t,2)*x3);
			    float vy0 = (float) (Math.pow((1-t),2)*y1+2*t*(1-t)*y2+Math.pow(t,2)*y3);
			    float vx1 = (float) (Math.pow((1-t),2)*x2+2*t*(1-t)*x3+Math.pow(t,2)*x4);
			    float vy1 = (float) (Math.pow((1-t),2)*y2+2*t*(1-t)*y3+Math.pow(t,2)*y4);
			    MyVector2d v0 = new MyVector2d(vx0, vy0);
			    MyVector2d v1 = new MyVector2d(vx1, vy1);
			    //2本目のベジェ曲線上のtにおける接ベクトル
			    MyVector2d svec = v0.sub(v1);
			    MyVector2d hvec = new MyVector2d(svec.y, -svec.x);
			    hvec.normalize();
			    MyVector2d revhvec = new MyVector2d(-hvec.x, -hvec.y);
			    float x = (float) (Math.pow((1-t),3)*x1+3*t*Math.pow((1-t),2)*x2+3*(1-t)*Math.pow(t,2)*x3+Math.pow(t,3)*x4);
			    float y = (float) (Math.pow((1-t),3)*y1+3*t*Math.pow((1-t),2)*y2+3*(1-t)*Math.pow(t,2)*y3+Math.pow(t,3)*y4);
			    MyVector2d vec0 = new MyVector2d(x,y);
			    for(int j = 0; j < 4000; j++) {
			    	x += hvec.x*0.1;
				   	y += hvec.y*0.1;
				   	if(prex.get(i) - 2 < (int)x && prex.get(i) + 2 > (int)x && prey.get(i) - 2 < (int)y && prey.get(i) + 2 > (int)y) {
				   		double sa = 100;
				    	int index = 0;
				    	double satmp;
				    	for(int k = 0; k < postx.size(); k++) {
					   		MyVector2d cxy = new MyVector2d(postx.get(k),posty.get(k));
				    		MyVector2d cxydirection = cxy.sub(vec0);
				    		cxydirection.normalize();
				    		satmp = Math.abs(cxydirection.x-revhvec.x)+Math.abs(cxydirection.y-revhvec.y);
				    		if(sa > satmp) {
				    			sa = satmp;
				    			index = k;
				    		}
				    	}
				    	double sa1 = 100.0;
				    	int index1 = 0;
				    	double satmp1;
				    	if(prex.size()/2 > i) {
				    		for(int l = coorinx.size() - 2000; l < coorinx.size(); l++) {
					    		MyVector2d vec = new MyVector2d(coorinx.get(l),cooriny.get(l));
					    		MyVector2d direc = vec.sub(vec0);
					    		direc.normalize();
					    		//satmp1 = Math.abs(direc.x-revhvec.x)+Math.abs(direc.y-revhvec.y);
					    		satmp1 = Math.abs(direc.x-hvec.x)+Math.abs(direc.y-hvec.y);
					    		if(sa1 > satmp1) {
					    			sa1 = satmp1;
					    			index1 = l;
					    		}
					    	}
				    	}else {
				    		for(int l = coorinx.size() - 1000; l < coorinx.size(); l++) {
					    		MyVector2d vec = new MyVector2d(coorinx.get(l),cooriny.get(l));
					    		MyVector2d direc = vec.sub(vec0);
					    		direc.normalize();
					    		//satmp1 = Math.abs(direc.x-revhvec.x)+Math.abs(direc.y-revhvec.y);
					    		satmp1 = Math.abs(direc.x-hvec.x)+Math.abs(direc.y-hvec.y);
					    		if(sa1 > satmp1) {
					    			sa1 = satmp1;
					    			index1 = l;
					    		}
					    	}
				    	}
				    	/*
				    	//for(int l = coorinx.size() - 2000 + 10; l < coorinx.size(); l++) {
				    	for(int l = coorinx.size() - 2000 + 300; l < coorinx.size(); l++) {
				    		MyVector2d vec = new MyVector2d(coorinx.get(l),cooriny.get(l));
				    		MyVector2d direc = vec.sub(vec0);
				    		direc.normalize();
				    		//satmp1 = Math.abs(direc.x-revhvec.x)+Math.abs(direc.y-revhvec.y);
				    		satmp1 = Math.abs(direc.x-hvec.x)+Math.abs(direc.y-hvec.y);
				    		if(sa1 > satmp1) {
				    			sa1 = satmp1;
				    			index1 = l;
				    		}
				    	}
				    	*/
				    	if(sa < sa1) {
				    		rx.add(postx.get(index));
					    	ry.add(posty.get(index));
					    	//g.setColor(Color.YELLOW);
					    	g.setColor(Color.RED);
						   	g.drawLine(prex.get(i), prey.get(i), postx.get(index), posty.get(index));

						   	rux.add(prex.get(i));
						   	ruy.add(prey.get(i));
						   	rux.add(postx.get(index));
						   	ruy.add(posty.get(index));

						   	//orix.add(postx.get(index));
						   	//oriy.add(posty.get(index));

						   	//System.out.println("rux.size() = "+rux.size());
						   	//System.out.println("orix.size() = "+orix.size());

						   	i++;
				    		break;
				    	}else {
				    		//緑マゼンタの理由は
				    		//うまく行かない部分をあぶり出すため
				    		g.setColor(Color.MAGENTA);
				    		g.drawLine(prex.get(i), prey.get(i), coorinx.get(index1), cooriny.get(index1));

				    		//rux.add(prex.get(i));
						   	//ruy.add(prey.get(i));
						   	//rux.add(coorinx.get(index1));
						   	//ruy.add(cooriny.get(index1));

						   	//System.out.println("rux.size() = "+rux.size());
						   	//System.out.println("orix.size() = "+orix.size());

				    		i++;
				    		break;
				    	}
				    	/*
				    	rx.add(postx.get(index));
				    	ry.add(posty.get(index));
				    	g.setColor(Color.YELLOW);
					   	g.drawLine(prex.get(i), prey.get(i), postx.get(index), posty.get(index));
					   	//a+=0.5;
				   		i++;
				   		break;
				   		*/
				   	}else {
				   	}
			    }
			}
		}
	}

	/*
	//heikatsuしようしているメソッド
	//rulingのスタートは、第一入力線上を分割した点
	static void drawRuling(Graphics g, List<Integer> cpx, List<Integer> cpy, List<Integer> coorinx, List<Integer> cooriny, List<Double> heikatsu) {
		start_x = new ArrayList<Integer>();
		start_y = new ArrayList<Integer>();
		pre_cx = new ArrayList<Integer>();
		pre_cy = new ArrayList<Integer>();
		post_cx = new ArrayList<Integer>();
		post_cy = new ArrayList<Integer>();
		//スタート地点を決めている部分
		makeStartPoint(heikatsu,start_x,start_y,cpx.get(0),cpy.get(0),cpx.get(1),cpy.get(1),cpx.get(2),cpy.get(2),cpx.get(3),cpy.get(3));
		pre_cx = start_x;
		pre_cy = start_y;

		for(int i = 0; i < (cpx.size()/4-1)/2; i++) {
			ArrayList<Integer> rx = new ArrayList<Integer>();//交点の記憶用x
			ArrayList<Integer> ry = new ArrayList<Integer>();//交点の記憶用y
			MyBezier.beziPoint(post_cx,post_cy,cpx.get((i+1)*8-4),cpy.get((i+1)*8-4),cpx.get((i+1)*8-3),cpy.get((i+1)*8-3),cpx.get((i+1)*8-2),cpy.get((i+1)*8-2),cpx.get((i+1)*8-1),cpy.get((i+1)*8-1));
			calcRuling(g,rx,ry,pre_cx,pre_cy,post_cx,post_cy,cpx.get((i+1)*8+0),cpy.get((i+1)*8+0),cpx.get((i+1)*8+1),cpy.get((i+1)*8+1),cpx.get((i+1)*8+2),cpy.get((i+1)*8+2),cpx.get((i+1)*8+3),cpy.get((i+1)*8+3),coorinx,cooriny);
			pre_cx = rx;
			pre_cy = ry;
		}
	}

	static void calcRuling(Graphics g, ArrayList<Integer> rx, ArrayList<Integer> ry, ArrayList<Integer> prex, ArrayList<Integer> prey, ArrayList<Integer> postx, ArrayList<Integer> posty, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, List<Integer> coorinx, List<Integer> cooriny) {
		//float a = 0;
		for(int i = 0; i < prex.size(); i++) {
			for(float t = 0; t <= 1.0; t += 0.005) {
			//for(float t = a; t <= 1.0; t += 0.005) {
				float vx0 = (float) (Math.pow((1-t),2)*x1+2*t*(1-t)*x2+Math.pow(t,2)*x3);
			    float vy0 = (float) (Math.pow((1-t),2)*y1+2*t*(1-t)*y2+Math.pow(t,2)*y3);
			    float vx1 = (float) (Math.pow((1-t),2)*x2+2*t*(1-t)*x3+Math.pow(t,2)*x4);
			    float vy1 = (float) (Math.pow((1-t),2)*y2+2*t*(1-t)*y3+Math.pow(t,2)*y4);
			    MyVector2d v0 = new MyVector2d(vx0, vy0);
			    MyVector2d v1 = new MyVector2d(vx1, vy1);
			    //2本目のベジェ曲線上のtにおける接ベクトル
			    MyVector2d svec = v0.sub(v1);
			    MyVector2d hvec = new MyVector2d(svec.y, -svec.x);
			    hvec.normalize();
			    MyVector2d revhvec = new MyVector2d(-hvec.x, -hvec.y);
			    float x = (float) (Math.pow((1-t),3)*x1+3*t*Math.pow((1-t),2)*x2+3*(1-t)*Math.pow(t,2)*x3+Math.pow(t,3)*x4);
			    float y = (float) (Math.pow((1-t),3)*y1+3*t*Math.pow((1-t),2)*y2+3*(1-t)*Math.pow(t,2)*y3+Math.pow(t,3)*y4);
			    MyVector2d vec0 = new MyVector2d(x,y);
			    for(int j = 0; j < 4000; j++) {
			    	x+=hvec.x*0.1;
				   	y+=hvec.y*0.1;
				   	if(prex.get(i) - 2 < (int)x && prex.get(i) + 2 > (int)x && prey.get(i) - 2 < (int)y && prey.get(i) + 2 > (int)y) {
				   		double sa = 100;
				    	int index = 0;
				    	for(int k = 0; k < postx.size(); k++) {
					   		MyVector2d cxy = new MyVector2d(postx.get(k),posty.get(k));
				    		MyVector2d cxydirection = cxy.sub(vec0);
				    		cxydirection.normalize();
				    		double satmp = Math.abs(cxydirection.x-revhvec.x)+Math.abs(cxydirection.y-revhvec.y);
				    		if(sa > satmp) {
				    			sa = satmp;
				    			index = k;
				    		}
				    	}
				    	rx.add(postx.get(index));
				    	ry.add(posty.get(index));
				    	g.setColor(Color.YELLOW);
					   	g.drawLine(prex.get(i), prey.get(i), postx.get(index), posty.get(index));
					   	//a+=0.5;
				   		i++;
				   		break;
				   	}else {
				   	}
			    }
			}
		}
	}
	*/
}
