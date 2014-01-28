(function($) {

    $(document).ready(function() {
        $("*[data-lp-event]").each(function() {
            var element = $(this);

            element.bind(element.attr('data-lp-event'), function() {
                if (typeof lpSendData == "undefined")
                    return;

                lpSendData(element.attr('data-lp-scope') || 'page', element.attr('data-lp-variableName'), element.attr('data-lp-variableValue'));
            });
        });
    });

})(jQuery);