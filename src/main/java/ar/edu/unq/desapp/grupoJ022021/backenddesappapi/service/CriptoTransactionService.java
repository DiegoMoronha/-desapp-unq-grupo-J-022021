package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.service;

import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.exceptions.ActivityNotFound;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.exceptions.CancelTransactionException;
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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
@Transactional
public class CriptoTransactionService {

    @Autowired
    private CriptoTransactionRepository criptoService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CriptoActivityRepository activityRepository;


    public UserTransactionDto startTransaction(Long id,Long userIdToNegociate, Long actId){
        KeyValueSaver.putTransactionIdwithUsers(id,userIdToNegociate);
        LocalDateTime hour = KeyValueSaver.dateTransaction(id,userIdToNegociate);
        CriptoActivity act=checkExistActivity(actId);
        KeyValueSaver.initTransaction(id,userIdToNegociate);
        KeyValueSaver.markActivityId(userIdToNegociate,actId);
        User user = userRepository.findById(userIdToNegociate);
        boolean userCreatedActivity = activityRepository.findByIdAndUser(actId,userIdToNegociate)!=null;
        if(act.getActivityType().equals("buy") && userCreatedActivity) {

            return new UserTransactionDto(hour,user.getName(),user.getLastName(),act.getCriptoName(),
                    act.getValueCripto(),act.getAmountInArs(),act.getNominals(),user.getTransactions().size(),
                    user.getReputation(),user.getCvu());
        }
        else{
            return checkSellOptionOrDefaultBuy(hour,act,user,userCreatedActivity);
        }
    }

    private CriptoActivity checkExistActivity(Long actId){
        Optional<CriptoActivity> activity = activityRepository.findById(actId);
        if(activity.isPresent()){
            return activity.get();
        }
        else{
            throw new ActivityNotFound("Activity not found");
        }
    }

    private UserTransactionDto checkSellOptionOrDefaultBuy(LocalDateTime hour,CriptoActivity act,User user, boolean userCreatedActivity){
        if(act.getActivityType().equals("sell") && userCreatedActivity){
            return new UserTransactionDto(hour,user.getName(),user.getLastName(),act.getCriptoName(),
                    act.getValueCripto(),act.getAmountInArs(),act.getNominals(),user.getTransactions().size(),
                    user.getReputation(),user.getCvu());
        }
        else if(act.getActivityType().equals("sell") && !userCreatedActivity) {
                return new UserTransactionDto(hour, user.getName(), user.getLastName(), act.getCriptoName(),
                        act.getValueCripto(), act.getAmountInArs(), act.getNominals(), user.getTransactions().size(),
                        user.getReputation(), user.getAddrWallet());
            }
            else{
                return new UserTransactionDto(hour,user.getName(),user.getLastName(),act.getCriptoName(),
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

    public TransactionBooleanResponseDto checkUserCompleteSend(Long selfUserId ,Long userID) throws CancelTransactionException {
        if(KeyValueSaver.isCompletedTransaction(userID) == null){
            KeyValueSaver.removeData(userID,selfUserId);
            throw new CancelTransactionException("transaction was canceled");
        }
        else {
           Boolean active =KeyValueSaver.isCompletedTransaction(userID);
           Boolean activeSelf =KeyValueSaver.isCompletedTransaction(selfUserId);
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
        LocalDateTime hour = KeyValueSaver.dateTransaction(idUser,userIdToNegociate);
        CriptoActivity activity = checkExistActivity(idActivity);
       if(usersCompleteTransaction(idUser,userIdToNegociate)) {
           User userToNegociate = userRepository.findById(userIdToNegociate);
           User user = userRepository.findById(idUser);

           LocalDateTime finishTransaction = LocalDateTime.now(ZoneId.of("America/Buenos_Aires"));
          Long score = generateScore(hour.plusMinutes(30).isAfter(finishTransaction));
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

