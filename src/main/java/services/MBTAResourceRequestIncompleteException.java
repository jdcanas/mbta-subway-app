package services;

//Exception to represent any errors related to connecting to the MBTA APIs
public class MBTAResourceRequestIncompleteException extends MBTAAPIException {
	
	private static final long serialVersionUID = -4678115341582044825L;

	public MBTAResourceRequestIncompleteException(String message) {
		super(message);
	}

}
