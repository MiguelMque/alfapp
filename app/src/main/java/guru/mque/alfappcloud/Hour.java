package guru.mque.alfappcloud;

import android.os.Parcelable;

import com.google.firebase.Timestamp;

public class Hour {


    private String date, state, studentAuth, description;
    private int number;


    public Hour() {

    }

    public Hour(String date, int number, String state) {
        this.date = date;
        this.number = number;
        this.state = state;
    }

    public String getStudentAuth() {
        return studentAuth;
    }

    public void setStudentAuth(String studentAuth) {
        this.studentAuth = studentAuth;
    }

    public Hour(String date, int number, String state, String studentAuth, String description) {
        this.date = date;
        this.number = number;
        this.state = state;
        this.studentAuth = studentAuth;

    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDate() {
        return date;
    }

}
