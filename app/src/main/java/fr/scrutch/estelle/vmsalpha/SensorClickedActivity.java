package fr.scrutch.estelle.vmsalpha;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.hardware.SensorEvent;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class SensorClickedActivity extends AppCompatActivity implements SensorEventListener {

    private int count = 0;
    private SensorManager aSensorManager;
//    private SensorManager bSensorManager;
    private Sensor aSensor;
    private long time1,time2,time3;
//    private int[] index;
    private SensorManager mSensorManager;
    private List<Sensor> sensorClicked;
    private ArrayList<Integer> index = new ArrayList<Integer>();

    public SensorClickedActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.acceleration);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);  //get the list of sensors
//        bSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorClicked = mSensorManager.getSensorList(Sensor.TYPE_ALL);

        Intent intent = getIntent();
        //Get the int[] from the putExtra in the MainActivity.java
        index = intent.getIntegerArrayListExtra("index");
        //We need the sensor manager to get the list of sensors in which the index is related to
        aSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
//        bSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

//        textview.setText(sensorClicked.get(sensorType[0]).toString());    //Test: return the right sensor ;)
        // magnetic then accelero if clicked accelero then magnetic
        for(int i = 0; i < index.size(); i++) {
            System.out.println(sensorClicked.get(index.get(i)).toString());
        }
        System.out.println("##############"+sensorClicked.get(index.get(0)).toString()+"erfed"+sensorClicked.get(index.get(1)).toString());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sensors_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
        //TEST: the accelero works
//            getAccelerometer(event);
        }
        //TEST: the magnetic
        if(event.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD){
//            getMagnetic(event);
        }
        getSensorType(event);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    protected void onResume() {
        super.onResume();
        // register this class as a listener for the orientation and
        // accelerometer sensors LISTENER
        aSensorManager.registerListener(this,aSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_FASTEST);
//        bSensorManager.registerListener(this,aSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),SensorManager.SENSOR_DELAY_FASTEST);
//        bSensorManager.registerListener(this,aSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),SensorManager.SENSOR_DELAY_FASTEST);
        //en avoir plein => correspond au nbr de sensors selected
    }

    @Override
    protected void onPause() {
        // unregister listener
        super.onPause();
        aSensorManager.unregisterListener(this);
//        bSensorManager.unregisterListener(this);

    }


    ////////////////////////Other sensors:
    private void getAccelerometer(SensorEvent event) {
        count = (count+1)%300;  //to print just 1 value/300
        //We have to get the textView of the layout
//        TextView textView =  (TextView) findViewById(R.id.textView);
//        TextView textView2 =  (TextView) findViewById(R.id.textView2);
//        TextView textView3 =  (TextView) findViewById(R.id.textView3);
        TextView textView4 = (TextView) findViewById(R.id.textView4);

        if(count==0)
            time1 = System.nanoTime();     //time before getting values
//            time1 = sensorClicked.get(sensorType[0]).getMaxDelay(); //minDelay = 10000 ms

        float[] values = event.values;
        // Movement, get values
        float x = values[0];
        float y = values[1];
        float z = values[2];

        if(count==0)
            time2 = System.nanoTime();


//        time2 = event.timestamp;      //time after getting values
//            //Toast.makeText(this, "x:"+x+"\n"+"y:"+y+"\n"+"z:"+z, Toast.LENGTH_SHORT).show();
//            //R.id.textView(Long.toString(time1));
//            //the force of gravity must be eliminated => problem of the direction of the phone

//        textView.setText("t1 = "+Long.toString(time1)+" ns");
////        setContentView(textViewTime2);
//        if(count==0)
//            time3 = System.nanoTime();
//        time3 = event.timestamp;
        //time to print, printed on screen:
//        textView2.setText("t2 = "+Long.toString(time2)+" ns");
//        textView3.setText("t3 = "+Long.toString(time3)+" ns");
        textView4.setText("Accelerometer" + "\n" + "x:" + x + " m.s²" + "\n" + "y:" + y + " m.s²" + "\n" + "z:" + z + " m.s²");

    }


    private void getMagnetic(SensorEvent event) {
        count = (count+1)%300;
//        TextView textView =  (TextView) findViewById(R.id.textView);
//        TextView textView2 =  (TextView) findViewById(R.id.textView2);
        TextView textView3 =  (TextView) findViewById(R.id.textView3);
//        TextView textView4 = (TextView) findViewById(R.id.textView4);

        if(count==0)
            time1 = System.nanoTime();     //time before getting values

        float[] values = event.values;
        //micro-Tesla µT ou uT
        float x = values[0];
        float y = values[1];
        float z = values[2];
        //missing y and z

//        if(count==0)
//            time2 = System.nanoTime();

//        textView.setText(Long.toString(time1));
//        if(count==0)
//            time3 = System.nanoTime();
//
//        textView2.setText(Long.toString(time2));
//        textView3.setText(Long.toString(time3));
        textView3.setText("Ambiant magnetic field : " + x + " µT" +"\n"+ y + " µT" +"\n"+ z + " µT");


    }

    protected  void getSensorType(SensorEvent event){
        TextView textView =  (TextView) findViewById(R.id.textView);
        TextView textView2 =  (TextView) findViewById(R.id.textView2);
        TextView textView3 =  (TextView) findViewById(R.id.textView3);
//        TextView textView4 = (TextView) findViewById(R.id.textView4);
        textView.setText(sensorClicked.get(index.get(0)).toString());
        textView2.setText(sensorClicked.get(index.get(1)).toString());
        textView3.setText(sensorClicked.get(index.get(2)).toString());
    }


    /////////////////////// End test
}
