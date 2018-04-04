package com.google.android.gms.samples.vision.barcodereader;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by IsaDD on 03/04/2018.
 */

public class TextToSpeech extends Activity implements  TTSListener, android.speech.tts.TextToSpeech.OnInitListener{

        private android.speech.tts.TextToSpeech textToSpeech;

        @Override
        protected void onCreate( Bundle savedInstanceState )
        {
            super.onCreate( savedInstanceState );
            setContentView( R.layout.activity_main );

            //Seleccion idioma
            /*
            final Button btnSpanish = ( Button ) findViewById( R.id.btnSpanish );
            final Button btnEnglish = ( Button ) findViewById( R.id.btnEnglish );
            */

            textToSpeech = new android.speech.tts.TextToSpeech( getApplicationContext(), this );

            // Por defecto
            textToSpeech.setLanguage( new Locale( "spa", "ESP" ) );

            /*
            btnSpanish.setOnClickListener( new View.OnClickListener()
            {
                @Override public void onClick( View v )
                {
                    textToSpeech.setLanguage( new Locale( "spa", "ESP" ) );
                }
            } );

            btnEnglish.setOnClickListener( new View.OnClickListener()
            {
                @Override public void onClick( View v )
                {
                    textToSpeech.setLanguage( Locale.ENGLISH );
                }
            } );
            */
        }

        @Override
        public void onInit( int status )
        {
            if ( status == android.speech.tts.TextToSpeech.LANG_MISSING_DATA | status == android.speech.tts.TextToSpeech.LANG_NOT_SUPPORTED )
            {
                Toast.makeText( this, "ERROR LANG_MISSING_DATA | LANG_NOT_SUPPORTED", Toast.LENGTH_SHORT ).show();
            }
        }

        // Publico para acceder mediante otras clases
        public void speak( String str )
        {
            textToSpeech.speak( str, android.speech.tts.TextToSpeech.QUEUE_FLUSH, null );
            textToSpeech.setSpeechRate( 0.0f );
            textToSpeech.setPitch( 0.0f );
        }

    @Override
    public void pause(long duration) {
        if (textToSpeech != null) {
            textToSpeech.stop();
        }
    }

    @Override
        protected void onDestroy()
        {
            if ( textToSpeech != null )
            {
                textToSpeech.stop();
                textToSpeech.shutdown();
            }
            super.onDestroy();
        }

}
