package com.krad.origin.hoframe.view.widget;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class X5WebView extends WebView {
    private static final String TAG = "x5webview";
    int progressColor = 0xFFFF4081;
    ProgressView mProgressView;
    TextView title;
    Context context;

    private ValueCallback<Uri[]> mUploadMessage;


    public ValueCallback<Uri[]> getmUploadMessage() {
        return mUploadMessage;
    }

    public void setmUploadMessage(ValueCallback<Uri[]> mUploadMessage) {
        this.mUploadMessage = mUploadMessage;
    }


    @SuppressLint("SetJavaScriptEnabled")
    public X5WebView(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
        this.context = arg0;
        initWebViewSettings();//初始化setting配置
        this.setWebViewClient(client);
        //this.setWebChromeClient(chromeClient);
        this.setDownloadListener(downloadListener);
        initProgressBar();//初始化进度条
        //this.getView().setClickable(true);
    }

    //setting配置
    private void initWebViewSettings() {
        WebSettings webSetting = this.getSettings();
        webSetting.setJavaScriptEnabled(true);//允许js调用
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口
        webSetting.setAllowFileAccess(true);//在File域下，能够执行任意的JavaScript代码，同源策略跨域访问能够对私有目录文件进行访问等
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);//控制页面的布局(使所有列的宽度不超过屏幕宽度)
        webSetting.setSupportZoom(false);//支持页面缩放
        webSetting.setBuiltInZoomControls(false);//设置出现缩放工具
        webSetting.setBuiltInZoomControls(false);//进行控制缩放
        webSetting.setAllowContentAccess(true);//是否允许在WebView中访问内容URL（Content Url），默认允许
        webSetting.setUseWideViewPort(true);//设置缩放密度
        webSetting.setSupportMultipleWindows(false);//设置WebView是否支持多窗口,如果为true需要实现onCreateWindow(WebView, boolean, boolean, Message)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //两者都可以
            webSetting.setMixedContentMode(webSetting.getMixedContentMode());//设置安全的来源
        }
        webSetting.setAppCacheEnabled(true);//设置应用缓存
        webSetting.setDomStorageEnabled(true);//DOM存储API是否可用
        webSetting.setGeolocationEnabled(true);//定位是否可用
        webSetting.setLoadWithOverviewMode(true);//是否允许WebView度超出以概览的方式载入页面，
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);//设置应用缓存内容的最大值
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);//设置是否支持插件
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);//重写使用缓存的方式
        webSetting.setAllowUniversalAccessFromFileURLs(true);//是否允许运行在一个file schema URL环境下的JavaScript访问来自其他任何来源的内容
        webSetting.setAllowFileAccessFromFileURLs(true);//是否允许运行在一个URL环境
    }

    //进度条
    private void initProgressBar() {
        mProgressView = new ProgressView(context);
        mProgressView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 6));
        mProgressView.setDefaultColor(progressColor);
        addView(mProgressView);
    }

    //客户端配置
    private WebViewClient client = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //这里直接加载url
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
            super.onPageStarted(webView, s, bitmap);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            //处理客户端与WebView同步，具体细节问题请看最上面传送门
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            String endCookie = cookieManager.getCookie(url);
            Log.i(TAG, "onPageFinished: endCookie : " + endCookie);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                CookieSyncManager.getInstance().sync();//同步cookie
            } else {
                CookieManager.getInstance().flush();
            }
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView webView, int i, String s, String s1) {
            super.onReceivedError(webView, i, s, s1);
            //网页问题报错的时候执行
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            super.onReceivedSslError(webView, sslErrorHandler, sslError);
            if (sslError.getPrimaryError() == android.net.http.SslError.SSL_INVALID) {// 校验过程遇到了bug
                //这里直接忽略ssl证书的检测出错问题，选择继续执行页面
                sslErrorHandler.proceed();
            } else {
                //不是证书问题时候则停止执行加载页面
                sslErrorHandler.cancel();
            }
        }
    };

    //下载监听器
    DownloadListener downloadListener = new DownloadListener() {
        @Override
        public void onDownloadStart(String arg0, String arg1, String arg2,
                                    String arg3, long arg4) {
            new AlertDialog.Builder(context)
                    .setTitle("allow to download？")
                    .setPositiveButton("yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    Toast.makeText(
                                            context,
                                            "fake message: i'll download...",
                                            Toast.LENGTH_LONG).show();
                                }
                            })
                    .setNegativeButton("no",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // TODO Auto-generated method stub
                                    Toast.makeText(
                                            context,
                                            "fake message: refuse download...",
                                            Toast.LENGTH_SHORT).show();
                                }
                            })
                    .setOnCancelListener(
                            new DialogInterface.OnCancelListener() {

                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    // TODO Auto-generated method stub
                                    Toast.makeText(
                                            context,
                                            "fake message: refuse download...",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }).show();
        }
    };

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        boolean ret = super.drawChild(canvas, child, drawingTime);
        canvas.save();
        Paint paint = new Paint();
        paint.setColor(0x7fff0000);
        paint.setTextSize(24.f);
        paint.setAntiAlias(true);
        canvas.restore();
        return ret;
    }

    public X5WebView(Context arg0) {
        super(arg0);
        setBackgroundColor(85621);
    }
}

