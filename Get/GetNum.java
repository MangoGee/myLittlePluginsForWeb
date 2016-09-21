import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;
import java.sql.SQLException; 

import org.json.JSONException;
import org.json.JSONObject;

public class GetNum {
    static String sqlInsert = null;
    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
    
 
    public static void main(String[] args) {
        Thread thread1 = new Thread(new get360PhoneLocation(), "A");
        Thread thread2 = new Thread(new get360PhoneLocation(), "B");
        Thread thread3 = new Thread(new get360PhoneLocation(), "C");
        Thread thread4 = new Thread(new get360PhoneLocation(), "D");
        Thread thread5 = new Thread(new get360PhoneLocation(), "E");
        Thread thread6 = new Thread(new get360PhoneLocation(), "F");
        Thread thread7 = new Thread(new get360PhoneLocation(), "G");
        Thread thread8 = new Thread(new get360PhoneLocation(), "H");
        Thread thread9 = new Thread(new get360PhoneLocation(), "I");
        Thread thread10 = new Thread(new get360PhoneLocation(), "J");
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

