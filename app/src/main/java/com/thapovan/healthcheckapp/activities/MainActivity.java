package com.thapovan.healthcheckapp.activities;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thapovan.healthcheckapp.R;
import com.thapovan.healthcheckapp.helper.RetrofitClientClass;
import com.thapovan.healthcheckapp.model.HeathDataResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private RecyclerView rvHealthList;
    private HealthListAdapter healthListAdapter;
    private AccessibleListAdapter accessibleListAdapter;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        getHealthListData();

    }

    //initialize views
    private void initUI() {
        toolbar = findViewById(R.id.toolbar);
        setToolBarTitle(toolbar, true, null);

        rvHealthList = findViewById(R.id.rv_healthList);
    }

    class HealthListAdapter extends RecyclerView.Adapter<HealthListAdapter.MyViewHolder> {
        List<HeathDataResponse.Health> healthList;

        public HealthListAdapter(List<HeathDataResponse.Health> healthList) {
            this.healthList = healthList;
        }

        @Override
        public HealthListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bb_recycler_healthlist, parent, false);
            return new HealthListAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(HealthListAdapter.MyViewHolder holder, int position) {
            holder.tvName.setText(healthList.get(position).getName());

            accessibleListAdapter = new AccessibleListAdapter(healthList.get(position).getAccessible());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            holder.rvAccessible.setLayoutManager(mLayoutManager);
            holder.rvAccessible.setAdapter(accessibleListAdapter);


        }

        @Override
        public int getItemCount() {
            return healthList.size();
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tvName;
            RecyclerView rvAccessible;


            public MyViewHolder(View view) {
                super(view);

                tvName = view.findViewById(R.id.tv_name);
                rvAccessible = view.findViewById(R.id.rv_accessible);
            }
        }
    }

    class AccessibleListAdapter extends RecyclerView.Adapter<AccessibleListAdapter.MyViewHolder> {

        List<HeathDataResponse.Accessible> accessible;

        public AccessibleListAdapter(List<HeathDataResponse.Accessible> accessible) {
            this.accessible = accessible;
        }

        @Override
        public AccessibleListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bb_recycler_healthlist_item, parent, false);
            return new AccessibleListAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(AccessibleListAdapter.MyViewHolder holder, int position) {
            if (accessible.get(position).getType() != null) {
                holder.tvAccessibleName.setText(accessible.get(position).getType());
            } else {
                holder.tvAccessibleName.setText(accessible.get(position).getName());
            }

            holder.tvAccessibleStatus.setText(accessible.get(position).getSuccess().toString());

            if (accessible.get(position).getSuccess()) {
                holder.tvAccessibleStatus.setTextColor(getResources().getColorStateList(R.color.green));
            } else {
                holder.tvAccessibleStatus.setTextColor(getResources().getColorStateList(R.color.red));
            }

            if (position == (accessible.size() - 1)) {
                holder.view_divider.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return accessible.size();
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tvAccessibleName, tvAccessibleStatus;
            View view_divider;


            public MyViewHolder(View view) {
                super(view);

                view_divider = view.findViewById(R.id.view_divider);
                tvAccessibleName = view.findViewById(R.id.tv_accessible_name);
                tvAccessibleStatus = view.findViewById(R.id.tv_accessible_status);
            }
        }


    }

    private void getHealthListData() {
        showProgressDialog();
        Call<HeathDataResponse> call = RetrofitClientClass.getInstance().getRestClient().getHealthDataList();
        call.enqueue(new Callback<HeathDataResponse>() {
            @Override
            public void onResponse(Call<HeathDataResponse> call, Response<HeathDataResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    if (response.body().getStatusCode() == 200 && response.body().getSuccess()) {
                        List<HeathDataResponse.Health> healthList = response.body().getData().getHealth();
                        if (healthList.size() > 0) {
                            rvHealthList.setVisibility(View.VISIBLE);
                            healthListAdapter = new HealthListAdapter(healthList);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            rvHealthList.setLayoutManager(mLayoutManager);
                            rvHealthList.setAdapter(healthListAdapter);
                        } else {
                            rvHealthList.setVisibility(View.GONE);
                        }
                    } else {
                        rvHealthList.setVisibility(View.GONE);
                    }
                }


            }

            @Override
            public void onFailure(Call<HeathDataResponse> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }

        });

    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {

            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.show();

        } else {
            if (!isFinishing()) {
                mProgressDialog.show();
            }

        }
    }

    public void hideProgressDialog() {
        try {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
        } catch (Exception e) {

        }
    }

    public void setToolBarTitle(Toolbar toolBar, boolean isDisplayHomeAsUpEnabled, String title) {
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(isDisplayHomeAsUpEnabled);
        if (title != null) {
            getSupportActionBar().setTitle(title);
        } else {
            getSupportActionBar().setTitle("");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}