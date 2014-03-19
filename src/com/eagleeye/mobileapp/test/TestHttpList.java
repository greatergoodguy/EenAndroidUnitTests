package com.eagleeye.mobileapp.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.http.Header;
import org.json.JSONArray;
import org.junit.Assert;
import org.junit.Ignore;

import com.eagleeye.mobileapp.extension.Ext_Assert;
import com.eagleeye.mobileapp.pojo.PojoListDevices;
import com.eagleeye.mobileapp.pojo.PojoListLayouts;
import com.eagleeye.mobileapp.pojo.PojoListUsers;
import com.eagleeye.mobileapp.util.UtilLogger;
import com.eagleeye.mobileapp.util.http.UtilHttpList;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

public class TestHttpList extends Test_Base {

	PojoListLayouts pojoListLayouts;
	boolean isListLayoutsGetSuccessful;
	public void testHttpListLayouts() {
		UtilLogger.logInfo(TAG, "testListLayouts()");

		final CountDownLatch latch = new CountDownLatch(1);
		try {
			runTestOnUiThread(new Runnable() {

				@Override
				public void run() {
					UtilHttpList.layoutsGet(new TextHttpResponseHandler() {

						@Override
						public void onSuccess(int statusCode, Header[] headers, String responseString) {
							UtilLogger.logInfo(TAG, "Http List Devices onSuccess()");
							//UtilLogger.logInfo(TAG, responseString);
							
							isListLayoutsGetSuccessful = true;
							pojoListLayouts = new PojoListLayouts(responseString);
							latch.countDown();
						}

						@Override
						public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
							latch.countDown();
						}

					});
				}
			});
		}
		catch (Throwable e) {
			e.printStackTrace();
		}

		try {
			latch.await(TIMER_NETWORK_TIMEOUT_IN_SECOUNDS, TimeUnit.SECONDS);
			Assert.assertTrue(true);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Assert.assertTrue(isListLayoutsGetSuccessful);
		Assert.assertNotNull(pojoListLayouts);
		Ext_Assert.assertValid(pojoListLayouts);
	}

	PojoListDevices pojoListDevices;
	boolean isListDevicesGetSuccessful;
	public void testHttpListDevices() {
		UtilLogger.logInfo(TAG, "testListDevices()");

		final CountDownLatch latch = new CountDownLatch(1);
		isListDevicesGetSuccessful = false;

		try {
			runTestOnUiThread(new Runnable() {

				@Override
				public void run() {
					UtilHttpList.devicesGet(new TextHttpResponseHandler() {

						@Override
						public void onSuccess(int statusCode, Header[] headers, String responseString) {
							UtilLogger.logInfo(TAG, "Http List Devices Get onSuccess()");
							//UtilLogger.logInfoAsJsonArray(TAG, responseString);
							isListDevicesGetSuccessful = true;
							pojoListDevices = new PojoListDevices(responseString);
							latch.countDown();
						}

						@Override
						public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
							UtilLogger.logInfo(TAG, "Http List Devices Get onFailure()");
							latch.countDown();
						}
					});

				}
			});
		} catch (Throwable e) {
			e.printStackTrace();
		}

		try {
			latch.await(TIMER_NETWORK_TIMEOUT_IN_SECOUNDS, TimeUnit.SECONDS);
			Assert.assertTrue(true);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Assert.assertTrue(isListDevicesGetSuccessful);
		Assert.assertNotNull(pojoListDevices);
		Ext_Assert.assertValid(pojoListDevices);
	}

	PojoListUsers pojoListUsers;
	boolean isListUsersGetSuccessful;
	@Ignore
	public void testHttpListUsers() {
		UtilLogger.logInfo(TAG, "testListUsers()");

		final CountDownLatch latch = new CountDownLatch(1);
		isListUsersGetSuccessful = false;


		try {
			runTestOnUiThread(new Runnable() {

				@Override
				public void run() {
					UtilHttpList.usersGet(new JsonHttpResponseHandler() {

						@Override
						public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
							UtilLogger.logInfo(TAG, "Http List Devices Get onSuccess()");
							UtilLogger.logInfoAsJsonArray(TAG, jsonArray);
							
							pojoListUsers = new PojoListUsers(jsonArray);
							isListUsersGetSuccessful = true;
							latch.countDown();
						}

						@Override
						public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
							UtilLogger.logInfo(TAG, "Http List Devices Get onFailure()");
							isListUsersGetSuccessful = false;
							latch.countDown();

						}
					});
				}
			});
		} catch (Throwable e) {
			e.printStackTrace();
		}


		try {
			latch.await(TIMER_NETWORK_TIMEOUT_IN_SECOUNDS, TimeUnit.SECONDS);
			Assert.assertTrue(true);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Assert.assertTrue(isListUsersGetSuccessful);
		Assert.assertNotNull(pojoListUsers);
		Ext_Assert.assertValid(pojoListUsers);
	}
}
