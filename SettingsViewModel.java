package com.example.minesweeper.shared;

import androidx.lifecycle.ViewModel;
import android.graphics.Color;

public class SettingsViewModel extends ViewModel {
    public int rows = 5;
    public int cols = 5;
    public double minePercent = 0.10;

    public int coveredColor = Color.LTGRAY;
    public int uncoveredColor = Color.WHITE;
    public int flagColor = 0xFFFF5252;   // red
    public int mineColor = 0xFF9C27B0;   // purple
}
