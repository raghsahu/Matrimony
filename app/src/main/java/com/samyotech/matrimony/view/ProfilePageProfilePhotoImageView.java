package com.samyotech.matrimony.view;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.ImageView.ScaleType;

public class ProfilePageProfilePhotoImageView extends AppCompatImageView {
    private int displayImageSize;
    private float iScale;
    private final Matrix matrix = new Matrix();
    private float redundantXSpace;
    private float redundantYSpace;

    public ProfilePageProfilePhotoImageView(Context context) {
        super(context);
        init();
    }

    public ProfilePageProfilePhotoImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        super.setScaleType(ScaleType.MATRIX);
    }

    public void setScaleType(ScaleType scaleType) {
    }

    protected void onMeasure(int i, int i2) {
        int size = MeasureSpec.getSize(i);
        int i3 = (size * 75) / 100;
        setMeasuredDimension(size, i3);
        if (size != 0 && i3 != 0) {
            Drawable drawable = getDrawable();
            if (drawable != null && drawable.getIntrinsicWidth() != 0 && drawable.getIntrinsicHeight() != 0) {
                int intrinsicWidth = drawable.getIntrinsicWidth();
                int intrinsicHeight = drawable.getIntrinsicHeight();
                this.iScale = ((float) size) / ((float) intrinsicWidth);
                this.redundantYSpace = (((float) i3) - (this.iScale * ((float) intrinsicHeight))) / 2.0f;
                this.redundantXSpace = (((float) size) - (this.iScale * ((float) intrinsicWidth))) / 2.0f;
                size = (int) (this.iScale * ((float) intrinsicHeight));
                if (size != this.displayImageSize) {
                    this.displayImageSize = size;
                }
                resetInitialViewMatrix();
                setImageMatrix(this.matrix);
            }
        }
    }

    private void resetInitialViewMatrix() {
        this.matrix.setScale(this.iScale, this.iScale);
        this.matrix.postTranslate(this.redundantXSpace, 0.0f);
    }
}
