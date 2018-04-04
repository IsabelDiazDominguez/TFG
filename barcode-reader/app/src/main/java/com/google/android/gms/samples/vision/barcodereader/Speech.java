package com.google.android.gms.samples.vision.barcodereader;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.Locale;

public class Speech {
    private static TextToSpeech tts;
    private static CharSequence SC_str;
    private static String S_str;

    public static void Talk(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
        S_str = str;
        tts = new TextToSpeech( context, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.UK);
                    tts.setPitch(1.3f);
                    tts.setSpeechRate(1f);
                    tts.setLanguage( new Locale( "spa", "ESP" ) );
                    //   tts.speak(SC_str, TextToSpeech.QUEUE_FLUSH, null,null);
                    tts.speak(S_str, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
    }
}
