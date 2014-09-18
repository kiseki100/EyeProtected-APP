package wangchao.voicemodule;

import com.special.ResideMenuDemo.HomeFragment;
import com.special.ResideMenuDemo.R;

import wangchao.service.VisualChartMethods;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends Activity{

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		ResultActivity.this.finish();
	}

//	private TextView eyehow;
	private TextView left_result;
	private TextView right_result;
	private Button back;
	VisualChartMethods vcm = new VisualChartMethods();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
		
//		eyehow = (TextView) findViewById(R.id.resulttitle);
		left_result = (TextView) findViewById(R.id.leftresult);
		right_result = (TextView) findViewById(R.id.rightresult);
		back = (Button) findViewById(R.id.back);
		
		Intent intent_result=getIntent();
		String leftresult = intent_result.getStringExtra("leftresult");
		String rightresult = intent_result.getStringExtra("rightresult");
		
//		if(vcm.getEye() == 1)
//		{
//			eyehow.setText("jiaozheng");
//		}else
//		{
//			eyehow.setText("luoyan");
//		}
		
		
		left_result.setText(leftresult);
		right_result.setText(rightresult);
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent_back = new Intent();
				intent_back.setClass(ResultActivity.this,BaiduVoiceTest.class);
				ResultActivity.this.startActivity(intent_back);
			}
		});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
