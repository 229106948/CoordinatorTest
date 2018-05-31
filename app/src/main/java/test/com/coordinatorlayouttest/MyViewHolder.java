package test.com.coordinatorlayouttest;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView textView;
    public MyViewHolder(View itemView) {
        super(itemView);
        textView=itemView.findViewById(R.id.content);
    }
}
