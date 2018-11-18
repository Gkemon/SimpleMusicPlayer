package gkemon.binarygeek.com.simplemusicplayer;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIinterface {

    //For uploading profile image
    @Multipart
    @POST("save_song")
    Call<ResponseData> uploadMusicFile(@Part MultipartBody.Part music);

}
