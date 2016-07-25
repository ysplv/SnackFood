package com.example.administrator.mysnack;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.mysnack.fragment.HomeFragment;
import com.example.administrator.mysnack.fragment.MineFragment;
import com.example.administrator.mysnack.fragment.SaleFragment;
import com.example.administrator.mysnack.fragment.SubjectFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageView ivHome;
    private ImageView ivSale;
    private ImageView ivSubject;
    private ImageView ivMine;
    private List<Fragment> fragments;
    private Fragment currentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initFragment();

    }

    private void initFragment() {
        fragments = new ArrayList<>();
        HomeFragment homeFragment = new HomeFragment();
        SaleFragment saleFragment = new SaleFragment();
        SubjectFragment subjectFragment = new SubjectFragment();
        MineFragment mineFragment = new MineFragment();
        fragments.add(homeFragment);
        fragments.add(saleFragment);
        fragments.add(subjectFragment);
        fragments.add(mineFragment);
        currentFragment = homeFragment;
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContent_activity_main, homeFragment).commit();
    }

    private void initView() {
        ivHome = (ImageView) findViewById(R.id.iv_home_main);
        ivHome.setEnabled(false);
        ivSale = (ImageView) findViewById(R.id.iv_sale_main);
        ivSubject = (ImageView) findViewById(R.id.iv_subject_main);
        ivMine = (ImageView) findViewById(R.id.iv_mine_main);

    }

    //点击监听事件
    public void click(View view) {
        changeState();
        switch (view.getId()) {
            case R.id.layout_home_main:
                ivHome.setEnabled(false);
                switchFragment(currentFragment, fragments.get(0));

                break;
            case R.id.layout_sale_main:
                ivSale.setEnabled(false);
                switchFragment(currentFragment, fragments.get(1));
                break;
            case R.id.layout_subject_main:
                ivSubject.setEnabled(false);
                switchFragment(currentFragment, fragments.get(2));
                break;
            case R.id.layout_mine_main:
                ivMine.setEnabled(false);
                switchFragment(currentFragment, fragments.get(3));
                break;
        }
    }

    private void changeState() {
        ivHome.setEnabled(true);
        ivSale.setEnabled(true);
        ivSubject.setEnabled(true);
        ivMine.setEnabled(true);

    }

    public void switchFragment(Fragment from, Fragment to) {
        if (from == to) {
//            Toast.makeText(MainActivity.this, "不要重复点击！", Toast.LENGTH_LONG).show();
            return;
        }
        if (from == null || to == null)
            return;
        if (!to.isAdded()) {
            // 隐藏当前的fragment，add下一个到Activity中

            getSupportFragmentManager().beginTransaction().hide(from).add(R.id.fragmentContent_activity_main, to).commit();

        } else {
            // 隐藏当前的fragment，显示下一个
            getSupportFragmentManager().beginTransaction().hide(from).show(to).commit();

        }
        currentFragment = to;

    }
}
