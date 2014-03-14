package com.eagleeye.mobileapp.test;

import java.util.concurrent.CountDownLatch;

import org.apache.http.Header;
import org.json.JSONObject;
import org.junit.Assert;

import com.eagleeye.mobileapp.extension.Ext_Assert;
import com.eagleeye.mobileapp.pojo.PojoAaaAuthenticatePost;
import com.eagleeye.mobileapp.pojo.PojoUser;
import com.eagleeye.mobileapp.util.UtilLogger;
import com.eagleeye.mobileapp.util.http.UtilHttpAaa;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

public class TestHttpAaa extends Test_Base {

	
	PojoUser pojoUser;
	public void testHttpLogin() {
		logout();
		
		final CountDownLatch latch = new CountDownLatch(1);
		
		try {
			runTestOnUiThread(new Runnable() {
				@Override public void run() {
					UtilHttpAaa.authenticatePost(TESTACCOUNT_USERNAME, TESTACCOUNT_PASSWORD, new TextHttpResponseHandler() {
						@Override public void onSuccess(int statusCode, Header[] headers, String responseString) {
							UtilLogger.logInfo(TAG, "Http Authenticate Post onSuccess()");
							
							PojoAaaAuthenticatePost pojo = new PojoAaaAuthenticatePost(responseString);
							String authenticationToken = pojo.token;
							
							UtilHttpAaa.authorizePost(authenticationToken, new JsonHttpResponseHandler() {
								
								@Override public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObj) {
									UtilLogger.logInfo(TAG, "Http Authorize Post onSuccess()");
									pojoUser = new PojoUser(jsonObj);
									latch.countDown();
									
								}
								
								@Override public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
									UtilLogger.logInfo(TAG, "Http Authorize Post onFailure()");
									latch.countDown();
								}
							});
							
						}
						
						@Override
						public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
							UtilLogger.logInfo(TAG, "Http Authenticate Post onFailure()");
							latch.countDown();
						}
					});
				}
			});
		} catch (Throwable e1) {
			e1.printStackTrace();
		}
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Assert.assertNotNull(pojoUser);
		Ext_Assert.assertValid(pojoUser);
	}
	
	public void testThisMethodAGoatIsNotAGoat() {
		Assert.assertTrue(true);
	}
}
