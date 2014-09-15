package com.special.ResideMenuDemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

public class CalendarFragment extends Fragment {

    private View parentView;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.calendar, container, false);
        listView   = (ListView) parentView.findViewById(R.id.listView);
        initView();
        return parentView;
    }

    private void initView(){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                getCalendarData());
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "Clicked item!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private ArrayList<String> getCalendarData(){
        ArrayList<String> calendarList = new ArrayList<String>();
        calendarList.add("爱眼资讯1");
        calendarList.add("爱眼资讯2");
        calendarList.add("爱眼资讯3");
        calendarList.add("爱眼资讯4");
        calendarList.add("爱眼资讯5");
        calendarList.add("爱眼资讯6");
        calendarList.add("爱眼资讯7");
        calendarList.add("爱眼资讯8");
        calendarList.add("爱眼资讯9");
        calendarList.add("爱眼资讯10");
        return calendarList;
    }
}
