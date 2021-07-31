
function abreForm(){

	if( $("#divCadastro").attr("style") == "display: none;" ){
		$("#divCadastro").show(700);
		$("#btnNovoCadastro").html("Fechar");

	}else{
		$("#divCadastro").hide(700);
		$("#btnNovoCadastro").html("Abrir Cadastro");
		limpaCampos("formSetores");
	}

}

function salvarDados(){
	
	var form = "formSetores";
	
	if( validaCampos(form) == false ){
		return;
	}
	
	var dataFormJSON = $("#" + form).find(':input').filter(function () {
          return $.trim(this.value).length > 0
	}).serializeJSON();
	
	$.ajax({
		  url: "setor",
		  method: "POST",
		  data: JSON.stringify(dataFormJSON),
		  contentType: 'application/json; charset=utf-8',
		  success: function(data) {
			  toastr.success("O registro foi salvo com sucesso!");
			  buscaSetores(0);
			  abreForm();
		  },
		  error: function(data) {
			  toastr.error("Erro ao salvar o registro!");
			  abreForm();
		  }
	});

}

function buscaSetores(pagina){

	var queryBusca = $("#queryBusca").val();

	$.ajax({
		url: "setor/all/" + pagina + "?q=" + queryBusca,
		method: "GET",
		contentType: 'application/json; charset=utf-8',
		success: function (data) {
			montaLista(data);
		}
	});
}

var setoresList;

function montaLista(data){
	let setores
	if(data && data.content && data.content.length) {
		let content = data.content
		setoresList = content

		setores = '<div class="portlet box blue">' +
							'<div class="portlet-title">' +
								'<div class="caption">' + 
									'<i class="fa fa-picture"></i>Lista de Pastorais' + 
								'</div>' + 
							'</div>' +
							'<div class="portlet-body">' +
								'<div class="table-responsive">' +
									'<table class="table table-condensed table-hover table-striped">' + 
										'<thead>' + 
											'<tr>' +
												'<th><div align="center">Nome</div> </th>' +
												'<th><div align="center">Pastoral</div> </th>' +
												'<th><div align="center">Coordenador</div> </th>' +
											'</tr>' +
										'</thead>'+
										'<tbody>';

		$.each(content, function (key, value) {
				let userName = value.user.name ? value.user.name : '-';

			setores += 			'<tr>'+
											'<td align="center">' + value.name + '</td>' +
											'<td align="center">' + value.paroquia.name + '</td>' +
											'<td align="center">' + userName +  '</td>' +
											'<td align="center">' +
												'<a style="cursor:pointer;" data-original-title="Editar" class="btn btn-icon-only blue tooltips" onClick="carregaCampos('+ key +');">' +
													'<i class="fa fa-edit"></i>' +
												'</a>' +
												'<a style="cursor:pointer;" data-original-title="Excluir" class="btn btn-icon-only blue tooltips" onClick="deleteSetor('+ value.id +');">' +
													'<i class="fa fa-remove"></i>' +
												'</a>' +
											'</td>' +
										'</tr>';
		});

		setores += 			'</tbody>'+
							'</table>'+
						'</div>' +
					'</div>'
				'</div>';

		if (data.pageable) {
			montaPaginacao(data.totalPages, data.number)
		}

	} else {
		setores = '<div class="alert alert-danger">' +
						'Nenhum Resultado Encontrado.' +
				   '</div>';
	}
	$("#divListaSetores").html(setores);

	$(".tooltips").tooltip()
}

function carregaCampos(key){

	if(!$("#divCadastro").is(":visible")){
		abreForm();
	}

	limpaCampos("formSetores");

	var data = setoresList[key]
	$.each(data, function(id, valor) {
	    $("#" + id).val(valor);
	    
	    if(id == "paroquia"){
			$("#paroquiaId").val(data.paroquia.id);
			$('#select2-paroquiaId-container').html(
				$( '#paroquiaId option:selected' ).text()
			);
	    }

	    if(id == "user"){
	    	$("#userId").val(data.user.id);
			$('#select2-userId-container').html(
				$( '#userId option:selected' ).text()
			);
	    }
	});
}

function deleteSetor(idSetor){

	bootbox.confirm({
	    title: 'ATENÇÃO!',
	    message: 'Deseja Realmente Excluir este Setor ?',
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
	    	if (result) {

				$.ajax({
					url: "setor/" + idSetor,
					method: "DELETE",
					contentType: 'application/json; charset=utf-8',
					success: function (data) {
						toastr.success("Registro excluído com sucesso!");
						buscaSetores(0);
					},
					error: function (data) {
						toastr.error("Erro ao excluir o registro.");

					}
				});
	    	}
	    }
	});
}


function montaPaginacao(totalPaginas, pagina) {
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

		buscaSetores(num -1);
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

