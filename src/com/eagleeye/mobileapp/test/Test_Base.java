package com.eagleeye.mobileapp.test;

import java.util.concurrent.CountDownLatch;

import org.apache.http.Header;

import android.test.ActivityInstrumentationTestCase2;

import com.eagleeye.mobileapp.ActivityUnitTests;
import com.eagleeye.mobileapp.pojo.PojoAaaAuthenticatePost;
import com.eagleeye.mobileapp.util.UtilLogger;
import com.eagleeye.mobileapp.util.http.UtilHttpAaa;
import com.loopj.android.http.TextHttpResponseHandler;

public abstract class Test_Base extends ActivityInstrumentationTestCase2<ActivityUnitTests> implements Constants {

	protected final String TAG = getClass().getSimpleName();
	
	public Test_Base() {
		super(ActivityUnitTests.class);
	}

	// =====================
	// Helper Methods
	// =====================
	public void login() {
		final CountDownLatch latch = new CountDownLatch(1);
		UtilHttpAaa.authenticatePost(TESTACCOUNT_USERNAME, TESTACCOUNT_PASSWORD, new TextHttpResponseHandler() {
			@Override public void onSuccess(int statusCode, Header[] headers, String responseString) {
				UtilLogger.logInfo(TAG, "Http Authenticate Post onSuccess()");
				
				PojoAaaAuthenticatePost pojo = new PojoAaaAuthenticatePost(responseString);
				String authenticationToken = pojo.token;
				
				UtilHttpAaa.authorizePost(authenticationToken, new TextHttpResponseHandler() {
					
					@Override public void onSuccess(int statusCode, Header[] headers, String responseString) {
						UtilLogger.logInfo(TAG, "Http Authorize Post onSuccess()");
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
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void logout() {
		final CountDownLatch latch = new CountDownLatch(1);
		UtilHttpAaa.logoutPost(new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, String responseString) {
				UtilLogger.logInfo(TAG, "Http Logout Post onSuccess()");
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				UtilLogger.logInfo(TAG, "Http Logout Post onSuccess()");
			}
			
			@Override
			public void onFinish() {
				latch.countDown();
			}
		});
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
