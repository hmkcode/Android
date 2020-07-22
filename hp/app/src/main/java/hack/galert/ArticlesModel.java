package hack.galert;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Ankit on 9/10/2016.
 */
public class ArticlesModel implements Parcelable{

    int id;
    String refrence;
    String readTime;
    String articleHeader;
    String articleAbstract;
    //    int likes;
//    boolean isLiked;
    String imageUrl;

    public ArticlesModel(int id, String refrence, String readTime, String articleHeader, String articleAbstract, String imageUrl) {
        this.id = id;
        this.refrence = refrence;
        this.readTime = readTime;
        this.articleHeader = articleHeader;
        this.articleAbstract = articleAbstract;
        this.imageUrl = imageUrl;
    }

    public ArticlesModel(String refrence, String readTime, String articleHeader, String articleAbstract, String imageUrl) {
        this.refrence = refrence;
        this.readTime = readTime;
        this.articleHeader = articleHeader;
        this.articleAbstract = articleAbstract;
        this.imageUrl = imageUrl;
    }

    public ArticlesModel(String refrence, String readTime, String articleHeader, String articleAbstract, int likes, boolean isLiked) {
        this.refrence = refrence;
        this.readTime = readTime;
        this.articleHeader = articleHeader;
        this.articleAbstract = articleAbstract;
//        this.likes = likes;
//        this.isLiked = isLiked;
    }

    public ArticlesModel(String refrence, String readTime, String articleHeader, String articleAbstract, int likes, boolean isLiked, String imageUrl) {
        this.refrence = refrence;
        this.readTime = readTime;
        this.articleHeader = articleHeader;
        this.articleAbstract = articleAbstract;
//        this.likes = likes;
//        this.isLiked = isLiked;
        this.imageUrl = imageUrl;
    }

    public ArticlesModel(Parcel in) {

        refrence = in.readString();
        readTime = in.readString();
        articleHeader = in.readString();
        articleAbstract = in.readString();
        imageUrl = in.readString();

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRefrence() {
        return refrence;
    }

    public void setRefrence(String refrence) {
        this.refrence = refrence;
    }

    public String getReadTime() {
        return readTime;
    }

    public void setReadTime(String readTime) {
        this.readTime = readTime;
    }

    public String getArticleHeader() {
        return articleHeader;
    }

    public void setArticleHeader(String articleHeader) {
        this.articleHeader = articleHeader;
    }

    public String getArticleAbstract() {
        return articleAbstract;
    }

    public void setArticleAbstract(String articleAbstract) {
        this.articleAbstract = articleAbstract;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(refrence);
        parcel.writeString(readTime);
        parcel.writeString(articleHeader);
        parcel.writeString(articleAbstract);
        parcel.writeString(imageUrl);

    }

    public static final Parcelable.Creator<ArticlesModel> CREATOR = new Parcelable.Creator<ArticlesModel>() {
        public ArticlesModel createFromParcel(Parcel in) {
            return new ArticlesModel(in);
        }

        public ArticlesModel[] newArray(int size) {
            return new ArticlesModel[size];
        }
    };
//    public int getLikes() {
//        return likes;
//    }
//
//    public void setLikes(int likes) {
//        this.likes = likes;
//    }
//
//    public boolean isLiked() {
//        return isLiked;
//    }
//
//    public void setIsLiked(boolean isLiked) {
//        this.isLiked = isLiked;
//    }
}
