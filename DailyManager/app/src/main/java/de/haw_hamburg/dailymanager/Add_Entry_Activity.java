package de.haw_hamburg.dailymanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.HashMap;

public class Add_Entry_Activity extends AppCompatActivity {

    //Für die Übergabe von Werten zwischen den Activitys (Intent)
    public static final  String YEAR = "YEAR";
    public static final  String MONTH = "MONTH";
    public static final  String DAY = "DAY";
    public static final  String DATE = "Date";

    /*
    Falls die Zeit als String eingegeben werden soll
    private String string;
    private String[] hoursMinutes;
    */

    private int year;
    private int month;
    private int day;
    private int hours = 0;
    private int minutes = 0;

    private TextView choosedDate;
    private TextView choosedTime;
    private TimePickerDialog.OnTimeSetListener timeSetListener;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    private WriterService writerService = new WriterService();
    private ReadService readService;
    private Object obj;
    private HashMap<String,Event> map;
    private long date;
    private Calendar calendar = Calendar.getInstance();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry_);

        readService = new ReadService();
        obj = readService.readList(Add_Entry_Activity.this);
        if(obj==null){
            Log.i("Ausgabe", "Kein Objekt gefunden.");
        }else if(obj instanceof HashMap){
            map=(HashMap) obj;
        }

        Intent intent = getIntent();

        //Abfangen, wenn 0 übertragen wird?

        if(intent.hasExtra(YEAR)){
            year = intent.getIntExtra(YEAR,0);
        }else{
            Log.i("Ausgabe", "Fehler Jahr! ");
        }
        if(intent.hasExtra(MONTH)){
            month = intent.getIntExtra(MONTH,0)+1;
        }else{
            Log.i("Ausgabe", "Fehler Monat! ");
        }
        if(intent.hasExtra(DAY)){
            day = intent.getIntExtra(DAY,0);
        }else{
            Log.i("Ausgabe", "Fehler Tag! ");
        }

        choosedDate = findViewById(R.id.Date);
        choosedDate.setText(year+"."+month+"."+day);

        choosedTime = findViewById(R.id.Time);
        choosedTime.setText("0"+hours+":"+"0"+minutes);

        choosedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Pop up Eingabe für das Datum. Mit year, month und day wird die Anzeige direkt auf das gewählte Datum eingestellt.
                DatePickerDialog dialog = new DatePickerDialog(Add_Entry_Activity.this, android.R.style.Theme_Black, dateSetListener, year, month, day);
                //Damit man den Text im Hintergrund nicht mehr sieht:
                dialog.getWindow().setBackgroundDrawable((new ColorDrawable(Color.TRANSPARENT)));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int jahr, int monat, int tag) {
                //Wenn das Datum mit dem DatePicker bearbeitet wird, werden die Daten hier abgespeichert. ACHTUNG: Diese Methode gibt den Monat von 1-12 zurück!
                choosedDate.setText(jahr+"."+monat+"."+tag);
                year = jahr;
                month = monat-1;
                day = tag;
                //Ändert die Textview in das neue Datum

            }
        };

        //TimePicker wie DatePicker, nur das ich das default Theme meine Gerätes verwende.
        choosedTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog dialog = new TimePickerDialog(Add_Entry_Activity.this, android.R.style.Theme_DeviceDefault_DayNight, timeSetListener, 12, 00, true);
                //dialog.getWindow().setBackgroundDrawable((new ColorDrawable(Color.TRANSPARENT)));
                dialog.show();
            }
        });

        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int stunden, int minuten) {
                hours = stunden;
                minutes = minuten;
                choosedTime.setText(hours+":"+minutes);
            }
        };

        //Code von Stephie
        Spinner spinner = findViewById(R.id.spinner);
        String[] arraySpinner = new String[] {
                "keine", "15 min vorher", "30 min vorher", "1 Stunde vorher",
                "morgens um 08:00", "1 Tag vorher"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Variablen zuweisen, in denen die Werte aus dem Formular abgespeichert werden (Zuweisen über die IDs).
                EditText name = findViewById(R.id.Name);
                EditText location = findViewById(R.id.Location);
                EditText note = findViewById(R.id.Note);
                Spinner notification = findViewById(R.id.spinner);

                /*
                Folgender Code ist für eine Eingabe der Zeit (oder Datum) über einen String.
                Es muss das ":" Zeichen aus der Eingabe der Zeit herausgefilter werden.
                string = time.getText().toString();
                hoursMinutes= string.split(":");
                 */

                //Event Objekt erstellen
                Event event = new Event(year, month-1, day, hours, minutes, name.getText().toString());

                /*
                Log.i("Ausgabe", "Jahr: "+year);
                Log.i("Ausgabe", "Monat: "+month);
                Log.i("Ausgabe", "Tag: "+day);
                Log.i("Ausgabe", "Stunden: "+hours);
                Log.i("Ausgabe", "Minuten: "+minutes);
                Log.i("Ausgabe", name.getText().toString());
                Log.i("Ausgabe", location.getText().toString());
                Log.i("Ausgabe", notification.getSelectedItem().toString());

                Für eine Zeiteingabe als String.
                Log.i("Ausgabe", (hoursMinutes[0]));
                Log.i("Ausgabe", (hoursMinutes[1]));
                */

                //Werte an Event Objekt übergeben
                event.setLocation(location.getText().toString());
                event.setNote(note.getText().toString());
                event.setRemindOption(notification.getSelectedItem().toString());

                Log.i("Ausgabe", event.toString());

                calendar.set(year,month-1,day);
                date = calendar.getTimeInMillis();
                map.put("Key:"+year+(month-1)+day, event);
                Log.i("Ausgabe", "Add entry Key:"+year+(month-1)+day);
                //Schreibt das Event Objekt weg. ACHTUNG: Event Objekt muss Seriallizable implementiert haben!!!
                writerService.writeObject(Add_Entry_Activity.this, map);

                //Zurück zur Main, mit der Info, dass ein Event Objekt erstellt wurde. Nur dann soll die Main dieses aus dem Speicher abrufen.
                Intent intent = new Intent(Add_Entry_Activity.this, MainActivity.class);
                intent.putExtra(DATE, date);
                startActivity(intent);
            }
        });
    }
}