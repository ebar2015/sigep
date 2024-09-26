package co.gov.dafp.sigep2.bean;

import java.math.BigDecimal;
import java.util.Date;

import co.gov.dafp.sigep2.utils.ErrorMensajes;

/**
 * @author Desarolladora Junior
 *
 */
public class HistoricoModificacionHojaVida extends ErrorMensajes{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1758865867897378580L;

	/**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HISTORICO_MODIFICACION_HOJA_VIDA.COD_MODIFICACION
     *
     * @mbg.generated Thu May 03 15:37:02 COT 2018
     */
    private Long codModificacion;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HISTORICO_MODIFICACION_HOJA_VIDA.COD_HOJA_VIDA
     *
     * @mbg.generated Thu May 03 15:37:02 COT 2018
     */
    private BigDecimal codHojaVida;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HISTORICO_MODIFICACION_HOJA_VIDA.JUSTIFICACION_MODIFICACION
     *
     * @mbg.generated Thu May 03 15:37:02 COT 2018
     */
    private String justificacionModificacion;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HISTORICO_MODIFICACION_HOJA_VIDA.AUD_FECHA_MODIFICACION
     *
     * @mbg.generated Thu May 03 15:37:02 COT 2018
     */
    private Date audFechaModificacion;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HISTORICO_MODIFICACION_HOJA_VIDA.AUD_COD_USUARIO
     *
     * @mbg.generated Thu May 03 15:37:02 COT 2018
     */
    private BigDecimal audCodUsuario;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HISTORICO_MODIFICACION_HOJA_VIDA.AUD_COD_ROL
     *
     * @mbg.generated Thu May 03 15:37:02 COT 2018
     */
    private Integer audCodRol;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HISTORICO_MODIFICACION_HOJA_VIDA.AUD_ACCION
     *
     * @mbg.generated Thu May 03 15:37:02 COT 2018
     */
    private Integer audAccion;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HISTORICO_MODIFICACION_HOJA_VIDA.IMAGEN_HOJA_VIDA_URL
     *
     * @mbg.generated Thu May 03 15:37:02 COT 2018
     */
    private String imagenHojaVidaUrl;
    
	private Short flgActivo;
	
	private String urlPdf;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HISTORICO_MODIFICACION_HOJA_VIDA.COD_MODIFICACION
     *
     * @return the value of HISTORICO_MODIFICACION_HOJA_VIDA.COD_MODIFICACION
     *
     * @mbg.generated Thu May 03 15:37:02 COT 2018
     */
    public Long getCodModificacion() {
        return codModificacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HISTORICO_MODIFICACION_HOJA_VIDA.COD_MODIFICACION
     *
     * @param codModificacion the value for HISTORICO_MODIFICACION_HOJA_VIDA.COD_MODIFICACION
     *
     * @mbg.generated Thu May 03 15:37:02 COT 2018
     */
    public void setCodModificacion(Long codModificacion) {
        this.codModificacion = codModificacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HISTORICO_MODIFICACION_HOJA_VIDA.COD_HOJA_VIDA
     *
     * @return the value of HISTORICO_MODIFICACION_HOJA_VIDA.COD_HOJA_VIDA
     *
     * @mbg.generated Thu May 03 15:37:02 COT 2018
     */
    public BigDecimal getCodHojaVida() {
        return codHojaVida;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HISTORICO_MODIFICACION_HOJA_VIDA.COD_HOJA_VIDA
     *
     * @param codHojaVida the value for HISTORICO_MODIFICACION_HOJA_VIDA.COD_HOJA_VIDA
     *
     * @mbg.generated Thu May 03 15:37:02 COT 2018
     */
    public void setCodHojaVida(BigDecimal codHojaVida) {
        this.codHojaVida = codHojaVida;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HISTORICO_MODIFICACION_HOJA_VIDA.JUSTIFICACION_MODIFICACION
     *
     * @return the value of HISTORICO_MODIFICACION_HOJA_VIDA.JUSTIFICACION_MODIFICACION
     *
     * @mbg.generated Thu May 03 15:37:02 COT 2018
     */
    public String getJustificacionModificacion() {
        return justificacionModificacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HISTORICO_MODIFICACION_HOJA_VIDA.JUSTIFICACION_MODIFICACION
     *
     * @param justificacionModificacion the value for HISTORICO_MODIFICACION_HOJA_VIDA.JUSTIFICACION_MODIFICACION
     *
     * @mbg.generated Thu May 03 15:37:02 COT 2018
     */
    public void setJustificacionModificacion(String justificacionModificacion) {
        this.justificacionModificacion = justificacionModificacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HISTORICO_MODIFICACION_HOJA_VIDA.AUD_FECHA_MODIFICACION
     *
     * @return the value of HISTORICO_MODIFICACION_HOJA_VIDA.AUD_FECHA_MODIFICACION
     *
     * @mbg.generated Thu May 03 15:37:02 COT 2018
     */
    public Date getAudFechaModificacion() {
        return audFechaModificacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HISTORICO_MODIFICACION_HOJA_VIDA.AUD_FECHA_MODIFICACION
     *
     * @param audFechaModificacion the value for HISTORICO_MODIFICACION_HOJA_VIDA.AUD_FECHA_MODIFICACION
     *
     * @mbg.generated Thu May 03 15:37:02 COT 2018
     */
    public void setAudFechaModificacion(Date audFechaModificacion) {
        this.audFechaModificacion = audFechaModificacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HISTORICO_MODIFICACION_HOJA_VIDA.AUD_COD_USUARIO
     *
     * @return the value of HISTORICO_MODIFICACION_HOJA_VIDA.AUD_COD_USUARIO
     *
     * @mbg.generated Thu May 03 15:37:02 COT 2018
     */
    public BigDecimal getAudCodUsuario() {
        return audCodUsuario;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HISTORICO_MODIFICACION_HOJA_VIDA.AUD_COD_USUARIO
     *
     * @param audCodUsuario the value for HISTORICO_MODIFICACION_HOJA_VIDA.AUD_COD_USUARIO
     *
     * @mbg.generated Thu May 03 15:37:02 COT 2018
     */
    public void setAudCodUsuario(BigDecimal audCodUsuario) {
        this.audCodUsuario = audCodUsuario;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HISTORICO_MODIFICACION_HOJA_VIDA.AUD_COD_ROL
     *
     * @return the value of HISTORICO_MODIFICACION_HOJA_VIDA.AUD_COD_ROL
     *
     * @mbg.generated Thu May 03 15:37:02 COT 2018
     */
    public Integer getAudCodRol() {
        return audCodRol;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HISTORICO_MODIFICACION_HOJA_VIDA.AUD_COD_ROL
     *
     * @param audCodRol the value for HISTORICO_MODIFICACION_HOJA_VIDA.AUD_COD_ROL
     *
     * @mbg.generated Thu May 03 15:37:02 COT 2018
     */
    public void setAudCodRol(Integer audCodRol) {
        this.audCodRol = audCodRol;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HISTORICO_MODIFICACION_HOJA_VIDA.AUD_ACCION
     *
     * @return the value of HISTORICO_MODIFICACION_HOJA_VIDA.AUD_ACCION
     *
     * @mbg.generated Thu May 03 15:37:02 COT 2018
     */
    public Integer getAudAccion() {
        return audAccion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HISTORICO_MODIFICACION_HOJA_VIDA.AUD_ACCION
     *
     * @param audAccion the value for HISTORICO_MODIFICACION_HOJA_VIDA.AUD_ACCION
     *
     * @mbg.generated Thu May 03 15:37:02 COT 2018
     */
    public void setAudAccion(Integer audAccion) {
        this.audAccion = audAccion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HISTORICO_MODIFICACION_HOJA_VIDA.IMAGEN_HOJA_VIDA_URL
     *
     * @return the value of HISTORICO_MODIFICACION_HOJA_VIDA.IMAGEN_HOJA_VIDA_URL
     *
     * @mbg.generated Thu May 03 15:37:02 COT 2018
     */
    public String getImagenHojaVidaUrl() {
        return imagenHojaVidaUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HISTORICO_MODIFICACION_HOJA_VIDA.IMAGEN_HOJA_VIDA_URL
     *
     * @param imagenHojaVidaUrl the value for HISTORICO_MODIFICACION_HOJA_VIDA.IMAGEN_HOJA_VIDA_URL
     *
     * @mbg.generated Thu May 03 15:37:02 COT 2018
     */
    public void setImagenHojaVidaUrl(String imagenHojaVidaUrl) {
        this.imagenHojaVidaUrl = imagenHojaVidaUrl;
    }

	/**
	 * @return the flgActivo
	 */
	public Short getFlgActivo() {
		return flgActivo;
	}

	/**
	 * @param flgActivo the flgActivo to set
	 */
	public void setFlgActivo(Short flgActivo) {
		this.flgActivo = flgActivo;
	}

	/**
	 * @return the urlPdf
	 */
	public String getUrlPdf() {
		return urlPdf;
	}

	/**
	 * @param urlPdf the urlPdf to set
	 */
	public void setUrlPdf(String urlPdf) {
		this.urlPdf = urlPdf;
	}
	
    
}