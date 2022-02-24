package org.lasque.tubeautysetting;

import com.tusdk.pulse.DispatchQueue;
import com.tusdk.pulse.Engine;
import com.tusdk.pulse.utils.gl.GLContext;

/**
 * TuSDK
 * org.lasque.tubeautysetting
 * android-ec-demo
 *
 * @author H.ys
 * @Date 2021/12/21  11:33
 * @Copyright (c) 2020 tusdk.com. All rights reserved.
 */
public class RenderPipe{
    private GLContext mGLCtx;

    private DispatchQueue mRenderPool;

    private boolean isInit = false;

    public boolean initRenderPipe(){
        if (mRenderPool != null) return false;

        mRenderPool = new DispatchQueue();
        mRenderPool.runSync(new Runnable() {
            @Override
            public void run() {
                mGLCtx = new GLContext();
                mGLCtx.createForRender(Engine.getInstance().getMainGLContext().getEGLContext());
                mGLCtx.makeCurrent();
            }
        });
        return isInit;
    }

    public DispatchQueue getRenderPool(){
        return mRenderPool;
    }

    public GLContext getContext(){
        return mGLCtx;
    }

    public void release(){
        mRenderPool.runSync(new Runnable() {
            @Override
            public void run() {
                mGLCtx.unMakeCurrent();
                mGLCtx.destroy();
            }
        });
    }
}
