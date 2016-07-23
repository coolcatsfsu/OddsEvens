package com.aec12e.opencvtut;
import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;

import org.opencv.android.JavaCameraView;
import org.opencv.core.Size;

import java.util.List;

/**
 * Created by antoniomojics on 7/19/2016.
 */
public class CustomSurfaceView extends JavaCameraView {
    private static final String TAG = "CustomSurfaceView";

    public CustomSurfaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public List<String> EffectList() {
        return mCamera.getParameters().getSupportedColorEffects();
    }

    public boolean EffectSupport() {
        return (mCamera.getParameters().getColorEffect() != null);
    }

    public String Effect() {
        return mCamera.getParameters().getColorEffect();
    }

    public void EffectON(String effectON) {
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setColorEffect(effectON);
        mCamera.setParameters(parameters);
    }

    public Camera.Parameters Parameter() {
        Camera.Parameters parameter = mCamera.getParameters();
        return parameter;
    }

    public void ParameterON(Camera.Parameters parameters) {
        mCamera.setParameters(parameters);
    }

    public List<Camera.Size> ResolutionList() {
        return mCamera.getParameters().getSupportedPreviewSizes();
    }

    public void ResolutionON(Camera.Size resolution) {
        disconnectCamera();
        mMaxHeight = resolution.height;
        mMaxWidth = resolution.width;
        connectCamera(getWidth(), getHeight());
    }

    public Camera.Size Resolution() {
        return mCamera.getParameters().getPreviewSize();
    }
}

