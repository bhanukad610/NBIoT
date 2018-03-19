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
import com.gk.hasitha.slasscom.adapter.RecyclerViewAdapter;

import org.jsoup.Jsoup;
import org.jsoup.UncheckedIOException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import aluth.solutions.etwinkle.aluth.R;

import com.gk.hasitha.slasscom.data.AluthRecent;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class RecyclerViewFragment extends Fragment {

    private static final boolean GRID_LAYOUT = false;
    private static final int ITEM_COUNT = 100;
    private static ArrayList<AluthRecent> aluthRecentArrayList = new ArrayList<>();
    private static RecyclerViewAdapter recyclerViewAdapter;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    public static RecyclerViewFragment newInstance() {
        return new RecyclerViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        getAluth();
        final List<Object> items = new ArrayList<>();

        for (int i = 0; i < ITEM_COUNT; ++i) {
            items.add(new Object());
        }

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerViewAdapter = new RecyclerViewAdapter(getContext(),aluthRecentArrayList);

        //setup materialviewpager

        if (GRID_LAYOUT) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        mRecyclerView.setHasFixedSize(true);

        //Use this now
        mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
        mRecyclerView.setAdapter(recyclerViewAdapter);
    }

    public static void getAluth(){
        //dataList = new ArrayList<>();
        disableSSLCertificateChecking();
        AsyncTask<String,Void,String> task = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                disableSSLCertificateChecking();
                System.setProperty("javax.net.ssl.trustStore", "/home/Downloads/mytemp.jks");
                Document document;
                Document document2;
//keytool -import -v -file /home/hasitha/musiclk.crt -keystore /home/hasitha/Downloads/Mayur 11-9/Musico_1.0 (2)/Musico/Musico.jks -storepass svaasahia
                try {
                    System.setProperty("javax.net.ssl.trustStore", "/home/Downloads/mytemp.jks");
                    document = Jsoup.connect("http://www.aluth.com/").validateTLSCertificates(true).get();
                    Log.d("TAG1",document.toString());
                    Elements elements = document.select("div.date-outer");
                    Log.d("TAG",elements.toString());
                    int i = 0;
                    for(Element div : elements){
                        Elements title = div.select("div.date-posts div.post-outer div.post.hentry h3.post-title.entry-title a");
                        Log.d("TAG2",title.text());
                        String strTitle = title.text();
                        String link = title.attr("href");
                        Elements tempAddress = div.select("div.date-posts div.post-outer div.post.hentry a");
                        String strTempAddress = tempAddress.attr("name");

                        Elements secondTitle = div.select("div.date-posts div.post-outer div.post.hentry div#post-body-"+strTempAddress+".post-body.entry-content div div span");
                        Log.d("TAG3",secondTitle.text());

                        Elements imgSrc = div.select("div.date-posts div.post-outer div.post.hentry div#post-body-"+strTempAddress+".post-body.entry-content div div div.separator a img");
                        Log.d("TAG5",imgSrc.attr("src"));

                        Elements temmm = div.select("div.date-posts div.post-outer div.post.hentry div#post-body-"+strTempAddress+".post-body.entry-content div div span");
                        Log.d("TAG4",temmm.text());

                        String strDescription = temmm.text();

                        String strImgSrc = imgSrc.attr("src");

                        Elements secondLink = div.select("div.date-posts div.post-outer div.post.hentry h3.post-title.entry-title a");

                        Log.d("SecondLink",secondLink.attr("href"));

                        document2 = Jsoup.connect(secondLink.attr("href")).validateTLSCertificates(true).get();

                        Elements content = document2.select("html body#sitemainbody.bodytype-item div#wrapper div#outer-wrapper div#wrap2 div#content-wrapper div#main-wrapper div#main.main.section div#Blog1.widget.Blog div.blog-posts.hfeed div.date-outer div.date-posts div.post-outer div.post.hentry div#post-body-2097530595396322691.post-body.entry-content div");

                        AluthRecent aluthRecent = new AluthRecent(i,strTitle,strImgSrc,strDescription,content.toString());
                        aluthRecentArrayList.add(aluthRecent);
                        //dataList.add(new ItemEntity(strTitle,strTempAddress,strImgSrc,strTitle,secondTitle.text(),strTitle,strImgSrc));
                        i++;

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (UncheckedIOException un){}

                return "";
            }

            @Override
            protected void onPostExecute(String s) {
                //pileAdapter.notifyAll();
                recyclerViewAdapter.notifyDataSetChanged();
                recyclerViewAdapter.updateData(aluthRecentArrayList);
            }
        };
        task.execute();
    }

    private static void disableSSLCertificateChecking() {
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                // Not implemented
            }

            @Override
            public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                // Not implemented
            }
        } };

        try {
            SSLContext sc = SSLContext.getInstance("TLS");

            sc.init(null, trustAllCerts, new java.security.SecureRandom());

            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
