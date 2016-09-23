package simple.plusmortproject;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;

/**
 * Created by Ankit on 9/23/2016.
 */
class SView extends ViewGroup {

    public SView(Context context) {
        super(context);
    }

    public SView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }

    @Override
    public int getDescendantFocusability() {
        return super.getDescendantFocusability();
    }

    @Override
    public void setDescendantFocusability(int focusability) {
        super.setDescendantFocusability(focusability);
    }

    @Override
    public void requestChildFocus(View child, View focused) {
        super.requestChildFocus(child, focused);
    }

    @Override
    public void focusableViewAvailable(View v) {
        super.focusableViewAvailable(v);
    }

    @Override
    public boolean showContextMenuForChild(View originalView) {
        return super.showContextMenuForChild(originalView);
    }

    @Override
    public ActionMode startActionModeForChild(View originalView, ActionMode.Callback callback) {
        return super.startActionModeForChild(originalView, callback);
    }

    @Override
    public ActionMode startActionModeForChild(View originalView, ActionMode.Callback callback, int type) {
        return super.startActionModeForChild(originalView, callback, type);
    }

    @Override
    public View focusSearch(View focused, int direction) {
        return super.focusSearch(focused, direction);
    }

    @Override
    public boolean requestChildRectangleOnScreen(View child, Rect rectangle, boolean immediate) {
        return super.requestChildRectangleOnScreen(child, rectangle, immediate);
    }

    @Override
    public boolean requestSendAccessibilityEvent(View child, AccessibilityEvent event) {
        return super.requestSendAccessibilityEvent(child, event);
    }

    @Override
    public boolean onRequestSendAccessibilityEvent(View child, AccessibilityEvent event) {
        return super.onRequestSendAccessibilityEvent(child, event);
    }

    @Override
    public void childHasTransientStateChanged(View child, boolean childHasTransientState) {
        super.childHasTransientStateChanged(child, childHasTransientState);
    }

    @Override
    public boolean hasTransientState() {
        return super.hasTransientState();
    }
}
