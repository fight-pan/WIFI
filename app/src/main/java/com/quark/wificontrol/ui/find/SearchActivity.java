package com.quark.wificontrol.ui.find;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import com.quark.wificontrol.R;
import com.quark.wificontrol.base.BaseActivity;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by pan on 2016/9/8 0008.
 * >#
 * >#搜索
 */
public class SearchActivity extends BaseActivity {


    @InjectView(R.id.search_et)
    EditText searchEt;
    @InjectView(R.id.cancel_id)
    TextView cancelId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.inject(this);

        searchEt.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        searchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                    intent.putExtra("search", searchEt.getText().toString());
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

    }


    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }


    @OnClick(R.id.cancel_id)
    public void onClick() {
        finish();
    }
}
