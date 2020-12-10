
function abreForm(){
	limpaCampos("formUsuarios");
	if( $("#divCadastro").attr("style") === "display: none;" ){
		$("#divCadastro").show(700);
		$("#btnNovoCadastro").html("Fechar");

	}else{
		$("#divCadastro").hide(700);
		$("#btnNovoCadastro").html("Abrir Cadastro");
		
	}

}

function save(){
	var form = "formUsuarios";

	if ($("#password").val() !== $("#confirmPassword").val()){
		toastr.error("As Senhas Digitadas não Conferem");
		return;
	}

	if (validaCampos(form) === false){
		return;
	}

	var dataFormJSON = $("#" + form).find(':input').filter(function () {
          return $.trim(this.value).length > 0
	}).serializeJSON();
	
	$.ajax({
		url: "usuario",
		method: "POST",
		data: JSON.stringify(dataFormJSON),
		contentType: 'application/json',
		success: function(data) {
		  toastr.success("O registro foi salvo com sucesso!");
		  buscaUsuarios(0);
		  abreForm();
		},
		error: function(data) {
		  toastr.error("Erro ao salvar o registro!");
		  abreForm();
		}
	});

}

function buscaUsuarios(pagina){

	var queryBusca = $("#queryBusca").val();

	$.ajax({
		url: "usuario/" + pagina + "?q=" + queryBusca,
		method: "GET",
		contentType: 'application/json; charset=utf-8',
		success: function(data) {
			montaLista(data);
		}
  	});
}

var usuariosList;

function montaLista(data){
	if(data && data.content){
		let content = data.content;
		usuariosList = content;

		var usuarios = '<div class="portlet box blue">' +
							'<div class="portlet-title">' +
								'<div class="caption">' +
									'<i class="fa fa-picture"></i>Lista de Usuários' +
								'</div>' +
							'</div>' +
							'<div class="portlet-body">' +
								'<div class="table-responsive">' +
									'<table class="table table-condensed table-hover table-striped">' +
										'<thead>' +
											'<tr>' +
												'<th><div align="center">Nome</div> </th>' +
												'<th><div align="center">Usuário</div> </th>' +
												'<th><div align="center">Nível</div> </th>' +
											'</tr>' +
										'</thead>'+
										'<tbody>';

			$.each(content, function (key, value) {
					usuarios += 			'<tr>'+
												'<td align="center">' + ((value.pessoa == null) ? " - " : value.pessoa.name) + '</td>' +
												'<td align="center">' + value.name + '</td>' +
												'<td align="center">' + value.role.name +  '</td>' +
												'<td align="center">' +
													'<a style="cursor:pointer;" data-original-title="Editar" class="btn btn-icon-only blue tooltips" onClick="carregaCampos('+ key +');">' +
	                                                	'<i class="fa fa-edit"></i>' +
	                                                '</a>' +
													'<a style="cursor:pointer;" data-original-title="Excluir" class="btn btn-icon-only blue tooltips" onClick="deleteUsuario('+ value.id +');">' +
	                                                	'<i class="fa fa-remove"></i>' +
	                                                '</a>' +
												'</td>' +
											'</tr>';
			});

			usuarios += 			    '</tbody>'+
									'</table>'+
								'</div>' +
							'</div>'
						'</div>';

		if (data.pageable) {
			montaPaginacao(data.totalPages, data.number)
		}

	} else {
		usuarios = '<div class="alert alert-danger">' +
						'Nenhum Resultado Encontrado.' +
			       '</div>';
	}

	$("#divListaUsuarios").html(usuarios);

	$(".tooltips").tooltip()
}

function carregaCampos(key){

	if(!$("#divCadastro").is(":visible")){
		abreForm();
	}

	limpaCampos("formUsuarios");

	let data = usuariosList[key];

	$.each(data, function(id, valor) {
	    $("#" + id).val(valor);
	    
	    if(id === "password"){
			$("#" + id).val(valor);
			$("#confirmPassword").val(valor);
	    }

	    if(id === "pessoa"){
			$("#pessoaId").val(data.pessoa.id);
			$('#select2-pessoaId-container').html(
				$( '#pessoaId option:selected' ).text()
			);
	    }

	    if(id === "blocked"){
	    	$("#blocked").val(valor);
	    	$('#select2-blocked-container').html(
	    		$( '#blocked option:selected' ).text()
	    	);
	    }

	    if (id === "role") {
			$("#roleId").val(valor.id);
			$('#select2-roleId-container').html(
				$( '#roleId option:selected' ).text()
			);
		}

	});
}

function deleteUsuario(idUsuario){

	bootbox.confirm({
	    title: 'ATENÇÃO!',
	    message: 'Deseja Realmente Excluir este Usuário ?',
	    buttons: {
	        'cancel': {
	            label: 'Não',
	            className: 'btn-default pull-left'
	        },
	        'confirm': {
	            label: 'SIM',
	            className: 'btn-danger pull-right'
	        }
	    },
	    callback: function(result) {
	    	if(result){
	    		$.ajax({
	    			url: "usuario/" + idUsuario,
	    			method: "DELETE",
	    			contentType: 'application/json; charset=utf-8',
					success: function() {
						toastr.success("Registro excluído com sucesso!");
						buscaUsuarios(0);
					},
					error: function(data) {
						toastr.error(data);
					}
	    		});
	    	}
	    }
	});

}


function montaPaginacao(totalPaginas, pagina){
	let cont = 1;
	pagina += 1

	$('.divPaginacao').bootpag({
		total: totalPaginas,
		page: pagina,
		maxVisible: 5,
		leaps: true,
		firstLastUse: true,
		first: '←',
		last: '→',
		wrapClass: 'pagination',
		activeClass: 'active',
		disabledClass: 'disabled',
		nextClass: 'next',
		prevClass: 'prev',
		lastClass: 'last',
		firstClass: 'first'
	}).on("page", function(event, num){
		if(cont > 1)
			return

		buscaUsuarios(num -1);
		cont +=1;
	});

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
	
	$("#accessBlocked").val("false");
	$('#select2-accessBlocked-container').html(
			$( '#accessBlocked option:selected' ).text()
	);
	
	$('.campoObrigratorio').each(function () {
		$(this).removeClass('has-error');
		$(this).removeClass('has-success');
	});


	$("#" + form + " .fa").removeClass("fa-warning");
	$("#" + form + " .fa").removeClass("fa-check");
	$("#" + form + " .alert-danger").hide();
}
