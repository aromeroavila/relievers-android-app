package arao.relieversapp.net;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Maintenance {

    @GET("/ban")
    Call<Void> report(@Query("room") String param1);

}
