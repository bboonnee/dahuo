package com.tuifi.quanzi.util;

import android.util.Log;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.tuifi.quanzi.R;
import com.tuifi.quanzi.SlideExample;

public class SampleGuest implements OnGestureListener {

    SlideExample se;

    private static final int SWIPE_MAX_OFF_PATH = 100;

    private static final int SWIPE_MIN_DISTANCE = 100;

    private static final int SWIPE_THRESHOLD_VELOCITY = 100;
    
    private static int current = 0;

    public SampleGuest(SlideExample se) {
        this.se = se;
    }

	// �û��ᴥ����������1��MotionEvent ACTION_DOWN����
    public boolean onDown(MotionEvent e) {
        Log.d("TAG", "[onDown]");
        return true;
    }

    // �û����´������������ƶ����ɿ�,��1��MotionEvent ACTION_DOWN,
    // ���ACTION_MOVE, 1��ACTION_UP����
    // e1����1��ACTION_DOWN MotionEvent
    // e2�����һ��ACTION_MOVE MotionEvent
    // velocityX��X���ϵ��ƶ��ٶȣ�����/��
    // velocityY��Y���ϵ��ƶ��ٶȣ�����/��
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
            float velocityY) {
        if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
            return false;

        if ((e1.getX() - e2.getX()) > SWIPE_MIN_DISTANCE
                && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
        	if(current<se.linearsize-1)
        	{
            se.linear[current].setAnimation(AnimationUtils.loadAnimation(se,
                    R.anim.push_left_out));
            se.linear[current+1].setVisibility(View.VISIBLE);
            se.linear[current+1].setAnimation(AnimationUtils.loadAnimation(se,
                    R.anim.push_right_in));
            se.linear[current].setVisibility(View.GONE);
            current++;
        	}

        } else if ((e2.getX() - e1.getX()) > SWIPE_MIN_DISTANCE
                && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
        	if(current>0)
        	{
            se.linear[current-1].setVisibility(View.VISIBLE);
            se.linear[current-1].setAnimation(AnimationUtils.loadAnimation(se,
                    R.anim.push_left_in));
            se.linear[current].setAnimation(AnimationUtils.loadAnimation(se,
                    R.anim.push_right_out));
            se.linear[current].setVisibility(View.GONE);
            current--;
        	}
        }
        return true;
    }

    // �û��������������ɶ��MotionEvent ACTION_DOWN����
    public void onLongPress(MotionEvent e) {
        Log.d("TAG", "[onLongPress]");
    }

    // �û����´����������϶�����1��MotionEvent ACTION_DOWN, ���ACTION_MOVE����
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
            float distanceY) {
        Log.d("TAG", "[onScroll]");
        return true;
    }

    // �û��ᴥ����������δ�ɿ����϶�����һ��1��MotionEvent ACTION_DOWN����
    // ע���onDown()������ǿ������û���ɿ������϶���״̬
    public void onShowPress(MotionEvent e) {
        Log.d("TAG", "[onShowPress]");

    }

    // �û����ᴥ���������ɿ�����һ��1��MotionEvent ACTION_UP����
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d("TAG", "[onSingleTapUp]");
        return true;
    }

}
