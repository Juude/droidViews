package net.juude.transitiondemos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

/**
 * Created by juude on 16/6/1.
 */
public class ListFragment extends Fragment{

    private static String[] mImages = new String[] {
      "https://www.juhe.cn/themes/images/data/new/a8.png",
     "https://www.juhe.cn/themes/images/data/new/a36.png"
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final RecyclerView recyclerView = new RecyclerView(getActivity());
        recyclerView.setBackgroundColor(Color.RED);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new ImageAdapter(getActivity()));
        recyclerView.setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return recyclerView;
    }


    static class ImageAdapter extends RecyclerView.Adapter<ImageViewHolder> implements View.OnClickListener {

        private final Context mContext;

        public ImageAdapter(Context context) {
            mContext = context;
        }

        @Override
        public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = new ImageView(mContext);
            v.setOnClickListener(this);
            v.setLayoutParams(new ViewGroup.LayoutParams(200, 200));
            return new ImageViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ImageViewHolder holder, int position) {
            holder.itemView.setTag(position);
            Picasso.with(mContext).load(mImages[position]).into((ImageView) holder.itemView);
        }

        @Override
        public int getItemCount() {
            return mImages.length;
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(mContext, DetailActivity.class);
            final String path = mImages[(int) v.getTag()];
            i.putExtra("path", path);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                v.setTransitionName(path);
            }
            ActivityOptionsCompat transitionActivityOptions =
                    ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, v, path);
            ActivityCompat.startActivity((Activity) mContext, i, transitionActivityOptions.toBundle());
        }
    }
    static class ImageViewHolder extends RecyclerView.ViewHolder {

        public ImageViewHolder(View itemView) {
            super(itemView);
        }
    }
}
