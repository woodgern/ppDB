(function(exports) {    
    exports.startApp = function() {
        $.get("{base_url}/pp", function(data) {
            $("#pp").html(data);
        });
    }
})(window);