package com.zerovoid.common.view.grouplist;

/**
 * Created by wanghaiming on 2016/6/3.
 */
public  class ChildData<CHILD>{
    private CHILD mChild;
    private GroupData<?,CHILD> mGroupData;
    private boolean mIsSelected;
    private boolean mIsEditing;
    private boolean mIsEditingSelected;

    public GroupData<?, CHILD> getGroupData() {
        return mGroupData;
    }

    public void setGroupData(GroupData<?, CHILD> groupData) {
        mGroupData = groupData;
    }

    public ChildData(CHILD child){
        mChild = child;
    }
    public CHILD getChild() {
        return mChild;
    }

    public void setChild(CHILD child) {
        mChild = child;
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
}
