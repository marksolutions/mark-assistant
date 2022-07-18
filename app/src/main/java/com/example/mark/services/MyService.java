package com.example.mark.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.mark.activities.MainActivity;
import com.example.mark.utils.Constants;

import java.util.ArrayList;

public class MyService extends Service {

    private final String TAG = "MyService";

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            setAudioRecord();
            handler.postDelayed(runnable, 3000);
        }
    };

    private void setAudioRecord() {

        AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audio.setStreamVolume(AudioManager.STREAM_MUSIC, 0, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                "com.example.theonetech.deepgo.services");

        SpeechRecognizer recognizer = SpeechRecognizer
                .createSpeechRecognizer(this.getApplicationContext());

        RecognitionListener listener = new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
                Log.i(TAG, "onReadyForSpeech");
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
                onVoiceResult(results);
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        };
        recognizer.setRecognitionListener(listener);
        recognizer.startListening(intent);
    }

    private void onVoiceResult(Bundle results) {
        ArrayList<String> voiceResults = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        if (voiceResults == null) {
            System.out.println("No voice results");
        } else {
            String s = voiceResults.get(0);
            openActivity(s);
        }
    }

    private void openActivity(String s) {

        if (s.toLowerCase().contains("mark")) {
            Intent dialogIntent = new Intent(this, MainActivity.class);
            dialogIntent.putExtra(Constants.START_ACTIVITY, true);
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(dialogIntent);

            onDestroy();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        handler.postDelayed(runnable, 0);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}