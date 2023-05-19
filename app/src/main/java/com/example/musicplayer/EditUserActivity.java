package com.example.musicplayer;

import static com.example.musicplayer.PlayerActivity.position;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musicplayer.api.SongApi;
import com.example.musicplayer.api.UserApi;
import com.example.musicplayer.domain.Category;
import com.example.musicplayer.domain.Song;
import com.example.musicplayer.domain.SongMessage;
import com.example.musicplayer.domain.SongUpdate;
import com.example.musicplayer.domain.User;
import com.example.musicplayer.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditUserActivity extends AppCompatActivity {

    EditText edFirstName, ed_lastname, edEmail, edPhone, edPassword;

    Button btnSubmit;
    TextView tvCancel;

    User user;

    UserApi userApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        init();
    }


    private void init() {
        edFirstName =findViewById(R.id.edFirstName);
        ed_lastname = findViewById(R.id.edLastname);
        edEmail = findViewById(R.id.edEmail);
        edPhone = findViewById(R.id.edPhone);
        edPassword = findViewById(R.id.edPassword);
        btnSubmit = findViewById(R.id.btnEditUserSubmit);
        tvCancel = findViewById(R.id.btnEditUserCancel);
        loadData();
        setEvent();
    }
    private void loadData(){
        Intent intent = getIntent();
        user =(User) intent.getSerializableExtra("data");
        edFirstName.setText(user.getFirst_name());
        ed_lastname.setText(user.getLast_name());
        edEmail.setText(user.getEmail());
        edPhone.setText(user.getPhone());
        edPassword.setText(user.getPassword());
    }
    private void setEvent() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edFirstName.getText().toString().isEmpty() || ed_lastname.getText().toString().isEmpty() ||
                        edEmail.getText().toString().isEmpty() ||edPassword.getText().toString().isEmpty() )
                {
                    Toast.makeText(EditUserActivity.this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else if (edPassword.getText().toString().trim().length()<4) {
                    Toast.makeText(EditUserActivity.this, "Mật khẩu ít nhất phải gồm 4 kí tự", Toast.LENGTH_SHORT).show();
                } else {
                    submit();
                }
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void submit(){
        userApi= RetrofitClient.getInstance().getRetrofit().create(UserApi.class);
        User userUpdate = user;
        userUpdate.setFirst_name(edFirstName.getText().toString());
        userUpdate.setLast_name(ed_lastname.getText().toString());
        userUpdate.setEmail(edEmail.getText().toString());
        userUpdate.setPassword(edPassword.getText().toString());

        Long id = userUpdate.getId();

        userApi.update(id, userUpdate).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(EditUserActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}
