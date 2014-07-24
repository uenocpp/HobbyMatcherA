package com.example.hobbymatcher;

public class Hobby {
	private String hobbyName;
	private String category;
	private long interestLevel;
	
	public Hobby(String name, String ctg, long interest) {
		this.hobbyName = name;
		this.category = ctg;
		this.interestLevel = interest;
	}
	
	public String getHobbyName() {
		return hobbyName;
	}
	public void setHobbyName(String hobbyName) {
		this.hobbyName = hobbyName;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public long getInterestLevel() {
		return interestLevel;
	}
	public void setInterestLevel(long interestLevel) {
		this.interestLevel = interestLevel;
	}
}
