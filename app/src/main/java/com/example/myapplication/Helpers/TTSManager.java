package com.example.myapplication.Helpers;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

public class TTSManager {

    private TextToSpeech tts = null;
    private boolean isLoaded = false;

    public void init(Context context) {
        try {
            tts = new TextToSpeech(context, onInitListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final TextToSpeech.OnInitListener onInitListener = new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int status) {
            if (status == TextToSpeech.SUCCESS) {
                tts.setLanguage(Locale.getDefault());
                isLoaded = true;
            }
        }
    };

    public void initQueue(String text) {

        if (isLoaded) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
            } else {
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            }
        }
        else
            Log.e("error", "TTS Not Initialized");
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public void shutdown() {
        tts.shutdown();
    }

}
