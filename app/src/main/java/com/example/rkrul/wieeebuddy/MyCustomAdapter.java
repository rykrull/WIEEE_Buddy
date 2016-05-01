package com.example.rkrul.wieeebuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rkrul on 5/1/2016.
 */
public class MyCustomAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();
    private ArrayList<Project> plist = new ArrayList<Project>();
    private Context context;



    public MyCustomAdapter(ArrayList<String> list,ArrayList<Project> plist, Context context) {
        this.list = list;
        this.plist = plist;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_w_button, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        listItemText.setText(list.get(position));

        //Handle buttons and add onClickListeners
        final Button addBtn = (Button)view.findViewById(R.id.interest_btn);
        final Button deleteBtn = (Button)view.findViewById(R.id.undointerest_btn);

        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                addBtn.setVisibility(View.GONE);
                deleteBtn.setVisibility(View.VISIBLE);
                plist.get(position).addInterest();
                notifyDataSetChanged();
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                plist.get(position).addInterest();
                notifyDataSetChanged();
                addBtn.setVisibility(View.VISIBLE);
                deleteBtn.setVisibility(View.GONE);
            }
        });

        return view;
    }
}
