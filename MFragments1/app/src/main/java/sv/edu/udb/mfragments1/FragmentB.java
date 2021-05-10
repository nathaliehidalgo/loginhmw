package sv.edu.udb.mfragments1;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.DecimalFormat;

public class FragmentB extends Fragment {
    private EditText editText;
    private Button buttonOk;

    private static String ARG_MODEL = "arg.fragmentb.model";
    private DecimalFormat df2 = new DecimalFormat("#.##");

    public static FragmentB getInstance(Modelo modelo) {
        FragmentB fragmentB = new FragmentB();

        Bundle arguments = new Bundle();
        arguments.putSerializable(ARG_MODEL, modelo);
        arguments.putString("holi", "hola");

        fragmentB.setArguments(arguments);
        return fragmentB;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_b, container, false);

        Bundle arguments = getArguments();
        Modelo modelo = (Modelo) arguments.getSerializable(ARG_MODEL);
        String x = arguments.getString("holi");
        double salario = modelo.getHoras()*8.50;
        double ISS = salario*0.03;
        double AFP = salario*0.04;
        double RENTA = salario*0.05;
        double salarioNeto = salario - ISS - AFP - RENTA;

        TextView salarioTv = view.findViewById(R.id.salarioId);
        salarioTv.setText(String.valueOf("Salario sin descuentos: " + "$"+df2.format(salario)));

        TextView ISSSTv = view.findViewById(R.id.descISSId);
        ISSSTv.setText(String.valueOf("ISS: " + "$"+df2.format(ISS)));

        TextView AFPTv = view.findViewById(R.id.descAFPId);
        AFPTv.setText(String.valueOf("AFP: " + "$"+df2.format(AFP)));

        TextView RENTATv = view.findViewById(R.id.descRentaId);
        RENTATv.setText(String.valueOf("Renta: " + "$"+ df2.format(RENTA)));

        TextView salarioNetoTv = view.findViewById(R.id.salarioNetoId);
        salarioNetoTv.setText(String.valueOf("Salario neto: " + "$"+df2.format(salarioNeto)));






        return view;
    }
}