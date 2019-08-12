package com.bignerdranch.android.draganddraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class BoxDrawingView extends View {
    private static final String TAG = "BoxDrawingView";

    private Box mCurrentBox;
    private List<Box> mBoxes = new ArrayList<>();

    private Paint mBoxPaint;
    private Paint mBackgroundPaint;

    public BoxDrawingView(Context context){
        super(context,null);
    }

    public BoxDrawingView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);

        mBoxPaint = new Paint();
        mBoxPaint.setColor(0x22ff0000);

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(0xfff8efe0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PointF current = new PointF(event.getX(),event.getY());
        String action = "";
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                action = "Action Down";
                mCurrentBox = new Box(current);
                mBoxes.add(mCurrentBox);
                break;
                case MotionEvent.ACTION_MOVE:
                    action = "Action Move";
                    if(mCurrentBox != null){
                        mCurrentBox.setCurrent(current);
                        invalidate();
                    }
                    break;
                    case MotionEvent.ACTION_UP:
                        action = "Action Up";
                        mCurrentBox = null;
                        break;
                        case MotionEvent.ACTION_CANCEL:
                            action = "Action Cancel";
                            mCurrentBox = null;
                            break;
        }
        Log.i(TAG,action + " at x = " + current.x + " and at y = " + current.y);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPaint(mBackgroundPaint);

        for(Box box: mBoxes){
            float left = Math.min(box.getOrigin().x,box.getCurrent().x);
            float right = Math.max(box.getOrigin().x,box.getOrigin().y);
            float bottom = Math.min(box.getOrigin().y,box.getCurrent().y);
            float top = Math.max(box.getOrigin().y,box.getCurrent().y);

            canvas.drawRect(left,top,right,bottom,mBoxPaint);
        }
    }
}
