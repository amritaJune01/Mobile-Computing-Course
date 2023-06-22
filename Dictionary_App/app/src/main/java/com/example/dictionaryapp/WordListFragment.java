package com.example.dictionaryapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;

public class WordListFragment extends Fragment {

    private List<Meaning> meaningsList;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Intent i = getActivity().getIntent();
        meaningsList = (List<Meaning>) i.getSerializableExtra("meanings_list");

        View view = inflater.inflate(R.layout.fragment_word_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new WordListAdapter(meaningsList, new pos_on_click() {
            @Override
            public void OnClick(View view, int pos) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("meanings_list", (Serializable) meaningsList.get(pos));
                FragmentManager fragmentManager = getFragmentManager();
                WordDetailFragment wordDetailFragment = null;
                if (fragmentManager != null) {
                    wordDetailFragment = (WordDetailFragment) fragmentManager.findFragmentByTag("back");
                }
                if (wordDetailFragment == null) {
                    wordDetailFragment = new WordDetailFragment();
                }
                wordDetailFragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.posListFragment, wordDetailFragment, "detail_frag").addToBackStack("back");
                fragmentTransaction.commit();
            }
        }));

        return view;
    }

}