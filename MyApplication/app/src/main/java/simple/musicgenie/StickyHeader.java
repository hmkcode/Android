package simple.musicgenie;

import android.widget.AbsListView;
import android.widget.FrameLayout;

public class StickyHeader extends FrameLayout{
    private static final String TAG = "StickyHeaderListView";
    protected boolean mChildViewsCreated = false;
    protected boolean mDoHeaderReset = false;
    protected Context mContext = null;
    protected Adapter mAdapter = null;
    protected HeaderIndexer mIndexer = null;
    protected View mStickyHeader = null;
    protected View mDummyHeader = null; // A invisible header used when a section has no header
    protected ListView mListView = null;
    protected ListView.OnScrollListener mListener = null;
    private int mSeparatorWidth;
    private View mSeparatorView;
    // This code is needed only if dataset changes do not force a call to OnScroll
    // protected DataSetObserver mListDataObserver = null;
    protected int mCurrentSectionPos = -1; // Position of section that has its header on the
    // top of the view
    protected int mNextSectionPosition = -1; // Position of next section's header
    protected int mListViewHeadersCount = 0;
    /**
     * Interface that must be implemented by the ListView adapter to provide headers locations
     * and number of items under each header.
     *
     */
    public interface HeaderIndexer {
        /**
         * Calculates the position of the header of a specific item in the adapter's data set.
         * For example: Assuming you have a list with albums and songs names:
         * Album A, song 1, song 2, ...., song 10, Album B, song 1, ..., song 7. A call to
         * this method with the position of song 5 in Album B, should return  the position
         * of Album B.
         * @param position - Position of the item in the ListView dataset
         * @return Position of header. -1 if the is no header
         */
        int getHeaderPositionFromItemPosition(int position);
        /**
         * Calculates the number of items in the section defined by the header (not including
         * the header).
         * For example: A list with albums and songs, the method should return
         * the number of songs names (without the album name).
         *
         * @param headerPosition - the value returned by 'getHeaderPositionFromItemPosition'
         * @return Number of items. -1 on error.
         */
        int getHeaderItemsNumber(int headerPosition);
    }
    /**
     * Sets the adapter to be used by the class to get views of headers
     *
     * @param adapter - The adapter.
     */
    public void setAdapter(Adapter adapter) {
        // This code is needed only if dataset changes do not force a call to
        // OnScroll
        // if (mAdapter != null && mListDataObserver != null) {
        // mAdapter.unregisterDataSetObserver(mListDataObserver);
        // }
        if (adapter != null) {
            mAdapter = adapter;
            // This code is needed only if dataset changes do not force a call
            // to OnScroll
            // mAdapter.registerDataSetObserver(mListDataObserver);
        }
    }
    /**
     * Sets the indexer object (that implements the HeaderIndexer interface).
     *
     * @param indexer - The indexer.
     */
    public void setIndexer(HeaderIndexer indexer) {
        mIndexer = indexer;
    }
    /**
     * Sets the list view that is displayed
     * @param lv - The list view.
     */
    public void setListView(ListView lv) {
        mListView = lv;
        mListView.setOnScrollListener(this);
        mListViewHeadersCount = mListView.getHeaderViewsCount();
    }
    /**
     * Sets an external OnScroll listener. Since the StickyHeaderListView sets
     * itself as the scroll events listener of the listview, this method allows
     * the user to register another listener that will be called after this
     * class listener is called.
     *
     * @param listener - The external listener.
     */
    public void setOnScrollListener(ListView.OnScrollListener listener) {
        mListener = listener;
    }
    // This code is needed only if dataset changes do not force a call to OnScroll
    // protected void createDataListener() {
    //    mListDataObserver = new DataSetObserver() {
    //        @Override
    //        public void onChanged() {
    //            onDataChanged();
    //        }
    //    };
    // }
    /**
     * Constructor
     *
     * @param context - application context.
     * @param attrs - layout attributes.
     */
    public StickyHeaderListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        // This code is needed only if dataset changes do not force a call to OnScroll
        // createDataListener();
    }
    /**
     * Scroll status changes listener
     *
     * @param view - the scrolled view
     * @param scrollState - new scroll state.
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mListener != null) {
            mListener.onScrollStateChanged(view, scrollState);
        }
    }
    /**
     * Scroll events listener
     *
     * @param view - the scrolled view
     * @param firstVisibleItem - the index (in the list's adapter) of the top
     *            visible item.
     * @param visibleItemCount - the number of visible items in the list
     * @param totalItemCount - the total number items in the list
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {
        updateStickyHeader(firstVisibleItem);
        if (mListener != null) {
            mListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }
    /**
     * Sets a separator below the sticky header, which will be visible while the sticky header
     * is not scrolling up.
     * @param color - color of separator
     * @param width - width in pixels of separator
     */
    public void setHeaderSeparator(int color, int width) {
        mSeparatorView = new View(mContext);
        ViewGroup.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                width, Gravity.TOP);
        mSeparatorView.setLayoutParams(params);
        mSeparatorView.setBackgroundColor(color);
        mSeparatorWidth = width;
        this.addView(mSeparatorView);
    }
    protected void updateStickyHeader(int firstVisibleItem) {
        // Try to make sure we have an adapter to work with (may not succeed).
        if (mAdapter == null && mListView != null) {
            setAdapter(mListView.getAdapter());
        }
        firstVisibleItem -= mListViewHeadersCount;
        if (mAdapter != null && mIndexer != null && mDoHeaderReset) {
            // Get the section header position
            int sectionSize = 0;
            int sectionPos = mIndexer.getHeaderPositionFromItemPosition(firstVisibleItem);
            // New section - set it in the header view
            boolean newView = false;
            if (sectionPos != mCurrentSectionPos) {
                // No header for current position , use the dummy invisible one, hide the separator
                if (sectionPos == -1) {
                    sectionSize = 0;
                    this.removeView(mStickyHeader);
                    mStickyHeader = mDummyHeader;
                    if (mSeparatorView != null) {
                        mSeparatorView.setVisibility(View.GONE);
                    }
                    newView = true;
                } else {
                    // Create a copy of the header view to show on top
                    sectionSize = mIndexer.getHeaderItemsNumber(sectionPos);
                    View v = mAdapter.getView(sectionPos + mListViewHeadersCount, null, mListView);
                    v.measure(MeasureSpec.makeMeasureSpec(mListView.getWidth(),
                            MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(mListView.getHeight(),
                            MeasureSpec.AT_MOST));
                    this.removeView(mStickyHeader);
                    mStickyHeader = v;
                    newView = true;
                }
                mCurrentSectionPos = sectionPos;
                mNextSectionPosition = sectionSize + sectionPos + 1;
            }
            // Do transitions
            // If position of bottom of last item in a section is smaller than the height of the
            // sticky header - shift drawable of header.
            if (mStickyHeader != null) {
                int sectionLastItemPosition =  mNextSectionPosition - firstVisibleItem - 1;
                int stickyHeaderHeight = mStickyHeader.getHeight();
                if (stickyHeaderHeight == 0) {
                    stickyHeaderHeight = mStickyHeader.getMeasuredHeight();
                }
                View SectionLastView = mListView.getChildAt(sectionLastItemPosition);
                if (SectionLastView != null && SectionLastView.getBottom() <= stickyHeaderHeight) {
                    int lastViewBottom = SectionLastView.getBottom();
                    mStickyHeader.setTranslationY(lastViewBottom - stickyHeaderHeight);
                    if (mSeparatorView != null) {
                        mSeparatorView.setVisibility(View.GONE);
                    }
                } else if (stickyHeaderHeight != 0) {
                    mStickyHeader.setTranslationY(0);
                    if (mSeparatorView != null && !mStickyHeader.equals(mDummyHeader)) {
                        mSeparatorView.setVisibility(View.VISIBLE);
                    }
                }
                if (newView) {
                    mStickyHeader.setVisibility(View.INVISIBLE);
                    this.addView(mStickyHeader);
                    if (mSeparatorView != null && !mStickyHeader.equals(mDummyHeader)){
                        FrameLayout.LayoutParams params =
                                new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                                        mSeparatorWidth);
                        params.setMargins(0, mStickyHeader.getMeasuredHeight(), 0, 0);
                        mSeparatorView.setLayoutParams(params);
                        mSeparatorView.setVisibility(View.VISIBLE);
                    }
                    mStickyHeader.setVisibility(View.VISIBLE);
                }
            }
        }
    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!mChildViewsCreated) {
            setChildViews();
        }
        mDoHeaderReset = true;
    }
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!mChildViewsCreated) {
            setChildViews();
        }
        mDoHeaderReset = true;
    }
    // Resets the sticky header when the adapter data set was changed
    // This code is needed only if dataset changes do not force a call to OnScroll
    // protected void onDataChanged() {
    // Should do a call to updateStickyHeader if needed
    // }
    private void setChildViews() {
        // Find a child ListView (if any)
        int iChildNum = getChildCount();
        for (int i = 0; i < iChildNum; i++) {
            Object v = getChildAt(i);
            if (v instanceof ListView) {
                setListView((ListView) v);
            }
        }
        // No child ListView - add one
        if (mListView == null) {
            setListView(new ListView(mContext));
        }
        // Create a dummy view , it will be used in case a section has no header
        mDummyHeader = new View (mContext);
        ViewGroup.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                1, Gravity.TOP);
        mDummyHeader.setLayoutParams(params);
        mDummyHeader.setBackgroundColor(Color.TRANSPARENT);
        mChildViewsCreated = true;
    }
}
