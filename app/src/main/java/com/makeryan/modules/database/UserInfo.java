package com.makeryan.modules.database;

import android.databinding.Bindable;
import android.databinding.PropertyChangeRegistry;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.makeryan.lib.BR;
import com.makeryan.lib.database.AppDatabase;
import com.makeryan.lib.pojo.BaseBean;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by MakerYan on 2017/6/3 18:27.
 * Modify by MakerYan on 2017/6/3 18:27.
 * Email : light.yan@qq.com
 * project name : shanghaixuankai_android
 * package name : com.makeryan.modules.database
 */
@Table(database = AppDatabase.class)
public class UserInfo
		extends BaseBean {

	public static UserInfo objectFromData(String str) {

		return new Gson().fromJson(
				str,
				UserInfo.class
								  );
	}

	public static List<UserInfo> arrayTestResponseFromData(String str) {

		Type listType = new TypeToken<ArrayList<UserInfo>>() {

		}.getType();

		return new Gson().fromJson(
				str,
				listType
								  );
	}

	@PrimaryKey
	@Column
	public long singleId = 666;

	@Column
	public String theOtherSideAvatar = "";

	@Column
	public String theOtherSideNickName = "";

	@Column
	public String about_me = "";

	@Column
	public String qianmin = "";

	@Column
	public String authentication = "";

	@Column
	public String photo_count = "";

	@Column
	public String present_count = "";

	@Column
	public String last_logintime = "";

	@Column
	public String jymd = "";

	@Column
	public String lianaiguan = "";

	@Column
	public String xwyhdd = "";

	@Column
	public String hlzt = "";

	@Column
	public String img = "";

	@Column
	public int visitsCount;

	@Column
	public String icon = "";

	@Column
	public String msg = "";

	@Column
	public String provinced = "";

	@Column
	public int ftype = 1;

	@Column
	public int rechargeAmount = 0;

	@Column
	public String nickName = "";

	@Column
	public String haveHome = "";

	@Column
	public String haveCar = "";

	@Column
	public String sexInterest = "";

	@Column
	public String fid = "";

	@Column
	public String zy_address = "";

	@Column
	public String zy_age = "";

	@Column
	public String zy_height = "";

	@Column
	public String zy_tag = "";

	@Column
	public String hxUserId = "";

	@Column
	public String sed_shxg = "";

	@Column
	public String sed_xqah = "";

	@Column
	public String sed_xag = "";

	@Column
	public String sed_jzg = "";

	@Column
	public String sed_ly = "";

	@Column
	public String pwd = "";

	@Column
	public String dialogTitle = "";

	@Column
	public int sex = 1;

	@Column
	public int size = 15;

	@Column
	public int page = 1;

	@Column
	public int type = 0;

	@Column
	public int currentYear = 0;

	@Column
	public int currentMonth = 0;

	@Column
	public int currentDay = 0;

	@Column
	public int minAge = 18;

	@Column
	public int maxAge = 19;

	@Column
	public int minHeight = 130;

	@Column
	public int maxHeight = 131;

	@Column
	public int diamond = 0;

	@Column
	public int coins = 0;

	@Column
	public boolean isMsgNotify = true;

	@Column
	public String appCacheSize = "0kb";

	@Column
	public String jd = "";

	@Column
	public String wd = "";

	@Column
	public String id = "";

	@Column
	public String mobile = "";

	@Column
	public String birthday = "";

	@Column
	public String pic = "";

	@Column
	public String interest_tag = "";

	@Column
	public String token = "";

	@Column
	public String vip = "";

	@Column
	public String address = "";

	@Column
	public String qianming = "";

	@Column
	public String profession = "";

	@Column
	public String educated = "";

	@Column
	public String wages = "";

	@Column
	public String qq = "";

	@Column
	public String ydl = "";

	@Column
	public String hqxxw = "";

	@Column
	public String tz = "";

	@Column
	public String password = "";

	@Column
	public String height = "";

	@Column
	public String bg_img = "";

	@Column
	public String interest = "";

	@Column
	public String like = "";

	@Column
	public String user_id = "";

	@Column
	public String user_ico = "";

	@Column
	public String avatar = "";

	@Column
	public String age = "";

	@Column
	public String province = "";

	@Column
	public String city = "";

	@Column
	public String tip = "";

	private transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();

	@Bindable
	public long getSingleId() {

		return singleId;
	}

	public UserInfo setSingleId(long singleId) {

		this.singleId = singleId;
		notifyChange(BR.singleId);
		return this;
	}

	@Bindable
	public String getAbout_me() {

		return about_me;
	}

	public UserInfo setAbout_me(String about_me) {

		this.about_me = about_me;
		notifyChange(BR.about_me);
		return this;
	}

	@Bindable
	public String getQianmin() {

		return qianmin;
	}

	public UserInfo setQianmin(String qianmin) {

		this.qianmin = qianmin;
		notifyChange(BR.qianmin);
		return this;
	}

	@Bindable
	public String getAuthentication() {

		return authentication;
	}

	public UserInfo setAuthentication(String authentication) {

		this.authentication = authentication;
		notifyChange(BR.authentication);
		return this;
	}

	@Bindable
	public String getPhoto_count() {

		return photo_count;
	}

	public UserInfo setPhoto_count(String photo_count) {

		this.photo_count = photo_count;
		notifyChange(BR.photo_count);
		return this;
	}

	@Bindable
	public String getPresent_count() {

		return present_count;
	}

	public UserInfo setPresent_count(String present_count) {

		this.present_count = present_count;
		notifyChange(BR.present_count);
		return this;
	}

	@Bindable
	public String getLast_logintime() {

		return last_logintime;
	}

	public UserInfo setLast_logintime(String last_logintime) {

		this.last_logintime = last_logintime;
		notifyChange(BR.last_logintime);
		return this;
	}

	@Bindable
	public String getJymd() {

		return jymd;
	}

	public UserInfo setJymd(String jymd) {

		this.jymd = jymd;
		notifyChange(BR.jymd);
		return this;
	}

	@Bindable
	public String getLianaiguan() {

		return lianaiguan;
	}

	public UserInfo setLianaiguan(String lianaiguan) {

		this.lianaiguan = lianaiguan;
		notifyChange(BR.lianaiguan);
		return this;
	}

	@Bindable
	public String getXwyhdd() {

		return xwyhdd;
	}

	public UserInfo setXwyhdd(String xwyhdd) {

		this.xwyhdd = xwyhdd;
		notifyChange(BR.xwyhdd);
		return this;
	}

	@Bindable
	public String getHlzt() {

		return hlzt;
	}

	public UserInfo setHlzt(String hlzt) {

		this.hlzt = hlzt;
		notifyChange(BR.hlzt);
		return this;
	}

	@Bindable
	public String getImg() {

		return img;
	}

	public UserInfo setImg(String img) {

		this.img = img;
		notifyChange(BR.img);
		return this;
	}

	@Bindable
	public int getVisitsCount() {

		return visitsCount;
	}

	public UserInfo setVisitsCount(int visitsCount) {

		this.visitsCount = visitsCount;
		notifyChange(BR.visitsCount);
		return this;
	}

	@Bindable
	public String getIcon() {

		return icon;
	}

	public UserInfo setIcon(String icon) {

		this.icon = icon;
		notifyChange(BR.icon);
		return this;
	}

	@Bindable
	public String getMsg() {

		return msg;
	}

	public UserInfo setMsg(String msg) {

		this.msg = msg;
		notifyChange(BR.msg);
		return this;
	}

	@Bindable
	public String getProvinced() {

		return provinced;
	}

	public UserInfo setProvinced(String provinced) {

		this.provinced = provinced;
		notifyChange(BR.provinced);
		return this;
	}

	@Bindable
	public int getFtype() {

		return ftype;
	}

	public UserInfo setFtype(int ftype) {

		this.ftype = ftype;
		notifyChange(BR.ftype);
		return this;
	}

	@Bindable
	public int getRechargeAmount() {

		return rechargeAmount;
	}

	public UserInfo setRechargeAmount(int rechargeAmount) {

		this.rechargeAmount = rechargeAmount;
		notifyChange(BR.rechargeAmount);
		return this;
	}

	@Bindable
	public String getNickName() {

		return nickName;
	}

	public UserInfo setNickName(String nickName) {

		this.nickName = nickName;
		notifyChange(BR.nickName);
		return this;
	}

	@Bindable
	public String getHaveHome() {

		return haveHome;
	}

	public UserInfo setHaveHome(String haveHome) {

		this.haveHome = haveHome;
		notifyChange(BR.haveHome);
		return this;
	}

	@Bindable
	public String getHaveCar() {

		return haveCar;
	}

	public UserInfo setHaveCar(String haveCar) {

		this.haveCar = haveCar;
		notifyChange(BR.haveCar);
		return this;
	}

	@Bindable
	public String getSexInterest() {

		return sexInterest;
	}

	public UserInfo setSexInterest(String sexInterest) {

		this.sexInterest = sexInterest;
		notifyChange(BR.sexInterest);
		return this;
	}

	@Bindable
	public String getFid() {

		return fid;
	}

	public UserInfo setFid(String fid) {

		this.fid = fid;
		notifyChange(BR.fid);
		return this;
	}

	@Bindable
	public String getZy_address() {

		return zy_address;
	}

	public UserInfo setZy_address(String zy_address) {

		this.zy_address = zy_address;
		notifyChange(BR.zy_address);
		return this;
	}

	@Bindable
	public String getZy_age() {

		return zy_age;
	}

	public UserInfo setZy_age(String zy_age) {

		this.zy_age = zy_age;
		notifyChange(BR.zy_age);
		return this;
	}

	@Bindable
	public String getZy_height() {

		return zy_height;
	}

	public UserInfo setZy_height(String zy_height) {

		this.zy_height = zy_height;
		notifyChange(BR.zy_height);
		return this;
	}

	@Bindable
	public String getZy_tag() {

		return zy_tag;
	}

	public UserInfo setZy_tag(String zy_tag) {

		this.zy_tag = zy_tag;
		notifyChange(BR.zy_tag);
		return this;
	}

	@Bindable
	public String getHxUserId() {

		return hxUserId;
	}

	public UserInfo setHxUserId(String hxUserId) {

		this.hxUserId = hxUserId;
		notifyChange(BR.hxUserId);
		return this;
	}

	@Bindable
	public String getSed_shxg() {

		return sed_shxg;
	}

	public UserInfo setSed_shxg(String sed_shxg) {

		this.sed_shxg = sed_shxg;
		notifyChange(BR.sed_shxg);
		return this;
	}

	@Bindable
	public String getSed_xqah() {

		return sed_xqah;
	}

	public UserInfo setSed_xqah(String sed_xqah) {

		this.sed_xqah = sed_xqah;
		notifyChange(BR.sed_xqah);
		return this;
	}

	@Bindable
	public String getSed_xag() {

		return sed_xag;
	}

	public UserInfo setSed_xag(String sed_xag) {

		this.sed_xag = sed_xag;
		notifyChange(BR.sed_xag);
		return this;
	}

	@Bindable
	public String getSed_jzg() {

		return sed_jzg;
	}

	public UserInfo setSed_jzg(String sed_jzg) {

		this.sed_jzg = sed_jzg;
		notifyChange(BR.sed_jzg);
		return this;
	}

	@Bindable
	public String getSed_ly() {

		return sed_ly;
	}

	public UserInfo setSed_ly(String sed_ly) {

		this.sed_ly = sed_ly;
		notifyChange(BR.sed_ly);
		return this;
	}

	@Bindable
	public String getPwd() {

		return pwd;
	}

	public UserInfo setPwd(String pwd) {

		this.pwd = pwd;
		notifyChange(BR.pwd);
		return this;
	}

	@Bindable
	public String getDialogTitle() {

		return dialogTitle;
	}

	public UserInfo setDialogTitle(String dialogTitle) {

		this.dialogTitle = dialogTitle;
		notifyChange(BR.dialogTitle);
		return this;
	}

	@Bindable
	public int getSex() {

		return sex;
	}

	public UserInfo setSex(int sex) {

		this.sex = sex;
		notifyChange(BR.sex);
		return this;
	}

	@Bindable
	public int getSize() {

		return size;
	}

	public UserInfo setSize(int size) {

		this.size = size;
		notifyChange(BR.size);
		return this;
	}

	@Bindable
	public int getPage() {

		return page;
	}

	public UserInfo setPage(int page) {

		this.page = page;
		notifyChange(BR.page);
		return this;
	}

	@Bindable
	public int getType() {

		return type;
	}

	public UserInfo setType(int type) {

		this.type = type;
		notifyChange(BR.type);
		return this;
	}

	@Bindable
	public int getCurrentYear() {

		return currentYear;
	}

	public UserInfo setCurrentYear(int currentYear) {

		this.currentYear = currentYear;
		notifyChange(BR.currentYear);
		return this;
	}

	@Bindable
	public int getCurrentMonth() {

		return currentMonth;
	}

	public UserInfo setCurrentMonth(int currentMonth) {

		this.currentMonth = currentMonth;
		notifyChange(BR.currentMonth);
		return this;
	}

	@Bindable
	public int getCurrentDay() {

		return currentDay;
	}

	public UserInfo setCurrentDay(int currentDay) {

		this.currentDay = currentDay;
		notifyChange(BR.currentDay);
		return this;
	}

	@Bindable
	public int getMinAge() {

		return minAge;
	}

	public UserInfo setMinAge(int minAge) {

		this.minAge = minAge;
		notifyChange(BR.minAge);
		return this;
	}

	@Bindable
	public int getMaxAge() {

		return maxAge;
	}

	public UserInfo setMaxAge(int maxAge) {

		this.maxAge = maxAge;
		notifyChange(BR.maxAge);
		return this;
	}

	@Bindable
	public int getMinHeight() {

		return minHeight;
	}

	public UserInfo setMinHeight(int minHeight) {

		this.minHeight = minHeight;
		notifyChange(BR.minHeight);
		return this;
	}

	@Bindable
	public int getMaxHeight() {

		return maxHeight;
	}

	public UserInfo setMaxHeight(int maxHeight) {

		this.maxHeight = maxHeight;
		notifyChange(BR.maxHeight);
		return this;
	}

	@Bindable
	public int getDiamond() {

		return diamond;
	}

	public UserInfo setDiamond(int diamond) {

		this.diamond = diamond;
		notifyChange(BR.diamond);
		return this;
	}

	@Bindable
	public int getCoins() {

		return coins;
	}

	public UserInfo setCoins(int coins) {

		this.coins = coins;
		notifyChange(BR.coins);
		return this;
	}

	@Bindable
	public boolean isMsgNotify() {

		return isMsgNotify;
	}

	public UserInfo setMsgNotify(boolean msgNotify) {

		isMsgNotify = msgNotify;
		notifyChange(BR.msgNotify);
		save();
		return this;
	}

	@Bindable
	public String getAppCacheSize() {

		return appCacheSize;
	}

	public UserInfo setAppCacheSize(String appCacheSize) {

		this.appCacheSize = appCacheSize;
		notifyChange(BR.appCacheSize);
		save();
		return this;
	}

	@Bindable
	public String getJd() {

		return jd;
	}

	public UserInfo setJd(String jd) {

		this.jd = jd;
		notifyChange(BR.jd);
		return this;
	}

	@Bindable
	public String getWd() {

		return wd;
	}

	public UserInfo setWd(String wd) {

		this.wd = wd;
		notifyChange(BR.wd);
		return this;
	}

	@Bindable
	public String getId() {

		return id;
	}

	public UserInfo setId(String id) {

		this.id = id;
		notifyChange(BR.id);
		return this;
	}

	@Bindable
	public String getMobile() {

		return mobile;
	}

	public UserInfo setMobile(String mobile) {

		this.mobile = mobile;
		notifyChange(BR.mobile);
		return this;
	}

	@Bindable
	public String getBirthday() {

		return birthday;
	}

	public UserInfo setBirthday(String birthday) {

		this.birthday = birthday;
		notifyChange(BR.birthday);
		return this;
	}

	@Bindable
	public String getPic() {

		return pic;
	}

	public UserInfo setPic(String pic) {

		this.pic = pic;
		notifyChange(BR.pic);
		return this;
	}

	@Bindable
	public String getInterest_tag() {

		return interest_tag;
	}

	public UserInfo setInterest_tag(String interest_tag) {

		this.interest_tag = interest_tag;
		notifyChange(BR.interest_tag);
		return this;
	}

	@Bindable
	public String getToken() {

		return token;
	}

	public UserInfo setToken(String token) {

		this.token = token;
		notifyChange(BR.token);
		return this;
	}

	@Bindable
	public String getVip() {

		return vip;
	}

	public UserInfo setVip(String vip) {

		this.vip = vip;
		notifyChange(BR.vip);
		return this;
	}

	@Bindable
	public String getAddress() {

		return address;
	}

	public UserInfo setAddress(String address) {

		this.address = address;
		notifyChange(BR.address);
		return this;
	}

	@Bindable
	public String getQianming() {

		return qianming;
	}

	public UserInfo setQianming(String qianming) {

		this.qianming = qianming;
		notifyChange(BR.qianming);
		return this;
	}

	@Bindable
	public String getProfession() {

		return profession;
	}

	public UserInfo setProfession(String profession) {

		this.profession = profession;
		notifyChange(BR.profession);
		return this;
	}

	@Bindable
	public String getEducated() {

		return educated;
	}

	public UserInfo setEducated(String educated) {

		this.educated = educated;
		notifyChange(BR.educated);
		return this;
	}

	@Bindable
	public String getWages() {

		return wages;
	}

	public UserInfo setWages(String wages) {

		this.wages = wages;
		notifyChange(BR.wages);
		return this;
	}

	@Bindable
	public String getQq() {

		return qq;
	}

	public UserInfo setQq(String qq) {

		this.qq = qq;
		notifyChange(BR.qq);
		return this;
	}

	@Bindable
	public String getYdl() {

		return ydl;
	}

	public UserInfo setYdl(String ydl) {

		this.ydl = ydl;
		notifyChange(BR.ydl);
		return this;
	}

	@Bindable
	public String getHqxxw() {

		return hqxxw;
	}

	public UserInfo setHqxxw(String hqxxw) {

		this.hqxxw = hqxxw;
		notifyChange(BR.hqxxw);
		return this;
	}

	@Bindable
	public String getTz() {

		return tz;
	}

	public UserInfo setTz(String tz) {

		this.tz = tz;
		notifyChange(BR.tz);
		return this;
	}

	@Bindable
	public String getPassword() {

		return password;
	}

	public UserInfo setPassword(String password) {

		this.password = password;
		notifyChange(BR.password);
		return this;
	}

	@Bindable
	public String getHeight() {

		return height;
	}

	public UserInfo setHeight(String height) {

		this.height = height;
		notifyChange(BR.height);
		return this;
	}

	@Bindable
	public String getBg_img() {

		return bg_img;
	}

	public UserInfo setBg_img(String bg_img) {

		this.bg_img = bg_img;
		notifyChange(BR.bg_img);
		return this;
	}

	@Bindable
	public String getInterest() {

		return interest;
	}

	public UserInfo setInterest(String interest) {

		this.interest = interest;
		notifyChange(BR.interest);
		return this;
	}

	@Bindable
	public String getLike() {

		return like;
	}

	public UserInfo setLike(String like) {

		this.like = like;
		notifyChange(BR.like);
		return this;
	}

	@Bindable
	public String getUser_id() {

		return user_id;
	}

	public UserInfo setUser_id(String user_id) {

		this.user_id = user_id;
		notifyChange(BR.user_id);
		return this;
	}

	@Bindable
	public String getUser_ico() {

		return user_ico;
	}

	public UserInfo setUser_ico(String user_ico) {

		this.user_ico = user_ico;
		notifyChange(BR.user_ico);
		return this;
	}

	@Bindable
	public String getAvatar() {

		return avatar;
	}

	public UserInfo setAvatar(String avatar) {

		this.avatar = avatar;
		notifyChange(BR.avatar);
		return this;
	}

	@Bindable
	public String getAge() {

		return age;
	}

	public UserInfo setAge(String age) {

		this.age = age;
		notifyChange(BR.age);
		return this;
	}

	@Bindable
	public String getProvince() {

		return province;
	}

	public UserInfo setProvince(String province) {

		this.province = province;
		notifyChange(BR.province);
		return this;
	}

	@Bindable
	public String getCity() {

		return city;
	}

	public UserInfo setCity(String city) {

		this.city = city;
		notifyChange(BR.city);
		return this;
	}

	@Bindable
	public String getTip() {

		return tip;
	}

	public UserInfo setTip(String tip) {

		this.tip = tip;
		notifyChange(BR.tip);
		return this;
	}

	@Bindable
	public String getTheOtherSideAvatar() {

		return theOtherSideAvatar;
	}

	public UserInfo setTheOtherSideAvatar(String theOtherSideAvatar) {

		this.theOtherSideAvatar = theOtherSideAvatar;
		notifyChange(BR.theOtherSideAvatar);
		return this;
	}

	@Bindable
	public String getTheOtherSideNickName() {

		return theOtherSideNickName;
	}

	public UserInfo setTheOtherSideNickName(String theOtherSideNickName) {

		this.theOtherSideNickName = theOtherSideNickName;
		notifyChange(BR.theOtherSideNickName);
		return this;
	}

	private void notifyChange(int propertyId) {

		if (propertyChangeRegistry == null) {
			propertyChangeRegistry = new PropertyChangeRegistry();
		}
		propertyChangeRegistry.notifyChange(
				this,
				propertyId
										   );
	}

	@Override
	public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {

		if (propertyChangeRegistry == null) {
			propertyChangeRegistry = new PropertyChangeRegistry();
		}
		propertyChangeRegistry.add(callback);

	}

	@Override
	public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {

		if (propertyChangeRegistry != null) {
			propertyChangeRegistry.remove(callback);
		}
	}

	@Override
	public String toString() {

		return "UserInfo{" + "singleId=" + singleId + ", theOtherSideAvatar='" + theOtherSideAvatar + '\'' + ", theOtherSideNickName='" + theOtherSideNickName + '\'' + ", about_me='" + about_me + '\'' + ", qianmin='" + qianmin + '\'' + ", authentication='" + authentication + '\'' + ", photo_count='" + photo_count + '\'' + ", present_count='" + present_count + '\'' + ", last_logintime='" + last_logintime + '\'' + ", jymd='" + jymd + '\'' + ", lianaiguan='" + lianaiguan + '\'' + ", xwyhdd='" + xwyhdd + '\'' + ", hlzt='" + hlzt + '\'' + ", img='" + img + '\'' + ", visitsCount=" + visitsCount + ", icon='" + icon + '\'' + ", msg='" + msg + '\'' + ", provinced='" + provinced + '\'' + ", ftype=" + ftype + ", rechargeAmount=" + rechargeAmount + ", nickName='" + nickName + '\'' + ", haveHome='" + haveHome + '\'' + ", haveCar='" + haveCar + '\'' + ", sexInterest='" + sexInterest + '\'' + ", fid='" + fid + '\'' + ", zy_address='" + zy_address + '\'' + ", zy_age='" + zy_age + '\'' + ", zy_height='" + zy_height + '\'' + ", zy_tag='" + zy_tag + '\'' + ", hxUserId='" + hxUserId + '\'' + ", sed_shxg='" + sed_shxg + '\'' + ", sed_xqah='" + sed_xqah + '\'' + ", sed_xag='" + sed_xag + '\'' + ", sed_jzg='" + sed_jzg + '\'' + ", sed_ly='" + sed_ly + '\'' + ", pwd='" + pwd + '\'' + ", dialogTitle='" + dialogTitle + '\'' + ", sex=" + sex + ", size=" + size + ", page=" + page + ", type=" + type + ", currentYear=" + currentYear + ", currentMonth=" + currentMonth + ", currentDay=" + currentDay + ", minAge=" + minAge + ", maxAge=" + maxAge + ", minHeight=" + minHeight + ", maxHeight=" + maxHeight + ", diamond=" + diamond + ", coins=" + coins + ", isMsgNotify=" + isMsgNotify + ", appCacheSize='" + appCacheSize + '\'' + ", jd='" + jd + '\'' + ", wd='" + wd + '\'' + ", id='" + id + '\'' + ", mobile='" + mobile + '\'' + ", birthday='" + birthday + '\'' + ", pic='" + pic + '\'' + ", interest_tag='" + interest_tag + '\'' + ", token='" + token + '\'' + ", vip='" + vip + '\'' + ", address='" + address + '\'' + ", qianming='" + qianming + '\'' + ", profession='" + profession + '\'' + ", educated='" + educated + '\'' + ", wages='" + wages + '\'' + ", qq='" + qq + '\'' + ", ydl='" + ydl + '\'' + ", hqxxw='" + hqxxw + '\'' + ", tz='" + tz + '\'' + ", password='" + password + '\'' + ", height='" + height + '\'' + ", bg_img='" + bg_img + '\'' + ", interest='" + interest + '\'' + ", like='" + like + '\'' + ", user_id='" + user_id + '\'' + ", user_ico='" + user_ico + '\'' + ", avatar='" + avatar + '\'' + ", age='" + age + '\'' + ", province='" + province + '\'' + ", city='" + city + '\'' + ", tip='" + tip + '\'' + "} " + super.toString();
	}
}
