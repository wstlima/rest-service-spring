package com.desafioapp.dto;

import java.util.List;

import com.desafioapp.entity.AwardsInterval;
import com.desafioapp.models.ReturnStatus;

public class AwardsIntervalDTO {
	private List<AwardsInterval> min;
	private List<AwardsInterval> max;
	private ReturnStatus returnStatus;
	
	public List<AwardsInterval> getMin() {
		return min;
	}
	public void setMin(List<AwardsInterval> min) {
		this.min = min;
	}
	public List<AwardsInterval> getMax() {
		return max;
	}
	public void setMax(List<AwardsInterval> max) {
		this.max = max;
	}
	public ReturnStatus getReturnStatus() {
		return returnStatus;
	}
	public void setReturnStatus(ReturnStatus returnStatus) {
		this.returnStatus = returnStatus;
	}
	public AwardsIntervalDTO() {
		super();
		this.returnStatus = new ReturnStatus("00200", "/movies/awardsinterval");
	}
	
	
	

}
