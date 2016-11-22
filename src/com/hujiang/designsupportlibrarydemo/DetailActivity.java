package com.hujiang.designsupportlibrarydemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;


/*要使用ToolBar，activity就必须继承AppCompatActivity类，更改主题为NoActionBar的主题
 * 
 * CollapsingToolbarLayout,CollapsingToolbarLayout,AppBarLayout这三个一般是相互结合使用，否则不会出现应该得到的效果额
 * */
public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //使用CollapsingToolbarLayout时必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上不会显示     
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.detail_toolbar);
       
//        toolbar.setTitle("内容详情");
       // toolbar.setNavigationIcon(R.drawable.ic_menu);这一句话会覆盖在下面出现返回的地方的图标

        
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//这句话会出现一个返回的图标
        
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        
        collapsingToolbar.setTitle("详情信息");
        
      //通过CollapsingToolbarLayout修改字体颜色
        collapsingToolbar.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
        collapsingToolbar.setCollapsedTitleTextColor(Color.RED);//设置收缩后Toolbar上字体的
        
      
        toolbar.setNavigationOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.println(v);
				finish();
			}
		});
    }

    public void checkin(View view) {
        Snackbar.make(view, "checkin success!", Snackbar.LENGTH_SHORT).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_overaction, menu);
        return true;
    }
}
