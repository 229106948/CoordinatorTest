package test.com.coordinatorlayouttest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

public class StickActivity extends Activity {
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private SparseArray<String>datas;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stick_recycler);
        recyclerView=findViewById(R.id.recyclerview);
        myAdapter=new MyAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);
        datas=new SparseArray<>();
        final List<String> content=new ArrayList<>();
        int index=0;
        for(int j=0;j<5;j++) {
            String title="title"+j;
            for (int i = 0; i < 10; i++) {
                content.add(title);
                datas.put(index++,"内容"+i);
            }
        }
        myAdapter.setDatas(datas);
        recyclerView.addItemDecoration(new StickHeaderDecoration(this, new IStick() {
            @Override
            public boolean isFirst(int pos) {
                    if (pos == 0 || !content.get(pos-1).equals(content.get(pos))) {
                        return true;
                    }
                return false;
            }

            @Override
            public String getTitle(int pos) {
                return content.get(pos);
            }
        }));
    }
}
