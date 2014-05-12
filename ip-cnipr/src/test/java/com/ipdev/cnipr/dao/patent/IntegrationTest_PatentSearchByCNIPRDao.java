package com.ipdev.cnipr.dao.patent;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ipdev.cnipr.architect.IntegrationTestCniprCase;
import com.ipdev.cnipr.entity.method.Cf1Request;
import com.ipdev.cnipr.entity.method.Cf1Response;
import com.ipdev.cnipr.entity.method.If1Request;
import com.ipdev.cnipr.entity.method.If2Request;
import com.ipdev.cnipr.entity.method.IfxResponse;
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
import com.ipdev.common.entity.method.RequestResponse;

@Test(groups = {"integration"})
public class IntegrationTest_PatentSearchByCNIPRDao extends IntegrationTestCniprCase {

	PatentSearchByCNIPRDao dao;
	
	@BeforeMethod
    public void init() throws Exception {
		dao = new PatentSearchByCNIPRDao(false);
		dao.setTokenManager(tokenManager);
		
		recorder.setJsonHelper(dao.getJsonHelper());
	}
	
	public void test_if1_getRootByIpc() {
		BufferedWriter outputWriter = null;
        try {
            outputWriter = createTempOutputFile("getRootByIpc.json");
        
            If1Request request = new If1Request();
                
            IfxResponse response = dao.getRootByIpc(request);
            RequestResponse rr = new RequestResponse("getRootByIpc", request, response);
            recorder.record(outputWriter, rr, /*outputNulls=*/true);
        } catch (Exception e) {
            System.err.println("Caught exception:" + e);
        } finally {
            IOUtils.closeQuietly(outputWriter);
        }
	}
	
	public void test_if2_getChildrenByIpc() {
		
		BufferedWriter outputWriter = null;
        try {
            outputWriter = createTempOutputFile("getChildrenByIpc.json");
        
            If2Request request = new If2Request();
            request.setKey("A");
                
            IfxResponse response = dao.getChildrenByIpc(request);
            RequestResponse rr = new RequestResponse("getChildrenByIpc", request, response);
            recorder.record(outputWriter, rr, /*outputNulls=*/true);
        } catch (Exception e) {
            System.err.println("Caught exception:" + e);
        } finally {
            IOUtils.closeQuietly(outputWriter);
        }
	}
	
	public void test_sf1_absSearchByExpression() throws IOException {		
		BufferedWriter outputWriter = createTempOutputFile("absSearchByExpression.json");
		try {
            String expression = "名称=（手机）";
            int maxResults = 1000;
			Sf1Request request = new Sf1Request();
			request.setExpression(expression);
			request.addDb("FMZL");
			request.addDb("SYXX");
			request.addDb("WGZL");
			request.addDb("FMSQ");
            request.setOrderBy("申请日", false);
		
			int step = 50;
			int count = 0;
			while (count<maxResults) {
				request.setFrom(count);
				request.setTo(count+step);
				Sf1Response response = dao.absSearchByExpression(request);
				
				RequestResponse rr = new RequestResponse("absSearchByExpression", request, response);
				recorder.record(outputWriter, rr, /*outputNulls=*/false);
				
				int res_size = response.getResults()==null ? 0 : response.getResults().size();
				if (res_size<step) {
					// no more data
					break;
				}
				count += res_size;
			}
		} catch (Exception e) {
			System.err.println("Caught exception:" + e);
		} finally {
			IOUtils.closeQuietly(outputWriter);
		}
	}
	
    /*
     * 常用检索字段: 名称 申请号 申请日 申请（专利权）人 摘要 公开（公告）号 公开（公告）日 发明（设计）人 最新法律状态 分类号 代理人 专利代理机构
     */
    public void test_sf1_absSearchByExpression_byDate() throws IOException {
        BufferedWriter outputWriter = createTempOutputFile("absSearchByExpression-date.json");
        try {
            String expression = "地址=(浙江省) and 公开（公告）日=(20140101 to 20140201)";
            int maxResults = 100;
            Sf1Request request = new Sf1Request();
            request.setExpression(expression);
            request.addDb("FMZL");
            // request.addDb("SYXX");
            // request.addDb("WGZL");
            // request.addDb("FMSQ");
            request.setOrderBy("申请日", false);

            int step = Math.min(50, maxResults);
            int count = 0;
            while (count < maxResults) {
                request.setFrom(count);
                request.setTo(count + step);
                Sf1Response response = dao.absSearchByExpression(request);

                RequestResponse rr = new RequestResponse("absSearchByExpression", request, response);
                recorder.record(outputWriter, rr, /* outputNulls= */false);

                int res_size = response.getResults() == null ? 0 : response.getResults().size();
                if (res_size < step) {
                    // no more data
                    break;
                }
                count += res_size;
            }
        } catch (Exception e) {
            System.err.println("Caught exception:" + e);
        } finally {
            IOUtils.closeQuietly(outputWriter);
        }
    }

    public void test_sf1_absSearchByExpression_US() throws IOException {
        BufferedWriter outputWriter = createTempOutputFile("absSearchByExpressionUS.json");
        try {
            String expression = "title=（smartphone）";
            int maxResults = 1000;
            Sf1Request request = new Sf1Request();
            request.setExpression(expression);
            request.addDb("USPATENT");
            request.setOrderBy("申请日", false);

            int step = 50;
            int count = 0;
            while (count < maxResults) {
                request.setFrom(count);
                request.setTo(count + step);
                Sf1Response response = dao.absSearchByExpression(request);

                RequestResponse rr = new RequestResponse("absSearchByExpression", request, response);
                recorder.record(outputWriter, rr, /* outputNulls= */false);

                int res_size = response.getResults() == null ? 0 : response.getResults().size();
                if (res_size < step) {
                    // no more data
                    break;
                }
                count += res_size;
            }
        } catch (Exception e) {
            System.err.println("Caught exception:" + e);
        } finally {
            IOUtils.closeQuietly(outputWriter);
        }
    }

	public void test_sf2_detailSearchByPid() throws IOException {
		List<String> ids = Arrays.asList("FMZL@CN103730938A", "FMZL@CN103677896A", "FMZL@CN103731526A");
		
		BufferedWriter outputWriter = null;
		try {
			outputWriter = createTempOutputFile("patentDetail.json");
			
			for (String pid : ids) {
				Sf2Request request = new Sf2Request();
				request.setPid(pid);
				
				Sf2Response response = dao.detailSearchByPid(request);
				RequestResponse rr = new RequestResponse("detailSearchByPid", request, response);
				recorder.record(outputWriter, rr, /*outputNulls=*/true);
			}
		} catch (Exception e) {
			System.err.println("Caught exception:" + e);
		} finally {
			IOUtils.closeQuietly(outputWriter);
		}
	}
	
	public void test_sf3_prsSearch() {
		BufferedWriter outputWriter = null;
		try {
			outputWriter = createTempOutputFile("prsSearchResults.json");
		
			Sf3Request request = new Sf3Request();
            request.setExpression("申请号=CN2014%");
				
			Sf3Response response = dao.prsSearch(request);
			RequestResponse rr = new RequestResponse("prsSearch", request, response);
			recorder.record(outputWriter, rr, /*outputNulls=*/true);
		} catch (Exception e) {
			System.err.println("Caught exception:" + e);
		} finally {
			IOUtils.closeQuietly(outputWriter);
		}
	}
	
	public void test_sf4_prsDetailSearch() {
		List<String> appns = Arrays.asList("CN201420006273.2", "CN201420014852.1", "CN201410007755.4");
		
		BufferedWriter outputWriter = null;
		try {
			outputWriter = createTempOutputFile("prsDetailSearchResults.json");
		
			for (String an : appns) {
				Sf4Request request = new Sf4Request();
				request.setAppnumber(an);
					
				Sf4Response response = dao.prsDetailSearch(request);
				RequestResponse rr = new RequestResponse("prsDetailSearch", request, response);
				recorder.record(outputWriter, rr, /*outputNulls=*/true);
			}
		} catch (Exception e) {
			System.err.println("Caught exception:" + e);
		} finally {
			IOUtils.closeQuietly(outputWriter);
		}
	}
	
	public void test_sf9_patentTransferSearch() {
		BufferedWriter outputWriter = null;
		try {
			outputWriter = createTempOutputFile("patentTransferSearchResults.json");
		
			Sf9Request request = new Sf9Request();
            request.setExpression("申请号=CN2013%");
				
			Sf9Response response = dao.patentTransferRecordsSearch(request);
			RequestResponse rr = new RequestResponse("patentTransferRecordsSearch", request, response);
			recorder.record(outputWriter, rr, /*outputNulls=*/true);
		} catch (Exception e) {
			System.err.println("Caught exception:" + e);
		} finally {
			IOUtils.closeQuietly(outputWriter);
		}
	}
	
	public void test_cf1_getNoCodeCorpByKey() {
		BufferedWriter outputWriter = null;
		try {
			outputWriter = createTempOutputFile("noCodeCorpByKeyResults.json");
		
			Cf1Request request = new Cf1Request();
			request.setKey("SKF");
			request.setFrom(0);
			request.setTo(50);
				
			Cf1Response response = dao.getNoCodeCorpByKey(request);
			RequestResponse rr = new RequestResponse("getNoCodeCorpByKey", request, response);
			recorder.record(outputWriter, rr, /*outputNulls=*/true);
		} catch (Exception e) {
			System.err.println("Caught exception:" + e);
		} finally {
			IOUtils.closeQuietly(outputWriter);
		}
	}
}
