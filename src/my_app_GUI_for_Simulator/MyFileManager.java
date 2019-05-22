package my_app_GUI_for_Simulator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class MyFileManager {
	//svgでの出力(新しい方)for simulator
	static void OutputForSimulatorSvg(List<Boolean> state, List<int[]> sqxlist, List<int[]> sqylist, List<Integer> cpx, List<Integer> cpy, List<Integer> rux, List<Integer> ruy, List<List<Integer>> orix, List<List<Integer>> oriy, List<List<Integer>> straightx, List<List<Integer>> straighty) {
		/*
		double bisector = Math.sqrt(Math.pow(sqx[0] - sqx[1], 2) + Math.pow(sqy[0] - sqy[1], 2));
		//√2
		double root_two = Math.sqrt(2);
		//正方形一辺の長さ
		int side_length = (int) (bisector/root_two);
		int bx = sqx[0] + side_length;
		int cy = sqy[0] + side_length;
		*/
		/*
		g.drawLine(x[0], y[0], bx, y[0]);
		g.drawLine(x[0], y[0], x[0], cy);
		g.drawLine(bx, y[0], bx, cy);
		g.drawLine(x[0], cy, bx, cy);
		*/

		//int ind = cpx.size() - 1 - 4;
		//int roopnum = (cpx.size() - 19)/8 + 1;
		try {
			PrintWriter pw = new PrintWriter("/Users/naruchan/Desktop/TestWriteOut.svg");
			pw.println("<svg version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" x=\"0px\" y=\"0px\" width=\"1400px\"");
			pw.println("		height=\"1200px\" viewBox=\"0 0 1400 1400\" enable-background=\"new 0 0 1400 1400\" xml:space=\"preserve\">");

			if(sqxlist.size() > 0) {
				pw.println("<g id=\"レイヤー_1\">");
				pw.println("");
				for(int i = 0; i < sqxlist.size(); i++) {
					int[] x = sqxlist.get(i);
					int[] y = sqylist.get(i);

					//折り線を書き出す部分(黒)
					pw.println("	<line fill=\"none\" stroke=\"#000000\" x1=\"" + String.valueOf(x[0]) + "\" y1=\"" + String.valueOf(y[0]) + "\" x2=\"" + String.valueOf(x[1]) + "\" y2=\"" + String.valueOf(y[1]) + "\"/>");
					pw.println("	<line fill=\"none\" stroke=\"#000000\" x1=\"" + String.valueOf(x[0]) + "\" y1=\"" + String.valueOf(y[0]) + "\" x2=\"" + String.valueOf(x[2]) + "\" y2=\"" + String.valueOf(y[2]) + "\"/>");
					pw.println("	<line fill=\"none\" stroke=\"#000000\" x1=\"" + String.valueOf(x[1]) + "\" y1=\"" + String.valueOf(y[1]) + "\" x2=\"" + String.valueOf(x[3]) + "\" y2=\"" + String.valueOf(y[3]) + "\"/>");
					pw.println("	<line fill=\"none\" stroke=\"#000000\" x1=\"" + String.valueOf(x[2]) + "\" y1=\"" + String.valueOf(y[2]) + "\" x2=\"" + String.valueOf(x[3]) + "\" y2=\"" + String.valueOf(y[3]) + "\"/>");
				}
				pw.println("</g>");
			}else {
				int num = cpx.size();
				if(num > 0 && num%4 == 0) {
					pw.println("<g id=\"レイヤー_1\">");
					pw.println("");
					pw.println("	<line fill=\"none\" stroke=\"#000000\" x1=\"" + String.valueOf(cpx.get(0)) + "\" y1=\"" + String.valueOf(cpy.get(0)) + "\" x2=\"" + String.valueOf(cpx.get(num-4)) + "\" y2=\"" + String.valueOf(cpy.get(num-4)) + "\"/>");
					pw.println("	<line fill=\"none\" stroke=\"#000000\" x1=\"" + String.valueOf(cpx.get(3)) + "\" y1=\"" + String.valueOf(cpy.get(3)) + "\" x2=\"" + String.valueOf(cpx.get(num-1)) + "\" y2=\"" + String.valueOf(cpy.get(num-1)) + "\"/>");
					for(float t = 0; t <= 1.0 - 0.05; t += 0.05) {
						float tt = (float) (t + 0.05);
						//1本目
					    float x = (float) (Math.pow((1-t),3)*cpx.get(0)+3*t*Math.pow((1-t),2)*cpx.get(1)+3*(1-t)*Math.pow(t,2)*cpx.get(2)+Math.pow(t,3)*cpx.get(3));
					    float y = (float) (Math.pow((1-t),3)*cpy.get(0)+3*t*Math.pow((1-t),2)*cpy.get(1)+3*(1-t)*Math.pow(t,2)*cpy.get(2)+Math.pow(t,3)*cpy.get(3));
					    float xx = (float) (Math.pow((1-tt),3)*cpx.get(0)+3*tt*Math.pow((1-tt),2)*cpx.get(2)+3*(1-tt)*Math.pow(tt,2)*cpx.get(2)+Math.pow(tt,3)*cpx.get(3));
					    float yy = (float) (Math.pow((1-tt),3)*cpy.get(0)+3*tt*Math.pow((1-tt),2)*cpy.get(1)+3*(1-tt)*Math.pow(tt,2)*cpy.get(2)+Math.pow(tt,3)*cpy.get(3));
					    //反対側
					    float x1 = (float) (Math.pow((1-t),3)*cpx.get(num-4)+3*t*Math.pow((1-t),2)*cpx.get(num-3)+3*(1-t)*Math.pow(t,2)*cpx.get(num-2)+Math.pow(t,3)*cpx.get(num-1));
					    float y1 = (float) (Math.pow((1-t),3)*cpy.get(num-4)+3*t*Math.pow((1-t),2)*cpy.get(num-3)+3*(1-t)*Math.pow(t,2)*cpy.get(num-2)+Math.pow(t,3)*cpy.get(3));
					    float xx1 = (float) (Math.pow((1-tt),3)*cpx.get(num-4)+3*tt*Math.pow((1-tt),2)*cpx.get(num-3)+3*(1-tt)*Math.pow(tt,2)*cpx.get(num-2)+Math.pow(tt,3)*cpx.get(num-1));
					    float yy1 = (float) (Math.pow((1-tt),3)*cpy.get(num-4)+3*tt*Math.pow((1-tt),2)*cpy.get(num-3)+3*(1-tt)*Math.pow(tt,2)*cpy.get(num-2)+Math.pow(tt,3)*cpy.get(num-1));
					    //折り線を書き出す部分(黒)
						pw.println("	<line fill=\"none\" stroke=\"#000000\" x1=\"" + String.valueOf((int)x) + "\" y1=\"" + String.valueOf((int)y) + "\" x2=\"" + String.valueOf((int)xx) + "\" y2=\"" + String.valueOf((int)yy) + "\"/>");
						pw.println("	<line fill=\"none\" stroke=\"#000000\" x1=\"" + String.valueOf((int)x1) + "\" y1=\"" + String.valueOf((int)y1) + "\" x2=\"" + String.valueOf((int)xx1) + "\" y2=\"" + String.valueOf((int)yy1) + "\"/>");
					}
					pw.println("</g>");
				}
			}

			if(cpx.size() >= 12 && cpx.size() != 0) {
				int roopnum = (cpx.size() - 19)/8 + 1;
				pw.println("");
				pw.println("<g id=\"レイヤー_2\">");
				//オリセンを書き出す部分
				for(int i = 0; i < roopnum; i++) { //オリセンの数だけループ回るよ
					if(state.get(i) == true) {
						pw.println("	<line fill=\"none\" stroke=\"#ff0000\" x1=\"" + cpx.get(i*8+4).toString() + "\" y1=\"" + cpy.get(i*8+4).toString() + "\" x2=\"" + orix.get(i).get(0).toString() + "\" y2=\"" + oriy.get(i).get(0).toString() + "\"/>");
						for(int j = 0; j < orix.get(i).size() - 1; j++) {
							//
							pw.println("	<line fill=\"none\" stroke=\"#ff0000\" x1=\"" + orix.get(i).get(j).toString() + "\" y1=\"" + oriy.get(i).get(j).toString() + "\" x2=\"" + orix.get(i).get(j+1).toString() + "\" y2=\"" + oriy.get(i).get(j+1).toString() + "\"/>");
							//
						}
						pw.println("	<line fill=\"none\" stroke=\"#ff0000\" x1=\"" + orix.get(i).get(orix.get(i).size()-1).toString() + "\" y1=\"" + oriy.get(i).get(oriy.get(i).size()-1).toString() + "\" x2=\"" + cpx.get(i*8+7).toString() + "\" y2=\"" + cpy.get(i*8+7).toString() + "\"/>");
					}else {
						pw.println("	<line fill=\"none\" stroke=\"#0000ff\" x1=\"" + cpx.get(i*8+4).toString() + "\" y1=\"" + cpy.get(i*8+4).toString() + "\" x2=\"" + orix.get(i).get(0).toString() + "\" y2=\"" + oriy.get(i).get(0).toString() + "\"/>");
						for(int j = 0; j < orix.get(i).size() - 1; j++) {
							//
							pw.println("	<line fill=\"none\" stroke=\"#0000ff\" x1=\"" + orix.get(i).get(j).toString() + "\" y1=\"" + oriy.get(i).get(j).toString() + "\" x2=\"" + orix.get(i).get(j+1).toString() + "\" y2=\"" + oriy.get(i).get(j+1).toString() + "\"/>");
							//
						}
						pw.println("	<line fill=\"none\" stroke=\"#0000ff\" x1=\"" + orix.get(i).get(orix.get(i).size()-1).toString() + "\" y1=\"" + oriy.get(i).get(oriy.get(i).size()-1).toString() + "\" x2=\"" + cpx.get(i*8+7).toString() + "\" y2=\"" + cpy.get(i*8+7).toString() + "\"/>");
					}
				}
				pw.println("</g>");
				pw.println("");


				pw.println("<g id=\"レイヤー_3\">");
				//rulingを書き出す部分(黄色)
				for(int i = 0; i < rux.size() - 2; i+=2) {
					pw.println("	<line fill=\"none\" stroke=\"#ffff00\" x1=\"" + rux.get(i).toString() + "\" y1=\"" + ruy.get(i).toString() + "\" x2=\"" + rux.get(i+1).toString() + "\" y2=\"" + ruy.get(i+1).toString() + "\"/>");
				}

				//直線ツールで追加したやつ
				if(straightx.size() > 0) {
					for(int i = 0; i < straightx.size(); i++) {
						pw.println("	<line fill=\"none\" stroke=\"#ffff00\" x1=\"" + straightx.get(i).get(0).toString() + "\" y1=\"" + straighty.get(i).get(0).toString() + "\" x2=\"" + straightx.get(i).get(1).toString() + "\" y2=\"" + straighty.get(i).get(1).toString() + "\"/>");
					}
				}

				pw.println("</g>");
			}
				pw.println("</svg>");
				pw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
	}

	//ここに、出力を書く。
	static void MyWriteOutToSvg(/*int hnum, */List<Integer> cpx, List<Integer> cpy, List<Integer> rux, List<Integer> ruy, List<Integer> orix, List<Integer> oriy){
		int ind = cpx.size() - 1 - 4;
		try {
			PrintWriter pw = new PrintWriter("/Users/naruchan/Desktop/TestWriteOut.svg");
			/*
			//輪郭線を書き出す部分
			pw.println("<svg version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" x=\"0px\" y=\"0px\" width=\"1200px\"");
			pw.println("		height=\"1200px\" viewBox=\"0 0 1200 1200\" enable-background=\"new 0 0 1200 1200\" xml:space=\"preserve\">");
			pw.println("<g id=\"レイヤー_1\">");
			pw.println("");
			pw.println("	<line fill=\"none\" stroke=\"#000000\" x1=\"" + cpx.get(0).toString() + "\" y1=\"" + cpy.get(0).toString() + "\" x2=\"" + cpx.get(3).toString() + "\" y2=\"" + cpy.get(3).toString() + "\"/>");
			pw.println("	<line fill=\"none\" stroke=\"#000000\" x1=\"" + cpx.get(0).toString() + "\" y1=\"" + cpy.get(0).toString() + "\" x2=\"" + cpx.get(ind-3).toString() + "\" y2=\"" + cpy.get(ind-3).toString() + "\"/>");
			pw.println("	<line fill=\"none\" stroke=\"#000000\" x1=\"" + cpx.get(3).toString() + "\" y1=\"" + cpy.get(3).toString() + "\" x2=\"" + cpx.get(ind).toString() + "\" y2=\"" + cpy.get(ind).toString() + "\"/>");
			pw.println("	<line fill=\"none\" stroke=\"#000000\" x1=\"" + cpx.get(ind-3).toString() + "\" y1=\"" + cpy.get(ind-3).toString() + "\" x2=\"" + cpx.get(ind).toString() + "\" y2=\"" + cpy.get(ind).toString() + "\"/>");
			pw.println("</g>");

			pw.println("");
			pw.println("<g id=\"レイヤー_2\">");
			*/

			pw.println("<svg version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" x=\"0px\" y=\"0px\" width=\"1200px\"");
			pw.println("		height=\"1200px\" viewBox=\"0 0 1200 1200\" enable-background=\"new 0 0 1200 1200\" xml:space=\"preserve\">");
			pw.println("<g id=\"レイヤー_1\">");
			pw.println("");
			pw.println("	<line fill=\"none\" stroke=\"#000000\" x1=\"" + cpx.get(0).toString() + "\" y1=\"" + cpy.get(0).toString() + "\" x2=\"" + cpx.get(3).toString() + "\" y2=\"" + cpy.get(3).toString() + "\"/>");
			pw.println("	<line fill=\"none\" stroke=\"#000000\" x1=\"" + cpx.get(0).toString() + "\" y1=\"" + cpy.get(0).toString() + "\" x2=\"" + cpx.get(ind-3).toString() + "\" y2=\"" + cpy.get(ind-3).toString() + "\"/>");
			pw.println("	<line fill=\"none\" stroke=\"#000000\" x1=\"" + cpx.get(3).toString() + "\" y1=\"" + cpy.get(3).toString() + "\" x2=\"" + cpx.get(ind).toString() + "\" y2=\"" + cpy.get(ind).toString() + "\"/>");
			pw.println("	<line fill=\"none\" stroke=\"#000000\" x1=\"" + cpx.get(ind-3).toString() + "\" y1=\"" + cpy.get(ind-3).toString() + "\" x2=\"" + cpx.get(ind).toString() + "\" y2=\"" + cpy.get(ind).toString() + "\"/>");
			pw.println("</g>");

			pw.println("");
			pw.println("<g id=\"レイヤー_2\">");

			/*
			//オリセンを書き出す部分(赤)
			for(int i = 0; i < rux.size() - 2 - 2; i+=2) {
				pw.println("	<line fill=\"none\" stroke=\"#ff0000\" x1=\"" + rux.get(i+1).toString() + "\" y1=\"" + ruy.get(i+1).toString() + "\" x2=\"" + rux.get(i+3).toString() + "\" y2=\"" + ruy.get(i+3).toString() + "\"/>");
			}
			*/
			//オリセンを書き出す部分(とりあえず赤)
			pw.println("	<line fill=\"none\" stroke=\"#ff0000\" x1=\"" + cpx.get(4).toString() + "\" y1=\"" + cpy.get(4).toString() + "\" x2=\"" + orix.get(0).toString() + "\" y2=\"" + oriy.get(0).toString() + "\"/>");
			for(int i = 0; i < orix.size() - 1; i++) {
				pw.println("	<line fill=\"none\" stroke=\"#ff0000\" x1=\"" + orix.get(i).toString() + "\" y1=\"" + oriy.get(i).toString() + "\" x2=\"" + orix.get(i+1).toString() + "\" y2=\"" + oriy.get(i+1).toString() + "\"/>");
			}
			pw.println("	<line fill=\"none\" stroke=\"#ff0000\" x1=\"" + orix.get(orix.size()-1).toString() + "\" y1=\"" + oriy.get(oriy.size()-1).toString() + "\" x2=\"" + cpx.get(7).toString() + "\" y2=\"" + cpy.get(7).toString() + "\"/>");
			pw.println("</g>");
			pw.println("");
			pw.println("<g id=\"レイヤー_3\">");

			//rulingを書き出す部分(黄色)
			for(int i = 0; i < rux.size() - 2; i+=2) {
				pw.println("	<line fill=\"none\" stroke=\"#ffff00\" x1=\"" + rux.get(i).toString() + "\" y1=\"" + ruy.get(i).toString() + "\" x2=\"" + rux.get(i+1).toString() + "\" y2=\"" + ruy.get(i+1).toString() + "\"/>");
			}

			pw.println("</g>");
			pw.println("</svg>");
			pw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
	}

	//一応Html用のやつ
	static void MyWriteOutToHtml(List<Integer> cpx, List<Integer> cpy, List<Integer> rulingx, List<Integer> rulingy, List<Integer> orisenx, List<Integer> oriseny){
		int ind = cpx.size() - 1 - 4;
		try {
			PrintWriter pw = new PrintWriter("/Users/naruchan/Desktop/TestWriteOut.html");
			pw.println("<html>");
			pw.println("this is a sample code.");
			pw.println("	<body>");
			pw.println("		<svg width=\"1000\" height=\"1000\">");


			pw.println("			<line x1=\""+cpx.get(0).toString()+"\" y1=\""+cpy.get(0).toString()+"\" x2=\""+cpx.get(3).toString()+"\" y2=\""+cpy.get(3).toString()+"\" stroke=\"#000\"></line>");
			pw.println("			<line x1=\""+cpx.get(0).toString()+"\" y1=\""+cpy.get(0).toString()+"\" x2=\""+cpx.get(ind-3).toString()+"\" y2=\""+cpy.get(ind-3).toString()+"\" stroke=\"#000\"></line>");
			pw.println("			<line x1=\""+cpx.get(3).toString()+"\" y1=\""+cpy.get(3).toString()+"\" x2=\""+cpx.get(ind).toString()+"\" y2=\""+cpy.get(ind).toString()+"\" stroke=\"#000\"></line>");
			pw.println("			<line x1=\""+cpx.get(ind-3).toString()+"\" y1=\""+cpy.get(ind-3).toString()+"\" x2=\""+cpx.get(ind).toString()+"\" y2=\""+cpy.get(ind).toString()+"\" stroke=\"#000\"></line>");


			//rulingを書き出す部分(黄色)
			for(int i = 0; i < rulingx.size(); i++) {
				pw.println("			<line x1=\""+cpx.get(0).toString()+"\" y1=\""+cpy.get(0).toString()+"\" x2=\""+cpx.get(3).toString()+"\" y2=\""+cpy.get(3).toString()+"\" stroke=\"#000\"></line>");
			}
			//オリセンを書き出す部分(とりあえず赤)
			for(int i = 0; i < orisenx.size(); i++) {
				pw.println("			<line x1=\""+cpx.get(0).toString()+"\" y1=\""+cpy.get(0).toString()+"\" x2=\""+cpx.get(3).toString()+"\" y2=\""+cpy.get(3).toString()+"\" stroke=\"#000\"></line>");
			}

			pw.println("		</svg>");
			pw.println("	</body>");
			pw.println("</html>");
			pw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
	}
}

/*
<line x1="50" y1="50" x2="200" y2="150" stroke="#000"></line>
<line x1="0" y1="0" x2="150" y2="150" stroke="black" stroke-width="1" />
<text x="100" y="75">Hello World</text>
<rect x="10" y="10" width="100" height="50" stroke="black" stroke-width="1" fill="none" />
<circle cx="150" cy="75" r="20" stroke="black" stroke-width="1" fill="none" />
<ellipse cx="150" cy="75" rx="30" ry="20" stroke="black" stroke-width="1" fill="none" />


うまく行ったやつ
<svg version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px" width="800px"
	 height="800px" viewBox="0 0 800 800" enable-background="new 0 0 800 800" xml:space="preserve">
<g id="レイヤー_1">
	<line fill="none" stroke="#000000" x1="100" y1="100" x2="700" y2="100"/>
	<line fill="none" stroke="#000000" x1="100" y1="100" x2="100" y2="700"/>
	<line fill="none" stroke="#000000" x1="100" y1="700" x2="700" y2="700"/>
	<line fill="none" stroke="#000000" x1="700" y1="100" x2="700" y2="700"/>
</g>
<g id="レイヤー_2">
	<line fill="none" stroke="#FF0000" x1="400" y1="100" x2="400" y2="700"/>
</g>
</svg>
*/



