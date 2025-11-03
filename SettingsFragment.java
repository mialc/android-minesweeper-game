package com.example.minesweeper.ui;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.example.minesweeper.R;
import com.example.minesweeper.shared.SettingsViewModel;

public class SettingsFragment extends Fragment {

    private SettingsViewModel vm;

    public SettingsFragment() {
        super(R.layout.fragment_settings);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(requireActivity()).get(SettingsViewModel.class);

        Spinner spRows = v.findViewById(R.id.spRows);
        Spinner spCols = v.findViewById(R.id.spCols);
        Spinner spMines = v.findViewById(R.id.spMines);

        Integer[] sizeOptions = {5,6,7,8,9,10};
        Integer[] minePercents = {10,15,20};

        spRows.setAdapter(new android.widget.ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, sizeOptions));
        spCols.setAdapter(new android.widget.ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, sizeOptions));
        spMines.setAdapter(new android.widget.ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, minePercents));

        // ✅ Color Palette with names
        ColorItem[] palette = new ColorItem[]{
                new ColorItem("Light Gray", Color.LTGRAY),
                new ColorItem("White", Color.WHITE),
                new ColorItem("Yellow", Color.YELLOW),
                new ColorItem("Mint", 0xFFB2FFCC),
                new ColorItem("Sky Blue", 0xFFB3E5FC),
                new ColorItem("Lilac", 0xFFCE93D8),
                new ColorItem("Flag Red", 0xFFFF5252),
                new ColorItem("Mine Purple", 0xFF9C27B0)
        };

        Spinner spCovered = v.findViewById(R.id.spCovered);
        Spinner spUncovered = v.findViewById(R.id.spUncovered);
        Spinner spFlag = v.findViewById(R.id.spFlag);
        Spinner spMine = v.findViewById(R.id.spMine);

        ColorAdapter adapter = new ColorAdapter(requireContext(), palette);
        spCovered.setAdapter(adapter);
        spUncovered.setAdapter(adapter);
        spFlag.setAdapter(adapter);
        spMine.setAdapter(adapter);

        // Select defaults
        spCovered.setSelection(0);
        spUncovered.setSelection(1);
        spFlag.setSelection(6);
        spMine.setSelection(7);

        Button start = v.findViewById(R.id.btnStartGame);
        start.setOnClickListener(view -> {
            vm.rows = (Integer) spRows.getSelectedItem();
            vm.cols = (Integer) spCols.getSelectedItem();
            vm.minePercent = ((Integer) spMines.getSelectedItem()) / 100.0;

            vm.coveredColor   = ((ColorItem) spCovered.getSelectedItem()).color;
            vm.uncoveredColor = ((ColorItem) spUncovered.getSelectedItem()).color;
            vm.flagColor      = ((ColorItem) spFlag.getSelectedItem()).color;
            vm.mineColor      = ((ColorItem) spMine.getSelectedItem()).color;

            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_view, new GameFragment())
                    .addToBackStack(null)
                    .commit();
        });
    }
}
