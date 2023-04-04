package com.carlos.apicine.controller;

import java.util.List;

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

import com.carlos.apicine.config.SchemaValidations;
import com.carlos.apicine.entities.Pelicula;
import com.carlos.apicine.services.PeliculaService;


@RestController
@RequestMapping("/api/peliculas")
public class PeliculaController {
	
	 @Autowired
	 private PeliculaService peliculaService;
	 
	 @Autowired
	 private SchemaValidations schemaValidations;
	 
	 private String pathJsonValidate = "/static/json/validate_pelicula.json";

	 @GetMapping
	 public ResponseEntity<List<Pelicula>> getAllPeliculas() {
	     return ResponseEntity.status(HttpStatus.OK).body(peliculaService.getAllPeliculas());
	 }

	 @GetMapping("/{id}")
	 public ResponseEntity<?> getPeliculaById(@PathVariable Integer id) {
		 Pelicula pelicula = peliculaService.getPeliculaById(id);
		 
		 if(pelicula == null) {
			 return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new JSONObject().put("message", "No existe una película con el ID: " + id).toString());
		 }
		 return ResponseEntity.status(HttpStatus.OK).body(pelicula);
	 }

	 @PostMapping
	 public ResponseEntity<?> create(@RequestBody Pelicula pelicula) {	
		 
		 if(!this.schemaValidations.validateJson(pathJsonValidate, pelicula)) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new JSONObject().put("message", "Los datos de entrada no tienen un formato válido").toString());
		 }
		 
		 if(peliculaService.checkRepeated(pelicula.getNombre())) {
			 return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new JSONObject().put("message", "El nombre de la película ya existe").toString());			 
		 }
		
		 return ResponseEntity.status(HttpStatus.CREATED).body(peliculaService.create(pelicula));
	 }

	 @PutMapping("/{id}")
	 public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody Pelicula pelicula) {
	     
		 if(!this.schemaValidations.validateJson(pathJsonValidate, pelicula)) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new JSONObject().put("message", "Los datos de entrada no tienen un formato válido").toString());
		 }
		 
		 if(peliculaService.checkRepeated(pelicula.getNombre(), id)) {
			 return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new JSONObject().put("message", "El nombre de la película ya existe").toString());			 
		 }
		 
		 pelicula.setId(id);
		 pelicula = peliculaService.update(pelicula);
		 
		 if(pelicula == null) {
			 return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new JSONObject().put("message", "No se logró modificar una película con el ID: " + id).toString()); 
		 }
	     return ResponseEntity.status(HttpStatus.OK).body(pelicula);
	 }

	 @DeleteMapping("/{id}")
	 public ResponseEntity<?> delete(@PathVariable Integer id) {
	     if(this.peliculaService.delete(id)){
	    	 return ResponseEntity.status(HttpStatus.OK).body(new JSONObject().put("message", "Se eliminó correctamente la película con el ID: " + id).toString()); 
	     } else {
	    	 return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new JSONObject().put("message", "No se logró eliminar la película con el ID: " + id).toString());
	     }
	 }
}
