	$(function() {
		//inicializa();
		
		//$(".datepicker").mask("99/99/9999");
		$(".cep").mask("99999-999");
		//$(".cnpj").mask("99.999.999/9999-99");
		$(".maskCpf").mask("999.999.999-99");
		$(".maskCnpj").mask("99.999.999/9999-99");
		$(".maskData").mask("99/99/9999");
		$(".maskHora").mask("99:99");
		$(".onlyNumbers").mask("9999");
		
		$('.fone').focusout(function(){
			var phone, element;
			element = $(this);
			element.unmask();
			phone = element.val().replace(/\D/g, '');
			if(phone.length > 10) {
				element.mask("(99) 99999-999?9");
			}else if(phone.length == 10) {
				element.mask("(99) 9999-9999?9");
			} else {
				element.mask("(99) 99999999?9");
			}
		}).trigger('focusout');
		
		$('.fone').focus(function(){
			var phone, element;
			element = $(this);
			element.unmask();
			phone = element.val().replace(/\D/g, '');
			
			element.mask("(99) 99999999?9");
		
		}).trigger('focus');
		
		
		
		
		$('.select2-selection__rendered').each(function () { 
			$(this).html('Selecione'); 
		});
		
	}); 

