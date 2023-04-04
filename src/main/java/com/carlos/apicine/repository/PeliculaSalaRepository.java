package com.carlos.apicine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.carlos.apicine.entities.PeliculaSala;

@Repository
public interface PeliculaSalaRepository  extends JpaRepository<PeliculaSala, Integer>{

	@Query("FROM PeliculaSala p where p.pelicula.nombre like %?1% and p.sala.id=?2")
    public List<PeliculaSala> searchMovie(String name, Integer salaId);
	
	@Query(value="Select * FROM tb_pelicula_sala as ps inner join tb_pelicula as p on p.id=ps.pelicula_id where ps.fecha_publicacion=?1", nativeQuery = true)
    public Iterable<PeliculaSala> searchDate(String fecha);
	
	@Query("FROM PeliculaSala p where p.sala.nombre like %?1%")
    public List<PeliculaSala> searchRoom(String name);
	
}
