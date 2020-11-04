package com.boc.jobleader.module.workspace.root;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.boc.jobleader.R;
import com.boc.jobleader.base.BaseFragment;
import com.boc.jobleader.common.MyApplication;
import com.boc.jobleader.help.ActivityStackManager;
import com.boc.jobleader.http.model.HttpData;
import com.boc.jobleader.http.request.ServiceHallHomeApi;
import com.boc.jobleader.http.response.BannerModel;
import com.boc.jobleader.http.response.ServiceHallHomeBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.TitleBar;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class WorkspaceFragment extends BaseFragment implements OnBannerListener {

    @Nullable
    @BindView(R.id.commonTitleBar)
    TitleBar mTitleBar;
    @Nullable
    @BindView(R.id.mybanner)
    Banner banner;

    @Nullable
    @BindView(R.id.rv_main)
    RecyclerView recyclerView;

    private ArrayList list_path;
    private ArrayList list_title;
    private List<WorkSpaceHomeItem> fruitList = new ArrayList<>();
    private List<BannerModel> dataSource;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_workspace;
    }

    @Override
    protected void initData() {
        super.initData();
        // 连接root服务器获取appAccessToken
        MyApplication application = ActivityStackManager.getInstance().getApplication();
        application.changeRootServer(application);

        // 刷新用户信息
        EasyHttp.post(this)
                .api(new ServiceHallHomeApi())
                .request(new HttpCallback<HttpData<ServiceHallHomeBean>>(this) {

                    @Override
                    public void onSucceed(HttpData<ServiceHallHomeBean> data) {
                        dataSource = data.getData().getBanner();
                        setBanner();
                    }
                });

        initFruits(); // 初始化水果数据
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(layoutManager);
        WorkSpaceAdapter adapter = new WorkSpaceAdapter(fruitList);
        recyclerView.setAdapter(adapter);

    }

    private void initFruits() {
        WorkSpaceHomeItem apple = new WorkSpaceHomeItem("求职", R.mipmap.icon_shortcut_1_1,
                "将助您更好地了解我们，轻松申请职位。因此我们希望您能够浏览本页，更多地了解我们");
        fruitList.add(apple);
        WorkSpaceHomeItem banana = new WorkSpaceHomeItem("招聘", R.mipmap.icon_shortcut_2_1,
                "将助您更好地了解我们，轻松申请职位。因此我们希望您能够浏览本页，更多地了解我们");
        fruitList.add(banana);
        WorkSpaceHomeItem orange = new WorkSpaceHomeItem("评估", R.mipmap.icon_shortcut_3_1,
                "将助您更好地了解我们，轻松申请职位。因此我们希望您能够浏览本页，更多地了解我们");
        fruitList.add(orange);
        WorkSpaceHomeItem watermelon = new WorkSpaceHomeItem("推荐", R.mipmap.icon_shortcut_5,
                "将助您更好地了解我们，轻松申请职位。因此我们希望您能够浏览本页，更多地了解我们");
        fruitList.add(watermelon);
        WorkSpaceHomeItem pear = new WorkSpaceHomeItem("实习", R.mipmap.icon_shortcut_4_1,
                "将助您更好地了解我们，轻松申请职位。因此我们希望您能够浏览本页，更多地了解我们");
        fruitList.add(pear);
        WorkSpaceHomeItem grape = new WorkSpaceHomeItem("更多", R.mipmap.icon_shortcut_18_1,
                "将助您更好地了解我们，轻松申请职位。因此我们希望您能够浏览本页，更多地了解我们");
        fruitList.add(grape);
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        ImmersionBar.with(this).titleBar(R.id.commonTitleBar).statusBarDarkFont(true).init();
    }

    private void setBanner() {

        list_path = new ArrayList<>();
        //放标题的集合
        list_title = new ArrayList<>();

        for ( int i = 0; i < dataSource.size(); i++ ) {
            list_path.add(dataSource.get(i).getImg());
            list_title.add(dataSource.get(i).getModel());
        }

        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器，图片加载器在下方
        banner.setImageLoader(new MyLoader());
        //设置图片网址或地址的集合
        banner.setImages(list_path);
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        banner.setBannerAnimation(Transformer.Default);
        //设置轮播图的标题集合
        banner.setBannerTitles(list_title);
        //设置轮播间隔时间
        banner.setDelayTime(3000);
        //设置是否为自动轮播，默认是“是”。
        banner.isAutoPlay(true);
        //设置不能手动影响 默认是手指触摸 轮播图不能翻页
        banner.setViewPagerIsScroll(false);
        //设置指示器的位置，小点点，左中右。
        banner.setIndicatorGravity(BannerConfig.CENTER)
                //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                .setOnBannerListener(this)
                //必须最后调用的方法，启动轮播图。
                .start();
    }

    @Override
    public void OnBannerClick(int position) {

    }
    //自定义的图片加载器
    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            RoundedCorners roundedCorners= new RoundedCorners(15);
            RequestOptions options=RequestOptions.bitmapTransform(roundedCorners);

            Glide.with(context)
                    .load((String) path)
                    .apply(options)
                    .into(imageView);


        }
    }
}

