package com.eagleeye.mobileapp.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.http.Header;
import org.junit.Assert;

import com.eagleeye.mobileapp.util.UtilLogger;
import com.eagleeye.mobileapp.util.http.UtilHttpAction;
import com.loopj.android.http.TextHttpResponseHandler;

public class TestHttpAction extends Test_Base {
	
	boolean isAllOnPostSuccessful = false;
	public void testHttpActionAllOn() {
		UtilLogger.logInfo(TAG, "testActionAllOn()");
		
		final CountDownLatch latch = new CountDownLatch(1);
		try {
			runTestOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					UtilHttpAction.allOnPost(new TextHttpResponseHandler() {
						
						@Override
						public void onSuccess(int statusCode, Header[] headers, String responseString) {
							UtilLogger.logInfo(TAG, "Http Action All On Post onSuccess()");
							isAllOnPostSuccessful = true;
							latch.countDown();
						}
						
						@Override
						public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
							UtilLogger.logInfo(TAG, "Http Action All On Post onFailure()");
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
		
		Assert.assertTrue(isAllOnPostSuccessful);
	}
	
	boolean isAllOffPostSuccessful = false;
	public void testHttpActionAllOff() {
		UtilLogger.logInfo(TAG, "testActionAllOff()");
		
		final CountDownLatch latch = new CountDownLatch(1);
		try {
			runTestOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					UtilHttpAction.allOffPost(new TextHttpResponseHandler() {
						
						@Override
						public void onSuccess(int statusCode, Header[] headers, String responseString) {
							UtilLogger.logInfo(TAG, "Http Action All Off Post onSuccess()");
							isAllOffPostSuccessful = true;
							latch.countDown();
						}
						
						@Override
						public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
							UtilLogger.logInfo(TAG, "Http Action All Off Post onFailure()");
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
		
		Assert.assertTrue(isAllOffPostSuccessful);
	}
	public void testHttpActionRecordNow() {
		Assert.assertTrue(false);
	}
	
	public void testThisMethodAGoatIsNotAGoat() {
		Assert.assertTrue(true);
	}
}
