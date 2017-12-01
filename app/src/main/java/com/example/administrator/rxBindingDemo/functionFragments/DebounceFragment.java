package com.example.administrator.rxBindingDemo.functionFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.rxBindingDemo.R;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DebounceFragment extends Fragment {

    @BindView(R.id.btn_debounce)
    Button debounceBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_debounce, container, false);
        ButterKnife.bind(this, layout);
        RxView.clicks(debounceBtn)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(aVoid -> {
                    Toast.makeText(getContext(), "防抖动按钮", Toast.LENGTH_SHORT).show();
                });
//                .subscribe(new Action1<Void>() {
//                    @Override
//                    public void call(Void aVoid) {
//                        Toast.makeText(getContext(), "防抖动按钮", Toast.LENGTH_SHORT).show();
//                    }
//                });
        return layout;
    }

}
