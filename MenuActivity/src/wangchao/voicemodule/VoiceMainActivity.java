package wangchao.voicemodule;


import com.special.ResideMenuDemo.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class VoiceMainActivity extends Activity {

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		VoiceMainActivity.this.finish(); 
	}
	
	private Button start_button;
	private Button test_button;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_voice_main);
		
		start_button = (Button) findViewById(R.id.start);
        start_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent_start=new Intent();
				intent_start.setClass(VoiceMainActivity.this,BaiduVoiceTest.class);
				VoiceMainActivity.this.startActivity(intent_start);
			}
		});
//		//test¡Ÿ ±”√
//		test_button = (Button) findViewById(R.id.speaktest);
//		test_button.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent_test=new Intent();
//				//intent_test.setClass(VoiceMainActivity.this,VoiceTest.class);      //google
//				intent_test.setClass(VoiceMainActivity.this,BaiduVoiceTest.class); //baidu
//				VoiceMainActivity.this.startActivity(intent_test);
//			}
//		});
	}
	
	
}

