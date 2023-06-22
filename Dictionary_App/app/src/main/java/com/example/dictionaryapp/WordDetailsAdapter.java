package com.example.dictionaryapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WordDetailsAdapter extends RecyclerView.Adapter<WordDetailsAdapter.WordDetailsViewHolder> {

    private final Meaning meaningsList;
    private final WordDetailFragment wordDetailFragment = new WordDetailFragment();


    public WordDetailsAdapter(Meaning list) {
        this.meaningsList = list;
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.details_list_item;
    }

    @NonNull
    @Override
    public WordDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_list_item, parent, false);
        return new WordDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordDetailsViewHolder holder, int position) {

        String def = meaningsList.getDefinitions().get(position).getDefinition();
        def = (def == null) ? "No definitions found." : def;
        def = (def.equals("")) ? "No definitions found." : def;
        holder.getDefView().setText(def);
        String eg = meaningsList.getDefinitions().get(position).getExample();
        eg = (eg == null) ? "No examples found." : eg;
        eg = (eg.equals("")) ? "No examples found." : eg;
        holder.getEgView().setText(eg);

        List<String> meaning_synonyms = meaningsList.getDefinitions().get(position).getSynonyms();
        String temp = "";
        String synonym1 = "";
        for (int i = 0; i < meaning_synonyms.size(); i++) {
            if (i == (meaning_synonyms.size() - 1))
                temp += meaning_synonyms.get(i) + ".";
            else
                temp += meaning_synonyms.get(i) + ",";
        }
        synonym1 += temp;
        synonym1 = (synonym1.equals("")) ? "No synonym found." : synonym1;
        holder.getSynView().setText(synonym1);


        List<String> meaning_antonyms = meaningsList.getDefinitions().get(position).getAntonyms();
        temp = "";
        String antonym1 = "";
        for (int i = 0; i < meaning_antonyms.size(); i++) {
            if (i == (meaning_antonyms.size() - 1))
                temp += meaning_antonyms.get(i) + ".";
            else
                temp += meaning_antonyms.get(i) + ",";
        }
        antonym1 += temp;
        antonym1 = (antonym1.equals("")) ? "No antonym found." : antonym1;
        holder.getAntView().setText(antonym1);
    }

    @Override
    public int getItemCount() {
        return meaningsList.getDefinitions().size();
    }


    public static class WordDetailsViewHolder extends RecyclerView.ViewHolder {

        TextView def, eg, syn, ant;

        public WordDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            def = itemView.findViewById(R.id.def);
            eg = itemView.findViewById(R.id.eg);
            syn = itemView.findViewById(R.id.syn);
            ant = itemView.findViewById(R.id.ant);
        }

        public TextView getDefView() {
            return def;
        }

        public TextView getEgView() {
            return eg;
        }

        public TextView getSynView() {
            return syn;
        }

        public TextView getAntView() {
            return ant;
        }

    }

}
