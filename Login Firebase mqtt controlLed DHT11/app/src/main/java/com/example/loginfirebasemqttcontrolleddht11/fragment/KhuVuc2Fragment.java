package com.example.loginfirebasemqttcontrolleddht11.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.loginfirebasemqttcontrolleddht11.MainActivity;
import com.example.loginfirebasemqttcontrolleddht11.R;

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

public class KhuVuc2Fragment extends Fragment {
    private TextView nhietdoKV2, doamKV2, txtback;
    private ImageView den1KV2, den2KV2;

    private int c1 = 1, c2 =1;
    private MqttAndroidClient mqttAndroidClient; // Khai báo biến
    private static final String Tag = "MQTT_TAG"; // Đặt tên cho tag log
    private String clientID = MqttClient.generateClientId(); // Khởi tạo clientID
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_khuvuc2, container, false);
        // Ánh xạ
        nhietdoKV2 = view.findViewById(R.id.txtTempKV2);
        doamKV2 = view.findViewById(R.id.txtHumKV2);
        den1KV2 = view.findViewById(R.id.imgden1KV2);
        den2KV2 = view.findViewById(R.id.imgden2KV2);
        txtback = view.findViewById(R.id.txtBackKV2);

        // su kien nut nhan Back
        txtback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), MainActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }
        });

        MQTT(); // Gọi phương thức MQTT

        return view;
    }
    // ket noi MQTT
    public void MQTT() {
        // Khởi tạo MqttAndroidClient nếu chưa khởi tạo
        if (mqttAndroidClient == null) {
            mqttAndroidClient = new MqttAndroidClient(requireContext(), "tcp://broker.hivemq.com:1883", clientID);
        }

        MqttConnectOptions options = new MqttConnectOptions();
        options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
        options.setCleanSession(false);

        try {
            final IMqttToken token = mqttAndroidClient.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(requireContext(), "Connected MQTT server", Toast.LENGTH_SHORT).show();
                    SUB(mqttAndroidClient, "den1KV2");
                    SUB(mqttAndroidClient, "den2KV2");
                    SUB(mqttAndroidClient, "nhietdoKV2");
                    SUB(mqttAndroidClient, "doamKV2");
                    mqttAndroidClient.setCallback(new MqttCallback() {
                        @Override
                        public void connectionLost(Throwable cause) {
                            Toast.makeText(requireContext(), "Mat ket noi Server", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void messageArrived(String topic, MqttMessage message) throws Exception {
                            // Xử lý tin nhắn nhận được
                            if (topic.equals("den1KV2")) {
                                if (message.toString().equals("1")) {
                                    den1KV2.setBackgroundResource(R.drawable.sang);
                                    c1 = 0;
                                }
                                if (message.toString().equals("0")) {
                                    den1KV2.setBackgroundResource(R.drawable.toi);
                                    c1 = 1;
                                }
                            }
                            if (topic.equals("den2KV2")) {
                                if (message.toString().equals("1")) {
                                    den2KV2.setBackgroundResource(R.drawable.sang);
                                    c1 = 0;
                                }
                                if (message.toString().equals("0")) {
                                    den2KV2.setBackgroundResource(R.drawable.toi);
                                    c1 = 1;
                                }
                            }
                            if (topic.equals("nhietdoKV2")) {
                                nhietdoKV2.setText(message.toString() + "℃");
                            }
                            if (topic.equals("doamKV2")) {
                                doamKV2.setText(message.toString() + "%");
                            }
                        }

                        @Override
                        public void deliveryComplete(IMqttDeliveryToken token) {
                        }
                    });

                    // xu lí su kien nhan nut dieu khien den
                    den1KV2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            c1++;
                            String topic = "den1KV2";
                            String payload = (c1 % 2 == 0) ? "1" : "0";
                            byte[] encodePayload;
                            try {
                                encodePayload = payload.getBytes("UTF-8");
                                MqttMessage message = new MqttMessage(encodePayload);
                                message.setRetained(true);
                                mqttAndroidClient.publish(topic, message);
                            } catch (UnsupportedEncodingException | MqttException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    den2KV2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            c2++;
                            String topic = "den2KV2";
                            String payload = (c2 % 2 == 0) ? "1" : "0";
                            byte[] encodePayload;
                            try {
                                encodePayload = payload.getBytes("UTF-8");
                                MqttMessage message = new MqttMessage(encodePayload);
                                message.setRetained(true);
                                mqttAndroidClient.publish(topic, message);
                            } catch (UnsupportedEncodingException | MqttException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(requireContext(), "that bai", Toast.LENGTH_SHORT).show();
                    Log.d(Tag, "onFailure");
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void SUB(MqttAndroidClient client, String topic) {
        int qos = 1;
        try {
            IMqttToken subToken = client.subscribe(topic, qos);
            subToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // Đã đăng ký thành công
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Đăng ký thất bại
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
