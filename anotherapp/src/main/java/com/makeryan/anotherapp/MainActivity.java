package com.makeryan.anotherapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.makeryan.lib.services.IAppServiceRemoteBinder;

public class MainActivity
		extends AppCompatActivity
		implements View.OnClickListener,
				   ServiceConnection {

	private Intent intentService = null;

	private IAppServiceRemoteBinder mRemoteBinder = null;

	private EditText mEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		intentService = new Intent();
		intentService.setComponent(new ComponentName(
				"com.makeryan.lib",
				"com.makeryan.lib.services.MkService"
		));

		intentService.putExtra(
				"page",
				"com.makeryan.modules.mine.ui.fragment.RandomFragment"
							  );
		//		findViewById(R.id.btnStartService).setOnClickListener(this);
		//		findViewById(R.id.btnStopService).setOnClickListener(this);
		findViewById(R.id.btnStartExternalService).setOnClickListener(this);
		findViewById(R.id.btnStopExternalService).setOnClickListener(this);
		findViewById(R.id.btnBindExternalService).setOnClickListener(this);
		findViewById(R.id.btnUnbindExternalService).setOnClickListener(this);
		findViewById(R.id.btnSync).setOnClickListener(this);
		mEditText = (EditText) findViewById(R.id.editText);
	}

	/**
	 * Called when a view has been clicked.
	 *
	 * @param v
	 * 		The view that was clicked.
	 */
	@Override
	public void onClick(View v) {

		int id = v.getId();
		/*if (id == R.id.btnStartService) {
			startService(intentService);
		} else if (id == R.id.btnStopService) {
			stopService(intentService);
		} else*/
		if (id == R.id.btnStartExternalService) {
			startService(intentService);
		} else if (id == R.id.btnStopExternalService) {
			stopService(intentService);
		} else if (id == R.id.btnBindExternalService) {
			bindService(
					intentService,
					this,
					Context.BIND_AUTO_CREATE
					   );
		} else if (id == R.id.btnUnbindExternalService) {
			unbindService(this);
			mRemoteBinder = null; // 解除绑定将Binder置空
		} else if (id == R.id.btnSync) {
			if (mRemoteBinder != null) {
				try {
					mRemoteBinder.setData(mEditText.getText()
												   .toString());
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Called when a connection to the Service has been established, with
	 * the {@link IBinder} of the communication channel to the
	 * Service.
	 *
	 * @param name
	 * 		The concrete component name of the service that has
	 * 		been connected.
	 * @param service
	 * 		The IBinder of the Service's communication channel,
	 */
	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {

		mRemoteBinder = IAppServiceRemoteBinder.Stub.asInterface(service);
	}

	/**
	 * Called when a connection to the Service has been lost.  This typically
	 * happens when the process hosting the service has crashed or been killed.
	 * This does <em>not</em> remove the ServiceConnection itself -- this
	 * binding to the service will remain active, and you will receive a call
	 * to {@link #onServiceConnected} when the Service is next running.
	 *
	 * @param name
	 * 		The concrete component name of the service whose
	 * 		connection has been lost.
	 */
	@Override
	public void onServiceDisconnected(ComponentName name) {

		mRemoteBinder = null;
	}
}
