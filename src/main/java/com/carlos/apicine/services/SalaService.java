package com.carlos.apicine.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carlos.apicine.entities.Sala;
import com.carlos.apicine.repository.SalaRepository;

@Service
@Transactional
public class SalaService {
	
	@Autowired
	private SalaRepository salaRepository;
	
    public Sala create(Sala sala) {
    	sala.setEliminado(false);
        return salaRepository.save(sala);
    }

    public List<Sala> getAllSalas() {
    	return this.salaRepository.findAll();
    }
    
    public boolean checkRepeated(String name) {
    	return this.salaRepository.findByNombre(name).size() > 0;
    }
    
	public boolean checkRepeated(String name, Integer id) {
		return this.salaRepository.findByNombreId(name, id).size() > 0;
	}

    public Sala getSalaById(Integer salaId) {

        Optional<Sala> salaDb = this.salaRepository.findById(salaId);

        if(salaDb.isPresent()) {
            return salaDb.get();
        } else {
            return null;
        }
    }

    public Sala update(Sala sala) {
        Optional<Sala> salaDb = this.salaRepository.findById(sala.getId());

        if(salaDb.isPresent()) {
        	sala.setEliminado(false);
            return salaRepository.save(sala);
        } else {
             return null;
        }
    }

    public Boolean delete(Integer salaId) {
        Optional<Sala> salaDb = this.salaRepository.findById(salaId);

        if(salaDb.isPresent()) {
            this.salaRepository.deleteById(salaId);
            return true;
        } else {
            return false;
        }
    }
}
