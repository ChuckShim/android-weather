package com.dev.chuck.weathergame;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by Chuck on 2015. 4. 23..
 */
public class ViewPagerAdapter extends PagerAdapter{

    Context context;
    int[] guksu;
    LayoutInflater inflater;

    public ViewPagerAdapter(Context context, int[] guksu){
        this.context = context;
        this.guksu = guksu;
    }

    @Override
    public boolean isViewFromObject(View view, Object object){
        return view == ((RelativeLayout)object);
    }

    @Override
    public int getCount(){
        return guksu.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){

        ImageView imgGuksu;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.viewpager_item, container, false);

        imgGuksu = (ImageView) itemView.findViewById(R.id.imageGuksu);
        imgGuksu.setImageResource(guksu[position]);
        imgGuksu.setLayoutParams(new RelativeLayout.LayoutParams(800, 800));

        ((ViewPager) container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        ((ViewPager) container).removeView((RelativeLayout) object);
    }

}
