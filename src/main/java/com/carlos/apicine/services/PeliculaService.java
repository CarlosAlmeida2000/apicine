package com.carlos.apicine.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carlos.apicine.entities.Pelicula;
import com.carlos.apicine.repository.PeliculaRepository;

@Service
@Transactional
public class PeliculaService {

	@Autowired
	private PeliculaRepository peliculaRepository;
	
    public Pelicula create(Pelicula pelicula) {
    	pelicula.setEliminado(false);
        return peliculaRepository.save(pelicula);
    }

    public List<Pelicula> getAllPeliculas() {
    	return this.peliculaRepository.findAll();
    }
    
    public boolean checkRepeated(String name) {
    	return this.peliculaRepository.findByNombre(name).size() > 0;
    }
    
    public boolean checkRepeated(String name, Integer id) {
    	return this.peliculaRepository.findByNombreId(name, id).size() > 0;
    }

    public Pelicula getPeliculaById(Integer peliculaId) {

        Optional<Pelicula> peliculaDb = this.peliculaRepository.findById(peliculaId);

        if(peliculaDb.isPresent()) {
            return peliculaDb.get();
        } else {
            return null;
        }
    }

    public Pelicula update(Pelicula pelicula) {
        Optional<Pelicula> peliculaDb = this.peliculaRepository.findById(pelicula.getId());

        if(peliculaDb.isPresent()) {
        	pelicula.setEliminado(false);
            return peliculaRepository.save(pelicula);
        } else {
             return null;
        }
    }

    public Boolean delete(Integer peliculaId) {
        Optional<Pelicula> peliculaDb = this.peliculaRepository.findById(peliculaId);

        if(peliculaDb.isPresent()) {
            this.peliculaRepository.deleteById(peliculaId);
            return true;
        } else {
            return false;
        }
    }
}
