package database;

/**
 * Created by Tyhj on 2016/8/24.
 */
public class EssayInfo {
    private int number;
    private String ivLz;
    private String ivEssay;
    private String tvLz;
    private String tvEssay;
    private int likeCounts;
    private boolean isLike;
    private String time;

    public EssayInfo(int number, String ivLz, String ivEssay, String tvLz, String tvEssay, int likeCounts, boolean isLike, String time) {
        this.number = number;
        this.ivLz = ivLz;
        this.ivEssay = ivEssay;
        this.tvLz = tvLz;
        this.tvEssay = tvEssay;
        this.likeCounts = likeCounts;
        this.isLike = isLike;
        this.time = time;
    }
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getIvEssay() {
        return ivEssay;
    }

    public void setIvEssay(String ivEssay) {
        this.ivEssay = ivEssay;
    }

    public String getIvLz() {
        return ivLz;
    }

    public void setIvLz(String ivLz) {
        this.ivLz = ivLz;
    }

    public String getTvLz() {
        return tvLz;
    }

    public void setTvLz(String tvLz) {
        this.tvLz = tvLz;
    }

    public String getTvEssay() {
        return tvEssay;
    }

    public void setTvEssay(String tvEssay) {
        this.tvEssay = tvEssay;
    }

    public int getLikeCounts() {
        return likeCounts;
    }

    public void setLikeCounts(int likeCounts) {
        this.likeCounts = likeCounts;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
