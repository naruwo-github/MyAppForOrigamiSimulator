package sample;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;

public class MyLeafApp extends Applet {
    public void paint(Graphics g) {
        final double RADIAN = Math.PI/180.0;
        final double[] x = {0,0,10,15,15,17,19,21,25,25,32,34,37,40,42,42,44,45,47,50};
        final double[] y = {0,1, 1, 6,11,14,13,15,14,10,15,14,14, 7, 8, 6, 7, 5, 6, 0};
        int N = x.length;  // ﾍﾕ､ﾎﾀ朎ｼ､ﾎｸﾄｿ
        int i,x1,y1,x2,y2;
        double xx,yy;
        int xupper,yupper,xlower,ylower;
        double scale = 5.0;
        double theta = 30;  // ｲｾｳﾑﾅﾙ

        //graphics.SetColor(g,0x008800);
        g.setColor(new Color(0x008800));

        // ﾍﾕ､ﾎｾ衒ｾﾊｬ､ﾈｲｼﾈｾﾊｬ､�ﾙ､ﾋﾉﾁ､ｯ
        for (i=0; i<N-1; i++) {
            for (xx=x[i]; xx<x[i+1]; xx+=0.001) {
                // yy = ((y[i+1]-y[i])/(x[i+1]-x[i])*(xx-x[i])+y[i])
                /*
                xupper = (int)(graphics.RotateX(xx,yy,theta)*scale);
                yupper = (int)(graphics.RotateY(xx,yy,theta)*scale);
                xlower = (int)(graphics.RotateX(xx,-yy,theta)*scale);
                ylower = (int)(graphics.RotateY(xx,-yy,theta)*scale);
                graphics.DrawLine(g,xupper,yupper,xlower,ylower);
                */
            }
        }
    }
}