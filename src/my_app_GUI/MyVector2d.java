package my_app_GUI;


class MyVector2d {
	double x, y;
	MyVector2d(double _x, double _y){
		x = _x;
		y = _y;
	}
	void set(double _x, double _y){
		x = _x;
		y = _y;
	}

	//長さを1に正規化する
	void normalize() {
		double len = length();
		x /= len;
		y /= len;
	}

	//長さを返す
	double length() {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}

	//s倍する
	void scale(double s) {
		x *= s;
		y *= s;
	}

	//加算の定義
	MyVector2d add(MyVector2d v) {
		MyVector2d v0 = new MyVector2d(x+v.x, y+v.y);
		return v0;
	}

	//減算の定義
	MyVector2d sub(MyVector2d v) {
		MyVector2d v0 = new MyVector2d(x-v.x, y-v.y);
		return v0;
	}

	//内積の定義
	double naiseki(MyVector2d v) {
		return x*v.x + y*v.y;
	}

	//割り算の定義
		void div(double s) {
			x = x/s;
			y = y/s;
		}
}
