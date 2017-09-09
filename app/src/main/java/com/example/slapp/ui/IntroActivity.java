package com.example.slapp.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.slapp.R;
import com.example.slapp.util.BasePageTransformer;
import com.example.slapp.util.ColorTransformer;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IntroActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.vp_intro)
    ViewPager mVpIntro;
    @BindView(R.id.tab_dots)
    TabLayout mTabDots;
    @BindView(R.id.btnGetStarted)
    Button mBtnGetStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ButterKnife.bind(this);



        mVpIntro.setPageTransformer(false, PAGE_TRANSFORMER);
        mVpIntro.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        mTabDots.setupWithViewPager(mVpIntro, true);
        mVpIntro.setPageTransformer(false, new ColorTransformer());
        mVpIntro.setOnPageChangeListener(this);
    }

    private static final BasePageTransformer PAGE_TRANSFORMER = new BasePageTransformer() {
        @Override
        protected void transformPage(View page, int pageIndex, float position) {
            new ColorTransformer();
        }
    };

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 3) {
            mBtnGetStarted.setVisibility(View.VISIBLE);
            mBtnGetStarted.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        } else {
            mBtnGetStarted.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public enum Content {
        ONE("1", Color.CYAN),
        TWO("2", Color.MAGENTA),
        THREE("3", Color.YELLOW),
        FOUR("4", Color.BLUE);

        private final String mText;
        private final int mColor;

        Content(final String text, final int color) {
            mText = text;
            mColor = color;
        }

        public String getText() {
            return mText;
        }

        public int getColor() {
            return mColor;
        }
    }

    private static class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(final FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(final int position) {
            return IntroFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return Content.values().length;
        }
    }
}
