package com.tianma.popupwindowsample;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

public class MainActivity extends AppCompatActivity {

    private Button mMatchParentButton;
    private Button mWrapContentButton;

    private PopupWindow mMatchParentWindow;
    private PopupWindow mWrapContentWindow;

    private boolean matchParentWindowShowing = false;
    private boolean wrapContentWindowShowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        mMatchParentButton = (Button) findViewById(R.id.match_parent_button);
        mWrapContentButton = (Button) findViewById(R.id.wrap_content_button);

        mMatchParentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (matchParentWindowShowing) {
                    hideMatchParentPopupWindow();
                } else {
                    showMatchParentPopupWindow();
                }
                matchParentWindowShowing = !matchParentWindowShowing;
            }
        });
        mWrapContentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wrapContentWindowShowing) {
                    hideWrapContentPopupWindow();
                } else {
                    showWrapContentPopupWindow();
                }
                wrapContentWindowShowing = !wrapContentWindowShowing;
            }
        });
    }

    private void showMatchParentPopupWindow() {
        if (mMatchParentWindow == null) {
            initMatchParentPopupWindow();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { // Android 7.x中,PopupWindow高度为match_parent时,会出现兼容性问题,需要处理兼容性
            int[] location = new int[2];
            mMatchParentButton.getLocationOnScreen(location);
            int y = location[1] + mMatchParentButton.getHeight();
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) { // Android 7.1中,PopupWindow高度为match_parent时,会占据所有屏幕
                int screenHeight = ScreenUtils.getScreenHeight(this);
                mMatchParentWindow.setHeight(screenHeight - y);
            }
            mMatchParentWindow.showAtLocation(mMatchParentButton, Gravity.NO_GRAVITY, 0, y);
        } else {
            mMatchParentWindow.showAsDropDown(mMatchParentButton);
        }
        mMatchParentButton.setText(R.string.hide_match_parent_popup_window);
        mWrapContentButton.setEnabled(false);
    }

    private void hideMatchParentPopupWindow() {
        if (mMatchParentWindow != null) {
            mMatchParentWindow.dismiss();
        }
        mMatchParentButton.setText(R.string.show_match_parent_popup_window);
        mWrapContentButton.setEnabled(true);
    }

    private void initMatchParentPopupWindow() {
        View popupRootView = LayoutInflater.from(this).inflate(R.layout.popup_window, null);
        mMatchParentWindow = new PopupWindow(this);
        mMatchParentWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mMatchParentWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        mMatchParentWindow.setContentView(popupRootView);
    }

    private void showWrapContentPopupWindow() {
        if (mWrapContentWindow == null) {
            initWrapContentPopupWindow();
        }
        mWrapContentWindow.showAsDropDown(mWrapContentButton);
        mWrapContentButton.setText(R.string.hide_wrap_content_popup_window);
        mMatchParentButton.setEnabled(false);
    }

    private void hideWrapContentPopupWindow() {
        if (mWrapContentWindow != null) {
            mWrapContentWindow.dismiss();
        }
        mWrapContentButton.setText(R.string.show_wrap_content_popup_window);
        mMatchParentButton.setEnabled(true);
    }

    private void initWrapContentPopupWindow() {
        View popupRootView = LayoutInflater.from(this).inflate(R.layout.popup_window, null);
        mWrapContentWindow = new PopupWindow(this);
        mWrapContentWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mWrapContentWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mWrapContentWindow.setContentView(popupRootView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMatchParentWindow != null) {
            mMatchParentWindow.dismiss();
        }
        if (mMatchParentWindow != null) {
            mMatchParentWindow.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        if (matchParentWindowShowing || wrapContentWindowShowing) {
            if (matchParentWindowShowing) {
                hideMatchParentPopupWindow();
                matchParentWindowShowing = !matchParentWindowShowing;
            }
            if (wrapContentWindowShowing) {
                hideWrapContentPopupWindow();
                wrapContentWindowShowing = !wrapContentWindowShowing;
            }
        } else {
            super.onBackPressed();
        }
    }
}
