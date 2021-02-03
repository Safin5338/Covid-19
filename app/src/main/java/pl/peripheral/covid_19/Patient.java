package pl.peripheral.covid_19;


import com.google.gson.annotations.SerializedName;

public class Patient {

    private int id;

    @SerializedName("pesel")
    private String sPesel;

    @SerializedName("name")
    private String sName;

    @SerializedName("surname")
    private String sSurname;

    @SerializedName("testDate")
    private String sTestDate;

    @SerializedName("resultDate")
    private String sResultDate;

    @SerializedName("testResult")
    private String sTestResult;

    public int getId() { return id; }

    public String getsPesel() { return sPesel; }

    public String getsName() { return sName; }

    public String getsSurname() { return sSurname; }

    public String getsTestDate() { return sTestDate; }

    public String getsResultDate() { return sResultDate; }

    public String getsTestResult() { return sTestResult; }
}
