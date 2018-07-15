package com.payamnet.sana.sana.view.page.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.payamnet.sana.sana.MainActivity;
import com.payamnet.sana.sana.R;
import com.payamnet.sana.sana.constants.Constant;
import com.payamnet.sana.sana.constants.Messages;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

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

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/YEKAN.TTF")
                .setFontAttrId(R.attr.fontPath)
                .build());

        TextView registerButton = (TextView) findViewById(R.id.register_button);
        userName = (TextView) findViewById(R.id.username);
        password = (TextView) findViewById(R.id.password);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userName.getText().toString().equals(Constant.USERNAME) && password.getText().toString().equals(Constant.PASSWORD)) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);

//                    Toast.makeText(LoginActivity.this, Messages.WELCOME, Toast.LENGTH_SHORT).show();


                    finish();
                } else {
                    Toast.makeText(getApplication(), Messages.NOT_AUTHENTICATED, Toast.LENGTH_LONG).show();
                    Log.i("debug", "onClick: not authenticated.");
                }
            }
        });

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
