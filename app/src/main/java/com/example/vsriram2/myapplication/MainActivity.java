package com.example.vsriram2.myapplication;


        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import android.app.Activity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ExpandableListView;
        import android.widget.ExpandableListView.OnChildClickListener;
        import android.widget.ExpandableListView.OnGroupClickListener;
        import android.widget.ExpandableListView.OnGroupCollapseListener;
        import android.widget.ExpandableListView.OnGroupExpandListener;
        import android.widget.TextView;
        import android.widget.Toast;

public class MainActivity extends Activity {

  //  ExpandableListAdapter listAdapter;
    ExpListViewAdapterWithCheckbox listAdapter;
    ExpandableListView expListView;
    ArrayList<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpListViewAdapterWithCheckbox(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
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

                return false;
            }
        });

        Button button = (Button) findViewById(R.id.button);
       final TextView textView = (TextView) findViewById(R.id.textView);


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int count =0;
                for(int mGroupPosition =0; mGroupPosition < listAdapter.getGroupCount(); mGroupPosition++)
                {
                    count = count +  listAdapter.getNumberOfCheckedItemsInGroup(mGroupPosition);
                }
                textView.setText(""+count);
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
}