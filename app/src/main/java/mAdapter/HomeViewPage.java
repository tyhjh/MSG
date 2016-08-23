package mAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.tyhj.mylogin.homefragment.First;
import com.tyhj.mylogin.homefragment.First_;
import com.tyhj.mylogin.homefragment.Last;
import com.tyhj.mylogin.homefragment.Linkman;
import com.tyhj.mylogin.homefragment.Linkman_;

/**
 * Created by Tyhj on 2016/8/22.
 */
public class HomeViewPage extends FragmentPagerAdapter {
    private final String[] TITLES = {"Home", "联系人", "设置"};
    public HomeViewPage(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new First_();
            case 1:
                return new Linkman_();
            case 2:
                return new Last();
            default:
                break;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }
}
