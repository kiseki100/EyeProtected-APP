package com.gamemodule;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.gamemodule.tacoyaki.Game;
import com.gamemodule.tacoyaki.GameConfig;
import com.gamemodule.tacoyaki.GameView;

public class TacoyakiActivity extends Activity { 

	private static final String TAG = "tacoyaki Activity";

	private GameView mGameView;
	private Game mGame;
	private int gameType; // tacoyaki OR tacoyaki plus
	private BootstrapButton tacoyaki1;
	private BootstrapButton tacoyaki2;
	
	// time counter
	private Chronometer timeCount;
	
	// store records
	private SharedPreferences sp;
	private TextView record;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tacoyaki);
		
		// sharedpreferences instantiated
		sp = getSharedPreferences("tacoyaki_record", MODE_PRIVATE);
		initView();
		gameType = GameConfig.TA_DEFAULT;
		initGame(gameType, GameConfig.NORMAL_MOD);
		// time counter
		timeCount = (Chronometer) findViewById(R.id.chronometer);
	}

	private void initView() {
		mGameView = (GameView) findViewById(R.id.ta_view);
		tacoyaki1 = (BootstrapButton) findViewById(R.id.tacoyakiBtn1);
		tacoyaki1.setBootstrapButtonEnabled(false);
		tacoyaki2 = (BootstrapButton) findViewById(R.id.tacoyakiBtn2);
		// buttn clicked event
		tacoyaki1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				gameType = mGame.getGameType();
				if(gameType != GameConfig.TA_DEFAULT){
					// change these two button style
					tacoyaki1.setBootstrapButtonEnabled(false);
					tacoyaki2.setBootstrapButtonEnabled(true);
					// switch game in the game view layer, and game view will call game layer to change result detection
					mGameView.switchGame(GameConfig.TA_DEFAULT);
					// new game type. DO NOT forget update it
					gameType = mGame.getGameType();
					
					timeCount.stop();
					// reset time
    				timeCount.setBase(SystemClock.elapsedRealtime());
    				// display curr game record
    				String tmp = sp.getString("best"+gameType+mGame.getGameMod(),"");
    				if(tmp != "") record.setText(tmp+"s");
    				else record.setText("");
				}// if
			}
		});
		
		// same as tacoyaki1
		tacoyaki2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				gameType = mGame.getGameType();
				if(gameType != GameConfig.TA_PLUS){
					tacoyaki2.setBootstrapButtonEnabled(false);
					tacoyaki1.setBootstrapButtonEnabled(true);
					mGameView.switchGame(GameConfig.TA_PLUS);
					gameType = mGame.getGameType();
					timeCount.stop();
    				timeCount.setBase(SystemClock.elapsedRealtime());
    				String tmp = sp.getString("best"+gameType+mGame.getGameMod(),"");
    				Log.d(TAG, "temp: "+tmp);
    				if(tmp != "") record.setText(tmp+"s");
    				else record.setText("");
				}// if
			}// func onclick
		});
		 
		record = (TextView) findViewById(R.id.besttime);
		
		// set font style
		TextView dis_best = (TextView) findViewById(R.id.dis_besttime);
		TextView dis_time = (TextView) findViewById(R.id.dis_timecount);
		Typeface typeFace = Typeface.createFromAsset(getAssets(),"jockerman.ttf");
		dis_best.setTypeface(typeFace);
		dis_time.setTypeface(typeFace);
		
		//display record
		String tmp = sp.getString("best"+GameConfig.TA_DEFAULT+GameConfig.NORMAL_MOD,"");
		if(tmp != "") record.setText(tmp+"s");
	}

	private void initGame(int type, int mod) {
		mGame = new Game(mRefreshHandler, type, mod);
		if(mGame == null){
			Log.d(TAG, "mgame is null");
		}
		mGameView.setGame(mGame);
	}
	
	private Handler mRefreshHandler = new Handler(){
    	public void handleMessage(Message msg) {
    		switch (msg.what) {
    		case GameConfig.PASS:
    			int mod = mGame.getGameMod();
    			// time counter stop
    			timeCount.stop();
    			// compare curr and best
    			int currTime = (int) (SystemClock.elapsedRealtime() - timeCount.getBase()) / 1000;
    			String tmp = sp.getString("best"+gameType+mod,"");
    			// result of first play is also the first record
    			if(tmp == "") sp.edit().putString("best"+gameType+mod,String.valueOf(currTime)).commit();
    			else if(Integer.parseInt(tmp) > currTime){// new record!
    				// TODO new record dialog
    				sp.edit().putString("best"+gameType+mod,String.valueOf(currTime)).commit();
    			}
    			
    			if(mod == GameConfig.HARD_MOD){
    				showWinDialog();
    			}else{
    				mGameView.levelUP();
    				timeCount.setBase(SystemClock.elapsedRealtime());
    				String tmp1 = sp.getString("best"+gameType+mGame.getGameMod(),"");
    				if(tmp1 != "") 
    					record.setText(tmp1+"s") ;
    			}
    			break;
    		case GameConfig.STARTTIMER:
    			timeCount.setBase(SystemClock.elapsedRealtime());
    			timeCount.start();
    			break;
    		default:
    			break;
    		}
    	};
    };
    
    private void showWinDialog(){
    	// TODO 
    	// need to add some features 
    	AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setCancelable(false);
        b.setMessage("Congratulations!");
        b.setPositiveButton("Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            	gameType = mGame.getGameType();
				if(gameType == GameConfig.TA_PLUS){
					tacoyaki1.setBootstrapButtonEnabled(false);
					tacoyaki2.setBootstrapButtonEnabled(true);
					mGameView.switchGame(GameConfig.TA_DEFAULT);
				} else {
					tacoyaki1.setBootstrapButtonEnabled(true);
					tacoyaki2.setBootstrapButtonEnabled(false);
					mGameView.switchGame(GameConfig.TA_PLUS);
				}
            	
            }
        });
        
        b.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        b.show();
    }
}
