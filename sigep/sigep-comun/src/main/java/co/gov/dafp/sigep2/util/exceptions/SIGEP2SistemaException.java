package co.gov.dafp.sigep2.util.exceptions;

public class SIGEP2SistemaException extends Exception {
	private static final long serialVersionUID = 47341547564341101L;

	public SIGEP2SistemaException() {
		super();
	}

	public SIGEP2SistemaException(String arg0) {
		super(arg0);
	}

	public SIGEP2SistemaException(Throwable arg0) {
		super(arg0);
	}

	public SIGEP2SistemaException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public SIGEP2SistemaException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
