function salvarDados(){
	var form = "formTermo";
	
	if(validaCampos(form) == false ){
		return;
	} 
	
	var dataFormJSON = $("#" + form).find(':input').filter(function () {
          return $.trim(this.value).length > 0
	}).serializeJSON();
	
	$.ajax({
		  url: "termo/save",
		  method: "POST",
		  async:false,
		  data: JSON.stringify(dataFormJSON),
		  contentType: 'application/json; charset=utf-8',
		  success: function(data) {
			  var retorno = data.split("|");
			  
			  if(retorno[0]=="success")
				  toastr.success(retorno[1]);
				  
			  buscaDados();
		  },
		  error: function(data) {
			  toastr.error(data);
		  }
	});
}

function buscaDados(){
	$.ajax({
		  url: "termo/busca",
		  method: "GET",
		  success: function(data) {
			  carregaCampos(data);
		  },
		  error: function(data) {
			  toastr.error(data);
		  }
	});
}



function carregaCampos(data){
	if (data) {
		$("#termo").val(data.termo);
		$("#id").val(data.id);
		applyWysihtml5();
	}
}

function validaCampos(form){
	
	$("#" + form).submit();
	
	var valid = true;
	
	$('.campoObrigratorio').each(function () { 
		if( $(this).hasClass('has-error') ){
			valid = false;
		} 
	});
	
	return valid;
	
}	

function applyWysihtml5(){
	var content = $('#termo');
	var contentPar = content.parent()
	contentPar.find('.wysihtml5-toolbar').remove()
	contentPar.find('iframe').remove()
	contentPar.find('input[name*="wysihtml5"]').remove()
	content.show()

	$('.inbox-wysihtml5').wysihtml5({
        "stylesheets": ["../plugins/bootstrap-wysihtml5/wysiwyg-color.css"]
	});
}