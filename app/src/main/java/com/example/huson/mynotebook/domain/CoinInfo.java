package com.example.huson.mynotebook.domain;


public class CoinInfo {
	private int id;
	private int coin;
	private String year;
	private String month;
	private String week;
	private String day;
	private int type;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCoin() {
		return coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "CoinInfo{" +
				"id=" + id +
				", coin=" + coin +
				", year='" + year + '\'' +
				", month='" + month + '\'' +
				", week='" + week + '\'' +
				", day='" + day + '\'' +
				", type=" + type +
				'}';
	}
}
