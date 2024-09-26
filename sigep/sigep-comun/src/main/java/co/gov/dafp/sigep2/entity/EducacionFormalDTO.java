package co.gov.dafp.sigep2.entity;

import java.io.Serializable;
import java.util.Date;

public class EducacionFormalDTO extends EntidadBaseDTO implements Serializable {
   private static final long serialVersionUID = 6833753906617947528L;

   private long idTitulo;
   private long codPersona;
   private long codPais;
   private PaisDTO paisEducacion;
   private long codDepartamento;
   private long codMunicipio;
   private long institucionAcademica;
   private long tituloObtenido;
   private Date fechaInicio;
   private Date fechaFinalizacion;
   private Boolean flgValidadoRRHH;
   private long codNivelFormacion;

   public long getInstitucionAcademica() {
      return institucionAcademica;
   }

   public void setInstitucionAcademica(long institucionAcademica) {
      this.institucionAcademica = institucionAcademica;
   }

   public long getCodMunicipio() {
      return codMunicipio;
   }

   public void setCodMunicipio(long codMunicipio) {
      this.codMunicipio = codMunicipio;
   }

   public long getCodDepartamento() {
      return codDepartamento;
   }

   public void setCodDepartamento(long codDepartamento) {
      this.codDepartamento = codDepartamento;
   }

   public PaisDTO getPaisEducacion() {
      return paisEducacion;
   }

   public void setPaisEducacion(PaisDTO paisEducacion) {
      this.paisEducacion = paisEducacion;
   }

   public long getCodNivelFormacion() {
      return codNivelFormacion;
   }

   public void setCodNivelFormacion(long codNivelFormacion) {
      this.codNivelFormacion = codNivelFormacion;
   }

   public long getIdTitulo() {
      return idTitulo;
   }

   public void setIdTitulo(long idTitulo) {
      this.idTitulo = idTitulo;
   }

   public long getCodPersona() {
      return codPersona;
   }

   public void setCodPersona(long codPersona) {
      this.codPersona = codPersona;
   }

   public long getCodPais() {
      return codPais;
   }

   public void setCodPais(long codPais) {
      this.codPais = codPais;
   }

   public long getTituloObtenido() {
      return tituloObtenido;
   }

   public void setTituloObtenido(long tituloObtenido) {
      this.tituloObtenido = tituloObtenido;
   }

   public Date getFechaInicio() {
      return fechaInicio;
   }

   public void setFechaInicio(Date fechaInicio) {
      this.fechaInicio = fechaInicio;
   }

   public Date getFechaFinalizacion() {
      return fechaFinalizacion;
   }

   public void setFechaFinalizacion(Date fechaFinalizacion) {
      this.fechaFinalizacion = fechaFinalizacion;
   }

   public Boolean getFlgValidadoRRHH() {
      return flgValidadoRRHH;
   }

   public void setFlgValidadoRRHH(Boolean flgValidadoRRHH) {
      this.flgValidadoRRHH = flgValidadoRRHH;
   }

   public EducacionFormalDTO() {
   }

   public EducacionFormalDTO(long idTitulo, long codPersona, long codPais, PaisDTO paisEducacion, long codDepartamento, long codMunicipio, long institucionAcademica, long tituloObtenido, Date fechaInicio, Date fechaFinalizacion, Boolean flgValidadoRRHH, long codNivelFormacion) {
      this.idTitulo = idTitulo;
      this.codPersona = codPersona;
      this.codPais = codPais;
      this.paisEducacion = paisEducacion;
      this.codDepartamento = codDepartamento;
      this.codMunicipio = codMunicipio;
      this.institucionAcademica = institucionAcademica;
      this.tituloObtenido = tituloObtenido;
      this.fechaInicio = fechaInicio;
      this.fechaFinalizacion = fechaFinalizacion;
      this.flgValidadoRRHH = flgValidadoRRHH;
      this.codNivelFormacion = codNivelFormacion;
   }

   @Override
   public long getId() {
      return idTitulo;
   }

   @Override
   public void setId(long id) {
      this.idTitulo = id;
   }

   @Override
   public String toString() {
      return null;
   }
}