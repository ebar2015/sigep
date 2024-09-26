package co.gov.dafp.sigep2.mbean.hojadevida;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import co.gov.dafp.sigep2.entities.LogroRecurso;
import co.gov.dafp.sigep2.entities.ParticipacionInstitucion;
import co.gov.dafp.sigep2.entity.PaisDTO;
import co.gov.dafp.sigep2.mbean.ext.DatoAdicionalExt;
import co.gov.dafp.sigep2.mbean.ext.DatoContactoExt;
import co.gov.dafp.sigep2.mbean.ext.DocumentosAdicionalesHvExt;
import co.gov.dafp.sigep2.mbean.ext.EducacionFormalExt;
import co.gov.dafp.sigep2.mbean.ext.EvaluacionDesempenoExt;
import co.gov.dafp.sigep2.mbean.ext.ExperienciaDocenteExt;
import co.gov.dafp.sigep2.mbean.ext.ExperienciaProfesionalExt;
import co.gov.dafp.sigep2.mbean.ext.IdiomaPersonaExt;
import co.gov.dafp.sigep2.mbean.ext.OtroConocimientoExt;
import co.gov.dafp.sigep2.mbean.ext.ParticipacionProyectoExt;
import co.gov.dafp.sigep2.mbean.ext.PersonaExt;
import co.gov.dafp.sigep2.mbean.ext.PublicacionExt;
import co.gov.dafp.sigep2.mbean.ext.ReconocimientoExt;

public class FotoHojaVida  implements Serializable{

	private static final long serialVersionUID = 7055431285630621500L;
	private PersonaExt detallePersona;
	private DatoContactoExt datoContacto;
	private DatoAdicionalExt datoAdicional;
	
	private List<EducacionFormalExt> detalleEducacion;
	private List<ExperienciaProfesionalExt> detalleLaboral;
	private List<ExperienciaDocenteExt> detalleExpDocente;
	private List<IdiomaPersonaExt> detalleIdioma;
	private List<OtroConocimientoExt> otroConocimientoExt = new ArrayList<>();
	private List<DocumentosAdicionalesHvExt> lstDocumentosAdicionales;
	   
	private List<LogroRecurso> lstLogrosRecursos;
	private List<PublicacionExt> lstpublicaciones;
	private List<ReconocimientoExt> lstReconocimientos;
	private List<ParticipacionProyectoExt> lstParticipacionProyecto;
	private List<ParticipacionInstitucion> lstParticipacionInstitucion;
	private List<EvaluacionDesempenoExt> lstEvaluacionDesempeno;
	
	private List<PaisDTO> nacionalidad = new ArrayList<>();
	
	public PersonaExt getDetallePersona() {
		return detallePersona;
	}
	public void setDetallePersona(PersonaExt detallePersona) {
		this.detallePersona = detallePersona;
	}
	public DatoContactoExt getDatoContacto() {
		return datoContacto;
	}
	public void setDatoContacto(DatoContactoExt datoContacto) {
		this.datoContacto = datoContacto;
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
		this.detalleLaboral = detalleLaboral;
	}
	public List<ExperienciaDocenteExt> getDetalleExpDocente() {
		return detalleExpDocente;
	}
	public void setDetalleExpDocente(List<ExperienciaDocenteExt> detalleExpDocente) {
		this.detalleExpDocente = detalleExpDocente;
	}
	public List<IdiomaPersonaExt> getDetalleIdioma() {
		return detalleIdioma;
	}
	public void setDetalleIdioma(List<IdiomaPersonaExt> detalleIdioma) {
		this.detalleIdioma = detalleIdioma;
	}
	public DatoAdicionalExt getDatoAdicional() {
		return datoAdicional;
	}
	public void setDatoAdicional(DatoAdicionalExt datoAdicional) {
		this.datoAdicional = datoAdicional;
	}
	public List<OtroConocimientoExt> getOtroConocimientoExt() {
		return otroConocimientoExt;
	}
	public void setOtroConocimientoExt(List<OtroConocimientoExt> otroConocimientoExt) {
		this.otroConocimientoExt = otroConocimientoExt;
	}
	public List<PaisDTO> getNacionalidad() {
		return nacionalidad;
	}
	public void setNacionalidad(List<PaisDTO> nacionalidad) {
		this.nacionalidad = nacionalidad;
	}
	
	public List<LogroRecurso> getLstLogrosRecursos() {
		return lstLogrosRecursos;
	}
	public void setLstLogrosRecursos(List<LogroRecurso> lstLogrosRecursos) {
		this.lstLogrosRecursos = lstLogrosRecursos;
	}
	public List<PublicacionExt> getLstpublicaciones() {
		return lstpublicaciones;
	}
	public void setLstpublicaciones(List<PublicacionExt> lstpublicaciones) {
		this.lstpublicaciones = lstpublicaciones;
	}
	public List<ReconocimientoExt> getLstReconocimientos() {
		return lstReconocimientos;
	}
	public void setLstReconocimientos(List<ReconocimientoExt> lstReconocimientos) {
		this.lstReconocimientos = lstReconocimientos;
	}
	public List<ParticipacionProyectoExt> getLstParticipacionProyecto() {
		return lstParticipacionProyecto;
	}
	public void setLstParticipacionProyecto(List<ParticipacionProyectoExt> lstParticipacionProyecto) {
		this.lstParticipacionProyecto = lstParticipacionProyecto;
	}
	public List<ParticipacionInstitucion> getLstParticipacionInstitucion() {
		return lstParticipacionInstitucion;
	}
	public void setLstParticipacionInstitucion(List<ParticipacionInstitucion> lstParticipacionInstitucion) {
		this.lstParticipacionInstitucion = lstParticipacionInstitucion;
	}
	public List<EvaluacionDesempenoExt> getLstEvaluacionDesempeno() {
		return lstEvaluacionDesempeno;
	}
	public void setLstEvaluacionDesempeno(List<EvaluacionDesempenoExt> lstEvaluacionDesempeno) {
		this.lstEvaluacionDesempeno = lstEvaluacionDesempeno;
	}
	/**
	 * @return the lstDocumentosAdicionales
	 */
	public List<DocumentosAdicionalesHvExt> getLstDocumentosAdicionales() {
		return lstDocumentosAdicionales;
	}
	/**
	 * @param lstDocumentosAdicionales the lstDocumentosAdicionales to set
	 */
	public void setLstDocumentosAdicionales(List<DocumentosAdicionalesHvExt> lstDocumentosAdicionales) {
		this.lstDocumentosAdicionales = lstDocumentosAdicionales;
	}
}