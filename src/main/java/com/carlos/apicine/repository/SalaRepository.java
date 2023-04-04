package com.carlos.apicine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.carlos.apicine.entities.Sala;

@Repository
public interface SalaRepository  extends JpaRepository<Sala, Integer>{

	@Query("FROM Sala s where s.nombre=?1")
	public List<Sala> findByNombre(String name);
	
	@Query("FROM Sala s where s.nombre=?1 and s.id!=?2")
	public List<Sala> findByNombreId(String name, Integer id);
}
