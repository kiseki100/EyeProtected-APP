package wangchao.voicemodule;

import java.util.ArrayList;
import java.util.HashMap;

import com.special.ResideMenuDemo.R;

import wangchao.service.*;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
/*
 * 测试临时用，留下做参考用，无实际意义
 */
public class VisualTest extends Activity {

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
		VisualTest.this.finish();
	}
	private Button uptest;
	private Button downtest;
	private Button righttest;
	private Button lefttest;
	private ImageView imageView = null;
	private TextView testtitle;
	
//	private Button speak;
	
	int imageNum = 0;
	
	
	int direct = 2;//初始默认方向为right
	int chart_row = 1; //初始视力表行数
	int chart_counter = 0;//计同一行判断正确次数
	int chart_counterf = 0; //计同一行判断错误次数
	int times = 0;//测试次数，0为左眼，1为右眼，此后结束
	int result[] =new int[2]; //左右眼视力对应的行数
	
	VisualChartMethods vcm = new VisualChartMethods();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.visualtest);
		
		Intent intent_go=getIntent();
		
		testtitle = (TextView) findViewById(R.id.testtitle);
		testtitle.setText("左眼测试：");
		
		uptest = (Button) findViewById(R.id.uptest);
		downtest = (Button) findViewById(R.id.downtest);
		righttest = (Button) findViewById(R.id.righttest);
		lefttest = (Button) findViewById(R.id.lefttest);
//		speak = (Button) findViewById(R.id.speaktest);
		
	     imageView = (ImageView)findViewById(R.id.image);
	     imageView.setImageDrawable(getResources().getDrawable(R.drawable.e_right));     
   
	    uptest.setOnClickListener(new JudgeListener());
	    downtest.setOnClickListener(new JudgeListener());
	    righttest.setOnClickListener(new JudgeListener());
	    lefttest.setOnClickListener(new JudgeListener());
	    
//	    speak.setOnClickListener(new VoiceListener());    
	
	
	}
	
//	/*
//	 * speak按钮
//	 */
//	class VoiceListener implements OnClickListener
//	{
//
//		@Override
//		public void onClick(View arg0) {
//			// TODO Auto-generated method stub
//			
//		}
//		
//	}
	
	/*
	 * 判断测试按键是否与图案一致，并返回判断结果
	 */
	class JudgeListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			int judgeok = 0;//方向正误的判断值
			//imageView.setImageDrawable(getResources().getDrawable(vcm.setImageE(direct)));
//			System.out.println("imageView.getId():"+imageView.getId());
//        	System.out.println("v.getId():"+v.getId());
//        	System.out.println("up:"+R.id.uptest);
        
        
			//单次方向判断
			if(v.getId() == R.id.uptest)
			{
				if(direct == 0)
					{
					judgeok = 1;
					chart_counter += 1;
					}
				else
				{
					judgeok = 0;
					chart_counterf +=1;
				}
			}else if(v.getId() == R.id.downtest)
			{
				if(direct == 1)
				{
					judgeok = 1;
					chart_counter += 1;
				}
			    else
			    {
			    	judgeok = 0;
			    	chart_counterf +=1;
			    }
			}else if(v.getId() == R.id.righttest)
			{
				if(direct == 2)
				{
					judgeok = 1;
					chart_counter += 1;
				}
			    else
			    {
			    	judgeok = 0;
			    	chart_counterf +=1;
			    }
			}else if(v.getId() == R.id.lefttest)
			{
				if(direct == 3)
				{
					judgeok = 1;
					chart_counter += 1;
				}
			    else
			    {
			    	judgeok = 0;
			    	chart_counterf +=1;
			    }
			}
			
			//总方式的计数
			if(chart_row >14)
			{
				if(times == 0)
				{
			        result[times] = chart_row;
			        times += 1;
			        testtitle.setText("右眼测试：");
				}else if(times == 1)
				{
					result[times] = chart_row;
					times = 0;
					System.out.println("result[0]:"+result[0]);
					System.out.println("result[1]:"+result[1]);					
					Intent result_show = new Intent();
					result_show.putExtra("leftresult",vcm.chart(result[0]));
					result_show.putExtra("rightresult",vcm.chart(result[1]));
					result_show.setClass(VisualTest.this,ResultActivity.class);
					VisualTest.this.startActivity(result_show);
				}
			}else if(judgeok == 0)
			{
				chart_counter = 0;
				if(chart_counterf == 2)
				{
					chart_counterf = 0;
					chart_counter = 0;
					System.out.println("result:  "+chart_row);
					if(times == 0)
					{
				        result[times] = chart_row;
				        times += 1;
					}else if(times == 1)
					{
						result[times] = chart_row;
						times = 0;
						System.out.println("result[0]:"+result[0]);
						System.out.println("result[1]:"+result[1]);
						Intent result_show = new Intent();
						result_show.putExtra("leftresult",vcm.chart(result[0]));
						result_show.putExtra("rightresult",vcm.chart(result[1]));
						result_show.setClass(VisualTest.this,ResultActivity.class);
						VisualTest.this.startActivity(result_show);
					}
					
					chart_row = 1;
				}
			}else if(judgeok == 1)
			{
				if(chart_row == 1)
				{
					chart_row += 1;
					chart_counter = 0;
					chart_counterf = 0;
				}else if(chart_row != 1)
				{
					if(chart_counter == 2)
						{
						chart_row += 1;
						chart_counterf = 0;
					    chart_counter = 0;
					    }
				}
			}
			
			
			System.out.println("judgeok        = "+ judgeok);		
			System.out.println("chart_counter  = "+ chart_counter);
			System.out.println("chart_counterf = "+ chart_counterf);
			System.out.println("chart_row      = "+ chart_row);
			direct = vcm.direct();
			
			
			//切换图片
			imageView.setImageDrawable(getResources().getDrawable(vcm.setImageE(direct)));
		}}
}

