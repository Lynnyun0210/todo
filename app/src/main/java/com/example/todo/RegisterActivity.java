package com.example.todo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todo.R;
import com.example.todo.entity.User;
import com.example.todo.sqlite.BusinessResult;
import com.example.todo.sqlite.UserDB;
import com.example.todo.utils.TitleBarUtils;


import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    private ImageView ivPasswordEye, ivVerifyCode;

    private EditText etUsername, etPassword, etVerifyCode;

    private Button btnRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        TitleBarUtils.setTitle(this, "");
        TitleBarUtils.setBack(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bindView();
        initView();
    }

    private void bindView() {
        ivPasswordEye = findViewById(R.id.iv_password_eye);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnRegister = findViewById(R.id.btn_register);
    }

    private void initView() {
        ivPasswordEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransformationMethod method = etPassword.getTransformationMethod();
                if (method.equals(HideReturnsTransformationMethod.getInstance())) {
                    ivPasswordEye.setImageResource(R.drawable.ic_password_hide);
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    ivPasswordEye.setImageResource(R.drawable.ic_password_show);
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //注册
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);

                BusinessResult<User> result = UserDB.register(user);
                Toast.makeText(RegisterActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                if (result.isSuccess()) {
                    // 注册成功，返回登录界面，并传递用户信息
                    Intent intent = new Intent();
                    intent.putExtra("user", user);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}
