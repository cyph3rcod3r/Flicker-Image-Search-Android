package com.flik.flickerimagelist;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.flik.flickerimagelist.models.Item;
import com.flik.flickerimagelist.netio.Utils;
import com.flik.flickerimagelist.ui.DataAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        edtSearch = (EditText) findViewById(R.id.edtSearch);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(edtSearch);
                String query = edtSearch.getText().toString().trim();
                if (query.length() > 0) {
                    Snackbar.make(view, "Searching " + query, Snackbar.LENGTH_LONG)
                            .show();
                    new Async().execute(query);
                }
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        new Async().execute("universe");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class Async extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... query) {
            return Utils.getActualResponse(Utils.getResponse(query[0]));
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                Log.e("Res:--", s);
                try {
                    Item item = Utils.getGson().fromJson(s, Item.class);
                    if (item != null) {
                        setupRecycler(item.getItems());
                    }
                } catch (Exception e) {
                    Log.e("Gson:--", e.getMessage());
                }
            }
        }
    }

    private void setupRecycler(List<Item.Item_> item_s) {
        if (item_s.isEmpty()) {
            Toast.makeText(MainActivity.this, "No data found please search again", Toast.LENGTH_SHORT).show();
            return;
        }
        DataAdapter adapter = new DataAdapter(getApplicationContext(), item_s);
        recyclerView.setAdapter(adapter);
    }

    protected void hideSoftKeyboard(EditText input) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
    }
}
