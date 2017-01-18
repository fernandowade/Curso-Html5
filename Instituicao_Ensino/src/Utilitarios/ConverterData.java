package Utilitarios;

import java.text.SimpleDateFormat;  
import java.util.Date;  
import java.text.ParseException;  

/**
 *
 * @author professor
 */
public class ConverterData {
       public SimpleDateFormat formatIso;  
       public SimpleDateFormat formatBra;  
       public Date date;  
         
       public ConverterData(){  
              formatIso = new SimpleDateFormat("yyyy-MM-dd");  
              formatBra = new SimpleDateFormat("dd/MM/yyyy");  
       }  

       public String parseDataIso(String dataBra){  
              try{  
                 date = formatIso.parse(dataBra);  
                 return(formatIso.format(date));  
              }catch(ParseException e){  
                 e.printStackTrace();  
                 return("Parse Date Error");  
              }  
       }  

        public String parseDataBra(String dataIso){  
              try{  
                 date = formatBra.parse(dataIso);  
                 return(formatBra.format(date));  
              }catch(ParseException e){
                 e.printStackTrace();  
                 return("Parse Date Error");  
              }  
        }  
}
