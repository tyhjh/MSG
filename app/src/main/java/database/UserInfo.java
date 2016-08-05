package database;

/**
 * Created by _Tyhj on 2016/8/3.
 */
public class UserInfo {
    String number,password,url, name, email, signature, place, snumber;

    @Override
    public String toString() {
        return "UserInfo{" +
                "number='" + number + '\'' +
                ", password='" + password + '\'' +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", signature='" + signature + '\'' +
                ", place='" + place + '\'' +
                ", snumber='" + snumber + '\'' +
                '}';
    }

    public UserInfo(String number, String password, String url, String name, String email, String signature, String place, String snumber) {
        this.number = number;
        this.password = password;
        this.url = url;
        this.name = name;
        this.email = email;
        this.signature = signature;
        this.place = place;
        this.snumber = snumber;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getSnumber() {
        return snumber;
    }

    public void setSnumber(String snumber) {
        this.snumber = snumber;
    }
}
