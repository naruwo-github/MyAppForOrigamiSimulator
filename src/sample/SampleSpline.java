package sample;

import java.applet.Applet;
import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class SampleSpline extends Applet implements
   MouseListener,MouseMotionListener,ActionListener{

   static final int X0=50,Y0=50;   //「方眼紙」の左上座標
   int data_count;                 //データ個数

   //入力データのｘ値（座標値ではない）
   double[] dataX=new double[20];
   //入力データのｙ値（座標値ではない）
   double[] dataY=new double[20];

   Button[] bt=new Button[6];

   public void init(){

      data_count=0;

      // ---------------------- マウス関係準備 ----------------------

      addMouseMotionListener(this);
      addMouseListener(this);

      // ---------------------- ボタン関係準備 ----------------------

      String[] label={"  Lagrange  ","   Newton   ",
                      " Lagrange改 "," ３次Spline ",
                      "  補間消去  "," データ消去 "};

      for(int i=0;i<6;i++){
         bt[i]=new Button(label[i]);
         add(bt[i]);
         bt[i].addActionListener(this);
      }

      //ボタンの色を設定する
      bt[0].setBackground(Color.green);   //Lagrange
      bt[1].setBackground(Color.yellow);  //Newton
      bt[2].setBackground(Color.magenta); //Lagrange改
      bt[3].setBackground(Color.cyan);    //３次Spline

   }

   // ------------------- マウス移動関係メソッド --------------------

   public void mouseMoved(MouseEvent me){  //マウスを動かした

      int x=me.getX();
      int y=me.getY();

      float xx=(float)((x-X0)/10.0);   //floatにキャストして丸める
      float yy=(float)(40.0-(y-Y0)/10.0);

      Graphics g=getGraphics();

      g.setColor(Color.white);
      g.fillRect(X0-5,Y0+445,300,20);  //前の表示を消す

      g.setColor(Color.black);
      if(xx>=0.0 && xx<=60.0 && yy>=0.0 && yy<=40.0)
         // 座標の表示
         g.drawString("X座標= "+ xx +" ,Y座標= "+ yy ,X0,Y0+460);
      else
         g.drawString("範囲外です。",X0,Y0+460);

   }

   //使用しないが、記述しておかないとエラーになる
   public void mouseDragged(MouseEvent me){ }

   // ------------------- マウス動作関係メソッド --------------------

   public void mousePressed(MouseEvent me){  //マウスをクリックした

      int x=me.getX();
      int y=me.getY();

      double xx=(x-X0)/10.0;
      double yy=40.0-(y-Y0)/10.0;

      if(xx>=0.0 && xx<=60.0 && yy>=0.0 && yy<=40.0){

         dataX[data_count]=xx;
         dataY[data_count]=yy;
         data_count++;
         repaint();

      }

   }

   //使用しないが、記述しておかないとエラーになる
   public void mouseClicked(MouseEvent me){ }
   public void mouseEntered(MouseEvent me){ }
   public void mouseReleased(MouseEvent me){ }
   public void mouseExited(MouseEvent me){ }

   // --------------------- ボタン関係メソッド ----------------------

   //ボタンがクリックされた
   public void actionPerformed(ActionEvent ae){

      if(ae.getSource()==bt[0])    drawLagrange();    //Lagrange
      if(ae.getSource()==bt[1])    drawNewton();      //Newton
      if(ae.getSource()==bt[2])    drawLagrange1();   //Lagrange改
      if(ae.getSource()==bt[3])    drawSpline();      //３次Spline
      if(ae.getSource()==bt[4])    repaint();         //補間消去
      if(ae.getSource()==bt[5])    clearData();       //データ消去

   }

   // --------------------- 補間処理メソッド類 ----------------------

   //Lagrange補間
   private void drawLagrange(){

      double x,y,lag;

      Graphics g=getGraphics();
      g.setColor(new Color(0,192,0));   //dark greenで線を引く

      for(x=dataX[0];x<=dataX[data_count-1];x+=0.01){
         y=0.0;
         for(int i=0;i<data_count;i++){
            lag=1.0;
            for(int j=0;j<data_count;j++)
               if(j!=i) lag*=(x-dataX[j])/(dataX[i]-dataX[j]);
            y+=lag*dataY[i];
         }

         g.drawRect(X0+(int)(10*x),Y0+(int)(10*(40.0-y)),0,0);

      }

   }

   //Newton補間
   private void drawNewton(){

      int i,j;
      double x,y,p;
      double[] n=new double[20];
      double[] c=new double[20];

      Graphics g=getGraphics();
      g.setColor(new Color(192,192,0));   //dark yellowで線を引く

      for(x=dataX[0];x<=dataX[data_count-1];x+=0.01){
         n[0]=1.0;
         for(i=1;i<data_count;i++){
            p=1.0;
            for(j=0;j<i;j++)
               p*=x-dataX[j];
            n[i]=p;
         }

         for(i=0;i<data_count;i++)
            c[i]=calculateDividedDiff(i,0);

         y=0.0;
         for(i=0;i<data_count;i++)
            y+=c[i]*n[i];

         g.drawRect(X0+(int)(10*x),Y0+(int)(10*(40.0-y)),0,0);

      }

   }

   //Newton補間から呼び出される再帰メソッド
   private double calculateDividedDiff(int a,int b){

      if(a<=b) return dataY[a];
      else     return (calculateDividedDiff(a,b+1)-
         calculateDividedDiff(a-1,b))/(dataX[a]-dataX[b]);

   }

   //Lagrange補間の改良
   private void drawLagrange1(){

      double x,y,lag;
      double[] dataX1=new double[22];    //データ点を増やす準備
      double[] dataY1=new double[22];

      for(int i=0;i<data_count;i++){     //入力したデータ点を移す
         dataX1[i+1]=dataX[i];
         dataY1[i+1]=dataY[i];
      }

      //両端のデータを外挿して求める
      dataX1[0]=2*dataX[0]-dataX[1];
      dataY1[0]=2*dataY[0]-dataY[1];
      dataX1[data_count+1]=2*dataX[data_count-1]-dataX[data_count-2];
      dataY1[data_count+1]=2*dataY[data_count-1]-dataY[data_count-2];

      Graphics g=getGraphics();
      g.setColor(new Color(192,0,192));   //dark magentaで線を引く

      for(x=dataX1[0];x<=dataX1[data_count+1];x+=0.01){
         y=0.0;
         for(int i=0;i<data_count+1;i++){
            lag=1.0;
            for(int j=0;j<data_count+1;j++)
               if(j!=i) lag*=((x-dataX1[j])/(dataX1[i]-dataX1[j]));
            y+=(dataY1[i]*lag);
         }

         //実際に入力した範囲のみを描画
         if(x>=dataX1[1] && x<=dataX1[data_count])
            g.drawRect(X0+(int)(10*x),Y0+(int)(10*(40.0-y)),0,0);

      }

   }

   //３次Spline補間
   private void drawSpline(){

      int i;
      double x,y,yy0,yy1,yy2,yy3;
      double[] h=new double[20];     //間隔
      double[] dif1=new double[20];  //一次微分
      double[] dif2=new double[20];  //二次微分

      h[0]=0.0;
      dif2[0]=0.0;
      dif2[data_count-1]=0.0;

      Graphics g=getGraphics();
      g.setColor(new Color(0,192,192));  //dark cyanで線を引く

      for(i=1;i<data_count;i++){
         h[i]=dataX[i]-dataX[i-1];             //間隔を計算
         dif1[i]=(dataY[i]-dataY[i-1])/h[i];   //一次微分を計算
      }

      for(i=1;i<data_count;i++)
         //二次微分を計算
         dif2[i]=(dif1[i+1]-dif1[i])/(dataX[i+1]-dataX[i-1]);

      i=1;
      for(x=dataX[0];x<dataX[data_count-1];x+=0.01){
         if(x<dataX[i]){
            yy0=dif2[i-1]/(6*h[i])*(dataX[i]-x)
               *(dataX[i]-x)*(dataX[i]-x);       //第１項
            yy1=dif2[i]/(6*h[i])*(x-dataX[i-1])
               *(x-dataX[i-1])*(x-dataX[i-1]);   //第２項
            yy2=(dataY[i-1]/h[i]-h[i]*dif2[i-1]/6)
               *(dataX[i]-x);                    //第３項
            yy3=(dataY[i]/h[i]-h[i]*dif2[i]/6)*
               (x-dataX[i-1]);                   //第４項
            y=yy0+yy1+yy2+yy3;
            g.drawRect(X0+(int)(10*x),Y0+(int)(10*(40-y)),0,0);
         }
         else i++;
      }

   }

   //データ点消去
   private void clearData(){

      data_count=0;
      for(int i=0;i<20;i++){
         dataX[i]=0.0;
         dataY[i]=0.0;
      }
      repaint();

   }

   public void paint(Graphics g){

      int i,j,x,y;

      //「方眼紙」を描く
      g.setColor(Color.lightGray);
      for(i=0;i<=60;i++)
         g.drawLine(X0+10*i,Y0,X0+10*i,Y0+400);
      for(j=0;j<=40;j++)
         g.drawLine(X0,Y0+10*j,X0+600,Y0+10*j);
      g.setColor(Color.gray);
      for(i=0;i<=12;i++)
         g.drawLine(X0+50*i,Y0,X0+50*i,Y0+400);
      for(j=0;j<=8;j++)
         g.drawLine(X0,Y0+50*j,X0+600,Y0+50*j);

      //目盛を入れる
      g.setColor(Color.black);
      for(i=0;i<=12;i++)
         g.drawString(String.valueOf(5*i),X0-5+50*i,Y0+424);
      for(j=0;j<=8;j++)
         g.drawString(String.valueOf(5*(8-j)),X0-25,Y0+5+50*j);


      //データ点を描く
      g.setColor(Color.red);

      i=data_count-1;
      while(i>=0){
         x=X0+(int)(dataX[i]*10);
         y=Y0+(int)((40.0-dataY[i])*10);
         g.drawOval(x-2, y-2, 4, 4);
         i--;
      }

   }

}