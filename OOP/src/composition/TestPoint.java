package composition;

public class TestPoint {

	public static void main(String[] args) {
		MyPoint p1 = new MyPoint();
		System.out.println(p1);

		p1.setX(8);
		p1.setY(6);
		System.out.println(p1.distance());
		p1.setXY(2, 3);
		System.out.println(p1);
		System.out.println(p1.distance());

		MyPoint p2 = new MyPoint(0, 4);
		System.out.println(p2);
		System.out.println(p1.distance(p2));
	}

}
