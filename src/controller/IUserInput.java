package au.edu.rmit.isys1117.group9.controller;

/**
 * A protocol to gather user input
 */
public interface IUserInput {
    int getInt(String message, int from, int to);
    String getString(String message);
    void plainMessage(String message);
}
