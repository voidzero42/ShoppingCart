package com.zerovoid.whmshoppintcart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.zerovoid.common.view.grouplist.GroupData;
import com.zerovoid.common.view.grouplist.AbstractGroupListAdapter;
import com.zerovoid.shoppingcart.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wanghaiming on 2016/6/4.
 */
public class WhmShoppingCartActivity extends AppCompatActivity {

    //列表
    @Bind(R.id.expandable_list)
    ExpandableListView mExpandableListView;

    //底部控件
    @Bind(R.id.cb_selectall)
    CheckBox mCbSelectAll;

    @Bind(R.id.tv_buy)
    TextView mTvBuy;

    @Bind(R.id.bottom_container)
    View mBottomContainer;

    @Bind(R.id.shoppingcart_bottom_buy_container)
    View mBuyContainer;

    @Bind(R.id.shoppintcart_bottom_edit_container)
    View mEditContainer;

    //other

    WhmShoppingCartAdapter mAdapter;
    private boolean mIsEditing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shopping_cart_whm);
        ButterKnife.bind(this);

        initToolbar();
        initExpandableList();
        initBottomButton();
        registerForContextMenu(mExpandableListView);

    }

    private void initBottomButton() {
        mBottomContainer.setVisibility(mAdapter.getGroupCount() <= 0 ? View.GONE : View.VISIBLE);
    }

    private void initExpandableList() {

        mAdapter = new WhmShoppingCartAdapter(WhmShoppingCartAdapter.createTestData());
        mAdapter.setOnDataSetChangeListener(new AbstractGroupListAdapter.OnDataSetChangeListener() {
            @Override
            public void onDataSetChanged(List<? extends GroupData> groupDataList) {
                expandAllGroup();
                initBottomButton();
                mCbSelectAll.setChecked(mAdapter.isAllSelected());
                mTvBuy.setText(String.format("结算(%d)",mAdapter.getSelectedCount()));
            }
        });

        mExpandableListView.setAdapter(mAdapter);
        expandAllGroup();
        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });

    }

    private void expandAllGroup() {
        for(int i = 0 ; i < mAdapter.getGroupCount(); i++){
            mExpandableListView.expandGroup(i);
        }
    }

    private void initToolbar(){
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("仿淘宝购物车");
        toolbar.setSubtitle("通用控件版");
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()){
                    case R.id.action_edit :
                        {
                            doEdit();
                        }
                        break;
                    default:
                }
                return true;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shopping_cart,menu);
        return true;
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.list_item_op,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.delete :
            {
                ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo)item.getMenuInfo();
                if(mExpandableListView.getPackedPositionType(info.packedPosition) == ExpandableListView.PACKED_POSITION_TYPE_CHILD){
                    doRemove(mExpandableListView.getPackedPositionGroup(info.packedPosition),mExpandableListView.getPackedPositionChild(info.packedPosition));
                }
            }
            break;
            case R.id.delete_all :
            {
                doRemoveSelectedChild();
            }
            break;
         default:

        }
        return true;
    }


    @OnClick(R.id.cb_selectall)
    void doSelectAll(CheckBox v) {
        mAdapter.onAllSelectedChanged(v.isChecked());
    }

    @OnClick(R.id.delete)
    void doRemoveSelectedChild() {
        mAdapter.removeAllSelectedChild();
    }
    private void doRemove(int groupPos, int childPos){
        mAdapter.removeChild(groupPos,childPos);
    }

    private void doEdit(){
        mIsEditing = !mIsEditing;
        if(mIsEditing){
            mBuyContainer.setVisibility(View.GONE);
            mEditContainer.setVisibility(View.VISIBLE);
        }
        else{
            mBuyContainer.setVisibility(View.VISIBLE);
            mEditContainer.setVisibility(View.GONE);
        }
        mAdapter.onAllEditingChanged(mIsEditing);
    }
}
