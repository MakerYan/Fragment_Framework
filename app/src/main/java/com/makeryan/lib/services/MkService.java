package com.makeryan.lib.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.socks.library.KLog;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

public class MkService
		extends Service {

	protected String mData = "默认数据";

	protected Subscription mTaskSubscription;

	public MkService() {

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		return new IAppServiceRemoteBinder.Stub() {

			/**
			 * Demonstrates some basic types that you can use as parameters
			 * and return values in AIDL.
			 *
			 * @param anInt
			 * @param aLong
			 * @param aBoolean
			 * @param aFloat
			 * @param aDouble
			 * @param aString
			 */
			@Override
			public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString)
					throws RemoteException {

			}

			@Override
			public void setData(String data)
					throws RemoteException {

				mData = data;
			}
		};
	}

	@Override
	public void onCreate() {

		super.onCreate();
		KLog.d("MkService onCreate");
		releaseTaskSubscription();
		mTaskSubscription = Observable.interval(
				1000,
				TimeUnit.MILLISECONDS
											   )
									  .subscribe(new Subscriber<Long>() {

										  @Override
										  public void onCompleted() {

										  }

										  @Override
										  public void onError(Throwable e) {

										  }

										  @Override
										  public void onNext(Long aLong) {

											  KLog.d(Thread.currentThread()
														   .getName());
											  KLog.d(mData);
										  }
									  });
	}

	@Override
	public void onStart(Intent intent, int startId) {

		super.onStart(
				intent,
				startId
					 );
		KLog.d("MkService onStart");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		KLog.d("MkService onStartCommand");
		return super.onStartCommand(
				intent,
				flags,
				startId
								   );
	}

	@Override
	public void onDestroy() {

		super.onDestroy();
		KLog.d("MkService onDestroy");
		releaseTaskSubscription();
	}

	private void releaseTaskSubscription() {

		if (mTaskSubscription != null) {
			mTaskSubscription.unsubscribe();
			mTaskSubscription = null;
		}
	}
}
