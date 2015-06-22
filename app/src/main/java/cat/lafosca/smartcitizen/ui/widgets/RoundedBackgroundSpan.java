package cat.lafosca.smartcitizen.ui.widgets;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;

public class RoundedBackgroundSpan extends ReplacementSpan {

    private int corner;
    private int backgroundColor;
    private int textColor;

    //todo : add padding? https://medium.com/@tokudu/android-adding-padding-to-backgroundcolorspan-179ab4fae187

    public RoundedBackgroundSpan(int corner, int backgroundColor, int textColor) {
        this.corner = corner;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }


    @Override
    public  void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint)
    {
        RectF rect = new RectF(x, top, x + measureText(paint, text, start, end) + corner, bottom);
        paint.setColor(backgroundColor);
        canvas.drawRoundRect(rect, corner, corner, paint);
        paint.setColor(textColor);
        canvas.drawText(text, start, end, x + corner/2, y, paint);
    }
    @Override
    public  int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm)
    {
        return Math.round(measureText(paint, text, start, end));
    }

    private float measureText(Paint paint, CharSequence text, int start, int end)
    {
        return paint.measureText(text, start, end);
    }

}
