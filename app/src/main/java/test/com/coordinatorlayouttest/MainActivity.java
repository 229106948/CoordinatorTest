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
        for(int i=0;i<50;i++){
            datas.put(i,"右边数据"+i);
        }
        myAdapter.setDatas(datas);
        final View view=new View(this);
        final FrameLayout frameLayout=new FrameLayout(this);
        frameLayout.addView(view);
        final FrameLayout.LayoutParams params=( FrameLayout.LayoutParams)view.getLayoutParams();
        params.height=Uitools.dip2px(this,80);
        myAdapter.setHeaderView(frameLayout);
    }
}
