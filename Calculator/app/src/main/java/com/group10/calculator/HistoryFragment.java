package com.group10.calculator;

import android.app.AlertDialog;
import android.os.Bundle;
import android.content.Context;
import android.graphics.Color;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.group10.calculator.dummy.History_Adapter;
import com.group10.calculator.dummy.ItemHistory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This is class history perform time result operand
 */
public class HistoryFragment extends Fragment {

    private List<ItemHistory> mListItemHistory;
    private ListView mListView;
    private History_Adapter history_adapter;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Set<String> stringSet;
    private ArrayList<String> array;
    private int position;

    /**
     * This is function handle convert hashset to arraylist
     * then add itemHistory in arraylist
     * value be formated: result | perand | day/month/year hour:minutes:second
     */
    private void AddListHistory()
    {
        sharedPreferences = this.getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        stringSet = new HashSet<>();
        stringSet.add("0|0|04/03/2019 11:51:32");
        stringSet = sharedPreferences.getStringSet("data",stringSet);
        array = new ArrayList(stringSet);
        for(int i=0;i<array.size();i++)
        {

            String data = array.get(i);
            String[] strings = data.split("\\|");
            String result = strings[0];
            String operand = strings[1];
            String time = strings[2];
            mListItemHistory.add(new ItemHistory(result,operand,time));
        }
    }
    /**
     * This is function handle remove value when long click into listview
     */
    /**
     * This is function handle remove value when click into listview
     */
    private void RemoveItem()
    {
        editor = sharedPreferences.edit();
        stringSet.remove(array.get(position));
        array.remove(position);
        editor.putStringSet("data",stringSet);
        editor.commit();
        mListItemHistory.remove(position);
        history_adapter.notifyDataSetChanged();
        Toast.makeText(getContext(),"Remove success",Toast.LENGTH_SHORT).show();
    }
    /**
     * This is function make color all item view on listview as begin
     */
    private void ResetColor()
    {
        View view;
        try {
            for (int i = 0; i < mListItemHistory.size(); i++) {
                view = mListView.getChildAt(i);
                view.setBackgroundColor(Color.rgb(199, 215, 216));
            }
        }
        catch (Exception e){};
    }
    /**
     * This is function click item on list
     */
    private void ClickItem()
    {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int _position, long id) {
                ResetColor();
                view.setBackgroundColor(Color.GRAY);
                position = _position;
            }
        });
    }
    /**
     * This is function handle create list itemhistory and put it on listview
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        mListItemHistory = new ArrayList<>();
        AddListHistory();
        mListView = view.findViewById(R.id.view_history);
        history_adapter =
                new History_Adapter(getContext(),R.layout.history_view,mListItemHistory);
        mListView.setAdapter(history_adapter);
        //Click item
        ClickItem();
        // Inflate the layout for this fragment
        return view;
    }
    /**
     * This is function create dialog comfirm remove
     */
    private void ConfirmRemove()
    {
        AlertDialog.Builder aBuilder = new AlertDialog.Builder(this.getContext());
        aBuilder.setMessage("Are you sure !!! you will lose these forever");
        aBuilder.setTitle("Warning");
        aBuilder.setIcon(R.drawable.ic_warning);
        aBuilder.setPositiveButton("Yes I want", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RemoveItem();
            }
        });
        aBuilder.show();
    }
    /**
     * This is functions create menu remove
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_remove,menu);
    }
    /**
     * This is function click items menu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.remove:
                if( mListItemHistory.size()!=0)
                    ConfirmRemove();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
