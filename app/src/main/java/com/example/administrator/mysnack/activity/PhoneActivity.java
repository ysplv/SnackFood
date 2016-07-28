package com.example.administrator.mysnack.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.ClipboardManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.mysnack.R;

public class PhoneActivity extends Activity {

    private TextView weiXin;
    private TextView phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        weiXin = (TextView) findViewById(R.id.text_weixin);
        phone = (TextView) findViewById(R.id.text_phone);

    }

    //事件分发与消费
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;

    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.linearlayout_phone_mine:
                Toast.makeText(PhoneActivity.this, "点击外侧关闭对话框!", Toast.LENGTH_LONG).show();
                break;
            case R.id.text_weixin:
                copy(weiXin.getText().toString().trim(), this);
                Toast.makeText(PhoneActivity.this, "已复制到剪切板!", Toast.LENGTH_LONG).show();
                finish();
                break;
            case R.id.text_call:
//                Intent intent = new Intent(Intent.ACTION_CALL);
//                Uri data = Uri.parse("tel:" + "135xxxxxxxx");
//                intent.setData(data);
//                startActivity(intent);

                Intent call = new Intent(Intent.ACTION_CALL);
                // 指定动作
                // call.setAction(Intent.ACTION_CALL);
                // 指定执行动作时需要的数据
                call.setData(Uri.parse("tel:10086"));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(call);
                finish();
//                Toast.makeText(this,"点我了!",Toast.LENGTH_LONG).show();
                break;
        }
    }

    public static void copy(String content, Context context) {
// 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }

    /**
     * 实现粘贴功能
     * add by wangqianzhou
     *
     * @param context
     * @return
     */
    public static String paste(Context context) {
// 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        return cmb.getText().toString().trim();
    }
}
