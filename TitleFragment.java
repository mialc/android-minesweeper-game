package com.example.minesweeper.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.View;
import android.widget.Button;

import com.example.minesweeper.R;

public class TitleFragment extends Fragment {

    public TitleFragment() {
        super(R.layout.fragment_title);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        Button start = v.findViewById(R.id.btnStart);
        start.setOnClickListener(view ->
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_view, new SettingsFragment())
                        .addToBackStack(null)
                        .commit()
        );
    }
}
