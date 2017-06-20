package com.me.ui.library.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.me.ui.R;

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

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSampleFragment();
        fragment.setArguments(bundle);
        fragmentTransaction.add(R.id.fl_sample, fragment, fragment.getClass().getSimpleName());
        fragmentTransaction.commit();
    }
}
