package com.hongqing.minjiemusic.fragment;

import android.accounts.NetworkErrorException;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hongqing.minjiemusic.R;
import com.hongqing.minjiemusic.utils.GlideImageLoader;
import com.hongqing.minjiemusic.utils.JsoupUtils;
import com.hongqing.minjiemusic.utils.MediaUtils;
import com.hongqing.minjiemusic.vo.MessageEvent;
import com.hongqing.minjiemusic.vo.MessageEventType;
import com.hongqing.minjiemusic.vo.NetMusic;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 贺红清 on 2017/2/15.
 */

public class LookFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private Banner banner;

    private HashMap<String, String> bannerImages;
    private LinearLayout linearLayout_gtb_look;
    private LinearLayout linearLayout_ndb_look;
    private LinearLayout linearLayout_lxb_look;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.look_fragment_layout, null);
        initView();
        return view;

    }

    private void initView() {
        banner = (Banner) view.findViewById(R.id.banner);
        linearLayout_ndb_look = (LinearLayout) view.findViewById(R.id.linearLayout_ndb_look);
        linearLayout_gtb_look = (LinearLayout) view.findViewById(R.id.linearLayout_gtb_look);
        linearLayout_lxb_look = (LinearLayout) view.findViewById(R.id.linearLayout_lxb_look);
        linearLayout_ndb_look.setOnClickListener(this);
        linearLayout_gtb_look.setOnClickListener(this);
        linearLayout_lxb_look.setOnClickListener(this);
        setBanner();


    }

    private void setBanner() {
        new JsoupUtils().getBannerImage(getContext(), new JsoupUtils.GetBannerImageListener() {
            @Override
            public void onSuccess(HashMap<String, String> data) {
                if (data != null) {
                    bannerImages = data;
                    Iterator<String> iterator = bannerImages.keySet().iterator();
                    List<String> imageUrls = new ArrayList<>();
                    while (iterator.hasNext()) {
                        imageUrls.add(iterator.next());
                    }
                    //设置banner样式
                    banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                    banner.setImages(imageUrls);
                    //设置banner动画效果
                    banner.setBannerAnimation(Transformer.DepthPage);
                    //设置标题集合（当banner样式有显示title时）
//        banner.setBannerTitles(titles);
                    //设置自动轮播，默认为true
                    banner.isAutoPlay(true);
                    //设置轮播时间
                    banner.setDelayTime(1500);
                    //设置指示器位置（当banner模式中有指示器时）
                    banner.setIndicatorGravity(BannerConfig.CENTER);
                    //设置图片加载器
                    banner.setImageLoader(new GlideImageLoader());
                    //banner设置方法全部调用完毕时最后调用
                    banner.start();
                }
            }

            @Override
            public void onError(NetworkErrorException e) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linearLayout_ndb_look:
                EventBus.getDefault().post(new MessageEvent(MessageEventType.SHOW_NDB_FRAGMENT));
                break;
            case R.id.linearLayout_gtb_look:
                EventBus.getDefault().post(new MessageEvent(MessageEventType.SHOW_GTB_FRAGMENT));
                break;
            case R.id.linearLayout_lxb_look:
                EventBus.getDefault().post(new MessageEvent(MessageEventType.SHOW_LXB_FRAGMENT));
                break;
        }
    }
}
