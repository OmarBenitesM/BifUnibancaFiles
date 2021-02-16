/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.banbif.util;

/**
 *
 * @author PSSPERU-TI - Omar Benites
 */

import com.ibm.as400.access.*;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
public class CnxAS400 {
    
    public void ejcutarPeticion(String server, String user, String pwd, String comando){
        AS400 system400 = new AS400(server,user, pwd);
        CommandCall command = new CommandCall(system400);
        
        try{
            boolean rpta = command.run(comando);
            System.out.println("respuesta as400: " + rpta);
            if(!rpta){
                AS400Message[] msglist = command.getMessageList();
                
                if(msglist.length>0){
                    for(int i=0;i<msglist.length;i++){
                        System.out.println(msglist[i].getID());
                        System.out.println(":");
                        System.out.println(msglist[i].getText());
                    }
                }
                system400.disconnectService(AS400.COMMAND);
            } else{
                system400.disconnectService(AS400.COMMAND);
            }
        } catch (AS400SecurityException | ErrorCompletingRequestException | IOException | InterruptedException | PropertyVetoException ex) {
            Logger.getLogger(CnxAS400.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
