package com.ipdev.cnipr.dao.patent;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.ipdev.architect.AppConfig;
import com.ipdev.cnipr.auth.AuthTokenManager;
import com.ipdev.cnipr.auth.OAuth2ClientCredentials;
import com.ipdev.cnipr.entity.method.Cf1Request;
import com.ipdev.cnipr.entity.method.Cf1Response;
import com.ipdev.cnipr.entity.method.Cf2Request;
import com.ipdev.cnipr.entity.method.Cf2Response;
import com.ipdev.cnipr.entity.method.Cf3Request;
import com.ipdev.cnipr.entity.method.Cf3Response;
import com.ipdev.cnipr.entity.method.CniprRequest;
import com.ipdev.cnipr.entity.method.CniprRequest.CallType;
import com.ipdev.cnipr.entity.method.CniprResponse;
import com.ipdev.cnipr.entity.method.If1Request;
import com.ipdev.cnipr.entity.method.If2Request;
import com.ipdev.cnipr.entity.method.If3Request;
import com.ipdev.cnipr.entity.method.If4Request;
import com.ipdev.cnipr.entity.method.IfxResponse;
import com.ipdev.cnipr.entity.method.Sf10Request;
import com.ipdev.cnipr.entity.method.Sf10Response;
import com.ipdev.cnipr.entity.method.Sf11Request;
import com.ipdev.cnipr.entity.method.Sf11Response;
import com.ipdev.cnipr.entity.method.Sf1Request;
import com.ipdev.cnipr.entity.method.Sf1Response;
import com.ipdev.cnipr.entity.method.Sf2Request;
import com.ipdev.cnipr.entity.method.Sf2Response;
import com.ipdev.cnipr.entity.method.Sf3Request;
import com.ipdev.cnipr.entity.method.Sf3Response;
import com.ipdev.cnipr.entity.method.Sf4Request;
import com.ipdev.cnipr.entity.method.Sf4Response;
import com.ipdev.cnipr.entity.method.Sf9Request;
import com.ipdev.cnipr.entity.method.Sf9Response;
import com.ipdev.cnipr.entity.method.Tf1Request;
import com.ipdev.cnipr.entity.method.Tf1Response;
import com.ipdev.common.DaoException;
import com.ipdev.common.auth.AuthToken;
import com.ipdev.common.auth.AuthTokenExpireException;
import com.ipdev.common.net.HttpMethodsInterface;
import com.ipdev.common.utility.json.JsonHelper;

public class CniprAPIDao {
    static final Log LOG = LogFactory.getLog(CniprAPIDao.class);

    HttpMethodsInterface httpUtil;
    JsonHelper jsonHelper;
    AuthTokenManager tokenManager;
    int maxRetries = 4;

    String getApiUrl() {
        return OAuth2ClientCredentials.getApiURL(AppConfig.getDomain());
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

    public AuthTokenManager getTokenManager() {
        return this.tokenManager;
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
        return cniprSearch(request, 0);
    }

    static final String ACCESS_TOKEN_INVALID_MSG = "请求失败：access_token";

    CniprResponse<?> cniprSearch(CniprRequest request, int retryCounter) {
        Preconditions.checkNotNull(request, "request cannot be null");
        if (retryCounter >= maxRetries) {
            LOG.error("Reach maximum retry times:" + this.maxRetries);
            throw new DaoException("Reach retry limit");
        }

        String url = getApiUrl() + request.getMethod() + "/" + OAuth2ClientCredentials.API_KEY;

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
            LOG.debug("CNIPR search Url: " + url);
            LOG.debug("CNIPR search response = " + responseStr);
        }
        if (StringUtils.isEmpty(responseStr)) {
            return null;
        }
        if (responseStr.contains(ACCESS_TOKEN_INVALID_MSG)) {
            // at least retry once
            this.tokenManager.handleTokenExpireException(new AuthTokenExpireException(responseStr));
            // TODO: add support for various back-off strategy: linear or exponential
            return cniprSearch(request, retryCounter++);
        } else {
            return jsonHelper.fromJsonString(responseStr, request.getResponseType());
        }
    }
}
