package com.vanhackathon.imagefy;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vanhackathon.imagefy.service.WishesService;
import com.vanhackathon.imagefy.service.data.auth.Wish;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements WishesAdapter.OnItemSelectedListener {

    private static final String TAG = "MAIN";
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayout;
    private WishesAdapter mAdapter;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        mAdapter = new WishesAdapter(getContext(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_main);

        mLinearLayout = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayout);

        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        update();
    }

    @Override
    public void onItemSelected(Wish wish, int position) {
    }

    private void update() {
        Call<List<Wish>> call = WishesService.getInstance(
                LocalLogin
                        .loginToken(getContext()))
                .imagefyWishesApi
                .get();

        call.enqueue(new Callback<List<Wish>>() {
            @Override
            public void onResponse(Call<List<Wish>> call, Response<List<Wish>> response) {
                Log.d(TAG, "response: " + response);
                if(response.body() != null) {
                    ArrayList<Wish> res = new ArrayList<Wish>(response.body());
                    Log.d(TAG, res.toString());
                    mAdapter.clearAll();
                    mAdapter.addAll(res);
                }
            }

            @Override
            public void onFailure(Call<List<Wish>> call, Throwable t) {
                Log.d(TAG, "fail", t);
                Toast.makeText(getActivity(), getString(R.string.list_error), Toast.LENGTH_LONG).show();
            }
        });
    }
}
