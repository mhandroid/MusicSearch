package com.mh.android.musicsearch.ui;

import android.os.Bundle;

import com.mh.android.musicsearch.R;
import com.mh.android.musicsearch.ui.BaseActivity;
import com.mh.android.musicsearch.ui.home.HomeFragment;

/**
 * Activity to display list of music
 */
public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addLayout(R.layout.home_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, HomeFragment.newInstance())
                    .commitNow();
        }

        setHomeButtonEnabled(true);
    }
}
