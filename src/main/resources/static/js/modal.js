//modal Ã³¸® script
$(document).ready( function() {
	$(opener.document).find('#loading').show();
});
$(window).on( "unload", function( event, ui ) {
	$(opener.document).find('#loading').hide();
});
 