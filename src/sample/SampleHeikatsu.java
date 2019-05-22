package sample;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class SampleHeikatsu extends JFrame implements MouseListener{
	public static void main(String[] args) {
		SampleHeikatsu app = new SampleHeikatsu();
		app.setVisible(true);
	}

	List<Integer> cpx = new ArrayList<Integer>();
	List<Integer> cpy = new ArrayList<Integer>();
	static int n = 20;
	static int runum = n;//rulingの本数

	int counter = 0;//この値が0でないとき、rulingの疎密が変更されているときとする

	/*
	//rulingがnほんならn+1この要素が必要なリスト↓
	List<ArrayList<Double>> heikatsu = new ArrayList<ArrayList<Double>>();//平滑化の値を格納する部分
	*/
	List<Double> heikatsu = new ArrayList<Double>();//平滑化の値を格納する部分

	SampleHeikatsu(){
		super("Ruling Change Sample");
		/*
		//ベジェ曲線1
		cpx.add(100);
		cpy.add(600);

		cpx.add(300);
		cpy.add(550);

		cpx.add(500);
		cpy.add(550);

		cpx.add(700);
		cpy.add(600);
		*/

		//ベジェ曲線2
		cpx.add(100);
		cpy.add(400);

		cpx.add(300);
		cpy.add(300);

		cpx.add(500);
		cpy.add(300);

		cpx.add(700);
		cpy.add(400);

		for(int i = 0; i < runum+1; i++) {
			heikatsu.add((double)(1.0/(n+1.0)));
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Container contentPane = getContentPane();
		setBounds(0,0,800,800);
		addMouseListener(this);
	}

	public void paint(Graphics g){
		super.paint(g);
		for(int i = 0; i < cpx.size(); i++) {
			g.setColor(Color.RED);
			g.drawOval(cpx.get(i)-2, cpy.get(i)-2, 3, 3);
		}
		if(counter == 0) {
			if(cpx.size() > 0) {
				calcBezier(cpx,cpy,g);
				/*
				for(int i = 0; i < runum+1; i++) {
					//System.out.println((double)(1.0/(n+1.0)));
					heikatsu.add((double)(1.0/(n+1.0)));
				}
				*/
				drawRulingMeth(g,heikatsu,cpx,cpy);
				//System.out.println("counter == "+counter+" , heikatsu size == "+heikatsu.size());
			}
		}else {
			//ここに編集された時の処理を記述する
			if(cpx.size() > 0) {
				calcBezier(cpx,cpy,g);
				//heikatsu = heikatsuka(3,heikatsu);
				drawRulingMeth(g,heikatsu,cpx,cpy);
				//System.out.println("counter == "+counter+" , heikatsu size == "+heikatsu.size());
			}
		}
	}

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
		    //distsum += Math.sqrt((x-xx)*(x-xx)+(y-yy)*(y-yy));
		    g.setColor(Color.black);
		    g.drawLine((int)x, (int)y, (int)xx, (int)yy);
		}
	}

	//ruling描画するやつ
	public static void drawRulingMeth(Graphics g, List<Double> heikatsu, List<Integer> cpx, List<Integer> cpy) {
		double sum = 0;
		for(int i = 0; i < heikatsu.size(); i++) {
			sum += heikatsu.get(i);
			System.out.println(heikatsu.get(i));
		}
		System.out.println("heikatsu sum == "+sum);
		System.out.println();

		double t = 0;
		for(int i = 0; i < heikatsu.size()-1; i++) {
			t += heikatsu.get(i);
			float x = (float) (Math.pow((1-t),3)*cpx.get(0)+3*t*Math.pow((1-t),2)*cpx.get(1)+3*(1-t)*Math.pow(t,2)*cpx.get(2)+Math.pow(t,3)*cpx.get(3));
		    float y = (float) (Math.pow((1-t),3)*cpy.get(0)+3*t*Math.pow((1-t),2)*cpy.get(1)+3*(1-t)*Math.pow(t,2)*cpy.get(2)+Math.pow(t,3)*cpy.get(3));
		    float vx0 = (float) (Math.pow((1-t),2)*cpx.get(0)+2*t*(1-t)*cpx.get(1)+Math.pow(t,2)*cpx.get(2));
		    float vy0 = (float) (Math.pow((1-t),2)*cpy.get(0)+2*t*(1-t)*cpy.get(1)+Math.pow(t,2)*cpy.get(2));
		    float vx1 = (float) (Math.pow((1-t),2)*cpx.get(1)+2*t*(1-t)*cpx.get(2)+Math.pow(t,2)*cpx.get(3));
		    float vy1 = (float) (Math.pow((1-t),2)*cpy.get(1)+2*t*(1-t)*cpy.get(2)+Math.pow(t,2)*cpy.get(3));
		    MyVector2d v0 = new MyVector2d(vx0, vy0);
		    MyVector2d v1 = new MyVector2d(vx1, vy1);
		    MyVector2d setsuvec = v0.sub(v1);
		    MyVector2d hosenvec = new MyVector2d(setsuvec.y, -setsuvec.x);
		    hosenvec.normalize();
		    g.setColor(Color.BLUE);
		    g.drawLine((int)(x+hosenvec.x*100), (int)(y+hosenvec.y*100), (int)(x-hosenvec.x*100), (int)(y-hosenvec.y*100));
		}
	}

	//クリックした座標から一番近いrulingの番号を返すメソッド
	int returnRulingNum(int mouseX, int mouseY, List<Integer> cpx2, List<Integer> cpy2, List<Double> heikatsu2) {
		double t = 0;
		int rulingnum = 0;
		double dist = 10000;
		double tmp = 0;
		for(int i = 0; i < heikatsu2.size()-1; i++) {
			t += heikatsu2.get(i);
			float x = (float) (Math.pow((1-t),3)*cpx2.get(0)+3*t*Math.pow((1-t),2)*cpx2.get(1)+3*(1-t)*Math.pow(t,2)*cpx2.get(2)+Math.pow(t,3)*cpx2.get(3));
		    float y = (float) (Math.pow((1-t),3)*cpy2.get(0)+3*t*Math.pow((1-t),2)*cpy2.get(1)+3*(1-t)*Math.pow(t,2)*cpy2.get(2)+Math.pow(t,3)*cpy2.get(3));
		    tmp = Math.sqrt((mouseX-x)*(mouseX-x)+(mouseY-y)*(mouseY-y));
		    if(tmp < dist) {
		    	dist = tmp;
		    	rulingnum = i;
		    }
		}
		return rulingnum;
	}

	//平滑化行うメソッド(rulingを追加するメソッド)
	List<Double> heikatsukaAdd(int num, List<Double> heikatsu) {
		List<Double> nextheikatsu = new ArrayList<Double>();
		List<Double> afterheikatsuka = new ArrayList<Double>();
		double presum = 0.0;
		double postsum = 0.0;
		double a = 0.0;

		for(int i = 0; i < heikatsu.size(); i++) {
			if(i == num) {
				nextheikatsu.add(0.0);
			}
			nextheikatsu.add(heikatsu.get(i));
		}

		//平滑化する前の和
		for(int i = 0; i < heikatsu.size(); i++) {
			presum += heikatsu.get(i);
		}

		//平滑化1回目
		afterheikatsuka.add(nextheikatsu.get(0));
		for(int i = 1; i < nextheikatsu.size() - 1; i++) {
			afterheikatsuka.add((nextheikatsu.get(i-1)+nextheikatsu.get(i)+nextheikatsu.get(i+1))/3);
		}
		afterheikatsuka.add((double)nextheikatsu.get(nextheikatsu.size()-1));
		nextheikatsu = afterheikatsuka;

		//平滑化2回目
		afterheikatsuka = new ArrayList<Double>();
		afterheikatsuka.add(nextheikatsu.get(0));
		for(int i = 1; i < nextheikatsu.size() - 1; i++) {
			afterheikatsuka.add((nextheikatsu.get(i-1)+nextheikatsu.get(i)+nextheikatsu.get(i+1))/3);
		}
		afterheikatsuka.add((double)nextheikatsu.get(nextheikatsu.size()-1));
		nextheikatsu = afterheikatsuka;

		//平滑化3回目
		afterheikatsuka = new ArrayList<Double>();
		afterheikatsuka.add(nextheikatsu.get(0));
		for(int i = 1; i < nextheikatsu.size() - 1; i++) {
			afterheikatsuka.add((nextheikatsu.get(i-1)+nextheikatsu.get(i)+nextheikatsu.get(i+1))/3);
		}
		afterheikatsuka.add(nextheikatsu.get(nextheikatsu.size()-1));
		nextheikatsu = afterheikatsuka;

		//平滑化した後の和
		for(int i = 0; i < nextheikatsu.size(); i++) {
			postsum += nextheikatsu.get(i);
		}

		a = presum/postsum;

		for(int i = 0; i < nextheikatsu.size(); i++) {
			nextheikatsu.set(i, nextheikatsu.get(i)*a);
		}

		return nextheikatsu;
	}

	//平滑化行うメソッド(rulingを除去するメソッド)
	List<Double> heikatsukaSub(int num, List<Double> heikatsu) {
		List<Double> nextheikatsu = new ArrayList<Double>();
		List<Double> afterheikatsuka = new ArrayList<Double>();
		double presum = 0.0;
		double postsum = 0.0;
		double a = 0.0;

		for(int i = 0; i < heikatsu.size(); i++) {
			if(i == num) {
				nextheikatsu.set(i-1, heikatsu.get(i-1)+heikatsu.get(i));
			}else {
				nextheikatsu.add(heikatsu.get(i));
			}
		}
		/*
		//これは追加する処理
		for(int i = 0; i < heikatsu.size(); i++) {
			if(i == num) {
				nextheikatsu.add(0.0);
			}
			nextheikatsu.add(heikatsu.get(i));
		}
		*/

		//平滑化する前の和
		for(int i = 0; i < heikatsu.size(); i++) {
			presum += heikatsu.get(i);
		}

		//平滑化1回目
		afterheikatsuka.add(nextheikatsu.get(0));
		for(int i = 1; i < nextheikatsu.size() - 1; i++) {
			afterheikatsuka.add((nextheikatsu.get(i-1)+nextheikatsu.get(i)+nextheikatsu.get(i+1))/3);
		}
		afterheikatsuka.add((double)nextheikatsu.get(nextheikatsu.size()-1));
		nextheikatsu = afterheikatsuka;

		//平滑化2回目
		afterheikatsuka = new ArrayList<Double>();
		afterheikatsuka.add(nextheikatsu.get(0));
		for(int i = 1; i < nextheikatsu.size() - 1; i++) {
			afterheikatsuka.add((nextheikatsu.get(i-1)+nextheikatsu.get(i)+nextheikatsu.get(i+1))/3);
		}
		afterheikatsuka.add((double)nextheikatsu.get(nextheikatsu.size()-1));
		nextheikatsu = afterheikatsuka;

		//平滑化3回目
		afterheikatsuka = new ArrayList<Double>();
		afterheikatsuka.add(nextheikatsu.get(0));
		for(int i = 1; i < nextheikatsu.size() - 1; i++) {
			afterheikatsuka.add((nextheikatsu.get(i-1)+nextheikatsu.get(i)+nextheikatsu.get(i+1))/3);
		}
		afterheikatsuka.add(nextheikatsu.get(nextheikatsu.size()-1));
		nextheikatsu = afterheikatsuka;

		//平滑化した後の和
		for(int i = 0; i < nextheikatsu.size(); i++) {
			postsum += nextheikatsu.get(i);
		}

		a = presum/postsum;

		for(int i = 0; i < nextheikatsu.size(); i++) {
			nextheikatsu.set(i, nextheikatsu.get(i)*a);
		}

		return nextheikatsu;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		int n = 0;//編集するrulingの番号を決める変数

		// TODO 自動生成されたメソッド・スタブ
		if((e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0) {
			//左クリック
			n = returnRulingNum(e.getX(),e.getY(),cpx,cpy,heikatsu);
			heikatsu = heikatsukaAdd(n,heikatsu);
			System.out.println("左クリックを検出しました。");
			}else if((e.getModifiers() & MouseEvent.BUTTON2_MASK) != 0) {
			//中央クリック
			System.out.println("中央クリックを検出しました。");
		}else if((e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
			//右クリック
			n = returnRulingNum(e.getX(),e.getY(),cpx,cpy,heikatsu);
			heikatsu = heikatsukaSub(n,heikatsu);
			System.out.println(n);
			System.out.println("右クリックを検出しました。");
		}else {
			//その他
		}
		counter++;
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		/*
		// TODO 自動生成されたメソッド・スタブ
		if((e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0) {
			//左クリック
			System.out.println("左クリックを検出しました。");
		}else if((e.getModifiers() & MouseEvent.BUTTON2_MASK) != 0) {
			//中央クリック
			System.out.println("中央クリックを検出しました。");
		}else if((e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
			//右クリック
			System.out.println("右クリックを検出しました。");
		}else {
			//その他
		}
		*/
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

class MyVector2d {
	double x, y;
	MyVector2d(double _x, double _y){
		x = _x;
		y = _y;
	}
	void set(double _x, double _y){
		x = _x;
		y = _y;
	}

	//長さを1に正規化する
	void normalize() {
		double len = length();
		x /= len;
		y /= len;
	}

	//長さを返す
	double length() {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}

	//s倍する
	void scale(double s) {
		x *= s;
		y *= s;
	}

	//加算の定義
	MyVector2d add(MyVector2d v) {
		MyVector2d v0 = new MyVector2d(x+v.x, y+v.y);
		return v0;
	}

	//減算の定義
	MyVector2d sub(MyVector2d v) {
		MyVector2d v0 = new MyVector2d(x-v.x, y-v.y);
		return v0;
	}

	//割り算の定義
	void div(double s) {
		x = x/s;
		y = y/s;
	}

	//内積の定義
	double naiseki(MyVector2d v) {
		return x*v.x + y*v.y;
	}
}

