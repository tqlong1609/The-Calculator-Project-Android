package com.group10.calculator;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.os.Bundle;
import android.text.Editable;
import android.widget.EditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.support.annotation.Nullable;
import android.widget.Spinner;
import android.widget.TextView;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * This is class of children fragment
 */
public class UnitFragment extends Fragment {

    private View fragment;
    private int[] tvUnitIds;
    private int[] tvValueIds;
    private TextView[] tvUnits;
    private TextView[] tvValues;
    private EditText etInput;
    private Spinner spUnit;
    private UnitConverter converter;
    private int curSignUnitChoice;
    private ArrayAdapter<String> adapter;
    //public List<String> listNameUnit = new ArrayList<String>();
    private String unitOfFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragment = inflater.inflate(R.layout.fragment_unit, container, false);
        converter  = new UnitConverter();
        curSignUnitChoice =0;
        etInput = fragment.findViewById(R.id.etinput);
        spUnit = fragment.findViewById(R.id.spunit);
        tvUnitIds = new int[]{R.id.tvunit1,R.id.tvunit2,R.id.tvunit3,
                R.id.tvunit4,R.id.tvunit5,R.id.tvunit6};
        tvValueIds = new int[] {R.id.tvvalue1,R.id.tvvalue2,R.id.tvvalue3,
                R.id.tvvalue4,R.id.tvvalue5,R.id.tvvalue6};
        MappingView();
        Bundle bundle = getArguments();
        unitOfFragment = bundle.getString("UNIT");
        adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, bundle.getStringArray("SIGN"));
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spUnit.setAdapter(adapter);

        AssignEventToSpinner();
        AssignEventToEditText();
        return fragment;
    }

    /**
     * This is function assign Event To Spinner
     * Value of different textbox change when spinner selected and edittext content value
     */
    private void AssignEventToSpinner() {
        spUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                curSignUnitChoice =i;
                if(!etInput.getText().toString().equals("")) {
                    converter.Confirm(etInput.getText().toString(),unitOfFragment,curSignUnitChoice);
                    for(int j=0;j<converter.GetArrName().length;j++)
                    {
                        tvValues[j].setText(String.valueOf(converter.GetArrValue()[j]));
                        tvUnits[j].setText(converter.GetArrName()[j]);
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                curSignUnitChoice =0;
            }
        });
    }

    /**
     * This is function assign Event To EditText
     * Value of different unit change when text of EditText change
     */
    private void AssignEventToEditText() {
        etInput.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.equals("") ) {
                    converter.Confirm(etInput.getText().toString(),unitOfFragment,curSignUnitChoice);
                    for(int i=0;i<converter.GetArrName().length;i++)
                    {
                        tvValues[i].setText(String.valueOf(converter.GetArrValue()[i]));
                        tvUnits[i].setText(converter.GetArrName()[i]);
                    }
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void afterTextChanged(Editable s) { }
        });
    }

    /**
     * This is function mapping all view to id of view on fragment
     */
    private void MappingView()
    {
        tvUnits = new TextView[6];
        tvValues = new TextView[6];
        for(int i=0;i<tvUnitIds.length;i++)
        {
            tvUnits[i] = fragment.findViewById(tvUnitIds[i]);
            tvValues[i] = fragment.findViewById(tvValueIds[i]);
        }
    }
}
