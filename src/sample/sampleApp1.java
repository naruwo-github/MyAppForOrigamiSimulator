import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

class sampleApp1 extends JFrame implements MouseListener{
	BufferedImage bimg;
	List<Integer> cpx = new ArrayList<Integer>();
	List<Integer> cpy = new ArrayList<Integer>();
	int selectedpoint = 0;
	boolean select = false;
    // Constructor
    public sampleApp1() throws IOException{
    	super("Drawing Application");
        bimg = ImageIO.read(new File("/Users/naruchan/javaworks/processingcodes/sample_images/test6.jpg"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(bimg.getWidth(),bimg.getHeight());
        setVisible(true);
        addMouseListener(this);
    }

    public static void main(String args[]) throws IOException{
    	new sampleApp1();
    }

    public void paint(Graphics g){
    	super.paint(g);
        if (bimg!=null){
        	g.drawImage(bimg,0,0,this);
        }
        for(int i = 0; i < cpx.size(); i++) {
        	g.drawRect(cpx.get(i),cpy.get(i),1,1);
        }
        MyBezier.calcBezier(cpx,cpy,g);
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		cpx.add(e.getX());
		cpy.add(e.getY());
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for(int i = 0; i < cpx.size(); i++){
		    if(e.getX() <= cpx.get(i)+5 && e.getX() >= cpx.get(i)-5){
		      if(e.getY() <= cpy.get(i)+5 && e.getY() >= cpy.get(i)-5){
		        selectedpoint = i;
		        select = true;
		      }
		    }
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(select == true){
		    cpx.set(selectedpoint,e.getX());
		    cpy.set(selectedpoint,e.getY());
		    repaint();
		    select = false;
		}
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
