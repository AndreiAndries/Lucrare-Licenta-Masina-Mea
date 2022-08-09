package com.example.licentafii2022;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentSearch extends Fragment {

    EditText carName, minPrice, maxPrice, minKm, maxKm, minEngine, maxEngine, fuel;
    Button submitButton;
    public static final String EXTRA_CAR_NAME = "com.example.licentafii2022.EXTRA_CAR_NAME";
    public static final String EXTRA_MIN_PRICE = "com.example.licentafii2022.EXTRA_MIN_PRICE";
    public static final String EXTRA_MAX_PRICE = "com.example.licentafii2022.EXTRA_MAX_PRICE";
    public static final String EXTRA_MIN_KM = "com.example.licentafii2022.EXTRA_MIN_KM";
    public static final String EXTRA_MAX_KM = "com.example.licentafii2022.EXTRA_MAX_KM";
    public static final String EXTRA_MIN_ENGINE = "com.example.licentafii2022.EXTRA_MIN_ENGINE";
    public static final String EXTRA_MAX_ENGINE = "com.example.licentafii2022.EXTRA_MAX_ENGINE";
    public static final String EXTRA_FUEL = "com.example.licentafii2022.EXTRA_FUEL";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment2,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        carName = getActivity().findViewById(R.id.search_car_brand);
        minPrice = getActivity().findViewById(R.id.min_price_search);
        maxPrice = getActivity().findViewById(R.id.max_proce_search);
        minKm = getActivity().findViewById(R.id.min_km_search);
        maxKm = getActivity().findViewById(R.id.max_km_search);
        minEngine = getActivity().findViewById(R.id.min_motorSize_search);
        maxEngine = getActivity().findViewById(R.id.max_motorSize_search);
        fuel = getActivity().findViewById(R.id.fuel_search);
        submitButton = getActivity().findViewById(R.id.search_car_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String carName1, minPrice1,  maxPrice1, minKm1, maxKm1, minEngine1, maxEngine1, fuel1;
                carName1 = carName.getText().toString();
                minPrice1 = minPrice.getText().toString();
                maxPrice1 = maxPrice.getText().toString();
                minKm1 = minKm.getText().toString();
                maxKm1 = maxKm.getText().toString();
                minEngine1 = minEngine.getText().toString();
                maxEngine1 = maxEngine.getText().toString();
                fuel1 = fuel.getText().toString();
                Intent intent = new Intent(getActivity(), SearchedCarsActivity.class);
                intent.putExtra(EXTRA_CAR_NAME,carName1);
                intent.putExtra(EXTRA_MAX_KM,maxKm1);
                intent.putExtra(EXTRA_MIN_KM,minKm1);
                intent.putExtra(EXTRA_MAX_PRICE,maxPrice1);
                intent.putExtra(EXTRA_MIN_PRICE,minPrice1);
                intent.putExtra(EXTRA_MAX_ENGINE,maxEngine1);
                intent.putExtra(EXTRA_MIN_ENGINE,minEngine1);
                intent.putExtra(EXTRA_FUEL,fuel1);
                startActivity(intent);

            }
        });
    }
}
