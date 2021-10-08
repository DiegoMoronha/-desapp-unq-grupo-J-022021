package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.configKeyValue;

import java.util.HashMap;
import java.util.Map;

public class KeyValueSaver {

    private static String token ;
    private static Map<String,Long> keyValues= new HashMap<String,Long>();
    private static Map<Long,Boolean> userCompleteTransaction= new HashMap<Long,Boolean>();
    private static Map<Long,Long> exposeIdToNegociate= new HashMap<Long,Long>();

    public static void completeTransaction(Long id){
        userCompleteTransaction.put(id,true);
    }

    public static void initTransaction(Long iduser,Long idToNegociate){
        userCompleteTransaction.put(idToNegociate,false);
        putIdandIdToNegociate(iduser, idToNegociate);
    }

    public static void cancelTransaction(Long id){
        userCompleteTransaction.remove(id);
    }

    public static Boolean isCompletedTransaction(Long id){
        return userCompleteTransaction.get(id);
    }

    public static void putKeyValue(String key ,Long value){
        setToken(key);
        keyValues.put(key,value);
    }

    public static Long getUserIdLogged(String key){
       return keyValues.get(key) ;
    }


    public static String getToken() {
        return token;
    }

    private static void setToken(String token) {
        KeyValueSaver.token = token;
    }

    private static void putIdandIdToNegociate(Long id, Long idToNegociate){
        exposeIdToNegociate.put(idToNegociate,id);
    }

    public static Long getIdToNegociate(Long id){
        return exposeIdToNegociate.get(id);
    }

}
