package services;

//The general exception super class for any data or connection errors related to the MBTA API
public class MBTAAPIException extends Exception {
	
	private static final long serialVersionUID = 2286867073753708422L;

	public MBTAAPIException(String message) {
		super(message);
	}

}
