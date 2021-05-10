package sv.edu.udb.mfragments1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ExampleActivity extends AppCompatActivity {
    public ExampleActivity() {
        super(R.layout.example_activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragment_container_view, new ExampleFragment(), null)
                    .commit();
        }
    }
}