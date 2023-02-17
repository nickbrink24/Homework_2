package com.example.gridclick;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;

public class GameView {

    private GameModel gameModel;
    private Button[][] buttons;

    public GameView(GameModel model) {
        gameModel = model;
        buttons = new Button[4][4];
    }

    public void addButton(int row, int col, Button button) {
        buttons[row][col] = button;
    }

    public void setOnClick(GameController controller) {
        for(int row = 0; row < 4; row++) {
            for(int col = 0; col < 4; col++) {
                // set an OnClickListener for each button
                buttons[row][col].setOnClickListener(controller);
            }
        }
    }

    public void resetButtons() {
        for(int row = 0; row < 4; row++) {
            for(int col = 0; col < 4; col++) {
                // reset the buttons
                buttons[row][col].setClickable(true);
                buttons[row][col].setVisibility(View.VISIBLE);
                buttons[row][col].setBackgroundColor(Color.BLACK);
            }
        }
    }

    public void hide(int row, int col) {
        // make the button not clickable and invisible
        buttons[row][col].setClickable(false);
        buttons[row][col].setVisibility(View.INVISIBLE);
    }

    public void displayNumbers(ArrayList<Integer> nums) {
        int index = 0;
        for(int row = 0; row < 4; row++) {
            for(int col = 0; col < 4; col++) {
                // prevent out of bounds error
                if(index == 15) {
                    break;
                }

                // display number as text on each button
                buttons[row][col].setText(Integer.toString(nums.get(index)));
                index++;
            }
        }
    }

    public Boolean winnableGame(ArrayList<Integer> nums) {
        int inversions = 0;

        // if a current number is bigger than a following
        // number (Left to Right, Top to Bottom)
        // then it's considered an inversion
        for(int i = 0; i < nums.size() - 1; i++) {
            for(int j = i + 1; j < nums.size(); j++) {
                if(nums.get(i) > nums.get(j)) {
                    inversions++;
                }
            }
        }

        // if the number of inversions is even, the puzzle has a solution
        return inversions % 2 == 0;
    }

    public ArrayList<Integer> possibleMoves() {
        // possible moves will be denoted based on their ID's which are integers
        ArrayList<Integer> ret = new ArrayList<>();

        // get the current empty space
        int row = gameModel.getEmptyRow();
        int col = gameModel.getEmptyCol();

        if(row == 0) {
            // add only the bottom button
            ret.add(addBottomButton(row, col));
        } else if(row == 3) {
            // add only the top button
            ret.add(addTopButton(row, col));
        } else {
            // add top and bottom buttons
            ret.add(addTopButton(row, col));
            ret.add(addBottomButton(row, col));
        }

        if(col == 0) {
            // add only the right button
            ret.add(addRightButton(row, col));
        } else if(col == 3) {
            // add only the left button
            ret.add(addLeftButton(row, col));
        } else {
            // add right and left buttons
            ret.add(addRightButton(row, col));
            ret.add(addLeftButton(row, col));
        }

        return ret;
    }

    public int addBottomButton(int row, int col) {
        return buttons[row + 1][col].getId();
    }

    public int addTopButton(int row, int col) {
        return buttons[row - 1][col].getId();
    }

    public int addRightButton(int row, int col) {
        return buttons[row][col + 1].getId();
    }

    public int addLeftButton(int row, int col) {
        return buttons[row][col - 1].getId();
    }

    public void moveButton(View view) {
        // get the current empty button
        int row = gameModel.getEmptyRow();
        int col = gameModel.getEmptyCol();

        // get the clicked button and it's number
        Button clickedButton = (Button)view;
        String num = clickedButton.getText().toString();

        // 'swap' clicked button and empty button
        buttons[row][col].setVisibility(View.VISIBLE);
        buttons[row][col].setClickable(true);
        buttons[row][col].setText(num);

        // make the clicked button invisible
        clickedButton.setVisibility(View.INVISIBLE);
        clickedButton.setClickable(false);

        // update the empty space coordinates
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                if(buttons[i][j].getId() == clickedButton.getId()) {
                    gameModel.setEmptyRow(i);
                    gameModel.setEmptyCol(j);
                }
            }
        }
    }

    public boolean checkWin() {
        int index = 1;
        for(int row = 0; row < 4; row++) {
            for(int col = 0; col < 4; col++) {
                // if the position matches the text, return true
                if(!buttons[row][col].getText().equals(Integer.toString(index))) {
                    return false; // return false if there's a single mismatch
                }
                index++;

                // prevent out of bounds
                if(index == 16) {
                    break;
                }
            }
        }

        return true;
    }

    public void displayWin() {
        for(int row = 0; row < 4; row++) {
            for(int col = 0; col < 4; col++) {
                // change background color to green for win
                buttons[row][col].setBackgroundColor(Color.GREEN);
            }
        }
    }
}
