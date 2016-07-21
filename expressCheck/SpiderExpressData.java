import java.io.IOException;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gweb.base.action.BaseController;
import com.gweb.base.system.RequestSupport;
import com.gweb.base.util.Tools;

@Controller
public class SpiderExpressData extends BaseController {
	
	/**
	 * 查询快递信息
	 * 
	 * @return
	 */
	
	@RequestMapping(value = "/expressdata/expressdata.json")
	public @ResponseBody
	String expressdata() {
		try {
			Map<String, Object> params = RequestSupport.getParameters();
			
			SpiderExpressData sed = new SpiderExpressData();
			String company = params.get("company").toString();
			String number = params.get("number").toString();
			String httpUrl = "http://www.kuaidi.com/index-ajaxselectcourierinfo-" + number + "-" + company + ".html";
			
			if (params.containsKey("company") && params.containsKey("number")) {	
				Document doc = sed.getDocument(httpUrl);
				
				Elements elementsHTML = doc.select("body");
				
				JSONObject json = new JSONObject();
				
				JSONArray rowArr = new JSONArray();
				
				//把HTML转化成String
				String expressInfo = elementsHTML.html();

				//申通特殊，有<a>标签，此步骤去除<a>
				if (company.equals("shentong")) {
					expressInfo = expressInfo.replaceAll("\\s<a[^>]+>", "");
					expressInfo = expressInfo.replaceAll("&lt;", "");
					expressInfo = expressInfo.replaceAll("\\\\/a&gt;", "");
				}
				
				//定义json设置key(reason, status, rowArr.length, success)保存到results中
				JSONObject results = new JSONObject();
				results.put("reason", infoJson.get("reason"));
				results.put("status", infoJson.get("status"));
				results.put("success", infoJson.get("success"));
				results.put("allResults", rowArr.length());
				
				json.put("results", results);
				
				//把String转化成Json
				JSONObject infoJson = new JSONObject(expressInfo);
				
				if (infoJson.getBoolean("success") == true) {
					rowArr = infoJson.getJSONArray("data");
					json.put("rows", rowArr);
					json.put("results", rowArr.length());
				}

				String jsonResult = json.toString();
				return jsonResult;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return updateFailure("快递查询失败！");
	}
	
	public Document getDocument (String url){
		try {
			return Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		try{
			SpiderExpressData sed = new SpiderExpressData();
			//以HTML返回快递信息
			Document doc = sed.getDocument("http://www.kuaidi.com/index-ajaxselectcourierinfo-227508615865-shentong.html");
			
			//HTML提取出json形式的快递信息
			Elements elementsHTML = doc.select("body");
//			String test = Tools.urlDecode(elementsHTML.toString());
//			System.out.println(test);
			
			JSONObject json = new JSONObject();
			
			//String转换成Json
			String expressInfo = elementsHTML.html();
			JSONArray rowArr = new JSONArray();
			
			expressInfo = expressInfo.replaceAll("\\s<a[^>]+>", "");
			expressInfo = expressInfo.replaceAll("&lt;", "");
			expressInfo = expressInfo.replaceAll("\\\\/a&gt;", "");
			expressInfo = expressInfo.replaceFirst("</a>", "");
			//String转换成Json
			JSONObject infoJson = new JSONObject(expressInfo);
			
			if (infoJson.getBoolean("success") == true) {
				rowArr = infoJson.getJSONArray("data");
				json.put("rows", rowArr);
				json.put("results", rowArr.length());
			}
			
			json.put("rows", rowArr);
			json.put("results", rowArr.length());
			json.put("issuccess", infoJson.getBoolean("success"));
			
			System.out.println(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}				
	}
}
