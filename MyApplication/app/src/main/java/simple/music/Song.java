package simple.music;

import java.io.Serializable;

/**
 * Created by Ankit on 8/5/2016.
 */

//todo: make it pacelable
public class Song implements Serializable{
    public String Title;
    public String TrackDuration;
    public String UploadedBy;
    public String Thumbnail_url;
    public String Video_id;
    public String TimeSinceUploaded;
    public String UserViews;

    public Song(String title, String trackDuration, String uploadedBy, String thumbnail_url, String video_id, String timeSinceUploaded, String userViews) {
        this.Title = title;
        this.TrackDuration = trackDuration;
        this.UploadedBy = uploadedBy;
        this.Thumbnail_url = thumbnail_url;
        this.Video_id = video_id;
        this.TimeSinceUploaded = timeSinceUploaded;
        this.UserViews = userViews;
    }

}
