(function(exports) {    
    exports.startApp = function() {
    	var url = 'https://ppdb.gustafn.com/pp';
    	$.ajax(url, {
            dataType: 'json',
            success: function(data) {
            	$("#pp").html(data.word);
            },
            error: function(r) {
                window.alert('error');
            }
        });
    }
})(window);