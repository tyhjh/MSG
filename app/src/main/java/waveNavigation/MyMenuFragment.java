package waveNavigation;

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

import custom.MyPublic;
import waveNavigation.MenuFragment;


public class MyMenuFragment extends MenuFragment {
    NavigationView navigationView;
    View view;
    View headView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu, container, false);
        navigationView= (NavigationView) view.findViewById(R.id.vNavigation);
        headView=navigationView.getHeaderView(0);
        ImageView imageView=(ImageView) headView.findViewById(R.id.userheadImage);
        imageView.setOutlineProvider(MyPublic.getOutline(true,10));
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
                        Toast.makeText(getActivity(),"menu_feed",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_direct:
                        Toast.makeText(getActivity(),"menu_direct",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_news:
                        Toast.makeText(getActivity(),"menu_news",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_popular:
                        Toast.makeText(getActivity(),"menu_popular",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_photos_nearby:
                        Toast.makeText(getActivity(),"menu_photos_nearby",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_group_2:
                        Toast.makeText(getActivity(),"menu_group_2",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_settings:
                        Toast.makeText(getActivity(),"menu_settings",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_about:
                        Toast.makeText(getActivity(),"menu_about",Toast.LENGTH_SHORT).show();
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
