import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.JSONObject;
import org.jsoup.*;
import org.jsoup.nodes.Document;


public class get360PhoneLocation extends Thread {
    static String sqlInsert = null;
    
    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

	
    public void run(){
    	String sqlurl="jdbc:mysql://localhost:3306/rechargeonline"; 
		String username = "root";
		String password = "12345";
		int getPhone=0;
		switch(Thread.currentThread().getName()) {
		case "A":
			getPhone=1300000;
			break;
		case "B":
			getPhone=1350000;
			break;
		case "C":
			getPhone=1400000;
			break;
		case "D":
			getPhone=1450000;
			break;
		case "E":
			getPhone=1500000;
			break;
		case "F":
			getPhone=1550000;
			break;
		case "G":
			getPhone=1700000;
			break;
		case "H":
			getPhone=1750000;
			break;
		case "I":
			getPhone=1800000;
			break;
		case "J":
			getPhone=1850000;
			break;	
		}
    	
		sqlInsert = "insert into sys_phonehome(prefix, province, city, supplier) values (?,?,?,?)";
		
		Connection conn = null;
		
        String result =null;
        int phone = getPhone;
        for(long i=0;i<50000;i++) {
        	String phoneNum = phone + "";
            phone += 1;
            String url ="http://cx.shouji.360.cn/phonearea.php?number=" + phoneNum;//ÇëÇó½Ó¿ÚµØÖ·
	        try {
				Class.forName("com.mysql.jdbc.Driver");
				
				conn=DriverManager.getConnection(sqlurl, username, password);

				PreparedStatement ps = conn.prepareStatement(sqlInsert);
	        	
	            result = net(url, "GET");
	            JSONObject object = new JSONObject(result);
	            String province = URLDecoder.decode(object.getJSONObject("data").getString("province"), "utf-8");
	            String city = URLDecoder.decode(object.getJSONObject("data").getString("city"), "utf-8");
	            String sp = URLDecoder.decode(object.getJSONObject("data").getString("sp"), "utf-8");
	            
	            if(object.getInt("code")==0 && !province.isEmpty()){
	                System.out.println(object.get("data"));
	                
					ps.setObject(1, phoneNum);
					ps.setObject(2, object.getJSONObject("data").get("province"));
					ps.setObject(3, object.getJSONObject("data").get("city"));
					ps.setObject(4, object.getJSONObject("data").get("sp"));
					
					ps.addBatch();
					ps.executeBatch();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
        }
	}
    
    public static String net(String strUrl, String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if(method==null || method.equals("GET")){
                conn.setRequestMethod("GET");
            }else{
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }
  }

