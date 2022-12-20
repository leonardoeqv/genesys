/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonda.cliente.controllers;

import com.sonda.cliente.entities.ClienteEntity;
import com.sonda.cliente.models.ResponseModel;
import com.sonda.cliente.repositories.ClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author leonardo
 */
@RestController
@RequestMapping(path = "/v1")
public class ClienteController {
    
    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("cliente")
    public ResponseEntity<ResponseModel> GetCliente (@RequestParam int rut){
        ResponseModel responseModel = new ResponseModel();
        ClienteEntity clienteEntity = clienteRepository.findByrut(rut);
        responseModel.setInformation((clienteEntity!=null)?"ok, encontrado":"error, no encontrado");
        responseModel.setData(clienteEntity);
        
        return ResponseEntity.status((clienteEntity!=null)?HttpStatus.OK:HttpStatus.NOT_FOUND).body(responseModel);
    }
    
    @GetMapping("clientes")
    public ResponseEntity<ResponseModel> listCliente (){
        ResponseModel responseModel = new ResponseModel();
        responseModel.setInformation("ok, listado");
        responseModel.setData(clienteRepository.findAll());
        
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }
    
    @PostMapping("cliente")
    public ResponseEntity<ResponseModel> InsertCliente(@RequestBody ClienteEntity clienteEntity) {
        ResponseModel responseModel = new ResponseModel();
        
        if (clienteRepository.findByrut(clienteEntity.getRut())== null){
            if (clienteRepository.findBycorreo(clienteEntity.getCorreo()) == null){
                responseModel.setInformation("Ok, creado existosamente");
                responseModel.setData(clienteRepository.save(clienteEntity));
                return ResponseEntity.status(HttpStatus.OK).body(responseModel);
            }else{
                responseModel.setInformation("Error, correo ya existe");
                responseModel.setData(clienteEntity);
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseModel);
            }
        }else{
            responseModel.setInformation("Error, rut ya existe");
            responseModel.setData(clienteEntity);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseModel);
        }
    }
    
    @PatchMapping("cliente")
    public ResponseEntity<ResponseModel> UpdateCliente(@RequestBody ClienteEntity clienteEntity) {
    	ResponseModel responseModel = new ResponseModel();
    	ClienteEntity auxClienteEntity = clienteRepository.findByidCliente(clienteEntity.getIdCliente());
        if (auxClienteEntity != null) {
        	if (clienteRepository.findByidClienteIsNotAndrutAndcorreo2(clienteEntity.getIdCliente(),clienteEntity.getRut(),clienteEntity.getCorreo()).size()==0) {
        		clienteRepository.save(clienteEntity);
            	responseModel.setInformation("ok, actualizado exitosamente");
                responseModel.setData(clienteEntity);
                return ResponseEntity.status(HttpStatus.OK).body(responseModel);
        	}else {
        		clienteRepository.delete(clienteEntity);
            	responseModel.setInformation("error, rut y/o correo ya existe");
                responseModel.setData(clienteEntity);
                return ResponseEntity.status(HttpStatus.OK).body(responseModel);	
        	}
        }else {
        	responseModel.setInformation("error, no encontrado");
            responseModel.setData(clienteEntity);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseModel);	
        }
    }
        
    @DeleteMapping("cliente")
    public ResponseEntity<ResponseModel> deleteCliente (@RequestParam int rut){
        ResponseModel responseModel = new ResponseModel();
        ClienteEntity clienteEntity = clienteRepository.findByrut(rut);
        if (clienteEntity != null) {
        	clienteRepository.delete(clienteEntity);
        	responseModel.setInformation("ok, borrado exitosamente");
            responseModel.setData(clienteEntity);
            return ResponseEntity.status(HttpStatus.OK).body(responseModel);
        }else {
        	responseModel.setInformation("error, no encontrado");
            responseModel.setData(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseModel);	
        }
        
    }
    
    
}
