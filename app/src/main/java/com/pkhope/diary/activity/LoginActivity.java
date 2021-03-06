package com.pkhope.diary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.pkhope.diary.MyApplication;
import com.pkhope.diary.R;
import com.pkhope.diary.callable.OnLogInCallback;
import com.pkhope.diary.model.Document;
import com.pkhope.diary.model.User;

import java.util.List;

/**
 * Created by thinkpad on 2015/8/11.
 */
public class LoginActivity extends AppCompatActivity {

    private Button mBtnLogin;
    private Button mBtnRegister;
  //  private TextView mTvForgetpassword;
    private EditText mEtUser;
    private EditText mEtPwd;
    private String mUser;
    private String mPwd;
//    public static final String URL = "http://10.0.3.2/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mBtnLogin = (Button) findViewById(R.id.login);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkInput()){

                    return;
                }

//                AVQuery<User> query = AVObject.getQuery(User.class);
//                query.whereEqualTo("userName",mUser);
//                query.findInBackground(new FindCallback<User>() {
//                    @Override
//                    public void done(List<User> list, AVException e) {
//                        if(list.size() == 0){
//
//                            Toast.makeText(getApplicationContext(),"用户不存在",Toast.LENGTH_SHORT).show();
//
//                        } else {
//                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                            startActivity(intent);
//                            finish();
//                        }
//                    }
//                });

                User.logInInBackground(mUser, mPwd, new OnLogInCallback() {
                    @Override
                    public void done(User user, AVException e) {
                        if(e == null){

                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            intent.putExtra("from","LoginActivity");
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        mBtnRegister = (Button) findViewById(R.id.register);
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        TextView tvFp = (TextView) findViewById(R.id.tv_forget_password);
        tvFp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ResetPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mEtUser = (EditText) findViewById(R.id.et_user);
        mEtPwd = (EditText) findViewById(R.id.et_pwd);
    }

    private boolean checkInput() {
        mUser = mEtUser.getText().toString();
        mPwd = mEtPwd.getText().toString();
        if (mUser.equals("") || mPwd.equals("")){
            return false;
        }
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
