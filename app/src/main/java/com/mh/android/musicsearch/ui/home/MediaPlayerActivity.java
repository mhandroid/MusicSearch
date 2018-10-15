package com.mh.android.musicsearch.ui.home;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.mh.android.musicsearch.R;
import com.mh.android.musicsearch.model.AudioPlayer;
import com.mh.android.musicsearch.ui.BaseActivity;
import com.mh.android.musicsearch.ui.viewmodel.MediaPlayerViewModel;
import com.squareup.picasso.Picasso;

/**
 * To display music track details and play the preview track
 */
public class MediaPlayerActivity extends BaseActivity implements View.OnClickListener {

    private Button btnPlay;

    private MediaPlayerViewModel mediaPlayerViewModel;
    private String url;
    private boolean btnState = false;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addLayout(R.layout.media_player_activity);

        setToolBarBackEnable();
        setToolBarTitle(getString(R.string.player));

        String imageUrl = null;
        Intent intent = getIntent();
        if (intent.hasExtra(HomeFragment.TRACK_URL)) {
            url = intent.getStringExtra(HomeFragment.TRACK_URL);
            imageUrl = intent.getStringExtra(HomeFragment.IMAGE_URL);
        }


        btnPlay = findViewById(R.id.button_play);
        //progressBar = findViewById(R.id.audio_seekbar);

        btnPlay.setOnClickListener(this);

        //initializeSeekbar();
        mediaPlayerViewModel = ViewModelProviders.of(this).get(MediaPlayerViewModel.class);

        ImageView imageView = findViewById(R.id.imag_preview);
        if (imageUrl != null) {
            Picasso.with(this).load(imageUrl).placeholder(R.mipmap.ic_launcher).into(imageView);
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_play:
                if (mediaPlayerViewModel.startOnlyFirstTime) {
                    mediaPlayerViewModel.play(url).observe(this, new Observer<AudioPlayer>() {
                        @Override
                        public void onChanged(@Nullable AudioPlayer audioPlayer) {
                            if (audioPlayer != null) {
                                if (audioPlayer.status == AudioPlayer.AudioStatus.PLAYING) {
                                    //Do something here
                                    //progressBar.setProgress(audioPlayer.position);
                                } else if (audioPlayer.status == AudioPlayer.AudioStatus.COMPLETED) {
                                    //toggleButton();

                                } else if (audioPlayer.status == AudioPlayer.AudioStatus.PREPARED) {
                                    //progressBar.setMax(audioPlayer.position);

                                }
                            }
                        }
                    });
                } else {
                    if (mediaPlayerViewModel.isPlaying() && btnState) {
                        mediaPlayerViewModel.stop();
                    } else {
                        mediaPlayerViewModel.start();
                    }
                }
                toggleButton();
                break;
        }
    }

    /**
     * method to toggle play button
     */
    private void toggleButton() {
        btnState = !btnState;
        if (btnState) {
            btnPlay.setText(R.string.stop);
        } else {
            btnPlay.setText(R.string.play);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayerViewModel.release();
    }
}
