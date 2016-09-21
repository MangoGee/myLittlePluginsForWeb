import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

import org.json.JSONException;
import org.json.JSONObject;

public class GetNum {
    public static void main(String[] args) {
        Thread thread1 = new Thread(new Get360PhoneLocation(), "A");
        Thread thread2 = new Thread(new Get360PhoneLocation(), "B");
        Thread thread3 = new Thread(new Get360PhoneLocation(), "C");
        Thread thread4 = new Thread(new Get360PhoneLocation(), "D");
        Thread thread5 = new Thread(new Get360PhoneLocation(), "E");
        Thread thread6 = new Thread(new Get360PhoneLocation(), "F");
        Thread thread7 = new Thread(new Get360PhoneLocation(), "G");
        Thread thread8 = new Thread(new Get360PhoneLocation(), "H");
        Thread thread9 = new Thread(new Get360PhoneLocation(), "I");
        Thread thread10 = new Thread(new Get360PhoneLocation(), "J");
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();
        thread7.start();
        thread8.start();
        thread9.start();
        thread10.start();
    }
}

