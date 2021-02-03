package pl.peripheral.covid_19;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Button bCheck;
    EditText ePesel;
    TextView sCallback;
    TextView sWrongPesel;
    TextView sName;
    TextView sSurname;
    TextView sDate;
    TextView sResultDate;
    TextView sResult;
    public static final String baseAddr = "http://172.19.224.1:8080/covid/"; //ścieżka do localhosta

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bCheck = (Button) findViewById(R.id.bCheck);
        sCallback = (TextView) findViewById(R.id.sCallback);
        sWrongPesel = (TextView) findViewById(R.id.sWrongPesel);
        sName = (TextView) findViewById(R.id.sName);
        sSurname = (TextView) findViewById(R.id.sSurname);
        sDate = (TextView) findViewById(R.id.sDate);
        sResultDate = (TextView) findViewById(R.id.sResultDate);
        sResult = (TextView) findViewById(R.id.sResult);
        ePesel = (EditText) findViewById(R.id.ePesel);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseAddr)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CovidAPI covidApi = retrofit.create(CovidAPI.class);

        bCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uInput = ePesel.getText().toString();

                Call<List<Patient>> call = covidApi.getPatients();

                call.enqueue(new Callback<List<Patient>>() {
                    @Override
                    public void onResponse(Call<List<Patient>> call, Response<List<Patient>> response) {
                        if(!response.isSuccessful()){
                            sCallback.setText("Kod błędu:" + response.code());
                            return;
                        }
                        List<Patient> oPatients =response.body();
                        for(Patient patient : oPatients) {

                            String pPesel = patient.getsPesel();

                            String fDate = patient.getsResultDate();
                            String fResult = patient.getsTestResult();

                            //sCallback.append(uInput + "\n");
                            //sCallback.append(pPesel + "\n");
                            sWrongPesel.setVisibility(View.INVISIBLE);

                            if (pPesel.equals(uInput)) {
                                sName.setText("Imię: " + patient.getsName());
                                sSurname.setText("Nazwisko: " + patient.getsSurname());
                                sDate.setText("Data wykonania testu: " + patient.getsTestDate());

                                if (fDate!=null && fResult!=null) {
                                    if (fResult.equals("Pozytywny")) {
                                        sResult.setTextColor(Color.parseColor("#ff1010"));
                                    }
                                    else if(fResult.equals("Negatywny")){
                                        sResult.setTextColor(Color.parseColor("#66CC00"));
                                    }
                                    sResultDate.setText("Data uzyskania wyników: " + fDate);
                                    sResult.setText("Wynik: " + fResult + "!");
                                } else {
                                    sResult.setTextColor(Color.parseColor("#A19999"));
                                    sResult.setText("Prosimy o cierpliwość, wyniki będą gotowe wkrótce");
                                    sWrongPesel.setVisibility(View.INVISIBLE);
                                }

                                break;
                            } else {
                                sWrongPesel.setVisibility(View.VISIBLE);
                                sName.setText("");
                                sSurname.setText("");
                                sDate.setText("");
                                sResultDate.setText("");
                                sResult.setText("");
                            }
                            //sCallback.setText();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Patient>> call, Throwable t) {
                        sCallback.setText(t.getMessage());
                    }
                });
            }
        });
    }
}