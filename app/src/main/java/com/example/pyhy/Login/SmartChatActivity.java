package com.example.pyhy.Login;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pyhy.Login.AIchat.Message;
import com.example.pyhy.Login.AIchat.MessageAdapter;
import com.example.pyhy.R;

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
    private ImageButton sendButton;
    private List<Message> messageList;
    private MessageAdapter messageAdapter;
    private OkHttpClient okHttpClient;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_smart_chat);

        // 初始化视图
        recyclerView = findViewById(R.id.recyclerView);
        welcomeTextView = findViewById(R.id.welcome_text);
        messageEditView = findViewById(R.id.chatEditText);
        sendButton = findViewById(R.id.sendButton);

        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);

        // 点击消息输入框时，隐藏欢迎信息
        messageEditView.setOnClickListener(v -> welcomeTextView.setVisibility(View.GONE));

        // 点击发送按钮时发送消息并调用API
        sendButton.setOnClickListener(v -> {
            String question = messageEditView.getText().toString().trim();
            if (!question.isEmpty()) {
                addToChat(question, Message.SENT_BY_ME);
//                callAPI(question);
                messageEditView.setText("");
                welcomeTextView.setVisibility(View.GONE);
            } else {
                Toast.makeText(SmartChatActivity.this, "请输入消息", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 添加消息到聊天列表
    protected void addToChat(String message, String sentBy) {
        runOnUiThread(() -> {
            messageList.add(new Message(message, sentBy));
            messageAdapter.notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
        });
    }

    // 添加机器人回应到聊天列表
    protected void addResponse(String response) {
        addToChat(response, Message.SENT_BY_BOT);
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