package simple.tierscrollview.util;

import android.app.Activity;
import android.os.Build;
import android.view.View;
public abstract class SystemUiHider {

    public static final int FLAG_LAYOUT_IN_SCREEN_OLDER_DEVICES = 0x1;
public static final int FLAG_FULLSCREEN = 0x2;

    public static final int FLAG_HIDE_NAVIGATION = FLAG_FULLSCREEN | 0x4;
    protected Activity mActivity;

    /**
     * The view on which {@link View#setSystemUiVisibility(int)} will be called.
     */
    protected View mAnchorView;

    /**
     * The current UI hider flags.
     *
     * @see #FLAG_FULLSCREEN
     * @see #FLAG_HIDE_NAVIGATION
     * @see #FLAG_LAYOUT_IN_SCREEN_OLDER_DEVICES
     */
    protected int mFlags;

    /**
     * The current visibility callback.
     */
    protected OnVisibilityChangeListener mOnVisibilityChangeListener = sDummyListener;

    public static SystemUiHider getInstance(Activity activity, View anchorView, int flags) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return new SystemUiHiderHoneycomb(activity, anchorView, flags);
        } else {
            return new SystemUiHiderBase(activity, anchorView, flags);
        }
    }

    protected SystemUiHider(Activity activity, View anchorView, int flags) {
        mActivity = activity;
        mAnchorView = anchorView;
        mFlags = flags;
    }

    /**
     * Sets up the system UI hider. Should be called from
     * {@link Activity#onCreate}.
     */
    public abstract void setup();

    /**
     * Returns whether or not the system UI is visible.
     */
    public abstract boolean isVisible();

    /**
     * Hide the system UI.
     */
    public abstract void hide();

    /**
     * Show the system UI.
     */
    public abstract void show();

    /**
     * Toggle the visibility of the system UI.
     */
    public void toggle() {
        if (isVisible()) {
            hide();
        } else {
            show();
        }
    }

    /**
     * Registers a callback, to be triggered when the system UI visibility
     * changes.
     */
    public void setOnVisibilityChangeListener(OnVisibilityChangeListener listener) {
        if (listener == null) {
            listener = sDummyListener;
        }

        mOnVisibilityChangeListener = listener;
    }

    /**
     * A dummy no-op callback for use when there is no other listener set.
     */
    private static OnVisibilityChangeListener sDummyListener = new OnVisibilityChangeListener() {
        @Override
        public void onVisibilityChange(boolean visible) {
        }
    };

    /**
     * A callback interface used to listen for system UI visibility changes.
     */
    public interface OnVisibilityChangeListener {
        /**
         * Called when the system UI visibility has changed.
         *
         * @param visible True if the system UI is visible.
         */
        public void onVisibilityChange(boolean visible);
    }
}
