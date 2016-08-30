package database;
import android.content.Context;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

import java.sql.DriverManager;
import java.sql.SQLException;

import custom.MyPublic;

public class Myslq {
    String url="jdbc:mysql://115.28.16.220/tyhj1?useUnicode=true&characterEncoding=utf-8";
    Connection conn;
    Statement statement;
    public Myslq(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("成功加载MySQL驱动！");
        }catch(Exception e){
            System.out.println("找不到MySQL驱动!");
            e.printStackTrace();
        }
        try {
            conn = (Connection) DriverManager.getConnection(url,"tyhj","4444");
            System.out.println("成功加载conn！");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(conn!=null) {
                statement = (Statement) conn.createStatement();
            }else {

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //查询用户是否已经存在
    public boolean isUserHad(String str, Context context)  {
        String sql="select * from user where id='"+str+"'";
        ResultSet rs = null;
        try {
                rs = (ResultSet) statement.executeQuery(sql);
            if (rs.next()) {
                release(rs);
                return true;
            } else {
                conn.close();
                statement.close();
                rs.close();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    //查询邮箱是否被注册
    public boolean isEmailHad (String str,Context context)  {
        String sql="select * from user where email='"+str+"'";
        ResultSet rs = null;
        try {
            rs = (ResultSet) statement.executeQuery(sql);
            if (rs.next()) {
                release(rs);
                return true;
            } else {
                release(rs);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //新建用户
    public void addUser(String id,String pas,String url,String name,String email, String signature,String place,String snumber)  {
        String sql="insert into user values('"+id+"','"+pas+"','"+url+"','"+name+"','"+email+
                "','"+signature +"','"+place+"','"+snumber +"')";
        try {
            statement.executeUpdate(sql);
            conn.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //修改密码
    public void changePas(String id,String pas){

    }
    //修改信息
    public void changeMsg(String id,String pas,String email,String name, String signature,String snumber,String place){
    }
    //登陆验证
    public UserInfo logIn(String id,String pas ){
        String sql="select * from user where id='"+id+"' and password='"+pas+"'";
        ResultSet rs = null;
        try {
            rs = (ResultSet) statement.executeQuery(sql);
            if (rs.next()) {
                return new UserInfo(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8));
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }finally {
            try {
                release(rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    //获取头像
    public String getHeadImageUrl(String str)  {
        String sql="select * from user where id='"+str+"'";
        String url=null;
        ResultSet rs = null;
        try {
            rs = (ResultSet) statement.executeQuery(sql);
            if (rs.next()) {
                url=rs.getString(3);
                release(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    //释放资源
    private void release(ResultSet rs) throws SQLException {
        conn.close();
        statement.close();
    }
}
