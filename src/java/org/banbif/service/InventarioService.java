/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.banbif.service;

import java.util.List;

import org.banbif.beans.Inventario;
import org.banbif.beans.UnlFilesParam;

/**
 *
 * @author PSSPERU-TI
 */
public interface InventarioService {
    //transferproc es el proceso principal
    public List<Inventario> transferProc(String fechaproceso);
    public List<Inventario> transferEventual(List<String> listaevt);
    public List<Inventario> getAllInv();
    public boolean unloadFilesBases(List<UnlFilesParam> tipoFileProceso, String tipoproc, String fechaproceso);
    public String valFecControl(String fileapertctrl);
}