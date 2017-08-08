package com.quark.wificontrol.ui.find;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RatingBar;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.CommentRequest;
import com.quark.api.auto.bean.CommentResponse;
import com.quark.wificontrol.AppContext;
import com.quark.wificontrol.R;
import com.quark.wificontrol.api.ApiResponse;
import com.quark.wificontrol.api.remote.QuarkApi;
import com.quark.wificontrol.base.BaseActivity;
import com.quark.wificontrol.util.Utils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

/**
 * Created by pan on 2016/9/8 0008.
 * >#
 * >#评价
 */
public class EvaluationActivity extends BaseActivity {

    @InjectView(R.id.ratingBar)
    RatingBar ratingBar;
    @InjectView(R.id.comment_et)
    EditText commentEt;

    String star;//星星
    String content;//评论内容
    String type;//1-同城，2-便民
    String id;//同城ID[city_information_id]或者便民Id[people_services_id]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);
        ButterKnife.inject(this);
        setTopTitle("评价");
        setBackButton();

        id = (String) getValue4Intent("id");
        type = (String) getValue4Intent("type");

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.ping_bt)
    public void onClick() {
        star = (ratingBar.getRating() + "").substring(0, (ratingBar.getRating() + "").indexOf("."));
        content = commentEt.getText().toString();
        if (Utils.isEmpty(content)) {
            showToast("请输入评价内容");
        } else {
            commentRequest();
        }
    }

    public void commentRequest() {
        CommentRequest rq = new CommentRequest();
        rq.setCity_information_id(id);
        rq.setContent(commentEt.getText().toString());
        rq.setType(type);
        rq.setStar(star);
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandlerget);
    }

    private final AsyncHttpResponseHandler mHandlerget = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, EvaluationActivity.this, CommentResponse.class);
            if (kd != null) {
                CommentResponse info = (CommentResponse) kd;
                if (info.getStatus() == 1) {
                    showToast(info.getMessage());
                    finish();
                } else {
                    showToast(info.getMessage());
                }
            }
            showWait(false);
        }

        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
            AppContext.showToast("网络出错" + arg0);
            showWait(false);
        }
    };
}
