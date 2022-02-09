// file: connect_four.GameException.java
// CSC 243, Spring 2020

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
