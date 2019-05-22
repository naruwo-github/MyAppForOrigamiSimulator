package my_app_GUI;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class MyFileManager {
	//ここに、出力を書く。
	static void MyWriteOutToSvg(int hnum, List<Integer> cpx, List<Integer> cpy, List<Integer> rux, List<Integer> ruy, List<Integer> orix, List<Integer> oriy){
		int ind = cpx.size() - 1 - 4;
		try {
			PrintWriter pw = new PrintWriter("/Users/naruchan/Desktop/TestWriteOut.svg");
			pw.println("<svg version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" x=\"0px\" y=\"0px\" width=\"1400px\"");
			pw.println("		height=\"1400px\" viewBox=\"0 0 1400 1400\" enable-background=\"new 0 0 1400 1400\" xml:space=\"preserve\">");
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


