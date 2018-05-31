package test.com.coordinatorlayouttest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private SparseArray<String> datas;
    private Context mContext;
    public int type;


    public MyAdapter(Context context) {
        mContext = context;

    }

    public SparseArray<String> getDatas() {
        return datas;
    }

    public void setDatas(SparseArray<String> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    private  View headerView;
    private int HEADER=1;
    public void setHeaderView(View view) {
        headerView=view;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view ;
        if(headerView!=null){
            if(viewType==HEADER){
                view=headerView;
            }else{
                view = LayoutInflater.from(mContext).inflate(R.layout.right_item_view, null);
            }
        }else{
            view = LayoutInflater.from(mContext).inflate(R.layout.right_item_view, null);
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(headerView==null||(headerView!=null&&position!=0)) {
            holder.textView.setText(datas.get(position-1));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(headerView!=null&&position==0){
          return HEADER;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        if(headerView==null){
            return datas == null ? 0 : datas.size();
        }else{
            return datas == null ? 1 : datas.size()+1;
        }
    }
}
