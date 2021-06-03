$(document).ready(function() {
    //alert("test");
});


$("#signUpNavBtn").click(function(){
    $('#loginFormModal').hide();
    $("#registrationFormModal").show();
    disableScroll();
    hideIndex1()
});


$("#cancelRegBtn").click(function(){
    enableScroll();
    $("#registrationFormModal").hide();
    showIndex1() 

});

function disableScroll(){
        var scrollPosition = [
        self.pageXOffset || document.documentElement.scrollLeft || document.body.scrollLeft,
        self.pageYOffset || document.documentElement.scrollTop  || document.body.scrollTop
                            ];
        var html = jQuery('html');
        html.data('scroll-position', scrollPosition);
        html.data('previous-overflow', html.css('overflow'));
        html.css('overflow', 'hidden');
        window.scrollTo(scrollPosition[0], scrollPosition[1]);
    }

function enableScroll(){
    var html = jQuery('html');
    var scrollPosition = html.data('scroll-position');
    html.css('overflow', html.data('previous-overflow'));
    window.scrollTo(scrollPosition[0], scrollPosition[1])
}

