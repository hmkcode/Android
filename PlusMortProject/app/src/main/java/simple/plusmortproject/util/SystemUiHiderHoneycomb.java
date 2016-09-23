package simple.plusmortproject.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;


    protected SystemUiHiderHoneycomb(Activity activity, View anchorView, int flags) {
        super(activity, anchorView, flags);

        mShowFlags = View.SYSTEM_UI_FLAG_VISIBLE;
        mHideFlags = View.SYSTEM_UI_FLAG_LOW_PROFILE;
        mTestFlags = View.SYSTEM_UI_FLAG_LOW_PROFILE;

        if ((mFlags & FLAG_FULLSCREEN) != 0) {
            // If the client requested fullscreen, add flags relevant to hiding
            // the status bar. Note that some of these constants are new as of
            // API 16 (Jelly Bean). It is safe to use them, as they are inlined
            // at compile-time and do nothing on pre-Jelly Bean devices.
            mShowFlags |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            mHideFlags |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        if ((mFlags & FLAG_HIDE_NAVIGATION) != 0) {
            // If the client requested hiding navigation, add relevant flags.
            mShowFlags |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            mHideFlags |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            mTestFlags |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setup() {
        mAnchorView.setOnSystemUiVisibilityChangeListener(mSystemUiVisibilityChangeListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hide() {
        mAnchorView.setSystemUiVisibility(mHideFlags);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void show() {
        mAnchorView.setSystemUiVisibility(mShowFlags);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isVisible() {
        return mVisible;
    }

    private View.OnSystemUiVisibilityChangeListener mSystemUiVisibilityChangeListener
            = new View.OnSystemUiVisibilityChangeListener() {
        @Override
        public void onSystemUiVisibilityChange(int vis) {
            // Test against mTestFlags to see if the system UI is visible.
            if ((vis & mTestFlags) != 0) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    // Pre-Jelly Bean, we must manually hide the action bar
                    // and use the old window flags API.
                    mActivity.getActionBar().hide();
                    mActivity.getWindow().setFlags(
                            WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
                }

                // Trigger the registered listener and cache the visibility
                // state.
                mOnVisibilityChangeListener.onVisibilityChange(false);
                mVisible = false;

            } else {
                mAnchorView.setSystemUiVisibility(mShowFlags);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    // Pre-Jelly Bean, we must manually show the action bar
                    // and use the old window flags API.
                    mActivity.getActionBar().show();
                    mActivity.getWindow().setFlags(
                            0,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
                }

                // Trigger the registered listener and cache the visibility
                // state.
                mOnVisibilityChangeListener.onVisibilityChange(true);
                mVisible = true;
            }
        }
    };
}
