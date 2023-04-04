package sys;

public class TestMath {

	public static void main(String[] args) {
		System.out.println("292137 = " + Math.ceil(292137.00));
		System.out.println("292137 = " + getMillisSeconds(292137));
		System.out.println("292137 = " + getSeconds(292137));
	}

	private static long getMillisSeconds(long millis) {
		return Math.round((double) millis);
	}

	private static long getSeconds(long millis) {
		return Math.round((double) millis / 1000);
	}
}
