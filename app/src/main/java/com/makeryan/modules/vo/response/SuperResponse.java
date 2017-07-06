package com.makeryan.modules.vo.response;

import android.databinding.Bindable;
import android.databinding.PropertyChangeRegistry;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.makeryan.lib.BR;
import com.makeryan.lib.net.BaseResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MakerYan on 2017/6/3 18:13.
 * Modify by MakerYan on 2017/6/3 18:13.
 * Email : light.yan@qq.com
 * project name : shanghaixuankai_android
 * package name : com.makeryan.modules.vo.response
 */
public class SuperResponse
		extends BaseResponse {

	private transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();

	public static SuperResponse objectFromData(String str) {

		return new Gson().fromJson(
				str,
				SuperResponse.class
								  );
	}

	public static List<SuperResponse> arrayTestResponseFromData(String str) {

		Type listType = new TypeToken<ArrayList<SuperResponse>>() {

		}.getType();

		return new Gson().fromJson(
				str,
				listType
								  );
	}

	public SuperResponse other_info;

	public SuperResponse photos;

	public SuperResponse present_info;

	public SuperResponse personal_space;

	public SuperResponse base_info;

	public SuperResponse user_contact;

	public SuperResponse more_info;

	public List<String> photo_list = new ArrayList<>();

	public List<String> present_list = new ArrayList<>();

	public List<SuperResponse> user_list = new ArrayList<>();

	public SuperResponse followMeUser;

	public SuperResponse meFollowUser;

	public List<SuperResponse> members = new ArrayList<>();

	public int count;

	public boolean isEnabled = true;

	public boolean isAttention = false;

	public boolean isSendLetter = false;

	public boolean isSayHi = false;

	public String distance = "";

	public String fromName = "";

	public String toName = "";

	public String presentName = "";

	public String about_me = "";

	public String qianmin = "";

	public String authentication = "";

	public String photo_count = "";

	public String present_count = "";

	public String last_logintime = "";

	public String jymd = "";

	public String lianaiguan = "";

	public String xwyhdd = "";

	public String hlzt = "";

	public String name = "";

	public String img = "";

	public int visitsCount;

	public String icon = "";

	public String msg = "";

	public String userName = "";

	public String provinced = "";

	public int ftype = 1;

	public int rechargeAmount = 0;

	public String nickName = "";

	public String haveHome = "";

	public String haveCar = "";

	public String sexInterest = "";

	public String fid = "";

	public String zy_address = "";

	public String zy_age = "";

	public String zy_height = "";

	public String zy_tag = "";

	public String hxUserId = "";

	public String sed_shxg = "";

	public String sed_xqah = "";

	public String sed_xag = "";

	public String sed_jzg = "";

	public String sed_ly = "";

	public String pwd = "";

	public String dialogTitle = "";

	public int sex = 1;

	public int size = 15;

	public int page = 1;

	public int type = 0;

	public int currentYear = 0;

	public int currentMonth = 0;

	public int currentDay = 0;

	public int minAge = 18;

	public int maxAge = 19;

	public int minHeight = 130;

	public int maxHeight = 131;

	public int diamond = 0;

	public int coins = 0;

	public boolean isMsgNotify = true;

	public String appCacheSize = "0kb";

	public String jd = "";

	public String wd = "";

	public String id = "";

	public String mobile = "";

	public String birthday = "";

	public String pic = "";

	public String fileType = "";

	public String interest_tag = "";

	public String token = "";

	public String vip = "";

	public String address = "";

	public String qianming = "";

	public String profession = "";

	public String educated = "";

	public String wages = "";

	public String qq = "";

	public String ydl = "";

	public String hqxxw = "";

	public String tz = "";

	public String password = "";

	public String height = "";

	public String bg_img = "";

	public String interest = "";

	public String like = "";

	public String user_id = "";

	public String user_ico = "";

	public String avatar = "";

	public String age = "";

	public String province = "";

	public String city = "";

	public String tip = "";

	@Bindable
	public SuperResponse getOther_info() {

		return other_info;
	}

	public void setOther_info(SuperResponse other_info) {

		this.other_info = other_info;
		notifyChange(BR.other_info);
	}

	@Bindable
	public SuperResponse getPhotos() {

		return photos;
	}

	public void setPhotos(SuperResponse photos) {

		this.photos = photos;
		notifyChange(BR.photos);
	}

	@Bindable
	public SuperResponse getPresent_info() {

		return present_info;
	}

	public void setPresent_info(SuperResponse present_info) {

		this.present_info = present_info;
		notifyChange(BR.present_info);
	}

	@Bindable
	public SuperResponse getPersonal_space() {

		return personal_space;
	}

	public void setPersonal_space(SuperResponse personal_space) {

		this.personal_space = personal_space;
		notifyChange(BR.personal_space);
	}

	@Bindable
	public SuperResponse getBase_info() {

		return base_info;
	}

	public void setBase_info(SuperResponse base_info) {

		this.base_info = base_info;
		notifyChange(BR.base_info);
	}

	@Bindable
	public SuperResponse getUser_contact() {

		return user_contact;
	}

	public void setUser_contact(SuperResponse user_contact) {

		this.user_contact = user_contact;
		notifyChange(BR.user_contact);
	}

	@Bindable
	public SuperResponse getMore_info() {

		return more_info;
	}

	public void setMore_info(SuperResponse more_info) {

		this.more_info = more_info;
		notifyChange(BR.more_info);
	}

	@Bindable
	public List<String> getPhoto_list() {

		return photo_list;
	}

	public void setPhoto_list(List<String> photo_list) {

		this.photo_list = photo_list;
		notifyChange(BR.photo_list);
	}

	@Bindable
	public List<String> getPresent_list() {

		return present_list;
	}

	public void setPresent_list(List<String> present_list) {

		this.present_list = present_list;
		notifyChange(BR.present_list);
	}

	@Bindable
	public List<SuperResponse> getUser_list() {

		return user_list;
	}

	public void setUser_list(List<SuperResponse> user_list) {

		this.user_list = user_list;
		notifyChange(BR.user_list);
	}

	@Bindable
	public String getAbout_me() {

		return about_me;
	}

	public void setAbout_me(String about_me) {

		this.about_me = about_me;
		notifyChange(BR.about_me);
	}

	@Bindable
	public String getQianmin() {

		return qianmin;
	}

	public void setQianmin(String qianmin) {

		this.qianmin = qianmin;
		notifyChange(BR.qianmin);
	}

	@Bindable
	public String getAuthentication() {

		return authentication;
	}

	public void setAuthentication(String authentication) {

		this.authentication = authentication;
		notifyChange(BR.authentication);
	}

	@Bindable
	public String getPhoto_count() {

		return photo_count;
	}

	public void setPhoto_count(String photo_count) {

		this.photo_count = photo_count;
		notifyChange(BR.photo_count);
	}

	@Bindable
	public String getPresent_count() {

		return present_count;
	}

	public void setPresent_count(String present_count) {

		this.present_count = present_count;
		notifyChange(BR.present_count);
	}

	@Bindable
	public String getLast_logintime() {

		return last_logintime;
	}

	public void setLast_logintime(String last_logintime) {

		this.last_logintime = last_logintime;
		notifyChange(BR.last_logintime);
	}

	@Bindable
	public String getJymd() {

		return jymd;
	}

	public void setJymd(String jymd) {

		this.jymd = jymd;
		notifyChange(BR.jymd);
	}

	@Bindable
	public String getLianaiguan() {

		return lianaiguan;
	}

	public void setLianaiguan(String lianaiguan) {

		this.lianaiguan = lianaiguan;
		notifyChange(BR.lianaiguan);
	}

	@Bindable
	public String getXwyhdd() {

		return xwyhdd;
	}

	public void setXwyhdd(String xwyhdd) {

		this.xwyhdd = xwyhdd;
		notifyChange(BR.xwyhdd);
	}

	@Bindable
	public String getHlzt() {

		return hlzt;
	}

	public void setHlzt(String hlzt) {

		this.hlzt = hlzt;
		notifyChange(BR.hlzt);
	}

	@Bindable
	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
		notifyChange(BR.name);
	}

	@Bindable
	public String getImg() {

		return img;
	}

	public void setImg(String img) {

		this.img = img;
		notifyChange(BR.img);
	}

	@Bindable
	public int getVisitsCount() {

		return visitsCount;
	}

	public void setVisitsCount(int visitsCount) {

		this.visitsCount = visitsCount;
		notifyChange(BR.visitsCount);
	}

	@Bindable
	public String getIcon() {

		return icon;
	}

	public void setIcon(String icon) {

		this.icon = icon;
		notifyChange(BR.icon);
	}

	@Bindable
	public String getMsg() {

		return msg;
	}

	public void setMsg(String msg) {

		this.msg = msg;
		notifyChange(BR.msg);
	}

	@Bindable
	public String getUserName() {

		return userName;
	}

	public void setUserName(String userName) {

		this.userName = userName;
		notifyChange(BR.userName);
	}

	@Bindable
	public String getProvinced() {

		return provinced;
	}

	public void setProvinced(String provinced) {

		this.provinced = provinced;
		notifyChange(BR.provinced);
	}

	@Bindable
	public int getFtype() {

		return ftype;
	}

	public void setFtype(int ftype) {

		this.ftype = ftype;
		notifyChange(BR.ftype);
	}

	@Bindable
	public int getRechargeAmount() {

		return rechargeAmount;
	}

	public void setRechargeAmount(int rechargeAmount) {

		this.rechargeAmount = rechargeAmount;
		notifyChange(BR.rechargeAmount);
	}

	@Bindable
	public String getNickName() {

		return nickName;
	}

	public void setNickName(String nickName) {

		this.nickName = nickName;
		notifyChange(BR.nickName);
	}

	@Bindable
	public String getHaveHome() {

		return haveHome;
	}

	public void setHaveHome(String haveHome) {

		this.haveHome = haveHome;
		notifyChange(BR.haveHome);
	}

	@Bindable
	public String getHaveCar() {

		return haveCar;
	}

	public void setHaveCar(String haveCar) {

		this.haveCar = haveCar;
		notifyChange(BR.haveCar);
	}

	@Bindable
	public String getSexInterest() {

		return sexInterest;
	}

	public void setSexInterest(String sexInterest) {

		this.sexInterest = sexInterest;
		notifyChange(BR.sexInterest);
	}

	@Bindable
	public String getFid() {

		return fid;
	}

	public void setFid(String fid) {

		this.fid = fid;
		notifyChange(BR.fid);
	}

	@Bindable
	public String getZy_address() {

		return zy_address;
	}

	public void setZy_address(String zy_address) {

		this.zy_address = zy_address;
		notifyChange(BR.zy_address);
	}

	@Bindable
	public String getZy_age() {

		return zy_age;
	}

	public void setZy_age(String zy_age) {

		this.zy_age = zy_age;
		notifyChange(BR.zy_age);
	}

	@Bindable
	public String getZy_height() {

		return zy_height;
	}

	public void setZy_height(String zy_height) {

		this.zy_height = zy_height;
		notifyChange(BR.zy_height);
	}

	@Bindable
	public String getZy_tag() {

		return zy_tag;
	}

	public void setZy_tag(String zy_tag) {

		this.zy_tag = zy_tag;
		notifyChange(BR.zy_tag);
	}

	@Bindable
	public String getHxUserId() {

		return hxUserId;
	}

	public void setHxUserId(String hxUserId) {

		this.hxUserId = hxUserId;
		notifyChange(BR.hxUserId);
	}

	@Bindable
	public String getSed_shxg() {

		return sed_shxg;
	}

	public void setSed_shxg(String sed_shxg) {

		this.sed_shxg = sed_shxg;
		notifyChange(BR.sed_shxg);
	}

	@Bindable
	public String getSed_xqah() {

		return sed_xqah;
	}

	public void setSed_xqah(String sed_xqah) {

		this.sed_xqah = sed_xqah;
		notifyChange(BR.sed_xqah);
	}

	@Bindable
	public String getSed_xag() {

		return sed_xag;
	}

	public void setSed_xag(String sed_xag) {

		this.sed_xag = sed_xag;
		notifyChange(BR.sed_xag);
	}

	@Bindable
	public String getSed_jzg() {

		return sed_jzg;
	}

	public void setSed_jzg(String sed_jzg) {

		this.sed_jzg = sed_jzg;
		notifyChange(BR.sed_jzg);
	}

	@Bindable
	public String getSed_ly() {

		return sed_ly;
	}

	public void setSed_ly(String sed_ly) {

		this.sed_ly = sed_ly;
		notifyChange(BR.sed_ly);
	}

	@Bindable
	public String getPwd() {

		return pwd;
	}

	public void setPwd(String pwd) {

		this.pwd = pwd;
		notifyChange(BR.pwd);
	}

	@Bindable
	public String getDialogTitle() {

		return dialogTitle;
	}

	public void setDialogTitle(String dialogTitle) {

		this.dialogTitle = dialogTitle;
		notifyChange(BR.dialogTitle);
	}

	@Bindable
	public int getSex() {

		return sex;
	}

	public void setSex(int sex) {

		this.sex = sex;
		notifyChange(BR.sex);
	}

	@Bindable
	public int getSize() {

		return size;
	}

	public void setSize(int size) {

		this.size = size;
		notifyChange(BR.size);
	}

	@Bindable
	public int getPage() {

		return page;
	}

	public void setPage(int page) {

		this.page = page;
		notifyChange(BR.page);
	}

	@Bindable
	public int getType() {

		return type;
	}

	public void setType(int type) {

		this.type = type;
		notifyChange(BR.type);
	}

	@Bindable
	public int getCurrentYear() {

		return currentYear;
	}

	public void setCurrentYear(int currentYear) {

		this.currentYear = currentYear;
		notifyChange(BR.currentYear);
	}

	@Bindable
	public int getCurrentMonth() {

		return currentMonth;
	}

	public void setCurrentMonth(int currentMonth) {

		this.currentMonth = currentMonth;
		notifyChange(BR.currentMonth);
	}

	@Bindable
	public int getCurrentDay() {

		return currentDay;
	}

	public void setCurrentDay(int currentDay) {

		this.currentDay = currentDay;
		notifyChange(BR.currentDay);
	}

	@Bindable
	public int getMinAge() {

		return minAge;
	}

	public void setMinAge(int minAge) {

		this.minAge = minAge;
		notifyChange(BR.minAge);
	}

	@Bindable
	public int getMaxAge() {

		return maxAge;
	}

	public void setMaxAge(int maxAge) {

		this.maxAge = maxAge;
		notifyChange(BR.maxAge);
	}

	@Bindable
	public int getMinHeight() {

		return minHeight;
	}

	public void setMinHeight(int minHeight) {

		this.minHeight = minHeight;
		notifyChange(BR.minHeight);
	}

	@Bindable
	public int getMaxHeight() {

		return maxHeight;
	}

	public void setMaxHeight(int maxHeight) {

		this.maxHeight = maxHeight;
		notifyChange(BR.maxHeight);
	}

	@Bindable
	public int getDiamond() {

		return diamond;
	}

	public void setDiamond(int diamond) {

		this.diamond = diamond;
		notifyChange(BR.diamond);
	}

	@Bindable
	public int getCoins() {

		return coins;
	}

	public void setCoins(int coins) {

		this.coins = coins;
		notifyChange(BR.coins);
	}

	@Bindable
	public boolean isMsgNotify() {

		return isMsgNotify;
	}

	public void setMsgNotify(boolean msgNotify) {

		isMsgNotify = msgNotify;
		notifyChange(BR.msgNotify);
	}


	@Bindable
	public String getAppCacheSize() {

		return appCacheSize;
	}

	public void setAppCacheSize(String appCacheSize) {

		this.appCacheSize = appCacheSize;
		notifyChange(BR.appCacheSize);
	}

	@Bindable
	public String getJd() {

		return jd;
	}

	public void setJd(String jd) {

		this.jd = jd;
		notifyChange(BR.jd);
	}

	@Bindable
	public String getWd() {

		return wd;
	}

	public void setWd(String wd) {

		this.wd = wd;
		notifyChange(BR.wd);
	}

	@Bindable
	public String getId() {

		return id;
	}

	public void setId(String id) {

		this.id = id;
		notifyChange(BR.id);
	}

	@Bindable
	public String getMobile() {

		return mobile;
	}

	public void setMobile(String mobile) {

		this.mobile = mobile;
		notifyChange(BR.mobile);
	}

	@Bindable
	public String getBirthday() {

		return birthday;
	}

	public void setBirthday(String birthday) {

		this.birthday = birthday;
		notifyChange(BR.birthday);
	}

	@Bindable
	public String getPic() {

		return pic;
	}

	public void setPic(String pic) {

		this.pic = pic;
		notifyChange(BR.pic);
	}

	@Bindable
	public String getInterest_tag() {

		return interest_tag;
	}

	public void setInterest_tag(String interest_tag) {

		this.interest_tag = interest_tag;
		notifyChange(BR.interest_tag);
	}

	@Bindable
	public String getToken() {

		return token;
	}

	public void setToken(String token) {

		this.token = token;
		notifyChange(BR.token);
	}

	@Bindable
	public String getVip() {

		return vip;
	}

	public void setVip(String vip) {

		this.vip = vip;
		notifyChange(BR.vip);
	}

	@Bindable
	public String getAddress() {

		return address;
	}

	public void setAddress(String address) {

		this.address = address;
		notifyChange(BR.address);
	}

	@Bindable
	public String getQianming() {

		return qianming;
	}

	public void setQianming(String qianming) {

		this.qianming = qianming;
		notifyChange(BR.qianming);
	}

	@Bindable
	public String getProfession() {

		return profession;
	}

	public void setProfession(String profession) {

		this.profession = profession;
		notifyChange(BR.profession);
	}

	@Bindable
	public String getEducated() {

		return educated;
	}

	public void setEducated(String educated) {

		this.educated = educated;
		notifyChange(BR.educated);
	}

	@Bindable
	public String getWages() {

		return wages;
	}

	public void setWages(String wages) {

		this.wages = wages;
		notifyChange(BR.wages);
	}

	@Bindable
	public String getQq() {

		return qq;
	}

	public void setQq(String qq) {

		this.qq = qq;
		notifyChange(BR.qq);
	}

	@Bindable
	public String getYdl() {

		return ydl;
	}

	public void setYdl(String ydl) {

		this.ydl = ydl;
		notifyChange(BR.ydl);
	}

	@Bindable
	public String getHqxxw() {

		return hqxxw;
	}

	public void setHqxxw(String hqxxw) {

		this.hqxxw = hqxxw;
		notifyChange(BR.hqxxw);
	}

	@Bindable
	public String getTz() {

		return tz;
	}

	public void setTz(String tz) {

		this.tz = tz;
		notifyChange(BR.tz);
	}

	@Bindable
	public String getPassword() {

		return password;
	}

	public void setPassword(String password) {

		this.password = password;
		notifyChange(BR.password);
	}

	@Bindable
	public String getHeight() {

		return height;
	}

	public void setHeight(String height) {

		this.height = height;
		notifyChange(BR.height);
	}

	@Bindable
	public String getBg_img() {

		return bg_img;
	}

	public void setBg_img(String bg_img) {

		this.bg_img = bg_img;
		notifyChange(BR.bg_img);
	}

	@Bindable
	public String getInterest() {

		return interest;
	}

	public void setInterest(String interest) {

		this.interest = interest;
		notifyChange(BR.interest);
	}

	@Bindable
	public String getLike() {

		return like;
	}

	public void setLike(String like) {

		this.like = like;
		notifyChange(BR.like);
	}

	@Bindable
	public String getUser_id() {

		return user_id;
	}

	public void setUser_id(String user_id) {

		this.user_id = user_id;
		notifyChange(BR.user_id);
	}

	@Bindable
	public String getUser_ico() {

		return user_ico;
	}

	public void setUser_ico(String user_ico) {

		this.user_ico = user_ico;
		notifyChange(BR.user_ico);
	}

	@Bindable
	public String getAvatar() {

		return avatar;
	}

	public void setAvatar(String avatar) {

		this.avatar = avatar;
		notifyChange(BR.avatar);
	}

	@Bindable
	public String getAge() {

		return age;
	}

	public void setAge(String age) {

		this.age = age;
		notifyChange(BR.age);
	}

	@Bindable
	public String getProvince() {

		return province;
	}

	public void setProvince(String province) {

		this.province = province;
		notifyChange(BR.province);
	}

	@Bindable
	public String getCity() {

		return city;
	}

	public void setCity(String city) {

		this.city = city;
		notifyChange(BR.city);
	}

	@Bindable
	public String getTip() {

		return tip;
	}

	public void setTip(String tip) {

		this.tip = tip;
		notifyChange(BR.tip);
	}

	@Bindable
	public String getFileType() {

		return fileType;
	}

	public void setFileType(String fileType) {

		this.fileType = fileType;
		notifyChange(BR.fileType);
	}

	@Bindable
	public String getFromName() {

		return fromName;
	}

	public void setFromName(String fromName) {

		this.fromName = fromName;
		notifyChange(BR.fromName);
	}

	@Bindable
	public String getToName() {

		return toName;
	}

	public void setToName(String toName) {

		this.toName = toName;
		notifyChange(BR.toName);
	}

	@Bindable
	public String getPresentName() {

		return presentName;
	}

	public void setPresentName(String presentName) {

		this.presentName = presentName;
		notifyChange(BR.presentName);
	}

	@Bindable
	public SuperResponse getFollowMeUser() {

		return followMeUser;
	}

	public void setFollowMeUser(SuperResponse followMeUser) {

		this.followMeUser = followMeUser;
		notifyChange(BR.followMeUser);
	}

	@Bindable
	public SuperResponse getMeFollowUser() {

		return meFollowUser;
	}

	public void setMeFollowUser(SuperResponse meFollowUser) {

		this.meFollowUser = meFollowUser;
		notifyChange(BR.meFollowUser);
	}

	@Bindable
	public List<SuperResponse> getMembers() {

		return members;
	}

	public void setMembers(List<SuperResponse> members) {

		this.members = members;
		notifyChange(BR.members);
	}

	@Bindable
	public int getCount() {

		return count;
	}

	public void setCount(int count) {

		this.count = count;
		notifyChange(BR.count);
	}

	@Bindable
	public String getDistance() {

		return distance;
	}

	public void setDistance(String distance) {

		this.distance = distance;
		notifyChange(BR.distance);
	}

	@Bindable
	public boolean isEnabled() {

		return isEnabled;
	}

	public void setEnabled(boolean enabled) {

		isEnabled = enabled;
		notifyChange(BR.enabled);
	}
	@Bindable
	public boolean isAttention() {

		return isAttention;
	}

	public void setAttention(boolean attention) {

		isAttention = attention;
		notifyChange(BR.attention);
	}
	@Bindable
	public boolean isSendLetter() {

		return isSendLetter;
	}

	public void setSendLetter(boolean sendLetter) {

		isSendLetter = sendLetter;
		notifyChange(BR.sendLetter);
	}
	@Bindable
	public boolean isSayHi() {

		return isSayHi;
	}

	public void setSayHi(boolean sayHi) {

		isSayHi = sayHi;
		notifyChange(BR.sayHi);
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

		return "SuperResponse{" + "other_info=" + other_info + ", photos=" + photos + ", present_info=" + present_info + ", personal_space=" + personal_space + ", base_info=" + base_info + ", user_contact=" + user_contact + ", more_info=" + more_info + ", photo_list=" + photo_list + ", present_list=" + present_list + ", user_list=" + user_list + ", followMeUser=" + followMeUser + ", meFollowUser=" + meFollowUser + ", members=" + members + ", count=" + count + ", isEnabled=" + isEnabled + ", isAttention=" + isAttention + ", isSendLetter=" + isSendLetter + ", isSayHi=" + isSayHi + ", distance='" + distance + '\'' + ", fromName='" + fromName + '\'' + ", toName='" + toName + '\'' + ", presentName='" + presentName + '\'' + ", about_me='" + about_me + '\'' + ", qianmin='" + qianmin + '\'' + ", authentication='" + authentication + '\'' + ", photo_count='" + photo_count + '\'' + ", present_count='" + present_count + '\'' + ", last_logintime='" + last_logintime + '\'' + ", jymd='" + jymd + '\'' + ", lianaiguan='" + lianaiguan + '\'' + ", xwyhdd='" + xwyhdd + '\'' + ", hlzt='" + hlzt + '\'' + ", name='" + name + '\'' + ", img='" + img + '\'' + ", visitsCount=" + visitsCount + ", icon='" + icon + '\'' + ", msg='" + msg + '\'' + ", userName='" + userName + '\'' + ", provinced='" + provinced + '\'' + ", ftype=" + ftype + ", rechargeAmount=" + rechargeAmount + ", nickName='" + nickName + '\'' + ", haveHome='" + haveHome + '\'' + ", haveCar='" + haveCar + '\'' + ", sexInterest='" + sexInterest + '\'' + ", fid='" + fid + '\'' + ", zy_address='" + zy_address + '\'' + ", zy_age='" + zy_age + '\'' + ", zy_height='" + zy_height + '\'' + ", zy_tag='" + zy_tag + '\'' + ", hxUserId='" + hxUserId + '\'' + ", sed_shxg='" + sed_shxg + '\'' + ", sed_xqah='" + sed_xqah + '\'' + ", sed_xag='" + sed_xag + '\'' + ", sed_jzg='" + sed_jzg + '\'' + ", sed_ly='" + sed_ly + '\'' + ", pwd='" + pwd + '\'' + ", dialogTitle='" + dialogTitle + '\'' + ", sex=" + sex + ", size=" + size + ", page=" + page + ", type=" + type + ", currentYear=" + currentYear + ", currentMonth=" + currentMonth + ", currentDay=" + currentDay + ", minAge=" + minAge + ", maxAge=" + maxAge + ", minHeight=" + minHeight + ", maxHeight=" + maxHeight + ", diamond=" + diamond + ", coins=" + coins + ", isMsgNotify=" + isMsgNotify + ", appCacheSize='" + appCacheSize + '\'' + ", jd='" + jd + '\'' + ", wd='" + wd + '\'' + ", id='" + id + '\'' + ", mobile='" + mobile + '\'' + ", birthday='" + birthday + '\'' + ", pic='" + pic + '\'' + ", fileType='" + fileType + '\'' + ", interest_tag='" + interest_tag + '\'' + ", token='" + token + '\'' + ", vip='" + vip + '\'' + ", address='" + address + '\'' + ", qianming='" + qianming + '\'' + ", profession='" + profession + '\'' + ", educated='" + educated + '\'' + ", wages='" + wages + '\'' + ", qq='" + qq + '\'' + ", ydl='" + ydl + '\'' + ", hqxxw='" + hqxxw + '\'' + ", tz='" + tz + '\'' + ", password='" + password + '\'' + ", height='" + height + '\'' + ", bg_img='" + bg_img + '\'' + ", interest='" + interest + '\'' + ", like='" + like + '\'' + ", user_id='" + user_id + '\'' + ", user_ico='" + user_ico + '\'' + ", avatar='" + avatar + '\'' + ", age='" + age + '\'' + ", province='" + province + '\'' + ", city='" + city + '\'' + ", tip='" + tip + '\'' + "} " + super.toString();
	}
}
