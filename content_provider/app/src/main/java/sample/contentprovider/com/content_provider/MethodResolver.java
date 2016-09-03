package sample.contentprovider.com.content_provider;

/**
 * Created by Ankit on 9/3/2016.
 */
public class MethodResolver {

    public String announcement;
    public String start_date;
    public String privacy;
    public String end_date;
    public String timezone;

    public MethodResolver(String announcement, String start_date, String privacy, String end_date, String timezone) {

        this.announcement = announcement;
        this.start_date = start_date;
        this.privacy = privacy;
        this.end_date = end_date;
        this.timezone = timezone;

    }

}
