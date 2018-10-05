package com.desafioapp.dto;

import java.util.List;

import com.desafioapp.models.AwardsIntervalWinnsCount;
import com.desafioapp.models.ReturnStatus;

public class MovieWinDTO {
	
	private List<AwardsIntervalWinnsCount> years;	
	private ReturnStatus returnStatus;
	
	public List<AwardsIntervalWinnsCount> getYears() {
		return years;
	}
	public void setYears(List<AwardsIntervalWinnsCount> years) {
		this.years = years;
	}
	public ReturnStatus getReturnStatus() {
		return returnStatus;
	}
	public void setReturnStatus(ReturnStatus returnStatus) {
		this.returnStatus = returnStatus;
	}
	
	public MovieWinDTO() {
		this.returnStatus = new ReturnStatus("00200", "/movies/yearsmoreonewinners");
	}

	
}
