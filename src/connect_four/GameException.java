// file: connect_four.GameException.java
// CSC 243, Spring 2020
// File was originally made by Dr. Schwesinger, updated and edited by Nik Golombek

package connect_four;

/**
* An exception for when a false move occurs
*/
public class GameException extends Exception {
	/**
	* Give message to the user about the exception
	* @param message the message to be delivered to the user
	*/
    public GameException(String message) {
        super(message);
    }
}
