package com.example.iot_android_ytbmeigroup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

public class khu_vuc_1 extends AppCompatActivity {

    private TextView nhietdo, doam, txtback;
    private ImageView den1, den2;

    private int c1=1;
    MqttAndroidClient mqttAndroidClient;
    private static final String Tag = "";
    String clientID = MqttClient.generateClientId();
    MqttAndroidClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khu_vuc1);
        AnhXa();
        txtback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x1 = new Intent(khu_vuc_1.this, main_activity.class);
                startActivity(x1);
            }
        });
        MQTT();
    }

    private void AnhXa()
    {
        nhietdo = findViewById(R.id.txtTemp);
        doam = findViewById(R.id.txtHum);
        den1 = findViewById(R.id.imgden1);
        den2 = findViewById(R.id.imgden2);
        txtback = findViewById(R.id.txtBack);
    }

    public void MQTT() {
        String clientID = MqttClient.generateClientId();
        final MqttAndroidClient client = new MqttAndroidClient(this.getApplicationContext(), "tcp://broker.hivemq.com:1883", clientID);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
        options.setCleanSession(false);

        // Remove these lines if not required
        // options.setUserName("");
        // options.setPassword("".toCharArray());

        try {
            final IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(khu_vuc_1.this, "Connected MQTT server", Toast.LENGTH_SHORT).show();
                    SUB(client, "den1");
                    SUB(client, "nhietdo");
                    client.setCallback(new MqttCallback() {
                        @Override
                        public void connectionLost(Throwable cause) {
                            Toast.makeText(khu_vuc_1.this, "Mat ket noi Server", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void messageArrived(String topic, MqttMessage message) throws Exception {
                            // control den
                            if (topic.equals("den1")) {
                                if (message.toString().equals("1")) {
                                    den1.setBackgroundResource(R.drawable.sang);
                                    c1 = 0;
                                }
                                if (message.toString().equals("0")) {
                                    den1.setBackgroundResource(R.drawable.toi);
                                    c1 = 1;
                                }
                            }
                            // lay du lieu nhiet do tu MQTT ve
                            if(topic.equals("nhietdo"))
                            {
                                nhietdo.setText(message.toString()+"℃");
                            }
                        }

                        @Override
                        public void deliveryComplete(IMqttDeliveryToken token) {
                        }
                    });

                    den1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            Toast.makeText(MainActivity.this, "den", Toast.LENGTH_SHORT).show();
                            c1++;
                            if(c1%2==0)
                            {
                                String topic = "den1";
                                String payload = "1";
                                byte[] encodePayload = new byte[0];
                                try
                                {
                                    encodePayload = payload.getBytes("UTF-8");
                                    MqttMessage message = new MqttMessage(encodePayload);
                                    message.setRetained(true);
                                    client.publish(topic, message);
                                }
                                catch (UnsupportedEncodingException | MqttException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                            else
                            {
                                String topic = "den1";
                                String payload = "0";
                                byte[] encodePayload = new byte[0];
                                try
                                {
                                    encodePayload = payload.getBytes("UTF-8");
                                    MqttMessage message = new MqttMessage(encodePayload);
                                    message.setRetained(true);
                                    client.publish(topic, message);
                                }
                                catch (UnsupportedEncodingException | MqttException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(khu_vuc_1.this, "that bai", Toast.LENGTH_SHORT).show();
                    Log.d(Tag, "onFailure");
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    } // end MQTT
    public void SUB( MqttAndroidClient client, String topic)
    {
        int qos = 1;
        try
        {
            IMqttToken subToken = client.subscribe(topic, qos);
            subToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // the message was published
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                }
            });

        }
        catch (MqttException e)
        {
            e.printStackTrace();
        }
    }//end SUB
}