package com.ipdev.common.utility.json;

import java.io.Reader;
import java.lang.reflect.Type;

public interface JsonHelper {

	JsonHelper setEnableHtmlEsacping(boolean enableHtmlEscaping);
	
	JsonHelper setDateFormat(String datePattern);
	
	JsonHelper setPrettyPrinting(boolean prettyPrinting);
	
	JsonHelper setSerializeNulls(boolean serializeNulls);
	
	JsonHelper registerTypeAdapter(Type type, Object typeAdaptor);
	
	 <T> T fromJsonString(String jsonString, Class<T> classOfT);
	 
	 <T> T fromJsonString(Reader reader, Class<T> classOfT);
	 
	 String toJsonString(Object object);
}
