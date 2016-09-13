package simple.music;

/**
 * Created by Ankit on 8/14/2016.
 */
public class TrendingSongModel extends Song {

    public String type;
    public TrendingSongModel(String title, String trackDuration, String uploadedBy, String thumbnail_url, String video_id, String timeSinceUploaded, String userViews) {
        super(title, trackDuration, uploadedBy, thumbnail_url, video_id, timeSinceUploaded, userViews);
    }

    public TrendingSongModel(String title, String trackDuration, String uploadedBy, String thumbnail_url, String video_id, String timeSinceUploaded, String userViews, String type) {
        super(title, trackDuration, uploadedBy, thumbnail_url, video_id, timeSinceUploaded, userViews);
        this.type = type;
    }
}
