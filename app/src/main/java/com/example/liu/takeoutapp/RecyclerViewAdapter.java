package com.example.liu.takeoutapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<UserInfo> mUserInfos;
    private static final String TAG = "RecyclerViewAdapter";
    private int size;

    public RecyclerViewAdapter(Context context, List<UserInfo> userInfos) {
        mContext = context;
        mUserInfos = userInfos;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recy_item, viewGroup, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {
        if (size==1&&!((ItemHolder) viewHolder).phone.isEnabled()) {
            ((ItemHolder) viewHolder).phone.setEnabled(true);
            ((ItemHolder) viewHolder).phone.requestFocus();
            ((ItemHolder) viewHolder).locate.setEnabled(true);
            ((ItemHolder) viewHolder).foodNum.setEnabled(true);
            ((ItemHolder) viewHolder).linearLayout.setBackgroundColor(Color.WHITE);
            Log.d(TAG, "onBindViewHolder: "+"haha");
        }
        ((ItemHolder) viewHolder).phone.setText(mUserInfos.get(i).getPhone());
        ((ItemHolder) viewHolder).locate.setText(mUserInfos.get(i).getLocate());
        ((ItemHolder) viewHolder).foodNum.setText(mUserInfos.get(i).getFoodNum());

        ((ItemHolder) viewHolder).save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!("".equals(((ItemHolder) viewHolder).phone.getText().toString())) && (!("".equals(((ItemHolder) viewHolder).locate.getText().toString()))) && (!("".equals(((ItemHolder) viewHolder).foodNum.getText().toString())))) {
                    mUserInfos.get(i).setPhone(((ItemHolder) viewHolder).phone.getText().toString());
                    mUserInfos.get(i).setLocate(((ItemHolder) viewHolder).locate.getText().toString());
                    mUserInfos.get(i).setFoodNum(((ItemHolder) viewHolder).foodNum.getText().toString());

                    ((ItemHolder) viewHolder).phone.setEnabled(false);
                    ((ItemHolder) viewHolder).locate.setEnabled(false);
                    ((ItemHolder) viewHolder).foodNum.setEnabled(false);
                    if (mUserInfos.get(i).getStatus() == 0) {
                        ((MainActivity) mContext).addNewItem();
                        mUserInfos.get(i).setStatus(1);
                    }

                } else {
                    Toast.makeText(mContext, "兄die,信息不完整不能保存哦", Toast.LENGTH_SHORT).show();
                }

            }
        });
        ((ItemHolder) viewHolder).change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ItemHolder) viewHolder).phone.setEnabled(true);
                ((ItemHolder) viewHolder).locate.setEnabled(true);
                ((ItemHolder) viewHolder).foodNum.setEnabled(true);
                Log.d(TAG, "onClick: "+i);
            }
        });
        ((ItemHolder) viewHolder).phoneCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!((ItemHolder) viewHolder).phone.isEnabled()) {
                    call(((ItemHolder) viewHolder).phone.getText().toString(), viewHolder);
                } else {
                    Toast.makeText(mContext, "别急，先保存", Toast.LENGTH_SHORT).show();
                }

            }
        });
        ((ItemHolder) viewHolder).sendText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!((ItemHolder) viewHolder).phone.isEnabled()) {
                    sendMessage(((ItemHolder) viewHolder).phone.getText().toString(), viewHolder);
                } else {
                    Toast.makeText(mContext, "别急，先保存", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void call(String phoneNum, RecyclerView.ViewHolder viewHolder) {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phoneNum));
            mContext.startActivity(intent);
            ((ItemHolder) viewHolder).linearLayout.setBackgroundColor(Color.GREEN);
        } catch (Exception e) {

        }
    }

    private void sendMessage(String phoneNum, RecyclerView.ViewHolder viewHolder) {
        try {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNum));
            intent.putExtra("sms_body", "兄弟我是送外卖的 给你打了几个电话都不接 给你放在圈存机下面了");
            mContext.startActivity(intent);
            ((ItemHolder) viewHolder).linearLayout.setBackgroundColor(Color.YELLOW);
        } catch (Exception e) {

        }
    }

    @Override
    public int getItemCount() {
        size = mUserInfos.size();
        return mUserInfos.size();
    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        public EditText phone;
        public EditText locate;
        public Button save;
        public Button change;
        public Button sendText;
        public Button phoneCall;
        public EditText foodNum;
        public LinearLayout linearLayout;

        public ItemHolder(View itemView) {
            super(itemView);
            locate = itemView.findViewById(R.id.locate);
            phone = itemView.findViewById(R.id.phone);
            save = itemView.findViewById(R.id.save);
            change = itemView.findViewById(R.id.replace);
            sendText = itemView.findViewById(R.id.sendtext);
            foodNum = itemView.findViewById(R.id.food_num);
            phoneCall = itemView.findViewById(R.id.phone_call);
            linearLayout = itemView.findViewById(R.id.linear);
        }
    }
}
