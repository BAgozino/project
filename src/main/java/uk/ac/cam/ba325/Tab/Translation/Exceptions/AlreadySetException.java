package uk.ac.cam.ba325.Tab.Translation.Exceptions;

/**
 * Created by root on 31/01/16.
 */
public class AlreadySetException extends Exception {

    String message;

    public AlreadySetException(String message){
        this.message = message;
    }
}
