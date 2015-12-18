package com.ihealth.devices;

import com.example.devicelibtest.R;
import com.ihealth.communication.control.Am3sControl;
import com.ihealth.communication.manager.iHealthDevicesCallback;
import com.ihealth.communication.manager.iHealthDevicesManager;
import com.ihealth.utils.MyLog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class AM3SActivity extends Activity implements OnClickListener{

	private static final String TAG = "AM3SActivity";
	private MyLog myLog;
	
	private Am3sControl am3sControl;
	private String mac;
	private int clientId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_am3_s);
		myLog = new MyLog(TAG);
		
		clientId = iHealthDevicesManager.getInstance().registerClientCallback(iHealthDevicesCallback);
		
		iHealthDevicesManager.getInstance().addCallbackFilterForDeviceType(clientId,
                iHealthDevicesManager.TYPE_AM3S);
		
		Intent intent = getIntent();
		this.mac = intent.getStringExtra("mac");
		//button
		findViewById(R.id.getcontrol).setOnClickListener(this);
		findViewById(R.id.getbattery).setOnClickListener(this);
		findViewById(R.id.getuserid).setOnClickListener(this);
		findViewById(R.id.getalarmnum).setOnClickListener(this);
		findViewById(R.id.syncstage).setOnClickListener(this);
		findViewById(R.id.syncactivity).setOnClickListener(this);
		findViewById(R.id.syncreal).setOnClickListener(this);
		findViewById(R.id.getuserinfo).setOnClickListener(this);
	}
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		iHealthDevicesManager.getInstance().unRegisterClientCallback(clientId);
	}


	private iHealthDevicesCallback iHealthDevicesCallback = new iHealthDevicesCallback() {

		@Override
		public void onScanDevice(String mac, String deviceType) {}

		@Override
		public void onDeviceConnectionStateChange(String mac, String deviceType, int status) {}

		@Override
		public void onDeviceNotify(String mac, String deviceType, String action, String message) {
			myLog.i(action);
			if (message != null) {
				myLog.i(message);
			}
		}
	};
	
	@Override
	public void onClick(View arg0) {
		int id = arg0.getId();
		switch (id) {
		case R.id.getcontrol:
			am3sControl = iHealthDevicesManager.getInstance().getAm3sControl(this.mac);
			myLog.i("获取控制");
			break;
		case R.id.getbattery:
			if (am3sControl != null) {
				am3sControl.queryAMState();
			}
			break;
		case R.id.getuserid:
			if (am3sControl != null) {
				am3sControl.getUserId();
			}
			break;
		case R.id.getalarmnum:
			if (am3sControl != null) {
				am3sControl.getAlarmClockNum();
			}
			break;
		case R.id.syncstage:
			if (am3sControl != null) {
				am3sControl.syncStageReprotData();
			}
			break;
		case R.id.syncactivity:
			if (am3sControl != null) {
				am3sControl.syncActivityData();
			}
			break;
		case R.id.syncreal:
			if (am3sControl != null) {
				am3sControl.syncRealData();
			}
			break;
		case R.id.getuserinfo:
			if (am3sControl != null) {
				am3sControl.getUserInfo();
			}
			break;
		default:
			break;
		}
	}
}
