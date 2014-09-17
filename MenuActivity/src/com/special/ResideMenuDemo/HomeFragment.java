package com.special.ResideMenuDemo;

import wangchao.voicemodule.BaiduVoiceTest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.special.ResideMenu.ResideMenu;

public class HomeFragment extends Fragment{

    
	private View parentView;
    private ResideMenu resideMenu;
    private ImageButton shiliceshi;
    private ImageButton gameMod;
    
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.home, container, false);
        setUpViews();
        return parentView;
    }
    
    @Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		shiliceshi = (ImageButton)getView().findViewById(R.id.shiliceshi);
		shiliceshi.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent_shiliceshi = new Intent();
				intent_shiliceshi.setClass(getActivity(),BaiduVoiceTest.class);
				HomeFragment.this.startActivity(intent_shiliceshi);
			}
		});
		
		gameMod = (ImageButton) getView().findViewById(R.id.aiyanyouxi);
		gameMod.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), com.gamemodule.MainActivity.class));
				
			}
			
		});
		
		
	}
    private void setUpViews() {
        MenuActivity parentActivity = (MenuActivity) getActivity();
        resideMenu = parentActivity.getResideMenu();
    }

}
