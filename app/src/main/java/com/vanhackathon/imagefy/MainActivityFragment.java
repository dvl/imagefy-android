package com.vanhackathon.imagefy;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vanhackathon.imagefy.service.data.auth.Wish;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements WishesAdapter.OnItemSelectedListener {

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
    public void onItemSelected(Wish wish, int position) {

    }
}
