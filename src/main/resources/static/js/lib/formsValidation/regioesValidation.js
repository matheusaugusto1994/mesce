var FormValidation = function () {

    // validation using icons
    var ValidationRegioes = function() {

            var form = $('#formRegioes');
            var error2 = $('.alert-danger', form);
            var success2 = $('.alert-success', form);
            
            form.validate({
                errorElement: 'span', //default input error message container
                errorClass: 'help-block help-block-error', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                ignore: true,  // validate all fields including form hidden input
                
                rules: {
                    name: {
                        required: true
                    }
                },

                invalidHandler: function (event, validator) { //display error alert on form submit              
                    success2.hide();
                    error2.show();
                    App.scrollTo(error2, -200);
                },

                errorPlacement: function (error, element) { // render error placement for each input type
                    var icon = $(element).parent('.input-icon').children('i');
                    icon.removeClass('fa-check').addClass("fa-warning");  
                    //icon.attr("data-original-title", error.text()).tooltip({'container': 'body'});
                },

                highlight: function (element) { // hightlight error inputs
                    $(element)
                        .closest('.campoObrigratorio').removeClass("has-success").addClass('has-error'); // set error class to the control group   
                },

                unhighlight: function (element) { // revert the change done by hightlight
                    
                },

                success: function (label, element) {
                    var icon = $(element).parent('.input-icon').children('i');
                    $(element).closest('.campoObrigratorio').removeClass('has-error').addClass('has-success'); // set success class to the control group
                    icon.removeClass("fa-warning").addClass("fa-check");
                    
                },

                submitHandler: function (form) {
                    //success2.show();
                    error2.hide();
                    //form[0].submit(); // submit the form
                    
                }
                
            });
            
            //Campo Especial, Tratado individualmente
            $( "#userId" ).rules( "add", {
          	  required: true
          	});
            
            //apply validation on select2 dropdown value change, this only needed for chosen dropdown integration.
            $('.select2me', form).change(function () {
            	form.validate().element($(this)); //revalidate the chosen dropdown value and show error or success message for the input
            });

            //initialize datepicker
            $('.date-picker').datepicker({
                rtl: App.isRTL(),
                autoclose: true
            });
            $('.date-picker .form-control').change(function() {
                form.validate().element($(this)); //revalidate the chosen dropdown value and show error or success message for the input 
            })


    }

    return {
        //main function to initiate the module
        init: function () {

            ValidationRegioes();

        }

    };

}();

jQuery(document).ready(function() {
    FormValidation.init();
});