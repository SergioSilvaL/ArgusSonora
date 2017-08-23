package com.tecnologiasintech.argussonora.domain;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;

import com.github.gcacace.signaturepad.views.SignaturePad;

import java.io.ByteArrayOutputStream;

/**
 * Created by sergiosilva on 8/23/17.
 */

public class costumSignaturePad extends SignaturePad {
    public costumSignaturePad(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public byte[] getDataFromSignaturePadAsBytes(){

        //Get the data from Signature Pad as bytes
        setDrawingCacheEnabled(true);
        buildDrawingCache();

        Bitmap signaturePadDrawingCache = getDrawingCache();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        signaturePadDrawingCache.compress(Bitmap.CompressFormat.JPEG,0,baos);

        return baos.toByteArray();
    }
}
