--                      SIGEP II                     --
--           DOCUMENTO DDL SCRIPTS DESARROLLO        -- 
-- ELABORARO POR: JOSE VISCAYA
-- FECHA: MAYO 30 DE 2018

--         ESTRUCTURA DE PUBLICACION DE SCRIPT        --

-- DESCRIPCION:
-- ELABORADO_POR:
-- CONSECUTIVO_SCRIPT:
-- FUNCIONALIDAD: (DESARROLLO, SOLUCION BUG N-BUG)
-- ENTORNO_ACTUAL: (DESARROLLO, QA, PREPRO, DAFP)
-- RESULTADO_DEL_SCRIPT: (EJECUTADO CORRECTAMENTE, CON ERRORES (ADJUNTAR SOLUCION DE LOS ERRORES CON SCRIPT))
-- TODOS LOS SCRIPTS SE DEBEN ALMACENAR EN LA BASE DE DATOS DE LA SIGUIENTE MANERA:




-- BLOQUE DEL SCRIPT EJEMPLO 

DECLARE
 -- DECLARACION DE VARIABLE
BEGIN
   
END ;


-- FIN DE APERTURA DE DOCUMENT DDL PARA EJECUCION DE SCRIPT --



--  ********************************************  INICIO DE SCRIPT *************************************************************
-- FECHA DE ELABORACION: 30 DE MAYO DE 2018
-- FECHA DE EJECUCION :  30 DE MAYO DE 2018
-- DESCRIPCION: CREACION DE TABLA PARA CONTROL DE SCRIPTS
-- ELABORADO_POR: JOSE VISCAYA
-- EJECUTADO_PO: JOSE VISCAYA
-- CONSECUTIVO_SCRIPT: 2
-- FUNCIONALIDAD: ALMACENAMIENTO DE SCRIPTS
-- ENTORNO_ACTUAL: DESARROLLO
-- RESULTADO_DEL_SCRIPT: (EJECUTADO CORRECTAMENTE)

-- *************************************************************
-- INSERCION DE SECUENCIA PARA LOS CODIGOS DE LOS SCRIPTS
    INSERT INTO  "SIGEP2"."SEQUENCIAS_SIGEP" ("TABLA_NOMBRE", "SECUENCIA", "TIPO_RETORNO", "DESCRIPCION", "FECHA_SQ")
    VALUES ('CODIGO_SCRIPT_SIGEP',1,6,'SEQUENCIA PARA LA GENERACION DE CODIGOS SCRIPT DE 6 DIGITOS', TO_DATE('2018-05-30 10:29:00','YYYY-MM-DD HH24:MI:SS'));
-- *************************************************************
--TABLA PARA CONTROL DE SCRIPTS
CREATE TABLE SCRIPT_SIGEP
(COD_SICRIPT NUMERIC(10) NOT NULL
  constraint SCRIPT_SIGEP_PK
    primary key,
 DESCRIPCION VARCHAR2(300) NOT NULL ,
 FUNCIONALIDAD VARCHAR2(100) NOT NULL ,
 ENTORNO_ACTUAL VARCHAR2(100) default 'DESARROLLO' NOT NULL
  check ( entorno_actual IN (
      'DESARROLLO',
      'QA',
      'PREPRO',
      'DAFP'
    ) ),
 RESULTADO_DEL_SCRIPT VARCHAR2(100) NOT NULL ,
 ELABORADO_POR VARCHAR2(100) NOT NULL ,
 EJECUTADO_POR VARCHAR2(100) NULL ,
 FECHA_EJECUCION DATE default SYSDATE not null
);
-- *************************************************************
DECLARE
   idNumero NUMBER(30);
BEGIN
   -- ENTORNO_ACTUAL SOLO PERMITE  'DESARROLLO','QA','PREPRO', 'DAFP'
   -- INSERCION DE CONTROL DE SCRIPT DE CREACION DE TABLA
   idNumero := SEQUENCIAS_SIGEP_FUN('CODIGO_SCRIPT_SIGEP');
   INSERT INTO  "SIGEP2"."SCRIPT_SIGEP" ("COD_SICRIPT", "DESCRIPCION", "FUNCIONALIDAD", "ENTORNO_ACTUAL", "RESULTADO_DEL_SCRIPT","ELABORADO_POR","EJECUTADO_POR", "FECHA_EJECUCION")
    VALUES (idNumero,'CREACION DE TABLA PARA ALMACENAR SCRIPT','ALMACENAR SCRIPT PARA EL CONTROL EN LOS DIFERENTES AMBIENTES','DESARROLLO','EJECUTADO SIN ERRORES','JOSE VISCAYA','JOSE VISCAYA', TO_DATE('2018-05-30 10:55:00','YYYY-MM-DD HH24:MI:SS'));
END ;
--  ********************************************  FIN DE SCRIPT *************************************************************
--  ********************************************  INICIO DE SCRIPT *************************************************************
-- FECHA DE ELABORACION: 01 DE JUNIO DE 2018
-- FECHA DE EJECUCION :  08 DE JUNIO DE 2018
-- DESCRIPCION: CREACION DE PARAMETRICA PARA TENER VERSIONAMIENTO
-- ELABORADO_POR: JESUS TORRES
-- EJECUTADO_POR: JOSE VISCAYA
-- CONSECUTIVO_SCRIPT: 9
-- FUNCIONALIDAD: CREA PARAMETRICA
-- ENTORNO_ACTUAL: QA
-- RESULTADO_DEL_SCRIPT: (EJECUTADO CORRECTAMENTE)
-- *************************************************************
DECLARE
	idNumero NUMBER(30);
BEGIN
   idNumero := SEQUENCIAS_SIGEP_FUN('ID_TABLA_PARAMETRICA');
   INSERT INTO  "SIGEP2"."PARAMETRICA" ("COD_TABLA_PARAMETRICA", "NOMBRE_PARAMETRO", "VALOR_PARAMETRO", "FLG_ESTADO", "TIPO_PARAMETRO","DESCRIPCION","AUD_COD_USUARIO")
    VALUES (1716, 'VERSION_SIGEP2', '3.0.0', '1', 'S','LA VERSION ACTUAL DEL SIGEP2', 1);
    commit;
END ;
--  ********************************************  FIN DE SCRIPT *************************************************************
--  ********************************************  INICIO DE SCRIPT *************************************************************
-- FECHA DE ELABORACION: 07 DE JUNIO DE 2018
-- FECHA DE EJECUCION :  07 DE JUNIO DE 2018
-- DESCRIPCION: ACTUALIZACION DE PARAMETRICA AJUSTE EN TEXTO DEL CUERPO DEL MAIL ENVIADO AL RESTABLECER CONTRASEÑA
-- ELABORADO_POR: NESTOR JAIR RIASCO
-- EJECUTADO_POR: JOSE VISCAYA
-- CONSECUTIVO_SCRIPT: 11
-- FUNCIONALIDAD: RESTABLECER CONTRASEÑA
-- ENTORNO_ACTUAL: QA
-- RESULTADO_DEL_SCRIPT: (EJECUTADO CORRECTAMENTE)
-- *************************************************************
BEGIN
	UPDATE PARAMETRICA 
	SET VALOR_PARAMETRO ='<br><b>Mensaje para: </b>USUARIO_RESTABLECE<hr><p style="text-align:justify">Usted ha solicitado el restablecimiento de su contrase&#241;a en SIGEP II. Se ha generado autorizaci&oacute;n con c&oacute;digo <span style="font-weight:bold">ticket</span>. Para ingresar una nueva contrase&#241;a haga click <a href="link" onmouseover="window.status=''Restablecer contrase&#241;a'';return true" target="_blank" style="color:#2a89b4"/>aqu&iacute;</a>.</p><p>Recuerde que:</p><ul style="text-align:justify"><li>La contrase&#241;a debe contener al menos una letra may&uacute;scula, una letra min&uacute;scula, un n&uacute;mero y un caracter especial tales como <b>PASS_VALIDATE_EXPRESION_REGULAR_SIMBOLOS</b>, sin espacios.</li><li>Esta no debe superar la longitud de PASS_VALIDATE_MAXIMO caracteres y debe tener una longitud m&iacute;nima de PASS_VALIDATE_MINIMO.</li><li>En enlace tiene vigencia hasta vence y puede ser utilizado solo una vez. Si dentro de este tiempo no realiza el procedimiento debe solicitar de nuevo en enlace de restablecimiento de contrase&#241;a.</li></ul>'
	WHERE COD_TABLA_PARAMETRICA = 680;
END ;
--  ********************************************  FIN DE SCRIPT *************************************************************
--  ********************************************  INICIO DE SCRIPT *************************************************************
-- FECHA DE ELABORACION: 08 DE JUNIO DE 2018
-- FECHA DE EJECUCION :  08 DE JUNIO DE 2018
-- DESCRIPCION: ACTUALIZACION DE PARAMETRICA AJUSTE EN TEXTO DEL CUERPO DEL MAIL ENVIADO AL CONFIRMAR RESTABLECER CONTRASEÑA
-- ELABORADO_POR: NESTOR JAIR RIASCO
-- EJECUTADO_PO: NESTOR JAIR RIASCO
-- CONSECUTIVO_SCRIPT: 6
-- FUNCIONALIDAD: RESTABLECER CONTRASEÑA
-- ENTORNO_ACTUAL: DESARROLLO
-- RESULTADO_DEL_SCRIPT: (EJECUTADO CORRECTAMENTE)
-- *************************************************************
BEGIN
	UPDATE PARAMETRICA 
	SET VALOR_PARAMETRO ='<font face="Arial, Verdana"><span style="font-size: 10pt;">Notificaci&oacute;n de gesti&oacute;n de usuario</span></font><div style="font-family: Arial, Verdana; font-size: 10pt; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: normal;"><br></div><div><font face="Arial, Verdana"><span style="font-size: 13.3333px;">La contrase&#241;a para el usuario <span style="font-weight: bold;">CUENTA_USUARIO</span> ha sido restablecida de forma exitosa.</span></font><br><div style="font-family: Arial, Verdana; font-size: 10pt; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: normal;"><br></div><div style="font-family: Arial, Verdana; font-size: 10pt; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: normal;"><div style="font-weight: normal;"><span style="color: rgb(255, 0, 0); font-weight: bold;">Nota: </span>Si sabe de un evento anormal en esta transacci&oacute;n por favor notificarlo al administrador del sistema enviando un correo a <span style="font-weight: bold;">CUENTA_ADMON</span>; o tambi&eacute;n restableciendo la contrase&#241;a en LINK_RESTABLECER link LINK_OLVIDO_CONTRASENA.</div></div></div>'
	WHERE COD_TABLA_PARAMETRICA = 689;
END ;
--  ********************************************  FIN DE SCRIPT *************************************************************