package wangchao.voicemodule;

import com.special.ResideMenuDemo.R;

import wangchao.service.*;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class GoActivity extends Activity {

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		finish();
	}
	
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		GoActivity.this.finish();
	}


	private RadioGroup group_select;
	private Button go_button;
	VisualChartMethods vcm = new VisualChartMethods();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.go_layout);
		Intent intent_start=getIntent();
		
		go_button = (Button)findViewById(R.id.go_button);		
		 go_button.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent_start=new Intent();
					intent_start.setClass(GoActivity.this,BaiduVoiceTest.class);
					GoActivity.this.startActivity(intent_start);
				}
			});
		 
		group_select = (RadioGroup) findViewById(R.id.selectGroup);
		group_select.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(checkedId == R.id.jiaozhengvisual)
				{
//					vcm.setEye(1);
					
					
				}else if(checkedId == R.id.luoyanvisual)
				{
//					vcm.setEye(0);
				}
			}
		});
	}
	
	
}
