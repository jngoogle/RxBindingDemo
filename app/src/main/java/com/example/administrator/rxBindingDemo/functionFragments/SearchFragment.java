package com.example.administrator.rxBindingDemo.functionFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.administrator.rxBindingDemo.R;
import com.example.administrator.rxBindingDemo.adapter.CommonRecyclerAdapter;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

public class SearchFragment extends Fragment {

    @BindView(R.id.et_search)
    EditText searchEt;
    @BindView(R.id.rv_keywords)
    RecyclerView keywordsRv;

    List<String> keywordsList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, layout);

        final KeywordsAdapter keywordsAdapter = new KeywordsAdapter(getContext(), R.layout.item_list_keywords);
        keywordsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        keywordsRv.setAdapter(keywordsAdapter);
        keywordsRv.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        RxTextView.textChanges(searchEt)
                .debounce(500, TimeUnit.MILLISECONDS)
                .map(new Func1<CharSequence, String>() {
                    @Override
                    public String call(CharSequence charSequence) {
                        return charSequence.toString();
                    }
                })
                .map(new Func1<String, List<String>>() {
                    @Override
                    public List<String> call(String keyword) {
                        if (!TextUtils.isEmpty(keyword)) {
                            for (String s : getKeywordsList()) {
                                if (s != null && s.contains(keyword)) {
                                    keywordsList.add(s);
                                }
                            }
                        }
                        return keywordsList;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> strings) {
                        keywordsAdapter.clearDataList();
                        keywordsAdapter.addDataList(strings);
                    }
                });

        return layout;
    }

    public class KeywordsAdapter extends CommonRecyclerAdapter<String> {

        public KeywordsAdapter(Context context, int resId) {
            super(context, resId);
        }

        @Override
        public void bindData(MyViewHolder holder, int position, String itemData) {
            holder.setText(R.id.tv_item_keywords, itemData);
        }
    }

    public List<String> getKeywordsList() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("aab");
        list.add("aaabc");
        list.add("aaabcd");
        list.add("aaaaaabcde");
        list.add("aaaaaaac");
        list.add("bcd");
        list.add("bcdef");
        list.add("cd");
        list.add("cde");
        list.add("cdefg");
        list.add("dddkkk5662");
        list.add("ddbbs");

        return list;
    }
}
