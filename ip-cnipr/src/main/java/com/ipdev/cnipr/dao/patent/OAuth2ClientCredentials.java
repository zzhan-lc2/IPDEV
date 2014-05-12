package com.ipdev.cnipr.dao.patent;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.Sets;
import com.ipdev.common.IpdException;

public class OAuth2ClientCredentials {
	private static Log LOG = LogFactory.getLog(OAuth2ClientCredentials.class);
	
	public static String ACCOUNT = "yuyangip";
	public static String PASSWORD = "kris526216107";	// TODO: for security purpose, we should use Odin to manage these credential
	
	/** Value of the "API Key". */
	public static final String API_KEY = "5051482BC437E4756BF652AB61710F63"; // client_id

	/** Value of the "API Secret". */
	public static final String API_SECRET = "731733BCF8A6B8D9E169953B4164BBCF";

	public static String AUTH_URL = "https://59.151.93.239/oauth2/authorize";	// "https://open.cnipr.com/oauth2/authorize?";
	
	/** Port in the "Callback URL". */
	public static final int PORT = 8080;

	public static String REGISTERED_URL = "http://www.yuyangip.com/";
	
	public static String AUTH_API_SCOPE = "sf1,sf2,sf3,sf4,cf1,cf2,cf3,if1,if2,if3,if4,sf9,sf10,sf11";
	public static String AUTH_DBS_SCOPE = "FMZL,FMSQ,SYXX,WGZL,FLZT,TWZL,HKPATENT,USPATENT,JPPATENT,GBPATENT,DEPATENT,FRPATENT,EPPATENT,WOPATENT,CHPATENT,KRPATENT,RUPATENT,ASPATENT,GCPATENT,AUPATENT,CAPATENT,ESPATENT,ATPATENT,ITPATENT,APPATENT,SEPATENT,OTHERPATENT,CNFMZL_EN,CNFMSQ_EN,CNSYXX_EN,CNWGZL_EN,CN_PRS_TRANSFER,CN_PRS_PRESERVATION,CN_PRS_EXPLOITATION,FMSX,XXSX,WGSX";
	public static Set<String> AUTH_API_SCOPE_SET = csvToSet(AUTH_API_SCOPE);
	public static Set<String> AUTH_DBS_SCOPE_SET = csvToSet(AUTH_DBS_SCOPE);
	
	/** Domain name in the "Callback URL". */
	public static final String DOMAIN = "127.0.0.1"; // "http://www.yuyangip.com/";

	public static void errorIfNotSpecified() {
	    if (API_KEY.startsWith("Enter ") || API_SECRET.startsWith("Enter ")) {
	      LOG.error(
	          "Enter API Key and API Secret from CNIPR http://59.151.93.226"
	          + " into API_KEY and API_SECRET in " + OAuth2ClientCredentials.class);
	      throw new IpdException("API key/secret are not set");
	    }
	}
	
	static Set<String> csvToSet(String csvString) {
		Set<String> results = Sets.newHashSet();
		String[] array = StringUtils.split(csvString, ",");
		for (String v : array) {
			results.add(StringUtils.trim(v));
		}
		return results;
	}

}
