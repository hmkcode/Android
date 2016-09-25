package simple.musicgenie;

public class DownloadTaskModel {
    public String Title;
    public int Progress;
    public String taskID;
    public String contentSize;

    public DownloadTaskModel(String title, int progress,String taskID,String contentSize) {
        this.Title = title;
        this.taskID= taskID;
        this.Progress = progress;
        this.contentSize = contentSize;
    }
}
