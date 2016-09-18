package waveNavigation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tyhj.myfist_2016_6_29.MyTime;
import com.tyhj.mylogin.R;
import com.tyhj.mylogin.umeng.MyLogin;
import com.tyhj.mylogin.umeng.MyLogin_;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import custom.MyPublic;
import database.Myslq;
import waveNavigation.MenuFragment;


public class MyMenuFragment extends MenuFragment {
    private static String PATH="http://115.28.16.220:8080/Upload/uploadFile/p123.JPEG";
    NavigationView navigationView;
    View view;
    Button camoral, images;
    Uri imageUri;
    ImageView imageView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu, container, false);
        navigationView= (NavigationView) view.findViewById(R.id.vNavigation);
        TextView textView= (TextView) view.findViewById(R.id.signature);
        imageView=(ImageView) view.findViewById(R.id.userheadImage);
        Picasso.with(getActivity()).load(MyPublic.getUserInfo().getUrl()).into(imageView);
        imageView.setOutlineProvider(MyPublic.getOutline(true,20));
        imageView.setClipToOutline(true);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();
            }
        });

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
                                //MyPublic.savaFile(PATH,"A4444.JPEG",handler,getActivity());
                            }
                        }).start();
                        break;
                    //分享
                    case R.id.menu_share:
                      Toast.makeText(getActivity(),getString(R.string.share),Toast.LENGTH_SHORT).show();
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
    // 上传用户头像
    private void dialog() {
        AlertDialog.Builder di;
        di = new AlertDialog.Builder(getActivity());
        di.setCancelable(true);
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View layout = inflater.inflate(R.layout.headchoose, null);
        di.setView(layout);
        final Dialog dialog=di.show();
        camoral = (Button) layout.findViewById(R.id.camoral);
        images = (Button) layout.findViewById(R.id.images);
        // 相机
        camoral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                File f1 = new File(Environment.getExternalStorageDirectory()+"/LinkManPhone");
                if(!f1.exists()){
                    f1.mkdirs();
                }
                File outputImage = new File(Environment
                        .getExternalStorageDirectory()+"/LinkManPhone", "head.jpg");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageUri = Uri.fromFile(outputImage);

                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                WHERE_PHOTO = 1;
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO);
            }
        });
        // 相册
        images.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.cancel();
                File f1 = new File(Environment.getExternalStorageDirectory()+"/LinkManPhone");
                if(!f1.exists()){
                    f1.mkdirs();
                }
                File outputImage = new File(Environment
                        .getExternalStorageDirectory()+"/LinkManPhone", "head.jpg");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageUri = Uri.fromFile(outputImage);

                WHERE_PHOTO = 2;
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.setType("image/*");
                intent.putExtra("crop", true);
                intent.putExtra("scale", true);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, 1);
            }
        });
    }
    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO = 2;
    int WHERE_PHOTO = 0;
    String date;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == getActivity().RESULT_OK) {
                    if (data != null) {
                        imageUri = data.getData();
                    }
                    SimpleDateFormat df = new SimpleDateFormat(
                            "yyyy-MM-dd HH:mm:ss");// 设置日期格式
                    MyTime myTime=new MyTime();
                    date =myTime.getYear()+myTime.getMonth_()+myTime.getDays()+
                            myTime.getWeek_()+myTime.getHour()+myTime.getMinute()+
                            myTime.getSecond()+MyPublic.getUserInfo().getName()+".JPEG";
                    Bitmap bitmap= null;
                    try {
                        bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(imageUri));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    MyPublic.saveBitmapFile(bitmap,date,handler1,getActivity());
                    File file=new File(Environment.getExternalStorageDirectory()+"/AmsgImage",date);
                    //换图片
                    imageUri = Uri.fromFile(file);
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, CROP_PHOTO);
                }
                break;
            case CROP_PHOTO:
                if (resultCode == getActivity().RESULT_OK) {
                    try {
                        Picasso.with(getActivity()).load(imageUri).into(imageView);
                        String path=Environment.getExternalStorageDirectory()+"/AmsgImage";
                        WHERE_PHOTO = 0;
                        if(!MyPublic.isIntenet(getActivity()))
                            return;
                        final File file=new File(path,date);
                        final String fileName=MyPublic.getUserInfo().getNumber()+".JPEG";
                        MyPublic.UploadFile(file,fileName,getActivity(),0);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                MyPublic.getMyslq().setHeadImageUrl(getString(R.string.headImageUrl)+fileName,MyPublic.getUserInfo().getNumber());
                                file.delete();
                            }
                        }).start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

    public void onOpenMenu(){

    }
    public void onCloseMenu(){
    }
    Handler handler1=new Handler();
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
