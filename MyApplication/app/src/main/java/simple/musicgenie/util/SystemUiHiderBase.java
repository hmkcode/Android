package simple.musicgenie.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

/**
 /**
 * An API 11+ implementation of {@link SystemUiHider}. Uses APIs available in
 * Honeycomb and later (specifically {@link View#setSystemUiVisibility(int)}) to
 * show and hide the system UI.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SystemUiHiderHoneycomb extends SystemUiHiderBase {
    /**
     * Flags for {@link View#setSystemUiVisibility(int)} to use when showing the
     * system UI.
     */
    private int mShowFlags;

    /**
     * Flags for {@link View#setSystemUiVisibility(int)} to use when hiding the
     * system UI.
     */
    private int mHideFlags;

    /**
     * Flags to test against the first parameter in
     * {@link android.view.View.OnSystemUiVisibilityChangeListener#onSystemUiVisibilityChange(int)}
     * to determine the system UI visibility state.
     */
    private int mTestFlags;

    /**
     * Whether or not the system UI is currently visible. This is cached from
     * {@link android.view.View.OnSystemUiVisibilityChangeListener}.
     */
    private boolean mVisible = true;

/**ble;
    }

    @Override
    public void hide() {
        if ((mFlags & FLAG_FULLSCREEN) != 0) {
            mActivity.getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        mOnVisibilityChangeListener.onVisibilityChange(false);
        mVisible = false;
    }

    @Override
    public void show() {
        if ((mFlags & FLAG_FULLSCREEN) != 0) {
            mActivity.getWindow().setFlags(
                    0,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        mOnVisibilityChangeListener.onVisibilityChange(true);
        mVisible = true;
    }
}
