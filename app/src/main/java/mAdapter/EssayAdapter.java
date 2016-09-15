package mAdapter;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tyhj.mylogin.R;

import java.util.List;

import custom.MyPublic;
import database.EssayInfo;

/**
 * Created by Tyhj on 2016/8/24.
 */
public class EssayAdapter extends RecyclerView.Adapter<EssayAdapter.MyViewHolder>{
    private ExpendImage expendImage;
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
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tvEssay.setText(essay.get(position).getTvEssay());
        holder.tvLz.setText(essay.get(position).getTvLz());
        holder.ivLz.setOutlineProvider(MyPublic.getOutline(true,15));
        holder.ivLz.setClipToOutline(true);
        holder.tvLikes.setText(essay.get(position).getLikeCounts()+" Likes");
        holder.ibLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeClik(holder, position);
            }
        });
        holder.ivEssay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expendImage.callBack(essay.get(position));
            }
        });
            Picasso.with(context).load(essay.get(position).getIvLz()).into(holder.ivLz);
        Picasso.with(context).load(essay.get(position).getIvEssay()).into(holder.ivEssay);
        switch (position){
            case 0:
                holder.essayStyle.setBackgroundResource(R.mipmap.essaystyle1);
                break;
        }
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
    public void setCallback(ExpendImage expendImage){
        this.expendImage=expendImage;
    }
    //收藏
    private void likeClik(MyViewHolder holder, int position) {
        ObjectAnimator animator=(ObjectAnimator) AnimatorInflater.loadAnimator(context,
                R.animator.likes);
        animator.setTarget(holder.ibLike);
        AnimatedVectorDrawable mAnimatedVectorDrawable;
        boolean isLike=essay.get(holder.getPosition()).isLike();
        int likes=essay.get(position).getLikeCounts();
        if(!isLike) {
            mAnimatedVectorDrawable = (AnimatedVectorDrawable) context.
                    getDrawable(R.drawable.essaycollect);
            holder.tvLikes.setText(likes+1+" Likes");
            essay.get(position).setLikeCounts(likes+1);
        } else {
            mAnimatedVectorDrawable = (AnimatedVectorDrawable) context.
                    getDrawable(R.drawable.essaynotcollect);
            holder.tvLikes.setText(likes-1+" Likes");
            essay.get(position).setLikeCounts(likes-1);
        }
        animator.start();
        holder.ibLike.setImageDrawable(mAnimatedVectorDrawable);
        mAnimatedVectorDrawable.start();
        essay.get(holder.getPosition()).setLike(!isLike);
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvLz,tvEssay,tvLikes;
        ImageView ivLz,ivEssay,essayStyle;
        ImageButton ibLike,ibLeft,ibMenu;
        ImageView likes;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvLz= (TextView) itemView.findViewById(R.id.tvLz);
            tvEssay= (TextView) itemView.findViewById(R.id.tvEssay);
            tvLikes= (TextView) itemView.findViewById(R.id.tvLikes);
            ivEssay= (ImageView) itemView.findViewById(R.id.ivEssay);
            ivLz= (ImageView) itemView.findViewById(R.id.ivLz);

            ibLike= (ImageButton) itemView.findViewById(R.id.ibtnLike);
            ibLeft= (ImageButton) itemView.findViewById(R.id.ibLeft);
            ibMenu= (ImageButton) itemView.findViewById(R.id.ibMenu);

            likes= (ImageView) itemView.findViewById(R.id.likes);
            essayStyle= (ImageView) itemView.findViewById(R.id.essayStyle);
        }
    }
    public interface ExpendImage{
         void callBack(EssayInfo essayInfo);
    };
}
