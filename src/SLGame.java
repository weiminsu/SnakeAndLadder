import au.edu.rmit.isys1117.group9.GameMain;
import au.edu.rmit.isys1117.group9.model.LadderPlacementException;
import au.edu.rmit.isys1117.group9.model.SnakeGuardPlacementException;
import au.edu.rmit.isys1117.group9.model.SnakePlacementException;

import javax.swing.*;

public class SLGame {

    // The very first method to be called
    // This method constructs a SLGame object and calls its control method
    public static void main(String args[]) throws SnakeGuardPlacementException, SnakePlacementException, LadderPlacementException {
        GameMain gameMain=new GameMain();

        gameMain.startGame();
    }


}