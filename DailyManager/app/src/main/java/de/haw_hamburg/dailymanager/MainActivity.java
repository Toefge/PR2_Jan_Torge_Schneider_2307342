package de.haw_hamburg.dailymanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Für die Übergabe von Werten zwischen den Activitys (Intent)
    public static final  String YEAR = "YEAR";
    public static final  String MONTH = "MONTH";
    public static final  String DAY = "DAY";
    public static final  String DATE = "Date";

    //Calender Objekt erstellen.
    private Calendar calendar = Calendar.getInstance();
    private int year;
    private int month;
    private int day;

    private ReadService readService;
    private WriterService writerService;
    private Object obj;

    private HashMap<String,Event> map = new HashMap<>();
    private TextView textView;
    private long date;
    private String key;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.Entry);

        final CalendarView calendarView = findViewById(R.id.calendarView);
        //Log.i("Ausgabe", "Year, Month, Day: "+year+" "+month+" "+day);


        //Übergabeparameter auslesen
        Intent intent = getIntent();

        //Nur wenn gerade ein Event erstellt wurde und Daten überben wurden, wird das Objekt mit dem Event Objekt initialisiert.
        if(intent.hasExtra(DATE)){
            //Das zweite ist der default Wert
            date = intent.getLongExtra(DATE, calendar.getTimeInMillis());
            calendarView.setDate(date);
            calendar.setTimeInMillis(date);
        }else{
            /*
            Die Daten year, month und day werden an die Add_Entry_Activity übergeben. Diese werden gesetzt, wenn im Calender ein Tag angeklickt wird.
            Damit auch die korrekten Daten übergeben werden können, wenn kein expliziter Tag im Kalender ausgweählt wurde,
            werden zu Beginn die aktuellen Daten in den Variablen eingetragen.
            */
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            date = calendar.getTimeInMillis();
            Log.i("Ausgabe", "Einmalig "+year+" "+(month)+" "+day);
        }

        obj = readService.readList(MainActivity.this);
        //Wenn das Objekt nicht richtig Übergeben wurde oder die App gerade erst gestartet wurde, ist das obj nicht initialisiert und es soll im Log darauf aufmerksam gemacht werden.
        if(obj==null){
            Log.i("Ausgabe", "Kein Objekt gefunden.");
        }else if(obj instanceof HashMap){

            map=(HashMap) obj;
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            key= "Key:"+year+month+day;
            if(map.containsKey(key)){
                textView.setText(map.get(key).getEventName()+", "+map.get(key).getHour()+":"+map.get(key).getMin()+"Uhr, "+map.get(key).getLocation()+", "+map.get(key).getNote());
            }
            //Log.i("Ausgabe", "Größe der Liste "+map.size());
        }

        /*
        else{

            map = new HashMap<>();
             /*
            ULTRA WICHTIG!!!!!!
            Sollte die App beim ersten Start abstürzen, wenn man direkt versucht  das Datum zu wechseln, dann liegt das daran, dass map noch nicht initialisiert wurde.
            Das kann daran liegen, dass bisher Event Elemente abgespeichert wurden. Also greift die If Abfrage "obj instanceof HashMap" nicht.
            Mit diesem else sollte das Problem behoben werden. Ansonsten kann man mit nachfolgendem Code einmalig eine HashMap abspeichern.
            //writerService.writeObject(MainActivity.this, map);
            Allerdings dar der Code nur einmalig ausgeführt werden und sollte danach wieder auskommentiert oder gelöscht werden.
            Ansonsten würde man unse HashMap immer mit einer leeren überschreiben.

        }
*/
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Want to add a new entry", Toast.LENGTH_SHORT).show();

                //Übergib den aktuellen Tag an die Activity
                Intent intent = new Intent(MainActivity.this, Add_Entry_Activity.class);
                intent.putExtra(YEAR, year);
                intent.putExtra(MONTH, month);
                intent.putExtra(DAY, day);

                startActivity(intent);
            }
        });

        calendarView.setOnDateChangeListener((new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int jahr, int monat, int tag) {

                //ACHTUNG: Diese Methode gibt den Monat von 0-11 zurück!
                year = jahr;
                month = monat;
                day = tag;
                Log.i("Ausgabe", "Main Key:"+year+month+day);
                calendar.set(year,month,day);
                key = "Key:"+year+month+day;

                //Super schlechte Dukumentation, da i, i1 und i2 das angewählte Jahr, Monat und Tag sind.
                //Das ging aus den alten Parametern nicht hervor. Außerdem ist das Objekt calenderView2 das gleiche wie calenderView, auch wenn sich der angewählte Tag verändert hat.
                //Mit folgendem Code, erhält man nur wieder das heutige Datum: datum.setTime(calendarView2.getDate());
                Toast.makeText(MainActivity.this, "Gewählter Tag: "+tag, Toast.LENGTH_SHORT).show();

                if(map.containsKey(key)){
                    textView.setText(map.get(key).getEventName()+", "+map.get(key).getHour()+":"+map.get(key).getMin()+"Uhr, "+map.get(key).getLocation()+", "+map.get(key).getNote());
                }else{
                    textView.setText("");
                }
            }
        }));
    }
}
