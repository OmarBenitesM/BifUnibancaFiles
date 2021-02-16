package org.banbif.controller;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.banbif.beans.Inventario;
import org.banbif.beans.UnlFilesParam;
import org.banbif.service.InventarioService;
import org.banbif.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 *
 * @author PSSPERU-TI - Omar Benites
 */
@Controller
public class InventarioController {
    @Autowired InventarioService servicio;
    
    @RequestMapping(value="/listainventario", method=RequestMethod.GET)
    public String doGetAll(HttpServletRequest request, Model modelo){
        String fecprocctrl = request.getParameter("fecctrl");
        List<Inventario> listinv = servicio.transferProc(fecprocctrl);
        if(listinv != null){
            modelo.addAttribute("listainv", listinv);
        }
        return "listainventario";
    }
    
    @RequestMapping(value="/procmanual", method=RequestMethod.GET)
    public String doGetAll2(Model modelo){
        List<Inventario> listinv = servicio.getAllInv();
        if(listinv != null){
            modelo.addAttribute("listainv", listinv);
        }
        return "procmanual";
    }
    
    @RequestMapping(value="/procmanual",method=RequestMethod.POST,produces="application/json")
    public @ResponseBody String procmanual(@RequestBody String datos){
        
        HashMap<String, Object> modelo = new HashMap<>();
        String cadevt = datos.replaceAll("=", "");
        //System.out.println("json real:" +cadevt);
        List<Inventario> lisprocevt;
        if(cadevt != null){
            String [] vector;
            vector = cadevt.split("&");
            List<String> files = new ArrayList<>();
            files = Arrays.asList(vector);            
            System.out.println("matriz de evt: " + files.toString());
            lisprocevt = servicio.transferEventual(files);
            
            if(lisprocevt != null){
                modelo.put("rpta", true);
            }else{
                modelo.put("rpta", false);
            }
        }    
        //modelo.put("detalle", lisprocevt);
        Gson gson = new Gson();
        String json = gson.toJson(modelo);
        return json;
    }
    
    @RequestMapping(value="/unlFilesBase",method=RequestMethod.POST,produces="application/json")
    public @ResponseBody String unlFilesBase(HttpServletRequest request){

        HashMap<String, Object> modelo = new HashMap<>();
        String tipoproceso = request.getParameter("tipoproc");
        String fecyhorcontrol = null;
        boolean rpta = false;

        List<UnlFilesParam> filesunl = new ArrayList<>();
        

        if(tipoproceso.equals(Util.PROCAUT)){            
            UnlFilesParam infofile2 = new UnlFilesParam();
            
            infofile2.setNomfile(Util.NOMFTTOCTRL);
            infofile2.setLongitud(Util.LNGFCTRL);

            filesunl.add(infofile2);
              
            rpta = servicio.unloadFilesBases(filesunl,Util.PROCAUT, Util.fechaactual());
            //proceso de validar fechas de OPCTRLF y CNTRLCNT sino son iguales no inicia proceso
            if(rpta){
                fecyhorcontrol = servicio.valFecControl(Util.NOMFTTOCTRL);
                if(fecyhorcontrol != null){
                   //descargando inventario con fecha y hora de tabla de control de operaciones
                   UnlFilesParam infofile1 = new UnlFilesParam();
                   filesunl.clear();                   
                   infofile1.setNomfile(Util.NOMFTTOINV);
                   infofile1.setLongitud(Util.LNGFINV);
                   filesunl.add(infofile1);
                   rpta = servicio.unloadFilesBases(filesunl, Util.PROCAUT, fecyhorcontrol);
                }
            }
        }
        if(tipoproceso.equals(Util.PROCMAN)){
            UnlFilesParam infofile1 = new UnlFilesParam();

            infofile1.setNomfile(Util.NOMFTTOINV);
            infofile1.setLongitud(Util.LNGFINV);
            
            filesunl.add(infofile1);
            
            rpta = servicio.unloadFilesBases(filesunl,Util.PROCMAN, Util.fechaactual()); //descarga archivo inventario para proceso manual
        }
        modelo.put("rpta", rpta);
        modelo.put("fecyhor",fecyhorcontrol);
        Gson gson = new Gson();
        String json = gson.toJson(modelo);
        return json;
    }
}
