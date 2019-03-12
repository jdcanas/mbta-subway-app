package mbta;

public class NoPathFoundException extends Exception {
	
	private static final long serialVersionUID = -5675372963623750263L;

	public NoPathFoundException(String message) {
		super(message);
	}
	
}
