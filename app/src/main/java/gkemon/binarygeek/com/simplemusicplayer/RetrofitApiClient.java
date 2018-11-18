
//Copied from my GitHub repo: https://github.com/hasancse91/retrofit-implementation

package gkemon.binarygeek.com.simplemusicplayer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitApiClient {

         public static final String BASE_URL = "http://188.226.161.183/moood/";
         // private static final String BASE_URL = "http://og.techlogicians.com/public/api/";



        private static Retrofit retrofit = null;
 
        private static Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        private RetrofitApiClient() {}
        // So that nobody can create an object with constructor
 
        public static synchronized Retrofit getClient() {
            if (retrofit==null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
            }
            return retrofit;
        }

 
}