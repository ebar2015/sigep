package co.gov.dafp.sigep2.constante;

public class PermisoProduces {
	/**
	 * Accion crear para el recurso seleccionado
	 */
	public static final int ACCION_CREAR = 1;
	/**
	 * Accion editarRegistro para el recurso seleccionado
	 */
	public static final int ACCION_EDITAR = 2;
	/**
	 * Accion eliminarRegistro para el recurso seleccionado
	 */
	public static final int ACCION_ELIMINAR = 3;
	/**
	 * Accion eliminarRegistro para el recurso seleccionado
	 */
	public static final int ACCION_IMPRIMIR = 4;
	/**
	 * Accion eliminarRegistro para el recurso seleccionado
	 */
	public static final int ACCION_ADJUNTAR 	= 5;
	public static final String PERMISO_CREATE 	= "create";
	public static final String PERMISO_READ 	= "read";
	public static final String PERMISO_UPDATE 	= "update";
	public static final String PERMISO_DELETE 	= "delete";

	public static final String MENU_INICIO 															= "InicioMenu";
	public static final String MENU_INICIO_RECAUDO 													= "RecaudoMenu";
	public static final String SUBMENU_RECAUDO_CARGUE_ARCHIVO 										= "RecaudoCargueArchivoTab";
	public static final String SUBMENU_RECAUDO_MULTAS 												= "RecaudoMultasSubMenu";
	public static final String SUBMENU_RECAUDO_MULTAS_CREAR 										= "RecaudoMultasCrearTab";
	public static final String SUBMENU_RECAUDO_MULTAS_BUSCAR 										= "RecaudoMultasSearchTab";
	public static final String MENU_INICIO_PRESUPUESTO 												= "PresupuestoMenu";
	public static final String SUBMENU_PRESUPUESTO_CARGUE_ARCHIVO 									= "PresupuestoCargueArchivoTab";
	public static final String SUBMENU_PRESUPUESTO_PROYECTOS 										= "PresupuestoProyectosSubMenu";
	public static final String SUBMENU_PRESUPUESTO_RESOLUCION 										= "PresupuestoResolucionSubMenu";
	public static final String SUBMENU_PRESUPUESTO_PROYECTOS_CREAR 									= "PresupuestoProyectosCrearTab";
	public static final String SUBMENU_PRESUPUESTO_PROYECTOS_EDITAR 								= "PresupuestoProyectosEditarTab";
	public static final String SUBMENU_PRESUPUESTO_PROYECTOS_SUSPENDER 								= "PresupuestoProyectosSuspenderTab";
	public static final String SUBMENU_PRESUPUESTO_PROYECTOS_ACTIVAR 								= "PresupuestoProyectosActivarTab";
	public static final String SUBMENU_PRESUPUESTO_PARAMETRIZAR_RESOLUCION 							= "PresupuestoParametrizarResolucionTab";
	public static final String SUBMENU_PRESUPUESTO_APROBAR_RESOLUCION 								= "PresupuestoAprobarResolucionTab";
	public static final String MENU_INICIO_PRIORIZACION 											= "PriorizacionMenu";
	public static final String SUBMENU_PRIORIZACION_PARAMETRIZACION_CARGAR_NOVEDADES 				= "PriorizacionCargarNovedadesTab";
	public static final String SUBMENU_PRIORIZACION_TOTAL	 										= "PriorizacionTotalTab";
	public static final String SUBMENU_PRIORIZACION_PARAMETRIZACION_ADMIN_PRIORIZACION_TOTAL 		= "AdminPriorizacionSubMenu";
	public static final String SUBMENU_PRIORIZACION_PARAMETRIZACION_ADMIN_PRIORIZACION_TOTAL_CREAR 	= "PriorizacionParametroCrearTab";
	public static final String SUBMENU_PRIORIZACION_PARAMETRIZACION_ADMIN_PRIORIZACION_TOTAL_EDITAR = "PriorizacionParametroEditarTab";
	public static final String MENU_INICIO_SISTEMA 													= "SistemaMenu";
	public static final String SUBMENU_SISTEMA_PARAMETRIZACION 										= "ParametrizacionSubMenu";
	public static final String SUBMENU_SISTEMA_PARAMETRIZACION_ADMIN_MAESTRO 						= "AdminMaestroSubMenu";
	public static final String SUBMENU_SISTEMA_PARAMETRIZACION_ADMIN_MAESTRO_CREAR 					= "AdminMaestroCrearTab";
	public static final String SUBMENU_SISTEMA_PARAMETRIZACION_ADMIN_MAESTRO_EDITAR 				= "AdminMaestroEditarTab";
	public static final String SUBMENU_SISTEMA_PARAMETRIZACION_ADMIN_PLANTILLA 						= "AdminPlantillaSubMenu";
	public static final String SUBMENU_SISTEMA_PARAMETRIZACION_ADMIN_PLANTILLA_CREAR 				= "AdminPlantillaCrearTab";
	public static final String SUBMENU_SISTEMA_PARAMETRIZACION_ADMIN_PLANTILLA_EDITAR 				= "AdminPlantillaEditarTab";
	public static final String SUBMENU_SISTEMA_PARAMETRIZACION_TERCERO 								= "TercerosSubMenu";
	public static final String SUBMENU_SISTEMA_PARAMETRIZACION_TERCERO_CREAR 						= "TercerosCrearTab";
	public static final String SUBMENU_SISTEMA_PARAMETRIZACION_TERCERO_EDITAR 						= "TercerosEditarTab";
	public static final String SUBMENU_SISTEMA_PARAMETRIZACION_PERSONA 								= "PersonasSubMenu";
	public static final String SUBMENU_SISTEMA_PARAMETRIZACION_PERSONA_CREAR 						= "PersonasCrearTab";
	public static final String SUBMENU_SISTEMA_PARAMETRIZACION_PERSONA_EDITAR 						= "PersonasEditarTab";
	public static final String SUBMENU_SISTEMA_SEGURIDAD 											= "SeguridadSubMenu";
	public static final String SUBMENU_SISTEMA_SEGURIDAD_RECURSOS_MENU 								= "RecursosMenuSubMenu";
	public static final String SUBMENU_SISTEMA_SEGURIDAD_RECURSOS_MENU_RECURSOS 					= "RecursosTab";
	public static final String SUBMENU_SISTEMA_SEGURIDAD_RECURSOS_MENU_RECURSOS_PERFIL 				= "RecursosPerfilTab";
	public static final String SUBMENU_SISTEMA_SEGURIDAD_USUARIOS 									= "UsuariosSubMenu";
	public static final String SUBMENU_SISTEMA_SEGURIDAD_USUARIOS_CRER 								= "UsuariosCrearTab";
	public static final String SUBMENU_SISTEMA_SEGURIDAD_USUARIOS_EDITAR 							= "UsuariosEditarTab";
	public static final String SUBMENU_SISTEMA_SEGURIDAD_LOG_AUDITORIA 								= "LogAuditoriaSubMenu";
	public static final String SUBMENU_SISTEMA_SEGURIDAD_LOG_AUDITORIA_CONSULTAR 					= "LogAuditoriaConsultarTab";
	public static final String MENU_AYUDA 															= "AyudaMenu";
	public static final String MENU_SALIR 															= "SalirMenu";
	public static final String MENU_REPORTES 														= "ReportesMenu";
	public static final String SUBMENU_REPORTES_RECAUDOS 											= "ReportesRecaudosSubMenu";
	public static final String SUBMENU_REPORTES_RECAUDOS_SUBSISTENCIA 								= "ReportesRecaudosSubsistenciaSubMenu";
	public static final String SUBMENU_REPORTES_RECAUDOS_SOLIDARIDAD 								= "ReportesRecaudosSolidaridadSubMenu";
	public static final String SUBMENU_REPORTES_PRESUPUESTO 										= "ReportesPresupuestoSubMenu";
	public static final String SUBMENU_REPORTES_PRIORIZACION 										= "ReportesPriorizacionSubMenu";
}
