package hack.galert;

/**
 * Created by Ankit on 9/10/2016.
 */
public class ArticlesModel {

    String refrence;
    String readTime;
    String articleHeader;
    String articleAbstract;
    int likes;
    boolean isLiked;

    public ArticlesModel(String refrence, String readTime, String articleHeader, String articleAbstract, int likes, boolean isLiked) {
        this.refrence = refrence;
        this.readTime = readTime;
        this.articleHeader = articleHeader;
        this.articleAbstract = articleAbstract;
        this.likes = likes;
        this.isLiked = isLiked;
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

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setIsLiked(boolean isLiked) {
        this.isLiked = isLiked;
    }
}
