package com.mh.android.musicsearch.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.FrameLayout;

import com.mh.android.musicsearch.R;

/**
 * common Base activity class to provide basic action bar feature
 */
public class BaseActivity extends AppCompatActivity {
    protected Toolbar mToolbar;
    protected ActionBar mActionBar;
    private FrameLayout mMainContainer;
    private Resources mResources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Do not use setContentView() method call.

        setContentView(R.layout.base_activity_layout);
        mMainContainer = findViewById(R.id.main_container_layout);
        mResources = getResources();
        setupActionBar();

    }

    /**
     * Method to set navigation back button enable
     */
    protected void setToolBarBackEnable() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    /**
     * Method to set up action bar
     */
    private void setupActionBar() {
        mToolbar = findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            mActionBar = getSupportActionBar();
            if (mActionBar != null) {
                mActionBar.setTitle(mResources.getString(R.string.app_name));
            }
        }
    }

    /**
     * Method to enable home button
     * @param @boolean isEnabled
     */
    protected void setHomeButtonEnabled(boolean isEnabled) {
        if (isEnabled) {
            if (mActionBar != null) {
                mActionBar.setHomeButtonEnabled(true);
            }
        } else {
            if (mActionBar != null) {
                mActionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    /**
     * Method to add layout in to base container
     * @param layoutId
     */
    protected void addLayout(int layoutId) {
        mMainContainer.addView(getLayoutInflater().inflate(layoutId, null));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * Method to set navigation bar title
     * @param title
     */
    protected void setToolBarTitle(String title) {
        if (mActionBar != null && !TextUtils.isEmpty(title)) {
            mActionBar.setTitle(title);
        }
    }
}
