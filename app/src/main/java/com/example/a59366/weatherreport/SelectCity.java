package com.example.a59366.weatherreport;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a59366.app.MyApplication;
import com.example.a59366.bean.City;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectCity extends Activity implements View.OnClickListener {
    private ImageView mBackBtn;
    private ListView listView = null;
    private TextView cityselected = null;
    private List<City> listcity = MyApplication.getInstance().getCityList();
    private int listSize = listcity.size();
    private String[] city = new String[listSize];
    private EditText mSearch;
    private ArrayList<String> mSearchResult = new ArrayList<>();
    private Map<String,String> nameToCode = new HashMap<>();
    private Map<String,String> nameToPinyin = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_city);
        mBackBtn = (ImageView) findViewById(R.id.title_back);
        cityselected = (TextView) findViewById(R.id.title_name);
        mBackBtn.setOnClickListener(this);
        listView=(ListView) findViewById(R.id.title_list);
        mSearch = (EditText)findViewById(R.id.search_edit);
        initViews();
    }

    private void initViews() {

        Log.d("city", listcity.get(1).getCity());
        for (int i = 0; i < listSize; i++) {
            city[i] = listcity.get(i).getCity();
            Log.d("City", city[i]);
        }
        String strName;
        String strNamePinyin;
        String strCode;

        for(City city1:listcity){
            strCode = city1.getNumber();
            strName = city1.getCity();
            strNamePinyin = city1.getFirstPY();
            nameToCode.put(strName,strCode);
            nameToPinyin.put(strName,strNamePinyin);
            mSearchResult.add(strName);
        }
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, city);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(SelectCity.this,"你已选择"+city[i],Toast.LENGTH_SHORT).show();
                cityselected.setText("当前城市："+city[i]);
            }
        });
        mSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                arrayAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                int position = listView.getCheckedItemPosition();
                String select_cityCode = listcity.get(position).getNumber();
                Intent intent = new Intent();
                intent.putExtra("cityCode",select_cityCode);
                setResult(RESULT_OK,intent);
                Log.d("citycode", select_cityCode);
                finish();
                break;
            case R.id.title_name:
                Log.d("citycode", "北京");
            default:
                break;
        }
    }
}
