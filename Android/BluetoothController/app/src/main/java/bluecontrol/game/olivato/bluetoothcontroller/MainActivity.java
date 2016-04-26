package bluecontrol.game.olivato.bluetoothcontroller;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import java.io.IOException;
import java.util.Set;
/*Criado por: Gabriel Olivato || Agradecimentos especiais para: Danilo Queiroz Barbosa , Lucas Baquião , Marcelo Almeida , Mr. Anderson , Juliano Mechanics , Jão and others...*/
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ToothReadWrite.statusTooth() != true) {
            liga_bluetooth();
        } else {
            pegaPareados();
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


        para.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        para.setImageAlpha(20);


                        break;
                    case MotionEvent.ACTION_UP:

                        para.setImageAlpha(255);
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


                        break;
                    case MotionEvent.ACTION_UP:

                        left.setImageAlpha(255);
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

                        break;
                    case MotionEvent.ACTION_UP:
                        right.setImageAlpha(255);
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

                        break;
                    case MotionEvent.ACTION_UP:
                        up.setImageAlpha(255);

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
                        break;
                    case MotionEvent.ACTION_UP:
                        down.setImageAlpha(255);
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

                        break;
                    case MotionEvent.ACTION_UP:
                        downleft.setImageAlpha(255);

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

                        break;
                    case MotionEvent.ACTION_UP:
                        downright.setImageAlpha(255);

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

                        break;
                    case MotionEvent.ACTION_UP:
                        upleft.setImageAlpha(255);

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

                        break;
                    case MotionEvent.ACTION_UP:
                        upright.setImageAlpha(255);

                        break;
                }
                return false;
            }
        });

    }

    public void pegaPareados() {
        int i = 0;
        Set<BluetoothDevice> pareados = ToothReadWrite.Pareados();

        if (pareados.size() > 0) {
            for (BluetoothDevice device : pareados) {
                i++;
            }
        }

        String address[] = new String[i];
        String names[] = new String[i];
        i = 0;
        if (pareados.size() > 0) {
            for (BluetoothDevice device : pareados) {
                names[i] = device.getName() + "#" + device.getAddress();
                i++;
            }
        }

        Spinner lista_de_dispositivos = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, names);
        lista_de_dispositivos.setAdapter(spinnerArrayAdapter);

    }
    public void conectar(View view) throws IOException {
        ImageView botao = (ImageView)findViewById(R.id.connect);
        Spinner lista_de_dispositivos = (Spinner) findViewById(R.id.spinner);
        String[] mac = lista_de_dispositivos.getSelectedItem().toString().split("#");

        botao.setImageAlpha(20);
        try {

            ToothReadWrite.Connect(mac[1].trim());


        } catch (Exception e) {

        }


    }

    public void liga_bluetooth() {
        if (!ToothReadWrite.statusTooth()) {
            Intent liga_blu_intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(liga_blu_intent, 1);
        }

    }

    public void para(View view)
    {
        ToothReadWrite.WriteBuffer("0");
    }
    public void parafrente(View view){ToothReadWrite.WriteBuffer("1");}
    public void paratras(View view)
    {
        ToothReadWrite.WriteBuffer("2");
    }
    public void paraleft(View view)
    {
        ToothReadWrite.WriteBuffer("4");
    }
    public void pararight(View view)
    {
        ToothReadWrite.WriteBuffer("8");
    }
    public void upnright(View view)
    {
        ToothReadWrite.WriteBuffer("9");
    }
    public void upnleft(View view)
    {
        ToothReadWrite.WriteBuffer("5");
    }
    public void downnright(View view)
    {
        ToothReadWrite.WriteBuffer("10");
    }
    public void downnleft(View view)
    {
        ToothReadWrite.WriteBuffer("6");
    }
    public void disconectar(View view)
    {
        ToothReadWrite.disconnect();
    }

}
