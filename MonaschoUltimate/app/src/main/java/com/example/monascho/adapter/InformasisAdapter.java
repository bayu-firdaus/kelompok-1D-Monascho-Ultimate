package com.example.monascho.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;
import com.example.monascho.R;
import com.example.monascho.model.PayloadInformasi;
import com.example.monascho.network.InitRetrofit;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class InformasisAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    private ArrayList<PayloadInformasi> dataModelArrayList;

    public InformasisAdapter(Context context, ArrayList<PayloadInformasi> dataModelArrayList){
        this.context = context;
        this.dataModelArrayList = dataModelArrayList;
    }

    @Override
    public int getCount() {
//        return slide_heading.length;
        return dataModelArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout_informasi, container, false);

        ImageView img = view.findViewById(R.id.imgView);
        TextView txtheading = view.findViewById(R.id.txt_head);
        TextView txtdesk = view.findViewById(R.id.txt_desk);
        TextView tvTgl = view.findViewById(R.id.tvTgl);
//        Button push_button = view.findViewById(R.id.push_button);

        txtheading.setText(dataModelArrayList.get(position).getJudul());
        txtdesk.setText(Html.fromHtml(dataModelArrayList.get(position).getKet()));
        tvTgl.setText(dataModelArrayList.get(position).getTgl());

        Picasso.with(context).load(new InitRetrofit().getFolderImg()+dataModelArrayList.get(position).getFoto()).into(img);

//        push_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(context, DetailPromo.class);
//                i.putExtra("ids", dataModelArrayList.get(position).getIds());
//                context.startActivity(i);
//            }
//        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
