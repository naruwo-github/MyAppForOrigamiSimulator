package sample;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.Stack;

public class MyGraphicGraph
{
	private Graphics m_g;
	public MyGraphicGraph(Graphics g)
	{
		m_g = g;

		setAngleRadian(3.1415926535 / 4);
	}

	//private static int HS_GAP_WIDTH = 5;		// ハッチングの線と線の幅（横幅）
	//private static int HS_GAP_HIGHT = 5;		// ハッチングの線と線の幅（縦幅）

	private static double m_dbGap = 15;		// ハッチングの線と線の幅
	private double m_dbAngleRadian;			// ハッチングの線の角度

	public void setAngleRadian(double dbAngleRadian)
	{
		m_dbAngleRadian = dbAngleRadian;
	}
	public double getAngleRadian()
	{
		return m_dbAngleRadian;
	}

	public void fillPolygonHatching(Polygon p)
	{
		int i;

		// 枠を描画する
		m_g.drawPolygon(p);
		m_g.drawLine(p.xpoints[0] , p.ypoints[0] , p.xpoints[p.npoints - 1] , p.ypoints[p.npoints - 1]);

		// Polygon に外接する四角形
		Rectangle rectBounding = new Rectangle();
		rectBounding = p.getBounds();

		// ハッチングの線の角度を変換する
		double dbAngleX = Math.sin(getAngleRadian());
		double dbAngleY = Math.cos(getAngleRadian());
		double dbAngle;

		// 描画するハッチングの線分を計算する
//		if (Math.abs(dbAngleX) >= Math.abs(dbAngleY))	// 横線に近い線
		{
			dbAngle = dbAngleY / dbAngleX;
			// 初期値 [y = dbAngle * x + Y0] の Y0
			int y0 = (int)(rectBounding.y - dbAngle * (rectBounding.x + rectBounding.width));
			int nGapY = (int)Math.abs(Math.cos(getAngleRadian()) * m_dbGap + 0.5);
			if (nGapY < 1)
				return;
			int y = ((y0 - nGapY) / nGapY) * nGapY;

			while (y <= rectBounding.y + rectBounding.height - dbAngle * rectBounding.x)
			{
				// 描画する線分の端の集合を取得する
				Stack stackPoint = getHatchPoint(p , dbAngle , y);
				int nStackLen = stackPoint.size();
				if (!stackPoint.empty() && (nStackLen > 1))
				{
					Point [] pt = new Point[nStackLen];
					for (i = 0 ; i < nStackLen ; i++)
						pt[i] = (Point)stackPoint.pop();
					sortPoint(pt , nStackLen);
					for (i = 0 ; i + 1 < nStackLen ; i += 2)
					{
						m_g.drawLine(pt[i].x , pt[i].y , pt[i + 1].x , pt[i + 1].y);
					}
				}
				y += nGapY;
			}
		}
/*		else	// 縦線に近い線
		{
//			dbAngle = dbAngleY / dbAngleX;
			dbAngle = dbAngleX / dbAngleY;
//			// 初期値 [y = dbAngle * x + Y0] の Y0
			// 初期値 [x = dbAngle * y + X0] の X0
//			int y0 = (int)(rectBounding.y - dbAngle * (rectBounding.x + rectBounding.width));
//			int y = ((y0 - HS_GAP_HIGHT) / HS_GAP_HIGHT) * HS_GAP_HIGHT;
			int x0 = (int)(rectBounding.x - dbAngle * (rectBounding.y + rectBounding.height));
			int x = ((x0 - HS_GAP_WIDTH) / HS_GAP_WIDTH) * HS_GAP_WIDTH;

//			while (y <= rectBounding.y + rectBounding.height - dbAngle * rectBounding.x)
			while (x <= rectBounding.x + rectBounding.width - dbAngle * rectBounding.y)
			{
				// 描画する線分の端の集合を取得する
				Stack stackPoint = getHatchPoint(p , dbAngle , y);
				int nStackLen = stackPoint.size();
				if (!stackPoint.empty() && (nStackLen > 1))
				{
					Point [] pt = new Point[nStackLen];
					for (i = 0 ; i < nStackLen ; i++)
						pt[i] = (Point)stackPoint.pop();
					sortPoint(pt , nStackLen);
					for (i = 0 ; i + 1 < nStackLen ; i += 2)
					{
						m_g.drawLine(pt[i].x , pt[i].y , pt[i + 1].x , pt[i + 1].y);
					}
				}
				y += HS_GAP_HIGHT;
			}
		}
*/	}

	private Stack getHatchPoint(Polygon p , double dbAngle , int y0)
	{
		Stack stackRet = new Stack();
		double a1 = dbAngle;
		double b1 = (double)y0;
		double a2,b2;
		double xCross , yCross;
		int i;
		Point ptRet;
		for (i = 0 ; i < p.npoints ; i++)
		{
			// 線分の両方の点
			Point p1 = new Point(p.xpoints[i] , p.ypoints[i]);
			Point p2;
			if (i == p.npoints - 1)
				p2 = new Point(p.xpoints[0] , p.ypoints[0]);
			else
				p2 = new Point(p.xpoints[i + 1] , p.ypoints[i + 1]);

			double dx = (double)(p1.x - p2.x);
			double dy = (double)(p1.y - p2.y);
			if (Math.abs(dx) >= Math.abs(dy))	// 横線に近い線
			{
				// 線分のパラメータ導出
				a2 = dy / dx;
				b2 = (double)p1.y - a2 * (double)p1.x;
				b2 += (double)0.01;						// ぴったりの場所から 0.01 だけ上にずらす
				if (a1 == a2)
					continue;
				// 交差点を計算する
				xCross = (b2 - b1) / (a1 - a2);
				yCross = a1 * xCross + b1;
				// 交差点は線分の内側か？
				if ((((double)p1.x < xCross) && (xCross < (double)p2.x)) || (((double)p2.x < xCross) && (xCross < (double)p1.x)))
				{
					ptRet = new Point((int)xCross , (int)yCross);
					stackRet.push((Object)ptRet);
				}
			}
			else		// 縦線に近い線
			{

				// 線分のパラメータ導出
				a2 = dx / dy;
				b2 = (double)p1.x - a2 * (double)p1.y;
				b2 -= (double)0.01;						// ぴったりの場所から 0.01 だけ左にずらす
				if ((1 / a1) == a2)
					continue;
				// 交差点を計算する
				yCross = (a1 * b2 + b1) / (1.0 - a1 * a2);
				xCross = a2 * yCross + b2;
				// 交差点は線分の内側か？
				if ((((double)p1.y < yCross) && (yCross < (double)p2.y)) || (((double)p2.y < yCross) && (yCross < (double)p1.y)))
				{
					ptRet = new Point((int)xCross , (int)yCross);
					stackRet.push((Object)ptRet);
				}
			}

		}
		return stackRet;
	}

	// Point 型をソートする
	private void sortPoint(Point [] pt , int nLen)
	{
		int x,y;
		int i,j;
		for (i = 0 ; i < nLen - 1 ; i++)
		{
			for (j = i + 1 ; j < nLen ; j++)
			{
				if (pt[i].x < pt[j].x)
				{
					x = pt[i].x;
					y = pt[i].y;
					pt[i].x = pt[j].x;
					pt[i].y = pt[j].y;
					pt[j].x = x;
					pt[j].y = y;
				}
			}
		}
	}

}