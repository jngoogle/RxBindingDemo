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

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;


public class BonusFragment extends Fragment {

    @BindView(R.id.btn_bonus)
    Button bonusBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_bonus, container, false);
        ButterKnife.bind(this, layout);
        Observable<Void> bonusObservable = RxView.clicks(bonusBtn).share();
        bonusObservable.buffer(bonusObservable.debounce(300, TimeUnit.MILLISECONDS))
                .map(new Func1<List<Void>, Integer>() {
                    @Override
                    public Integer call(List<Void> voids) {
                        return voids.size();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aInteger -> {
                    bonusBtn.setText("" + aInteger + "次");
                    if (aInteger == 7) {
                        Toast.makeText(getContext(), "竟然成功连击了7次", Toast.LENGTH_SHORT).show();
                    }
                });
//                .subscribe(new Action1<Integer>() {
//                    @Override
//                    public void call(Integer integer) {
//                        bonusBtn.setText("" + integer + "次");
//                        if (integer == 7) {
//                            Toast.makeText(getContext(), "竟然成功连击了7次", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
        return layout;
    }

}
