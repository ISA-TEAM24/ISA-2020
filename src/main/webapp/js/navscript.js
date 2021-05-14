$(document).ready(function(){       
    navbarColorToggler();
});

function navbarColorToggler() {
    var scroll_pos = 0;
    $(document).scroll(function() { 
        scroll_pos = $(this).scrollTop();
        if(scroll_pos > 650) {
            $('.nav-link').css('color', '#000');
        } else {
            $('.nav-link').css('color', '#fff');
        }
    });
}