/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.android.gms.samples.vision.barcodereader;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;


import android.util.Log;
import android.view.View;

import com.google.android.gms.samples.vision.barcodereader.ui.camera.GraphicOverlay;
import com.google.android.gms.vision.barcode.Barcode;



/**
 * Graphic instance for rendering barcode position, size, and ID within an associated graphic
 * overlay view.
 */
public class BarcodeGraphic extends GraphicOverlay.Graphic {

    private int mId;


    private static final int COLOR_CHOICES[] = {
            Color.BLUE,
            Color.CYAN,
            Color.GREEN
    };

    private static int mCurrentColorIndex = 0;

    private Paint mRectPaint;
    private Paint mTextPaint;
    private volatile Barcode mBarcode;

    BarcodeGraphic(GraphicOverlay overlay) {
        super(overlay);

        mCurrentColorIndex = (mCurrentColorIndex + 1) % COLOR_CHOICES.length;
        final int selectedColor = COLOR_CHOICES[mCurrentColorIndex];

        mRectPaint = new Paint();
        mRectPaint.setColor(selectedColor);
        mRectPaint.setStyle(Paint.Style.STROKE);
        mRectPaint.setStrokeWidth(4.0f);

        mTextPaint = new Paint();
        mTextPaint.setColor(selectedColor);
        mTextPaint.setTextSize(36.0f);
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public Barcode getBarcode() {
        return mBarcode;
    }

    /**
     * Updates the barcode instance from the detection of the most recent frame.  Invalidates the
     * relevant portions of the overlay to trigger a redraw.
     */
    void updateItem(Barcode barcode) {
        mBarcode = barcode;
        postInvalidate();
    }

    /**
     * Draws the barcode annotations for position, size, and raw value on the supplied canvas.
     */
    @Override
    public void draw(Canvas canvas) {
        Barcode barcode = mBarcode;
        if (barcode == null) {
            return;
        }

        // Draws the bounding box around the barcode.
        RectF rect = new RectF(barcode.getBoundingBox());

        float Left = rect.left;
        float Right = rect.right;
        float Top = rect.top;
        float Bottom = rect.bottom;

        rect.left = translateX(rect.left);
        rect.top = translateY(rect.top);
        rect.right = translateX(rect.right);
        rect.bottom = translateY(rect.bottom);
        canvas.drawRect(rect, mRectPaint);


        Log.d("DRAW_L", String.valueOf(Left) );
        Log.d("DRAW_R" , String.valueOf(Right));
        Log.d("DRAW_T" , String.valueOf(Top));
        Log.d("DRAW_B" , String.valueOf(Bottom));

        float rl = ( Right - Left );
        float tb = (Bottom - Top);
        float medium_value = ( rl + tb ) / 2;

        Log.d("Medidas_rl" , String.valueOf(rl));
        Log.d("Medidas_tb", String.valueOf(tb));
        Log.d("Medidas_m", String.valueOf(medium_value));

        double sizeQR= 11.60;
        int dpi = 240;

        //Calc
        Log.d("PIXELS", String.valueOf(medium_value));
        double size_distance = (medium_value / dpi ) * 2.54;

        //QR 15*15

        String text = "";

        if (size_distance < 1 ) {
            text = "About 3 meters ";
        }
        if (size_distance >1 && size_distance < 2) {
            text = "About 2 meters ";
        }
        if (size_distance >= 2) {
            text="Less 1 meter ";
        }


        //text = "Size " + String.valueOf(size_distance);

        if (rect.top < 400) {
            text += "\n UP ";
        }else {text += "\n DOWN "; }

        if (rect.right<300) {
            text += "\n RIGHT ";
        } else { text += "\n LEFT ";}



        // Draws a label at the bottom of the barcode indicate the barcode value that was detected.
        Log.d("RESULT", barcode.rawValue);
        //canvas.drawText(barcode.rawValue, rect.left, rect.bottom, mTextPaint);
        canvas.drawText(text, rect.left, rect.bottom, mTextPaint);

    }

}

