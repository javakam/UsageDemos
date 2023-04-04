package sys;

import java.nio.file.*;
import java.io.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class FileDateTimeTest {

	private static String path = "C:\\Users\\ChangBao\\Desktop\\VID_20210708150334038.mp4";

	public static void main(String[] args) {
		File f = new File(path);
		// System.out.println(getCreationTime(f));

		Path file = f.toPath();
		BasicFileAttributes attr;
		try {
			attr = Files.readAttributes(file, BasicFileAttributes.class);
			System.out.println("creationTime: " + attr.creationTime());
			System.out.println("lastAccessTime: " + attr.lastAccessTime());
			System.out.println("lastModifiedTime: " + attr.lastModifiedTime());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getCreationTime(File file) {
		if (file == null) {
			return null;
		}

		BasicFileAttributes attr = null;
		try {
			Path path = file.toPath();
			attr = Files.readAttributes(path, BasicFileAttributes.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 创建时间
		Instant instant = attr.creationTime().toInstant();
		// 更新时间
//	    Instant instant = attr.lastModifiedTime().toInstant();
		// 上次访问时间
//	        Instant instant = attr.lastAccessTime().toInstant();
		String format = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.systemDefault()).format(instant);
		return format;
	}
}
