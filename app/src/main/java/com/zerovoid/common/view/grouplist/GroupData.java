package com.zerovoid.common.view.grouplist;

import java.util.List;

/**
 * Created by wanghaiming on 2016/6/3.
 */
public  class GroupData <GROUP/*group data*/, CHILD /*child data*/> {
    private GROUP mGroup;
    private boolean mIsSelected;
    private boolean mIsEditing;
    private boolean mIsEditingSelected;

    private List<? extends ChildData<CHILD>> mChildList;

    public GroupData(GROUP group, List< ? extends ChildData<CHILD>>  childList){
        mGroup = group;
        mChildList = childList;
        for(ChildData<CHILD> childData: childList){
            childData.setGroupData(this);
        }
    }

    public boolean isSelected() {
        return mIsEditing? mIsEditingSelected: mIsSelected;
    }

    public void setSelected(boolean selected) {
        if(mIsEditing){
            mIsEditingSelected = selected;
        }
        else{
            mIsSelected = selected;
        }
    }

    public boolean isEditing() {
        return mIsEditing;
    }

    public void setEditing(boolean editing) {
        mIsEditing = editing;
        mIsEditingSelected = false;
    }

    public GROUP getGroup() {
        return mGroup;
    }

    public void setGroup(GROUP group) {
        mGroup = group;
    }

    public List<? extends ChildData<CHILD>> getChildList() {

        return mChildList;
    }

    public void setChildList(List<ChildData<CHILD>> childList) {
        mChildList = childList;
        for(ChildData<CHILD> childData: childList){
            childData.setGroupData(this);
        }
    }

    public int getChildCount(){

        if(mChildList == null) return 0;

        return mChildList.size();
    }
    public ChildData<CHILD> getChild(int childPos)
    {
        return mChildList.get(childPos);
    }
    public void removeChild(int childPos)
    {
        mChildList.remove(childPos);
    }
    public void removeChild(ChildData<CHILD> child)
    {
        mChildList.remove(child);
    }

}
