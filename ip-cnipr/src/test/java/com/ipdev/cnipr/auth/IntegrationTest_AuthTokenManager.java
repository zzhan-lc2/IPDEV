package com.ipdev.cnipr.auth;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ipdev.cnipr.architect.IntegrationTestCniprCase;
import com.ipdev.cnipr.auth.AuthTokenManager;
import com.ipdev.common.auth.AuthToken;

@Test(groups = { "integration" })
public class IntegrationTest_AuthTokenManager extends IntegrationTestCniprCase {

    AuthTokenManager tokenManager;

    @BeforeMethod
    public void init() {
        tokenManager = this.getBean(AuthTokenManager.class);
    }

    public void test_getAccessCode() {
        String actual = tokenManager.getAccessCode();
        System.out.println(actual);
    }

    public void test_requestAccessToken() {
        AuthToken actual = tokenManager.requestAccessToken();
        System.out.println(actual);
    }

    public void test_parseTokenFromResponseBody() {
        String response = "<head><title>implicit授权成功</title></head>"
            + "<body>"
            + "<div id=\"openid\">ab6f1509925e39836a32f7dd22224a15</div>"
            + "<div id=\"openkey\">8896dbdd8a9323c1e78a9d8c87f5f4c8</div>"
            + "<div id=\"expires_in\">2592000</div>"
            + "<div id=\"access_token\">d9eea4e8ad1119ed30b4e8134aa37b47</div>"
            + "<div id=\"state\">test</div>"
            + "</body>";
        AuthToken actual = AuthTokenManager.parseTokenFromResponseBody(response, null);
        Assert.assertNotNull(actual);
    }
}
