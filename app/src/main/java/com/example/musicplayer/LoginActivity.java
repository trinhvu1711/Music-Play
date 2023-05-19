package com.example.musicplayer;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musicplayer.api.UserApi;
import com.example.musicplayer.asset.LoadingDialog;
import com.example.musicplayer.domain.User;
import com.example.musicplayer.domain.UserMessage;
import com.example.musicplayer.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextView tvToRegister;
    Button btnLogin;
    EditText edPhone, edPassword;
    UserApi userApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        setEvent();
    }

    private void setEvent() {
        tvToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(edPhone.getText().toString().isEmpty() || edPassword.getText().toString().isEmpty())
                    {
                        Toast.makeText(LoginActivity.this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String phone = edPhone.getText().toString().trim();
                        String password = edPassword.getText().toString().trim();

                        if (!phone.isEmpty() && !password.isEmpty()) {
                            userApi = RetrofitClient.getInstance().getRetrofit().create(UserApi.class);
                            userApi.login(phone, password).enqueue(new Callback<UserMessage>() {
                                @Override
                                public void onResponse(Call<UserMessage> call, Response<UserMessage> response) {
                                    UserMessage userLogin = response.body();
                                    //                            System.out.println("-----------------");
                                    //                            System.out.println(userLogin.getUser().getRole());
                                    if (userLogin.getUser() != null) {
                                        System.out.println("----------" + userLogin.getUser().getFirst_name());
                                        // Xử lý kết quả trả về nếu thành công
                                        Toast.makeText(LoginActivity.this, userLogin.getMessage(), Toast.LENGTH_SHORT).show();
                                        User user = userLogin.getUser();
                                        System.out.println("-----------------");
                                        System.out.println(user.getRole());
                                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                                        //                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        //                                startActivity(intent);
                                        if (user.getRole().equals("user")) {
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        } else {
                                            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                            startActivity(intent);
                                        }
                                    } else {
                                        // Xử lý lỗi nếu kết quả trả về không thành công
                                        Toast.makeText(LoginActivity.this, userLogin.getMessage(), Toast.LENGTH_SHORT).show();
                                        //                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                        //                                startActivity(intent);
                                    }
                                }

                                @Override
                                public void onFailure(Call<UserMessage> call, Throwable t) {
                                    // Xử lý lỗi nếu có lỗi xảy ra trong quá trình gọi API
                                    System.out.println(t);
                                }
                            });
                        }
                    }
                }
            });
    }

    private void init() {
        tvToRegister = findViewById(R.id.tvToRegister);
        btnLogin = findViewById(R.id.btnLogin);
        edPhone = findViewById(R.id.edLoginPhone);
        edPassword = findViewById(R.id.edLoginPw);
    }
}
