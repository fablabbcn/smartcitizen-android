package com.smartcitizen.commons;

import android.content.Context;
import android.content.Intent;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Created by ferran on 17/06/15.
 */
public class NonUnderlindeClickableSpan extends ClickableSpan {
    private Context context;
    private Intent intent;

    public NonUnderlindeClickableSpan(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
    }

    @Override
    public void onClick(View widget) {
        context.startActivity(intent);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setUnderlineText(false);
    }
}
