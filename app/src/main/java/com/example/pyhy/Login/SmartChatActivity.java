package com.example.pyhy.Login;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pyhy.Login.AIchat.Message;
import com.example.pyhy.Login.AIchat.MessageAdapter;
import com.example.pyhy.R;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SmartChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView welcomeTextView;
    private EditText messageEditView;
    private MaterialButton sendImage;
    private List<Message> messageList;
    private MessageAdapter messageAdapter;
    private OkHttpClient okHttpClient;

    // 标志位，防止递归调用
    private boolean isAddingResponse = false;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_smart_chat);

        // 初始化视图
        recyclerView = findViewById(R.id.recyclerView);
        welcomeTextView = findViewById(R.id.welcome_text);
        messageEditView = findViewById(R.id.chatEditText);
        sendImage = findViewById(R.id.sendImage);

        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);

        // 点击消息输入框时，隐藏欢迎信息
        messageEditView.setOnClickListener(v -> welcomeTextView.setVisibility(View.GONE));

        // 点击发送按钮时发送消息并调用API
        sendImage.setOnClickListener(v -> {
            String question = messageEditView.getText().toString().trim();
            if (!question.isEmpty()) {
                addToChat(question, Message.SENT_BY_ME);
                messageEditView.setText("");
                welcomeTextView.setVisibility(View.GONE);
                callAPI(question);  // 调用API获取机器人的响应
            } else {
                Toast.makeText(SmartChatActivity.this, "请输入消息", Toast.LENGTH_SHORT).show();
            }
        });

        // 添加TextWatcher监听器，监听输入框的文本变化
        messageEditView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // 可以在这里处理文本变化前的事情
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // 如果输入框非空，设置按钮为深色，反之为淡色
                if (charSequence.length() > 0) {
                    sendImage.setEnabled(true); // 启用发送按钮
                    sendImage.setBackgroundColor(getResources().getColor(R.color.colorPrimary)); // 深色按钮
                } else {
                    sendImage.setEnabled(false); // 禁用发送按钮
                    sendImage.setBackgroundColor(getResources().getColor(R.color.colorButtonInactive)); // 淡色按钮
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // 可以在这里处理文本变化后的事情
            }
        });
    }

    // 添加消息到聊天列表
    @SuppressLint("NotifyDataSetChanged")
    protected void addToChat(String message, String sentBy) {
        runOnUiThread(() -> {
            messageList.add(new Message(message, sentBy));
            messageAdapter.notifyItemInserted(messageList.size() - 1);  // 只刷新新增项
            recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
        });
    }

    // 模拟调用API获取机器人的响应
    private void callAPI(String question) {
        // 这里是API调用的逻辑，可以是网络请求
        // 假设我们收到了回应
        String response = "测试回答：》》》》";  // 模拟回应
        addResponse(response);
    }

    // 添加机器人回应到聊天列表
    protected void addResponse(String response) {
        // 防止递归调用
        if (isAddingResponse) {
            return;  // 如果已经在添加机器人回应，则直接返回
        }

        isAddingResponse = true;  // 设置标志位，表示正在添加回应
        addToChat(response, Message.SENT_BY_BOT);  // 添加回应到聊天列表
        isAddingResponse = false;  // 重置标志位
    }


    // 调用API接口获取回答
//    protected void callAPI(String question) {
//        okHttpClient = new OkHttpClient();
//
//        // 1. 初始化SDK
//        SparkChainConfig config = SparkChainConfig.builder()
//                .appID(getString(R.string.app_id))
//                .apiKey(getString(R.string.api_key))
//                .apiSecret(getString(R.string.api_secret));
//
//        int ret = SparkChain.getInst().init(getApplicationContext(), config);
//        Log.v("API_INIT", String.valueOf(ret));
//
//        // 2. 构建OkHttp请求体
//        String jsonPayload = String.format("{\"question\":\"%s\"}", question);
//        RequestBody body = RequestBody.create(jsonPayload, MediaType.get("application/json; charset=utf-8"));
//
//        // 3. 构建POST请求
//        Request request = new Request.Builder()
//                .url(getString(R.string.chat_api_url))
//                .post(body)
//                .addHeader("Authorization", "Bearer " + getString(R.string.api_token))
//                .build();
//
//        // 4. 发送请求并处理响应
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
//                runOnUiThread(() -> {
//                    Toast.makeText(SmartChatActivity.this, "请求失败，请稍后再试", Toast.LENGTH_SHORT).show();
//                });
//                Log.e("API_CALL", "Request failed: " + e.getMessage());
//            }
//
//            @Override
//            public void onResponse(@NonNull okhttp3.Call call, @NonNull Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    String responseData = response.body().string();
//                    try {
//                        JSONObject jsonResponse = new JSONObject(responseData);
//                        if (jsonResponse.has("answer")) {
//                            String answer = jsonResponse.getString("answer");
//                            addResponse(answer.trim());
//                        } else {
//                            Log.e("API_CALL", "No 'answer' field in the response");
//                        }
//                    } catch (JSONException e) {
//                        Log.e("API_CALL", "Error parsing JSON response: " + e.getMessage());
//                    }
//                } else {
//                    Log.e("API_CALL", "Request failed with code: " + response.code());
//                }
//            }
//        });
//    }
}