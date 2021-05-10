package sv.edu.udb.mfragments1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements FragmentA.FragmentAListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentA fragmentA = new FragmentA();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_a, fragmentA)
                .commit();
    }

    @Override
    public void onInputASent(Modelo modelo) {

        FragmentB fragmentB = FragmentB.getInstance(modelo);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_a, fragmentB).commit();

    }
}