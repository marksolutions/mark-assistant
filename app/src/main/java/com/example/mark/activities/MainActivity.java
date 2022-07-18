package com.example.mark.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mark.R;
import com.example.mark.utils.Libs;
import com.example.mark.utils.Utility;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imgMic;

    TextToSpeech textToSpeech;
    String voiceSearchString;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            onTextCatch();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });

        onInit();
    }

    private void onInit() {
        imgMic = findViewById(R.id.imgMic);

        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                Manifest.permission.RECORD_AUDIO
        };

        if (!Utility.hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        speechToText();


        imgMic.setOnClickListener(this);
    }

    private void onTextCatch() {
        String s = Libs.answerSpeech(MainActivity.this, voiceSearchString);

        textToSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgMic:

                speechToText();

                break;

        }
    }

    private void speechToText() {

        AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audio.setStreamVolume(AudioManager.STREAM_MUSIC, 100, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                "com.example.mark");

        SpeechRecognizer speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        RecognitionListener recognitionListener = new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {

            }

            @Override
            public void onResults(Bundle results) {
                ArrayList list = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                voiceSearchString = (String) list.get(0);

                handler.postDelayed(runnable, 1000);
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        };

        speechRecognizer.setRecognitionListener(recognitionListener);
        speechRecognizer.startListening(intent);
    }

    @Override
    protected void onPause() {
        /*if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }*/

        Utility.startService(MainActivity.this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        Utility.stopService(MainActivity.this);
        super.onResume();
    }
}
