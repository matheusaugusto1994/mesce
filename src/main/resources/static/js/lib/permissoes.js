
function abreForm(){

	if( $("#divCadastro").attr("style") == "display: none;" ){
		$("#divCadastro").show(700);
		$("#btnNovoCadastro").html("Fechar");

	}else{
		$("#divCadastro").hide(700);
		$("#btnNovoCadastro").html("Abrir Cadastro");
		limpaCampos("formUsuarios");
	}

}

function buscaPermissions(idRole){
	
	if(idRole == ""){
		$("#divListaTelasRole").html("");
		return;
	}
	var json =  idRole;

	var request = $.ajax({
		  url: "permissao/listPermissions",
		  data: JSON.stringify(json),
		  method: "POST",
		  contentType: 'application/json; charset=utf-8',
	});
	
	request.success(function(data) {
		montaListaTelas(data);
		permissionsList = data;
  	});
}



function savePermission(key){
	
	var data = permissionsList[key];
	
	var idRolePermission = data.id;
	var idTela = data.tela.id;
	var idRole = data.role.id;
	
	if( $("#acessoAccess_" +idRolePermission ).is(":checked") ){
		var permission = "ACCESS";
	}else{
		var permission = "DENIED";
	}
	
	var json = {
			"id" : idRolePermission,
			"permission" : permission,
			"tela" : { "id" : idTela },
			"role" : { "id" : idRole }
	}

	var request = $.ajax({
		  url: "permissao/save",
		  data: JSON.stringify(json),
		  method: "POST",
		  contentType: 'application/json; charset=utf-8',
	});
	
	request.success(function(data) {
		toastr.success(data);
  	});

	request.error(function(data) {
		toastr.error("Ocorreu um erro ao salvar.");
	});
	
}

var permissionsList;

function montaListaTelas(data){
	var obj = data;
	var usuarios = '<div class="portlet box blue">' +
						'<div class="portlet-title">' +
							'<div class="caption">' + 
								'<i class="fa fa-picture"></i>Lista de Telas' + 
							'</div>' + 
						'</div>' +
						'<div class="portlet-body">' +
							'<div class="table-responsive">' +
								'<table class="table table-condensed table-hover table-striped">' + 
									'<thead>' + 
										'<tr>' +
											'<th><div align="center">Tela</div> </th>' +
											'<th><div align="center">Libeado</div> </th>' +
											'<th><div align="center">Bloqueado</div> </th>' +
										'</tr>' +
									'</thead>'+
									'<tbody>';
		$.each(data, function (key, value) {
				usuarios += 			'<tr>'+
											'<td align="center">' + value.tela.descricao + '</td>' +
											'<td align="center"><input type="radio" ' + ((value.permission == "ACCESS") ? " checked " : "") + ' id="acessoAccess_'+value.id+'" name="acesso_'+value.tela.id+'" onClick="savePermission('+ key +');"></td>' +
											'<td align="center"><input type="radio" ' + ((value.permission == "DENIED") ? " checked " : "") + ' id="acessoDenied_'+value.id+'" name="acesso_'+value.tela.id+'" onClick="savePermission('+ key +');"></td>' +
										'</tr>';
		});						
			usuarios += 			'</tbody>'+
								'</table>'+
							'</div>' + 
						'</div>'  
					'</div>';
		$("#divListaTelasRole").html(usuarios);
		
		$(".tooltips").tooltip()                        
}

		
function topPage(){
	$("#back-to-top").click();
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

function limpaCampos(form){

	$("#" + form)[0].reset();
	$("#id").val('');
	$('#fotoPath').val('');

	$('.select2-selection__rendered').each(function () {
		$(this).html('Selecione');
	});

	$('.campoObrigratorio').each(function () {
		$(this).removeClass('has-error');
		$(this).removeClass('has-success');
	});


	$("#" + form + " .fa").removeClass("fa-warning");
	$("#" + form + " .fa").removeClass("fa-check");
	$("#" + form + " .alert-danger").hide();
}

