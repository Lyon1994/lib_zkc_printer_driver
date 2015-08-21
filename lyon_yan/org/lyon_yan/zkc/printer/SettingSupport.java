package org.lyon_yan.zkc.printer;

import com.zkc.pinter.activity.PrintActivity;

public class SettingSupport {
	/**
	 * 检测型号,1B 2B
	 */
	public static void checkoutModel() {
		byte[] bt = hexStringToBytes("1B 2B");
		PrintActivity.pl.write(bt);
	}

	/**
	 * 水平制表,09
	 */
	public static void addTab() {
		byte[] bt = hexStringToBytes("09");
		PrintActivity.pl.write(bt);
	}

	/**
	 * 换行,0A
	 */
	public static void nextLine() {
		byte[] bt = hexStringToBytes("0A");
		PrintActivity.pl.write(bt);
	}

	/**
	 * 打印当前存储内容,0D
	 */
	public static void printAll() {
		byte[] bt = hexStringToBytes("0D");
		PrintActivity.pl.write(bt);
	}

	/**
	 * 初始化打印机,1B 40
	 */
	public static void init() {
		byte[] bt = hexStringToBytes("1B 2B");
		PrintActivity.pl.write(bt);
	}

	/**
	 * 允许下划线打印,1C 2D 01
	 */
	public static void bottomLine() {
		byte[] bt = hexStringToBytes("1B 2B");
		PrintActivity.pl.write(bt);
	}

	/**
	 * 禁止下划线打印,1C 2D 00
	 */
	public static void noBottomLine() {
		byte[] bt = hexStringToBytes("1B 2B");
		PrintActivity.pl.write(bt);
	}

	/**
	 * 允许粗体打印,1B 45 01
	 */
	public static void blodText() {
		byte[] bt = hexStringToBytes("1B 2B");
		PrintActivity.pl.write(bt);
	}

	/**
	 * 禁止粗体打印,1B 45 00
	 */
	public static void noBlodText() {
		byte[] bt = hexStringToBytes("1B 2B");
		PrintActivity.pl.write(bt);
	}

	/**
	 * 选择字体：ASCII(12*24) 汉字（24*24）,1B 4D 00
	 */
	public static void selectFont24() {
		byte[] bt = hexStringToBytes("1B 4D 00");
		PrintActivity.pl.write(bt);
	}

	/**
	 * 选择字体：ASCII(8*16) 汉字（16*16）,1B 4D 01
	 */
	public static void selectFont16() {
		byte[] bt = hexStringToBytes("1B 4D 01");
		PrintActivity.pl.write(bt);
	}

	/**
	 * 字符正常： 不放大,1D 21 00
	 */
	public static void simpleText() {
		byte[] bt = hexStringToBytes("1D 21 00");
		PrintActivity.pl.write(bt);
	}

	/**
	 * 字符2倍高：纵向放大,1D 21 01
	 */
	public static void zoomVText() {
		byte[] bt = hexStringToBytes("1D 21 01");
		PrintActivity.pl.write(bt);
	}

	/**
	 * 字符2倍宽：横向放大,1D 21 10
	 */
	public static void zoomHText() {
		byte[] bt = hexStringToBytes("1D 21 10");
		PrintActivity.pl.write(bt);
	}

	/**
	 * 字符2倍整体放大,1D 21 11
	 */
	public static void zoomHVText() {
		byte[] bt = hexStringToBytes("1D 21 11");
		PrintActivity.pl.write(bt);
	}

	/**
	 * 左对齐,1B 61 00
	 */
	public static void alignLeft() {
		byte[] bt = hexStringToBytes("1B 61 00");
		PrintActivity.pl.write(bt);
	}

	/**
	 * 居中对齐,1B 61 01
	 */
	public static void alignCenter() {
		byte[] bt = hexStringToBytes("1B 61 01");
		PrintActivity.pl.write(bt);
	}

	/**
	 * 居右对齐,1B 61 02
	 */
	public static void alignRight() {
		byte[] bt = hexStringToBytes("1B 61 02");
		PrintActivity.pl.write(bt);
	}

	/**
	 * 页进纸,0C
	 */
	public static void scrollPage() {
		byte[] bt = hexStringToBytes("0C");
		PrintActivity.pl.write(bt);
	}

	/**
	 * 将字符串形式表示的十六进制数转换为byte数组
	 */
	private static byte[] hexStringToBytes(String hexString) {
		hexString = hexString.toLowerCase();
		String[] hexStrings = hexString.split(" ");
		byte[] bytes = new byte[hexStrings.length];
		for (int i = 0; i < hexStrings.length; i++) {
			char[] hexChars = hexStrings[i].toCharArray();
			bytes[i] = (byte) (charToByte(hexChars[0]) << 4 | charToByte(hexChars[1]));
		}
		return bytes;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789abcdef".indexOf(c);
	}
}
