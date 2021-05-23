package com.example.listcrowling;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class ChartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    String SW_notice = "https://software.cbnu.ac.kr/bbs/bbs.php?db=notice";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.notice_board);

        recyclerView = findViewById(R.id.recyclerView_chart);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);

        getData();
    }

    private void getData(){
        SWJsoup jsoupAsyncTask = new SWJsoup();
        jsoupAsyncTask.execute();
    }

    private class SWJsoup extends AsyncTask<Void, Void, Void> {
        ArrayList<String> listTitle = new ArrayList<>();
        //ArrayList<String> listName = new ArrayList<>();
        //ArrayList<String> listUrl = new ArrayList<>();
        ArrayList<String> listAlbumID = new ArrayList<>();

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document doc = Jsoup.connect(SW_notice).get();

               // final Elements rank_list_name = doc.select("div.wrap_song_info div.ellipsis.rank02 span a");

                //final Elements image_list1 = doc.select("tr#lst50.lst50 div.wrap a.image_typeAll img");

                //앨범 아이디 추출하기
                final Elements albumId_list_1 = doc.select("td.body_bold nobr a");

                Log.d("CharActivity!!!", albumId_list_1.toString());

                Handler handler = new Handler(Looper.getMainLooper()); // 객체생성
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //순위정보


                        for (Element element : albumId_list_1) {
                            // 앨범 아이디만 나올 수 있도록 문자열 추출 작업
                            // <a href="javascript:melon.link.goAlbumDetail('10427559');" 에서 href 속성만 떼어내기
                            String tmp = element.attr("href");

                            String result = tmp;
                            //앨범 아이디만 리스트에 추가
                            listAlbumID.add(result);
                        }

                        for (int i = 0; i < 20 ; i++) {
                            NoticeItem data = new NoticeItem();
                            data.setTitle(listTitle.get(i));
                            //data.setImageUrl(listUrl.get(i));
                            //data.setRankNum(String.valueOf(i+1));
                            //data.setName(listName.get(i));
                            data.setUrl(listAlbumID.get(i));

                            adapter.addItem(data);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
