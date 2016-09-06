package com.example.nsd.nsdaudio.NSDAudio.com.example.nsd.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.nsd.nsdaudio.NSDAudio.AudioInputBase64;
import com.example.nsd.nsdaudio.NSDAudio.AudioInputDelegate;
import com.example.nsd.nsdaudio.NSDAudio.AudioOutputBase64;
import com.example.nsd.nsdaudio.NSDAudio.AudioOutputDelegate;
import com.example.nsd.nsdaudio.R;

public class MainActivity extends AppCompatActivity {
    Button player = null;
    Button recorder = null;
    AudioOutputBase64 audioOutputBase64 = new AudioOutputBase64() ;
    AudioInputBase64 audioInputBase64;
    String str = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        player = (Button) findViewById(R.id.player);
        recorder = (Button) findViewById(R.id.recorder);

        audioInputBase64.setDelegate(new AudioInputDelegate() {
            @Override
            public void afterPlay() {
                Toast.makeText(MainActivity.this,"Audio stopped",Toast.LENGTH_LONG).show();
                player.setText("Play");
            }
        });

        audioOutputBase64.setDelegate(new AudioOutputDelegate() {
            @Override
            public void finish(String base64) {
                Log.d("result " ,base64);

                str = base64;

            }
        });

        recorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((Button)v).getText().toString().equals("Stop")){
                    audioOutputBase64.stopRecording();
                    ((Button)v).setText("Record");
                    return;

                }




                audioOutputBase64.startRecording();

                ((Button)v).setText("Stop");
            }

        });

        player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(((Button)v).getText().toString().equals("Stop")){
                    audioInputBase64.stopAudio();
                    return;
                }

            audioInputBase64 = new AudioInputBase64(str);


                audioInputBase64.playAudio();
                ((Button)v).setText("Stop");
            }
        });


    }



}
