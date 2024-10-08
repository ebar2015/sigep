package co.gov.dafp.sigep2.bean;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import co.gov.dafp.sigep2.utils.ErrorMensajes;

@XmlRootElement
@XmlType (propOrder = { "codIdioma", "nombreIdioma","flgActivo","audFechaActualizacion","audCodUsuario", "audCodRol","audAccion"},
          namespace = "http://www.sigepII/idioma")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Idioma extends ErrorMensajes{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2552209628965323808L;

	/**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column IDIOMA.COD_IDIOMA
     *
     * @mbg.generated Fri Feb 02 16:56:24 COT 2018
     */
    private Integer codIdioma;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column IDIOMA.NOMBRE_IDIOMA
     *
     * @mbg.generated Fri Feb 02 16:56:24 COT 2018
     */
    private String nombreIdioma;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column IDIOMA.FLG_ACTIVO
     *
     * @mbg.generated Fri Feb 02 16:56:24 COT 2018
     */
    private Short flgActivo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column IDIOMA.AUD_FECHA_ACTUALIZACION
     *
     * @mbg.generated Fri Feb 02 16:56:24 COT 2018
     */
    private Date audFechaActualizacion;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column IDIOMA.AUD_COD_USUARIO
     *
     * @mbg.generated Fri Feb 02 16:56:24 COT 2018
     */
    private BigDecimal audCodUsuario;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column IDIOMA.AUD_COD_ROL
     *
     * @mbg.generated Fri Feb 02 16:56:24 COT 2018
     */
    private Integer audCodRol;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column IDIOMA.AUD_ACCION
     *
     * @mbg.generated Fri Feb 02 16:56:24 COT 2018
     */
    private Integer audAccion;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IDIOMA.COD_IDIOMA
     *
     * @return the value of IDIOMA.COD_IDIOMA
     *
     * @mbg.generated Fri Feb 02 16:56:24 COT 2018
     */
    @XmlElement
    public Integer getCodIdioma() {
        return codIdioma;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IDIOMA.COD_IDIOMA
     *
     * @param codIdioma the value for IDIOMA.COD_IDIOMA
     *
     * @mbg.generated Fri Feb 02 16:56:24 COT 2018
     */
    public void setCodIdioma(Integer codIdioma) {
        this.codIdioma = codIdioma;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IDIOMA.NOMBRE_IDIOMA
     *
     * @return the value of IDIOMA.NOMBRE_IDIOMA
     *
     * @mbg.generated Fri Feb 02 16:56:24 COT 2018
     */
    @XmlElement
    public String getNombreIdioma() {
        return nombreIdioma;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IDIOMA.NOMBRE_IDIOMA
     *
     * @param nombreIdioma the value for IDIOMA.NOMBRE_IDIOMA
     *
     * @mbg.generated Fri Feb 02 16:56:24 COT 2018
     */
    public void setNombreIdioma(String nombreIdioma) {
        this.nombreIdioma = nombreIdioma;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IDIOMA.FLG_ACTIVO
     *
     * @return the value of IDIOMA.FLG_ACTIVO
     *
     * @mbg.generated Fri Feb 02 16:56:24 COT 2018
     */
    @XmlElement
    public Short getFlgActivo() {
        return flgActivo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IDIOMA.FLG_ACTIVO
     *
     * @param flgActivo the value for IDIOMA.FLG_ACTIVO
     *
     * @mbg.generated Fri Feb 02 16:56:24 COT 2018
     */
    public void setFlgActivo(Short flgActivo) {
        this.flgActivo = flgActivo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IDIOMA.AUD_FECHA_ACTUALIZACION
     *
     * @return the value of IDIOMA.AUD_FECHA_ACTUALIZACION
     *
     * @mbg.generated Fri Feb 02 16:56:24 COT 2018
     */
    @XmlElement
    public Date getAudFechaActualizacion() {
        return audFechaActualizacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IDIOMA.AUD_FECHA_ACTUALIZACION
     *
     * @param audFechaActualizacion the value for IDIOMA.AUD_FECHA_ACTUALIZACION
     *
     * @mbg.generated Fri Feb 02 16:56:24 COT 2018
     */
    public void setAudFechaActualizacion(Date audFechaActualizacion) {
        this.audFechaActualizacion = audFechaActualizacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IDIOMA.AUD_COD_USUARIO
     *
     * @return the value of IDIOMA.AUD_COD_USUARIO
     *
     * @mbg.generated Fri Feb 02 16:56:24 COT 2018
     */
    @XmlElement
    public BigDecimal getAudCodUsuario() {
        return audCodUsuario;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IDIOMA.AUD_COD_USUARIO
     *
     * @param audCodUsuario the value for IDIOMA.AUD_COD_USUARIO
     *
     * @mbg.generated Fri Feb 02 16:56:24 COT 2018
     */
    public void setAudCodUsuario(BigDecimal audCodUsuario) {
        this.audCodUsuario = audCodUsuario;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IDIOMA.AUD_COD_ROL
     *
     * @return the value of IDIOMA.AUD_COD_ROL
     *
     * @mbg.generated Fri Feb 02 16:56:24 COT 2018
     */
    @XmlElement
    public Integer getAudCodRol() {
        return audCodRol;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IDIOMA.AUD_COD_ROL
     *
     * @param audCodRol the value for IDIOMA.AUD_COD_ROL
     *
     * @mbg.generated Fri Feb 02 16:56:24 COT 2018
     */
    public void setAudCodRol(Integer audCodRol) {
        this.audCodRol = audCodRol;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IDIOMA.AUD_ACCION
     *
     * @return the value of IDIOMA.AUD_ACCION
     *
     * @mbg.generated Fri Feb 02 16:56:24 COT 2018
     */
    @XmlElement
    public Integer getAudAccion() {
        return audAccion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IDIOMA.AUD_ACCION
     *
     * @param audAccion the value for IDIOMA.AUD_ACCION
     *
     * @mbg.generated Fri Feb 02 16:56:24 COT 2018
     */
    public void setAudAccion(Integer audAccion) {
        this.audAccion = audAccion;
    }
}