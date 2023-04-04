package com.carlos.apicine.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carlos.apicine.entities.PeliculaSala;
import com.carlos.apicine.services.PeliculaSalaService;

@RestController
@RequestMapping("/api/peliculas-salas")
public class PeliculaSalaController {

	 @Autowired
	 private PeliculaSalaService peliculaSalaService;
	 

	 @GetMapping
	 public ResponseEntity<List<PeliculaSala>> getAllPeliculaSala() {
	     return ResponseEntity.status(HttpStatus.OK).body(peliculaSalaService.getAllPeliculaSala());
	 }

	 @GetMapping("/{id}")
	 public ResponseEntity<?> getPeliculaSalaById(@PathVariable Integer id) {
		 PeliculaSala peliculaSala = peliculaSalaService.getPeliculaSalaById(id);
		 
		 if(peliculaSala == null) {
			 return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new JSONObject().put("message", "No existe una película programada en sala con el ID: " + id).toString());
		 }
		 return ResponseEntity.status(HttpStatus.OK).body(peliculaSala);
	 }

	 @PostMapping
	 public ResponseEntity<?> create(@RequestBody PeliculaSala peliculaSala) {	
		 return ResponseEntity.status(HttpStatus.CREATED).body(peliculaSalaService.create(peliculaSala));
	 }

	 @PutMapping("/{id}")
	 public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody PeliculaSala peliculaSala) {
		 peliculaSala.setId(id);
		 peliculaSala = peliculaSalaService.update(peliculaSala);
		 
		 if(peliculaSala == null) {
			 return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new JSONObject().put("message", "No se logró modificar una película programada en sala con el ID: " + id).toString()); 
		 }
	     return ResponseEntity.status(HttpStatus.OK).body(peliculaSala);
	 }

	 @DeleteMapping("/{id}")
	 public ResponseEntity<?> delete(@PathVariable Integer id) {
	     if(this.peliculaSalaService.delete(id)){
	    	 return ResponseEntity.status(HttpStatus.OK).body(new JSONObject().put("message", "Se eliminó correctamente la película programada en sala con el ID: " + id).toString()); 
	     } else {
	    	 return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new JSONObject().put("message", "No se logró eliminar la película programada en sala con el ID: " + id).toString());
	     }
	 }
	 
	 @GetMapping("/{name}/{sala_id}")
	 public ResponseEntity<?> searchMovie(@PathVariable("name") String name, @PathVariable("sala_id") Integer salaId) {
		 List<PeliculaSala> listaPeliculas = peliculaSalaService.searchMovie(name, salaId);
		 
		 if(listaPeliculas.size() == 0) {
			 return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new JSONObject().put("message", "No existe una película programada en la sala con los datos proporcionados").toString());
		 }
		 return ResponseEntity.status(HttpStatus.OK).body(listaPeliculas);
	 }
	 
	 @GetMapping("fecha/{fecha}")
	 public ResponseEntity<?> searchDate(@PathVariable String fecha) {
		 List<PeliculaSala> listaPeliculas = StreamSupport.stream(peliculaSalaService.searchDate(fecha).spliterator(), false).collect(Collectors.toList());
		 
		 if(listaPeliculas.size() == 0) {
			 return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new JSONObject().put("message", "No existen películas con la fecha de publicación proprocionada").toString());
		 }
		 
		 return ResponseEntity.status(HttpStatus.OK).body(listaPeliculas);
	 }
	 
	 @GetMapping("sala/{name}")
	 public ResponseEntity<?> searchRoom(@PathVariable String name) {
		 List<PeliculaSala> listaPeliculaSalas = peliculaSalaService.searchRoom(name);
		 
		 if(listaPeliculaSalas.size() == 0) {
			 return ResponseEntity.status(HttpStatus.OK).body(new JSONObject().put("message", "Sala vacía").toString());
		 }else if(listaPeliculaSalas.size() < 3) {
			 return ResponseEntity.status(HttpStatus.OK).body(new JSONObject().put("message", "Sala casi vacía").toString());
		 }else if(listaPeliculaSalas.size() >= 3 & listaPeliculaSalas.size() <= 5) {
			 return ResponseEntity.status(HttpStatus.OK).body(new JSONObject().put("message", "Sala casi llena").toString());
		 }
		 
		 return ResponseEntity.status(HttpStatus.OK).body(new JSONObject().put("message", "Sala llena").toString());
	 }
}
