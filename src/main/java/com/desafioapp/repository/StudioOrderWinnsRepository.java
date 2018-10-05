package com.desafioapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.desafioapp.entity.Studio;
import com.desafioapp.models.StudiosOrderWinns;

public interface StudioOrderWinnsRepository extends JpaRepository<Studio, Long> {
	String strQry = "SELECT * FROM (SELECT S.NAME, SUM(CASE WHEN M.WINNER = TRUE THEN 1	ELSE 0 END) AS WINS FROM STUDIOS S, MOVIES M WHERE M.ID = S.FK_ID_MOVIE GROUP BY S.NAME ORDER BY WINS DESC ) WHERE WINS >0";
    @Query(value = strQry, nativeQuery = true)
    List<StudiosOrderWinns> studiosOrderWinns();
}
