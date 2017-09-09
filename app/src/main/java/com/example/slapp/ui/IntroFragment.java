package com.example.slapp.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.slapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class IntroFragment extends Fragment {


    private static final String ARG_POSITION = "com.example.IntroFragment.ARG_POSITION";
    @BindView(R.id.contentTextView)
    TextView mContentTextView;
    Unbinder unbinder;

    public IntroFragment() {
        // Required empty public constructor
    }

    public static IntroFragment newInstance(final int position) {
        final Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        IntroFragment fragment = new IntroFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_intro, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final int position = getArguments().getInt(ARG_POSITION);
        final String text = IntroActivity.Content.values()[position].getText();

        view.setTag(position);

        mContentTextView.setText(text);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
