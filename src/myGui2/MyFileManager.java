package myGui2;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MyFileManager {


	//ファイルからの入力
	static void InputFromTxt(String path, List<List<Integer>> straightx, List<List<Integer>> straighty, List<Color> straightColor, List<Integer> stWidth,
			List<List<Integer>> freex, List<List<Integer>> freey, List<Color> freeColor, List<Integer> freeWidth,
			List<Integer> curvex, List<Integer> curvey, List<Color> curveColor, List<Integer> curveWidth,
			List<int[]> sqx, List<int[]> sqy,
			List<List<Integer>> hatchx, List<List<Integer>> hatchy, List<Color> hatchColor, List<Integer> hatchWidth,
			List<Integer> cpx, List<Integer> cpy, List<List<Integer>> rulingsx, List<List<Integer>> rulingsy,
			List<List<List<Integer>>> orisenx, List<List<List<Integer>>> oriseny, List<List<Boolean>> foldpatern, int foldnum, boolean[] creInfo) {
		try {
			File file = new File(path);
			if (!file.exists()) {
                System.out.print("ファイルが存在しません");
                return;
            }
			FileReader fileReader = new FileReader(file);
            int data;
            while ((data = fileReader.read()) != -1) {
                System.out.print((char) data);
            }
            // 4.最後にファイルを閉じてリソースを開放する
            fileReader.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	//サンプル
	static void inputting(String path, List<Integer> cpx, List<Integer> cpy) {
		List<String> temp = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))){
            String line;
            //ファイルから読み込んだ行を一旦リストに入れておく
            while((line = br.readLine()) != null){
                temp.add(line);
            }
            //文字列を::で分割し，数値解析して配列に格納
            for(int i = 0; i < temp.size(); i++){
                String[] s = temp.get(i).split("::");
                cpx.add(Integer.parseInt(s[0]));
                cpy.add(Integer.parseInt(s[1]));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
		/*
		try {
			File file = new File(path);
			if (!file.exists()) {
                System.out.print("ファイルが存在しません");
                return;
            }
			FileReader fileReader = new FileReader(file);
            int data;
            while ((data = fileReader.read()) != -1) {
                System.out.print((char) data);
            }
            fileReader.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		*/
	}


	//ファイルへの出力
	static void OutputForTxt(String path, List<List<Integer>> straightx, List<List<Integer>> straighty, List<Color> straightColor, List<Integer> stWidth,
			List<List<Integer>> freex, List<List<Integer>> freey, List<Color> freeColor, List<Integer> freeWidth,
			List<Integer> curvex, List<Integer> curvey, List<Color> curveColor, List<Integer> curveWidth,
			List<int[]> sqx, List<int[]> sqy,
			List<List<Integer>> hatchx, List<List<Integer>> hatchy, List<Color> hatchColor, List<Integer> hatchWidth,
			List<Integer> cpx, List<Integer> cpy, List<List<Integer>> rulingsx, List<List<Integer>> rulingsy,
			List<List<List<Integer>>> orisenx, List<List<List<Integer>>> oriseny, List<List<Boolean>> foldpatern, int foldnum, boolean[] creInfo) {
		try {
			PrintWriter pw = new PrintWriter("/Users/naruchan/Desktop/"+path+".txt");
			//ここから記述する。
			//
			//直線ツール
			if(straightx.size() > 0) {
				//
			}
			//
			//自由線ツール
			if(freex.size() > 0) {
				//
			}
			//
			//曲線ツール
			if(curvex.size() > 0) {
				//
			}
			//
			//四角形ツール
			if(sqx.size() > 0) {
				//
			}
			//
			//
			//ハッチング ツール
			if(hatchx.size() > 0) {
				//
			}
			//
			//rulingツール
			if(cpx.size() > 0) {
				//
				for(int i = 0; i < cpx.size(); i++) {
					pw.println(cpx.get(i)+"::"+cpy.get(i));
				}
			}
			//
			//
			pw.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}


	//svgでの出力(新しい方)for simulator
		static void OutputForSimulatorSvg(boolean[] creInfo, List<int[]> sqxlist, List<int[]> sqylist, List<Integer> cpx, List<Integer> cpy, List<Integer> rux, List<Integer> ruy, List<List<Integer>> orix, List<List<Integer>> oriy, List<List<Integer>> straightx, List<List<Integer>> straighty) {
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
						if(creInfo[i] == true) {
						//if(state.get(i) == true) {
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
						//試しに青にする
						//pw.println("	<line fill=\"none\" stroke=\"#0000ff\" x1=\"" + rux.get(i).toString() + "\" y1=\"" + ruy.get(i).toString() + "\" x2=\"" + rux.get(i+1).toString() + "\" y2=\"" + ruy.get(i+1).toString() + "\"/>");
					}

					//直線ツールで追加したやつ
					if(straightx.size() > 0) {
						for(int i = 0; i < straightx.size(); i++) {
							pw.println("	<line fill=\"none\" stroke=\"#ffff00\" x1=\"" + straightx.get(i).get(0).toString() + "\" y1=\"" + straighty.get(i).get(0).toString() + "\" x2=\"" + straightx.get(i).get(1).toString() + "\" y2=\"" + straighty.get(i).get(1).toString() + "\"/>");
							//試しに青にする
							//pw.println("	<line fill=\"none\" stroke=\"#0000ff\" x1=\"" + straightx.get(i).get(0).toString() + "\" y1=\"" + straighty.get(i).get(0).toString() + "\" x2=\"" + straightx.get(i).get(1).toString() + "\" y2=\"" + straighty.get(i).get(1).toString() + "\"/>");
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


	//折り紙シミュレータへの出力ではなく、描画結果を純粋にsvgに出力するメソッド
	static void MyWriteOutToSvg(int hnum, List<Integer> cpx, List<Integer> cpy, List<Integer> rux, List<Integer> ruy, List<Integer> orix, List<Integer> oriy){
		/*
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
		*/
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


