package waveNavigation;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tyhj.mylogin.R;
import com.tyhj.mylogin.umeng.MyLogin;
import com.tyhj.mylogin.umeng.MyLogin_;

import java.io.File;

import custom.MyPublic;
import waveNavigation.MenuFragment;


public class MyMenuFragment extends MenuFragment {
    private static String PATH="http://115.28.16.220:8080/Upload/uploadFile/p123.JPEG";
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
        TextView textView= (TextView) view.findViewById(R.id.signature);
        ImageView imageView=(ImageView) view.findViewById(R.id.userheadImage);
        Picasso.with(getActivity()).load(MyPublic.getUserInfo().getUrl()).into(imageView);
        imageView.setOutlineProvider(MyPublic.getOutline(true,20));
        imageView.setClipToOutline(true);
        textView.setText(MyPublic.getUserInfo().getSignature());
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
                    case R.id.menu_photos_nearby:
                        break;
                    case R.id.menu_group_2:
                        break;
                    case R.id.menu_settings:
                        if(!MyPublic.isIntenet(getActivity()))
                            break;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                MyPublic.savaFile(PATH,"A4444.JPEG",handler,getActivity());
                            }
                        }).start();
                        break;
                    case R.id.menu_share:
                        if(!MyPublic.isIntenet(getActivity()))
                            break;
                        MyPublic.UploadFile(new File(Environment.getExternalStorageDirectory()+
                                getString(R.string.savaphotopath),"Tyhj789.JPEG"),MyPublic.getUserInfo().getName()+
                                MyPublic.getTime()+".JPEG",getActivity());
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
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Toast.makeText(getContext(), "已保存", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}
