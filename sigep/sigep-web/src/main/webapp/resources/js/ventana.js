var IE = navigator.appName == "Microsoft Internet Explorer";
var NS = navigator.appName == "Netscape";
var bVer = parseInt(navigator.appVersion);

function openWin() {
	window
			.open(
					getContextPath() + '/sigep2/index.xhtml',
					'_blank',
					'status=no,menubar=no,directories=no,scrollbars=yes,resizable=yes,fullscreen=yes');
}

function getContextPath() {
	return window.location.pathname.substring(0, window.location.pathname
			.indexOf("/", 2));
}

document.onkeydown = mykeyhandler;
document.onunload = validarCierreVentana;
document.addEventListener("unload", validarCierreVentana);

function validarCierreVentana() {
	window.alert("Do you really want to close?");
}

function mykeyhandler() {
	// Deshabilita la funcion de desarrollador del navegador
	if (window.event.keyCode == 123) {
		window.event.cancelBubble = true;
		window.event.keyCode = 0;
		window.event.returnValue = false;
		window.location.href = getContextPath() + '/sigep2/logoutOnIndex.xhtml';
		return false;
	}

	if ((event.altLeft && window.event.keyCode == 37)
			|| (event.ctrlLeft && window.event.keyCode == 78)) {
		window.location.href = getContextPath() + '/sigep2/logoutOnIndex.xhtml';
		return false;
	}

	// Deshabilitar el alt+f4 y ctl+f4
	if ((event.altLeft && window.event.keyCode == 115)
			|| (event.ctrlLeft && window.event.keyCode == 115)) {
		window.event.cancelBubble = true;
		window.event.keyCode = 0;
		window.event.returnValue = false;
		window.location.href = getContextPath() + '/sigep2/logoutOnIndex.xhtml';
		return false;
	}

	// Deshabilita la función actualizar página
	if ((window.event && window.event.keyCode == 116)
			|| (window.event && window.event.keyCode == 122)) {
		window.event.cancelBubble = true;
		window.event.keyCode = 0;
		window.event.returnValue = false;
		window.location.href = getContextPath() + '/sigep2/logoutOnIndex.xhtml';
		return false;
	}
}

// Time out

function clickIE() {
	if (document.all) {
		(message);
		return false;
	}
}
function clickNS(e) {
	if (document.layers || (document.getElementById && !document.all)) {
		if (e.which == 2 || e.which == 3) {
			(message);
			return false;
		}
	}
}
if (document.layers) {
	document.captureEvents(Event.MOUSEDOWN);
	document.onmousedown = clickNS;
} else {
	document.onmouseup = clickNS;
	document.oncontextmenu = clickIE;
}

document.oncontextmenu = new Function("return false");

function mouseDown(e) {
	var ctrlPressed = 0;
	var altPressed = 0;
	var shiftPressed = 0;
	if (parseInt(navigator.appVersion) > 3) {
		if (navigator.appName == "Netscape") {
			var mString = (e.modifiers + 32).toString(2).substring(3, 6);
			shiftPressed = (mString.charAt(0) == "1");
			ctrlPressed = (mString.charAt(1) == "1");
			altPressed = (mString.charAt(2) == "1");
			self.status = "modifiers=" + e.modifiers + " (" + mString + ")"
		} else {
			shiftPressed = event.shiftKey;
			altPressed = event.altKey;
			ctrlPressed = event.ctrlKey;
		}
		if (shiftPressed || altPressed || ctrlPressed)
			alert("Funci\u00F3n no permitida");

	}
	return true;
}
if (parseInt(navigator.appVersion) > 3) {
	document.onmousedown = mouseDown;
	if (navigator.appName == "Netscape")
		document.captureEvents(Event.MOUSEDOWN);
}

function message() {
	return "Funci\u00F3n no permitida";
}

function fontSize(valor) {
	tam = parseFloat(document.body.style.fontSize);
	if ((tam + valor) >= .8 && (tam + valor) <= 1.2) {
		tam += valor;
		elem = document.getElementsByTagName('body');
		for (i = 0; ele = elem[i]; i++) {
			ele.style.fontSize = tam + 'em';
		}
	}

	elem = document.getElementsById('banner');
	height = parseFloat(elem.style.height);
	if ((height + valor) >= 13 && (height + valor) <= 14) {
		ele.style.height = height + 'em';
	}

	elem = document.getElementsByTagName('input');
	for (i = 0; ele = elem[i]; i++) {
		var tamInput = ele.style.fontSize;
		if (tamInput != '') {
			tamInput = parseFloat(tamInput.replace('em', '').replace('px', ''))
					+ valor;
			ele.style.fontSize = tamInput + 'em';
		}
	}

	elem = document.getElementsByTagName('button');
	for (i = 0; ele = elem[i]; i++) {
		var tamButton = ele.style.width;
		if (tamButton != '') {
			tamButton = parseFloat(tamButton.replace('em', '')
					.replace('px', ''))
					+ valor;
			ele.style.width = tamButton + 'em';
			ele.style.height = tamButton + 'em';
		}
	}
}

function getCookie(cname) {
	var name = cname + "=";
	var decodedCookie = decodeURIComponent(document.cookie);
	var ca = decodedCookie.split(';');
	for (var i = 0; i < ca.length; i++) {
		var c = ca[i];
		while (c.charAt(0) == ' ') {
			c = c.substring(1);
		}
		if (c.indexOf(name) == 0) {
			return c.substring(name.length, c.length);
		}
	}
	return null;
}

function setCookie(cname, value, expires_seconds) {
	var d = new Date();
	var expires_date = new Date(d.getTime() + 1000 * expires_seconds);
	document.cookie = cname + '=' + value + '; expires='
			+ expires_date.toGMTString() + ';';
}

PrimeFaces.locales['es'] = {
	closeText : 'Cerrar',
	prevText : 'Anterior',
	nextText : 'Siguiente',
	monthNames : [ 'Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio',
			'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre',
			'Diciembre' ],
	monthNamesShort : [ 'Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago',
			'Sep', 'Oct', 'Nov', 'Dic' ],
	dayNames : [ 'Domingo', 'Lunes', 'Martes', 'Mi\u00E9rcoles', 'Jueves',
			'Viernes', 'Sábado' ],
	dayNamesShort : [ 'Dom', 'Lun', 'Mar', 'Mie', 'Jue', 'Vie', 'Sab' ],
	dayNamesMin : [ 'Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'S\u00E1' ],
	weekHeader : 'Semana',
	firstDay : 1,
	isRTL : false,
	showMonthAfterYear : false,
	yearSuffix : '',
	timeOnlyTitle : 'S\u00F3lo hora',
	timeText : 'Tiempo',
	hourText : 'Hora',
	minuteText : 'Minuto',
	secondText : 'Segundo',
	currentText : 'Fecha actual',
	ampm : false,
	month : 'Mes',
	week : 'Semana',
	day : 'D\u00EDa',
	allDayText : 'Todo el d\u00EDa'
}

function start() {
	PF('statusDialog').show();
}

function stop() {
	PF('statusDialog').hide();
}

function nobackbutton() {
	window.location.hash = "no-back-button";
	window.location.hash = "Again-No-back-button" // chrome
	window.onhashchange = function() {
		window.location.hash = "no-back-button";
	}
}

function numbervalida(e) {
	tecla = (document.all) ? e.keyCode : e.which;

	// Tecla de retroceso para borrar, siempre la permite

	if (tecla == 8 || tecla == 0) {
		return true;

	}

	// Patron de entrada, en este caso solo acepta numeros
	patron = /[0-9]/;
	tecla_final = String.fromCharCode(tecla);
	return patron.test(tecla_final);
}

function numbervalidaTipoDoc(e) {
	var tipoId = document.getElementById("frmPrincipal:formges:tDocumento").value;

	tecla = (document.all) ? e.keyCode : e.which;

	// Tecla de retroceso para borrar, siempre la permite
	if (tecla == 8 || tecla == 0) {
		return true;
	}

	// Patron de entrada, valida si es pasaporte y permite letras y números
	if (tipoId == "42") {
		patron = /[A-Za-z0-9]/;
	} else {
		patron = /[0-9]/;
	}

	tecla_final = String.fromCharCode(tecla);
	return patron.test(tecla_final);
}

nobackbutton();

var delay = (function() {
	var timer = 0;
	return function(callback, ms) {
		clearTimeout(timer);
		timer = setTimeout(callback, ms);
	};
})();

function posicionarAncla(titleMessage, message, location) {
	var ancla = window.document.getElementById(location);

	if (ancla != null) {
		window.location.hash = "#";
		window.location.hash = location;
	} else {
		swal(titleMessage, message, 'warning');
	}
}