package com.zerovoid.common.view.grouplist;

import java.util.List;

/**
 * Created by wanghaiming on 2016/6/3.
 */
public final class GroupData <GROUP/*group data*/, CHILD /*child data*/> {
    private GROUP mGroup;
    private boolean mSelected;
    private boolean mIsEditing;

    private List<ChildData<CHILD>> mChildList;

    public boolean isSelected() {
        return mSelected;
    }

    public void setSelected(boolean selected) {
        mSelected = selected;
    }

    public boolean isEditing() {
        return mIsEditing;
    }

    public void setEditing(boolean editing) {
        mIsEditing = editing;
    }

    public GroupData(GROUP group, List<ChildData<CHILD>>  childList){
        mGroup = group;
        mChildList = childList;
    }
    public int getChildCount(){

        if(mChildList == null) return 0;

        return mChildList.size();
    }
    public ChildData<CHILD> getChild(int childPos){
        return mChildList.get(childPos);
    }

    public GROUP getGroup() {
        return mGroup;
    }

    public void setGroup(GROUP group) {
        mGroup = group;
    }

    public List<ChildData<CHILD>> getChildList() {
        return mChildList;
    }

    public void setChildList(List<ChildData<CHILD>> childList) {
        mChildList = childList;
    }
}
