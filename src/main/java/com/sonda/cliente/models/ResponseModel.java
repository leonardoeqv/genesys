/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonda.cliente.models;

/**
 *
 * @author leonardo Villalobos
 */
public class ResponseModel {
    private String mensaje;
    private Object data;

    public String getInformation() {
        return mensaje;
    }

    public void setInformation(String information) {
        this.mensaje = information;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
    
}
