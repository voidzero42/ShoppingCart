package com.zerovoid.common.view.grouplist;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.ListIterator;

/**
 * Created by wanghaiming on 2016/6/3.
 */
abstract public class AbstractGroupListAdapter<GROUP,CHILD> extends BaseExpandableListAdapter {

    public interface OnDataSetChangeListener {
        void onDataSetChanged(List<? extends GroupData> groupDataList);
    }

    private List<? extends GroupData<GROUP,CHILD>>  mGroupDataList;
    private OnDataSetChangeListener mOnDataSetChangeListener;


    public AbstractGroupListAdapter(List<? extends GroupData<GROUP,CHILD>> groupDataList){
        mGroupDataList = groupDataList;
    }

    public OnDataSetChangeListener getOnDataSetChangeListener() {
        return mOnDataSetChangeListener;
    }

    public void setOnDataSetChangeListener(OnDataSetChangeListener onDataSetChangeListener) {
        mOnDataSetChangeListener = onDataSetChangeListener;
    }

    public int getSelectedCount() {
        int selectedCount = 0;
        for(GroupData<GROUP,CHILD> groupData : mGroupDataList){
            for(ChildData<CHILD> childData : groupData.getChildList()){
                if(childData.isSelected()){
                    selectedCount++;
                }
            }
        }
        return selectedCount;
    }
    public boolean isAllSelected(){
        for(GroupData<GROUP,CHILD> groupData : mGroupDataList){
            if(!groupData.isSelected()){
                return false;
            }
        }
        return true;
    }
    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if(mOnDataSetChangeListener != null){
            mOnDataSetChangeListener.onDataSetChanged(mGroupDataList);
        }
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

    abstract protected View onCreateGroupView(ViewGroup parent,int groupViewType);
    abstract protected void onBindGroupView(ViewGroup parent, int groupViewType, View groupView, GroupData<GROUP,CHILD> groupData);

    abstract protected View onCreateChildView(ViewGroup parent,int childViewType,boolean isLasChild);
    abstract protected void onBindChildView(ViewGroup parent,int childViewType,boolean isLastChild,View childView,ChildData<CHILD> childData);


    //------------------------------------------specific interface-------------------------------------------------
    public void onGroupSelectedChanged(GroupData<GROUP,CHILD> groupData,boolean selected){
        groupData.setSelected(selected);
        for(ChildData<CHILD> childData : groupData.getChildList()){
            childData.setSelected(selected);
        }
        notifyDataSetChanged();
    }
    public void onGroupEditingChanged(GroupData<GROUP,CHILD> groupData,boolean editing){
        groupData.setEditing(editing);
        for(ChildData<CHILD> childData : groupData.getChildList()){
            childData.setEditing(editing);
        }
        notifyDataSetChanged();
    }
    public void onAllSelectedChanged(boolean selected){
        for(GroupData<GROUP,CHILD> groupData: mGroupDataList){
            groupData.setSelected(selected);
            for(ChildData<CHILD> childData : groupData.getChildList()){
                childData.setSelected(selected);
            }
        }
        notifyDataSetChanged();
    }
    public void onAllEditingChanged(boolean editing){
        for(GroupData<GROUP,CHILD> groupData: mGroupDataList){
            groupData.setEditing(editing);
            for(ChildData<CHILD> childData : groupData.getChildList()){
                childData.setEditing(editing);
            }
        }
        notifyDataSetChanged();
    }

    public void onChildSelectedChanged(ChildData<CHILD> childData,boolean selected){
        childData.setSelected(selected);
        boolean hasUnSelectedChild = false;
        for(ChildData<CHILD> sibling : childData.getGroupData().getChildList()){
            if(!sibling.isSelected()){
                hasUnSelectedChild =  true;
                break;
            }
        }
        if(!hasUnSelectedChild){
            childData.getGroupData().setSelected(true);
        }
        else {
            childData.getGroupData().setSelected(false);
        }
        notifyDataSetChanged();
    }
    public void removeChild(int groupPos, int childPos){
        GroupData<GROUP,CHILD> groupData = mGroupDataList.get(groupPos);
        groupData.removeChild(childPos);
        if(groupData.getChildCount() == 0){
            mGroupDataList.remove(groupData);
        }
        notifyDataSetChanged();
    }
    public void removeAllSelectedChild(){

        ListIterator<? extends GroupData<GROUP,CHILD>>  groupDataListIterator = mGroupDataList.listIterator();
        while(groupDataListIterator.hasNext()){

            GroupData<GROUP,CHILD> groupData = groupDataListIterator.next();

            ListIterator<? extends ChildData<CHILD>> childDataListIterator = groupData.getChildList().listIterator();
            while(childDataListIterator.hasNext()){
                ChildData<CHILD> childData = childDataListIterator.next();
                if(childData.isSelected()){
                    childDataListIterator.remove();
                }
            }

            if(groupData.getChildCount() <= 0){
                groupDataListIterator.remove();
            }
        }
        notifyDataSetChanged();
    }
}
