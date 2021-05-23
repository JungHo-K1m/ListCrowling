package com.example.listcrowling;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {
    private ArrayList<NoticeItem> listData = new ArrayList<>(); //adapter에 들어갈 list
    private Context context;
    private Intent intent;

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();

        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킴
        // return 인자는 ViewHolder
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notice_list, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ItemViewHolder itemViewHolder, int i) {
        // item을 하나씩 보여주는(bind 되는) 함수
        itemViewHolder.onBind(listData.get(i));
    }

    @Override
    public int getItemCount() {
        //RecyclerView의 총 개수
        return listData.size();
    }

    void addItem(NoticeItem data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView txt_chartTitle;
        private NoticeItem data;
        private String albumID;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_chartTitle = itemView.findViewById(R.id.txt_chartTitle);
        }

        void onBind(NoticeItem data){
            this.data = data;

            txt_chartTitle.setText(data.getTitle());

            itemView.setOnClickListener(this);
            txt_chartTitle.setOnClickListener(this);

            Log.d("RecyclerAdapter!!!!!", data.getTitle() + "\n");
        }

        @Override
        public void onClick(View view) {
            Log.d("click_item!!!!!", String.valueOf(view.getId()));
            Log.d("click_item!!!!!", data.getTitle()  + " / "+ data.getTitle() + " / " + data.getUrl());

            //Toast.makeText(context, "TITLE : " + data.getTitle() + "\nContent : " + data.getTitle(), Toast.LENGTH_SHORT).show();

            //앨범 번호
            albumID = data.getUrl();

            //앨범 디테일 주소 melon_detail_url + albumID
            //String melon_detail_url = "https://www.melon.com/album/detail.htm?albumId=";

            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(albumID));
            view.getContext().startActivities(new Intent[]{intent});
        }
    }
}