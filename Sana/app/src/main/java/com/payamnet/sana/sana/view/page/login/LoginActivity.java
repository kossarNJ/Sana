package com.payamnet.sana.sana.view.page.login;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.payamnet.sana.sana.R;
import com.payamnet.sana.sana.server.CallLoginWebService;
import com.payamnet.sana.sana.server.CheckInternetConnectivity;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by kosar on 7/10/18.
 */

public class LoginActivity extends FragmentActivity {

    private TextView userName;
    private TextView password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        new CheckInternetConnectivity(LoginActivity.this).execute((Context) getApplication());

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/YEKAN.TTF")
                .setFontAttrId(R.attr.fontPath)
                .build());

        userName = (TextView) findViewById(R.id.username);
        password = (TextView) findViewById(R.id.password);

        TextView login = (TextView) findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameStr = userName.getText().toString();
                String password_Str = password.getText().toString();
                new CallLoginWebService(LoginActivity.this).execute(usernameStr, password_Str);
            }
        });
    }
}
