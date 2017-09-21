package com.example.vsriram2.myapplication;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.vsriram2.myapplication.ExpListViewAdapterWithCheckbox;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;
import android.widget.Toast;

import com.buildware.widget.indeterm.IndeterminateCheckBox;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static java.security.AccessController.getContext;

public class MainActivity extends Activity {



    //  ExpandableListAdapter listAdapter;
    ExpListViewAdapterWithCheckbox listAdapter;
    ExpandableListView expListView;
    IndeterminateCheckBox indetermCheck;
    ArrayList<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private static OkHttpClient mOkHttpClient;
    private static Context mInstance;

    public static Context getCon() {
        return mInstance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mInstance = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

/*        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Index");
        listDataHeader.add("Genre");
        listDataHeader.add("Certification");
        listDataHeader.add("Release year");
        listDataHeader.add("User rating");
        listDataHeader.add("Video resolution");
        listDataHeader.add("Others");*/

        listAdapter = new ExpListViewAdapterWithCheckbox(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                if (expListView.isGroupExpanded(groupPosition))
                    expListView.collapseGroup(groupPosition);
                else
                    //new BackgroundTask(groupPosition, listDataHeader.get(groupPosition)).execute();
                    expListView.expandGroup(groupPosition);

                return true;
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                // TODO Auto-generated method stub
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();

                if (v != null) {
                    CheckBox checkBox = (CheckBox) v.findViewById(R.id.lstcheckBox);
                    checkBox.setChecked(!checkBox.isChecked());
/*                    if (listAdapter.getNumberOfCheckedItemsInGroup(groupPosition) == 0)
                        listAdapter.getChk().setIndeterminate(true);*/
                }
 /*                   IndeterminateCheckBox indetermCheck = (IndeterminateCheckBox) parent.getChildAt(groupPosition).findViewById(R.id.lstcheckBox1);
                    indetermCheck.setIndeterminate(true);
                    if (listAdapter.getNumberOfCheckedItemsInGroup(groupPosition) == 0)
                        indetermCheck.setIndeterminate(false);
                }*/
                return false;
            }
        });




        Button button = (Button) findViewById(R.id.button);
        final TextView textView = (TextView) findViewById(R.id.textView);


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int count = 0;
                for (int mGroupPosition = 0; mGroupPosition < listAdapter.getGroupCount(); mGroupPosition++) {
                    count = count + listAdapter.getNumberOfCheckedItemsInGroup(mGroupPosition);
                }
                textView.setText("" + count);
            }
        });

    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Index");
        listDataHeader.add("Genre");
        listDataHeader.add("Certification");
        listDataHeader.add("Release year");
        listDataHeader.add("User rating");
        listDataHeader.add("Video resolution");
        listDataHeader.add("Others");

        // Adding child data
        List<String> Index = new ArrayList<String>();
        Index.add("The Shawshank Redemption");
        Index.add("The Godfather");
        Index.add("The Godfather: Part II");
        Index.add("Pulp Fiction");
        Index.add("The Good, the Bad and the Ugly");
        Index.add("The Dark Knight");
        Index.add("12 Angry Men");

        List<String> Genre = new ArrayList<String>();
        Genre.add("The Conjuring");
        Genre.add("Despicable Me 2");
        Genre.add("Turbo");
        Genre.add("Grown Ups 2");
        Genre.add("Red 2");
        Genre.add("The Wolverine");

        List<String> Certification = new ArrayList<String>();
        Certification.add("2 Guns");
        Certification.add("The Smurfs 2");
        Certification.add("The Spectacular Now");
        Certification.add("The Canyons");
        Certification.add("Europa Report");

        listDataChild.put(listDataHeader.get(0), Index); // Header, Child data
        listDataChild.put(listDataHeader.get(1), Genre);
        listDataChild.put(listDataHeader.get(2), Certification);
    }

    private class BackgroundTask extends AsyncTask<Void, Void, List<String>>
    {
        int groupPosition;
        String groupType;

        private BackgroundTask(int groupPosition, String groupType)
        {
            this.groupPosition = groupPosition;
            this.groupType = groupType;
        }

        @Override
        protected List<String> doInBackground(Void... params)
        {
            // TODO: Do your background computation...
            // Then return it
            List<String> Index = new ArrayList<String>();
            String URL = "http://pchportal.duckdns.org/NMJManagerTablet_web/getData.php?action=getMenu&dbpath=guerilla/nmj_database/media.db&filter=" + groupType.toLowerCase();
            JSONObject jObject = getJSONObject(getCon(), URL.replace(" ", "%20"));
            try {
                JSONArray jArray = jObject.getJSONArray("data");
                System.out.println("JSON Array: " + jArray.toString());
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject dObject = jArray.getJSONObject(i);
                    String name = getStringFromJSONObject(dObject, "id", "");
                    Index.add(name);
                }
            } catch(Exception e){
                System.out.println("Exception occurred: " + e.toString());
            }
            return Index;
        }



        @Override
        protected void onPostExecute(List<String> resultsList)
        {
            super.onPostExecute(resultsList);
            listDataChild.put(listDataHeader.get(groupPosition), resultsList);
            // TODO: Add the resultsList to whatever structure you're using to store the data in the ListView Adapter
            expListView.expandGroup(groupPosition);
        }
    }

    public static OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            File cacheDir = getCon().getCacheDir();
            Cache cache = new Cache(cacheDir, 2 * 1024 * 1024); // 25 MB cache
            mOkHttpClient = new OkHttpClient.Builder()
                    .cache(cache)
                    .build();
        }

        return mOkHttpClient;
    }

    public static String getStringFromJSONObject(JSONObject json, String name, String fallback) {
        try {
            String s = json.getString(name);
            if (s.equals("null"))
                return fallback;
            return s;
        } catch (Exception e) {
            return fallback;
        }
    }

    public static JSONObject getJSONObject(Context context, String url) {
        System.out.println("getJSONFromURL: " + url);
        final OkHttpClient client = getOkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute();

            if (response.code() >= 429) {
                // HTTP error 429 and above means that we've exceeded the query limit
                // for TMDb. Sleep for 5 seconds and try again.
                Thread.sleep(5000);
                response = client.newCall(request).execute();
            }
            String result = response.body().string();
            //System.out.println("Output: " + result);
            return new JSONObject(result);
        } catch (Exception e) { // IOException and JSONException
            return new JSONObject();
        }
    }
}