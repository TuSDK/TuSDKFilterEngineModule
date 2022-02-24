package org.lasque.tubeautysetting;

import android.content.Context;
import android.opengl.EGLContext;
import android.util.Pair;

import androidx.annotation.Nullable;

import com.tusdk.pulse.Config;
import com.tusdk.pulse.Engine;
import com.tusdk.pulse.filter.Image;
import com.tusdk.pulse.utils.gl.GLContext;

/**
 * TuSDK
 * org.lasque.tubeautysetting
 * tencent-tusdk-txliteavdemo-filterengine
 *
 * @author H.ys
 * @Date 2022/2/17  10:43
 * @Copyright (c) 2020 tusdk.com. All rights reserved.
 */
public class LivePipeMediator {

    private static final String TAG = "LivePipeMediator";


    private static LivePipeMediator INSTANCE = null;

    public static LivePipeMediator getInstance(){
        if (INSTANCE == null){
            synchronized (LivePipeMediator.class){
                if (INSTANCE == null){
                    INSTANCE = new LivePipeMediator();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * RenderPipe
     */
    private RenderPipe mRenderPipe;

    /**
     * 方向传感器
     */
    private SensorHelper mSensorHelper;

    /**
     * 美颜参数管理器
     */
    private BeautyManager mBeautyManager;

    private boolean isReady = false;


    private LivePipeMediator(){
    }

    /**
     * @return 是否可用
     */
    public boolean isReady(){
        return isReady;
    }

    public Pair<Boolean,Integer> requestInit(Context context,@Nullable EGLContext shareContext){
        if (isReady){
            return new Pair<>(false,-1);
        }

        Engine.Config config = new Engine.Config();
        config.eglContext = shareContext;

        Engine engine = Engine.getInstance();
        engine.init(config);

        mRenderPipe = new RenderPipe();
        mRenderPipe.initRenderPipe();

        mSensorHelper = new SensorHelper(context);

        mBeautyManager = new BeautyManager();
        mBeautyManager.requestInit(mRenderPipe);

        isReady = true;

        return new Pair<Boolean,Integer>(true,0);
    }

    public Image process(final int texture, final int width, final int height, final long ts){
        final Image[] output = new Image[1];
        mRenderPipe.getRenderPool().runSync(new Runnable() {
            @Override
            public void run() {
                Image in = new Image(texture,width,height,ts);
                in.setAgree(mSensorHelper.getDeviceAngle());
                in.setMarkSenceEnable(mBeautyManager.checkEnableMarkSence());

                Image out = mBeautyManager.processFrame(in);
                output[0] = out;
            }
        });

        return output[0];
    }

    /**
     * @return 获取美颜属性管理类
     */
    public BeautyManager getBeautyManager(){
        return mBeautyManager;
    }

    public void release(){
        mBeautyManager.release();
        mRenderPipe.release();
        isReady = false;
    }

    public GLContext getGLContext(){
        if (mRenderPipe == null) return null;
        return mRenderPipe.getContext();
    }



}
