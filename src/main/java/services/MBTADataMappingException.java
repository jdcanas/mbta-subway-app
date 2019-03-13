package services;

//Used to represent errors in processing an MBTA data response
public class MBTADataMappingException extends MBTAAPIException {

	private static final long serialVersionUID = -632334111195038660L;

	public MBTADataMappingException(String message) {
		super(message);
	}
}
