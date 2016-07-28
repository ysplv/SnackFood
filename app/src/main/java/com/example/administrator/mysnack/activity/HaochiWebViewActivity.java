package com.example.administrator.mysnack.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.example.administrator.mysnack.R;

public class HaochiWebViewActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = HaochiWebViewActivity.class.getSimpleName();
    private String WebViewPath1="http://ds.lingshi.cccwei.com/api.php? apptype=0&srv=2506&goods_id=";
    private String WebViewPath2="&cid=10002&uid=0&tms=20150721190147&sig=8c35f5a024148111&wssig=308efe4382a088e0&os_type=3&version=23";
    protected ImageView ivBack;
    protected WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_haochi_web_view);
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        String WVPath=WebViewPath1+id+"&cid=10002&uid=0&tms=20150721190147&sig=8c35f5a024148111&wssig=308efe4382a088e0&os_type=3&version=23";
        Log.e(TAG, "========initData: " + id + "====" + WVPath);
        WebSettings webSetting = webView.getSettings();
        webSetting.setDomStorageEnabled(true);
        webView.loadUrl(WVPath);
        Log.e(TAG, "=====initData=====: "+webView.getUrl() );

    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back_webview);
        ivBack.setOnClickListener(this);//返回的监听
        webView = (WebView) findViewById(R.id.webView_webView);

    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
