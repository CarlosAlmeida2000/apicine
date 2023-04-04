package com.carlos.apicine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.carlos.apicine.entities.Pelicula;


@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, Integer>{

	@Query("FROM Pelicula p where p.nombre=?1")
	public List<Pelicula> findByNombre(String name);
	
	@Query("FROM Pelicula p where p.nombre=?1 and p.id!=?2")
	public List<Pelicula> findByNombreId(String name, Integer id);
}
