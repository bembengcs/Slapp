package com.example.slapp.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.slapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoContainerActivity extends AppCompatActivity {

    @BindView(R.id.fragment_container)
    FrameLayout mFragmentContainer;

    private String videoUrl;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_container);
        ButterKnife.bind(this);

        videoUrl = getIntent().getStringExtra("link");

        if (savedInstanceState == null) {
            VideoFragment videoFragment = new VideoFragment();
            FragmentManager fm = getSupportFragmentManager();
            Bundle args = new Bundle();
            args.putString("videoUrl", videoUrl);
            videoFragment.setArguments(args);
            fm.beginTransaction()
                    .add(R.id.fragment_container, videoFragment)
                    .commit();
        }
    }

}
