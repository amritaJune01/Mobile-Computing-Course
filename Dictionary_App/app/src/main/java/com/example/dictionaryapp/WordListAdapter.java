package com.example.dictionaryapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordListViewHolder> {

    private final List<Meaning> meaningsList;
    private final WordDetailFragment wordDetailFragment = new WordDetailFragment();
    public pos_on_click onClick;

    public WordListAdapter(List<Meaning> list, pos_on_click pos_on_click) {
        this.onClick = pos_on_click;
        this.meaningsList = list;
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.word_list_item;
    }

    @NonNull
    @Override
    public WordListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_list_item, parent, false);
        final WordListViewHolder holder = new WordListViewHolder(view);
        view.setOnClickListener(v -> {
            onClick.OnClick(view, holder.getPosition());
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull WordListViewHolder holder, int position) {
        String pos = meaningsList.get(position).getPartOfSpeech();
        holder.getPosView().setText(pos);
    }

    @Override
    public int getItemCount() {
        return meaningsList.size();
    }

    public static class WordListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView posTextView;

        public WordListViewHolder(@NonNull View itemView) {
            super(itemView);
            posTextView = itemView.findViewById(R.id.pos);
            itemView.setOnClickListener(this);
        }

        public TextView getPosView() {
            return posTextView;
        }

        @Override
        public void onClick(View v) {

        }
    }

}
