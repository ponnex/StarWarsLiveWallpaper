package com.ponnex.livewallpaper.starwars;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.service.wallpaper.WallpaperService;
import android.util.AttributeSet;
import android.view.SurfaceHolder;

import com.ponnex.livewallpaper.starwars.opengl.ParticleSystemRenderer;

/**
 * Created by Ramos on 2/16/2016.
 */
public class StarWarsWallpaperService extends WallpaperService {

    @Override
    public Engine onCreateEngine() {
        return new GLEngine();
    }

    public class GLEngine extends Engine {
        private StarWarsGLSurfaceView starWarsGLSurfaceView;

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            starWarsGLSurfaceView = new StarWarsGLSurfaceView(StarWarsWallpaperService.this);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            if (visible) {
                starWarsGLSurfaceView.onResume();
            } else {
                starWarsGLSurfaceView.onPause();
            }
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            starWarsGLSurfaceView.onDestroy();
        }

        class StarWarsGLSurfaceView extends GLSurfaceView {
            private ActivityManager activityManager;
            private ConfigurationInfo configurationInfo;
            private boolean supportsEs2;
            public StarWarsGLSurfaceView(Context context) {
                super(context);
                if (!isInEditMode()) {
                    activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                    configurationInfo = activityManager.getDeviceConfigurationInfo();
                    supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;
                    if (supportsEs2) {
                        // Request an OpenGL ES 2.0 compatible context.
                        this.setEGLContextClientVersion(2);
                        // Set the renderer to our demo renderer, defined below.
                        ParticleSystemRenderer mRenderer = new ParticleSystemRenderer(this);
                        this.setRenderer(mRenderer);
                        this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
                    } else {
                        if (!isInEditMode()) throw new UnsupportedOperationException();
                    }
                }
            }

            public StarWarsGLSurfaceView(Context context, AttributeSet attrs) {
                super(context, attrs);
                this.setEGLContextClientVersion(2);
                this.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
                this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
                this.setZOrderOnTop(false);
                if (!isInEditMode()) {
                    activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                    configurationInfo = activityManager.getDeviceConfigurationInfo();
                    supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;
                    if (supportsEs2) {
                        // Request an OpenGL ES 2.0 compatible context.
                        this.setEGLContextClientVersion(2);

                        // Set the renderer to our demo renderer, defined below.
                        ParticleSystemRenderer mRenderer = new ParticleSystemRenderer(this);
                        this.setRenderer(mRenderer);
                        this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
                    } else {
                        if (!isInEditMode()) throw new UnsupportedOperationException();
                    }
                }
            }

            @Override
            public SurfaceHolder getHolder() {
                return getSurfaceHolder();
            }

            public void onDestroy() {
                super.onDetachedFromWindow();
            }

        }
    }
}
