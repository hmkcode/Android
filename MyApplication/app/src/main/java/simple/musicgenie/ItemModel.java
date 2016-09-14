package simple.musicgenie;


public class ItemModel extends BaseSong {

    public String type;
    public ItemModel(String title, String trackDuration, String uploadedBy, String thumbnail_url, String video_id, String timeSinceUploaded, String userViews) {
        super(title, trackDuration, uploadedBy, thumbnail_url, video_id, timeSinceUploaded, userViews);
    }

    public ItemModel(String title, String trackDuration, String uploadedBy, String thumbnail_url, String video_id, String timeSinceUploaded, String userViews, String type) {
        super(title, trackDuration, uploadedBy, thumbnail_url, video_id, timeSinceUploaded, userViews);
        this.type = type;
    }
}
