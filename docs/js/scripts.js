(function(exports) {    
    exports.startApp = function() {
    	var url = 'http://192.168.1.108:3000/pp';
    	$.ajax(url, {
            dataType: 'json',
            headers: {

            },
            success: function(r) {
            	$("#pp").html(data.word);
                window.alert(JSON.stringify(r));
            },
            error: function(r) {
                window.alert(JSON.stringify(r));
            }
        });
        /*var x = $.get("http://192.168.1.108:3000/pp", function(data) {
        	window.alert(data);
            $("#pp").html(data.word);
        }).fail(function() {
        	window.alert('failed');
        });*/
    }
})(window);