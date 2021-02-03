package pl.peripheral.covid_19;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CovidAPI {
    @GET("list.json") //względny adres listy pacjentów
    Call<List<Patient>> getPatients();
}
