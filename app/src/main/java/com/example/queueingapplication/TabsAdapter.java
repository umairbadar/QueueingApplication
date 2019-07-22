package com.example.queueingapplication;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


public class TabsAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    public TabsAdapter(FragmentManager fm, int NoofTabs){
        super(fm);
        this.mNumOfTabs = NoofTabs;
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:
                Fragment_Queueing fragment_queueing = new Fragment_Queueing();
                return fragment_queueing;
            case 1:
                Fragment_Control_Room fragment_control_room = new Fragment_Control_Room();
                return fragment_control_room;
            default:
                return null;
        }
    }
}