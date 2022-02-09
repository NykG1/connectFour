// file: connect_four.DrawException.java
// CSC 243, Spring 2020
// File was originally made by Dr. Schwesinger, updated and edited by Nik Golombek

package connect_four;

/**
* An exception for when a draw occurs
*/
public class DrawException extends GameException {
	/**
	* Give message to the user about the exception
	* @param message the message to be delivered to the user
	*/
    public DrawException(String message) {
        super(message);
    }
}
