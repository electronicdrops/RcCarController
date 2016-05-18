package bluecontrol.game.olivato.bluetoothcontroller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by olivato on 28/04/16.
 */
public class MainMenu extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.sobre:
            {
                new AlertDialog.Builder(this)
                        .setTitle("Criado por Eletronic Drops")
                        .setMessage("Criado por programadores , para programadores. Caso você não saiba usar, não seja ignorante" +
                                " acesse https://github.com/electronicdrops/RcCarController e aprenda como funciona! Criadores:\n *Lucas Baquião" +
                                "\n *Danilo Queiroz Barbosa \n *Gabriel Olivato")
                        .setPositiveButton("Acessar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String url = "https://github.com/electronicdrops/RcCarController";
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                startActivity(i);
                            }
                        })
                        .setNegativeButton("Sair", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                break;
            }
        }
        return true;
    }


    public void GotoBasicsOne(View view)
    {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }
    public void GotoBasicsTwo(View view)
    {
        Intent i = new Intent(getApplicationContext(), BasicsTwo.class);
        startActivity(i);
    }
    public void vaiprogit(View view)
    {
        String url = "https://github.com/electronicdrops/RcCarController";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
