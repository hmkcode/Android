package simple.musicgenie;

import android.os.Parcel;

/**
 * Created by Ankit on 9/25/2016.
 */
public class SearchSuggestion implements com.arlib.floatingsearchview.suggestions.model.SearchSuggestion {

    private String mSearchHint;
    private boolean mIsHistory = false;

    public SearchSuggestion(String suggestion){
        this.mSearchHint = suggestion.toLowerCase();
    }

    public SearchSuggestion(Parcel source){
        this.mSearchHint = source.readString();
        this.mIsHistory = source.readInt() != 0;
    }

    public void setIsHistory(boolean isHistory){
        this.mIsHistory = isHistory;
    }

    public boolean getIsHistory(){
        return this.mIsHistory;
    }


    @Override
    public String getBody() {
        return mSearchHint;
    }

    public static final Creator<SearchSuggestion> CREATOR  = new Creator<SearchSuggestion>() {
        @Override
        public SearchSuggestion createFromParcel(Parcel parcel) {

            return new SearchSuggestion(parcel);
        }

        @Override
        public SearchSuggestion[] newArray(int i) {
            return new SearchSuggestion[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(mSearchHint);
        parcel.writeInt(mIsHistory ? 1:0);
    }
}
