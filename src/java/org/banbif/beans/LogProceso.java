/*
 * esta clase servira para traer la informacion del log generado por IBS
 * y listar los logs  generados.                           
 * la lista de logs se genera cuando se graba el log. al generarse otro log se agrega a la lista
 * tanto la lista de logs y el mismo log generado tienen la misma estructura. solo un campo de descripcion texto.
*/
package org.banbif.beans;

import java.io.Serializable;

/**
 *
 * @author PSSPERU-TI - Omar Benites
 */
public class LogProceso implements Serializable {
  private String textoLog; //puede ser la informacion del log como el nombre de log generado(lista de logs por fecha)

    public LogProceso() {
        
    }

    public LogProceso(String textoLog) {
        this.textoLog = textoLog;
    }

    public String getTextoLog() {
        return textoLog;
    }

    public void setTextoLog(String textoLog) {
        this.textoLog = textoLog;
    }

    @Override
    public String toString() {
        return textoLog; //To change body of generated methods, choose Tools | Templates.
    }
  
}
