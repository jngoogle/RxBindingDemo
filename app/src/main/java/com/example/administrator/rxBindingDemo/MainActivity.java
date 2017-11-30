package com.example.administrator.rxBindingDemo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.rxBindingDemo.functionFragments.BonusFragment;
import com.example.administrator.rxBindingDemo.functionFragments.DebounceFragment;
import com.example.administrator.rxBindingDemo.functionFragments.LoginFragment;
import com.example.administrator.rxBindingDemo.functionFragments.SearchFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    List<Fragment> fragmentList = new ArrayList<>();
    LoginFragment loginFragment = new LoginFragment();
    DebounceFragment debounceFragment = new DebounceFragment();
    BonusFragment bonusFragment = new BonusFragment();
    SearchFragment searchFragment = new SearchFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        fragmentList.add(loginFragment);
        fragmentList.add(debounceFragment);
        fragmentList.add(bonusFragment);
        fragmentList.add(searchFragment);
        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(myViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public class MyViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList;
        private String[] titles = {"多表单验证", "防抖处理", "彩蛋", "搜索功能"};

        public MyViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

}
