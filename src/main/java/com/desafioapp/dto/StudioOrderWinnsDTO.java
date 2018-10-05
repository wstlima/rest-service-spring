package com.desafioapp.dto;

import java.util.List;

import com.desafioapp.models.ReturnStatus;
import com.desafioapp.models.StudiosOrderWinns;

public class StudioOrderWinnsDTO {
	private List<StudiosOrderWinns> studios;	
	private ReturnStatus returnStatus;
	
	public List<StudiosOrderWinns> getStudios() {
		return studios;
	}
	public void setStudios(List<StudiosOrderWinns> studios) {
		this.studios = studios;
	}
	public ReturnStatus getReturnStatus() {
		return returnStatus;
	}
	public void setReturnStatus(ReturnStatus returnStatus) {
		this.returnStatus = returnStatus;
	}
	public StudioOrderWinnsDTO() {
		this.returnStatus = new ReturnStatus("00200", "/movies/studiosorderwinns");
	}
	
}
