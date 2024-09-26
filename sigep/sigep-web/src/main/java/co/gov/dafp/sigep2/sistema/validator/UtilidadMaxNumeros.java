/**
 * 
 */
package co.gov.dafp.sigep2.sistema.validator;

import java.math.BigDecimal;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.TipoParametro;

/**
 * @author joseviscaya
 *
 */

@ManagedBean(name = "maxNumeros")
@SessionScoped
public class UtilidadMaxNumeros {
	
	private final BigDecimal PASS_VALIDATE_MAXIMO 			= new BigDecimal(2095);
	private final BigDecimal PASS_VALIDATE_MINIMO 			= new BigDecimal(2094);
	private final BigDecimal MAX_TAMANO_NUM_CELULAR		    = new BigDecimal(2167);
	private final BigDecimal MIN_TAMANO_NUM_CELULAR		    = new BigDecimal(2200);
	private final BigDecimal MAX_TAMANO_NUM_OFICINA		    = new BigDecimal(2168);
	private final BigDecimal MIN_TAMANO_NUM_OFICINA		    = new BigDecimal(2201);
	private final BigDecimal MAX_TAMANO_NUM_EXTENSION		= new BigDecimal(2169);
	private final BigDecimal MIN_CONYUGUE					= new BigDecimal(2198);
	private final BigDecimal MAX_MAYORIA_EDAD				= new BigDecimal(2199);
	private final BigDecimal MIN_TAMANO_NUM_RESIDENCIA		= new BigDecimal(TipoParametro.MIN_TAMANO_NUM_RESIDENCIA.getValue());
	private final BigDecimal MAX_TAMANO_NUM_RESIDENCIA		= new BigDecimal(TipoParametro.MAX_TAMANO_NUM_RESIDENCIA.getValue());
	
	
	private final Integer BASE_VALIDATE_MAXIMO				= 10;
	private final Integer BASE_VALIDATE_MINIMO				= 6;
	private final Integer BASE_TAMANO_NUM_OFICINA			= 10;
	private final Integer BASE_TAMANO_NUM_OFICINA_MIN		= 7;
	private final Integer BASE_TAMANO_NUM_EXTENSION			= 10;
	private final Integer BASE_TAMANO_NUM_CELULAR			= 25;
	private final Integer BASE_TAMANO_NUM_CELULAR_MIN		= 7;
	private final Integer BASE_TAMANO_MIN_CONYUGUE			= 14;
	private final Integer BASE_MAYORIA_EDAD					= 18;
	private final Integer BASE_MIN_TAMANO_NUM_RESIDENCIA	= 1;
	private final Integer BASE_MAX_TAMANO_NUM_RESIDENCIA	= 25;

	
	private Integer maxNumCelular;
	
	private Integer minNumCelular;
	
	private Integer maxNumContrasena;
	
	private Integer minNumContrasena;
	
	private Integer maxNumOficina;
	
	private Integer minNumOficina;
	
	private Integer maxNumextension;
	
	private Integer minEdadConyugue;
	
	private Integer mayoriaEdad;
	
	private Integer minTamanoNumResidencia, maxTamanoNumResidencia;
	
	/*Gerencia pública tamaños*/
	private Integer gpReTammaxValor,gpReTammaxNumeroempleados,gpReTammaxNumerocargos,gpReTammaxLogros,
	                gpPuTammaxNombrearticulo,gpPuTammaxNombrelibro,gpPuTammaxNombrepublicacion,
	                gpEdTammaxCalificacionobtenida,gpEdTammaxCalificacionescala,
	                gpPpTammaxNombreproyecto,gpPpTammaxRoldesempenado,gpPpTammaxNombreentidad,
	                gpPcTammaxNombrecorporacion,gpPcTammaxNombrerazonsocial,gpPcTammaxNombreentidad;
	private final Integer gpReTammaxValorBase=15;
	private final Integer gpReTammaxNumeroempleadosBase=6;
	private final Integer gpReTammaxNumerocargosBase=4;
	private final Integer gpReTammaxLogrosBase=3000;
	private final Integer gpPuTammaxNombrearticuloBase=2000;
	private final Integer gpPuTammaxNombrelibroBase=2000;
	private final Integer gpPuTammaxNombrepublicacionBase=2000;
	private final Integer gpEdTammaxCalificacionobtenidaBase=5;
	private final Integer gpEdTammaxCalificacionescalaBase=100;
	private final Integer gpPpTammaxNombreproyectoBase=2000;
	private final Integer gpPpTammaxRoldesempenadoBase=2000;
	private final Integer gpPpTammaxNombreentidadBase=2000;
	private final Integer gpPcTammaxNombrecorporacionBase=2000;
	private final Integer gpPcTammaxNombrerazonsocialBase=2000;
	private final Integer gpPcTammaxNombreentidadBase=2000;
	
	/**
	 * @return the maxNumCelular
	 */
	public Integer getMaxNumCelular() {
		String num = ComunicacionServiciosSis.getParametricaporId(MAX_TAMANO_NUM_CELULAR).getValorParametro();
		if(num != null) {
			maxNumCelular = Integer.parseInt(num);
			if(maxNumCelular > BASE_TAMANO_NUM_CELULAR)
				maxNumCelular = BASE_TAMANO_NUM_CELULAR;
		}else {
			maxNumCelular = BASE_TAMANO_NUM_CELULAR;
		}
		return maxNumCelular;
	}

	/**
	 * @param maxNumCelular the maxNumCelular to set
	 */
	public void setMaxNumCelular(Integer maxNumCelular) {
		this.maxNumCelular = maxNumCelular;
	}

	/**
	 * @return the maxNumContrasena
	 */
	public Integer getMaxNumContrasena() {
		String num = ComunicacionServiciosSis.getParametricaporId(PASS_VALIDATE_MAXIMO).getValorParametro();
		if(num != null) {
			maxNumContrasena = Integer.parseInt(num);
		}else {
			maxNumContrasena = BASE_VALIDATE_MAXIMO;
		}
		return maxNumContrasena;
	}

	/**
	 * @param maxNumContrasena the maxNumContrasena to set
	 */
	public void setMaxNumContrasena(Integer maxNumContrasena) {
		this.maxNumContrasena = maxNumContrasena;
	}

	/**
	 * @return the maxNumOficinar
	 */
	public Integer getMaxNumOficina() {
		String num = ComunicacionServiciosSis.getParametricaporId(MAX_TAMANO_NUM_OFICINA).getValorParametro();
		if(num != null) {
			maxNumOficina = Integer.parseInt(num);
		}else {
			maxNumOficina = BASE_TAMANO_NUM_OFICINA;
		}
		return maxNumOficina;
	}

	/**
	 * @param maxNumOficinar the maxNumOficinar to set
	 */
	public void setMaxNumOficina(Integer maxNumOficina) {
		this.maxNumOficina = maxNumOficina;
	}

	/**
	 * @return the maxNumextension
	 */
	public Integer getMaxNumextension() {
		String num = ComunicacionServiciosSis.getParametricaporId(MAX_TAMANO_NUM_EXTENSION).getValorParametro();
		if(num != null) {
			maxNumextension  =  Integer.parseInt(num);
		}else {
			maxNumextension  = BASE_TAMANO_NUM_EXTENSION;
		}
		return maxNumextension;
	}

	/**
	 * @param maxNumextension the maxNumextension to set
	 */
	public void setMaxNumextension(Integer maxNumextension) {
		this.maxNumextension = maxNumextension;
	}

	/**
	 * @return the minEdadConyugue
	 */
	public Integer getMinEdadConyugue() {
		String num = ComunicacionServiciosSis.getParametricaporId(MIN_CONYUGUE).getValorParametro();
		if(num != null) {
			minEdadConyugue = Integer.parseInt(num);
		}else {
			minEdadConyugue = BASE_TAMANO_MIN_CONYUGUE;
		}
		return minEdadConyugue;
	}

	/**
	 * @param minEdadConyugue the minEdadConyugue to set
	 */
	public void setMinEdadConyugue(Integer minEdadConyugue) {
		this.minEdadConyugue = minEdadConyugue;
	}

	/**
	 * @return the mayoriaEdad
	 */
	public Integer getMayoriaEdad() {
		String num = ComunicacionServiciosSis.getParametricaporId(MAX_MAYORIA_EDAD).getValorParametro();
		if(num != null) {
			mayoriaEdad = Integer.parseInt(num);
		}else {
			mayoriaEdad = BASE_MAYORIA_EDAD;
		}
		return mayoriaEdad;
	}

	/**
	 * @param mayoriaEdad the mayoriaEdad to set
	 */
	public void setMayoriaEdad(Integer mayoriaEdad) {
		this.mayoriaEdad = mayoriaEdad;
	}

	/**
	 * @return the minNumCelular
	 */
	public Integer getMinNumCelular() {
		String num = ComunicacionServiciosSis.getParametricaporId(MIN_TAMANO_NUM_CELULAR).getValorParametro();
		if(num != null) {
			minNumCelular = Integer.parseInt(num);
		}else {
			minNumCelular = BASE_TAMANO_NUM_CELULAR_MIN;
		}
		return minNumCelular;
	}

	/**
	 * @param minNumCelular the minNumCelular to set
	 */
	public void setMinNumCelular(Integer minNumCelular) {
		this.minNumCelular = minNumCelular;
	}

	/**
	 * @return the minNumContrasena
	 */
	public Integer getMinNumContrasena() {
		String num = ComunicacionServiciosSis.getParametricaporId(PASS_VALIDATE_MINIMO).getValorParametro();
		if(num != null) {
			minNumContrasena = Integer.parseInt(num);
		}else {
			minNumContrasena = BASE_VALIDATE_MINIMO;
		}
		return minNumContrasena;
	}

	/**
	 * @param minNumContrasena the minNumContrasena to set
	 */
	public void setMinNumContrasena(Integer minNumContrasena) {
		this.minNumContrasena = minNumContrasena;
	}

	/**
	 * @return the minNumOficina
	 */
	public Integer getMinNumOficina() {
		String num = ComunicacionServiciosSis.getParametricaporId(MIN_TAMANO_NUM_OFICINA).getValorParametro();
		if(num != null) {
			minNumOficina = Integer.parseInt(num);
		}else {
			minNumOficina = BASE_TAMANO_NUM_OFICINA_MIN;
		}
		return minNumOficina;
	}

	/**
	 * @param minNumOficina the minNumOficina to set
	 */
	public void setMinNumOficina(Integer minNumOficina) {
		this.minNumOficina = minNumOficina;
	}

	public Integer getMinTamanoNumResidencia() {
		try {
			String num = ComunicacionServiciosSis.getParametricaporId(MIN_TAMANO_NUM_RESIDENCIA).getValorParametro();
			if(num != null) {
				minTamanoNumResidencia = Integer.parseInt(num);
				if(minTamanoNumResidencia < BASE_MIN_TAMANO_NUM_RESIDENCIA)
					minTamanoNumResidencia = BASE_MIN_TAMANO_NUM_RESIDENCIA;
			}else {
				minTamanoNumResidencia = BASE_MIN_TAMANO_NUM_RESIDENCIA;
			}
		}catch(Exception z) {
			minTamanoNumResidencia = BASE_MIN_TAMANO_NUM_RESIDENCIA;
		}
		return minTamanoNumResidencia;		
	}

	public void setMinTamanoNumResidencia(Integer minTamanoNumResidencia) {
		this.minTamanoNumResidencia = minTamanoNumResidencia;
	}

	public Integer getMaxTamanoNumResidencia() {
		try {
			String num = ComunicacionServiciosSis.getParametricaporId(MAX_TAMANO_NUM_RESIDENCIA).getValorParametro();
			if(num != null) {
				maxTamanoNumResidencia = Integer.parseInt(num);
				if(maxTamanoNumResidencia > BASE_MAX_TAMANO_NUM_RESIDENCIA)
					maxTamanoNumResidencia = BASE_MAX_TAMANO_NUM_RESIDENCIA;
			}else {
				maxTamanoNumResidencia = BASE_MAX_TAMANO_NUM_RESIDENCIA;
			}
		}catch(Exception z) {
			maxTamanoNumResidencia = BASE_MIN_TAMANO_NUM_RESIDENCIA;
		}
		return maxTamanoNumResidencia;			
	}

	public void setMaxTamanoNumResidencia(Integer maxTamanoNumResidencia) {
		this.maxTamanoNumResidencia = maxTamanoNumResidencia;
	}
	
	
	
	/*Tamaños gerencia publica*/
	public Integer getGpReTammaxValor() {
		gpReTammaxValor = getValorTamannoMaxParametro(new BigDecimal(TipoParametro.GP_RE_TAMMAX_VALOR.getValue()),gpReTammaxValorBase);
		return gpReTammaxValor;
	}
	public void setGpReTammaxValor(Integer gpReTammaxValor) {
		this.gpReTammaxValor = gpReTammaxValor;
	}

	public Integer getGpReTammaxNumeroempleados() {
		gpReTammaxNumeroempleados = getValorTamannoMaxParametro(new BigDecimal(TipoParametro.GP_RE_TAMMAX_NUMEROEMPLEADOS.getValue()),gpReTammaxNumeroempleadosBase);
		return gpReTammaxNumeroempleados;
	}

	public void setGpReTammaxNumeroempleados(Integer gpReTammaxNumeroempleados) {
		this.gpReTammaxNumeroempleados = gpReTammaxNumeroempleados;
	}

	public Integer getGpReTammaxNumerocargos() {
		gpReTammaxNumerocargos = getValorTamannoMaxParametro(new BigDecimal(TipoParametro.GP_RE_TAMMAX_NUMEROCARGOS.getValue()),gpReTammaxNumerocargosBase);
		return gpReTammaxNumerocargos;
	}

	public void setGpReTammaxNumerocargos(Integer gpReTammaxNumerocargos) {
		this.gpReTammaxNumerocargos = gpReTammaxNumerocargos;
	}

	public Integer getGpReTammaxLogros() {
		gpReTammaxLogros = getValorTamannoMaxParametro(new BigDecimal(TipoParametro.GP_RE_TAMMAX_LOGROS.getValue()),gpReTammaxLogrosBase);
		return gpReTammaxLogros;
	}

	public void setGpReTammaxLogros(Integer gpReTammaxLogros) {
		this.gpReTammaxLogros = gpReTammaxLogros;
	}

	public Integer getGpPuTammaxNombrearticulo() {
		gpPuTammaxNombrearticulo = getValorTamannoMaxParametro(new BigDecimal(TipoParametro.GP_PU_TAMMAX_NOMBREARTICULO.getValue()),gpPuTammaxNombrearticuloBase);
		return gpPuTammaxNombrearticulo;
	}

	public void setGpPuTammaxNombrearticulo(Integer gpPuTammaxNombrearticulo) {
		this.gpPuTammaxNombrearticulo = gpPuTammaxNombrearticulo;
	}

	public Integer getGpPuTammaxNombrelibro() {
		gpPuTammaxNombrelibro = getValorTamannoMaxParametro(new BigDecimal(TipoParametro.GP_PU_TAMMAX_NOMBRELIBRO.getValue()),gpPuTammaxNombrelibroBase);
		return gpPuTammaxNombrelibro;
	}

	public void setGpPuTammaxNombrelibro(Integer gpPuTammaxNombrelibro) {
		this.gpPuTammaxNombrelibro = gpPuTammaxNombrelibro;
	}

	public Integer getGpPuTammaxNombrepublicacion() {
		gpPuTammaxNombrepublicacion = getValorTamannoMaxParametro(new BigDecimal(TipoParametro.GP_PU_TAMMAX_NOMBREPUBLICACION.getValue()),gpPuTammaxNombrepublicacionBase);
		return gpPuTammaxNombrepublicacion;
	}

	public void setGpPuTammaxNombrepublicacion(Integer gpPuTammaxNombrepublicacion) {
		this.gpPuTammaxNombrepublicacion = gpPuTammaxNombrepublicacion;
	}

	public Integer getGpEdTammaxCalificacionobtenida() {
		gpEdTammaxCalificacionobtenida = getValorTamannoMaxParametro(new BigDecimal(TipoParametro.GP_ED_TAMMAX_CALIFICACIONOBTENIDA.getValue()),gpEdTammaxCalificacionobtenidaBase);
		return gpEdTammaxCalificacionobtenida;
	}

	public void setGpEdTammaxCalificacionobtenida(Integer gpEdTammaxCalificacionobtenida) {
		this.gpEdTammaxCalificacionobtenida = gpEdTammaxCalificacionobtenida;
	}

	public Integer getGpEdTammaxCalificacionescala() {
		gpEdTammaxCalificacionescala = getValorTamannoMaxParametro(new BigDecimal(TipoParametro.GP_ED_TAMMAX_CALIFICACIONESCALA.getValue()),gpEdTammaxCalificacionescalaBase);
		return gpEdTammaxCalificacionescala;
	}

	public void setGpEdTammaxCalificacionescala(Integer gpEdTammaxCalificacionescala) {
		this.gpEdTammaxCalificacionescala = gpEdTammaxCalificacionescala;
	}

	public Integer getGpPpTammaxNombreproyecto() {
		gpPpTammaxNombreproyecto = getValorTamannoMaxParametro(new BigDecimal(TipoParametro.GP_PP_TAMMAX_NOMBREPROYECTO.getValue()),gpPpTammaxNombreproyectoBase);
		return gpPpTammaxNombreproyecto;
	}

	public void setGpPpTammaxNombreproyecto(Integer gpPpTammaxNombreproyecto) {
		this.gpPpTammaxNombreproyecto = gpPpTammaxNombreproyecto;
	}

	public Integer getGpPpTammaxRoldesempenado() {
		gpPpTammaxRoldesempenado = getValorTamannoMaxParametro(new BigDecimal(TipoParametro.GP_PP_TAMMAX_ROLDESEMPENADO.getValue()),gpPpTammaxRoldesempenadoBase);
		return gpPpTammaxRoldesempenado;
	}

	public void setGpPpTammaxRoldesempenado(Integer gpPpTammaxRoldesempenado) {
		this.gpPpTammaxRoldesempenado = gpPpTammaxRoldesempenado;
	}

	public Integer getGpPpTammaxNombreentidad() {
		gpPpTammaxNombreentidad = getValorTamannoMaxParametro(new BigDecimal(TipoParametro.GP_PP_TAMMAX_NOMBREENTIDAD.getValue()),gpPpTammaxNombreentidadBase);
		return gpPpTammaxNombreentidad;
	}

	public void setGpPpTammaxNombreentidad(Integer gpPpTammaxNombreentidad) {
		this.gpPpTammaxNombreentidad = gpPpTammaxNombreentidad;
	}

	public Integer getGpPcTammaxNombrecorporacion() {
		gpPcTammaxNombrecorporacion = getValorTamannoMaxParametro(new BigDecimal(TipoParametro.GP_PC_TAMMAX_NOMBRECORPORACION.getValue()),gpPcTammaxNombrecorporacionBase);
		return gpPcTammaxNombrecorporacion;
	}

	public void setGpPcTammaxNombrecorporacion(Integer gpPcTammaxNombrecorporacion) {
		this.gpPcTammaxNombrecorporacion = gpPcTammaxNombrecorporacion;
	}

	public Integer getGpPcTammaxNombrerazonsocial() {
		gpPcTammaxNombrerazonsocial = getValorTamannoMaxParametro(new BigDecimal(TipoParametro.GP_PC_TAMMAX_NOMBRERAZONSOCIAL.getValue()),gpPcTammaxNombrerazonsocialBase);
		return gpPcTammaxNombrerazonsocial;
	}

	public void setGpPcTammaxNombrerazonsocial(Integer gpPcTammaxNombrerazonsocial) {
		this.gpPcTammaxNombrerazonsocial = gpPcTammaxNombrerazonsocial;
	}

	public Integer getGpPcTammaxNombreentidad() {
		gpPcTammaxNombreentidad = getValorTamannoMaxParametro(new BigDecimal(TipoParametro.GP_PC_TAMMAX_NOMBRE_ENTIDAD.getValue()),gpPcTammaxNombreentidadBase);
		return gpPcTammaxNombreentidad;
	}

	public void setGpPcTammaxNombreentidad(Integer gpPcTammaxNombreentidad) {
		this.gpPcTammaxNombreentidad = gpPcTammaxNombreentidad;
	}

	private Integer getValorTamannoMaxParametro( BigDecimal lbdParametro, Integer lbdBase){
		Integer retorno = null;
		try {
			String num = ComunicacionServiciosSis.getParametricaporId(lbdParametro).getValorParametro();
			if(num != null) {
				retorno = Integer.parseInt(num);
				if(retorno > lbdBase)
					retorno = lbdBase;
			}else {
				retorno = lbdBase;
			}
		}catch(Exception z) {
			retorno = lbdBase;
		}
		return retorno;
	}



}
