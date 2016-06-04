package com.zerovoid.common.view.grouplist;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.List;

/**
 * Created by wanghaiming on 2016/6/3.
 */
abstract public class GroupListAdapter<GROUP,CHILD> extends BaseExpandableListAdapter {
    private List<? extends GroupData<GROUP,CHILD>>  mGroupDataList;

    public GroupListAdapter(List<? extends GroupData<GROUP,CHILD>> groupDataList){
        mGroupDataList = groupDataList;
    }
    @Override
    public int getGroupCount() {
        return mGroupDataList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mGroupDataList.get(groupPosition).getChildCount();
    }

    @Override
    public GroupData<GROUP,CHILD> getGroup(int groupPosition) {
        return mGroupDataList.get(groupPosition);
    }

    @Override
    public ChildData<CHILD> getChild(int groupPosition, int childPosition) {
        return mGroupDataList.get(groupPosition).getChild(childPosition);
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
        return false;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    abstract protected View onCreateGroupView(ViewGroup parent,int groupViewType);
    abstract protected void onBindGroupView(ViewGroup parent, int groupViewType, View groupView, GroupData<GROUP,CHILD> groupData);

    abstract protected View onCreateChildView(ViewGroup parent,int childViewType,boolean isLasChild);
    abstract protected View onBindChildView(ViewGroup parent,int childViewType,boolean isLastChild,View childView,ChildData<CHILD> childData);

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = onCreateGroupView(parent,getGroupType(groupPosition));
        }

        onBindGroupView(parent,getGroupType(groupPosition),convertView,mGroupDataList.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = onCreateChildView(parent,getChildType(groupPosition,childPosition),isLastChild);
        }
        onBindChildView(parent,getChildType(groupPosition,childPosition),isLastChild,convertView,mGroupDataList.get(groupPosition).getChild(childPosition));
        return convertView;
    }

    protected void onGroupSelectedChanged(GroupData<GROUP,CHILD> groupData,boolean selected){

    }
    protected void onGroupEditingChanged(GroupData<GROUP,CHILD> groupData,boolean editing){

    }
}
