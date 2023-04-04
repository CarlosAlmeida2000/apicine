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
import com.carlos.apicine.entities.Sala;
import com.carlos.apicine.services.SalaService;

@RestController
@RequestMapping("/api/salas")
public class SalaController {
	
	 @Autowired
	 private SalaService salaService;
	 
	 @Autowired
	 private SchemaValidations schemaValidations;
	 
	 private String pathJsonValidate = "/static/json/validate_sala.json";

	 @GetMapping
	 public ResponseEntity<List<Sala>> getAllSalas() {
	     return ResponseEntity.status(HttpStatus.OK).body(salaService.getAllSalas());
	 }
	 
	 @GetMapping("/{id}")
	 public ResponseEntity<?> getSalaById(@PathVariable Integer id) {
		 Sala sala = salaService.getSalaById(id);
		 
		 if(sala == null) {
			 return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new JSONObject().put("message", "No existe una sala con el ID: " + id).toString());
		 }
		 return ResponseEntity.status(HttpStatus.OK).body(sala);
	 }

	 @PostMapping
	 public ResponseEntity<?> create(@RequestBody Sala sala) {	
		 
		 if(!this.schemaValidations.validateJson(pathJsonValidate, sala)) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new JSONObject().put("message", "Los datos de entrada no tienen un formato válido").toString());
		 }
		 
		 if(salaService.checkRepeated(sala.getNombre())) {
			 return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new JSONObject().put("message", "El nombre de la sala ya existe").toString());			 
		 }
		
		 return ResponseEntity.status(HttpStatus.CREATED).body(salaService.create(sala));
	 }

	 @PutMapping("/{id}")
	 public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody Sala sala) {
	     
		 if(!this.schemaValidations.validateJson(pathJsonValidate, sala)) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new JSONObject().put("message", "Los datos de entrada no tienen un formato válido").toString());
		 }
		 
		 if(salaService.checkRepeated(sala.getNombre(), id)) {
			 return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new JSONObject().put("message", "El nombre de la sala ya existe").toString());			 
		 }
		 
		 sala.setId(id);
		 sala = salaService.update(sala);
		 
		 if(sala == null) {
			 return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new JSONObject().put("message", "No se logró modificar una sala con el ID: " + id).toString()); 
		 }
	     return ResponseEntity.status(HttpStatus.OK).body(sala);
	 }

	 @DeleteMapping("/{id}")
	 public ResponseEntity<?> delete(@PathVariable Integer id) {
	     if(this.salaService.delete(id)){
	    	 return ResponseEntity.status(HttpStatus.OK).body(new JSONObject().put("message", "Se eliminó correctamente la sala con el ID: " + id).toString()); 
	     } else {
	    	 return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new JSONObject().put("message", "No se logró eliminar la sala con el ID: " + id).toString());
	     }
	 }
}
