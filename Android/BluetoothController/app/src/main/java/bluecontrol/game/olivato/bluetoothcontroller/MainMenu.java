package bluecontrol.game.olivato.bluetoothcontroller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

}
