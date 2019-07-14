package com.group10.calculator.dummy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.group10.calculator.R;

import java.util.List;

/**
 * This is class adapter support list view
 */
public class History_Adapter extends BaseAdapter {

    private Context mContext;
    private int layout;
    private List<ItemHistory> mListItemHistory;

    public History_Adapter(Context mContext, int layout, List<ItemHistory> mListItemHistory) {
        this.mContext = mContext;
        this.layout = layout;
        this.mListItemHistory = mListItemHistory;
    }


    // return size of list
    @Override
    public int getCount() {
        return mListItemHistory.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * This is class function handle return view for support listview and set value for perform view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater
                = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(layout,null);
        TextView result = convertView.findViewById(R.id.result);
        TextView operand = convertView.findViewById(R.id.operand);
        TextView time = convertView.findViewById(R.id.timer);
        ItemHistory itemHistory = mListItemHistory.get(position);
        result.setText(itemHistory.getResult());
        operand.setText(itemHistory.getOperand());
        time.setText(itemHistory.getTime());
        return convertView;
    }
}
