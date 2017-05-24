Fragment 框架: 
全局只使用一个Activity, 所有的页面跳转全部使用Fragment;
包含5.0+ 权限检查及权限请求跳转, 使用EventBus解耦(喜欢使用RxBus的朋友可自行更改);
网络请求框架使用 RxJava+Retrofti2;
图片加载Glide;
通用PagerAdapter(TabPagerAdapter);
orm集成的dbflow;
经过小小修改的XRecyclerView, 集成自动换行的WrapLayoutManager管理器, 通用的RecyclerView的Adapter(CommonRecyclerViewAdapter);
项目使用的是Databinding;
添加 jni的使用;
添加 AIDL跨应用通信;

欢迎Fork,Star,Issues
