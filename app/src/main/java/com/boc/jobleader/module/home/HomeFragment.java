package com.boc.jobleader.module.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.boc.jobleader.R;
import com.boc.jobleader.base.BaseFragment;
import com.boc.jobleader.base.BaseFragmentAdapter;
import com.boc.jobleader.common.MyApplication;
import com.boc.jobleader.help.ActivityStackManager;
import com.boc.jobleader.http.model.HttpData;
import com.boc.jobleader.http.request.BannerApi;
import com.boc.jobleader.http.request.GetAppAccessTokenApi;
import com.boc.jobleader.http.response.AppAccessToken;
import com.boc.jobleader.http.response.BannerBean;
import com.boc.jobleader.http.response.BannerModel;
import com.boc.jobleader.module.message.ApplyFragment;
import com.boc.jobleader.module.mine.aboutme.AboutmeActivity;
import com.boc.jobleader.module.mine.authentication.AuthenticationActivity;
import com.boc.jobleader.module.mine.company.CompanyAuthActivity;
import com.boc.jobleader.module.mine.help.HelpActivity;
import com.boc.jobleader.module.mine.personal.PersonalActivity;
import com.boc.jobleader.module.mine.set.SettingActivity;
import com.boc.jobleader.widget.TextSwitchView;
import com.boc.jobleader.widget.XCollapsingToolbarLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.tabs.TabLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.http.EasyConfig;
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
import butterknife.OnClick;

public class HomeFragment extends BaseFragment implements XCollapsingToolbarLayout.OnScrimsListener
    , OnBannerListener {

    @Nullable
    @BindView(R.id.ctl_home_bar)
    XCollapsingToolbarLayout mCollapsingToolbarLayout;

    @Nullable
    @BindView(R.id.tb_home_title)
    Toolbar mTitleBar;

    @Nullable
    @BindView(R.id.tv_home_address)
    TextView mAddressView;
    @Nullable
    @BindView(R.id.tv_home_hint)
    AppCompatTextView mHintView;
    @Nullable
    @BindView(R.id.iv_home_search)
    AppCompatImageView mSearchView;
    @Nullable
    @BindView(R.id.tl_home_tab)
    TabLayout mTabLayout;
    @Nullable
    @BindView(R.id.vp_home_pager)
    ViewPager mViewPager;
    @Nullable
    @BindView(R.id.mybanner)
    Banner banner;
    @Nullable
    @BindView(R.id.switcher1)
    TextSwitchView textSwitcher1;
    @Nullable
    @BindView(R.id.ll_item)
    LinearLayout ll_item;

    private ArrayList list_path;
    private ArrayList list_title;
    private List<BannerModel> dataSource;


    private int curStr;

    private BaseFragmentAdapter<BaseFragment> mPagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {
        super.initData();
        // 连接root服务器获取appAccessToken
        MyApplication application = ActivityStackManager.getInstance().getApplication();
        application.changeRootServer(application);

        // 刷新用户信息
        EasyHttp.post(this)
                .api(new BannerApi())
                .request(new HttpCallback<HttpData<BannerBean>>(this) {

                    @Override
                    public void onSucceed(HttpData<BannerBean> data) {
                        dataSource = data.getData().getBanner();

                        setBanner();
                    }
                });
    }

    @Override
    protected void initView() {
        super.initView();
        mPagerAdapter = new BaseFragmentAdapter<>(this);
        mPagerAdapter.addFragment(ApplyFragment.newInstance(), "列表 A");
        mPagerAdapter.addFragment(ApplyFragment.newInstance(), "列表 B");
        mPagerAdapter.addFragment(ApplyFragment.newInstance(), "列表 C");
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        //设置渐变监听
        mCollapsingToolbarLayout.setOnScrimsListener(this);
        initImmersionBar();

        String[] autoRes = {"大学生薪酬排行:哪些大学月薪过万?",
                "您的简历已经被安谷科技HR关注",
                "'简单的线条'关注了您的最新动态",
                "内地明年大学毕业生数量将创新高"};
        textSwitcher1.setResources(autoRes);
        textSwitcher1.setTextStillTime(3000);


    }

    public void initImmersionBar() {
        // 给这个 ToolBar 设置顶部内边距，才能和 TitleBar 进行对齐
        ImmersionBar.setTitleBar(getActivity(), mTitleBar);
        ImmersionBar.with(this).titleBar(R.id.tb_home_title).statusBarDarkFont(false).init();
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
        toast("点击了"+position);
    }
    //自定义的图片加载器
    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
//            Glide.with(context).load((String) path).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            RoundedCorners roundedCorners= new RoundedCorners(15);
            RequestOptions options=RequestOptions.bitmapTransform(roundedCorners);

            Glide.with(context)
                    .load((String) path)
                    .apply(options)
                    .into(imageView);


        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onScrimsStateChange(XCollapsingToolbarLayout layout, boolean shown) {
        if (shown) {
            mAddressView.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
            mHintView.setBackgroundResource(R.drawable.home_search_bar_gray_bg);
            mHintView.setTextColor(ContextCompat.getColor(getActivity(), R.color.black60));
            ImmersionBar.with(this).titleBar(R.id.tb_home_title).statusBarDarkFont(true).init();
            mSearchView.setSupportImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.black60)));
            banner.setVisibility(View.INVISIBLE);
            ll_item.setVisibility(View.INVISIBLE);
        } else {
            mAddressView.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
            mHintView.setBackgroundResource(R.drawable.home_search_bar_transparent_bg);
            mHintView.setTextColor(ContextCompat.getColor(getActivity(), R.color.white60));
            ImmersionBar.with(this).titleBar(R.id.tb_home_title).statusBarDarkFont(false).init();
            mSearchView.setSupportImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.white)));
            banner.setVisibility(View.VISIBLE);
            ll_item.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.tv_home_address, R.id.tv_home_hint, R.id.iv_home_search,
            })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_home_address:
                startActivity(new Intent(getContext(), SettingActivity.class));
                break;
            case R.id.tv_home_hint:
                startActivity(new Intent(getContext(), AuthenticationActivity.class));
                break;
            case R.id.iv_home_search:
                startActivity(new Intent(getContext(), AboutmeActivity.class));
                break;
        }
    }
}

