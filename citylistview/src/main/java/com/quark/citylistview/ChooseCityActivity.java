package com.quark.citylistview;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sortlistview.R;
import com.mrwujay.cascade.model.ProvinceModel;
import com.mrwujay.cascade.service.XmlParserHandler;
import com.quark.citylistview.SideBar.OnTouchingLetterChangedListener;

public class ChooseCityActivity extends Activity {
	private ListView sortListView;
	private SideBar sideBar;
	private TextView dialog;
	private SortAdapter adapter;
	private ClearEditText mClearEditText;
	String dingweiCity="";
	String dingweiProvince="";
	String currentCity="";
	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	private List<SortModel> SourceDateList = new ArrayList<SortModel>();
	
	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.city_list);
		
		currentCity = getIntent().getStringExtra("currentCity");
		dingweiCity = getIntent().getStringExtra("dingweiCity");
		dingweiProvince = getIntent().getStringExtra("dingweiProvince");
		TextView curren = (TextView)findViewById(R.id.curren);
		curren.setText("当前定位-"+dingweiCity);
		initViews();
		
		LinearLayout backlay = (LinearLayout)findViewById(R.id.backlay);
		ImageButton back = (ImageButton)findViewById(R.id.back);
		backlay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	
	private void initViews() {
		//实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();
		pinyinComparator = new PinyinComparator();
		sideBar = (SideBar) findViewById(R.id.sidrbar);
		dialog = (TextView) findViewById(R.id.dialog);
		sideBar.setTextView(dialog);
		
		//设置右侧触摸监听
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {
			
			@Override
			public void onTouchingLetterChanged(String s) {
				if(s.equals("定位城市")){
					sortListView.setSelection(0);
				}else if(s.equals("热门城市")){
					sortListView.setSelection(1);
				}else{
					//该字母首次出现的位置
					int position = adapter.getPositionForSection(s.charAt(0));
					if(position != -1){
						sortListView.setSelection(position);
					}
				}
			}
		});
		
		
		sortListView = (ListView) findViewById(R.id.country_lvcountry);
		sortListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//这里要利用adapter.getItem(position)来获取当前position所对应的对象
				Toast.makeText(getApplication(), ((SortModel)adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
				Intent intent=new Intent(); 
				
		        intent.putExtra("province", ((SortModel)adapter.getItem(position)).getProvince()); 
		        intent.putExtra("city", ((SortModel)adapter.getItem(position)).getName());
		        
		        ChooseCityActivity.this.setResult(RESULT_OK, intent); 
		        ChooseCityActivity.this.finish();
			}
		});
		
//		SourceDateList = filledData(getResources().getStringArray(R.array.date));
		SourceDateList =  getData();
		
		// 根据a-z进行排序源数据
		Collections.sort(SourceDateList, pinyinComparator);
		//在排序好的list前面增加 定位城市和热门城市    上海 北京 广州 深圳 武汉 天津 西安 南京 杭州 成都 重庆
		String[] dr = new String[]{dingweiCity,"上海市","北京市","广州市","深圳市","武汉市","天津市","西安市","南京市","杭州市","成都市","重庆市"};
		String[] pr = new String[]{dingweiProvince,"上海","北京","广东省","广东省","湖北省","天津市","陕西省","江苏省","浙江省","四川省","重庆市"};
		for (int j = 0; j < 12; j++) {
			SortModel sortModel = new SortModel();
			sortModel.setName(dr[j]);
			sortModel.setProvince(pr[j]);
			//汉字转换成拼音
//			String pinyin = characterParser.getSelling(dr[j]);
//			String sortString = pinyin.substring(0, 1).toUpperCase();
			
			// 正则表达式，判断首字母是否是英文字母
//			if(sortString.matches("[A-Z]")){
//				sortModel.setSortLetters(sortString.toUpperCase());
//			}else{
				sortModel.setSortLetters("%");
//			}
			
			SourceDateList.add(j, sortModel);
		}
		
		
		
		adapter = new SortAdapter(this, SourceDateList);
		sortListView.setAdapter(adapter);
		
		mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);
		
		//根据输入框输入值的改变来过滤搜索
		mClearEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				//当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
				filterData(s.toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}


	/**
	 * 为ListView填充数据
	 * @param date
	 * @return
	 */
	private List<SortModel> filledData(String [] date){
		List<SortModel> mSortList = new ArrayList<SortModel>();
		
		for(int i=0; i<date.length; i++){
			SortModel sortModel = new SortModel();
			sortModel.setName(date[i]);
			//汉字转换成拼音
			String pinyin = characterParser.getSelling(date[i]);
			String sortString = pinyin.substring(0, 1).toUpperCase();
			
			// 正则表达式，判断首字母是否是英文字母
			if(sortString.matches("[A-Z]")){
				sortModel.setSortLetters(sortString.toUpperCase());
			}else{
				sortModel.setSortLetters("#");
			}
			
			mSortList.add(sortModel);
		}
		return mSortList;
		
	}
	
	public List<SortModel> getData(){
		List<SortModel> mSortList = new ArrayList<SortModel>();
	try {
		List<ProvinceModel> provinceList = null;
    	AssetManager asset = getAssets();
		InputStream input;
		input = asset.open("province_data.xml");
        // 创建一个解析xml的工厂对象
		SAXParserFactory spf = SAXParserFactory.newInstance();
		// 解析xml
		SAXParser parser = spf.newSAXParser();
		XmlParserHandler handler = new XmlParserHandler();
		parser.parse(input, handler);
		input.close();
		// 获取解析出来的数据
		provinceList = handler.getDataList();
		System.out.println(provinceList.size());
		for (int i = 0; i < provinceList.size(); i++) {
			for (int k = 0; k < provinceList.get(i).getCityList().size(); k++) {
//				SortModel p = new SortModel();
//				p.setName(provinceList.get(i).getCityList().get(k).getName());
//				p.setProvince(provinceList.get(i).getName());
//				SourceDateList.add(p);
				
				SortModel sortModel = new SortModel();
				sortModel.setName(provinceList.get(i).getCityList().get(k).getName());
				//汉字转换成拼音
				String pinyin = characterParser.getSelling(provinceList.get(i).getCityList().get(k).getName());
				String sortString = pinyin.substring(0, 1).toUpperCase();
				
				// 正则表达式，判断首字母是否是英文字母
				if(sortString.matches("[A-Z]")){
					sortModel.setSortLetters(sortString.toUpperCase());
				}else{
					sortModel.setSortLetters("#");
				}
				sortModel.setProvince(provinceList.get(i).getName());
				
				mSortList.add(sortModel);
			}
		}
	} catch (Throwable e) {  
        e.printStackTrace();  
    } finally {
    	
    } 
	
	return mSortList;
}
	
	
	/**
	 * 根据输入框中的值来过滤数据并更新ListView
	 * @param filterStr
	 */
	private void filterData(String filterStr){
		List<SortModel> filterDateList = new ArrayList<SortModel>();
		
		if(TextUtils.isEmpty(filterStr)){
			filterDateList = SourceDateList;
		}else{
			filterDateList.clear();
			for(SortModel sortModel : SourceDateList){
				String name = sortModel.getName();
				if(name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())){
					filterDateList.add(sortModel);
				}
			}
		}
		
		// 根据a-z进行排序
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}
	
}
