package sendemail;

import android.content.Context;

import com.tyhj.mylogin.R;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 * Created by _Tyhj on 2016/7/31.
 */
public class SendEmai {
    private static final String from = "tyhj5@qq.com";
    private static final String host = "smtp.qq.com";
    private static final boolean isSSL = true;
    private static final int port = 465;
    private static final String username = "tyhj5@qq.com";
    private static final String password = "swtqbhkrldwnbdjh";
    public SendEmai(String emailnumber,String str, Context context,String name){
        //发送邮件
            try {
                if(name.equals("")){
                    name="用户";
                }
                Email email = new SimpleEmail();
                email.setSSLOnConnect(isSSL);
                email.setHostName(host);
                email.setSmtpPort(port);
                email.setAuthentication(username, password);
                email.setFrom(from);
                email.addTo(emailnumber);
                email.setSubject("MSG邮箱验证");
                email.setMsg("亲爱的 "+name+context.getString(R.string.registers)+str+context.getString(R.string.registere));
                email.send();
            } catch (EmailException e) {
                e.printStackTrace();
            }
            System.out.println("发送完毕！");
    }
}
