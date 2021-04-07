package net.onest.photographget;

import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class ViewPagerAdapter extends PagerAdapter {

    private List<View> mViewList;
    private List<String> mTitleLists;

    public ViewPagerAdapter(List<View> mViewList,List<String> mTitleLists){
        this.mViewList=mViewList;
        this.mTitleLists=mTitleLists;
    }
    @Override
    public int getCount() {
        return mViewList.size();
    }
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(mViewList.get(position));
        return mViewList.get(position);
    }

    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(mViewList.get(position));         //删除页卡
    }
    public CharSequence getPageTitle(int position){
        return mTitleLists.get(position);             //页卡标题
    }

}
