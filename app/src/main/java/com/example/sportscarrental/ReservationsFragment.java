package com.example.sportscarrental;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReservationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReservationsFragment extends Fragment {
    private DBhelper DB;
    private DatePicker datePicker;
    private Spinner spinnerCars;
    private Button btnReserve, btnUpdate, btnDelete, btnView;
    private EditText txtName, txtMail;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReservationsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReservationsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReservationsFragment newInstance(String param1, String param2) {
        ReservationsFragment fragment = new ReservationsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_reservations, container, false);
        datePicker = rootView.findViewById(R.id.datePicker);
        spinnerCars = rootView.findViewById(R.id.spinnerCars);
        txtName = rootView.findViewById(R.id.txtName);
        txtMail = rootView.findViewById(R.id.txtMail);
        btnReserve = rootView.findViewById(R.id.btnReserve);
        btnUpdate = rootView.findViewById(R.id.btnUpdate);
        btnDelete = rootView.findViewById(R.id.btnDelete);
        btnView = rootView.findViewById(R.id.btnView);

        DB = new DBhelper(requireContext());
        ArrayAdapter<String> carsAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, DB.getCarsList());
        carsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCars.setAdapter(carsAdapter);

        DBhelper DB = new DBhelper(requireContext());
        btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtName.getText().toString();
                String mail = txtMail.getText().toString();
                int year = datePicker.getYear();
                int month = datePicker.getMonth() +1;
                int day = datePicker.getDayOfMonth();
                String selectedDate = year + "-" + month + "-" + day;
                String selectedCar = spinnerCars.getSelectedItem().toString();


                Boolean checkDBop = DB.insertUserData(name, mail, selectedDate, selectedCar);
                if(checkDBop == true){
                    Toast.makeText(getActivity(), "New car reserved sucessfully", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getActivity(), "Unable to reserve a car", Toast.LENGTH_LONG).show();

                }

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtName.getText().toString();
                String mail = txtMail.getText().toString();
                int year = datePicker.getYear();
                int month = datePicker.getMonth() +1;
                int day = datePicker.getDayOfMonth();
                String selectedDate = year + "-" + month + "-" + day;
                String selectedCar = spinnerCars.getSelectedItem().toString();

                Boolean checkupdate = DB.updateUserData(name, mail, selectedDate, selectedCar);
                if(checkupdate == true){
                    Toast.makeText(getActivity(), "Car reservation updated sucessfully", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getActivity(), "Unable to update car reservation", Toast.LENGTH_LONG).show();

                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtName.getText().toString();

                Boolean checkdelete = DB.deleteUserData(name);
                if(checkdelete == true){
                    Toast.makeText(getActivity(), "Car reservation deleted Sucessfully", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getActivity(), "Unable to delete car reservation", Toast.LENGTH_LONG).show();

                }
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtName.getText().toString().trim();
                Cursor records;

                if (!name.isEmpty()) {
                    records = DB.viewUserData(name);
                } else {
                    records = DB.viewUserData(null);
                }

                StringBuffer allRecords = new StringBuffer();
                while (records.moveToNext()){
                    allRecords.append("Name:"+records.getString(0)+"\n");
                    allRecords.append("Mail:"+records.getString(1)+"\n");
                    allRecords.append("Date:"+records.getString(2)+"\n");
                    allRecords.append("Car:"+records.getString(3)+"\n");

                }
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setCancelable(true);
                builder.setTitle("View all car reservations");
                builder.setMessage(allRecords.toString());
                builder.show();
            }
        });



        return rootView;
    }

}