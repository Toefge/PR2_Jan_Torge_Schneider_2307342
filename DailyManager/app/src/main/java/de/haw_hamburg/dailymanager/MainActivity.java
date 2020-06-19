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
    public static final  String EVENT = "Event";

    //Calender Objekt erstellen.
    private Calendar calendar = Calendar.getInstance();;
    private int year;
    private int month;
    private int day;

    private ReadService readService;
    private WriterService writerService;
    private Object obj;

    private HashMap<Integer,Event> map = new HashMap<>();
    private TextView textView;
    private long buffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.Entry);

        CalendarView calendarView = findViewById(R.id.calendarView);
        //Log.i("Ausgabe", "Year, Month, Day: "+year+" "+month+" "+day);

        //Übergabeparameter auslesen
        Intent intent = getIntent();

        //Nur wenn gerade ein Event erstellt wurde und Daten überben wurden, wird das Objekt mit dem Event Objekt initialisiert.
        if(intent.hasExtra(EVENT)){
            //Das zweite ist der default Wert
            buffer = intent.getLongExtra(EVENT, calendarView.getDate());
            calendarView.setDate(buffer);
        }
        if(intent.hasExtra(DAY)){
            //Das zweite ist der default Wert
            day = intent.getIntExtra(DAY, calendar.get(Calendar.DAY_OF_MONTH));
        }else{
            /*
            Die Daten year, month und day werden an die Add_Entry_Activity übergeben. Diese werden gesetzt, wenn im Calender ein Tag angeklickt wird.
            Damit auch die korrekten Daten übergeben werden können, wenn kein expliziter Tag im Kalender ausgweählt wurde,
            werden zu Beginn die aktuellen Daten in den Variablen eingetragen.
            */
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH)+1;
            day = calendar.get(Calendar.DAY_OF_MONTH) ;
        }

        obj = readService.readObject(MainActivity.this);
        //Wenn das Objekt nicht richtig Übergeben wurde oder die App gerade erst gestartet wurde, ist das obj nicht initialisiert und es soll im Log darauf aufmerksam gemacht werden.
        if(obj==null){
            Log.i("Ausgabe", "Kein Objekt gefunden.");
        }else if(obj instanceof HashMap){
            //Log.i("Ausgabe", "Tag"+((Event) obj).getDay());
            //CalenderView auf den aktuellen Tag setzte. setDate benötigt ein Long Wert. Dieser kann mit dem Calender Objekt (getStartTime()) aus dem Event Objekt mit der Methode getTimeInMillis() abgerufen werden.
            map=(HashMap) obj;
            if(map.containsKey(day)){
                textView.setText(map.get(day).getEventName()+" "+map.get(day).getHour()+":"+map.get(day).getMin()+" "+map.get(day).getLocation()+" "+map.get(day).getNote());
            }
        }else{
            map = new HashMap<>();
             /*
            ULTRA WICHTIG!!!!!!
            Sollte die App beim ersten Start abstürzen, wenn man direkt versucht  das Datum zu wechseln, dann liegt das daran, dass map noch nicht initialisiert wurde.
            Dies liegt daran, dass noch kein Objekt von Typ HashMap bzw. keine Datei Data.bin existiert. Ich denke mal mit diesem else sollte das Problem gelöst sein.
            Ist dies nicht der Fall, muss der nachfolde Code auskommentiert werden und über das Einlesen mit dem readService plaziert werden.
            Dadurch wird einmalig eine Datei/Objekt angelegt. Anschließend löscht man diese Zeile Code oder kommentiert wie wieder.
            Denn ansonsten würde man bei jedem Aufruf das Objekt mit einer leeren HashMap überschreiben, was ja nicht gewollt ist :D.
            Generell müsste ich eigentlich anders abfragen, z.B. ob das Objekt bzw. die Data.bin Datei bereits existiert.
             */
            //writerService.writeObject(MainActivity.this, map);
        }

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
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int jahr, int monat, int tag) {

                //ACHTUNG: Diese Methode gibt den Monat von 0-11 zurück!
                year = jahr;
                month = monat+1;
                day = tag;

                //Super schlechte Dukumentation, da i, i1 und i2 das angewählte Jahr, Monat und Tag sind.
                //Das ging aus den alten Parametern nicht hervor. Außerdem ist das Objekt calenderView2 das gleiche wie calenderView, auch wenn sich der angewählte Tag verändert hat.
                //Mit folgendem Code, erhält man nur wieder das heutige Datum: datum.setTime(calendarView2.getDate());
                Toast.makeText(MainActivity.this, "Gewählter Tag: "+tag, Toast.LENGTH_SHORT).show();

                if(map.containsKey(tag)){
                    textView.setText(map.get(tag).getEventName()+" "+map.get(tag).getHour()+":"+map.get(tag).getMin()+" "+map.get(tag).getLocation()+" "+map.get(tag).getNote());
                }else{
                    textView.setText("");
                }
            }
        }));
    }
}
