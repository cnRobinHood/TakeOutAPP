package com.example.liu.takeoutapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView mRecyclerView;
    private Button mButton;
    private List<UserInfo> mUserInfos;
    private RecyclerViewAdapter mAdapter;
    private long oldTime = 0;
    private LinearLayoutManager mLinearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();
        mUserInfos = new ArrayList<>();
        mButton = findViewById(R.id.bt_clear);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUserInfos.clear();
                userInfoInit();
                mAdapter.notifyDataSetChanged();


            }
        });


            userInfoInit();

        mRecyclerView = findViewById(R.id.recycler);
        mAdapter = new RecyclerViewAdapter(this, mUserInfos);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void userInfoInit() {
        UserInfo userInfo = new UserInfo();
        userInfo.setFoodNum("");
        userInfo.setLocate("");
        userInfo.setPhone("");
        userInfo.setStatus(0);
        mUserInfos.add(userInfo);
    }

    public void addNewItem() {
        userInfoInit();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        if (oldTime == 0) {
            oldTime = System.currentTimeMillis();
            Toast.makeText(this, "再次点击退出", Toast.LENGTH_SHORT).show();

        } else if (System.currentTimeMillis() - oldTime < 2000) {
           MainActivity.this.finish();
        } else {
            oldTime = 0;
            Toast.makeText(this, "再次点击退出", Toast.LENGTH_SHORT).show();

        }

    }

    private void checkPermission() {
        if ((ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.SEND_SMS}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                Log.d(TAG, "onRequestPermissionsResult: " + grantResults.length + (grantResults[0] == PackageManager.PERMISSION_GRANTED));
                if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    this.finish();
                }
        }
    }
}
