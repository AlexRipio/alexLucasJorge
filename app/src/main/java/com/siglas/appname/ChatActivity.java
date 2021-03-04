package com.siglas.appname;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cenkgun.chatbar.ChatBarView;

public class ChatActivity extends AppCompatActivity {

    TextView textView;
    ChatBarView chatBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        textView = (TextView)findViewById(R.id.textView);
        chatBarView = (ChatBarView)findViewById(R.id.ChatBar);

        //Setup
        chatBarView.setMessageBoxHint("Enter your message");
        chatBarView.setSendClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(chatBarView.getMessageText());
            }
        });

    }
}