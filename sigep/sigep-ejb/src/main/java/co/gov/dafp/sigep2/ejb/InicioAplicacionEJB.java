package co.gov.dafp.sigep2.ejb;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.gov.dafp.sigep2.constante.MenuBundleConstants;
import co.gov.dafp.sigep2.constante.PermisoProduces;
import co.gov.dafp.sigep2.entity.jpa.comun.Parametrica;
import co.gov.dafp.sigep2.entity.jpa.seguridad.Recurso;
import co.gov.dafp.sigep2.entity.jpa.seguridad.Rol;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.entity.view.ProcesoArchivoDTO;
import co.gov.dafp.sigep2.factoria.ParametricaFactoria;
import co.gov.dafp.sigep2.factoria.TipoDocumentoFactoria;
import co.gov.dafp.sigep2.factoria.PersonaFactoria;
import co.gov.dafp.sigep2.factoria.ProcesoArchivoFactoria;
import co.gov.dafp.sigep2.factoria.RecursoFactoria;
import co.gov.dafp.sigep2.factoria.RolFactoria;

import co.gov.dafp.sigep2.factoria.UsuarioFactoria;
import co.gov.dafp.sigep2.interfaces.IInicioAplicacionRemote;
import co.gov.dafp.sigep2.util.enums.TipoDocumentoIdentidadEnum;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.logger.Logger;

@Stateless
public class InicioAplicacionEJB implements IInicioAplicacionRemote {
	private Logger logger = Logger.getInstance(InicioAplicacionEJB.class);

	@EJB
	private ParametricaFactoria parametricaFactoria;

	@EJB
	private UsuarioFactoria usuarioFactoria;

	@EJB
	private PersonaFactoria personaFactoria;

	@EJB
	private RolFactoria rolFactoria;

	@EJB
	private RecursoFactoria componenteFactoria;

	@EJB
	private TipoDocumentoFactoria tipoDocumentoFactoria;

	@EJB
	private ProcesoArchivoFactoria reporteFactoria;

	@Override
	public void startup() {
		String msg = "void startup()";
		try {
			logger.log().info(msg, "Inicio de aplicación en ejecución");
			crearParametros();
			crearRecursos();
			crearUsuarios();
			logger.log().info(msg, "Inicio de aplicación completado");
		} catch (SIGEP2SistemaException e) {
			logger.log().error(msg, "Inicio de aplicación completado con errores", e);
		}
	}

	private void crearParametros() throws SIGEP2SistemaException {
		// Cargues - ProcesoArchivo
		Parametrica cargueCrearUsuario = new Parametrica();
		cargueCrearUsuario.setNombreParametro(ProcesoArchivoDTO.CARGUE_CREAR_USUARIOS);

		Parametrica cargueActivacionUsuario = new Parametrica();
		cargueActivacionUsuario.setNombreParametro(ProcesoArchivoDTO.CARGUE_ACTIVACION_USUARIOS);

		parametricaFactoria.crearParametro(Parametrica.PAR_CARGUE_ARCHIVO, cargueCrearUsuario, cargueActivacionUsuario);

		// Gestionar Rol
		Rol rolSuperAdmin = new Rol();
		rolSuperAdmin.setNombre(RolDTO.SUPER_ADMINISTRADOR);
		Rol rolAdmin = new Rol();
		rolAdmin.setNombre(RolDTO.ADMINISTRADOR_ENTIDADES_PRIVADAS);
		Rol rolCoordinadorRegional = new Rol();
		rolCoordinadorRegional.setNombre(RolDTO.ADMINISTRADOR_FUNCIONAL);
		Rol rolResponsableAprobacionPresupuesto = new Rol();
		rolResponsableAprobacionPresupuesto.setNombre(RolDTO.ADMINISTRADOR_POLITICAS);
		Rol rolReportesCargueRecaudo = new Rol();
		rolReportesCargueRecaudo.setNombre(RolDTO.ADMINISTRADOR_TECNICO);
		Rol rolReportesCarguePresupuesto = new Rol();
		rolReportesCarguePresupuesto.setNombre(RolDTO.AUDITOR);
		Rol rolReportesCarguePriorizacion = new Rol();
		rolReportesCarguePriorizacion.setNombre(RolDTO.CONTRATISTA);
		rolFactoria.crearRoles(rolSuperAdmin, rolAdmin, rolCoordinadorRegional, rolResponsableAprobacionPresupuesto,
				rolReportesCargueRecaudo, rolReportesCarguePresupuesto, rolReportesCarguePriorizacion);
		// Tipo Documento
		Parametrica nit = new Parametrica();
		nit.setNombreParametro(TipoDocumentoIdentidadEnum.NIT.getSigla());
		Parametrica cc = new Parametrica();
		cc.setNombreParametro(TipoDocumentoIdentidadEnum.CC.getSigla());
		Parametrica ce = new Parametrica();
		ce.setNombreParametro(TipoDocumentoIdentidadEnum.CE.getSigla());
		Parametrica ti = new Parametrica();
		ti.setNombreParametro(TipoDocumentoIdentidadEnum.TI.getSigla());
		Parametrica rc = new Parametrica();
		rc.setNombreParametro(TipoDocumentoIdentidadEnum.RC.getSigla());
		Parametrica pa = new Parametrica();
		pa.setNombreParametro(TipoDocumentoIdentidadEnum.PA.getSigla());
		parametricaFactoria.crearParametro(Parametrica.PAR_TIPO_DOCUMENTO_IDENTIDAD, nit, cc, ce, ti, rc, pa);
		// Crear conceptos

	}

	private void crearUsuarios() throws SIGEP2SistemaException {
		crearUsuarioSuperAdmin();
		crearUsuarioSistema();
	}

	private void crearUsuarioSuperAdmin() throws SIGEP2SistemaException {

	}

	private void crearUsuarioSistema() throws SIGEP2SistemaException {

	}

	private void crearRecursos() throws SIGEP2SistemaException {
		// Menu Inicio
		Long orden = 0l;
		Recurso componenteInicio = new Recurso();
		componenteInicio.setDescripcion((MenuBundleConstants.MENU_INICIO));
		componenteInicio.setTituloVentana((MenuBundleConstants.MENU_INICIO));
		componenteInicio.setCodigoVentana(PermisoProduces.MENU_INICIO);
		componenteInicio.setCodigoRecursoPadre(componenteInicio);
		componenteInicio.setOrden(orden++);
		componenteInicio.setIconoMenu("ui-icon-home");
		componenteInicio.setFlgBloqueado(Boolean.FALSE);
		this.componenteFactoria.persist(componenteInicio, null);

		// Menu recaudo
		Recurso componenteRecaudo = new Recurso();
		componenteRecaudo.setDescripcion((MenuBundleConstants.MENU_INICIO_RECAUDO));
		componenteRecaudo.setTituloVentana((MenuBundleConstants.MENU_INICIO_RECAUDO));
		componenteRecaudo.setCodigoVentana(PermisoProduces.MENU_INICIO_RECAUDO);
		componenteRecaudo.setCodigoRecursoPadre(componenteInicio);
		componenteRecaudo.setOrden(orden++);
		componenteRecaudo.setIconoMenu("ui-icon-bookmark");
		componenteRecaudo.setFlgBloqueado(Boolean.FALSE);
		this.componenteFactoria.crearRecurso(componenteRecaudo);
	}
}
