package test.com.coordinatorlayouttest;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private SparseArray<String> datas;
    LinearLayout searchView;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerview1);
        searchView=findViewById(R.id.search_view);
        myAdapter=new MyAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);
        datas=new SparseArray<>();
        context=this;
        final List<String>content=new ArrayList<>();
        int index=0;
        for(int j=0;j<5;j++) {
            String title="title"+j;
            for (int i = 0; i < 10; i++) {
                content.add(title);
                datas.put(index++,"内容"+i);
            }

        }
        myAdapter.setDatas(datas);
        final View view=new View(this);
        final FrameLayout frameLayout=new FrameLayout(this);
        frameLayout.addView(view);
        final FrameLayout.LayoutParams params=( FrameLayout.LayoutParams)view.getLayoutParams();
        params.height=Uitools.dip2px(this,80);
        myAdapter.setHeaderView(frameLayout);
        recyclerView.addItemDecoration(new StickHeaderDecoration(this, new IStick() {
            @Override
            public boolean isFirst(int pos) {
                if(pos>0) {
                    if (pos == 1 || !content.get(pos - 1).equals(content.get(pos))) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public String getTitle(int pos) {

                return content.get(pos-1);
            }
        }));
    }
}
