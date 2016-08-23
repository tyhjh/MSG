package custom;

import android.app.Activity;
import android.content.Context;
import android.graphics.Outline;
import android.net.ConnectivityManager;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.Toast;

import com.tyhj.mylogin.R;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import database.UserInfo;

/**
 * Created by _Tyhj on 2016/7/30.
 */
public  class MyPublic {
    //用户信息
    private static UserInfo userInfo;
    private static ViewOutlineProvider viewOutlineProvider;
    public static UserInfo getUserInfo() {
        return userInfo;
    }

    public static void setUserInfo(UserInfo userInfo) {
        MyPublic.userInfo = userInfo;
    }

    //是否有网络
    public static boolean isIntenet(Context context){
        ConnectivityManager con=(ConnectivityManager)context.getSystemService(Activity.CONNECTIVITY_SERVICE);
        boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        boolean internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        if(wifi||internet){
            return true;
        }else {
            MyPublic.Toast(context,"网络连接已断开，请检查网络");
            return false;
        }
    }
    //是否有WIFI
    public static boolean isWifi(Context context){
        ConnectivityManager con=(ConnectivityManager)context.getSystemService(Activity.CONNECTIVITY_SERVICE);
        return con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
    }
    //Toast
    public static void Toast(Context context,String str){
        Toast.makeText(context,str,Toast.LENGTH_SHORT).show();
    }
    //轮廓
    public static ViewOutlineProvider getOutline(boolean b, final int x){
        if(b) {
           return  new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    final int margin = Math.min(view.getWidth(), view.getHeight()) / x;
                    //outline.setRoundRect(margin, margin, view.getWidth() - margin, view.getHeight() - margin, 20);
                    outline.setOval(margin, margin, view.getWidth() - margin, view.getHeight() - margin);
                }
            };
        }else {
            return new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    final int margin = Math.min(view.getWidth(), view.getHeight()) / x;
                    outline.setRoundRect(margin, margin, view.getWidth() - margin, view.getHeight() - margin, 20);
                    //outline.setOval(margin, margin, view.getWidth() - margin, view.getHeight() - margin);
                }
            };
        }

    }
}
