package au.edu.anu.rscs.aot;

import fr.ens.biologie.generic.Textable;

/**
 * @author shayne.flint@anu.edu.au
 *
 * 
 * 
 */
// NB: this was AotException before - but Exception have to stay local to their Library
public class QGraphException extends RuntimeException {

	/**
	 * Serializable
	 */
	private static final long serialVersionUID = 4121451020638650287L;

	public QGraphException(Textable item, String message) {
		super("[on " + item + "]\n[" + message + "]");
	}

	public QGraphException(String message) {
		super("[" + message + "]");
	}

	public QGraphException(Exception e) {
		super(e);
	}

	public QGraphException(String message, Exception e) {
		super("[" + message + "]\n[original exception: " + e + "]");
		e.printStackTrace();
	}

}
