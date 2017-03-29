package bbkj.org.cn.util;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Config {
	private static ResourceBundle resource = ResourceBundle.getBundle("config");
	
	public static String value(String key){
		try {
			return resource.getString(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Map<String,String> values(String key){
		Map<String,String> keyValue = new HashMap<String, String>();
		for(String k:resource.keySet()){
			if(k.contains(key)){
				keyValue.put(k, resource.getString(k));
			}
		}
		return keyValue;
	}
}
