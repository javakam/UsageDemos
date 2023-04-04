package sys;

import java.util.Locale;

public class TestStringSplit {

	public static void main(String[] args) {
		final String pathOrName = "/xxx/xxx/note.txt";
		printLog("文件名: " + pathOrName);
		printLog(getExtension(pathOrName, '.', false));
		printLog(getExtension(pathOrName, '.', true));
		printLog(changeFileExtension(pathOrName, '.', "jpeg"));
		splitPathOrName("00:00:01.52");
	}

	private static String getExtension(String pathOrName, char split, boolean fullExtension) {
		if (pathOrName.isBlank()) {
			return "";
		}
		int dot = pathOrName.lastIndexOf(split);
		if (dot != -1) {
			int beginIndex = 0;
			if (fullExtension) {
				beginIndex = dot;
			} else {
				beginIndex = (dot + 1);
			}
			return pathOrName.substring(beginIndex).toLowerCase(Locale.getDefault());
		}
		return "";
	}

	private static String changeFileExtension(String pathOrName, char split, String newExtension) {
		if (pathOrName.isBlank()) {
			return "";
		}
		int dot = pathOrName.lastIndexOf(split);
		if (dot != -1) {
			int endIndex = dot + 1;
			return pathOrName.substring(0, endIndex).toLowerCase(Locale.getDefault()) + newExtension;
		}
		return "";
	}

	private static void splitPathOrName(String pathOrName) {
		if (pathOrName.contains(".")) {
			String[] arr = pathOrName.split("\\.");
			printLog("splitPathOrName : " + arr[0]);
			printLog("splitPathOrName : " + arr[1]);
		}
	}

	private static void printLog(String info) {
		System.out.println("文件扩展: " + info);
	}
}
