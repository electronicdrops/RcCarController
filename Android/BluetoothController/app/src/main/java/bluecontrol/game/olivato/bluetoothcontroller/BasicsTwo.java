package bluecontrol.game.olivato.bluetoothcontroller;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Set;

/**
 * Created by olivato on 28/04/16.
 */
public class BasicsTwo extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basics2);
        if (ToothReadWrite.statusTooth() != true) {
            liga_bluetooth();
        }
        final ImageView left = (ImageView) findViewById(R.id.left);
        final ImageView right = (ImageView) findViewById(R.id.right);
        final ImageView up = (ImageView) findViewById(R.id.up);
        final ImageView down = (ImageView) findViewById(R.id.down);
        final ImageView upright = (ImageView)findViewById(R.id.upright);
        final ImageView upleft = (ImageView) findViewById(R.id.upleft);
        final ImageView downright = (ImageView)findViewById(R.id.downright);
        final ImageView downleft = (ImageView)findViewById(R.id.downleft);
        final ImageView para = (ImageView)findViewById(R.id.stop);
        final ImageView buzina = (ImageView) findViewById(R.id.buzina);
        final ImageView farol = (ImageView) findViewById(R.id.FarolBaixo);

        para.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        para.setImageAlpha(20);
                        ToothReadWrite.WriteBuffer((byte) 0);



                        break;
                    case MotionEvent.ACTION_UP:
                        para.setImageAlpha(255);
                        ToothReadWrite.WriteBuffer((byte)0);

                        break;
                }
                return false;
            }
        });
        buzina.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        buzina.setImageAlpha(20);
                        ToothReadWrite.WriteBuffer((byte) 32);



                        break;
                    case MotionEvent.ACTION_UP:
                        buzina.setImageAlpha(255);
                        ToothReadWrite.WriteBuffer((byte)32);

                        break;
                }
                return false;
            }
        });
        farol.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        farol.setImageAlpha(100);
                        ToothReadWrite.WriteBuffer((byte) 16);



                        break;
                    case MotionEvent.ACTION_UP:
                        farol.setImageAlpha(255);


                        break;
                }
                return false;
            }
        });

        left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        left.setImageAlpha(100);
                        ToothReadWrite.WriteBuffer((byte)4);



                        break;
                    case MotionEvent.ACTION_UP:
                        left.setImageAlpha(255);

                        Log.v("MANDA:", "0");
                        ToothReadWrite.WriteBuffer((byte)0);

                        break;
                }

                return false;
            }
        });
        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        right.setImageAlpha(100);
                        ToothReadWrite.WriteBuffer((byte)8);


                        break;
                    case MotionEvent.ACTION_UP:
                        right.setImageAlpha(255);
                        ToothReadWrite.WriteBuffer((byte)0);

                        break;
                }
                return false;
            }
        });
        up.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        up.setImageAlpha(100);
                        ToothReadWrite.WriteBuffer((byte)1);


                        break;
                    case MotionEvent.ACTION_UP:
                        up.setImageAlpha(255);
                        ToothReadWrite.WriteBuffer((byte)0);


                        break;
                }
                return false;
            }
        });
        down.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        down.setImageAlpha(100);

                        ToothReadWrite.WriteBuffer((byte)2);


                        break;
                    case MotionEvent.ACTION_UP:
                        down.setImageAlpha(255);

                        ToothReadWrite.WriteBuffer((byte)0);


                        break;
                }
                return false;
            }
        });
        downleft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downleft.setImageAlpha(100);

                        ToothReadWrite.WriteBuffer((byte)6);


                        break;
                    case MotionEvent.ACTION_UP:
                        downleft.setImageAlpha(255);

                        ToothReadWrite.WriteBuffer((byte)0);


                        break;
                }
                return false;
            }
        });
        downright.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downright.setImageAlpha(100);

                        ToothReadWrite.WriteBuffer((byte)10);


                        break;
                    case MotionEvent.ACTION_UP:
                        downright.setImageAlpha(255);
                        ToothReadWrite.WriteBuffer((byte)0);


                        break;
                }
                return false;
            }
        });
        upleft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        upleft.setImageAlpha(100);
                        ToothReadWrite.WriteBuffer((byte)5);


                        break;
                    case MotionEvent.ACTION_UP:
                        upleft.setImageAlpha(255);
                        ToothReadWrite.WriteBuffer((byte)0);


                        break;
                }
                return false;
            }
        });
        upright.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        upright.setImageAlpha(100);
                        ToothReadWrite.WriteBuffer((byte)9);


                        break;
                    case MotionEvent.ACTION_UP:
                        upright.setImageAlpha(255);
                        ToothReadWrite.WriteBuffer((byte)0);


                        break;
                }
                return false;
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.conectar:
            {
                if (ToothReadWrite.statusTooth() != true) {
                    liga_bluetooth();
                } else {


                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Dispositivos Pareados");


                    final EditText input = new EditText(this);


                    int i = 0;
                    Set<BluetoothDevice> pareados = ToothReadWrite.Pareados();

                    if (pareados.size() > 0) {
                        for (BluetoothDevice device : pareados) {
                            i++;
                        }
                    }

                    String address[] = new String[i];
                    final String names[] = new String[i];
                    i = 0;
                    if (pareados.size() > 0) {
                        for (BluetoothDevice device : pareados) {
                            names[i] = device.getName() + "#" + device.getAddress();
                            i++;
                        }
                    }
                    builder.setItems(names, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                            String[] mac = names[which].toString().split("#");
                            try {

                                ToothReadWrite.Connect(mac[1].trim());


                            } catch (Exception e) {

                            }

                        }

                    });


                    builder.show();

                }

                break;
            }
            case R.id.disconectar:
            {

                ToothReadWrite.disconnect();
                break;
            }






        }
        return true;
    }

    public void liga_bluetooth() {
        if (!ToothReadWrite.statusTooth()) {
            Intent liga_blu_intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(liga_blu_intent, 1);
        }

    }
    public void desconecta(View view)
    {
        ToothReadWrite.disconnect();

    }
    public void clickerclicker(View view)
    {

    }
    }




