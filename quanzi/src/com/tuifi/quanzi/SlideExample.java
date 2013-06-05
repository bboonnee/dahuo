package com.tuifi.quanzi;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tuifi.quanzi.R;
import com.tuifi.quanzi.util.SampleGuest;

public class SlideExample extends Activity {

    public ViewGroup container1, container2;
    public LinearLayout[] linear;
    public static int linearsize;

    private GestureDetector gestureDetector;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fillper);

        // 监听屏幕动作事件
        SampleGuest gestureListener = new SampleGuest(this);
        gestureDetector = new GestureDetector(gestureListener);

        linearsize =3;
        linear = new LinearLayout[linearsize];
        linear[0] = (LinearLayout) findViewById(R.id.container1);
        linear[1] = (LinearLayout) findViewById(R.id.container2);
        linear[2] = (LinearLayout) findViewById(R.id.container1);
        
    }

    // called automatically, any screen action will Triggered it
    public boolean onTouchEvent(MotionEvent event) {
        if (gestureDetector.onTouchEvent(event))
            return true;
        else
            return false;
    }

}
