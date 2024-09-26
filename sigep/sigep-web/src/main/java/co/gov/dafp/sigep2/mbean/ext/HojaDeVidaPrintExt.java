package co.gov.dafp.sigep2.mbean.ext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import co.gov.dafp.sigep2.entities.LogroRecurso;
import co.gov.dafp.sigep2.entities.NacionalidadPerfil;
import co.gov.dafp.sigep2.entities.ParticipacionInstitucion;

public class HojaDeVidaPrintExt extends HojaVidaExt implements Serializable {

	private static final long serialVersionUID = -7989606583825797118L;
	private String rutaImagen;
	PersonaExt detallePersona;
	private NacionalidadPerfil nacionalidadPersona;
	private List<EducacionFormalExt> detalleEducacion = new ArrayList<>();
	private List<EducacionFormalExt> detalleEducacionPrimaria = new ArrayList<>();
	private List<EducacionFormalExt> detalleEducacionProfesional = new ArrayList<>();
	private List<ExperienciaProfesionalExt> empleoActualExt;
	private List<ExperienciaProfesionalExt> detalleLaboral;
	private List<ExperienciaDocenteExt> detalleExpDocente;
	private List<IdiomaPersonaExt> detalleIdioma;

	private List<DatoAdicionalExt> datoAdicional = new ArrayList<>();
	private List<LogroRecurso> logroRecurso = new ArrayList<>();
	private List<PublicacionExt> publicacion = new ArrayList<>();
	private List<ReconocimientoExt> reconocimiento = new ArrayList<>();
	private List<ParticipacionProyectoExt> participacionProyecto = new ArrayList<>();
	private List<ParticipacionInstitucion> participacionInstitucion = new ArrayList<>();
	private List<EvaluacionDesempenoExt> evaluacionDesempeno = new ArrayList<>();
	private List<OtroConocimientoExt> otroConocimientoExt = new ArrayList<>();
	
	private List<ExperienciaProfesionalExt> detalleLaboralCompleto = new ArrayList<>();

	private String nombresPersona;

	private Boolean flgFormatoCompleto;
	private Boolean flgGerenciaPublica;

	private Long tiempoTrabajoPublicoano 				= 0l;
	private Long tiempoTrabajoPublicomes 				= 0l;
	private Long tiempoTrabajoPrivadoano 				= 0l;
	private Long tiempoTrabajoPrivadomes 				= 0l;
	private Long tiempoTrabajoIndependienteano 			= 0l;
	private Long tiempoTrabajoPrivadoIndependientemes 	= 0l;	
	private Long tiempoTrabajoAno 						= 0l;
	private Long tiempoTrabajoMes 						= 0l;

	private String fechaActual;

	public String getFechaActual() {
		return fechaActual;
	}	

	public void setDatoAdicional(List<DatoAdicionalExt> datoAdicional) {
		this.datoAdicional = datoAdicional;
	}

	public void setLogroRecurso(List<LogroRecurso> logroRecurso) {
		this.logroRecurso = logroRecurso;
	}

	public void setPublicacion(List<PublicacionExt> publicacion) {
		this.publicacion = publicacion;
	}

	public void setReconocimiento(List<ReconocimientoExt> reconocimiento) {
		this.reconocimiento = reconocimiento;
	}

	public void setParticipacionProyecto(List<ParticipacionProyectoExt> participacionProyecto) {
		this.participacionProyecto = participacionProyecto;
	}

	public void setParticipacionInstitucion(List<ParticipacionInstitucion> participacionInstitucion) {
		this.participacionInstitucion = participacionInstitucion;
	}



	public void setFechaActual(String fechaActual) {
		this.fechaActual = fechaActual;
	}
	
	public List<ExperienciaProfesionalExt> getEmpleoActualExt() {
		return empleoActualExt;
	}

	public void setEmpleoActualExt(List<ExperienciaProfesionalExt> empleoActualExt) {
		if (empleoActualExt.isEmpty()) {
			List<ExperienciaProfesionalExt> empleoActual = new ArrayList<>();
			empleoActual.add(new ExperienciaProfesionalExt());
			empleoActualExt = empleoActual;
		}
		this.empleoActualExt = empleoActualExt;
	}

	public String getNombresPersona() {
		if (detallePersona.getPrimerNombre() != null) {
			nombresPersona = detallePersona.getPrimerNombre();
		}
		if (detallePersona.getSegundoNombre() != null) {
			nombresPersona += " " + detallePersona.getSegundoNombre();
		}
		return nombresPersona;
	}

	public void setNombresPersona(String nombresPersona) {
		this.nombresPersona = nombresPersona;
	}

	public PersonaExt getDetallePersona() {
		return detallePersona;
	}

	public void setDetallePersona(PersonaExt detallePersona) {
		this.detallePersona = detallePersona;
	}

	public List<OtroConocimientoExt> getOtroConocimientoExt() {
		return otroConocimientoExt;
	}

	public void setOtroConocimientoExt(List<OtroConocimientoExt> otroConocimientoExt) {
		if (otroConocimientoExt.size() <= 0) {
			List<OtroConocimientoExt> otroConocimiento = new ArrayList<>();
			otroConocimiento.add(new OtroConocimientoExt());
			otroConocimientoExt = otroConocimiento;
		}
		this.otroConocimientoExt = otroConocimientoExt;
	}

	public List<EducacionFormalExt> getDetalleEducacion() {
		return detalleEducacion;
	}

	public void setDetalleEducacion(List<EducacionFormalExt> detalleEducacion) {
		this.detalleEducacion = detalleEducacion;
	}

	public List<ExperienciaProfesionalExt> getDetalleLaboral() {
		return detalleLaboral;
	}

	public void setDetalleLaboral(List<ExperienciaProfesionalExt> detalleLaboral) {
		if (detalleLaboral.isEmpty()) {
			List<ExperienciaProfesionalExt> defaulLaboral = new ArrayList<>();
			defaulLaboral.add(new ExperienciaProfesionalExt());
			detalleLaboral = defaulLaboral;
		}
		this.detalleLaboral = detalleLaboral;
	}

	public List<ExperienciaDocenteExt> getDetalleExpDocente() {
		return detalleExpDocente;
	}

	public void setDetalleExpDocente(List<ExperienciaDocenteExt> detalleExpDocente) {
		if (detalleExpDocente.size() <= 0) {
			List<ExperienciaDocenteExt> defaulExpDocente = new ArrayList<>();
			ExperienciaDocenteExt defaultExpDoc = new ExperienciaDocenteExt();
			defaulExpDocente.add(defaultExpDoc);
			detalleExpDocente = defaulExpDocente;
		}
		this.detalleExpDocente = detalleExpDocente;
	}

	public List<IdiomaPersonaExt> getDetalleIdioma() {
		return detalleIdioma;
	}

	public void setDetalleIdioma(List<IdiomaPersonaExt> detalleIdioma) {
		if (detalleIdioma.size() <= 0) {
			List<IdiomaPersonaExt> defaulIdioma = new ArrayList<>();
			defaulIdioma.add(new IdiomaPersonaExt());
			detalleIdioma = defaulIdioma;
		}
		this.detalleIdioma = detalleIdioma;
	}

	public Boolean getFlgGerenciaPublica() {
		return flgGerenciaPublica;
	}

	public void setFlgGerenciaPublica(Boolean flgGerenciaPublica) {
		this.flgGerenciaPublica = flgGerenciaPublica;
	}

	public Boolean getFlgFormatoCompleto() {
		return flgFormatoCompleto;
	}

	public void setFlgFormatoCompleto(Boolean flgFormatoCompleto) {
		this.flgFormatoCompleto = flgFormatoCompleto;
	}

	public List<LogroRecurso> getLogroRecurso() {
		return logroRecurso;
	}



	public List<PublicacionExt> getPublicacion() {
		return publicacion;
	}

	public void setPublicacion(PublicacionExt publicacion) {
		this.publicacion.add(publicacion);
	}

	public List<ReconocimientoExt> getReconocimiento() {
		return reconocimiento;
	}

	public List<ParticipacionProyectoExt> getParticipacionProyecto() {
		return participacionProyecto;
	}

	public void setParticipacionProyecto(ParticipacionProyectoExt participacionProyecto) {
		this.participacionProyecto.add(participacionProyecto);
	}

	public List<ParticipacionInstitucion> getParticipacionInstitucion() {
		return participacionInstitucion;
	}

	public void setParticipacionInstitucion(ParticipacionInstitucion participacionInstitucion) {
		this.participacionInstitucion.add(participacionInstitucion);
	}



	public List<EvaluacionDesempenoExt> getEvaluacionDesempeno() {
		return evaluacionDesempeno;
	}

	public void setEvaluacionDesempeno(List<EvaluacionDesempenoExt> evaluacionDesempeno) {
		this.evaluacionDesempeno = evaluacionDesempeno;
	}

	public List<DatoAdicionalExt> getDatoAdicional() {
		return datoAdicional;
	}

	public void setDatoAdicional(DatoAdicionalExt datoAdicional) {
		this.datoAdicional.add(datoAdicional);
	}

	public Long getTiempoTrabajoPublicoano() {
		return tiempoTrabajoPublicoano;
	}

	public void setTiempoTrabajoPublicoano(Long tiempoTrabajoPublicoano) {
		this.tiempoTrabajoPublicoano = tiempoTrabajoPublicoano;
	}

	public Long getTiempoTrabajoPublicomes() {
		return tiempoTrabajoPublicomes;
	}

	public void setTiempoTrabajoPublicomes(Long tiempoTrabajoPublicomes) {
		this.tiempoTrabajoPublicomes = tiempoTrabajoPublicomes;
	}

	public Long getTiempoTrabajoPrivadoano() {
		return tiempoTrabajoPrivadoano;
	}

	public void setTiempoTrabajoPrivadoano(Long tiempoTrabajoPrivadoano) {
		this.tiempoTrabajoPrivadoano = tiempoTrabajoPrivadoano;
	}

	public Long getTiempoTrabajoPrivadomes() {
		return tiempoTrabajoPrivadomes;
	}

	public void setTiempoTrabajoPrivadomes(Long tiempoTrabajoPrivadomes) {
		this.tiempoTrabajoPrivadomes = tiempoTrabajoPrivadomes;
	}

	public Long getTiempoTrabajoAno() {
		return tiempoTrabajoAno;
	}

	public void setTiempoTrabajoAno(Long tiempoTrabajoAno) {
		this.tiempoTrabajoAno = tiempoTrabajoAno;
	}

	public Long getTiempoTrabajoMes() {
		return tiempoTrabajoMes;
	}

	public void setTiempoTrabajoMes(Long tiempoTrabajoMes) {
		this.tiempoTrabajoMes = tiempoTrabajoMes;
	}

	/**
	 * @return the detalleLaboralCompleto
	 */
	public List<ExperienciaProfesionalExt> getDetalleLaboralCompleto() {
		return detalleLaboralCompleto;
	}

	/**
	 * @param detalleLaboralCompleto the detalleLaboralCompleto to set
	 */
	public void setDetalleLaboralCompleto(List<ExperienciaProfesionalExt> detalleLaboralCompleto) {
		this.detalleLaboralCompleto = detalleLaboralCompleto;
	}

	public List<EducacionFormalExt> getDetalleEducacionPrimaria() {
		return detalleEducacionPrimaria;
	}

	public void setDetalleEducacionPrimaria(List<EducacionFormalExt> detalleEducacionPrimaria) {
		this.detalleEducacionPrimaria = detalleEducacionPrimaria;
	}

	public List<EducacionFormalExt> getDetalleEducacionProfesional() {
		return detalleEducacionProfesional;
	}

	public void setDetalleEducacionProfesional(List<EducacionFormalExt> detalleEducacionProfesional) {
		this.detalleEducacionProfesional = detalleEducacionProfesional;
	}

	/**
	 * @return the tiempoTrabajoIndependienteano
	 */
	public Long getTiempoTrabajoIndependienteano() {
		return tiempoTrabajoIndependienteano;
	}

	/**
	 * @param tiempoTrabajoIndependienteano the tiempoTrabajoIndependienteano to set
	 */
	public void setTiempoTrabajoIndependienteano(Long tiempoTrabajoIndependienteano) {
		this.tiempoTrabajoIndependienteano = tiempoTrabajoIndependienteano;
	}

	/**
	 * @return the tiempoTrabajoPrivadoIndependientemes
	 */
	public Long getTiempoTrabajoPrivadoIndependientemes() {
		return tiempoTrabajoPrivadoIndependientemes;
	}

	/**
	 * @param tiempoTrabajoPrivadoIndependientemes the tiempoTrabajoPrivadoIndependientemes to set
	 */
	public void setTiempoTrabajoPrivadoIndependientemes(Long tiempoTrabajoPrivadoIndependientemes) {
		this.tiempoTrabajoPrivadoIndependientemes = tiempoTrabajoPrivadoIndependientemes;
	}

	public NacionalidadPerfil getNacionalidadPersona() {
		return nacionalidadPersona;
	}

	public void setNacionalidadPersona(NacionalidadPerfil nacionalidadPersona) {
		this.nacionalidadPersona = nacionalidadPersona;
	}

	public String getRutaImagen() {
		return rutaImagen;
	}

	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}
	
	
}