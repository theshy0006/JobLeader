package com.boc.jobleader.module.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.boc.jobleader.http.response.BannerBean;
import com.boc.jobleader.http.response.BannerModel;
import com.boc.jobleader.module.home.search.SearchActivity;
import com.boc.jobleader.module.message.ApplyFragment;
import com.boc.jobleader.module.mine.aboutme.AboutmeActivity;
import com.boc.jobleader.module.mine.authentication.AuthenticationActivity;
import com.boc.jobleader.module.mine.set.SettingActivity;
import com.boc.jobleader.module.sbweb.SBWebActivity;
import com.boc.jobleader.widget.TextSwitchView;
import com.boc.jobleader.widget.XCollapsingToolbarLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.tabs.TabLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @BindView(R.id.searchLinaer)
    LinearLayout searchLinaer;

    @Nullable
    @BindView(R.id.tv_home_hint)
    AppCompatTextView mHintView;

    @Nullable
    @BindView(R.id.iv_home_scan)
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
    @Nullable
    @BindView(R.id.moreImageView)
    ImageView moreImageView;

    @Nullable
    @BindView(R.id.itemSelect)
    ImageView itemSelect;


    @Nullable
    @BindView(R.id.downView)
    ImageView downView;

    @Nullable
    @BindView(R.id.serviceOneImage)
    ImageView serviceOneImage;

    @Nullable
    @BindView(R.id.serviceTwoImage)
    ImageView serviceTwoImage;

    @Nullable
    @BindView(R.id.serviceThreeImage)
    ImageView serviceThreeImage;

    @Nullable
    @BindView(R.id.serviceOneText)
    TextView serviceOneText;

    @Nullable
    @BindView(R.id.serviceTwoText)
    TextView serviceTwoText;

    @Nullable
    @BindView(R.id.serviceThreeText)
    TextView serviceThreeText;

    private ArrayList list_path;
    private ArrayList list_title;
    private List<BannerModel> dataSource;
    private Map<String, Integer> serviceDic = new HashMap<String, Integer>();

    private int curStr;

    private BaseFragmentAdapter<BaseFragment> mPagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {
        super.initData();
        initService();
        XXPermissions.with(getActivity())
                .permission(Permission.READ_EXTERNAL_STORAGE)
                .permission(Permission.WRITE_EXTERNAL_STORAGE)
                .permission(Permission.ACCESS_COARSE_LOCATION)
                .permission(Permission.CAMERA)

                .request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean all) {
                    }
                    @Override
                    public void noPermission(List<String> denied, boolean never) {
                    }
                });


        initBanner();
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

    private void initService() {
        serviceDic.put("校招",R.mipmap.icon_xiaozhao);
        serviceDic.put("社招",R.mipmap.icon_shezhao);
        serviceDic.put("简历中心",R.mipmap.icon_job_resume_editor);
        serviceDic.put("求职意向",R.mipmap.icon_job_data);
        serviceDic.put("实习",R.mipmap.icon_shixi);
        serviceDic.put("投递记录",R.mipmap.icon_toudi);
        serviceDic.put("公司库",R.mipmap.icon_zhaopingshuju);
        serviceDic.put("收藏中心",R.mipmap.icon_shoucang);
        serviceDic.put("模板库",R.mipmap.icon_mubanku);
        serviceDic.put("排行榜",R.mipmap.icon_paihangbang);
        serviceDic.put("职位管理",R.mipmap.icon_zhiweiguanli);
        serviceDic.put("公司主页",R.mipmap.icon_gszhuye);
        serviceDic.put("人才收藏",R.mipmap.icon_rencaishoucang);
        serviceDic.put("简历库",R.mipmap.icon_jianliku);
        serviceDic.put("简历处理",R.mipmap.icon_jianlichuli);
        serviceDic.put("邀请同事",R.mipmap.icon_yaoqingtongshi);
        serviceDic.put("招聘数据",R.mipmap.icon_zhaopingshuju);
        serviceDic.put("评估意向",R.mipmap.icon_pingguyixiang);
        serviceDic.put("评估题库",R.mipmap.icon_pinggutiku);
        serviceDic.put("评估记录",R.mipmap.icon_pinggujilu);
        serviceDic.put("评估数据",R.mipmap.icon_pinggushuju);
        serviceDic.put("我要推荐",R.mipmap.icon_yaotuijian);
        serviceDic.put("推荐记录",R.mipmap.icon_tuijianjilu);
        serviceDic.put("推荐数据",R.mipmap.icon_mine_helper);
    }

    @Override
    protected void initView() {
        super.initView();
        mPagerAdapter = new BaseFragmentAdapter<>(this);
        mPagerAdapter.addFragment(JobListFragment.newInstance(), "职位列表");
        mPagerAdapter.addFragment(InviteNewsFragment.newInstance(), "招聘资讯");
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

    private void initBanner() {

        list_path = new ArrayList<>();
        //放标题的集合
        list_title = new ArrayList<>();

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

    }

    private void setBanner() {
        for ( int i = 0; i < dataSource.size(); i++ ) {
            list_path.add(dataSource.get(i).getImg());
            list_title.add(dataSource.get(i).getModel());
        }

        banner.setImages(list_path);
        banner.setBannerTitles(list_title);
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
            mHintView.setTextColor(ContextCompat.getColor(getActivity(), R.color.black60));
            ImmersionBar.with(this).titleBar(R.id.tb_home_title).statusBarDarkFont(true).init();
            searchLinaer.setBackgroundResource(R.drawable.home_search_bar_gray_bg);
            mSearchView.setSupportImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.black60)));
            downView.setImageResource(R.mipmap.icon_arrow_blackdown);
            banner.setVisibility(View.INVISIBLE);
            ll_item.setVisibility(View.INVISIBLE);
        } else {
            mAddressView.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
            mHintView.setTextColor(ContextCompat.getColor(getActivity(), R.color.white60));
            ImmersionBar.with(this).titleBar(R.id.tb_home_title).statusBarDarkFont(false).init();
            searchLinaer.setBackgroundResource(R.drawable.home_search_bar_transparent_bg);
            mSearchView.setSupportImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.white)));
            downView.setImageResource(R.mipmap.icon_arrow_down_2);

            banner.setVisibility(View.VISIBLE);
            ll_item.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.tv_home_address, R.id.searchLinaer, R.id.moreImageView, R.id.itemSelect})
    public void onViewClicked(View view) {
        SharedPreferences settings = getActivity().getSharedPreferences("UserInfo", 0);
        switch (view.getId()) {
            case R.id.tv_home_address:
                startActivity(new Intent(getActivity(), SBWebActivity.class)
                        .putExtra("url", "http://122.192.73.178:8082/jobleader/#/pages/city/city?a=无锡")
                        .putExtra("title", "选择城市"));
                break;
            case R.id.searchLinaer:
                startActivity(new Intent(getContext(), SearchActivity.class));
                break;
            case R.id.moreImageView:
                // 设置服务类目

                String serviceOne =  settings.getString("serviceOne", "校招").toString();
                String serviceTwo =  settings.getString("serviceTwo", "社招").toString();
                String serviceThree =  settings.getString("serviceThree", "简历中心").toString();

                String urlString = String.format("http://122.192.73.178:8082/jobleader/#/pages/more/more?a=%s&b=%s&c=%s", serviceOne,serviceTwo,serviceThree);
                startActivity(new Intent(getActivity(), SBWebActivity.class)
                        .putExtra("url", urlString)
                        .putExtra("title", "更多"));
                break;
            case R.id.itemSelect:
                // 设置服务类目

                String itemOne =  settings.getString("itemOne", "求职列表").toString();
                String itemTwo =  settings.getString("itemTwo", "招聘资讯").toString();
                String itemThree =  settings.getString("itemThree", "").toString();
                String itemFour =  settings.getString("itemFour", "").toString();
                String itemFive =  settings.getString("itemFive", "").toString();

                if( itemFive.length() != 0 && itemFour.length() != 0 && itemThree.length() != 0) {
                    String urlString1 = String.format("http://122.192.73.178:8082/jobleader/#/pages/editColumn/editColumn?a=%s&b=%s&c=%s&d=%s&e=%s", itemOne,itemTwo,itemThree,itemFour,itemFive);
                    startActivity(new Intent(getActivity(), SBWebActivity.class)
                            .putExtra("url", urlString1)
                            .putExtra("title", "栏目编辑"));
                } else if(itemFour.length() != 0 && itemThree.length() != 0) {
                    String urlString2 = String.format("http://122.192.73.178:8082/jobleader/#/pages/editColumn/editColumn?a=%s&b=%s&c=%s&d=%s", itemOne,itemTwo,itemThree,itemFour);
                    startActivity(new Intent(getActivity(), SBWebActivity.class)
                            .putExtra("url", urlString2)
                            .putExtra("title", "栏目编辑"));
                } else if(itemThree.length() != 0) {
                    String urlString3 = String.format("http://122.192.73.178:8082/jobleader/#/pages/editColumn/editColumn?a=%s&b=%s&c=%s", itemOne,itemTwo,itemThree);
                    startActivity(new Intent(getActivity(), SBWebActivity.class)
                            .putExtra("url", urlString3)
                            .putExtra("title", "栏目编辑"));
                } else {
                    String urlString4 = String.format("http://122.192.73.178:8082/jobleader/#/pages/editColumn/editColumn?a=%s&b=%s", itemOne,itemTwo);
                    startActivity(new Intent(getActivity(), SBWebActivity.class)
                            .putExtra("url", urlString4)
                            .putExtra("title", "栏目编辑"));
                }

                break;

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MyApplication application = ActivityStackManager.getInstance().getApplication();
        application.changeUserServer(application);

        SharedPreferences settings = getActivity().getSharedPreferences("UserInfo", 0);

        // 设置城市
        String currentCity =  settings.getString("currentCity", "无锡").toString();
        mAddressView.setText(currentCity);
        // 设置服务类目
        String serviceOne =  settings.getString("serviceOne", "校招").toString();
        String serviceTwo =  settings.getString("serviceTwo", "社招").toString();
        String serviceThree =  settings.getString("serviceThree", "简历中心").toString();

        Integer imageOne = serviceDic.get(serviceOne);
        Integer imageTwo = serviceDic.get(serviceTwo);
        Integer imageThree = serviceDic.get(serviceThree);

        serviceOneImage.setImageResource(imageOne);
        serviceTwoImage.setImageResource(imageTwo);
        serviceThreeImage.setImageResource(imageThree);

        serviceOneText.setText(serviceOne);
        serviceTwoText.setText(serviceTwo);
        serviceThreeText.setText(serviceThree);

        String itemOne =  settings.getString("itemOne", "求职列表").toString();
        String itemTwo =  settings.getString("itemTwo", "招聘资讯").toString();
        String itemThree =  settings.getString("itemThree", "").toString();
        String itemFour =  settings.getString("itemFour", "").toString();
        String itemFive =  settings.getString("itemFive", "").toString();

    }
}

