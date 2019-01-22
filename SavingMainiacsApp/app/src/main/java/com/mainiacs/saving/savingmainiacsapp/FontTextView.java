package com.mainiacs.saving.savingmainiacsapp;

/**
 * Created by Zach on 4/30/2017.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class FontTextView extends android.support.v7.widget.AppCompatTextView {
    public static Typeface Plymouth;


    public FontTextView(Context context) {
        super(context);
        if(Plymouth == null) Plymouth = Typeface.createFromAsset(context.getAssets(), "fonts/Plymouth.ttf");
        this.setTypeface(Plymouth);
    }
    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(Plymouth == null) Plymouth = Typeface.createFromAsset(context.getAssets(), "fonts/Plymouth.ttf");
        this.setTypeface(Plymouth);
    }
    public FontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if(Plymouth == null) Plymouth = Typeface.createFromAsset(context.getAssets(), "fonts/Plymouth.ttf");
        this.setTypeface(Plymouth);
    }
}