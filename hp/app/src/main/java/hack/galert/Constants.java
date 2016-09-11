package hack.galert;

import java.net.PortUnreachableException;

/**
 * Created by Ankit on 9/10/2016.
 */
public class Constants {

    public static final String SERVER_ROOT = "http://nowknowmore.aavi.me/api/";
    public static final String SERVER_URL_LOGIN = SERVER_ROOT + "login";
    public static final String SERVER_URL_NAVIGATION_ITEMS = SERVER_ROOT + "subscriptions";
    public static final String SERVER_URL_FEEDS = SERVER_ROOT + "subscriptions/";
    public static final String LOGIN_PREF = "prefLogin";

    public static final String IMAGE_LOAD_CHOICE = "shouldLoadImage";
    public static final String EXTRAA_DETAILS = "detailsExtraa";
    public static final String TOKEN = "token";
    public static final String SERVER_URL_ADD_INTEREST = SERVER_ROOT + "subscriptions";
    public static final String EXTRAA_RESULTS = "articles_list";
    public static final String LOGIN_KEY = "email";
    public static final String LOGIN_PASS_KEY = "password";
    public static final String SERVER_URL_REGISTER = SERVER_ROOT + "users";
    public static final String SERVER_URL_DELETE_INTEREST = SERVER_ROOT + "subscriptions/";
    public static final String EXTRAA_DATA_LOADED_FLAG = "dataLoaded";
}
