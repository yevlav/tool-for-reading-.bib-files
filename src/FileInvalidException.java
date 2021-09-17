import java.lang.Exception;

public class FileInvalidException extends Exception {

	public FileInvalidException() {
		super("ERROR: Input file cannot be parsed due to missing information (i.e. month={}, title={}, etc.)");
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return super.getMessage();
	}
	
}
