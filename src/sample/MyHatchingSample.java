package sample;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyHatchingSample extends Applet implements MouseListener
{
	Polygon m_polygon;

	public MyHatchingSample()
	{
		m_polygon = new Polygon();
	}

	public String getAppletInfo()
	{
		return "名前: Hatching\r\n" +
		       "著作者: 田中  啓\r\n";
	}


	public void init()
	{
		resize(320, 240);

		addMouseListener(this);
	}

	public void destroy()
	{
	}

	public void paint(Graphics g)
	{
	}

	public void start()
	{
	}


	public void stop()
	{
	}


	public void mousePressed(MouseEvent evt)
	{
		Graphics g = getGraphics();

		if ((evt.getModifiers() & MouseEvent.BUTTON2_MASK) !=0)
		{
			// 中央ボタン Down
		}
		else if ((evt.getModifiers() & MouseEvent.BUTTON3_MASK) != 0)
		{
			// 右ボタン Down
			MyGraphicGraph gGraph = new MyGraphicGraph(g);
			gGraph.fillPolygonHatching(m_polygon);
			m_polygon =  new Polygon();
		}
		else
		{
			// 左ボタン Down
			int x = evt.getX();
			int y = evt.getY();
			m_polygon.addPoint(x , y);
			g.drawRect(x - 5 , y - 5 , 10 , 10);
		}
	}

	public void mouseClicked(MouseEvent evt)
	{
	}

	public void mouseReleased(MouseEvent e)
	{
	}

	public void mouseEntered(MouseEvent e)
	{
	}

	public void mouseExited(MouseEvent e)
	{
	}
}