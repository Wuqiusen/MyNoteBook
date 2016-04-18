package com.example.huson.mynotebook.domain;


public class DataInfo {
	private int id;
	private String msgid;
	private String type;
	private String context;
	private String starttime;
	private String endtime;
	private String complete;
	private String year;
	private String month;
	private String week;
	private String day;
	private String finishyear;
	private String finishmonth;
	private String finishweek;
	private String finishday;
	private int isread;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getComplete() {
		return complete;
	}

	public void setComplete(String complete) {
		this.complete = complete;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public int getIsread() {
		return isread;
	}

	public String getFinishyear() {
		return finishyear;
	}

	public void setFinishyear(String finishyear) {
		this.finishyear = finishyear;
	}

	public String getFinishmonth() {
		return finishmonth;
	}

	public void setFinishmonth(String finishmonth) {
		this.finishmonth = finishmonth;
	}

	public String getFinishweek() {
		return finishweek;
	}

	public void setFinishweek(String finishweek) {
		this.finishweek = finishweek;
	}

	public String getFinishday() {
		return finishday;
	}

	public void setFinishday(String finishday) {
		this.finishday = finishday;
	}

	public void setIsread(int isread) {
		this.isread = isread;
	}

	@Override
	public String toString() {
		return "DataInfo{" +
				"id=" + id +
				", msgid='" + msgid + '\'' +
				", type='" + type + '\'' +
				", context='" + context + '\'' +
				", starttime='" + starttime + '\'' +
				", endtime='" + endtime + '\'' +
				", complete='" + complete + '\'' +
				", year='" + year + '\'' +
				", month='" + month + '\'' +
				", week='" + week + '\'' +
				", day='" + day + '\'' +
				", finishyear='" + finishyear + '\'' +
				", finishmonth='" + finishmonth + '\'' +
				", finishweek='" + finishweek + '\'' +
				", finishday='" + finishday + '\'' +
				", isread=" + isread +
				'}';
	}
}
