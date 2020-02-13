package com.nait.rhofstede1.callitwhatyouwill;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

public class MyItemListener implements AdapterView.OnItemSelectedListener
{
    static MainActivity activity;
    public MyItemListener (Context context)
    {
        activity = (MainActivity)context;
    }

    @Override
    public void onItemSelected(AdapterView<?> spinner, View row, int position, long id)
    {
        Chat chat = (Chat)spinner.getAdapter().getItem(position);

        TextView tvSender = activity.findViewById(R.id.tv_sender);
        TextView tvDate = activity.findViewById(R.id.tv_date);
        TextView tvContent = activity.findViewById(R.id.tv_content);

        tvSender.setText(chat.getChatSender());
        tvDate.setText(chat.getChatDate());
        tvContent.setText(chat.getChatContent());

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
        //do nothing
    }
}
