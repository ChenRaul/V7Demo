package com.hujiang.designsupportlibrarydemo;

import java.util.ArrayList;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<String> data = new ArrayList<String>();
    

    public RecyclerViewAdapter(Context mContext,ArrayList<String> mData) {
        this.mContext = mContext;
        this.data = mData;
    }
    public void setData(ArrayList<String> mData){
    	this.data = mData;
    	notifyDataSetChanged();
    }
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_card_main, parent, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.item_image);
        
        return new ViewHolder(view,imageView);
    }

    @TargetApi(Build.VERSION_CODES.BASE)
    @Override
    public void onBindViewHolder(final RecyclerViewAdapter.ViewHolder holder, int position) {
        final View view = holder.mView;
//        if(position%2 == 0){
        	VolleyRequestDemo.imageLoader(holder.imageView,data.get(position));
//        }else{
//        	VolleyRequestDemo.imageLoader(holder.imageView,"http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg");
//        }
        //点击事件，由于RecycView没有设置click事件和Long Click事件，所以需要自己在此处添加
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v7.app.AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("是否进入详情界面?")
                		.setNegativeButton("取消", new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						})
						.setPositiveButton("确定", new OnClickListener() {
							@Override
							public void onClick(final DialogInterface dialog, int which) {
								ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationZ", 100, 0);
				                animator.addListener(new AnimatorListenerAdapter() {
				                    @Override
				                    public void onAnimationEnd(Animator animation) {				                    	
				                    	mContext.startActivity(new Intent(mContext, DetailActivity.class));
				                    	dialog.dismiss();
				                    }
				                });
				                animator.start();
				                
								
							}
						}).setTitle("V7中最新dialog展示：")
						.setCancelable(false)
						.show();
            }
        });
        //设置长点击事件
        view.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				//TODO
				return true;
			}
		});
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public  ImageView imageView;
        public ViewHolder(View view,ImageView imageView) {
            super(view);
            this.mView = view;
            this.imageView = imageView;
        }
    }
}
