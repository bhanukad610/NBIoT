package com.gk.hasitha.slasscom.adapter;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import aluth.solutions.etwinkle.aluth.R;
import com.gk.hasitha.slasscom.activity.ScrollingActivity;
import com.gk.hasitha.slasscom.data.AluthRecent;
import com.gk.hasitha.slasscom.view.FadeTransitionImageView;

/**
 * Created by hasitha on 1/19/18.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements View.OnClickListener {

    private ArrayList<AluthRecent> items;
    private OnItemClickListener onItemClickListener;
    private Context context;
    private String realLink;

    public RecyclerViewAdapter(Context context, ArrayList<AluthRecent> items) {
        this.items = items;
        this.context = context;
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_card_big_2, parent, false);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    public void updateData(ArrayList<AluthRecent> viewModels) {
        /*
        int preSize = items.size();
        items.clear();
        items = new ArrayList<>();
        items.addAll(viewModels);
        */
        notifyDataSetChanged();
        //notifyItemRangeChanged(v,items.size());
    }
    /*
    public void addItem(int position, SongData viewModel) {
        items.add(position, viewModel);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

*/

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        try {
            final AluthRecent item = items.get(position);
            holder.fadeTransitionImageView.firstInit(items.get(position).getImgsrc());
            holder.title.setText(items.get(position).getTitle());
            holder.description.setText(items.get(position).getDescription());
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ScrollingActivity.class);
                    intent.putExtra("imgUrl",items.get(position).getImgsrc());
                    intent.putExtra("content",items.get(position).getSubTitle());
                    context.startActivity(intent);
                }
            });
        } catch (IndexOutOfBoundsException in){

        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onClick(final View v) {
        // Give some time to the ripple to finish the effect
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView description;
        public FadeTransitionImageView fadeTransitionImageView;
        private CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            description = (TextView) itemView.findViewById(R.id.description);
            title = (TextView) itemView.findViewById(R.id.title);
            fadeTransitionImageView = (FadeTransitionImageView) itemView.findViewById(R.id.bottomImageView);

        }
    }

    public interface OnItemClickListener {

        void onItemClick(View view, String viewModel);

    }

    public void download(String name, String link, View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(context,android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //Log.v(TAG,"Permission is granted");
                //File write logic here
            }else {
                ActivityCompat.requestPermissions((Activity)v.getContext(), new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                Log.d("h", "1done");
            }
        }
        try {
            String storagePath = Environment.getExternalStorageDirectory()
                    .getPath()
                    + "/eTwinkleSolutions/Music";
            //Log.d("Strorgae in view",""+storagePath);
            File f = new File(storagePath);
            if (!f.exists()) {
                f.mkdirs();
            }
            //storagePath.mkdirs();
            String pathname = f.toString();
            if (!f.exists()) {
                f.mkdirs();
            }
            DownloadManager downloadManager = (DownloadManager) v.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri = Uri.parse(link);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir("/eTwinkleSolutions/Music", name+".mp3");
            Long referrence = downloadManager.enqueue(request);
            Log.d("h", "done");
        }catch (IllegalArgumentException i){
            Toast.makeText(context,"Sorry! Download failed!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Disables the SSL certificate checking for new instances of {@link HttpsURLConnection} This has been created to
     * aid testing on a local box, not for use on production.
     */
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
/*
    public void createInterstitial() {
        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(context, context.getString(R.string.mobileSDK));
        interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdUnitId(context.getString(R.string.interstitial_ad));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

            }
            @Override
            public void onAdClosed() {
                loadInterstitial();
            }
        });
    }

    public void loadInterstitial() {
        AdRequest interstitialRequest = new AdRequest.Builder().build();
        interstitialAd.loadAd(interstitialRequest);
    }

    public void showInterstitial() {
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {
            loadInterstitial();
        }
    }
*/
}