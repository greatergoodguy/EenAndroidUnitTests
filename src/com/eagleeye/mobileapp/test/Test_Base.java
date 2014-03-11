package com.eagleeye.mobileapp.test;

import org.apache.http.Header;

import android.test.ActivityInstrumentationTestCase2;

import com.eagleeye.mobileapp.ActivityUnitTests;
import com.eagleeye.mobileapp.util.UtilLogger;
import com.eagleeye.mobileapp.util.http.UtilHttpAaa;
import com.loopj.android.http.TextHttpResponseHandler;

public abstract class Test_Base extends ActivityInstrumentationTestCase2<ActivityUnitTests> implements Constants {

	protected final String TAG = getClass().getSimpleName();
	
	String authenticationToken = "";
	
	public Test_Base() {
		super(ActivityUnitTests.class);
	}

	// =====================
	// Helper Methods
	// =====================
	public void login() {
		
	}
	
	public void logout() {
		UtilHttpAaa.logoutPost(new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, String responseString) {
				UtilLogger.logInfo(TAG, "Http Logout Post onSuccess()");
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				UtilLogger.logInfo(TAG, "Http Logout Post onSuccess()");
			}
		});
	}
	
}
