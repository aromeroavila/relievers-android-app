package arao.relieversapp.net;

import java.util.List;

import arao.relieversapp.model.Room;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RoomsStatus {

    @GET("/doors/status")
    Call<List<Room>> rooms();
}
