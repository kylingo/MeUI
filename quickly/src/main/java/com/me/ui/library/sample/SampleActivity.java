package com.me.ui.library.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.me.ui.quickly.R;

import java.util.List;

public abstract class SampleActivity extends AppCompatActivity {

    protected abstract String getSampleTitle();

    protected abstract Fragment getSampleFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        addSampleFragment();
    }

    protected void addSampleFragment() {
        Bundle bundle = new Bundle();
        bundle.putString(AbstractSampleFragment.KEY_TITLE, getSampleTitle());

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (int i = 0; i < fragments.size(); i++) {
                fragmentManager.popBackStack();
            }
        }

        Fragment sampleFragment = getSampleFragment();
        sampleFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fl_sample, sampleFragment, sampleFragment.getClass().getSimpleName());
        fragmentTransaction.commit();
    }
}
