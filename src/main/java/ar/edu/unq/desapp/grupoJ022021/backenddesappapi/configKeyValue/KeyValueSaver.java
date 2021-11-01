package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.configKeyValue;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

public class KeyValueSaver {

    private static Long transactionID= 0L;
    private static Map<String,Long> keyValues= new HashMap<String,Long>();
    private static Map<Long,Boolean> userCompleteTransaction= new HashMap<Long,Boolean>();
    private static Map<Long,Long> exposeIdToNegociate= new HashMap<Long,Long>();
    private static Map<Long,Long> assocActivityWithUnegociate=new HashMap<Long,Long>();
    private static Map<Long,Long> transactionIDWithUsersID=new HashMap<Long,Long>();
    private static Map<Long,LocalDateTime> transactionIDDate=new HashMap<Long, LocalDateTime>();

    public static void putTransactionIdwithUsers(Long id, Long idToNegociate){
        if(getTransactionId(id)==null ||getTransactionId(idToNegociate)==null) {
            transactionIDWithUsersID.put(id, transactionID);
            transactionIDWithUsersID.put(idToNegociate, transactionID);
            transactionID++;
        }
    }

    public static LocalDateTime dateTransaction(Long id , Long idToNegociate){
        Long transactionId = getTransactionId(id);
        Boolean startUserId=isCompletedTransaction(id)==null?true:isCompletedTransaction(id);
        Boolean startIdToNegociate=isCompletedTransaction(idToNegociate)==null?true:isCompletedTransaction(idToNegociate);
        if(startUserId&& startIdToNegociate) {
            LocalDateTime date = LocalDateTime.now(ZoneId.of("America/Buenos_Aires"));
            transactionIDDate.put(transactionId, date);
        }
        return transactionIDDate.get(transactionId);
    }



    private static Long getTransactionId(Long idUser){
        return transactionIDWithUsersID.get(idUser);
    }

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
