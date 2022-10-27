import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

//第一财经
public class Src {

    public static void main(String[] args) {
        URL url = null;
        try {
            url = new URL("https://weibo.com/diyicaijing?tabtype=feed");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        InputStream is = null;
        try {
            is = url.openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedInputStream bis = new BufferedInputStream(is);
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(new File("C:\\fastwork\\cj.html")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] b = new byte[1024];
        int len = -1;
        try {
            len = bis.read(b);
            while (len != -1) {
                bos.write(b);
                len = bis.read(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (bis != null) {
                    bis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
