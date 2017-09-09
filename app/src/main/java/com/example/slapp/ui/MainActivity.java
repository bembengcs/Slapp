package com.example.slapp.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.slapp.R;
import com.example.slapp.adapter.KataAdapter;
import com.example.slapp.data.ApiClient;
import com.example.slapp.data.ApiInterface;
import com.example.slapp.data.KosaKata;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements KataAdapter.OnItemClickListener {

    @BindView(R.id.rv_kata)
    RecyclerView mRvKata;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;

    private ApiInterface apiService;
    private KataAdapter mKataAdapter;
    private String TAG;
    private List<KosaKata> kosaKata = new ArrayList<>();
    private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        setTitle(R.string.app_name);

        mLinearLayout = linearLayout;

        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        loadAllKosaKata();
    }

    private void loadAllKosaKata() {
        Call<List<KosaKata>> call = apiService.getAllKosaKata();
        call.enqueue(new Callback<List<KosaKata>>() {
            @Override
            public void onResponse(Call<List<KosaKata>> call, Response<List<KosaKata>> response) {
                if (response.isSuccessful()) {
                    kosaKata.clear();
                    kosaKata.addAll(response.body());
                    setupAdapter(kosaKata);
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(MainActivity.this, "Failed to get data!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<KosaKata>> call, Throwable t) {
                mProgressBar.setVisibility(View.GONE);
                Snackbar snackbar = Snackbar
                        .make(mLinearLayout, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                        .setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mProgressBar.setVisibility(View.VISIBLE);
                                loadAllKosaKata();
                            }
                        });

                // Changing message text color
                snackbar.setActionTextColor(Color.RED);

                // Changing action button text color
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);
                snackbar.show();
            }
        });
    }

    private void setupAdapter(List<KosaKata> kosaKata) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvKata.setLayoutManager(linearLayoutManager);
        mKataAdapter = new KataAdapter(kosaKata, this, this);
        mRvKata.setAdapter(mKataAdapter);
    }

    @Override
    public void onItemClick(String link) {
        Intent intent = new Intent(this, VideoContainerActivity.class);
        intent.putExtra("link", link);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mKataAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }
}
