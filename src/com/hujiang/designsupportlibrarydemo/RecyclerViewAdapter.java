package com.hujiang.designsupportlibrarydemo;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context mContext;

    public RecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
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
        if(position%2 == 0){
        	VolleyRequestDemo.imageLoader(holder.imageView,"http://192.168.11.223:8080/image/QQ20160920143938.png");
        }else{
        	VolleyRequestDemo.imageLoader(holder.imageView,"http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg");
        }
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
    }

    @Override
    public int getItemCount() {
        return 100;
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
