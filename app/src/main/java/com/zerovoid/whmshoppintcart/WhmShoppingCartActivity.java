package com.zerovoid.whmshoppintcart;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.zerovoid.common.view.grouplist.GroupData;
import com.zerovoid.common.view.grouplist.GroupListAdapter;
import com.zerovoid.shoppingcart.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wanghaiming on 2016/6/4.
 */
public class WhmShoppingCartActivity extends Activity {
    @Bind(R.id.ivSelectAll)
    CheckBox mCbSelectAll;

    @Bind(R.id.btnSettle)
    TextView mTvSettle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whm_shopping_cart);
        ButterKnife.bind(this);


        ExpandableListView listView = (ExpandableListView)findViewById(R.id.expandable_list);
        final WhmShoppintCartAdapter adapter = new WhmShoppintCartAdapter(WhmShoppintCartAdapter.createTestData());
        listView.setAdapter(adapter);

        for(int i = 0 ; i < adapter.getGroupCount(); i++){
            listView.expandGroup(i);
        }
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });

        mCbSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.onAllSelectedChanged(((CheckBox)v).isChecked());
            }
        });
        adapter.setOnDataSetChangeListener(new GroupListAdapter.OnDataSetChangeListener() {
            @Override
            public void onDataSetChanged(List<? extends GroupData> groupDataList) {
                mCbSelectAll.setChecked(adapter.isAllSelected());
                mTvSettle.setText(String.format("结算(%d)",adapter.getSelectedCount()));
            }
        });
    }

}
