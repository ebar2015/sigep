// Constant required to determine, whether to show Navigation away check message or not.
var exiparationSesionOnLoad = 300; // segundos

/*
 * This function will force current user log out of the application, if he is
 * navigating away from application
 */
window.onunload = function() {
	var isPageUnloadHandled = getCookie('isPageUnloadHandled');
	if (isPageUnloadHandled == null) {
		isPageUnloadHandled = false;
	}
	var urlLogout = getContextPath() + '/sigep2/index.xhtml';
	if (isPageUnloadHandled) {
		/* Se invoca la URL de cierre de sesión */
		urlLogout = getContextPath() + '/sigep2/logoutOnIndex.xhtml';
		$.ajax({
			url : urlLogout,
			success : function(data) {

			}
		});

		// Logout user from the application using AJAX request.
		$.ajax({
			url : logoutUrl,
			type : 'POST',
			async : false,
			cache : false,
			dataType : 'HTML'
		});
	}

};

function hideSessionNotificationDialog() {
	$('#cerrarSesionConfirm').dialog('close');
}