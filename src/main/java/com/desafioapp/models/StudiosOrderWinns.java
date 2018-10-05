package com.desafioapp.models;

public class StudiosOrderWinns {
	private String name;
	private int winCount;

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public int getWinCount() {
		return winCount;
	}

	public void setWinCount(int winCount) {
		this.winCount = winCount;
	}
	
	
	public StudiosOrderWinns(String name, int wincount) {
		this.setName(name);
		this.setWinCount(wincount);
	}	
	
}
