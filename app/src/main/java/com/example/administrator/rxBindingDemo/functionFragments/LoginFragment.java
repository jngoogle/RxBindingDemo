package com.example.administrator.rxBindingDemo.functionFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.rxBindingDemo.R;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Func2;


public class LoginFragment extends Fragment {

    @BindView(R.id.et_username)
    EditText usernameEt;
    @BindView(R.id.et_psd)
    EditText psdEt;
    @BindView(R.id.btn_login)
    Button loginBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, layout);
        loginBtn.setEnabled(false);// 登陆按钮默认不可用

        // TODO 1 使用 RxBinding 绑定 View 并且创建其相应的事件源 Observable
        // TODO 2 使用 ComebineLatest 操作符合并两个事件源最近发射的事件，
        // TODO 然后根据这一事件进行按钮可用状态的判断
        Observable<CharSequence> usernameObservable = RxTextView.textChanges(usernameEt);
        Observable<CharSequence> psdObservable = RxTextView.textChanges(psdEt);
        rx.Observable.combineLatest(usernameObservable, psdObservable,
                new Func2<CharSequence, CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence usernameCharSequence, CharSequence psdCharSequence) {
                        if (!TextUtils.isEmpty(usernameCharSequence) && !TextUtils.isEmpty(psdCharSequence)) {
                            return true;
                        }
                        return false;
                    }
                })
                .subscribe(aBoolean -> {
                    RxView.enabled(loginBtn).call(aBoolean);
                });
//                .subscribe(new Action1<Boolean>() {
//                    @Override
//                    public void call(Boolean isBtnAvailalbe) {
//                        RxView.enabled(loginBtn).call(isBtnAvailalbe);
//                    }
//                });

        return layout;
    }
}
