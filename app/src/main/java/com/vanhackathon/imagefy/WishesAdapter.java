package com.vanhackathon.imagefy;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class WishesAdapter extends RecyclerView.Adapter<WishesAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Wish> items = new ArrayList<>();
    private OnItemSelectedListener mOnItemSelectedListener;

    public interface OnItemSelectedListener {
        void onItemSelected(Wish wish, int position);
    }

    public WishesAdapter(Context c, OnItemSelectedListener onItemSelectedListener) {
        mContext = c;
        mOnItemSelectedListener = onItemSelectedListener;
    }

    public void clearAll() {
        items.clear();
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<Wish> wishes) {
        this.items.addAll(wishes);
        notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.wish_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Wish wish = items.get(position);

//        String path = "http://image.tmdb.org/t/p/w185/" + movie.getPosterPath();
//        Picasso.with(mContext).load(path).into(holder.img);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemSelectedListener.onItemSelected(wish, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView img;
        public TextView title;
        public CardView card;

        public ViewHolder(View v) {
            super(v);
            img = (ImageView) v.findViewById(R.id.wish_item_img);
            title = (TextView) v.findViewById(R.id.wish_item_desc);
            card = (CardView) v.findViewById(R.id.card_view);
        }
    }
}