package bluecontrol.game.olivato.bluetoothcontroller;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ArrayAdapter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

/**
 * Created by olivato on 11/03/16.
 * Created for aBox Project 2016
 *
 * Especial thanks to: Bianca C.B. Campos, Marcelo Schmidt, Gabriel Teston, Danilo Queiroz Barbosa.
 *
 */
public class ToothReadWrite {

    //------ ISSO DAQUI É UM MONTE DE DECLARAÇÃO DOIDA, NA REALIDADE NGM LIGA PRA ISSO --------//
    private static BluetoothAdapter adaptador = BluetoothAdapter.getDefaultAdapter();
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static BluetoothSocket btSocket = null;
    private static BluetoothDevice btDevice = null;
    private static OutputStream outstream = null;
    private static InputStream instream = null;




    public ArrayAdapter<String> deviceslist;







    //--------- AQUI EU FIZ MAS NAO SEI SE FUNCIONA, MAS SE FUNCIONASSE ERA PRA VER OS AMIGUINHOS -------//
    public static Set<BluetoothDevice> Pareados()
    {
        return adaptador.getBondedDevices();
    }

    //------ USAMOS ESSA BAGAÇA ABAIXO PARA FECHAR O QUE DEU RUIM -------//
    private static void close(Closeable aConnectedObject) {
        if ( aConnectedObject == null ) return;
        try {
            aConnectedObject.close();
        } catch ( IOException e ) {
        }
        aConnectedObject = null;
    }

    //------ ISSO EU SEI LÁ É QUE, O ANDROID STUDIO COLOCOU AUTOMATICAMENTE -------//
    private UUID getSerialPortUUID() {
        return MY_UUID;
    }

    //------ ISSO É BOM PQ A GNT USA PRA CONECTAR COM NOSSOS AMIGUINHOS -------//
    public static void Connect(String DeviceMac) {
        try
        {
            btDevice = adaptador.getRemoteDevice(DeviceMac.trim());
            btSocket = btDevice.createRfcommSocketToServiceRecord(MY_UUID);
            adaptador.cancelDiscovery();
            btSocket.connect();
            outstream = btSocket.getOutputStream();
            instream = btSocket.getInputStream();


        }
        //--------- AQUI É O FAMOSO CATCH QUE SE CRASHAR NÃO CRASHA -----------//
        catch (Exception e)
        {
            Log.v("Erro:",e.toString());
            close( instream );
            close( btSocket );


        }

    }
    //--------- BLUETOOTH STATUS ----------//
    public static boolean statusTooth()
    {
        return adaptador.isEnabled();
    }


    //------- ISSO AQUI ERA PRA ESCREVER MAS NAO SEI SE FUNCIONA ------/
    public static void WriteBuffer(String msg)
    {
        byte[] Buffer=msg.getBytes();

        try {
            outstream.write(Buffer);
        }
        catch (Exception e)
        {
            Log.v("ERRO:",e.toString());
        }

    }


    /*------- ESSA DESGRAÇA ME DEU MUITO TRABALHO E SERVE PRA LER AS CAPOEIRA QUE CHEGA
              PRA VC USAR SEU ANIMAL É SÓ JOGAR EM UM TEXTVIEW+COUNTDOWNTIMER -------*/

    public static String ReadBuffer() {
        byte[] buffer = new byte[1024];
        int bytes;
        try
        {
            if (instream.available()>0) {
                bytes = ToothReadWrite.instream.read(buffer);
                String str = new String(buffer);
                Log.v("BUFFER:", str);
                return str;
            }
            else {SystemClock.sleep(100);}


        } catch (Exception e) {

        }
        return null;
    }
    //----- AQUI É PRA DISCONECTA DOS PARÇA-----//
    public static void disconnect()
    {
        try {
            btSocket.close();
        } catch (IOException e)
        {

            e.printStackTrace();
        }
    }


}