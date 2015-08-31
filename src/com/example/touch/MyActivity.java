package com.example.touch;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.concurrent.TimeUnit;


public class MyActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new Word(this));
    }

    class Word extends View {

        private Paint mPaint = new Paint();
        private Rect mTextBoundRect = new Rect();

        float width, height, centerX, centerY;
        String text = "word";

        float dragY = 0;

        public Word(Context context) {
            super(context);
        }

        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            Log.d("MyTag", "Y: "+centerY);
            //screen sizes (in fact it is just View sizes)
            width = getWidth();
            height = getHeight();

            //set the text on the horizontal center
            centerX = width / 2;

            //set the text on the vertical center (works at the start)
            if (centerY == 0) {
                centerY = height / 2;
            }

            //make a background
            mPaint.setColor(Color.WHITE);
            canvas.drawPaint(mPaint);

            //make the text
            float mTextWidth, mTextHeight;
            mPaint.setColor(Color.BLUE);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setAntiAlias(true);
            mPaint.setTextSize(100);
            //sizes of rectangle that is made by the text
            mPaint.getTextBounds(text, 0, text.length(), mTextBoundRect);
            mTextWidth = mPaint.measureText(text);
            mTextHeight = mTextBoundRect.height();
            //draw the text
            canvas.drawText(text,
                    centerX - (mTextWidth / 2f),
                    centerY + (mTextHeight /2f),
                    mPaint
            );

        }


        @Override
        public boolean onTouchEvent(MotionEvent event) {

            // define Y-coordinate of the Touch-event
            float evY = event.getY();

            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:

                    dragY = evY - centerY; //difference between the Touch-event and the center of the text
                    break;

                case MotionEvent.ACTION_MOVE:

                    centerY = evY - dragY;  //define new center of the text
                    invalidate();           //re-draw the text
                    break;

                case MotionEvent.ACTION_UP:

                    //Log.d("MyTag", "2w: "+width);
                    //Log.d("MyTag", "2h: "+height);

                    if ( (evY - dragY) < (height/4) ) { //upper quoter of the screen "I KNOW"

                        try {

                            while (centerY > 1) {
                                Log.d("MyTag", "upY: "+centerY);
                                centerY-=20;
                                invalidate();
                                TimeUnit.MILLISECONDS.sleep(50);
                            }

                        }catch (Exception e) {
                            e.printStackTrace();
                        }

                        nextWord();

                    } else if ((evY - dragY) > (height*3/4)) { //lower quoter of the screen "I FORGOT"

                        Log.d("MyTag", "downY: "+centerY);
                        nextWord();

                    } else if (Math.abs(centerY - height / 2) < 20) {

                        translateWord ();

                    }

                    centerY = height / 2;       //return the text' center to the center of the screen
                    invalidate();               //re-draw the text
                    break;
            }
            return true;
        }


        private void nextWord () {

            if (text.compareTo("word") == 0) {
                text = "next word";
            } else {
                text = "word";
            }

        }


        private void translateWord () {

            if (text.compareTo("word") == 0) {
                text = "translate";
            } else {
                text = "word";
            }
        }




    }
}

