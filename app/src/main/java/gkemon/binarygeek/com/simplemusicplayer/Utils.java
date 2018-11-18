package gkemon.binarygeek.com.simplemusicplayer;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Utils {

    public static List<String> getMusicList(Activity activity){
        List<String> musicList = new ArrayList<>();

        //Some audio may be explicitly marked as not being music
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        ContentResolver cr = activity.getContentResolver();

        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,

        };

       Cursor cursor = cr.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                null,
                null);


        while(cursor.moveToNext()) {


            musicList.add(cursor.getString(0) + "||"
                    + cursor.getString(1) + "||"
                    + cursor.getString(2) + "||"
                    + cursor.getString(3) + "||"
                    + cursor.getString(4) + "||"
                    + cursor.getString(5));

            Log.d("GK","File size :"+getFileSize(cursor.getString(3) )+" for "+cursor.getString(3) );
            Log.d("GK","Duration :"+getDuration(cursor.getString(3),activity));
            Log.d("GK","Current :"+getCurrentDuration((cursor.getString(3) ),activity));


            upFileToServer(new File(cursor.getString(3)),activity);

        }
        cursor.close();

        return musicList;
    }


    public static String getFileSize(String path){
        File file=new File(path);

        if(file.exists())Log.d("GK","FILE EXIST");
        else Log.d("GK","FILE NOT EXIST");

        return getformatedFileSize(file.length());
    }

    public static String getDuration(String path , Activity activity){

        Uri uri = Uri.parse(path);
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(activity,uri);
        String durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        int millSecond = Integer.parseInt(durationStr);



        return millSecond+" milli second";

    }

    public
   static void upFileToServer(File musicFile,Activity activity) {
        if (musicFile != null&&musicFile.exists() ) {
            Log.e("GK","FILE IS EXIST");


            APIinterface apiInterface;
            apiInterface = RetrofitApiClient.getClient().create(APIinterface.class);


            RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), musicFile);
            MultipartBody.Part body = MultipartBody.Part.createFormData("song", musicFile.getName(), reqFile);

            Call<ResponseData> call = apiInterface.uploadMusicFile( body);

            call.enqueue(new Callback<ResponseData>() {
                @Override
                public
                void onResponse(Call<ResponseData> call, Response<ResponseData> response) {

                    // Log.d("GK","On success "+response.body().responseStat.msg);
                    Log.e("GK", "on success " );


                }

                @Override
                public
                void onFailure(Call<ResponseData> call, Throwable t) {
                    Log.e("GK", "onFailure: " + t.toString());



                }
            });


        } else {
            Log.d("GK","File is not selected");
            Toast.makeText(activity, "File is not selected",Toast.LENGTH_LONG).show();
        }


    }

    public static String getCurrentDuration(String path, Activity activity){
        MediaPlayer mp = MediaPlayer.create(activity, Uri.parse(path));
         return mp.getCurrentPosition()+" milli secoond of current duration";
    }

    public static String getformatedFileSize(long size) {
        String suffix = null;


        if (size >= 1024) {
            suffix = " Bytes";
            size /= 1024;
            if (size >= 1024) {
                suffix = " MB";
                size /= 1024;
            }
        }
        StringBuilder resultBuffer = new StringBuilder(Long.toString(size));

        int commaOffset = resultBuffer.length() - 3;
        while (commaOffset > 0) {
            resultBuffer.insert(commaOffset, ',');
            commaOffset -= 3;
        }
        if (suffix != null) resultBuffer.append(suffix);
        return resultBuffer.toString();
    }
    


}
