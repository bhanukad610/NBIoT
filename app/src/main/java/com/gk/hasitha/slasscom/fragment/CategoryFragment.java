package com.gk.hasitha.slasscom.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.gk.hasitha.slasscom.adapter.CategoryAdapter;
import com.gk.hasitha.slasscom.data.CategoryData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import aluth.solutions.etwinkle.aluth.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {

    private static ArrayList<CategoryData> categoryDataList = new ArrayList<>();
    private static CategoryAdapter categoryAdapter;
    private static final boolean GRID_LAYOUT = true;
    private static final int ITEM_COUNT = 100;
    private String imgUrl;

    public CategoryFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        getCategories();
        final List<Object> items = new ArrayList<>();

        imgUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTxpgTvfjDzyBBzR5V_V5VZi1VG823CIPOn2m1Z_9RfGkjgYDhj";

        for (int i = 0; i < ITEM_COUNT; ++i) {
            items.add(new Object());
        }

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        categoryAdapter = new CategoryAdapter(getContext(),categoryDataList);

        //setup materialviewpager

        if (GRID_LAYOUT) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        mRecyclerView.setHasFixedSize(true);

        //Use this now
        mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
        mRecyclerView.setAdapter(categoryAdapter);
    }

    public void getCategories2(){
        CategoryData categoryData = new CategoryData("1","1", "Humidity sensor","Control your humidity sensor",
                "humi","humi");
        categoryDataList.add(categoryData);
        CategoryData categoryData1 = new CategoryData("2","2", "Temperature sensor","Control your temperature sensor",
                "temp","temp");
        categoryDataList.add(categoryData1);
        CategoryData categoryData2 = new CategoryData("3","3", "Moisture sensor","Control your moisture sensor",
                "mois","mois");
        categoryDataList.add(categoryData2);
    }

    public void getCategories(){

        AsyncTask<Integer,Void,Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                CategoryData categoryData = new CategoryData("1","1", "Humidity sensor","Control your humidity sensor",
                        "humi","humi");
                categoryDataList.add(categoryData);
                CategoryData categoryData1 = new CategoryData("2","2", "Temperature sensor","Control your temperature sensor",
                        "temp","temp");
                categoryDataList.add(categoryData1);
                CategoryData categoryData2 = new CategoryData("3","3", "Moisture sensor","Control your moisture sensor",
                        "mois","mois");
                categoryDataList.add(categoryData2);
                CategoryData categoryData3 = new CategoryData("4","4", "Water tap","Control your water tap",
                        "wat","wat");
                categoryDataList.add(categoryData3);

                /*
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://www.lankacarmart.lk/gk/categories2.php")
                        .build();

                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());
                    Log.d("yahoo",array.toString());
                    for (int i=0; i<1; i++){

                        JSONObject object = array.getJSONObject(i);

                        CategoryData categoryData = new CategoryData(object.getString("id"),object.getString("cat_id"), "Water sensor","Control your water sensor",
                                imgUrl,"IoT");
                        Log.d("yahooooll",object.getString("cat_id"));
                        categoryDataList.add(categoryData);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                */
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                categoryAdapter.notifyDataSetChanged();

            }
        };

        task.execute(0);

    }

}
