package com.example.fa19_bcs_010_asg3_frag_and_recycler;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class SharedViewModel extends ViewModel {
    public MutableLiveData<Field> _field;

    public SharedViewModel()
    {
        _field = new MutableLiveData<>();
    }

}
