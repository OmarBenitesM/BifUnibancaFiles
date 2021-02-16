/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.banbif.beans;

/**
 *
 * @author PSSPERU-TI
 */
public class UnlFilesParam {
    // esta clase es para generar los .tto para descargar los archivos inventario(OPEINVW),
    // opciones de control (OPCTRLF) y control cnt (CNTRLCNT)
    private String nomfile;
    private String longitud;

    public UnlFilesParam() {
        
    }

    public String getNomfile() {
        return nomfile;
    }

    public void setNomfile(String nomfile) {
        this.nomfile = nomfile;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    @Override
    public String toString() {
        return "UnlFilesParam{" + "nomfile=" + nomfile + ", longitud=" + longitud + '}';
    }
    
}
