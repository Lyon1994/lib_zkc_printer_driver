package com.zkc.pinter.activity;

import com.example.btpdemo76.R;
import com.zkc.helper.printer.PrinterClass;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PrintTextActivity extends Activity {
	private TextView et_input;
	private Button bt_print,btnUnicode;// 文字打印
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_print_text);
		
		et_input=(EditText)findViewById(R.id.et_input);
		et_input.setText("打印机测试(Printer Testing)");
		
		bt_print = (Button) findViewById(R.id.bt_print);		
		bt_print.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String message = et_input.getText().toString();
				
				PrintActivity.pl.printText(message+"\r\n");
			}
		});
		
		btnUnicode=(Button)findViewById(R.id.btnUnicode);
		btnUnicode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String message = et_input.getText().toString();
				
				PrintActivity.pl.printUnicode(message);
				PrintActivity.pl.printText("\r\n");
			}
		});
	}
}
