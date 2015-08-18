package com.zkc.helper.printer.bt;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import com.zkc.helper.printer.BlueToothService;
import com.zkc.helper.printer.Device;
import com.zkc.helper.printer.PrintService;
import com.zkc.helper.printer.PrinterClass;
import com.zkc.helper.printer.BlueToothService.OnReceiveDataHandleEvent;
import com.zkc.pinter.activity.MainActivity;
import com.zkc.pinter.activity.PrintActivity;
import com.zkc.pinter.activity.PrintSettingActivity;

public class BtService extends PrintService implements PrinterClass {

	Context context;
	Handler mhandler,handler;
	public static BlueToothService mBTService = null;
	
	public BtService(Context _context,Handler _mhandler,Handler _handler)
	{
		context=_context;
		mhandler=_mhandler;
		handler=_handler;
		mBTService = new BlueToothService(context, mhandler);
		
		mBTService.setOnReceive(new OnReceiveDataHandleEvent() {
			@Override
			public void OnReceive(final BluetoothDevice device) {
				// TODO Auto-generated method stub
				if (device != null) {
					Device d=new Device();
					d.deviceName=device.getName();
					d.deviceAddress=device.getAddress();
					Message msg = new Message();
					msg.what=1;
					msg.obj = d;
					handler.sendMessage(msg);
					setState(STATE_SCANING);
				} else {
					Message msg = new Message();
					msg.what = 8;
					handler.sendMessage(msg);
				}
			}
		});
	}
	
	@Override
	public boolean open(Context context) {
		// TODO Auto-generated method stub
		mBTService.OpenDevice();
		return true;
	}

	@Override
	public boolean close(Context context) {
		// TODO Auto-generated method stub
		mBTService.CloseDevice();
		return false;
	}

	@Override
	public void scan() {
		// TODO Auto-generated method stub
		if (!mBTService.IsOpen()) {// 判断蓝牙是否打开
			mBTService.OpenDevice();
			return;
		}
		if (mBTService.GetScanState() == STATE_SCANING)
			return;
		
		new Thread() {
			public void run() {
				mBTService.ScanDevice();
			}
		}.start();
	}

	@Override
	public boolean connect(String device) {
		// TODO Auto-generated method stub
		if (mBTService.GetScanState() == STATE_SCANING) {
			stopScan();
		}
		if (mBTService.getState() == STATE_CONNECTING) {
			return false;
		}
		mBTService.DisConnected();
		mBTService.ConnectToDevice(device);// 连接蓝牙
    	PrintActivity.pl.setState(PrinterClass.STATE_CONNECTING);
		return true;
	}

	@Override
	public boolean disconnect() {
		// TODO Auto-generated method stub
		mBTService.DisConnected();
		return true;
	}

	@Override
	public int getState() {
		// TODO Auto-generated method stub
		return mBTService.getState();
	}

	@Override
	public boolean write(byte[] bt) {
		// TODO Auto-generated method stub
		mBTService.write(bt);
		return true;
	}

	@Override
	public boolean printText(String textStr) {
		// TODO Auto-generated method stubbyte[] send;
		return write(getText(textStr));
	}

	@Override
	public boolean printImage(Bitmap bitmap) {
		// TODO Auto-generated method stub		
		write(getImage(bitmap));
		return write(new byte[]{0x0a});
	}

	@Override
	public boolean printUnicode(String textStr) {
		// TODO Auto-generated method stub
		return write(getTextUnicode(textStr));
	}

	@Override
	public boolean IsOpen() {
		// TODO Auto-generated method stub
		return mBTService.IsOpen();
	}

	@Override
	public void stopScan() {
		// TODO Auto-generated method stub
		if(PrintActivity.pl.getState()==PrinterClass.STATE_SCANING)
		{
			mBTService.StopScan();
			mBTService.setState(PrinterClass.STATE_SCAN_STOP);
		}
	}

	@Override
	public void setState(int state) {
		// TODO Auto-generated method stub
		mBTService.setState(state);
	}

	@Override
	public List<Device> getDeviceList() {
		List<Device> devList=new ArrayList<Device>();
		// TODO Auto-generated method stub
		Set<BluetoothDevice> devices = mBTService.GetBondedDevice();
		for (BluetoothDevice bluetoothDevice : devices) {
			Device d=new Device();
			d.deviceName=bluetoothDevice.getName();
			d.deviceAddress=bluetoothDevice.getAddress();
			devList.add(d);
		}
		return devList;
	}
}
