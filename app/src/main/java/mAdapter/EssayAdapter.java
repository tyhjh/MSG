package mAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tyhj.mylogin.R;

import java.util.List;

import custom.MyPublic;
import database.EssayInfo;

/**
 * Created by Tyhj on 2016/8/24.
 */
public class EssayAdapter extends RecyclerView.Adapter<EssayAdapter.MyViewHolder> {

    private LayoutInflater mInflater;
    private Context context;
    private List<EssayInfo> essay;

    public EssayAdapter(Context context,List<EssayInfo> essay){
        this.context=context;
        this.essay=essay;
        this.mInflater=LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.item_essay,parent,false);
        MyViewHolder viewHolder=new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvEssay.setText(essay.get(position).getTvEssay());
        holder.tvLz.setText(essay.get(position).getTvLz());
        holder.ivLz.setOutlineProvider(MyPublic.getOutline(true,15));
        holder.ivLz.setClipToOutline(true);
        Picasso.with(context).load(essay.get(position).getIvLz()).into(holder.ivLz);
        Picasso.with(context).load(essay.get(position).getIvEssay()).into(holder.ivEssay);
    }

    @Override
    public int getItemCount() {
        return essay.size();
    }

    public void addData(EssayInfo essayInfo){
        essay.add(0,essayInfo);
        notifyItemInserted(0);
    }
    public void deleteData(EssayInfo essayInfo,int pos){
        essay.remove(pos);
        notifyItemRemoved(pos);
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvLz;
        TextView tvEssay;
        ImageView ivLz;
        ImageView ivEssay;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvLz= (TextView) itemView.findViewById(R.id.tvLz);
            tvEssay= (TextView) itemView.findViewById(R.id.tvEssay);
            ivEssay= (ImageView) itemView.findViewById(R.id.ivEssay);
            ivLz= (ImageView) itemView.findViewById(R.id.ivLz);
        }
    }


}
