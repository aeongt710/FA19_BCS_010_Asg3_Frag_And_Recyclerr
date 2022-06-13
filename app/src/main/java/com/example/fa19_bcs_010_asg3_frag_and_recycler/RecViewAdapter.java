package com.example.fa19_bcs_010_asg3_frag_and_recycler;

import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class RecViewAdapter extends RecyclerView.Adapter<RecViewAdapter.RecViewViewHoder> {
    private Field[] _fields;

    public RecViewAdapter(Field[] names) {
        this._fields = names;
    }

    @NonNull
    @Override
    public RecViewViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Creating the layout for the single item of recyclerview
        LinearLayout itemLayout =(LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        RecViewViewHoder recViewViewHoder = new RecViewViewHoder(itemLayout);
        return recViewViewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecViewViewHoder holder, int position) {

        holder._videoText.setText(_fields[position].getName());

        try {
            int id=_fields[position].getInt(null);
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            AssetFileDescriptor afd=holder.itemView.getResources().openRawResourceFd(id);
            retriever.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());

            long duration = Long.parseLong(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
            Bitmap bitmap = retriever.getFrameAtTime(ThreadLocalRandom.current().nextLong(1, duration + 1)*1000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
            String height = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
            String width = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);

            holder._videoThumbnail.setImageBitmap(bitmap);
            holder._videoDuration.setText(getTime(duration));
            holder._videoResolution.setText(height+"x"+width);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    private String getTime(long millisecond) {

        long min = TimeUnit.MILLISECONDS.toMinutes(millisecond);
        long sec = TimeUnit.MILLISECONDS.toSeconds(millisecond) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisecond));
        String time = min + ":" + sec;
        return time;
    }


    @Override
    public int getItemCount() {
        return _fields.length;
    }

    public class RecViewViewHoder extends RecyclerView.ViewHolder {

        public TextView _videoText;
        public SharedViewModel _sharedViewModel;
        public TextView _videoDuration;
        public ImageView _videoThumbnail;
        public TextView _videoResolution;

        public RecViewViewHoder(@NonNull View itemView)  {
            super(itemView);
            _sharedViewModel = new ViewModelProvider((ViewModelStoreOwner) itemView.getContext()).get(SharedViewModel.class);
            _videoText = itemView.findViewById(R.id.video_text);
            _videoDuration = itemView.findViewById(R.id.video_duration);
            _videoThumbnail = itemView.findViewById(R.id.video_thumbnail);
            _videoResolution = itemView.findViewById(R.id.video_resolution);

            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    _sharedViewModel._field.setValue(_fields[getAdapterPosition()]);
                }
            };
            _videoText.setOnClickListener(listener);
            _videoDuration.setOnClickListener(listener);
            _videoThumbnail.setOnClickListener(listener);
            _videoResolution.setOnClickListener(listener);
        }
    }
}