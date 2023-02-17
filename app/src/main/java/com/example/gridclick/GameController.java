package com.example.gridclick;

import android.view.View;
import java.util.Collections;
import java.util.ArrayList;

public class GameController implements View.OnClickListener {

    private GameModel gameModel;
    private GameView gameView;

    public GameController(GameModel model, GameView view) {
        gameModel = model;
        gameView = view;
    }

    public void beginGame() {
        // make all the buttons visible
        gameView.resetButtons();


        // hide bottom right button
        gameView.hide(3, 3);
        gameModel.setEmptyRow(3);
        gameModel.setEmptyCol(3);

        // get the numbers
        ArrayList<Integer> nums = new ArrayList<>();
        for(int i = 1; i <= 15; i++) {
            nums.add(i);
        }

        // randomize the numbers
        Collections.shuffle(nums);

        // make sure the game is winnable
        boolean winnable = gameView.winnableGame(nums);
        if(!winnable) {
            beginGame();
        }

        // display the numbers
        gameView.displayNumbers(nums);

    }

    public void onClick(View view) {
        // reset the game if necessary
        if(view.getId() == R.id.resetButton) {
            beginGame();
        }

        // check if the move was legal
        int move = view.getId();
        ArrayList<Integer> possibleMoves = gameView.possibleMoves();

        // change location if the move was legal
        if(possibleMoves.contains(move)) {
            gameView.moveButton(view);

            // check if the user has won
            if(gameView.checkWin()) {
                gameView.displayWin();
            }
        }
    }
}
