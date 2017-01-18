/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilitarios;

/**
 *
 * @author professor
 */
public class Limpar {
        
    public String removerMascara(String str){  
               return str.replaceAll("\\D", "");  
    }
}  