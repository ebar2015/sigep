package co.gov.dafp.sigep2.util.sistema;

import javax.ws.rs.NotSupportedException;

import co.gov.dafp.sigep2.util.xml.elemento.Archivo;

public class CargueArchivoDefault extends ProcesoCargueArchivo {
	private static final long serialVersionUID = 3808659632211236094L;

	@Override
	public boolean informacionArchivoCorrecta(Archivo archivo) {
		return false;
	}

	@Override
	public void prepararCargue() throws Exception {
		throw new NotSupportedException();
	}
}
