package com.hongqing.minjiemusic.utils;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.parsers.ParserConfigurationException;

/**
 * descreption:
 * company: moliying.com
 * Created by vince on 16/7/14.
 */
public class DownloadUtils {

    private static final String BAIDU_LRC = "http://music.baidu.com/search/lrc?key=";
    private static final String BAIDU_LRC_ROOT = "http://music.baidu.com";
    private static final int SUCCESS_LRC = 1;  //下载歌词成功
    private static final int FAILED_LRC = 2;  //下载歌词失败
    private static final int SUCCESS_MP3 = 3;  //下载MP3成功
    private static final int FAILED_MP3 = 4;  //下载MP3失败
    private static final String TAG = "DownloadUtils";

    private static DownloadUtils sInstance;
    private OnDownloadListener mListener;

    private ExecutorService mThreadPool;

    /**
     * 设置回调的监听器对象
     *
     * @param mListener
     * @return
     */
    public DownloadUtils setListener(OnDownloadListener mListener) {
        this.mListener = mListener;
        return this;
    }

    //获取下载工具的实例
    public synchronized static DownloadUtils getInstance() {
        if (sInstance == null) {
            try {
                sInstance = new DownloadUtils();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
        }
        return sInstance;
    }

    private DownloadUtils() throws ParserConfigurationException {
        mThreadPool = Executors.newCachedThreadPool();
        handler = new MyHandler(this);
    }

    private MyHandler handler;

    static class MyHandler extends Handler {
        WeakReference<DownloadUtils> ref;
        public MyHandler(DownloadUtils downloadUtils){
            ref = new WeakReference<>(downloadUtils);
        }
        @Override
        public void handleMessage(Message msg) {
            DownloadUtils downloadUtils = ref.get();
            if (downloadUtils!=null) {
                switch (msg.what) {
                    case SUCCESS_LRC:
                        if (downloadUtils.mListener != null) downloadUtils.mListener.onDownload(msg.obj.toString(), null);
                        break;
                    case FAILED_LRC:
                        if (downloadUtils.mListener != null)
                            downloadUtils.mListener.onDownload(null, new MlyException(msg.obj.toString()));
                        break;
                    case SUCCESS_MP3:
                        if (downloadUtils.mListener != null) downloadUtils.mListener.onDownload(msg.obj.toString(), null);
                        break;
                    case FAILED_MP3:
                        if (downloadUtils.mListener != null)
                            downloadUtils.mListener.onDownload(null, new MlyException(msg.obj.toString()));
                        break;
                    default:
                        break;
                }
            }
        }
    };

    /**
     * 下载MP3音乐的具体方法
     *
     * @param url
     */
    public DownloadUtils downloadMusic(Context context, final String url,final String songName) {
        if (!AppUtils.isNetworkConnected(context)) {
            handler.obtainMessage(FAILED_MP3, "网络不可用").sendToTarget();
            return this;
        }
        final File file = new File(Environment.getExternalStorageDirectory()+Constant.DIR_MLY_MUSIC+"/"+songName+".mp3");
        if(file.exists()){
            handler.obtainMessage(FAILED_MP3, "文件已存在").sendToTarget();
            return this;
        }
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                new HttpUtils().get(url, new HttpUtils.RequestListener() {
                    @Override
                    public void response(byte[] bytes) {
                        if(bytes!=null) {
                            try {
                                PrintStream out = new PrintStream(new FileOutputStream(file));
                                out.write(bytes);
                                out.close();
                                handler.obtainMessage(SUCCESS_MP3,file.getPath()).sendToTarget();
                            } catch (IOException e) {
                                e.printStackTrace();
                                handler.obtainMessage(FAILED_MP3,"").sendToTarget();
                            }
                        }else{
                            handler.obtainMessage(FAILED_MP3).sendToTarget();
                        }
                    }
                });
            }
        });
        return this;
    }


    //下载歌词的具体方法
    public DownloadUtils downloadLRC(Context context, final String musicName, final String singer) {
        if (!AppUtils.isNetworkConnected(context)) {
            handler.obtainMessage(FAILED_LRC, "网络不可用").sendToTarget();
            return this;
        }
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                final String url = BAIDU_LRC +"/"+musicName + "-" + singer;
                Log.i(TAG, "run: --");
                new HttpUtils().get(url, new HttpUtils.RequestListener() {
                    @Override
                    public void response(byte[] bytes) {
                        String html = new String(bytes);
                        try {
                            parseLrcFile(html, musicName, singer);
                        } catch (IOException e) {
                            e.printStackTrace();
                            handler.obtainMessage(FAILED_LRC, "没有找到合适的歌词").sendToTarget();
                        }
                    }
                });
            }
        });

        return this;
    }

    //解析出歌词的URL文
    private void parseLrcFile(String html, String musicName, String singer) throws IOException {
        Document doc = Jsoup.parse(html);
        //获取第一个找到的歌词
        Elements elements = doc.select("div.lrc-content");
        if (elements != null && elements.size() > 0) {
            Element element = elements.get(0);
            //从 a标签中获取class属性以down-lrc-btn开头的class属性
            String data = element.select("a[class^=down-lrc-btn]").attr("class");
            data = data.substring(data.indexOf("/"), data.lastIndexOf("'"));
            downloadLrcFile(BAIDU_LRC_ROOT + data, musicName, singer);
//            Log.d(TAG, "run: --" + data);
        } else {
//            Log.d(TAG, "run: --找不到歌词");
            throw new IOException("找不到歌词");
        }
    }

    //下载歌词文件
    private void downloadLrcFile(String url, final String musicName, final String singer) {
        new HttpUtils().get(url, new HttpUtils.RequestListener() {
            @Override
            public void response(byte[] bytes) {

                File lrcDirFile = new File(Environment.getExternalStorageDirectory() + Constant.DIR_LRC);
                if (!lrcDirFile.exists()) {
                    lrcDirFile.mkdirs();
                }
                String target = lrcDirFile + "/" +musicName + "-" + singer + ".lrc";
//                Log.i(TAG, "response: " + target);
                try {
                    PrintStream ps = new PrintStream(new File(target));
                    ps.write(bytes, 0, bytes.length);
                    ps.close();
                    handler.obtainMessage(SUCCESS_LRC, target).sendToTarget();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
//                    Log.d(TAG, "run: --下载歌词失败");
                }

            }
        });
    }

    /**
     * 自定义下载事件监听器
     */
    public interface OnDownloadListener {
        public void onDownload(String result, MlyException e);
    }
}
