package controller;

/**
 * A protocol to gather user input
 */
public interface IUserInput {
    int getInt(String message, int from, int to);
    String getString(String message);
    void plainMessage(String message);
}
