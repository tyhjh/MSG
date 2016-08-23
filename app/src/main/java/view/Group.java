package view;

import java.util.List;

/**
 * Created by Tyhj on 2016/8/9.
 */
public class Group {
    private String groupName;//分组名
    private List<Child> childList;//该分组写子列表

    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public List<Child> getChildList() {
        return childList;
    }
    public void setChildList(List<Child> childList) {
        this.childList = childList;
    }

}
