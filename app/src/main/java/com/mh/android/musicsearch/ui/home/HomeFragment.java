package com.mh.android.musicsearch.ui.home;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mh.android.musicsearch.R;
import com.mh.android.musicsearch.model.Music;
import com.mh.android.musicsearch.model.Resource;
import com.mh.android.musicsearch.model.Track;
import com.mh.android.musicsearch.ui.adapter.MusicListAdapter;
import com.mh.android.musicsearch.ui.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment to display list of music
 */
public class HomeFragment extends Fragment implements MusicListAdapter.ListItemClickListener, SearchView.OnQueryTextListener, View.OnClickListener {

    private HomeViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private static final String TAG = HomeFragment.class.getSimpleName();
    public static final String TRACK_URL = "track_url";
    public static final String IMAGE_URL = "IMAGE_URL";
    private SearchView searchView;
    private ProgressBar progressBar;
    private TextView errorText;
    private MusicListAdapter musicListAdapter;
    private List<Track> mList = new ArrayList<>();
    private View errorView;
    private Button retryBtn;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        mRecyclerView = view.findViewById(R.id.list_music);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false
        );
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        searchView = view.findViewById(R.id.search);
        searchView.setOnQueryTextListener(this);
        progressBar = view.findViewById(R.id.progressBar);
        errorText = view.findViewById(R.id.txtError);
        musicListAdapter = new MusicListAdapter(getContext(), mList, HomeFragment.this);
        mRecyclerView.setAdapter(musicListAdapter);
        errorView = view.findViewById(R.id.error_view);
        retryBtn = view.findViewById(R.id.btn_retry);
        retryBtn.setOnClickListener(this);
        searchView.onActionViewExpanded();
        searchView.setActivated(true);
        showLoading(true);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        loadMusic();
    }

    /**
     * To load and make backend request
     */
    private void loadMusic() {
        mViewModel.getMusicList().observe(this, new Observer<Resource<Music>>() {
            @Override
            public void onChanged(@Nullable Resource<Music> apiResponseResource) {

                if (apiResponseResource.status == Resource.Status.SUCCESS) {

                    if (apiResponseResource.data != null && apiResponseResource.data.getResults() != null) {
                        Log.d(TAG, apiResponseResource.data.getResultCount() + "");
                        mList.clear();
                        mList.addAll(apiResponseResource.data.getResults());
                        musicListAdapter.notifyDataSetChanged();
                        showLoading(false);
                    }
                } else if (apiResponseResource.status == Resource.Status.NO_INTERNET) {
                    showError(apiResponseResource.message);
                } else {
                    showError(getString(R.string.try_aggain));
                }
            }
        });
    }

    /**
     * Method to show error view
     * @param msg
     */
    private void showError(String msg) {
        errorView.setVisibility(View.VISIBLE);
        errorText.setText(msg);
        progressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);

    }

    @Override
    public void onItemClick(Track track) {
        if (TextUtils.isEmpty(track.getPreviewUrl())) {
            Toast.makeText(getActivity(), "Preview url is not available", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(getActivity(), MediaPlayerActivity.class);
        intent.putExtra(TRACK_URL, track.getPreviewUrl());
        intent.putExtra(IMAGE_URL, track.getArtworkUrl100());
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        Log.d(TAG, s);
        if (!TextUtils.isEmpty(s)) {
            mViewModel.getMusicList(s.replace(" ", "+")).observe(this, new Observer<Resource<Music>>() {
                @Override
                public void onChanged(@Nullable Resource<Music> musicResource) {
                    if (musicResource != null && musicResource.data != null) {
                        if (musicResource.status == Resource.Status.SUCCESS) {
                            Log.d(TAG, musicResource.data.getResultCount() + "");
                            mList.clear();
                            mList.addAll(musicResource.data.getResults());
                            musicListAdapter.notifyDataSetChanged();
                            showLoading(false);
                        } else if (musicResource.status == Resource.Status.NO_INTERNET) {
                            showError(musicResource.message);
                        } else {
                            showError(getString(R.string.try_aggain));
                        }
                    }
                }
            });
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        showLoading(true);
        return false;
    }

    /**
     * Method to show and hide loading screen
     * @param state
     */
    private void showLoading(boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
            errorView.setVisibility(View.GONE);

        } else {
            progressBar.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            errorView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_retry:
                loadMusic();

                showLoading(true);
                break;
        }
    }
}
