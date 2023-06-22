package com.example.dictionaryapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class WordDetailFragment extends Fragment {


    private String synonym1 = "";
    private String antonym1 = "";
    private Meaning meaningList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            meaningList = (Meaning) bundle.getSerializable("meanings_list");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view1 = inflater.inflate(R.layout.fragment_word_detail, container, false);

        View view = inflater.inflate(R.layout.fragment_word_detail, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_details_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new WordDetailsAdapter(meaningList));
        return view;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView pos_text = view.findViewById(R.id.static_pos);
        pos_text.setText(meaningList.getPartOfSpeech());
        TextView pos_heading = view.findViewById(R.id.heading_pos);
        pos_heading.setText("Part of Speech: ");
        TextView synonym_text = view.findViewById(R.id.synonym);
        TextView antonym_text = view.findViewById(R.id.antonym);
        TextView synonym = view.findViewById(R.id.static_synonym);
        synonym.setText("Synonyms: ");
        TextView antonym = view.findViewById(R.id.static_antonym);
        antonym.setText("Antonyms: ");

        List<String> meaning_synonyms = meaningList.getSynonyms();
        String temp = "";
        for (int i = 0; i < meaning_synonyms.size(); i++) {
            if (i == (meaning_synonyms.size() - 1)) temp += meaning_synonyms.get(i) + ".";
            else temp += meaning_synonyms.get(i) + ",";
        }
        synonym1 += temp;
        synonym1 = (synonym1.equals("")) ? "No synonym found." : synonym1;
        synonym_text.setText(synonym1);

        List<String> meaning_antonyms = meaningList.getAntonyms();
        temp = "";
        for (int i = 0; i < meaning_antonyms.size(); i++) {
            if (i == (meaning_antonyms.size() - 1)) temp += meaning_antonyms.get(i) + ".";
            else temp += meaning_antonyms.get(i) + ",";
        }
        antonym1 += temp;
        antonym1 = (antonym1.equals("")) ? "No antonym found." : antonym1;
        antonym_text.setText(antonym1);
    }

}