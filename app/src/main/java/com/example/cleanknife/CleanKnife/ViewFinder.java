package com.example.cleanknife.CleanKnife;

import android.app.Activity;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * FindViewByID
 */
public class ViewFinder {
    private Activity mActivity;
    private AppCompatActivity mAppCompatActivity;
    private View mView;

    public ViewFinder(Activity activity) {
        this.mActivity = activity;
    }

    public ViewFinder(AppCompatActivity appCompatActivity) {
        this.mAppCompatActivity = appCompatActivity;
    }

    public ViewFinder(View view) {
        this.mView = view;
    }

    public View findViewById(int viewId) {
        View view = null;
        if (mActivity != null) view = mActivity.findViewById(viewId);
        if (mAppCompatActivity != null) view = mAppCompatActivity.findViewById(viewId);
        if (mView != null) mView.findViewById(viewId);
        return view;
    }
}
