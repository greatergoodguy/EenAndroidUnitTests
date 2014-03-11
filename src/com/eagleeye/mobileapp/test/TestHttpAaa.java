package com.eagleeye.mobileapp.test;

import java.util.concurrent.CountDownLatch;

import org.apache.http.Header;
import org.junit.Assert;

import com.eagleeye.mobileapp.pojo.PojoAaaAuthenticatePost;
import com.eagleeye.mobileapp.util.UtilLogger;
import com.eagleeye.mobileapp.util.UtilRunnable;
import com.eagleeye.mobileapp.util.http.UtilHttpAaa;
import com.eagleeye.mobileapp.util.http.UtilHttpPoll;
import com.loopj.android.http.TextHttpResponseHandler;

public class TestHttpAaa extends Test_Base implements Constants {

	@Override
	public void setUp() throws Exception  {
		super.setUp();
	}

	public void testThisMethodAGoatIsNotAGoat() {
		// Assert false because this method is not a goat,
		// it is a method.
		Assert.assertFalse(false);
	}

	String authenticationToken = "";
	boolean isHttpAaaSuccessfulAuthenticate = false;
	boolean isHttpAaaSuccessfulAuthorize = false;
	public void testHttpLogin() throws Throwable{
		UtilLogger.logInfo("TestActivityUnitTests", "testHttpLogin()");

		final CountDownLatch latch = new CountDownLatch(1);
		
		runTestOnUiThread(new Runnable() {
			@Override public void run() {
				httpAaaAuthenticate(
						new Runnable() {
							@Override public void run() {
								isHttpAaaSuccessfulAuthenticate = true;
								
								httpAaaAuthorize(authenticationToken, 
										new Runnable() {
									@Override public void run() {
										isHttpAaaSuccessfulAuthorize = true;
										latch.countDown();
									}
								}, 
								new Runnable() {
									@Override public void run() {
										isHttpAaaSuccessfulAuthorize = false;
										latch.countDown();
									}
								});
							}
						}, 
						new Runnable() {
							@Override public void run() {
								isHttpAaaSuccessfulAuthenticate = false;
								latch.countDown();
							}
						});	
			}
		});
		
		try{
            latch.await();
            Assert.assertTrue(isHttpAaaSuccessfulAuthenticate);
    		Assert.assertTrue(isHttpAaaSuccessfulAuthorize);
       }catch(InterruptedException ie){
           ie.printStackTrace();
           Assert.assertTrue(false);
           
       }
	}

	//	boolean isHttpPollGetSuccessful = false;
	//	public void testHttpPollGet() throws Throwable {
	//		httpPollGet(
	//				new Runnable() {
	//					@Override public void run() {
	//						isHttpPollGetSuccessful = true;
	//					}
	//				}, 
	//				new Runnable() {
	//					@Override public void run() {
	//					}
	//				});
	//
	//
	//
	//		signal.await(5, TimeUnit.SECONDS);
	//		Assert.assertTrue(isHttpPollGetSuccessful);
	//	}

	@Override
	public void tearDown()  {
	}

	
	
	// =====================
	// Http Aaa Methods
	// =====================
	private void httpAaaAuthenticate(final Runnable runnableOnSuccess, final Runnable runnableOnFailure) {
		UtilHttpAaa.authenticatePost(TESTACCOUNT_USERNAME, TESTACCOUNT_PASSWORD, new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, String response) {
				UtilLogger.logInfo("TestActivityUnitTests", "Http Aaa Authenticate onSuccess()");
				
				PojoAaaAuthenticatePost pojo = new PojoAaaAuthenticatePost(response);
				authenticationToken = pojo.token;

				UtilRunnable.runRunnableIfNotNull(runnableOnSuccess);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String response, Throwable throwable) {
				UtilLogger.logInfo("TestActivityUnitTests", "Http Aaa Authenticate OnFailure()");
				UtilLogger.logInfo("TestActivityUnitTests", "statusCode, response: " + statusCode + ", " + response);
				
				UtilRunnable.runRunnableIfNotNull(runnableOnFailure);
			}
		});
	}

	private void httpAaaAuthorize(String authenticateToken, final Runnable runnableOnSuccess, final Runnable runnableOnFailure) {

		UtilHttpAaa.authorizePost(authenticateToken, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers, String responseString) {
				UtilLogger.logInfo("TestActivityUnitTests", "Http Aaa Authorize OnSuccess()");
				UtilRunnable.runRunnableIfNotNull(runnableOnSuccess);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				UtilLogger.logInfo("TestActivityUnitTests", "Http Aaa Authorize onFailure()");
				UtilRunnable.runRunnableIfNotNull(runnableOnFailure);
			}
		});
	}

	// =====================
	// Http Poll Methods
	// =====================
	private void httpPollGet(final Runnable runnableOnSuccess, final Runnable runnableOnFailure) {
		UtilHttpPoll.get(new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers, String responseString) {
				UtilRunnable.runRunnableIfNotNull(runnableOnSuccess);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				UtilRunnable.runRunnableIfNotNull(runnableOnFailure);
			}
		});
	}
}
