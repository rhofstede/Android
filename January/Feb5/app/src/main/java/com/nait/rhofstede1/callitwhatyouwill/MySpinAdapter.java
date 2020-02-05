package com.nait.rhofstede1.callitwhatyouwill;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MySpinAdapter extends ArrayAdapter
{
    private Context context;
    private ArrayList array;

    public MySpinAdapter(Context context, int layoutID, ArrayList objects)
    {
        super(context, layoutID, objects);
        this.context = context;
        this.array = objects;

    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Chat getItem(int position) {
        return (Chat)array.get(position);
    }
    //called when dropdown is closed
    @Override
    public View getView(int position, View row, ViewGroup spinner) {
        TextView label = new TextView(context);
        label.setTextColor(Color.CYAN);
        Chat chat = (Chat)array.get(position);
        label.setText(chat.getChatContent());

        return label;
    }
    //called when list drops down
    @Override
    public View getDropDownView(int position, View row, ViewGroup spinner) {
        MainActivity activity = (MainActivity)context;
        LayoutInflater inflator = activity.getLayoutInflater();
        View spinnerRow = inflator.inflate(R.layout.spinner_row, null);

        TextView sender = spinnerRow.findViewById(R.id.text_view_chat_sender);
        TextView date = spinnerRow.findViewById(R.id.text_view_chat_date);
        TextView content = spinnerRow.findViewById(R.id.text_view_chat_content);

        Chat chat = getItem(position);

        sender.setText(chat.getChatSender());
        date.setText(chat.getChatDate());
        content.setText(chat.getChatContent());

        return spinnerRow;
    }
}
