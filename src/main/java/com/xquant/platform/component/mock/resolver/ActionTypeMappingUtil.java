package com.xquant.platform.component.mock.resolver;

import java.util.HashMap;
import java.util.Map;

public class ActionTypeMappingUtil {
	
	private static Map<String, String> map = new HashMap<String, String>();
	
	static {
		map.put("2000", "1");
		map.put("2001", "1");
		map.put("2002", "1");
		map.put("2003", "1");
		map.put("2004", "1");
		map.put("2005", "1");
		map.put("4000", "112");
		map.put("4001", "113");
		map.put("4002", "112");
		map.put("4003", "113");
		map.put("4004", "112");
		map.put("4005", "113");
		map.put("3002", "107");
		map.put("3000", "2");
		map.put("3001", "105");	
	}
	
	public static String getTypeViaAction(String action) {
		return map.get(action);
	}

}
