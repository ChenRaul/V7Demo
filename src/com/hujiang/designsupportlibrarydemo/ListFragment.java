package com.hujiang.designsupportlibrarydemo;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class ListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout refreshView;//v4扩展包中的下拉刷新组件
	private OnRefreshListener refreshListener;
	
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			refreshView.setRefreshing(false);	
			Toast.makeText(MainActivity.main_this, "更新", 0).show();
			
		};
	};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       
        if(refreshView == null){
        	refreshView =(SwipeRefreshLayout) inflater.inflate(R.layout.list_fragment, container, false);
            
        	mRecyclerView = (RecyclerView) refreshView.findViewById(R.id.recycler_view);
            
            
            
            /*setColorSchemeColors() 设置进度条颜色，可设置多个值，进度条颜色在这多个颜色值之间变化
    			setSize() 设置下拉出现的圆形进度条的大小，有两个值：SwipeRefreshLayout.DEFAULT 和 SwipeRefreshLayout.LARGE
    			setProgressBackgroundColorSchemeColor()设置圆形进度条背景颜色。
    			setDistanceToTriggerSync() 设置手势操作下拉多少距离之后开始刷新数据*/
           refreshListener = new OnRefreshListener() {
    			@Override
    			public void onRefresh() {
    				System.out.println("刷新");
    				new Thread(){
    					@Override
						public void run() {
    						try {
    							Thread.sleep(3000);
    						} catch (InterruptedException e) {
    							// TODO Auto-generated catch block
    							e.printStackTrace();
    						}
    						handler.sendEmptyMessage(0);
    					};
    				}.start();
    			}
    		};
            refreshView.setOnRefreshListener(refreshListener);
            refreshView.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN,Color.YELLOW);
            refreshView.setSize(SwipeRefreshLayout.DEFAULT);
            refreshView.setProgressBackgroundColorSchemeColor(Color.GRAY);
            refreshView.setDistanceToTriggerSync(50);
           /*要添加下面两句话才能让这个刷新组建主动调用出来，并消失*/
            refreshView.post(new Runnable() {			
    			@Override
    			public void run() {
    				refreshView.setRefreshing(true);			
    			}
    		});  
            refreshListener.onRefresh();
        }
        ViewGroup parent = (ViewGroup) refreshView.getParent();
	    if (parent != null){
	    	parent.removeView(refreshView);
	    }
     
        return refreshView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //设置布局管理器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
//        mRecyclerView.setLayoutManager(new GridLayoutManager(mRecyclerView.getContext(),2));
        /*使用瀑布流的方式布局，StaggeredGridLayoutManager.VERTICAL代表有3列；
         * StaggeredGridLayoutManager.HORIZONTAL代表有3行，这样一来此时就是横向滑动的GridView了，
         * 
        */
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL));
        mRecyclerView.setAdapter(new RecyclerViewAdapter(getActivity()));
        /*设置Item的增加、移除动画，
        如果使用了动画，增加和删除数据更新ITem，则需要使用notifyItemInserted(position)与notifyItemRemoved(position)方法，
       前者是增加，后者是删除，目测好像只能用position一个一个的添加、删除，一般是先增加或删除数据集的position，再执行这两个方法
       当然如果没有使用动画模式，则跟listview是一样的
        */
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
       
       
        
    }
}
