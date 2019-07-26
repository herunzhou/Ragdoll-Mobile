package com.example.ragdoll_mobile;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.Paint;
import java.util.Vector;


public class DrawingCanvas extends View {
    private Vector<Sprite> sprites = new Vector<Sprite>(); // All sprites we're managing
    private Sprite interactiveSprite = null; // Sprite with which user is interacting

    class MyOnTouchListener implements View.OnTouchListener {
        public boolean onTouch(View v, MotionEvent e) {
            switch(e.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // touch down code
                    Log.d("herunR: ", "Pressed Position: (" + e.getX() + "," + e.getY() + ")");
                    handleTouchPress(e);
                    break;

                case MotionEvent.ACTION_MOVE:
                    // touch move code
                    Log.d("herun: ", "Action Move Activated");
                    handleDragEvent(e);
                    break;

                case MotionEvent.ACTION_UP:
                    // touch up code
                    Log.d("herun: ", "Action Up Activated");
                    handleReleaseEvent(e);
                    break;
            }
            return true;
        }

    }

    public DrawingCanvas(Context context) {
        super(context);
        this.setOnTouchListener(new MyOnTouchListener());
    }

    public void handleTouchPress(MotionEvent e) {
        for (Sprite sprite : sprites) {
            interactiveSprite = sprite.getSpriteHit(e);
            if (interactiveSprite != null) {
                interactiveSprite.handleTouchEvent(e);
                break;
            }
        }
    }

    public void handleDragEvent(MotionEvent e) {
        if (interactiveSprite != null && !interactiveSprite.oval) {
            interactiveSprite.handleDragEvent(e);
            this.invalidate();
        } else if (interactiveSprite != null  && interactiveSprite.oval) {
            Log.d("herunR: ", "Rotation Called");
            interactiveSprite.handleRotationEvent(e);
            this.invalidate();
        }
    }

    public void handleReleaseEvent(MotionEvent e) {
        if (interactiveSprite != null) {
            interactiveSprite.handleMouseUp(e);
            this.invalidate();
        }
        interactiveSprite = null;
    }


    public void addSprite(Sprite s) {
        sprites.add(s);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        for (Sprite sprite : sprites) {
            sprite.draw(canvas);
        }
    }

}
