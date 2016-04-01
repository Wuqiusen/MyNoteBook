package com.example.huson.mynotebook.domain;


public class WishInfo {
	private int id;
	private int type;
	private int needcoin;
	private String context;
	private String starttime;
	private String endtime;
	private int isfinish;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getNeedcoin() {
		return needcoin;
	}

	public void setNeedcoin(int needcoin) {
		this.needcoin = needcoin;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public int getIsfinish() {
		return isfinish;
	}

	public void setIsfinish(int isfinish) {
		this.isfinish = isfinish;
	}

	@Override
	public String toString() {
		return "WishInfo{" +
				"id=" + id +
				", type=" + type +
				", needcoin=" + needcoin +
				", context='" + context + '\'' +
				", starttime='" + starttime + '\'' +
				", endtime='" + endtime + '\'' +
				", isfinish=" + isfinish +
				'}';
	}
}
