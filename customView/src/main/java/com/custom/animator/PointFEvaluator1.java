package com.custom.animator;

import android.animation.TypeEvaluator;
import android.graphics.PointF;


public class PointFEvaluator1 implements TypeEvaluator<PointF> {
    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {

        float startX = startValue.x;
        float endX = endValue.x;
        float currentX = startX + (endX - startX) * fraction;
        float startY = startValue.y;
        float endY = endValue.y;
        float currentY = startY + (endY - startY) * fraction;
        return new PointF(currentX, currentY);
    }
}
