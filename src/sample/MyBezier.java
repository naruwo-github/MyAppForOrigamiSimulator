import java.awt.Graphics;
import java.util.List;

public class MyBezier {
	public static void calcBezier(List<Integer> cpx, List<Integer> cpy, Graphics g) {
		if(cpx.size() % 4 == 0) {
		    for(int i = 0; i < cpx.size(); i+=4) {
		    	drawBezier(g,cpx.get(i),cpy.get(i),cpx.get(i+1),cpy.get(i+1),cpx.get(i+2),cpy.get(i+2),cpx.get(i+3),cpy.get(i+3));
		    }
		  }
	}

	public static void drawBezier(Graphics g, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
		for(float t = 0; t <= 1.0; t += 0.001) {
		    float x = (float) (Math.pow((1-t),3)*x1+3*t*Math.pow((1-t),2)*x2+3*(1-t)*Math.pow(t,2)*x3+Math.pow(t,3)*x4);
		    float y = (float) (Math.pow((1-t),3)*y1+3*t*Math.pow((1-t),2)*y2+3*(1-t)*Math.pow(t,2)*y3+Math.pow(t,3)*y4);
		    g.drawRect((int)x,(int)y,0,0);
		}
	}
}
