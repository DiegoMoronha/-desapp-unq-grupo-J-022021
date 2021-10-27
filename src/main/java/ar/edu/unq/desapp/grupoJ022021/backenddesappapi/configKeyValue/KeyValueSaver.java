package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.configKeyValue;

import java.util.HashMap;
import java.util.Map;

public class KeyValueSaver {

    private static Map<String,Long> keyValues= new HashMap<String,Long>();
    private static Map<Long,Boolean> userCompleteTransaction= new HashMap<Long,Boolean>();
    private static Map<Long,Long> exposeIdToNegociate= new HashMap<Long,Long>();
    private static Map<Long,Long> assocActivityWithUnegociate=new HashMap<Long,Long>();

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

    public static void markActivityId(Long idToNegociate ,Long activityId){
        assocActivityWithUnegociate.put(idToNegociate,activityId);
    }

    public static Long activityUser(Long id){
        return assocActivityWithUnegociate.get(id);
    }

    public static Boolean isCompletedTransaction(Long id){
        return userCompleteTransaction.get(id);
    }

    public static void putKeyValue(String key ,Long value){
        keyValues.put(key,value);
    }


    private static void putIdandIdToNegociate(Long id, Long idToNegociate){
        exposeIdToNegociate.put(idToNegociate,id);
    }

    public static void removeData(Long userBuy, Long userSell){
        exposeIdToNegociate.remove(userSell);
        exposeIdToNegociate.remove(userBuy);
        cancelTransaction(userBuy);
        cancelTransaction(userSell);
        assocActivityWithUnegociate.remove(userBuy);
        assocActivityWithUnegociate.remove(userSell);
    }

    public static Long getIdToNegociate(Long id){
        return exposeIdToNegociate.get(id);
    }

}
