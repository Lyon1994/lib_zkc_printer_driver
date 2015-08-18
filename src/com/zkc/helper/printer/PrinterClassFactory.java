package com.zkc.helper.printer;

import android.content.Context;
import android.os.Handler;

import com.zkc.helper.printer.bt.BtService;
import com.zkc.helper.printer.wifi.WifiService;

public class PrinterClassFactory {
	/**
	 * 
	 * @param type
	 * @param _context
	 * @param _mhandler
	 *            通知打印机连接状态的回调对象
	 * @param _handler
	 *            接受回传的数据并进行处理
	 * @return
	 */
	public static PrinterClass create(int type, Context _context,
			Handler _mhandler, Handler _handler) {
		if (type == 0) {
			return new BtService(_context, _mhandler, _handler);
		} else if (type == 1) {
			return new WifiService(_context, _mhandler, _handler);
		}
		return null;
	}

}
