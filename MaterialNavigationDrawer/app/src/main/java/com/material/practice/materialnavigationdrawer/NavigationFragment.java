package com.material.practice.materialnavigationdrawer;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class NavigationFragment extends Fragment {
    public NavigationFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_navigation_drawer, null, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listView = (ListView) view.findViewById(R.id.navList);
        ArrayList<NavItems> listItems = new ArrayList<>();
        String[] icons = getActivity().getResources().getStringArray(R.array.NavHeadersIcons);
        String[] title = getActivity().getResources().getStringArray(R.array.NavHeaders);

        for (int i = 0; i < icons.length; i++) {
            listItems.add(new NavItems(icons[i], title[i]));
        }

        final NavListAdapter adapter = NavListAdapter.getInstance(getActivity());
        listView.setAdapter(adapter);
        adapter.setItems(listItems);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setSelected(true);
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
