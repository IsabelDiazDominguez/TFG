package com.google.android.gms.samples.vision.barcodereader;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class BarcodeGraphicInterface extends AppCompatActivity {

    Context mContext;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.barcode_capture);
        mContext = getApplicationContext();


    }

}
