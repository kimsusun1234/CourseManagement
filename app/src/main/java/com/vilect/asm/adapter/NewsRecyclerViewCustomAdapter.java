package com.vilect.asm.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vilect.asm.R;
import com.vilect.asm.activity.WebViewActivity;

import static com.vilect.asm.LibraryClass.newsArrayList;

public class NewsRecyclerViewCustomAdapter extends RecyclerView.Adapter<NewsRecyclerViewCustomAdapter.ViewHolder> {

    private Context context;

    public NewsRecyclerViewCustomAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_recycler_view_news, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.txtPubDate.setText(newsArrayList.get(position).getPubDate());
        holder.txtTitle.setText(newsArrayList.get(position).getTitle());
        holder.txtLink.setText(newsArrayList.get(position).getLink());
        holder.txtLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("link", newsArrayList.get(position).getLink());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView txtPubDate;
        TextView txtTitle;
        TextView txtLink;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtPubDate = itemView.findViewById(R.id.txtPubDate);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtLink = itemView.findViewById(R.id.txtLink);
        }
    }
}
