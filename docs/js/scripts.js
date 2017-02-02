(function(exports) {    
    exports.startApp = function() {
    	var url = 'http://192.168.1.108:3000/pp';
    	$.ajax(url, {
            dataType: 'json',
            success: function(data) {
            	$("#pp").html(data.word);
            },
            error: function(r) {
                window.alert('error');
            }
        });

        /*var x = $.get("http://192.168.1.108:3000/pp", function(data) {
        	window.alert(data);
            $("#pp").html(data.word);
        }).fail(function(r) {
        	window.alert(JSON.stringify(r));
        });*/
    }
})(window);