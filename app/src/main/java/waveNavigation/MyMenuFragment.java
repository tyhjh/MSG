package waveNavigation;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tyhj.mylogin.R;
import com.tyhj.mylogin.umeng.MyLogin;
import com.tyhj.mylogin.umeng.MyLogin_;

import custom.MyPublic;
import waveNavigation.MenuFragment;


public class MyMenuFragment extends MenuFragment {
    NavigationView navigationView;
    View view;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu, container, false);
        navigationView= (NavigationView) view.findViewById(R.id.vNavigation);
        ImageView imageView=(ImageView) view.findViewById(R.id.userheadImage);
        imageView.setOutlineProvider(MyPublic.getOutline(true,20));
        imageView.setClipToOutline(true);
        Picasso.with(getActivity()).load(R.mipmap.headimage).into(imageView);
        return  setupReveal(view) ;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_feed:
                        break;
                    case R.id.menu_direct:
                        break;
                    case R.id.menu_news:
                        break;
                    case R.id.menu_popular:
                        break;
                    case R.id.menu_photos_nearby:
                        break;
                    case R.id.menu_group_2:
                        break;
                    case R.id.menu_settings:
                        break;
                    case R.id.menu_about:
                        break;
                    case R.id.menu_out:
                        //删除数据
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("saveLogin", getActivity().MODE_PRIVATE);
                        sharedPreferences.edit().clear().commit();
                        MyPublic.setUserInfo(null);
                        MyPublic.startActivity(getActivity(), MyLogin_.class);
                        getActivity().finish();
                        break;
                }
                return false;
            }
        });
    }

    public void onOpenMenu(){

    }
    public void onCloseMenu(){
    }
}
