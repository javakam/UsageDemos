package sys;

public class TestStringByteArray {

	public static void main(String[] args) {

		String s = "abcdefgh";
		String s2 = s.substring(3, 8);// !!!!!!!!!!!!
		System.out.println("s=" + s2);
		System.out.println("-----------");

		byte[] sbs = s.getBytes();
		byte[] sbsNew = subBytes(sbs, 3, 8 - 3);// !!!!!!!!!!!!
		// System.out.println("sbs len=" + sbs.length + "; sbsNew len=" +
		// sbsNew.length);

		String sNew = new String(sbsNew);
		System.out.println("sNew=" + sNew.toString());
	}

	public static byte[] subBytes(byte[] src, int begin, int count) {
		byte[] bs = new byte[count];
		System.arraycopy(src, begin, bs, 0, count);
		return bs;
	}
}
