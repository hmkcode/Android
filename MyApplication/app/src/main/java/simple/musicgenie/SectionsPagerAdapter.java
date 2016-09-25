package simple.musicgenie;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Ankit on 9/25/2016.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new ActiveTaskFragment();
        } else return new DownloadFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }
}
