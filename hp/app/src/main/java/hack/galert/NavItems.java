package hack.galert;

/**
 * Created by Ankit on 9/10/2016.
 */
public class NavItems {
    String icon;
    String title;

    public NavItems(String icon, String title) {
        this.icon = icon;
        this.title = title;
    }


    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
