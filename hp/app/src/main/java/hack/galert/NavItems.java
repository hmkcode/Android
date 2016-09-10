package hack.galert;

/**
 * Created by Ankit on 9/10/2016.
 */
public class NavItems {
    String title;
    int id;
    String lastFetched;

    public NavItems(String title, int id, String lastFetched) {
        this.title = title;
        this.id = id;
        this.lastFetched = lastFetched;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastFetched() {
        return lastFetched;
    }

    public void setLastFetched(String lastFetched) {
        this.lastFetched = lastFetched;
    }
}
