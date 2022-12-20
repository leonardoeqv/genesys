/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonda.cliente.repositories;

import com.sonda.cliente.entities.ClienteEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Leonardo Villalobos
 */
public interface ClienteRepository extends JpaRepository<ClienteEntity, Integer> {
	public ClienteEntity findByidCliente(int idCliente);
    public ClienteEntity findByrut(int rut);
    public ClienteEntity findBycorreo(String correo);
    
    @Query(value = "SELECT *FROM tbl_clientes c WHERE c.co_cliente <> ?1 AND (c.rut = ?2 OR c.correo = ?3)",nativeQuery = true)
    public List<ClienteEntity> findByidClienteIsNotAndrutAndcorreo2(int idCliente, int rut, String correo);
    
}
