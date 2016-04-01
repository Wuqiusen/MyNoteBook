package com.example.huson.mynotebook.domain;


public class TypeInfo {
	private int id;
	private String type;
	private String name;
	private String other;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	@Override
	public String toString() {
		return "TypeInfo{" +
				"id=" + id +
				", type='" + type + '\'' +
				", name='" + name + '\'' +
				", other='" + other + '\'' +
				'}';
	}
}
