/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.banbif.controller;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.banbif.beans.LogProceso;
import org.banbif.service.LogProcesoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author PSSPERU-TI
 */
@Controller
public class LogProcesoController {
    @Autowired 
    private LogProcesoService servicio;
    
    @RequestMapping(value="/leerdetalle",method=RequestMethod.POST,produces="application/json")
    public @ResponseBody String leerdetalle(HttpServletRequest request){
        
        HashMap<String, String> modelo = new HashMap<>();
        
        String nomlog = request.getParameter("nomlog");
        String nomato = request.getParameter("nomato");
        
        List<LogProceso> listlogproc = servicio.obtLogFile(nomlog, nomato);
       
        if(listlogproc.size()>0){
            modelo.put("rpta", "true");
            modelo.put("nomato",nomato);
            modelo.put("detalles",listlogproc.toString());            
        }
        Gson gson = new Gson();
        String json = gson.toJson(modelo);
        return json;
    }
}
