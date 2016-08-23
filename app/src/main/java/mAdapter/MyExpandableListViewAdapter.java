package mAdapter;

import android.content.Context;
import android.graphics.Outline;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tyhj.mylogin.R;

import java.util.HashMap;
import java.util.List;

import view.Child;
import view.Group;
import view.IphoneTreeView;

/**
 * Created by Tyhj on 2016/8/20.
 */
public class MyExpandableListViewAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<Group> groupList;

    private HashMap<Integer, Integer> groupStatusMap;
    private ViewOutlineProvider viewOutlineProvider;
    public MyExpandableListViewAdapter(Context context, List<Group> groupList){
        this.mContext = context;
        this.groupList = groupList;
        groupStatusMap = new HashMap<Integer, Integer>();
        //轮廓
        viewOutlineProvider=new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                final int margin = Math.min(view.getWidth(), view.getHeight()) / 30;
                //outline.setRoundRect(margin, margin, view.getWidth() - margin, view.getHeight() - margin, 20);
                outline.setOval(margin, margin, view.getWidth() - margin, view.getHeight() - margin);
            }
        };
    }
    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groupList.get(groupPosition).getChildList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groupList.get(groupPosition).getChildList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_constact_group, null);
            holder = new GroupHolder();
            holder.nameView = (TextView) convertView.findViewById(R.id.group_name);
            holder.onLineView = (TextView) convertView.findViewById(R.id.online_count);
            holder.iconView = (ImageView) convertView.findViewById(R.id.group_indicator);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }
        holder.nameView.setText(groupList.get(groupPosition).getGroupName());
        holder.onLineView.setText(getChildrenOnlineCount(groupPosition) + "/"+ getChildrenCount(groupPosition));
        if (isExpanded) {
            holder.iconView.setImageResource(R.drawable.close);
        } else {
            holder.iconView.setImageResource(R.drawable.open);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_constact_child, null);
            holder = new ChildHolder();
            holder.nameView = (TextView) convertView.findViewById(R.id.contact_list_item_name);//昵称
            holder.feelView = (TextView) convertView.findViewById(R.id.cpntact_list_item_state);//心情
            holder.iconView = (ImageView) convertView.findViewById(R.id.icon);//头像
            holder.iconView.setOutlineProvider(viewOutlineProvider);
            holder.iconView.setClipToOutline(true);
            convertView.setTag(holder);
        } else {
            holder = (ChildHolder) convertView.getTag();
        }
        Child child= (Child) getChild(groupPosition, childPosition);
        //加载头像
        Picasso.with(mContext).load(child.getHeadphoto()).into(holder.iconView);
        if(!TextUtils.isEmpty(child.getOnline_status())&&child.getOnline_status().equals("1")){//在线
            holder.iconView.setAlpha(255);
        }else{//不在线
            holder.iconView.setAlpha(100);
        }
        holder.nameView.setText(child.getUsername());//昵称
        holder.feelView.setText(child.getMood());//心情

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    class GroupHolder {
        TextView nameView;
        TextView onLineView;
        ImageView iconView;
    }

    class ChildHolder {
        TextView nameView;
        TextView feelView;
        ImageView iconView;
    }
    public int getChildrenOnlineCount(int groupPosition) {
        int count=0;
        List<Child> childList=groupList.get(groupPosition).getChildList();
        for(int i=0;i<childList.size();i++){
            Child child=childList.get(i);
            if(!TextUtils.isEmpty(child.getOnline_status())&&child.getOnline_status().equals("1")){
                count++;
            }
        }
        return count;
    }
}
