package com.hongqing.minjiemusic.fragment;

import android.accounts.NetworkErrorException;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hongqing.minjiemusic.R;
import com.hongqing.minjiemusic.utils.JsoupUtils;
import com.hongqing.minjiemusic.utils.MediaUtils;
import com.hongqing.minjiemusic.vo.NetMusic;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 贺红清 on 2017/2/15.
 */

public class LookFragment extends BaseFragment {

    private View view;
    private Banner banner;

    private HashMap<String, String> bannerImages;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.look_fragment_layout,null);
        initView();
        return view;

    }

    private void initView() {
        banner = (Banner) view.findViewById(R.id.banner);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
//        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合

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
        //banner设置方法全部调用完毕时最后调用
//
        new JsoupUtils().getBannerImage(getContext(), new JsoupUtils.GetBannerImageListener() {
            @Override
            public void onSuccess(HashMap<String, String> data) {
                System.out.println("------------他被执行了--------------");
                 if (data!=null){
                     Iterator<String> iterator = data.keySet().iterator();
                   List<String> imageUrls=new ArrayList<String>();
                     while (iterator.hasNext()) {
                         imageUrls.add(iterator.next());
                         banner.setImages(imageUrls);
                         System.out.println(imageUrls.toString());
                     }
                     System.out.println(imageUrls.size()+"images  size----------------------");



                 }
            }
            @Override
            public void onError(NetworkErrorException e) {

            }
        });

//        List<String> imas=new ArrayList<>();
//        imas.add("http://img04.sogoucdn.com/app/a/07/bd7ec8a2324ea1ffc1afd0d0c1c47097");
//        banner.setImages(imas);
        banner.start();
    }


}
