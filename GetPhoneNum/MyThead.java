import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;


public class MyThread extends Thread {
    static String sqlInsert = null;
    
	/**
	*手机号码归属地调用示例代码 － 聚合数据
	*在线接口文档：http://www.juhe.cn/docs/11
	**/
    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
 
    //配置您申请的KEY
    public static final String APPKEY ="85927295a5d5a1fd356f376d38e31f4d";
	
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
    	
		sqlInsert = "insert into sys_test(prefix, supplier, province, city, suit) values (?,?,?,?,?)";
		
		Connection conn = null;
		
        String result =null;
        String url ="http://apis.juhe.cn/mobile/get";//请求接口地址
        int phone = getPhone;
        for(int i=0;i<50000;i++) {
        	String phoneNum = phone + "";
        	Map params = new HashMap();//请求参数
            params.put("phone",phoneNum);//需要查询的手机号码或手机号码前7位
            phone += 1;
            params.put("key","85927295a5d5a1fd356f376d38e31f4d");//应用APPKEY(应用详细页查询)
            params.put("dtype","json");//返回数据的格式,xml或json，默认json
 
	        try {
				Class.forName("com.mysql.jdbc.Driver");
				
				conn=DriverManager.getConnection(sqlurl, username, password);

				PreparedStatement ps = conn.prepareStatement(sqlInsert);
	        	
	            result =net(url, params, "GET");
	            JSONObject object = new JSONObject(result);
	            if(object.getInt("error_code")==0){
	                System.out.println(object.get("result"));
	                
					ps.setObject(1, phoneNum);
					ps.setObject(2, object.getJSONObject("result").get("company"));
					ps.setObject(3, object.getJSONObject("result").get("province"));
					ps.setObject(4, object.getJSONObject("result").get("city"));
					ps.setObject(5, object.getJSONObject("result").get("card"));
					
					System.out.println(phoneNum);
					
					ps.addBatch();
					ps.executeBatch();
	            }else{
	                System.out.println(object.get("error_code")+":"+object.get("reason"));
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
				try {
					conn.close();
					System.out.println(4);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
        }
	}
    
    /**
    *
    * @param strUrl 请求地址
    * @param params 请求参数
    * @param method 请求方法
    * @return  网络请求字符串
    * @throws Exception
    */
   public static String net(String strUrl, Map params,String method) throws Exception {
       HttpURLConnection conn = null;
       BufferedReader reader = null;
       String rs = null;
       try {
           StringBuffer sb = new StringBuffer();
           if(method==null || method.equals("GET")){
               strUrl = strUrl+"?"+urlencode(params);
           }
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
           if (params!= null && method.equals("POST")) {
               try {
                   DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                   out.writeBytes(urlencode(params));
               } catch (Exception e) {
                   // TODO: handle exception
                   e.printStackTrace();
               }
                
           }
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

   //将map型转为请求参数型
   public static String urlencode(Map<String,String> data) {
       StringBuilder sb = new StringBuilder();
       for (Map.Entry i : data.entrySet()) {
           try {
               sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
           } catch (UnsupportedEncodingException e) {
               e.printStackTrace();
           }
       }
       return sb.toString();
   }
}

