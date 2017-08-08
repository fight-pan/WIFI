package com.quarktime.testjar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hyphenate.EMValueCallBack;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<String> d = new ArrayList<>();
        d.add(CONFIG_TABLE_NAME);
        getContactInfos(d, null);

    }
    private static final String CONFIG_TABLE_NAME = "hxuser";
    private static final String CONFIG_USERNAME = "username";
    private static final String CONFIG_NICK = "nickname";
    private static final String CONFIG_AVATAR = "avatar";
    public void getContactInfos(List<String> usernames, final EMValueCallBack<List<EaseUser>> callback) {
        ParseQuery<ParseObject> pQuery = ParseQuery.getQuery(CONFIG_TABLE_NAME);
        pQuery.whereContainedIn(CONFIG_USERNAME, usernames);
        pQuery.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> arg0, ParseException arg1) {
                if (arg0 != null) {
                    List<EaseUser> mList = new ArrayList<EaseUser>();
                    for (ParseObject pObject : arg0) {
                        EaseUser user = new EaseUser(pObject.getString(CONFIG_USERNAME));
                        ParseFile parseFile = pObject.getParseFile(CONFIG_AVATAR);
                        if (parseFile != null) {
                            user.setAvatar(parseFile.getUrl());
                        }
                        user.setNick(pObject.getString(CONFIG_NICK));
                        EaseCommonUtils.setUserInitialLetter(user);
                        mList.add(user);
                    }
                    callback.onSuccess(mList);
                } else {
//                    callback.onError(arg1.getCode(), arg1.getMessage());
                }
            }
        });
    }
}
