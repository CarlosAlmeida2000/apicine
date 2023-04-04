package com.carlos.apicine.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carlos.apicine.entities.PeliculaSala;
import com.carlos.apicine.repository.PeliculaSalaRepository;

@Service
@Transactional
public class PeliculaSalaService {

	@Autowired
	private PeliculaSalaRepository peliculaSalaRepository;
	
    public PeliculaSala create(PeliculaSala peliculaSala) {
        return peliculaSalaRepository.save(peliculaSala);
    }

    public List<PeliculaSala> getAllPeliculaSala() {
    	return this.peliculaSalaRepository.findAll();
    }

    public PeliculaSala getPeliculaSalaById(Integer peliculaSalaId) {

        Optional<PeliculaSala> peliculaSalaDb = this.peliculaSalaRepository.findById(peliculaSalaId);

        if(peliculaSalaDb.isPresent()) {
            return peliculaSalaDb.get();
        } else {
            return null;
        }
    }

    public PeliculaSala update(PeliculaSala peliculaSala) {
        Optional<PeliculaSala> peliculaSalaDb = this.peliculaSalaRepository.findById(peliculaSala.getId());

        if(peliculaSalaDb.isPresent()) {
            return peliculaSalaRepository.save(peliculaSala);
        } else {
             return null;
        }
    }

    public Boolean delete(Integer peliculaSalaId) {
        Optional<PeliculaSala> peliculaSalaDb = this.peliculaSalaRepository.findById(peliculaSalaId);

        if(peliculaSalaDb.isPresent()) {
            this.peliculaSalaRepository.deleteById(peliculaSalaId);
            return true;
        } else {
            return false;
        }
    }
    
    public List<PeliculaSala> searchMovie(String name, Integer salaId) {
        return peliculaSalaRepository.searchMovie(name, salaId);
    }
    
    public Iterable<PeliculaSala> searchDate(String fecha) {
        return peliculaSalaRepository.searchDate(fecha);
    }
    
	public List<PeliculaSala> searchRoom(String name) {
		return peliculaSalaRepository.searchRoom(name);
	}
}
