package meuestudo.wave.br.meuestudo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    BancoController banco = new BancoController(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void apertar(View v){
        Intent it = new Intent(this, CalendarioActivity.class);
        startActivity(it);
    }
}
