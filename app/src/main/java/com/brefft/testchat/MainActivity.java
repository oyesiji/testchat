package com.brefft.testchat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.brefft.entity.ChatMessage;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;


public class MainActivity extends AppCompatActivity {
    private Button start;
    private TextView output;
    private OkHttpClient client;
    private final class EchoWebSocketListener extends WebSocketListener {
        private static final int NORMAL_CLOSURE_STATUS = 1000;
        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            Gson gson=new Gson();
            ChatMessage chatMessage=new ChatMessage();
           chatMessage.setSender("Daviii");
            chatMessage.setType(ChatMessage.MessageType.JOIN);
           String paramter = gson.toJson(chatMessage);
          // Stomp dd;
         //  Stomp dd;
            //webSocket.se
           webSocket.send(paramter);

         //   webSocket.send("What's up ?");
         //   webSocket.send(ByteString.decodeHex("deadbeef"));
          //  webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye !");
        }
        @Override
        public void onMessage(WebSocket webSocket, String text) {
            output("Receiving : " + text);
        }
        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            output("Receiving bytes : " + bytes.hex());
        }
        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(NORMAL_CLOSURE_STATUS, null);
            output("Closing : " + code + " / " + reason);
        }
        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            output("Error : " + t.getMessage());
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = (Button) findViewById(R.id.start);
        output = (TextView) findViewById(R.id.output);
        client = new OkHttpClient();
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start();
            }
        });
    }
    private void start() {
        //Request request = new Request.Builder().url("ws://10.0.2.2:8080").build();
        Request request = new Request.Builder().url("ws://10.0.2.2:8080/ws/067/z45cobwg/websocket").build();
        EchoWebSocketListener listener = new EchoWebSocketListener();
        WebSocket ws = client.newWebSocket(request, listener);

      //  client.dispatcher().executorService().shutdown();
    }

    public void test(){

    }
    private void output(final String txt) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i(txt,txt);
                output.setText(output.getText().toString() + "\n\n" + txt);
            }
        });
    }
}