package wangchao.voicemodule;

import com.baidu.voicerecognition.android.Candidate;
import com.baidu.voicerecognition.android.DataUploader;
import com.baidu.voicerecognition.android.VoiceRecognitionClient;
import com.baidu.voicerecognition.android.VoiceRecognitionClient.VoiceClientStatusChangeListener;
import com.baidu.voicerecognition.android.VoiceRecognitionConfig;



import com.special.ResideMenuDemo.R;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import wangchao.service.Config;
import wangchao.service.Constants;
import wangchao.service.VisualChartMethods;


public class BaiduVoiceTest extends FragmentActivity {
    private ControlPanelFragment mControlPanel;

    @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	private VoiceRecognitionClient mASREngine;

    /** 正在识别中 */
    private boolean isRecognition = false;

    /** 音量更新间隔 */
    private static final int POWER_UPDATE_INTERVAL = 100;

    /** 识别回调接口 */
    private MyVoiceRecogListener mListener = new MyVoiceRecogListener();

    /** 主线程Handler */
    private Handler mHandler;

    /**
     * 结果展示
     */
    private EditText mResult = null;

//    private Button uptest = null;
//	private Button downtest = null;
//	private Button righttest = null;
//	private Button lefttest = null;
	private ImageView imageView = null;
	private TextView testtitle;
	
    VisualChartMethods vcm = new VisualChartMethods();
    
    int imageNum = 0;	
	int direct = 0;//初始默认方向
	int chart_row = 1; //初始视力表行数
	int chart_counter = 0;//计同一行判断正确次数
	int chart_counterf = 0; //计同一行判断错误次数
	int times = 0;//测试次数，0为左眼，1为右眼，此后结束
	int result[] =new int[2]; //左右眼视力对应的行数
	int directE = vcm.setImageE(direct) ;
	int result_one = 0;
	    
    /**
     * 音量更新任务
     */
    private Runnable mUpdateVolume = new Runnable() {
        public void run() {
            if (isRecognition) {
                long vol = mASREngine.getCurrentDBLevelMeter();
                mControlPanel.volumeChange((int) vol);
                mHandler.removeCallbacks(mUpdateVolume);
                mHandler.postDelayed(mUpdateVolume, POWER_UPDATE_INTERVAL);
            }
        }
    };

    VisualChartMethods judge = new VisualChartMethods();
    
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baidu_voice);
        
        Intent intent_test=getIntent();
        
        testtitle = (TextView) findViewById(R.id.testtitle);
		testtitle.setText("开始测试：");
		
		Toast.makeText(getApplicationContext(), "请将手机置于眼前50-70cm处，先进行左眼测试",
			     Toast.LENGTH_SHORT).show();
//		uptest = (Button) findViewById(R.id.uptest);
//		downtest = (Button) findViewById(R.id.downtest);
//		righttest = (Button) findViewById(R.id.righttest);
//		lefttest = (Button) findViewById(R.id.lefttest);
//		
//		uptest.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
		
	     imageView = (ImageView)findViewById(R.id.image);
	     imageView.setImageDrawable(getResources().getDrawable(R.drawable.e_up));  
	     
////////////////////////////////////////////////////////////////////////////////////////   
        mResult = (EditText) findViewById(R.id.recognition_text);
        mASREngine = VoiceRecognitionClient.getInstance(this);
        mASREngine.setTokenApis(Constants.getApiKey(), Constants.getSecretKey());
//        uploadContacts();
        mHandler = new Handler();
        mControlPanel = (ControlPanelFragment) (getSupportFragmentManager()
                .findFragmentById(R.id.control_panel));
        mControlPanel.setOnEventListener(new ControlPanelFragment.OnEventListener() {

            @Override
            public boolean onStopListening() {
                mASREngine.speakFinish();
                return true;
            }

            @Override
            public boolean onStartListening() {
                mResult.setText(null);
                VoiceRecognitionConfig config = new VoiceRecognitionConfig();
                config.setProp(Config.CURRENT_PROP);
                config.setLanguage(Config.getCurrentLanguage());
                config.enableContacts(); // 启用通讯录
                config.enableVoicePower(Config.SHOW_VOL); // 音量反馈。
                if (Config.PLAY_START_SOUND) {
                    config.enableBeginSoundEffect(R.raw.bdspeech_recognition_start); // 设置识别开始提示音
                }
                if (Config.PLAY_END_SOUND) {
                    config.enableEndSoundEffect(R.raw.bdspeech_speech_end); // 设置识别结束提示音
                }
                config.setSampleRate(VoiceRecognitionConfig.SAMPLE_RATE_8K); // 设置采样率,需要与外部音频一致
                // 下面发起识别
                int code = mASREngine.startVoiceRecognition(mListener, config);
                if (code != VoiceRecognitionClient.START_WORK_RESULT_WORKING) {
                    mResult.setText(getString(R.string.error_start, code));
                } 

                return code == VoiceRecognitionClient.START_WORK_RESULT_WORKING;
            }

            @Override
            public boolean onCancel() {
                mASREngine.stopVoiceRecognition();
                return true;
            }
        });
       
    };

    /**
     * 重写用于处理语音识别回调的监听器
     */
    class MyVoiceRecogListener implements VoiceClientStatusChangeListener {

        @Override
        public void onClientStatusChange(int status, Object obj) {
            switch (status) {
            // 语音识别实际开始，这是真正开始识别的时间点，需在界面提示用户说话。
                case VoiceRecognitionClient.CLIENT_STATUS_START_RECORDING:
                    isRecognition = true;
                    mHandler.removeCallbacks(mUpdateVolume);
                    mHandler.postDelayed(mUpdateVolume, POWER_UPDATE_INTERVAL);
                    mControlPanel.statusChange(ControlPanelFragment.STATUS_RECORDING_START);
                    break;
                case VoiceRecognitionClient.CLIENT_STATUS_SPEECH_START: // 检测到语音起点
                    mControlPanel.statusChange(ControlPanelFragment.STATUS_SPEECH_START);
                    break;
                // 已经检测到语音终点，等待网络返回
                case VoiceRecognitionClient.CLIENT_STATUS_SPEECH_END:
                    mControlPanel.statusChange(ControlPanelFragment.STATUS_SPEECH_END);
                    break;
                // 语音识别完成，显示obj中的结果
                case VoiceRecognitionClient.CLIENT_STATUS_FINISH:
                    mControlPanel.statusChange(ControlPanelFragment.STATUS_FINISH);
                    isRecognition = false;
                    updateRecognitionResult(obj);
                    break;
                // 处理连续上屏
                case VoiceRecognitionClient.CLIENT_STATUS_UPDATE_RESULTS:
                    updateRecognitionResult(obj);
                    break;
                // 用户取消
                case VoiceRecognitionClient.CLIENT_STATUS_USER_CANCELED:
                    mControlPanel.statusChange(ControlPanelFragment.STATUS_FINISH);
                    isRecognition = false;
                    break;
                default:
                    break;
            }

        }

        @Override
        public void onError(int errorType, int errorCode) {
            isRecognition = false;
            mResult.setText(getString(R.string.error_occur, Integer.toHexString(errorCode)));
            mControlPanel.statusChange(ControlPanelFragment.STATUS_FINISH);
        }

        @Override
        public void onNetworkStatusChange(int status, Object obj) {
            // 这里不做任何操作不影响简单识别
        }
    }

    /**
     * 将识别结果更新到UI上，搜索模式结果类型为List<String>,输入模式结果类型为List<List<Candidate>>
     * 
     * @param result
     */
    private void updateRecognitionResult(Object result) {
        if (result != null && result instanceof List) {
            List results = (List) result;
            if (results.size() > 0) {
                if (results.get(0) instanceof List) {
                    List<List<Candidate>> sentences = (List<List<Candidate>>) result;
                    StringBuffer sb = new StringBuffer();
                    for (List<Candidate> candidates : sentences) {
                        if (candidates != null && candidates.size() > 0) {
                            sb.append(candidates.get(0).getWord());
                        }
                    }
                    //信息反馈///////////////
                   
                     if(vcm.Direct2Pic(sb.toString()) == directE)
                     {
                    	  mResult.setText(sb.toString()+"判断： "+judge.toJudge(sb.toString())+"正确");
//                    	  System.out.println("yes!!!!!!!!!");
                    	  result_one = vcm.VisualRowSimp(1);
                     }else
                     {
                    	 mResult.setText(sb.toString()+"判断： "+judge.toJudge(sb.toString())+"错误");
//                    	 System.out.println("no!!!!!!!!!");
                    	 result_one = vcm.VisualRowSimp(0);
                     }
                     //获取两次结果
                     if(result_one != 0)
                     {
                    	 if(times == 0)
                    	 {
                    		 this.result[times] = result_one;
                    		 System.out.println("result["+times+"]: "+this.result[times]);
                    		 Toast.makeText(getApplicationContext(), "左眼测试结束，请更换为右眼开始测试",
                    			     Toast.LENGTH_SHORT).show();
                    		 times += 1;
                        	 result_one = 0;
                    	 }else if(times == 1)
                    	 {
                    		 this.result[times] = result_one;
                    		 System.out.println("result["+times+"]: "+this.result[times]);
                    		 
                    		Intent result_show = new Intent();
         					result_show.putExtra("leftresult",vcm.chart(this.result[0]));
         					result_show.putExtra("rightresult",vcm.chart(this.result[1]));
         					result_show.setClass(BaiduVoiceTest.this,ResultActivity.class);
         					BaiduVoiceTest.this.startActivity(result_show);
         					
//                    		 times = 0;
//                        	 result_one = 0;
                    	 }                    	                  	 
                    	
                     }
                     //System.out.println("direvtE: "+directE);
                     
                     
                     
                     //下一步的方向和图片设置
                     directE = vcm.setImageE(vcm.direct());
                  //   imageView.setImageDrawable(getResources().getDrawable(directE));
                     
                     Bitmap bitMap = BitmapFactory.decodeResource(getResources(), directE);
             		int width = bitMap.getWidth();
             		int height = bitMap.getHeight();
             		
             		float scale = vcm.Picscale(vcm.getchartrow());
             		// 取得想要缩放的matrix参数
             		Matrix matrix = new Matrix();
             		matrix.postScale(scale,scale);
             		// 得到新的图片
             		 bitMap = Bitmap.createBitmap(bitMap, 0, 0, width, height, matrix, true);
             		 imageView.setImageBitmap(bitMap);
             		
                } else {
                    mResult.setText(results.get(0).toString());
                   
                    
                }
            }
        }
    }

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		finish();
	}
    
}

