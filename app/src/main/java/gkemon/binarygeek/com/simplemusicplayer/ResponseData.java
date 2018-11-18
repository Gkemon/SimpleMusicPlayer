
package gkemon.binarygeek.com.simplemusicplayer;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class ResponseData {

    @SerializedName("message")
    @Expose
    public String message;


    @SerializedName("has_error")
    @Expose
    public Boolean hasError;


    public ResponseData(){

    }
    public ResponseData(String message,Boolean hasError){
        super();
        this.hasError=hasError;
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getHasError() {
        return hasError;
    }

    public void setHasError(Boolean hasError) {
        this.hasError = hasError;
    }



}
