package com.example.fa19_bcs_010_asg3_frag_and_recycler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private FragmentManager _fragMan;
    private FragmentTransaction _fragTrans;
    private VideoFragment _videoFragment;
    private ListFragment _listFragment;
    private SharedViewModel _sharedViewMoel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _sharedViewMoel = new ViewModelProvider(this).get(SharedViewModel.class);
        _fragMan = getSupportFragmentManager();
        _fragTrans = _fragMan.beginTransaction();
        _listFragment = _listFragment.newInstance();
        _videoFragment = _videoFragment.newInstance();
        _fragTrans.replace(R.id.Frag_List, _listFragment);
        _fragTrans.replace(R.id.Frag_Video, _videoFragment);
        _fragTrans.commitNow();
    }
}