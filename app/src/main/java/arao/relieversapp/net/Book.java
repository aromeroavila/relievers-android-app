package arao.relieversapp.net;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Book {

    @GET("/book")
    Call<int[]> book();
}
