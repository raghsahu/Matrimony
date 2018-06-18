package com.samyotech.matrimony.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.EditText;

@SuppressLint("AppCompatCustomView")
public class ExtendedEditText extends EditText {
    TagDrawable left = new TagDrawable();
    Rect line0bounds = new Rect();
    float mFontHeight = getTextSize();
    int mLine0Baseline;
    String mSuffix = "";
    TextPaint mTextPaint = new TextPaint();

    public class TagDrawable extends Drawable {
        public String text = "";

        public void setText(String s) {
            this.text = s;
            setBounds(0, 0, getIntrinsicWidth(), getIntrinsicHeight());
            invalidateSelf();
        }

        public void draw(Canvas canvas) {
            canvas.drawText(this.text, 0.0f, (float) (ExtendedEditText.this.mLine0Baseline + canvas.getClipBounds().top), ExtendedEditText.this.mTextPaint);
        }

        public void setAlpha(int i) {
        }

        public void setColorFilter(ColorFilter colorFilter) {
        }

        public int getOpacity() {
            return 1;
        }

        public int getIntrinsicHeight() {
            return (int) ExtendedEditText.this.mFontHeight;
        }

        public int getIntrinsicWidth() {
            return (int) ExtendedEditText.this.mTextPaint.measureText(this.text);
        }
    }

    public ExtendedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
        this.mTextPaint.setColor(getCurrentTextColor());
        this.mTextPaint.setTextSize(this.mFontHeight);
        this.mTextPaint.setTextAlign(Align.LEFT);
        setCompoundDrawablesWithIntrinsicBounds(this.left, null, null, null);
    }

    public void setTypeface(Typeface typeface) {
        super.setTypeface(typeface);
        if (this.mTextPaint != null) {
            this.mTextPaint.setTypeface(typeface);
        }
        postInvalidate();
    }

    public void setPrefix(String s) {
        this.left.setText(s);
        setCompoundDrawablesWithIntrinsicBounds(this.left, null, null, null);
    }

    public void setSuffix(String s) {
        this.mSuffix = s;
        setCompoundDrawablesWithIntrinsicBounds(this.left, null, null, null);
    }

    public void onDraw(@NonNull Canvas c) {
        this.mLine0Baseline = getLineBounds(0, this.line0bounds);
        super.onDraw(c);
        c.drawText(this.mSuffix, (float) (((int) this.mTextPaint.measureText(this.left.text + getText().toString())) + getPaddingLeft()), (float) this.line0bounds.bottom, this.mTextPaint);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("Ubuntu-Medium.ttf", context);
        setTypeface(customFont);
    }

}
