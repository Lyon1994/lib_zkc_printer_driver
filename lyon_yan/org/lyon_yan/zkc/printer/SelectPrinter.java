package org.lyon_yan.zkc.printer;

import java.util.ArrayList;
import java.util.List;

import org.lyon_yan.android.utils.data.DataRecordByAndroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
//import android.widget.Toast;

import com.zkc.helper.printer.Device;
import com.zkc.helper.printer.PrinterClass;
import com.zkc.helper.printer.PrinterClassFactory;
import com.zkc.pinter.activity.PrintActivity;
import com.zkc.pinter.activity.PrintSettingActivity;

public class SelectPrinter {
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;
	public static final String KEY_SELECTED_PRINTER = "SELECTED_PRINTER_BT_CMD";

	public SelectPrinter(final Activity activity) {
		// TODO Auto-generated constructor stub
		if (PrintActivity.pl == null) {
			initMainActivity(activity);
		}
		initSetting(activity);
		if (getSelectedPrinter(activity).equals("")) {
			initPrintActivity(activity);
		}
	}

	public static void initMainActivity(final Activity activity) {
		PrintActivity.pl = PrinterClassFactory.create(0, activity,
				new Handler() {
					public void handleMessage(Message msg) {
						super.handleMessage(msg);
						switch (msg.what) {
						case MESSAGE_STATE_CHANGE:// 蓝牙连接状
							switch (msg.arg1) {
							case PrinterClass.STATE_CONNECTED:// 已经连接
								break;
							case PrinterClass.STATE_CONNECTING:// 正在连接
								break;
							case PrinterClass.STATE_LISTEN:
							case PrinterClass.STATE_NONE:
								break;
							case PrinterClass.SUCCESS_CONNECT:
								break;
							case PrinterClass.FAILED_CONNECT:
								break;
							case PrinterClass.LOSE_CONNECT:
								break;
							}
							break;
						case MESSAGE_READ:
//							byte[] readBuf = (byte[]) msg.obj;
							// construct a string from the valid bytes in the
							// buffer
//							String readMessage = new String(readBuf, 0,
//									msg.arg1);
//							Toast.makeText(activity, readMessage,
//									Toast.LENGTH_SHORT).show();
							break;
						case MESSAGE_WRITE:// 缓冲区未�?
							// sendFlag = true;
							break;
						}
					}
				}, new Handler() {
					@Override
					public void handleMessage(Message msg) {
						super.handleMessage(msg);
						switch (msg.what) {
						case 0:
							break;
						case 1:// 扫描完毕
								// PrintActivity.pl.stopScan();
							Device d = (Device) msg.obj;
							if (d != null) {
								/**
								 * 初始化deviceList
								 */
								if (PrintSettingActivity.deviceList == null) {
									PrintSettingActivity.deviceList = new ArrayList<Device>();
								}
								/**
								 * 判断并显示已配对的蓝牙设备
								 */
								if (!checkData(PrintSettingActivity.deviceList,
										d)) {
									PrintSettingActivity.deviceList.add(d);
								}
							}
							break;
						case 2:// 停止扫描
							break;
						}
					}
				});
	}

	/**
	 * 同步執行，有阻断
	 * 
	 * @param activity
	 */

	private void initPrintActivity(Activity activity) {
		// TODO Auto-generated method stub
		if (PrintActivity.pl.getState() != PrinterClass.STATE_CONNECTED) {
			Intent intent = new Intent();
			intent.setClass(activity, PrintSettingActivity.class);
			activity.startActivityForResult(intent, 0);
		}
	}

	private static boolean checkData(List<Device> list, Device d) {
		for (Device device : list) {
			if (device.deviceAddress.equals(d.deviceAddress)) {
				return true;
			}
		}
		return false;
	}

	private void initSetting(Activity activity) {
		// TODO Auto-generated method stub
		// 允许主线程连接网络
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		if (!PrintActivity.pl.IsOpen()) {
			PrintActivity.pl.open(activity);
			PrintActivity.pl.scan();
			PrintSettingActivity.deviceList = PrintActivity.pl.getDeviceList();
		}
		PrintActivity.pl.disconnect();
		PrintActivity.pl.connect(getSelectedPrinter(activity));
	}

	public static void saveSelectedPrinter(Context c, String cmd) {
		DataRecordByAndroid dataRecordByAndroid = new DataRecordByAndroid(c);
		try {
			dataRecordByAndroid.saveValue(KEY_SELECTED_PRINTER, cmd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void startSettingPrintActivity(Activity activity) {
		activity.startActivity(new Intent(activity, PrintSettingActivity.class));
	}

	public static String getSelectedPrinter(Context c) {
		DataRecordByAndroid dataRecordByAndroid = new DataRecordByAndroid(c);
		String default_value = "";
		try {
			return dataRecordByAndroid.getValue(KEY_SELECTED_PRINTER,
					default_value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return default_value;
		}
	}
}
