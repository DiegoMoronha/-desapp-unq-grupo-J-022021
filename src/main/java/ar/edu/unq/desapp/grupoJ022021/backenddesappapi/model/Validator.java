package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model;

import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.exceptions.ValidationException;

import java.util.regex.Pattern;

public class Validator {

    public static boolean greaterThan(Integer cant, String str){
        return cant<=str.length();
    }

    public static boolean smallerThan(Integer cant, String str) {
        return cant>=str.length();

    }

    public static String between(Integer min ,Integer max ,String str, String errorMessage) throws ValidationException {

        if(smallerThan(max,str)&& greaterThan(min,str)){
            return str ;
        }
        else{
            throw new ValidationException(errorMessage);
        }

    }
    public static boolean isEmailValid(String email) {
        final Pattern EMAIL_REGEX = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", Pattern.CASE_INSENSITIVE);
        return EMAIL_REGEX.matcher(email).matches();
    }

    public static String validateEmail(String email,String errorMessage) throws ValidationException {
        if(isEmailValid(email)){
            return email;
        }
        else{
            throw new ValidationException(errorMessage);
        }
    }
}
