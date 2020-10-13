import au.edu.rmit.isys1117.group9.GameMain;
import au.edu.rmit.isys1117.group9.exception.InvalidInputException;
import au.edu.rmit.isys1117.group9.exception.LadderPlacementException;
import au.edu.rmit.isys1117.group9.exception.SnakeGuardPlacementException;
import au.edu.rmit.isys1117.group9.exception.SnakePlacementException;

public class SLGame {

    // The very first method to be called
    // This method constructs a SLGame object and calls its control method
    public static void main(String args[]) throws InvalidInputException, SnakeGuardPlacementException, SnakePlacementException, LadderPlacementException {
        GameMain gameMain=new GameMain();
        gameMain.enableTestMode();
        gameMain.startGame();
    }


}