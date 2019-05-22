package prototype_ruling;

import java.util.ArrayList;
import java.util.List;

public class MyHeikatsuka {
	//クリックした座標から一番近いrulingの番号を返すメソッド
		static int returnRulingNum(int mouseX, int mouseY, List<Double> heikatsu, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
			double t = 0;
			int rulingnum = 0;
			double dist = 10000;
			double tmp = 0;
			for(int i = 0; i < heikatsu.size()-1; i++) {
				t += heikatsu.get(i);
				float x = (float) (Math.pow((1-t),3)*x1+3*t*Math.pow((1-t),2)*x2+3*(1-t)*Math.pow(t,2)*x3+Math.pow(t,3)*x4);
			    float y = (float) (Math.pow((1-t),3)*y1+3*t*Math.pow((1-t),2)*y2+3*(1-t)*Math.pow(t,2)*y3+Math.pow(t,3)*y4);
			    tmp = Math.sqrt((mouseX-x)*(mouseX-x)+(mouseY-y)*(mouseY-y));
			    if(tmp < dist) {
			    	dist = tmp;
			    	rulingnum = i;
			    }
			}
			return rulingnum;
		}


		//平滑化行うメソッド(rulingを追加するメソッド)
		static List<Double> heikatsukaAdd(int num, List<Double> heikatsu) {
			List<Double> nextheikatsu = new ArrayList<Double>();
			List<Double> afterheikatsuka = new ArrayList<Double>();
			double presum = 0.0;
			double postsum = 0.0;
			double a = 0.0;

			for(int i = 0; i < heikatsu.size(); i++) {
				if(i == num) {
					nextheikatsu.add(0.0);
				}
				nextheikatsu.add(heikatsu.get(i));
			}

			//平滑化する前の和
			for(int i = 0; i < heikatsu.size(); i++) {
				presum += heikatsu.get(i);
			}

			//平滑化1回目
			afterheikatsuka.add(nextheikatsu.get(0));
			for(int i = 1; i < nextheikatsu.size() - 1; i++) {
				afterheikatsuka.add((nextheikatsu.get(i-1)+nextheikatsu.get(i)+nextheikatsu.get(i+1))/3);
			}
			afterheikatsuka.add((double)nextheikatsu.get(nextheikatsu.size()-1));
			nextheikatsu = afterheikatsuka;

			//平滑化2回目
			afterheikatsuka = new ArrayList<Double>();
			afterheikatsuka.add(nextheikatsu.get(0));
			for(int i = 1; i < nextheikatsu.size() - 1; i++) {
				afterheikatsuka.add((nextheikatsu.get(i-1)+nextheikatsu.get(i)+nextheikatsu.get(i+1))/3);
			}
			afterheikatsuka.add((double)nextheikatsu.get(nextheikatsu.size()-1));
			nextheikatsu = afterheikatsuka;

			//平滑化3回目
			afterheikatsuka = new ArrayList<Double>();
			afterheikatsuka.add(nextheikatsu.get(0));
			for(int i = 1; i < nextheikatsu.size() - 1; i++) {
				afterheikatsuka.add((nextheikatsu.get(i-1)+nextheikatsu.get(i)+nextheikatsu.get(i+1))/3);
			}
			afterheikatsuka.add(nextheikatsu.get(nextheikatsu.size()-1));
			nextheikatsu = afterheikatsuka;

			//平滑化した後の和
			for(int i = 0; i < nextheikatsu.size(); i++) {
				postsum += nextheikatsu.get(i);
			}

			a = presum/postsum;

			for(int i = 0; i < nextheikatsu.size(); i++) {
				nextheikatsu.set(i, nextheikatsu.get(i)*a);
			}

			return nextheikatsu;
		}

		//平滑化行うメソッド(rulingを除去するメソッド)
		static List<Double> heikatsukaSub(int num, List<Double> heikatsu) {
			List<Double> nextheikatsu = new ArrayList<Double>();
			List<Double> afterheikatsuka = new ArrayList<Double>();
			double presum = 0.0;
			double postsum = 0.0;
			double a = 0.0;

			for(int i = 0; i < heikatsu.size(); i++) {
				if(i == num) {
					nextheikatsu.set(i-1, heikatsu.get(i-1)+heikatsu.get(i));
				}else {
					nextheikatsu.add(heikatsu.get(i));
				}
			}

			//平滑化する前の和
			for(int i = 0; i < heikatsu.size(); i++) {
				presum += heikatsu.get(i);
			}

			//平滑化1回目
			afterheikatsuka.add(nextheikatsu.get(0));
			for(int i = 1; i < nextheikatsu.size() - 1; i++) {
				afterheikatsuka.add((nextheikatsu.get(i-1)+nextheikatsu.get(i)+nextheikatsu.get(i+1))/3);
			}
			afterheikatsuka.add((double)nextheikatsu.get(nextheikatsu.size()-1));
			nextheikatsu = afterheikatsuka;

			//平滑化2回目
			afterheikatsuka = new ArrayList<Double>();
			afterheikatsuka.add(nextheikatsu.get(0));
			for(int i = 1; i < nextheikatsu.size() - 1; i++) {
				afterheikatsuka.add((nextheikatsu.get(i-1)+nextheikatsu.get(i)+nextheikatsu.get(i+1))/3);
			}
			afterheikatsuka.add((double)nextheikatsu.get(nextheikatsu.size()-1));
			nextheikatsu = afterheikatsuka;

			//平滑化3回目
			afterheikatsuka = new ArrayList<Double>();
			afterheikatsuka.add(nextheikatsu.get(0));
			for(int i = 1; i < nextheikatsu.size() - 1; i++) {
				afterheikatsuka.add((nextheikatsu.get(i-1)+nextheikatsu.get(i)+nextheikatsu.get(i+1))/3);
			}
			afterheikatsuka.add(nextheikatsu.get(nextheikatsu.size()-1));
			nextheikatsu = afterheikatsuka;

			//平滑化した後の和
			for(int i = 0; i < nextheikatsu.size(); i++) {
				postsum += nextheikatsu.get(i);
			}

			a = presum/postsum;

			for(int i = 0; i < nextheikatsu.size(); i++) {
				nextheikatsu.set(i, nextheikatsu.get(i)*a);
			}

			return nextheikatsu;
		}


		static List<Double> gokoHeikinAdd(int num, List<Double> heikatsu) {
			List<Double> nextheikatsu = new ArrayList<Double>();
			List<Double> afterheikatsuka = new ArrayList<Double>();
			double presum = 0.0;
			double postsum = 0.0;
			double a = 0.0;

			for(int i = 0; i < heikatsu.size(); i++) {
				if(i == num) {
					nextheikatsu.add(0.0);
				}
				nextheikatsu.add(heikatsu.get(i));
			}

			//平滑化する前の和
			for(int i = 0; i < heikatsu.size(); i++) {
				presum += heikatsu.get(i);
			}

			if(heikatsu.size() < 5) {
				return heikatsu;
			}else {
				//平滑化1回目
				afterheikatsuka.add(nextheikatsu.get(0));
				afterheikatsuka.add(nextheikatsu.get(1));
				for(int i = 2; i < nextheikatsu.size() - 2; i++) {
					afterheikatsuka.add((nextheikatsu.get(i-2)+nextheikatsu.get(i-1)+nextheikatsu.get(i)+nextheikatsu.get(i+1)+nextheikatsu.get(i+2))/5);
				}
				afterheikatsuka.add((double)nextheikatsu.get(nextheikatsu.size()-2));
				afterheikatsuka.add((double)nextheikatsu.get(nextheikatsu.size()-1));
				nextheikatsu = afterheikatsuka;

				//平滑化2回目
				afterheikatsuka = new ArrayList<Double>();
				afterheikatsuka.add(nextheikatsu.get(0));
				afterheikatsuka.add(nextheikatsu.get(1));
				for(int i = 2; i < nextheikatsu.size() - 2; i++) {
					afterheikatsuka.add((nextheikatsu.get(i-2)+nextheikatsu.get(i-1)+nextheikatsu.get(i)+nextheikatsu.get(i+1)+nextheikatsu.get(i+2))/5);
				}
				afterheikatsuka.add((double)nextheikatsu.get(nextheikatsu.size()-2));
				afterheikatsuka.add((double)nextheikatsu.get(nextheikatsu.size()-1));
				nextheikatsu = afterheikatsuka;

				//平滑化3回目
				afterheikatsuka = new ArrayList<Double>();
				afterheikatsuka.add(nextheikatsu.get(0));
				afterheikatsuka.add(nextheikatsu.get(1));
				for(int i = 2; i < nextheikatsu.size() - 2; i++) {
					afterheikatsuka.add((nextheikatsu.get(i-2)+nextheikatsu.get(i-1)+nextheikatsu.get(i)+nextheikatsu.get(i+1)+nextheikatsu.get(i+2))/5);
				}
				afterheikatsuka.add((double)nextheikatsu.get(nextheikatsu.size()-2));
				afterheikatsuka.add((double)nextheikatsu.get(nextheikatsu.size()-1));
				nextheikatsu = afterheikatsuka;

				//平滑化した後の和
				for(int i = 0; i < nextheikatsu.size(); i++) {
					postsum += nextheikatsu.get(i);
				}

				a = presum/postsum;

				for(int i = 0; i < nextheikatsu.size(); i++) {
					nextheikatsu.set(i, nextheikatsu.get(i)*a);
				}

				return nextheikatsu;
			}
		}

		//平滑化行うメソッド(rulingを除去するメソッド)
		static List<Double> gokoHeikinSub(int num, List<Double> heikatsu) {
			List<Double> nextheikatsu = new ArrayList<Double>();
			List<Double> afterheikatsuka = new ArrayList<Double>();
			double presum = 0.0;
			double postsum = 0.0;
			double a = 0.0;

			for(int i = 0; i < heikatsu.size(); i++) {
				if(i == num) {
					nextheikatsu.set(i-1, heikatsu.get(i-1)+heikatsu.get(i));
				}else {
					nextheikatsu.add(heikatsu.get(i));
				}
			}

			//平滑化する前の和
			for(int i = 0; i < heikatsu.size(); i++) {
				presum += heikatsu.get(i);
			}

			if(heikatsu.size() < 5) {
				return heikatsu;
			}else {
				//平滑化1回目
				afterheikatsuka.add(nextheikatsu.get(0));
				afterheikatsuka.add(nextheikatsu.get(1));
				for(int i = 2; i < nextheikatsu.size() - 2; i++) {
					afterheikatsuka.add((nextheikatsu.get(i-2)+nextheikatsu.get(i-1)+nextheikatsu.get(i)+nextheikatsu.get(i+1)+nextheikatsu.get(i+2))/5);
				}
				afterheikatsuka.add((double)nextheikatsu.get(nextheikatsu.size()-2));
				afterheikatsuka.add((double)nextheikatsu.get(nextheikatsu.size()-1));
				nextheikatsu = afterheikatsuka;

				//平滑化2回目
				afterheikatsuka = new ArrayList<Double>();
				afterheikatsuka.add(nextheikatsu.get(0));
				afterheikatsuka.add(nextheikatsu.get(1));
				for(int i = 2; i < nextheikatsu.size() - 2; i++) {
					afterheikatsuka.add((nextheikatsu.get(i-2)+nextheikatsu.get(i-1)+nextheikatsu.get(i)+nextheikatsu.get(i+1)+nextheikatsu.get(i+2))/5);
				}
				afterheikatsuka.add((double)nextheikatsu.get(nextheikatsu.size()-2));
				afterheikatsuka.add((double)nextheikatsu.get(nextheikatsu.size()-1));
				nextheikatsu = afterheikatsuka;

				//平滑化3回目
				afterheikatsuka = new ArrayList<Double>();
				afterheikatsuka.add(nextheikatsu.get(0));
				afterheikatsuka.add(nextheikatsu.get(1));
				for(int i = 2; i < nextheikatsu.size() - 2; i++) {
					afterheikatsuka.add((nextheikatsu.get(i-2)+nextheikatsu.get(i-1)+nextheikatsu.get(i)+nextheikatsu.get(i+1)+nextheikatsu.get(i+2))/5);
				}
				afterheikatsuka.add((double)nextheikatsu.get(nextheikatsu.size()-2));
				afterheikatsuka.add((double)nextheikatsu.get(nextheikatsu.size()-1));
				nextheikatsu = afterheikatsuka;

				//平滑化した後の和
				for(int i = 0; i < nextheikatsu.size(); i++) {
					postsum += nextheikatsu.get(i);
				}

				a = presum/postsum;

				for(int i = 0; i < nextheikatsu.size(); i++) {
					nextheikatsu.set(i, nextheikatsu.get(i)*a);
				}

				return nextheikatsu;
			}
		}

}



/*
		//3こう平滑化
		//平滑化行うメソッド(rulingを追加するメソッド)
		static List<Double> heikatsukaAdd(int num, List<Double> heikatsu) {
			List<Double> nextheikatsu = new ArrayList<Double>();
			List<Double> afterheikatsuka = new ArrayList<Double>();
			double presum = 0.0;
			double postsum = 0.0;
			double a = 0.0;

			for(int i = 0; i < heikatsu.size(); i++) {
				if(i == num) {
					nextheikatsu.add(0.0);
				}
				nextheikatsu.add(heikatsu.get(i));
			}

			//平滑化する前の和
			for(int i = 0; i < heikatsu.size(); i++) {
				presum += heikatsu.get(i);
			}

			//平滑化1回目
			afterheikatsuka.add(nextheikatsu.get(0));
			for(int i = 1; i < nextheikatsu.size() - 1; i++) {
				afterheikatsuka.add((nextheikatsu.get(i-1)+nextheikatsu.get(i)+nextheikatsu.get(i+1))/3);
			}
			afterheikatsuka.add((double)nextheikatsu.get(nextheikatsu.size()-1));
			nextheikatsu = afterheikatsuka;

			//平滑化2回目
			afterheikatsuka = new ArrayList<Double>();
			afterheikatsuka.add(nextheikatsu.get(0));
			for(int i = 1; i < nextheikatsu.size() - 1; i++) {
				afterheikatsuka.add((nextheikatsu.get(i-1)+nextheikatsu.get(i)+nextheikatsu.get(i+1))/3);
			}
			afterheikatsuka.add((double)nextheikatsu.get(nextheikatsu.size()-1));
			nextheikatsu = afterheikatsuka;

			//平滑化3回目
			afterheikatsuka = new ArrayList<Double>();
			afterheikatsuka.add(nextheikatsu.get(0));
			for(int i = 1; i < nextheikatsu.size() - 1; i++) {
				afterheikatsuka.add((nextheikatsu.get(i-1)+nextheikatsu.get(i)+nextheikatsu.get(i+1))/3);
			}
			afterheikatsuka.add(nextheikatsu.get(nextheikatsu.size()-1));
			nextheikatsu = afterheikatsuka;

			//平滑化4回目
			afterheikatsuka = new ArrayList<Double>();
			afterheikatsuka.add(nextheikatsu.get(0));
			for(int i = 1; i < nextheikatsu.size() - 1; i++) {
				afterheikatsuka.add((nextheikatsu.get(i-1)+nextheikatsu.get(i)+nextheikatsu.get(i+1))/3);
			}
			afterheikatsuka.add((double)nextheikatsu.get(nextheikatsu.size()-1));
			nextheikatsu = afterheikatsuka;

			//平滑化5回目
			afterheikatsuka = new ArrayList<Double>();
			afterheikatsuka.add(nextheikatsu.get(0));
			for(int i = 1; i < nextheikatsu.size() - 1; i++) {
				afterheikatsuka.add((nextheikatsu.get(i-1)+nextheikatsu.get(i)+nextheikatsu.get(i+1))/3);
			}
			afterheikatsuka.add(nextheikatsu.get(nextheikatsu.size()-1));
			nextheikatsu = afterheikatsuka;

			//平滑化6回目
			afterheikatsuka = new ArrayList<Double>();
			afterheikatsuka.add(nextheikatsu.get(0));
			for(int i = 1; i < nextheikatsu.size() - 1; i++) {
				afterheikatsuka.add((nextheikatsu.get(i-1)+nextheikatsu.get(i)+nextheikatsu.get(i+1))/3);
			}
			afterheikatsuka.add((double)nextheikatsu.get(nextheikatsu.size()-1));
			nextheikatsu = afterheikatsuka;

			//平滑化7回目
			afterheikatsuka = new ArrayList<Double>();
			afterheikatsuka.add(nextheikatsu.get(0));
			for(int i = 1; i < nextheikatsu.size() - 1; i++) {
				afterheikatsuka.add((nextheikatsu.get(i-1)+nextheikatsu.get(i)+nextheikatsu.get(i+1))/3);
			}
			afterheikatsuka.add(nextheikatsu.get(nextheikatsu.size()-1));
			nextheikatsu = afterheikatsuka;

			//平滑化した後の和
			for(int i = 0; i < nextheikatsu.size(); i++) {
				postsum += nextheikatsu.get(i);
			}

			a = presum/postsum;

			for(int i = 0; i < nextheikatsu.size(); i++) {
				nextheikatsu.set(i, nextheikatsu.get(i)*a);
			}

			return nextheikatsu;
		}

		//平滑化行うメソッド(rulingを除去するメソッド)
		static List<Double> heikatsukaSub(int num, List<Double> heikatsu) {
			List<Double> nextheikatsu = new ArrayList<Double>();
			List<Double> afterheikatsuka = new ArrayList<Double>();
			double presum = 0.0;
			double postsum = 0.0;
			double a = 0.0;

			for(int i = 0; i < heikatsu.size(); i++) {
				if(i == num) {
					nextheikatsu.set(i-1, heikatsu.get(i-1)+heikatsu.get(i));
				}else {
					nextheikatsu.add(heikatsu.get(i));
				}
			}

			//平滑化する前の和
			for(int i = 0; i < heikatsu.size(); i++) {
				presum += heikatsu.get(i);
			}

			//平滑化1回目
			afterheikatsuka.add(nextheikatsu.get(0));
			for(int i = 1; i < nextheikatsu.size() - 1; i++) {
				afterheikatsuka.add((nextheikatsu.get(i-1)+nextheikatsu.get(i)+nextheikatsu.get(i+1))/3);
			}
			afterheikatsuka.add((double)nextheikatsu.get(nextheikatsu.size()-1));
			nextheikatsu = afterheikatsuka;

			//平滑化2回目
			afterheikatsuka = new ArrayList<Double>();
			afterheikatsuka.add(nextheikatsu.get(0));
			for(int i = 1; i < nextheikatsu.size() - 1; i++) {
				afterheikatsuka.add((nextheikatsu.get(i-1)+nextheikatsu.get(i)+nextheikatsu.get(i+1))/3);
			}
			afterheikatsuka.add((double)nextheikatsu.get(nextheikatsu.size()-1));
			nextheikatsu = afterheikatsuka;

			//平滑化3回目
			afterheikatsuka = new ArrayList<Double>();
			afterheikatsuka.add(nextheikatsu.get(0));
			for(int i = 1; i < nextheikatsu.size() - 1; i++) {
				afterheikatsuka.add((nextheikatsu.get(i-1)+nextheikatsu.get(i)+nextheikatsu.get(i+1))/3);
			}
			afterheikatsuka.add(nextheikatsu.get(nextheikatsu.size()-1));
			nextheikatsu = afterheikatsuka;

			//平滑化4回目
			afterheikatsuka = new ArrayList<Double>();
			afterheikatsuka.add(nextheikatsu.get(0));
			for(int i = 1; i < nextheikatsu.size() - 1; i++) {
				afterheikatsuka.add((nextheikatsu.get(i-1)+nextheikatsu.get(i)+nextheikatsu.get(i+1))/3);
			}
			afterheikatsuka.add((double)nextheikatsu.get(nextheikatsu.size()-1));
			nextheikatsu = afterheikatsuka;

			//平滑化5回目
			afterheikatsuka = new ArrayList<Double>();
			afterheikatsuka.add(nextheikatsu.get(0));
			for(int i = 1; i < nextheikatsu.size() - 1; i++) {
				afterheikatsuka.add((nextheikatsu.get(i-1)+nextheikatsu.get(i)+nextheikatsu.get(i+1))/3);
			}
			afterheikatsuka.add(nextheikatsu.get(nextheikatsu.size()-1));
			nextheikatsu = afterheikatsuka;

			//平滑化6回目
			afterheikatsuka = new ArrayList<Double>();
			afterheikatsuka.add(nextheikatsu.get(0));
			for(int i = 1; i < nextheikatsu.size() - 1; i++) {
				afterheikatsuka.add((nextheikatsu.get(i-1)+nextheikatsu.get(i)+nextheikatsu.get(i+1))/3);
			}
			afterheikatsuka.add((double)nextheikatsu.get(nextheikatsu.size()-1));
			nextheikatsu = afterheikatsuka;

			//平滑化7回目
			afterheikatsuka = new ArrayList<Double>();
			afterheikatsuka.add(nextheikatsu.get(0));
			for(int i = 1; i < nextheikatsu.size() - 1; i++) {
				afterheikatsuka.add((nextheikatsu.get(i-1)+nextheikatsu.get(i)+nextheikatsu.get(i+1))/3);
			}
			afterheikatsuka.add(nextheikatsu.get(nextheikatsu.size()-1));
			nextheikatsu = afterheikatsuka;

			//平滑化した後の和
			for(int i = 0; i < nextheikatsu.size(); i++) {
				postsum += nextheikatsu.get(i);
			}

			a = presum/postsum;

			for(int i = 0; i < nextheikatsu.size(); i++) {
				nextheikatsu.set(i, nextheikatsu.get(i)*a);
			}

			return nextheikatsu;
		}
		*/
