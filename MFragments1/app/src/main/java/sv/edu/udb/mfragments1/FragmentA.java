package sv.edu.udb.mfragments1;

import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import org.jetbrains.annotations.Nullable;

public class FragmentA extends Fragment {
    private FragmentAListener listener;
    private EditText editText, editText2;
    private Button buttonOk;

    public interface FragmentAListener {
        void onInputASent(Modelo input);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_a, container, false);
        editText = v.findViewById(R.id.nameId);
        editText2 = v.findViewById(R.id.hoursId);
        buttonOk = v.findViewById(R.id.calculatebtnId);
        buttonOk.setOnClickListener(v1 -> {
            Modelo modelo = new Modelo();
            modelo.setNombre(editText.getText().toString());
            modelo.setHoras(Integer.parseInt(editText2.getText().toString()));
            listener.onInputASent(modelo);
        });

        return v;
    }

    public void updateEditText(CharSequence newText) {
        editText.setText(newText);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentAListener) {
            listener = (FragmentAListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentAListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
