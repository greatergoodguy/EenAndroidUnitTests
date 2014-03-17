package com.eagleeye.mobileapp.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.http.Header;
import org.json.JSONArray;
import org.junit.Assert;

import com.eagleeye.mobileapp.extension.Ext_Assert;
import com.eagleeye.mobileapp.pojo.PojoListDevice;
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
							UtilLogger.logInfo(TAG, responseString);
							
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
							UtilLogger.logInfoAsJson(TAG, responseString);
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

		for(PojoListDevice pojoListDevice : pojoListDevices.devices) {
			Ext_Assert.assertStringNotNullOrEmpty(pojoListDevice.account_id);
			Ext_Assert.assertStringNotNullOrEmpty(pojoListDevice.id);
			Ext_Assert.assertStringNotNullOrEmpty(pojoListDevice.name);
			Ext_Assert.assertStringNotNullOrEmpty(pojoListDevice.type);
			Assert.assertNotNull(pojoListDevice.bridges);

			Ext_Assert.assertStringNotNullOrEmpty(pojoListDevice.service_status);
			Ext_Assert.assertStringNotNullOrEmpty(pojoListDevice.permissions);
			Assert.assertNotNull(pojoListDevice.tags);
			Ext_Assert.assertStringNotNullOrEmpty(pojoListDevice.guid);
			Ext_Assert.assertStringNotNullOrEmpty(pojoListDevice.serial_number);

			Ext_Assert.assertStringNotNullOrEmpty(pojoListDevice.timezone);

			// TODO: Assert Failure
			Ext_Assert.assertStringNotNullOrEmpty(pojoListDevice.ip_address);

			Ext_Assert.assertStringNotNullOrEmpty(pojoListDevice.owner_account_name);

			Ext_Assert.assertStringNotNullOrEmpty(pojoListDevice.video_input);
			Ext_Assert.assertStringNotNullOrEmpty(pojoListDevice.video_status);
		}
	}

//	PojoListUsers pojoListUsers;
//	boolean isListUsersGetSuccessful;
//	public void testHttpListUsers() {
//		UtilLogger.logInfo(TAG, "testListUsers()");
//
//		final CountDownLatch latch = new CountDownLatch(1);
//		isListUsersGetSuccessful = false;
//
//
//		try {
//			runTestOnUiThread(new Runnable() {
//
//				@Override
//				public void run() {
//					UtilHttpList.usersGet(new JsonHttpResponseHandler() {
//
//						@Override
//						public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
//							UtilLogger.logInfo(TAG, "Http List Devices Get onSuccess()");
//							UtilLogger.logInfoAsJsonArray(TAG, jsonArray);
//							
//							pojoListUsers = new PojoListUsers(jsonArray);
//							isListUsersGetSuccessful = true;
//							latch.countDown();
//						}
//
//						@Override
//						public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//							UtilLogger.logInfo(TAG, "Http List Devices Get onFailure()");
//							isListUsersGetSuccessful = false;
//							latch.countDown();
//
//						}
//					});
//				}
//			});
//		} catch (Throwable e) {
//			e.printStackTrace();
//		}
//
//
//		try {
//			latch.await(TIMER_NETWORK_TIMEOUT_IN_SECOUNDS, TimeUnit.SECONDS);
//			Assert.assertTrue(true);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//
//		Assert.assertTrue(isListUsersGetSuccessful);
//		Assert.assertNotNull(pojoListUsers);
//		Assert.assertTrue(false);
//	}

//	public void testHttpListAccouts() {
//		Assert.assertTrue(false);
//	}
}
