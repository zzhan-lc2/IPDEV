package com.ipdev.cnipr.dao.patent;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import com.ipdev.cnipr.entity.method.*;
import com.ipdev.cnipr.entity.method.CniprRequest.CallType;
import com.ipdev.common.auth.AuthToken;
import com.ipdev.common.dao.patent.PatentSearchDao;
import com.ipdev.common.entity.patent.Patent;
import com.ipdev.common.net.HttpMethodsInterface;
import com.ipdev.common.utility.json.GsonJsonHelper;
import com.ipdev.common.utility.json.JsonHelper;

public class PatentSearchByCNIPRDao implements PatentSearchDao {
	static final Log LOG = LogFactory.getLog(PatentSearchByCNIPRDao.class);
	
	static final String CNIPR_URL_PROD = "https://open.cnipr.com/cnipr-api/rs/api/";
	static final String CNIPR_URL_DEVO = "https://59.151.93.239/cnipr-api/rs/api/";
	
	HttpMethodsInterface httpUtil = new HttpMethodsCNIPR(true);
	JsonHelper jsonHelper;
	AuthTokenManager tokenManager;
	
	String cniprUrl = CNIPR_URL_DEVO;
	
	public PatentSearchByCNIPRDao(boolean isProd) {
		if (isProd) {
			cniprUrl = CNIPR_URL_PROD;
		}
		
		jsonHelper = new GsonJsonHelper()	// default Json helper
			.setDateFormat("yyyy.MM.dd")
			.setEnableHtmlEsacping(false)
			.setSerializeNulls(true);
	}
	
	public void setHttpUtil(HttpMethodsInterface httpUtil) {
		this.httpUtil = httpUtil;
	}
	
	public void setJsonHelper(JsonHelper externalJsonHelper) {
		this.jsonHelper = externalJsonHelper;
	}
	
	public JsonHelper getJsonHelper() {
		return this.jsonHelper;
	}
	
	public void setTokenManager(AuthTokenManager tokenManager) {
		this.tokenManager = tokenManager;
	}
	
	public List<Patent> generalSearch(Map<String, String> searchParams) {
		// TODO Auto-generated method stub
		return null;
	}
	
	AuthToken getAuthToken() {
		return tokenManager.getAuthToken();
	}
	
	List<NameValuePair> populateAuth(List<NameValuePair> nvps) {
		AuthToken token = getAuthToken();
		nvps.add(new BasicNameValuePair("access_token", token.getAccessToken()));   
		nvps.add(new BasicNameValuePair("openid", token.getOpenId()));   
		return nvps;
	}

	// 分类号检索接口列表
	public IfxResponse getRootByIpc(If1Request request) {
		return (IfxResponse) cniprSearch(request);
	}
	
	
	public IfxResponse getChildrenByIpc(If2Request request) {
		return (IfxResponse) cniprSearch(request);
	}
	
	public IfxResponse getIpcByIpcKey(If3Request request) {
		return (IfxResponse) cniprSearch(request);
	}
	
	public IfxResponse getIpcByNoteKey(If4Request request) {
		return (IfxResponse) cniprSearch(request);
	}
	
	
	// 专利信息检索接口列表
	public Sf1Response absSearchByExpression(Sf1Request request) {
		return (Sf1Response) cniprSearch(request);
	}
	
	public Sf2Response detailSearchByPid(Sf2Request request) {
		return (Sf2Response) cniprSearch(request);
	}
	
	public Sf3Response prsSearch(Sf3Request request) {
		return (Sf3Response) cniprSearch(request);
	}
	
	public Sf4Response prsDetailSearch(Sf4Request request) {
		return (Sf4Response) cniprSearch(request);
	}
	
	public Sf9Response patentTransferRecordsSearch(Sf9Request request) {
		return (Sf9Response) cniprSearch(request);
	}
	
	public Sf10Response patentPledgessResearch(Sf10Request request) {
		return (Sf10Response) cniprSearch(request);
	}
	
	public Sf11Response patentAssignRecordsResearch(Sf11Request request) {
		return (Sf11Response) cniprSearch(request);
	}
	
	// 公司代码检索接口列表
	public Cf1Response getNoCodeCorpByKey(Cf1Request request) {
		return (Cf1Response) cniprSearch(request);
	}
	
	public Cf2Response getRootCorpByKey(Cf2Request request) {
		return (Cf2Response) cniprSearch(request);
	}
	
	public Cf3Response getChildrenByCode(Cf3Request request) {
		return (Cf3Response) cniprSearch(request);
	}
	
	public Tf1Response translateString(Tf1Request request) {
		return (Tf1Response) cniprSearch(request);
	}
	
	CniprResponse<?> cniprSearch(CniprRequest request) {
		Preconditions.checkNotNull(request, "request cannot be null");
		String url = cniprUrl + request.getMethod() + "/" + OAuth2ClientCredentials.API_KEY;
		
		List<NameValuePair> nvps = Lists.newArrayList();  
		request.populateNameValuePairs(nvps);
		populateAuth(nvps);
		  
		String responseStr = null;
		if (request.getCallType().equals(CallType.POST)) {
			responseStr = httpUtil.post(url, nvps);
		} else {
			responseStr = httpUtil.get(url, nvps);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("CNIPR search response = " + responseStr);
		}
		if (StringUtils.isEmpty(responseStr)) {
			return null;
		}
		
		return jsonHelper.fromJsonString(responseStr, request.getResponseType());
	}
}
