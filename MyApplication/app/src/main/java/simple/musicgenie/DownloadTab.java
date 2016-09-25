package simple.musicgenie;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Typeface;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DownloadTab extends TabLayout {

    private Typeface mTypeface;
    private Context context;
    public DownloadTab(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public DownloadTab(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DownloadTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mTypeface = FontManager.getInstance(context).getTypeFace(FontManager.FONT_MATERIAL);
    }

    @Override
    public void addTab(Tab tab) {
        super.addTab(tab);
        ViewGroup mainView = (ViewGroup) getChildAt(0);
        ViewGroup tabView = (ViewGroup) mainView.getChildAt(tab.getPosition());

        int tabChildCount = tabView.getChildCount();
        for (int i = 0; i < tabChildCount; i++) {
            View tabViewChild = tabView.getChildAt(i);
            if (tabViewChild instanceof TextView) {
                ((TextView) tabViewChild).setTypeface(mTypeface,Typeface.BOLD);
                ((TextView) tabViewChild).setHeight(60);
                ((TextView) tabViewChild).setTextSize(TypedValue.COMPLEX_UNIT_SP,60);
                ((TextView) tabViewChild).setTextColor(getResources().getColor(R.color.White));
            }
        }
    }

    public void setUpTab(DownloadTab tab){
        String downloadingTab = " \uE2C4 ";
        String downloadedTab =" \uE149 ";
        addTab(tab.newTab().setText(downloadingTab));
        addTab(tab.newTab().setText(downloadedTab));

    }
}
