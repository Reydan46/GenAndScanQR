package rey.genandscanqr;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

class PointsOverlayView extends View
{

    private PointF[] points;
    private Paint paint;

    public PointsOverlayView(Context context) {
        super(context);
        init();
    }

    public PointsOverlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PointsOverlayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.FILL);
    }

    void setPoints(PointF[] points) {
        this.points = points;
        invalidate();
    }

    @Override public void draw(Canvas canvas) {
        super.draw(canvas);
        if (points != null) {
            for (PointF pointF : points) {
                canvas.drawRect(pointF.x-20, pointF.y-20,pointF.x+20, pointF.y+20,paint);
            }
        }
    }
}
