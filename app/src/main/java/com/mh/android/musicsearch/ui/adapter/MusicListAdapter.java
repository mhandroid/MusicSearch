package com.mh.android.musicsearch.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mh.android.musicsearch.R;
import com.mh.android.musicsearch.model.Track;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Adapter to display list of music items
 */
public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.MusicViewHolder> {
    private Context mContext;
    private List<Track> mList;
    private ListItemClickListener listItemClickListener;

    public MusicListAdapter(Context context, List<Track> list, ListItemClickListener listItemClickListener) {
        mContext = context;
        mList = list;
        this.listItemClickListener = listItemClickListener;
    }


    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_layout, viewGroup, false);
        return new MusicViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicViewHolder musicViewHolder, final int i) {

        Track track = mList.get(i);
        if (track != null) {
            musicViewHolder.txtTitle.setText(track.getTrackName());
            musicViewHolder.txtDesc.setText(track.getArtistName());
            Picasso.with(mContext).load(track.getArtworkUrl60()).placeholder(R.mipmap.ic_launcher).into(musicViewHolder.img);
        }
        musicViewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listItemClickListener != null) {
                    listItemClickListener.onItemClick(mList.get(i));
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * To hold the view of list item
     */
    class MusicViewHolder extends RecyclerView.ViewHolder {

        final TextView txtTitle;
        final TextView txtDesc;
        final ImageView img;
        final View view;

        MusicViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtDesc = itemView.findViewById(R.id.txt_desc);
            img = itemView.findViewById(R.id.imageView);
        }
    }

    public interface ListItemClickListener {
        void onItemClick(Track track);
    }
}
