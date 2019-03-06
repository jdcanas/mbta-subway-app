package mbta;

public class MBTAUnexpectedResponseFormat extends Exception {

	private static final long serialVersionUID = -632334111195038660L;

	public MBTAUnexpectedResponseFormat(String message) {
		super(message);
	}
}
