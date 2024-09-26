package co.gov.dafp.sigep2.entities;

import java.util.Date;

import co.gov.dafp.sigep2.utils.ErrorMensajes;

public class CmHvEducacionFormal extends ErrorMensajes{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1723264970742438565L;

	/**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_FORMAL.COD_CM_HV_EDUCACION_FORMAL
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private Long codCmHvEducacionFormal;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_FORMAL.COD_TIPO_IDENTIFICACION
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private String codTipoIdentificacion;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_FORMAL.NOMBRE_INSTITUCION
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private String nombreInstitucion;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_FORMAL.TITULO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private String titulo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_FORMAL.PAIS
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private String pais;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_FORMAL.FECHA_INICIO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private String fechaInicio;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_FORMAL.FECHA_FIN
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private String fechaFin;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_FORMAL.TITULO_VERIFICADO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private String tituloVerificado;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_FORMAL.NIVEL_EDUCATIVO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private String nivelEducativo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_FORMAL.NIVEL_FORMACION
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private String nivelFormacion;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_FORMAL.AREA_CONOCIMIENTO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private String areaConocimiento;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_FORMAL.INSTITUCION_EDUCATIVA
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private String institucionEducativa;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_FORMAL.PROGRAMA_ACADEMICO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private String programaAcademico;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_FORMAL.TITULO_OBTENIDO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private String tituloObtenido;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_FORMAL.SEMESTRES_APROBADOS
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private Short semestresAprobados;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_FORMAL.ESTADO_ESTUDIO_EN
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private String estadoEstudioEn;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_FORMAL.ESTADO_ESTUDIO_FINALIZADO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private String estadoEstudioFinalizado;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_FORMAL.FECHA_TERMINACION_MATERIAS
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private String fechaTerminacionMaterias;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_FORMAL.FECHA_GRADO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private String fechaGrado;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_FORMAL.ESTUDIO_EN_EXTERIOR
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private String estudioEnExterior;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_FORMAL.ESTUDIO_COVALIDADO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private String estudioCovalidado;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_FORMAL.FECHA_COVALIDACION
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private String fechaCovalidacion;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_FORMAL.FECHA_CONVALIDACION
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private String fechaConvalidacion;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_FORMAL.NUM_TARJETA_PROFESIONAL
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private String numTarjetaProfesional;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_FORMAL.SOPORTE_TARJETA_PROFESIONAL
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private String soporteTarjetaProfesional;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_FORMAL.DOCUMENTO_VERIFICADO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private String documentoVerificado;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_FORMAL.AUD_FECHA_ACTUALIZACION
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private Date audFechaActualizacion;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_FORMAL.AUD_COD_USUARIO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private Long audCodUsuario;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_FORMAL.AUD_COD_ROL
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private Integer audCodRol;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_FORMAL.AUD_ACCION
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private Integer audAccion;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_FORMAL.COD_PROCESO_CARGA_MASIVA
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private Short codProcesoCargaMasiva;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_FORMAL.RESULTADO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private String resultado;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_FORMAL.NUMERO_IDENTIFICACION
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private String numeroIdentificacion;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_FORMAL.COD_CM_HV_EDUCACION_FORMAL
     *
     * @return the value of CM_HV_EDUCACION_FORMAL.COD_CM_HV_EDUCACION_FORMAL
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public Long getCodCmHvEducacionFormal() {
        return codCmHvEducacionFormal;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_FORMAL.COD_CM_HV_EDUCACION_FORMAL
     *
     * @param codCmHvEducacionFormal the value for CM_HV_EDUCACION_FORMAL.COD_CM_HV_EDUCACION_FORMAL
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setCodCmHvEducacionFormal(Long codCmHvEducacionFormal) {
        this.codCmHvEducacionFormal = codCmHvEducacionFormal;
    }

    /**
	 * @return the codTipoIdentificacion
	 */
	public String getCodTipoIdentificacion() {
		return codTipoIdentificacion;
	}

	/**
	 * @param codTipoIdentificacion the codTipoIdentificacion to set
	 */
	public void setCodTipoIdentificacion(String codTipoIdentificacion) {
		this.codTipoIdentificacion = codTipoIdentificacion;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_FORMAL.NOMBRE_INSTITUCION
     *
     * @return the value of CM_HV_EDUCACION_FORMAL.NOMBRE_INSTITUCION
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public String getNombreInstitucion() {
        return nombreInstitucion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_FORMAL.NOMBRE_INSTITUCION
     *
     * @param nombreInstitucion the value for CM_HV_EDUCACION_FORMAL.NOMBRE_INSTITUCION
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setNombreInstitucion(String nombreInstitucion) {
        this.nombreInstitucion = nombreInstitucion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_FORMAL.TITULO
     *
     * @return the value of CM_HV_EDUCACION_FORMAL.TITULO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_FORMAL.TITULO
     *
     * @param titulo the value for CM_HV_EDUCACION_FORMAL.TITULO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_FORMAL.PAIS
     *
     * @return the value of CM_HV_EDUCACION_FORMAL.PAIS
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public String getPais() {
        return pais;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_FORMAL.PAIS
     *
     * @param pais the value for CM_HV_EDUCACION_FORMAL.PAIS
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setPais(String pais) {
        this.pais = pais;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_FORMAL.FECHA_INICIO
     *
     * @return the value of CM_HV_EDUCACION_FORMAL.FECHA_INICIO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public String getFechaInicio() {
        return fechaInicio;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_FORMAL.FECHA_INICIO
     *
     * @param fechaInicio the value for CM_HV_EDUCACION_FORMAL.FECHA_INICIO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_FORMAL.FECHA_FIN
     *
     * @return the value of CM_HV_EDUCACION_FORMAL.FECHA_FIN
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public String getFechaFin() {
        return fechaFin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_FORMAL.FECHA_FIN
     *
     * @param fechaFin the value for CM_HV_EDUCACION_FORMAL.FECHA_FIN
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_FORMAL.TITULO_VERIFICADO
     *
     * @return the value of CM_HV_EDUCACION_FORMAL.TITULO_VERIFICADO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public String getTituloVerificado() {
        return tituloVerificado;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_FORMAL.TITULO_VERIFICADO
     *
     * @param tituloVerificado the value for CM_HV_EDUCACION_FORMAL.TITULO_VERIFICADO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setTituloVerificado(String tituloVerificado) {
        this.tituloVerificado = tituloVerificado;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_FORMAL.NIVEL_EDUCATIVO
     *
     * @return the value of CM_HV_EDUCACION_FORMAL.NIVEL_EDUCATIVO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public String getNivelEducativo() {
        return nivelEducativo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_FORMAL.NIVEL_EDUCATIVO
     *
     * @param nivelEducativo the value for CM_HV_EDUCACION_FORMAL.NIVEL_EDUCATIVO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setNivelEducativo(String nivelEducativo) {
        this.nivelEducativo = nivelEducativo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_FORMAL.NIVEL_FORMACION
     *
     * @return the value of CM_HV_EDUCACION_FORMAL.NIVEL_FORMACION
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public String getNivelFormacion() {
        return nivelFormacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_FORMAL.NIVEL_FORMACION
     *
     * @param nivelFormacion the value for CM_HV_EDUCACION_FORMAL.NIVEL_FORMACION
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setNivelFormacion(String nivelFormacion) {
        this.nivelFormacion = nivelFormacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_FORMAL.AREA_CONOCIMIENTO
     *
     * @return the value of CM_HV_EDUCACION_FORMAL.AREA_CONOCIMIENTO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public String getAreaConocimiento() {
        return areaConocimiento;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_FORMAL.AREA_CONOCIMIENTO
     *
     * @param areaConocimiento the value for CM_HV_EDUCACION_FORMAL.AREA_CONOCIMIENTO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setAreaConocimiento(String areaConocimiento) {
        this.areaConocimiento = areaConocimiento;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_FORMAL.INSTITUCION_EDUCATIVA
     *
     * @return the value of CM_HV_EDUCACION_FORMAL.INSTITUCION_EDUCATIVA
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public String getInstitucionEducativa() {
        return institucionEducativa;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_FORMAL.INSTITUCION_EDUCATIVA
     *
     * @param institucionEducativa the value for CM_HV_EDUCACION_FORMAL.INSTITUCION_EDUCATIVA
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setInstitucionEducativa(String institucionEducativa) {
        this.institucionEducativa = institucionEducativa;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_FORMAL.PROGRAMA_ACADEMICO
     *
     * @return the value of CM_HV_EDUCACION_FORMAL.PROGRAMA_ACADEMICO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public String getProgramaAcademico() {
        return programaAcademico;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_FORMAL.PROGRAMA_ACADEMICO
     *
     * @param programaAcademico the value for CM_HV_EDUCACION_FORMAL.PROGRAMA_ACADEMICO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setProgramaAcademico(String programaAcademico) {
        this.programaAcademico = programaAcademico;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_FORMAL.TITULO_OBTENIDO
     *
     * @return the value of CM_HV_EDUCACION_FORMAL.TITULO_OBTENIDO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public String getTituloObtenido() {
        return tituloObtenido;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_FORMAL.TITULO_OBTENIDO
     *
     * @param tituloObtenido the value for CM_HV_EDUCACION_FORMAL.TITULO_OBTENIDO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setTituloObtenido(String tituloObtenido) {
        this.tituloObtenido = tituloObtenido;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_FORMAL.SEMESTRES_APROBADOS
     *
     * @return the value of CM_HV_EDUCACION_FORMAL.SEMESTRES_APROBADOS
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public Short getSemestresAprobados() {
        return semestresAprobados;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_FORMAL.SEMESTRES_APROBADOS
     *
     * @param semestresAprobados the value for CM_HV_EDUCACION_FORMAL.SEMESTRES_APROBADOS
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setSemestresAprobados(Short semestresAprobados) {
        this.semestresAprobados = semestresAprobados;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_FORMAL.ESTADO_ESTUDIO_EN
     *
     * @return the value of CM_HV_EDUCACION_FORMAL.ESTADO_ESTUDIO_EN
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public String getEstadoEstudioEn() {
        return estadoEstudioEn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_FORMAL.ESTADO_ESTUDIO_EN
     *
     * @param estadoEstudioEn the value for CM_HV_EDUCACION_FORMAL.ESTADO_ESTUDIO_EN
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setEstadoEstudioEn(String estadoEstudioEn) {
        this.estadoEstudioEn = estadoEstudioEn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_FORMAL.ESTADO_ESTUDIO_FINALIZADO
     *
     * @return the value of CM_HV_EDUCACION_FORMAL.ESTADO_ESTUDIO_FINALIZADO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public String getEstadoEstudioFinalizado() {
        return estadoEstudioFinalizado;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_FORMAL.ESTADO_ESTUDIO_FINALIZADO
     *
     * @param estadoEstudioFinalizado the value for CM_HV_EDUCACION_FORMAL.ESTADO_ESTUDIO_FINALIZADO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setEstadoEstudioFinalizado(String estadoEstudioFinalizado) {
        this.estadoEstudioFinalizado = estadoEstudioFinalizado;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_FORMAL.FECHA_TERMINACION_MATERIAS
     *
     * @return the value of CM_HV_EDUCACION_FORMAL.FECHA_TERMINACION_MATERIAS
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public String getFechaTerminacionMaterias() {
        return fechaTerminacionMaterias;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_FORMAL.FECHA_TERMINACION_MATERIAS
     *
     * @param fechaTerminacionMaterias the value for CM_HV_EDUCACION_FORMAL.FECHA_TERMINACION_MATERIAS
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setFechaTerminacionMaterias(String fechaTerminacionMaterias) {
        this.fechaTerminacionMaterias = fechaTerminacionMaterias;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_FORMAL.FECHA_GRADO
     *
     * @return the value of CM_HV_EDUCACION_FORMAL.FECHA_GRADO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public String getFechaGrado() {
        return fechaGrado;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_FORMAL.FECHA_GRADO
     *
     * @param fechaGrado the value for CM_HV_EDUCACION_FORMAL.FECHA_GRADO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setFechaGrado(String fechaGrado) {
        this.fechaGrado = fechaGrado;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_FORMAL.ESTUDIO_EN_EXTERIOR
     *
     * @return the value of CM_HV_EDUCACION_FORMAL.ESTUDIO_EN_EXTERIOR
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public String getEstudioEnExterior() {
        return estudioEnExterior;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_FORMAL.ESTUDIO_EN_EXTERIOR
     *
     * @param estudioEnExterior the value for CM_HV_EDUCACION_FORMAL.ESTUDIO_EN_EXTERIOR
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setEstudioEnExterior(String estudioEnExterior) {
        this.estudioEnExterior = estudioEnExterior;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_FORMAL.ESTUDIO_COVALIDADO
     *
     * @return the value of CM_HV_EDUCACION_FORMAL.ESTUDIO_COVALIDADO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public String getEstudioCovalidado() {
        return estudioCovalidado;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_FORMAL.ESTUDIO_COVALIDADO
     *
     * @param estudioCovalidado the value for CM_HV_EDUCACION_FORMAL.ESTUDIO_COVALIDADO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setEstudioCovalidado(String estudioCovalidado) {
        this.estudioCovalidado = estudioCovalidado;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_FORMAL.FECHA_COVALIDACION
     *
     * @return the value of CM_HV_EDUCACION_FORMAL.FECHA_COVALIDACION
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public String getFechaCovalidacion() {
        return fechaCovalidacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_FORMAL.FECHA_COVALIDACION
     *
     * @param fechaCovalidacion the value for CM_HV_EDUCACION_FORMAL.FECHA_COVALIDACION
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setFechaCovalidacion(String fechaCovalidacion) {
        this.fechaCovalidacion = fechaCovalidacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_FORMAL.FECHA_CONVALIDACION
     *
     * @return the value of CM_HV_EDUCACION_FORMAL.FECHA_CONVALIDACION
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public String getFechaConvalidacion() {
        return fechaConvalidacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_FORMAL.FECHA_CONVALIDACION
     *
     * @param fechaConvalidacion the value for CM_HV_EDUCACION_FORMAL.FECHA_CONVALIDACION
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setFechaConvalidacion(String fechaConvalidacion) {
        this.fechaConvalidacion = fechaConvalidacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_FORMAL.NUM_TARJETA_PROFESIONAL
     *
     * @return the value of CM_HV_EDUCACION_FORMAL.NUM_TARJETA_PROFESIONAL
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public String getNumTarjetaProfesional() {
        return numTarjetaProfesional;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_FORMAL.NUM_TARJETA_PROFESIONAL
     *
     * @param numTarjetaProfesional the value for CM_HV_EDUCACION_FORMAL.NUM_TARJETA_PROFESIONAL
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setNumTarjetaProfesional(String numTarjetaProfesional) {
        this.numTarjetaProfesional = numTarjetaProfesional;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_FORMAL.SOPORTE_TARJETA_PROFESIONAL
     *
     * @return the value of CM_HV_EDUCACION_FORMAL.SOPORTE_TARJETA_PROFESIONAL
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public String getSoporteTarjetaProfesional() {
        return soporteTarjetaProfesional;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_FORMAL.SOPORTE_TARJETA_PROFESIONAL
     *
     * @param soporteTarjetaProfesional the value for CM_HV_EDUCACION_FORMAL.SOPORTE_TARJETA_PROFESIONAL
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setSoporteTarjetaProfesional(String soporteTarjetaProfesional) {
        this.soporteTarjetaProfesional = soporteTarjetaProfesional;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_FORMAL.DOCUMENTO_VERIFICADO
     *
     * @return the value of CM_HV_EDUCACION_FORMAL.DOCUMENTO_VERIFICADO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public String getDocumentoVerificado() {
        return documentoVerificado;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_FORMAL.DOCUMENTO_VERIFICADO
     *
     * @param documentoVerificado the value for CM_HV_EDUCACION_FORMAL.DOCUMENTO_VERIFICADO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setDocumentoVerificado(String documentoVerificado) {
        this.documentoVerificado = documentoVerificado;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_FORMAL.AUD_FECHA_ACTUALIZACION
     *
     * @return the value of CM_HV_EDUCACION_FORMAL.AUD_FECHA_ACTUALIZACION
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public Date getAudFechaActualizacion() {
        return audFechaActualizacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_FORMAL.AUD_FECHA_ACTUALIZACION
     *
     * @param audFechaActualizacion the value for CM_HV_EDUCACION_FORMAL.AUD_FECHA_ACTUALIZACION
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setAudFechaActualizacion(Date audFechaActualizacion) {
        this.audFechaActualizacion = audFechaActualizacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_FORMAL.AUD_COD_USUARIO
     *
     * @return the value of CM_HV_EDUCACION_FORMAL.AUD_COD_USUARIO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public Long getAudCodUsuario() {
        return audCodUsuario;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_FORMAL.AUD_COD_USUARIO
     *
     * @param audCodUsuario the value for CM_HV_EDUCACION_FORMAL.AUD_COD_USUARIO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setAudCodUsuario(Long audCodUsuario) {
        this.audCodUsuario = audCodUsuario;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_FORMAL.AUD_COD_ROL
     *
     * @return the value of CM_HV_EDUCACION_FORMAL.AUD_COD_ROL
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public Integer getAudCodRol() {
        return audCodRol;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_FORMAL.AUD_COD_ROL
     *
     * @param audCodRol the value for CM_HV_EDUCACION_FORMAL.AUD_COD_ROL
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setAudCodRol(Integer audCodRol) {
        this.audCodRol = audCodRol;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_FORMAL.AUD_ACCION
     *
     * @return the value of CM_HV_EDUCACION_FORMAL.AUD_ACCION
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public Integer getAudAccion() {
        return audAccion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_FORMAL.AUD_ACCION
     *
     * @param audAccion the value for CM_HV_EDUCACION_FORMAL.AUD_ACCION
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setAudAccion(Integer audAccion) {
        this.audAccion = audAccion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_FORMAL.COD_PROCESO_CARGA_MASIVA
     *
     * @return the value of CM_HV_EDUCACION_FORMAL.COD_PROCESO_CARGA_MASIVA
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public Short getCodProcesoCargaMasiva() {
        return codProcesoCargaMasiva;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_FORMAL.COD_PROCESO_CARGA_MASIVA
     *
     * @param codProcesoCargaMasiva the value for CM_HV_EDUCACION_FORMAL.COD_PROCESO_CARGA_MASIVA
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setCodProcesoCargaMasiva(Short codProcesoCargaMasiva) {
        this.codProcesoCargaMasiva = codProcesoCargaMasiva;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_FORMAL.RESULTADO
     *
     * @return the value of CM_HV_EDUCACION_FORMAL.RESULTADO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public String getResultado() {
        return resultado;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_FORMAL.RESULTADO
     *
     * @param resultado the value for CM_HV_EDUCACION_FORMAL.RESULTADO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_FORMAL.NUMERO_IDENTIFICACION
     *
     * @return the value of CM_HV_EDUCACION_FORMAL.NUMERO_IDENTIFICACION
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_FORMAL.NUMERO_IDENTIFICACION
     *
     * @param numeroIdentificacion the value for CM_HV_EDUCACION_FORMAL.NUMERO_IDENTIFICACION
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }
}