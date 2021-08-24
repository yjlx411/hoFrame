package com.krad.origin.hoframe.view.widget.tabView;

import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

/**
 * 作者：yaochangliang on 2016/8/13 18:37
 * 邮箱：yaochangliang159@sina.com
 */
public class TabViewChild {
    private String imageViewSelIcon;
    private String imageViewUnSelIcon;

    private int resSelIcon;
    private int resUnSelIcon;

    private String textViewText;
    private Fragment mFragment;
    private boolean isNeedReadCircle;
    private boolean isNumber;
    private TextView unreadCountTextView;
    private boolean showNum;

    public void setShowNum(boolean showNum) {
        this.showNum = showNum;
    }

    private int count;

    public int getCount() {
        return count;
    }

    public void setUnReadLost() {
        if (unreadCountTextView != null) {
            unreadCountTextView.setVisibility(View.GONE);
        }
    }

    public void setUnReadShow(Integer count) {
        if (count == null) {
            if (unreadCountTextView != null) {
                unreadCountTextView.setVisibility(View.VISIBLE);
            }
        } else {
            if (unreadCountTextView != null) {
                if (count == 0) {
                    unreadCountTextView.setVisibility(View.GONE);
                } else {
                    unreadCountTextView.setVisibility(View.VISIBLE);
                    if (showNum) {
                        unreadCountTextView.setText(count + "");
                    } else {
                        unreadCountTextView.setText("");
                    }
                }
            }
            this.count = count;
        }
    }

    public void setUnreadCountTextView(TextView unreadCountTextView) {
        this.unreadCountTextView = unreadCountTextView;
    }

    public boolean isNeedReadCircle() {
        return isNeedReadCircle;
    }

    public void setNeedReadCircle(boolean needReadCircle) {
        isNeedReadCircle = needReadCircle;
    }

    public boolean isNumber() {
        return isNumber;
    }

    public void setNumber(boolean number) {
        isNumber = number;
    }

    private TabViewChild() {

    }

    public TabViewChild(int resSelIcon, int resUnSelIcon, String imageViewSelIcon, String imageViewUnSelIcon, String textViewText, Fragment mFragment) {
        this.resSelIcon = resSelIcon;
        this.resUnSelIcon = resUnSelIcon;
        this.imageViewSelIcon = imageViewSelIcon;
        this.imageViewUnSelIcon = imageViewUnSelIcon;
        this.textViewText = textViewText;
        this.mFragment = mFragment;
    }


    public TabViewChild(int resSelIcon, int resUnSelIcon, String textViewText, Fragment mFragment) {
        this.resSelIcon = resSelIcon;
        this.resUnSelIcon = resUnSelIcon;
        this.textViewText = textViewText;
        this.mFragment = mFragment;
    }


    public TabViewChild(String imageViewSelIcon, String imageViewUnSelIcon, String textViewText, Fragment mFragment) {
        this.imageViewSelIcon = imageViewSelIcon;
        this.imageViewUnSelIcon = imageViewUnSelIcon;
        this.textViewText = textViewText;
        this.mFragment = mFragment;
    }


    public String getImageViewSelIcon() {
        return imageViewSelIcon;
    }


    public void setImageViewSelIcon(String imageViewSelIcon) {
        this.imageViewSelIcon = imageViewSelIcon;
    }


    public String getImageViewUnSelIcon() {
        return imageViewUnSelIcon;
    }


    public void setImageViewUnSelIcon(String imageViewUnSelIcon) {
        this.imageViewUnSelIcon = imageViewUnSelIcon;
    }


    public String getTextViewText() {
        return textViewText;
    }


    public void setTextViewText(String textViewText) {
        this.textViewText = textViewText;
    }


    public Fragment getmFragment() {
        return mFragment;
    }

    public void setmFragment(Fragment mFragment) {
        this.mFragment = mFragment;
    }

    public int getResSelIcon() {
        return resSelIcon;
    }

    public void setResSelIcon(int resSelIcon) {
        this.resSelIcon = resSelIcon;
    }

    public int getResUnSelIcon() {
        return resUnSelIcon;
    }

    public void setResUnSelIcon(int resUnSelIcon) {
        this.resUnSelIcon = resUnSelIcon;
    }
}
