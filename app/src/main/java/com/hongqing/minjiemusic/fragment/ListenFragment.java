package com.hongqing.minjiemusic.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hongqing.minjiemusic.R;
import com.hongqing.minjiemusic.utils.MediaUtils;
import com.hongqing.minjiemusic.vo.MessageEvent;
import com.hongqing.minjiemusic.vo.MessageEventType;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by 贺红清 on 2017/2/15.
 */

public class ListenFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private ImageButton ib_tool_next;
    private LinearLayout line_show_hide;
    private float mDensity;
    private int mHiddenViewMeasuredHeight;
    private LinearLayout line_localSons;
    private TextView tv_song_count;
    private Button bu_song_list;
    private Button bu_down;
    private Button bu_love;
    private Button bu_recent;

    @Override
    public void onResume() {
        super.onResume();
        //        根据手机的分辨率从 dp 的单位 转成为 px(像素)
        mDensity = getResources().getDisplayMetrics().density;
        mHiddenViewMeasuredHeight = (int) (mDensity * 50 + 0.5);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.listen_fragment_layout, null);
        initView();
        return view;
    }

    private void initView() {
        ib_tool_next = (ImageButton) view.findViewById(R.id.ib_tool_next);
        line_show_hide = (LinearLayout) view.findViewById(R.id.line_show_hide);
        line_localSons = (LinearLayout) view.findViewById(R.id.line_localSongs);
        tv_song_count = (TextView) view.findViewById(R.id.tv_song_Count);

        bu_song_list = (Button) view.findViewById(R.id.bu_song_list);
        bu_down = (Button) view.findViewById(R.id.bu_down);
        bu_love = (Button) view.findViewById(R.id.bu_love);
        bu_recent = (Button) view.findViewById(R.id.bu_recent);

        tv_song_count.setText(MediaUtils.getMp3Count(getContext())+"首");
        initListener();
    }

    private void initListener() {
        ib_tool_next.setOnClickListener(this);
        line_localSons.setOnClickListener(this);
        bu_song_list.setOnClickListener(this);
        bu_down.setOnClickListener(this);
        bu_love.setOnClickListener(this);
        bu_recent.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_tool_next:
                setNextListener();
                break;
            case R.id.line_localSongs:
                //打开本地音乐
                EventBus.getDefault().post(new MessageEvent(MessageEventType.SHOW_LOCAL_SONGS));
                break;
            case R.id.bu_song_list:
                //歌单
                EventBus.getDefault().post(new MessageEvent(MessageEventType.SHOW_SONG_List));
                break;
            case R.id.bu_down:
                //下载
                EventBus.getDefault().post(new MessageEvent(MessageEventType.SHOW_DOWNMANAGER));
                break;
            case R.id.bu_love:
                //收藏
                EventBus.getDefault().post(new MessageEvent(MessageEventType.SHOW_LOVE));
                break;
            case R.id.bu_recent:
                 //最近
                EventBus.getDefault().post(new MessageEvent(MessageEventType.SHOW_RECENT));
                break;
        }
    }

    private void setNextListener() {
        //判断这个组件的状态  GONE  or    VISIBLE
        if (line_show_hide.getVisibility() == View.GONE) {
            animateOpen(line_show_hide);
            animationIvOpen();
        } else {
            animateClose(line_show_hide);
            animationIvClose();
        }
    }

    private void animateOpen(View v) {

        v.setVisibility(View.VISIBLE);
        //参数  开始位置   结束位置
        ValueAnimator animator = createDropAnimator(v, 0,
                mHiddenViewMeasuredHeight);
        animator.start();
    }

    private void animationIvOpen() {
        //参数  起始的角度 旋转的角度 相对于组件x坐标的位置  值 相对于组件Y坐标的位置  值
        RotateAnimation animation = new RotateAnimation(0, 180,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        animation.setFillAfter(true);
        animation.setDuration(100);
        ib_tool_next.startAnimation(animation);
    }

    private void animationIvClose() {
        RotateAnimation animation = new RotateAnimation(180, 0,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        animation.setFillAfter(true);
        animation.setDuration(100);
        ib_tool_next.startAnimation(animation);
    }

    private void animateClose(final View view) {
        //如果组件可见状态则就需要获取现在的高度
        int origHeight = view.getHeight();
        ValueAnimator animator = createDropAnimator(view, origHeight, 0);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
            }
        });
        animator.start();
    }

    //这个方式是定义动画
    private ValueAnimator createDropAnimator(final View v, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);//这是一个可变参数 也可设置为ofFloat类型
        //监听动画的动作变换
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                int value = (int) arg0.getAnimatedValue();
                /**
                 * 重新设置组件的Y坐标
                 * 1:先获取view的LayoutParams
                 *    2:重新设置高度
                 *       3:将修改好的参数设置给view
                 */
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                layoutParams.height = value;
                v.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }
}
