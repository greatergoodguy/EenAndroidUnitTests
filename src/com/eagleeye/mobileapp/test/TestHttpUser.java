package com.eagleeye.mobileapp.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.http.Header;
import org.json.JSONObject;
import org.junit.Assert;

import com.eagleeye.mobileapp.extension.Ext_Assert;
import com.eagleeye.mobileapp.pojo.PojoUser;
import com.eagleeye.mobileapp.util.UtilLogger;
import com.eagleeye.mobileapp.util.http.UtilHttpUser;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TestHttpUser extends Test_Base {
	
	PojoUser pojoUser;
	public void testHttpUserGet() {
		UtilLogger.logInfo(TAG, "testHttpUserGet()");
		final CountDownLatch latch = new CountDownLatch(1);
		
		try {
			runTestOnUiThread(new Runnable() {
				
				@Override public void run() {
					UtilHttpUser.get(new JsonHttpResponseHandler() {
						@Override public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObj) {
							UtilLogger.logInfo(TAG, "Http User Get onSuccess()");
							UtilLogger.logInfoAsJson(TAG, jsonObj);
							pojoUser = new PojoUser(jsonObj);
							
							latch.countDown();
						}
						
						@Override
						public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
							UtilLogger.logInfo(TAG, "Http User Get onFailure()");
							latch.countDown();
						}
					});
				}
			});
		} catch (Throwable e1) {
			e1.printStackTrace();
		}
		
		try {
			latch.await(TIMER_NETWORK_TIMEOUT_IN_SECOUNDS, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Assert.assertNotNull(pojoUser);
		Ext_Assert.assertValid(pojoUser);
	}
	
//	public void testHttpUserPut() {
//		UtilLogger.logInfo(TAG, "testHttpUserPut()");
//		Assert.assertTrue(false);
//	}
//	
//	public void testHttpUserPost() {
//		UtilLogger.logInfo(TAG, "testHttpUserPost()");
//		Assert.assertTrue(false);
//	}
//	
//	public void testHttpUserDelete() {
//		UtilLogger.logInfo(TAG, "testHttpUserDelete()");
//		Assert.assertTrue(false);
//	}
}
