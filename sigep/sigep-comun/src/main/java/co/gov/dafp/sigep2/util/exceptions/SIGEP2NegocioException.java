package co.gov.dafp.sigep2.util.exceptions;

public class SIGEP2NegocioException extends Exception {
	private static final long serialVersionUID = 47341547564341101L;

	public SIGEP2NegocioException() {
		super();
	}

	public SIGEP2NegocioException(String arg0) {
		super(arg0);
	}

	public SIGEP2NegocioException(Throwable arg0) {
		super(arg0);
	}

	public SIGEP2NegocioException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public SIGEP2NegocioException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
