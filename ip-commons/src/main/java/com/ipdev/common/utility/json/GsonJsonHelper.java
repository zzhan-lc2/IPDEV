package com.ipdev.common.utility.json;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Map;

import org.joda.time.DateTimeZone;

import com.google.common.collect.Maps;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class GsonJsonHelper implements JsonHelper {

	boolean enableHtmlEscaping = false;
	String datePattern = "yyyy-MMM-dd";
	boolean serializeNulls = false;
	boolean prettyPrinting = true;
	Map<Type,Object> typeAdaptorMaps = Maps.newHashMap();
	
	public JsonHelper setEnableHtmlEsacping(boolean enableHtmlEscaping) {
		this.enableHtmlEscaping = enableHtmlEscaping;
		return this;
	}

	public JsonHelper setDateFormat(String datePattern) {
		this.datePattern = datePattern;
		return this;
	}

	public JsonHelper setSerializeNulls(boolean serializeNulls) {
		this.serializeNulls = serializeNulls;
		return this;
	}

	public JsonHelper setPrettyPrinting(boolean prettyPrinting) {
		this.prettyPrinting = prettyPrinting;
		return this;
	}
	
	public JsonHelper registerTypeAdapter(Type type, Object typeAdaptor) {
		typeAdaptorMaps.put(type, typeAdaptor);
		return this;
	}
	
	Gson create() {
		GsonBuilder builder = new GsonBuilder()
			.setExclusionStrategies(new MyExclusionStrategy(null))
			.setDateFormat(datePattern);
		if (!enableHtmlEscaping) builder.disableHtmlEscaping();
		if (serializeNulls) builder.serializeNulls();
		for (Type type : typeAdaptorMaps.keySet()) {
			builder.registerTypeAdapter(type, typeAdaptorMaps.get(type));
		}
		if (prettyPrinting) builder.setPrettyPrinting();
		return builder.create();
	}

	public <T> T fromJsonString(String jsonString, Class<T> classOfT) {
		Gson gson = create();
		return gson.fromJson(jsonString, classOfT);
	}

	public String toJsonString(Object object) {
		Gson gson = create();
		return gson.toJson(object);
	}

	public <T> T fromJsonString(Reader reader, Class<T> classOfT) {
		Gson gson = create();
		return gson.fromJson(reader, classOfT);
	}

	////////////////////////////////////////////////////////////////////////
	
	public static class MyExclusionStrategy implements ExclusionStrategy {
	    private final Class<?> typeToSkip;

	    private MyExclusionStrategy(Class<?> typeToSkip) {
	      this.typeToSkip = typeToSkip;
	    }

	    public boolean shouldSkipClass(Class<?> clazz) {
	      return (clazz == typeToSkip);
	    }

	    public boolean shouldSkipField(FieldAttributes f) {
	      return f.getAnnotation(JsonSkip.class) != null;
	    }
	  }


	public static class DateTimeZoneSerializer implements JsonSerializer<DateTimeZone> {
		@Override
		public JsonElement serialize(DateTimeZone src, Type typeOfSrc,
				JsonSerializationContext context) {
			return new JsonPrimitive(src.getID());
		}
	}
	
	public static class DateTimeZoneDeserializer implements JsonDeserializer<DateTimeZone> {
		@Override
		public DateTimeZone deserialize(JsonElement json, Type typeOfT,
				JsonDeserializationContext context) throws JsonParseException {
			return DateTimeZone.forID(json.getAsJsonPrimitive().getAsString());
		}
		
	}
}
