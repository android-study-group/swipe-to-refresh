package br.com.rafaelportela.swipetoreload;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity {

    private String[] catNames;

    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // current available data
        catNames =  getResources().getStringArray(R.array.brazilian_cities);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh);
        listView = (ListView) findViewById(R.id.activity_main_listview);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, catNames);

        listView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("MainActivity", "Refreshing...");

                // your background task, or async call with callbacks, to load data
                new FakeDataLoader(swipeRefreshLayout).execute((Void) null);
            }
        });
    }
}

/**
 * Dummy async task that waits 2 seconds, so you can see the nice spinning on the screen.
 */
class FakeDataLoader extends AsyncTask<Void, Void, Void> {

    private SwipeRefreshLayout swipeRefreshLayout;

    public FakeDataLoader(SwipeRefreshLayout swipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Log.i("FakeDataLoader", " Stopping the spinning.");

        // Back to the UI Thread update the screen with fresh data and stop the spinning animation.
        swipeRefreshLayout.setRefreshing(false);
    }
}