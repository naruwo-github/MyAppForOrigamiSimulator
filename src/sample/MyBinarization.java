package sample;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MyBinarization {
	public static void main(String[] args) {
		//
	}
	BufferedImage binarize(String path) throws IOException {
		int tv = 255/2;
		BufferedImage binarized = ImageIO.read(new File(path));
		if(BufferedImage.TYPE_3BYTE_BGR != binarized.getType()) {
			// ２値化
			int x, y;
			int width, height;
			int color, r, g, b;
			int p;
			int newcolor;

			// 画像サイズの取得
			width = binarized.getWidth();
			height= binarized.getHeight();

			for ( y = 0; y < height; ++ y ) {
				for ( x = 0; x < width; ++ x ) {
					// (x,y)の色を取得
					color = binarized.getRGB( x, y );

					// 色をr,g,bに分解
					r = ( color >> 16 ) & 0xff;
					g = ( color >> 8 ) & 0xff;
					b = color & 0xff;

					// rgbの平均値を計算
					p = ( r + g + b ) / 3;

					// ２値化
					if ( tv <= p ) {
						// 閾値tv以上なら白
						r = 255;
						g = 255;
						b = 255;
					}
					else {
						// 閾値tv未満なら黒
						r = 0;
						g = 0;
						b = 0;
					}

					// r,g,bの色を合成
					newcolor = ( r << 16 ) + ( g << 8 ) + b;

					// 合成した色を(x,y)に設定
					binarized.setRGB( x, y, newcolor );
				}
			}
		}
		return binarized;
	}
}
