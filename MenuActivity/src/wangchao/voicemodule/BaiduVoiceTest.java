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

    /** ����ʶ���� */
    private boolean isRecognition = false;

    /** �������¼�� */
    private static final int POWER_UPDATE_INTERVAL = 100;

    /** ʶ��ص��ӿ� */
    private MyVoiceRecogListener mListener = new MyVoiceRecogListener();

    /** ���߳�Handler */
    private Handler mHandler;

    /**
     * ���չʾ
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
	int direct = 0;//��ʼĬ�Ϸ���
	int chart_row = 1; //��ʼ����������
	int chart_counter = 0;//��ͬһ���ж���ȷ����
	int chart_counterf = 0; //��ͬһ���жϴ������
	int times = 0;//���Դ�����0Ϊ���ۣ�1Ϊ���ۣ��˺����
	int result[] =new int[2]; //������������Ӧ������
	int directE = vcm.setImageE(direct) ;
	int result_one = 0;
	    
    /**
     * ������������
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
		testtitle.setText("��ʼ���ԣ�");
		
		Toast.makeText(getApplicationContext(), "�뽫�ֻ�������ǰ50-70cm�����Ƚ������۲���",
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
                config.enableContacts(); // ����ͨѶ¼
                config.enableVoicePower(Config.SHOW_VOL); // ����������
                if (Config.PLAY_START_SOUND) {
                    config.enableBeginSoundEffect(R.raw.bdspeech_recognition_start); // ����ʶ��ʼ��ʾ��
                }
                if (Config.PLAY_END_SOUND) {
                    config.enableEndSoundEffect(R.raw.bdspeech_speech_end); // ����ʶ�������ʾ��
                }
                config.setSampleRate(VoiceRecognitionConfig.SAMPLE_RATE_8K); // ���ò�����,��Ҫ���ⲿ��Ƶһ��
                // ���淢��ʶ��
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
     * ��д���ڴ�������ʶ��ص��ļ�����
     */
    class MyVoiceRecogListener implements VoiceClientStatusChangeListener {

        @Override
        public void onClientStatusChange(int status, Object obj) {
            switch (status) {
            // ����ʶ��ʵ�ʿ�ʼ������������ʼʶ���ʱ��㣬���ڽ�����ʾ�û�˵����
                case VoiceRecognitionClient.CLIENT_STATUS_START_RECORDING:
                    isRecognition = true;
                    mHandler.removeCallbacks(mUpdateVolume);
                    mHandler.postDelayed(mUpdateVolume, POWER_UPDATE_INTERVAL);
                    mControlPanel.statusChange(ControlPanelFragment.STATUS_RECORDING_START);
                    break;
                case VoiceRecognitionClient.CLIENT_STATUS_SPEECH_START: // ��⵽�������
                    mControlPanel.statusChange(ControlPanelFragment.STATUS_SPEECH_START);
                    break;
                // �Ѿ���⵽�����յ㣬�ȴ����緵��
                case VoiceRecognitionClient.CLIENT_STATUS_SPEECH_END:
                    mControlPanel.statusChange(ControlPanelFragment.STATUS_SPEECH_END);
                    break;
                // ����ʶ����ɣ���ʾobj�еĽ��
                case VoiceRecognitionClient.CLIENT_STATUS_FINISH:
                    mControlPanel.statusChange(ControlPanelFragment.STATUS_FINISH);
                    isRecognition = false;
                    updateRecognitionResult(obj);
                    break;
                // ������������
                case VoiceRecognitionClient.CLIENT_STATUS_UPDATE_RESULTS:
                    updateRecognitionResult(obj);
                    break;
                // �û�ȡ��
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
            // ���ﲻ���κβ�����Ӱ���ʶ��
        }
    }

    /**
     * ��ʶ�������µ�UI�ϣ�����ģʽ�������ΪList<String>,����ģʽ�������ΪList<List<Candidate>>
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
                    //��Ϣ����///////////////
                   
                     if(vcm.Direct2Pic(sb.toString()) == directE)
                     {
                    	  mResult.setText(sb.toString()+"�жϣ� "+judge.toJudge(sb.toString())+"��ȷ");
//                    	  System.out.println("yes!!!!!!!!!");
                    	  result_one = vcm.VisualRowSimp(1);
                     }else
                     {
                    	 mResult.setText(sb.toString()+"�жϣ� "+judge.toJudge(sb.toString())+"����");
//                    	 System.out.println("no!!!!!!!!!");
                    	 result_one = vcm.VisualRowSimp(0);
                     }
                     //��ȡ���ν��
                     if(result_one != 0)
                     {
                    	 if(times == 0)
                    	 {
                    		 this.result[times] = result_one;
                    		 System.out.println("result["+times+"]: "+this.result[times]);
                    		 Toast.makeText(getApplicationContext(), "���۲��Խ����������Ϊ���ۿ�ʼ����",
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
                     
                     
                     
                     //��һ���ķ����ͼƬ����
                     directE = vcm.setImageE(vcm.direct());
                  //   imageView.setImageDrawable(getResources().getDrawable(directE));
                     
                     Bitmap bitMap = BitmapFactory.decodeResource(getResources(), directE);
             		int width = bitMap.getWidth();
             		int height = bitMap.getHeight();
             		
             		float scale = vcm.Picscale(vcm.getchartrow());
             		// ȡ����Ҫ���ŵ�matrix����
             		Matrix matrix = new Matrix();
             		matrix.postScale(scale,scale);
             		// �õ��µ�ͼƬ
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

