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
