// UI logic & reveal/flag logic assisted by ChatGPT.
// Debugging, UI fixes & game flow done by me Mia Carrillo.

package com.example.minesweeper.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.minesweeper.R;
import com.example.minesweeper.model.Board;
import com.example.minesweeper.shared.SettingsViewModel;

public class GameFragment extends Fragment {

    private SettingsViewModel vm;
    private Board board;
    private GridLayout grid;
    private Button[][] buttons;

    public GameFragment() {
        super(R.layout.fragment_game);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {

        Button restartBtn = v.findViewById(R.id.btnRestart);
        restartBtn.setOnClickListener(view -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container_view, new SettingsFragment())
                    .commit();
        });

        vm = new ViewModelProvider(requireActivity()).get(SettingsViewModel.class);
        grid = v.findViewById(R.id.grid);

        board = new Board(vm.rows, vm.cols, vm.minePercent);

        grid.setRowCount(vm.rows);
        grid.setColumnCount(vm.cols);
        buttons = new Button[vm.rows][vm.cols];

        for (int r = 0; r < vm.rows; r++) {
            for (int c = 0; c < vm.cols; c++) {
                final int rr = r, cc = c;

                Button cell = new Button(getContext());
                cell.setAllCaps(false);
                cell.setPadding(0,0,0,0);

                GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
                lp.rowSpec = GridLayout.spec(r, 1f);
                lp.columnSpec = GridLayout.spec(c, 1f);
                lp.width = 0;
                lp.height = 0;
                lp.setMargins(2,2,2,2);
                cell.setLayoutParams(lp);

                cell.setOnClickListener(view2 -> {
                    // Don't click flagged tiles
                    if (board.flagged[rr][cc]) return;

                    if (!board.revealed[rr][cc]) {
                        board.reveal(rr, cc);
                        updateUI();

                        if (board.mines[rr][cc]) {
                            gameOver(false);
                            showAllAndLock();
                        } else if (board.isWin()) {
                            gameOver(true);
                            showAllAndLock();
                        }
                    }
                });



                cell.setOnLongClickListener(view -> {
                    if (!board.revealed[rr][cc]) {
                        board.toggleFlag(rr, cc);
                        updateUI();
                    }
                    return true;
                });

                buttons[r][c] = cell;
                grid.addView(cell);
            }
        }

        updateUI();
    }

    private void updateUI() {
        for (int r = 0; r < vm.rows; r++) {
            for (int c = 0; c < vm.cols; c++) {
                Button b = buttons[r][c];

                if (board.revealed[r][c]) {
                    b.setEnabled(false);

                    if (board.mines[r][c]) {
                        b.setBackgroundColor(vm.mineColor);
                        b.setText("💣");
                    } else {
                        b.setBackgroundColor(vm.uncoveredColor);
                        int count = board.adj[r][c];
                        b.setText(count == 0 ? "" : String.valueOf(count));
                    }

                } else if (board.flagged[r][c]) {
                    b.setEnabled(true);
                    b.setBackgroundColor(vm.flagColor);
                    b.setText("🚩");

                } else {
                    b.setEnabled(true);
                    b.setBackgroundColor(vm.coveredColor);
                    b.setText("");
                }
            }
        }
    }


    private void showAllAndLock() {
        for (int r = 0; r < vm.rows; r++) {
            for (int c = 0; c < vm.cols; c++) {
                board.revealed[r][c] = true;
                buttons[r][c].setEnabled(false);
            }
        }
        updateUI();
    }

    private void gameOver(boolean win) {
        Toast.makeText(getContext(), win ? "You Win!" : "Game Over", Toast.LENGTH_LONG).show();
        Button restartBtn = getView().findViewById(R.id.btnRestart);
        restartBtn.setVisibility(View.VISIBLE);
    }
}
