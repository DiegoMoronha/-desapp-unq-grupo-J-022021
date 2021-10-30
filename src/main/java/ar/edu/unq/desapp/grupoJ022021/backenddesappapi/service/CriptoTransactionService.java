package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.service;

import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.Exceptions.CancelTransactionException;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.configKeyValue.KeyValueSaver;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto.TransactionBooleanResponseDto;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto.UserTransactionDto;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model.CriptoActivity;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model.CriptoTransaction;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.repository.CriptoActivityRepository;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.repository.CriptoTransactionRepository;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@Transactional
public class CriptoTransactionService {

    @Autowired
    private CriptoTransactionRepository criptoService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CriptoActivityRepository activityRepository;

    private LocalDateTime initTransactionHour;


    public UserTransactionDto startTransaction(Long id,Long userIdToNegociate, Long actId){
       if(initTransactionHour ==null){
        initTransactionHour = LocalDateTime.now(ZoneId.of("America/Buenos_Aires"));
       }
        CriptoActivity act=activityRepository.findById(actId).get();
        KeyValueSaver.initTransaction(id,userIdToNegociate);
        KeyValueSaver.markActivityId(userIdToNegociate,actId);
        User user = userRepository.findById(userIdToNegociate);
        boolean userCreatedActivity = activityRepository.findByIdAndUser(actId,userIdToNegociate)!=null;
        if(act.getActivityType().equals("buy") && userCreatedActivity) {

            return new UserTransactionDto(initTransactionHour,user.getName(),user.getLastName(),act.getCriptoName(),
                    act.getValueCripto(),act.getAmountInArs(),act.getNominals(),user.getTransactions().size(),
                    user.getReputation(),user.getCvu());
        }
        else{
            return checkSellOptionOrDefaultBuy(act,user,userCreatedActivity);
        }
    }


    private UserTransactionDto checkSellOptionOrDefaultBuy(CriptoActivity act,User user, boolean userCreatedActivity){
        if(act.getActivityType().equals("sell") && userCreatedActivity){
            return new UserTransactionDto(initTransactionHour,user.getName(),user.getLastName(),act.getCriptoName(),
                    act.getValueCripto(),act.getAmountInArs(),act.getNominals(),user.getTransactions().size(),
                    user.getReputation(),user.getCvu());
        }
        else if(act.getActivityType().equals("sell") && !userCreatedActivity) {
                return new UserTransactionDto(initTransactionHour, user.getName(), user.getLastName(), act.getCriptoName(),
                        act.getValueCripto(), act.getAmountInArs(), act.getNominals(), user.getTransactions().size(),
                        user.getReputation(), user.getAddrWallet());
            }
            else{
                return new UserTransactionDto(initTransactionHour,user.getName(),user.getLastName(),act.getCriptoName(),
                        act.getValueCripto(),act.getAmountInArs(),act.getNominals(),user.getTransactions().size(),
                        user.getReputation(),user.getAddrWallet());
            }
        }

        


    private boolean isTransactionInProgress(Long iduser){
        return KeyValueSaver.isCompletedTransaction(iduser) !=null;
    }


    public TransactionBooleanResponseDto userIsInTransaction(Long iduser){
        boolean active = isTransactionInProgress(iduser);
        return new TransactionBooleanResponseDto(iduser,active);
    }

    public void cancelTransaction(Long iduser){
        User user =  userRepository.findById(iduser);
        user.discountReputation(20L);
        KeyValueSaver.cancelTransaction(iduser);
        userRepository.save(user);
    }

    public TransactionBooleanResponseDto checkUserCompleteSend(Long selfUserId ,Long userID) throws Exception {
        if(KeyValueSaver.isCompletedTransaction(userID) == null){
            KeyValueSaver.removeData(userID,selfUserId);
            throw new CancelTransactionException("transaction was canceled");
        }
        else {
           boolean active =KeyValueSaver.isCompletedTransaction(userID);
            boolean activeSelf =KeyValueSaver.isCompletedTransaction(selfUserId);
           TransactionBooleanResponseDto resp =new TransactionBooleanResponseDto(userID,active);
            if(active && activeSelf){
                KeyValueSaver.removeData(selfUserId,userID);
            }
            return resp;
        }
    }

    public TransactionBooleanResponseDto notifyStartTransaction(Long id){
        Long idToNegociate= KeyValueSaver.getIdToNegociate(id);
        boolean isActive =isTransactionInProgress(id);
        Long actId = KeyValueSaver.activityUser(id);
        TransactionBooleanResponseDto resp =new TransactionBooleanResponseDto(idToNegociate,isActive,actId);
        return resp;
    }

    public void confirmTransaction(Long id){
        KeyValueSaver.completeTransaction(id);
    }

    public void completeTransaction(Long idActivity ,Long idUser,Long userIdToNegociate){
        confirmTransaction(idUser);
        CriptoActivity activity = activityRepository.findById(idActivity).get();
       if(usersCompleteTransaction(idUser,userIdToNegociate)) {
           User userToNegociate = userRepository.findById(userIdToNegociate);
           User user = userRepository.findById(idUser);

           LocalDateTime finishTransaction = LocalDateTime.now(ZoneId.of("America/Buenos_Aires"));
          initTransactionHour.plusMinutes(30);
          Long score = generateScore(finishTransaction.isAfter(initTransactionHour));
          CriptoTransaction transaction = new CriptoTransaction(finishTransaction,activity.getActivityType()
                        , activity.getCriptoName(),activity.getNominals(),activity.getValueCripto(),
                        activity.getAmountInArs(), score,user);

            CriptoTransaction transactionUserToNegociate=new CriptoTransaction(finishTransaction,opposite(activity.getActivityType())
                    , activity.getCriptoName(),activity.getNominals(),activity.getValueCripto(),
                    activity.getAmountInArs(), score,userToNegociate);


           confirmTransaction(idUser);
           saveDataTransaction(user,userToNegociate,score,transaction,transactionUserToNegociate);

       }
        }

        private String  opposite(String transactionType){
        if(transactionType.equals("buy")){
            return "sell";
        }
        else{
            return "buy";
        }
        }

        private Long generateScore(boolean condition){
        if(condition){
            return 10L;
        }
        else{
            return 5L;
    }
}

    private boolean usersCompleteTransaction(Long id,Long idToNegociate){
        boolean isCompleteUserTransaction =KeyValueSaver.isCompletedTransaction(id);
        boolean isCompleteUserToNegociate =KeyValueSaver.isCompletedTransaction(idToNegociate);
        return   isCompleteUserTransaction && isCompleteUserToNegociate;
    }

    private void saveDataTransaction(User user,User userToNegociate,Long score,CriptoTransaction transaction,
                                     CriptoTransaction transactionUserToNegociate){
        user.addTransaction(transaction);
        userToNegociate.addTransaction(transactionUserToNegociate);
        user.sumReputation(score);
        userToNegociate.sumReputation(score);
        Long reputationUser =user.calculateReputation();
        Long reputationUserToNegociate = user.calculateReputation();
        user.setReputation(reputationUser);
        userToNegociate.setReputation(reputationUserToNegociate);
        userRepository.save(user);
        userRepository.save(userToNegociate);

    }

    public void clearDatabase(){
        criptoService.deleteAllInBatch();
    }
}

