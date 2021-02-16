package org.banbif.beans;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author PSSPERU-TI - Omar Benites
 */
public class Inventario {
    private String codsecbanco;    
    private String nombreorigen;
    private String descripcion;
    private String nombreato;
    private String longreg;
    private String indrecep;
    private String tabperiodopc;
    private LocalDate fecharegistro;
    private LocalTime horaregistro;
    private String usuarireg;
    private String programreg;
    private String estadoreg;
    private String logproceso;
    
    public Inventario(){
    }
    
    public Inventario(String codsecbanco, String nombreorigen, String descripcion, String nombreato,
                      String longreg, String tabperiodopc, LocalDate fecharegistro, LocalTime horaregistro, 
                      String usuarireg, String programreg, String estadoreg, String logproceso) {
        this.codsecbanco = codsecbanco;
        this.nombreorigen = nombreorigen;
        this.descripcion = descripcion;
        this.nombreato = nombreato;
        this.longreg = longreg;
        this.tabperiodopc = tabperiodopc;
        this.fecharegistro = fecharegistro;
        this.horaregistro = horaregistro;
        this.usuarireg = usuarireg;
        this.programreg = programreg;
        this.estadoreg = estadoreg;
        this.logproceso = logproceso;
    }
    
    
    public String getNombreorigen() {
        return nombreorigen;
    }

    public void setNombreorigen(String nombreorigen) {
        this.nombreorigen = nombreorigen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombreato() {
        return nombreato;
    }

    public void setNombreato(String nombreato) {
        this.nombreato = nombreato;
    }    
        public String getLongreg() {
        return longreg;
    }

    public void setLongreg(String longreg) {
        this.longreg = longreg;
    }
    
    public String getCodsecbanco() {
        return codsecbanco;
    }

    public void setCodsecbanco(String codsecbanco) {
        this.codsecbanco = codsecbanco;
    }

    public String getTabperiodopc() {
        return tabperiodopc;
    }

    public void setTabperiodopc(String tabperiodopc) {
        this.tabperiodopc = tabperiodopc;
    }

    public LocalDate getFecharegistro() {
        return fecharegistro;
    }

    public void setFecharegistro(LocalDate fecharegistro) {
        this.fecharegistro = fecharegistro;
    }

    public LocalTime getHoraregistro() {
        return horaregistro;
    }

    public void setHoraregistro(LocalTime horaregistro) {
        this.horaregistro = horaregistro;
    }

    public String getUsuarireg() {
        return usuarireg;
    }

    public void setUsuarireg(String usuarireg) {
        this.usuarireg = usuarireg;
    }

    public String getProgramreg() {
        return programreg;
    }

    public void setProgramreg(String programreg) {
        this.programreg = programreg;
    }

    public String getEstadoreg() {
        return estadoreg;
    }

    public void setEstadoreg(String estadoreg) {
        this.estadoreg = estadoreg;
    }

    public String getLogproceso() {
        return logproceso;
    }

    public void setLogproceso(String logproceso) {
        this.logproceso = logproceso;
    }

    public String getIndrecep() {
        return indrecep;
    }

    public void setIndrecep(String indrecep) {
        this.indrecep = indrecep;
    }
    
}
