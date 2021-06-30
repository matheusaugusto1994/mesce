
function abreForm(){

	if( $("#divCadastro").attr("style") == "display: none;" ){
		$("#divCadastro").show(700);
		$("#btnNovoCadastro").html("Fechar");

	}else{
		$("#divCadastro").hide(700);
		$("#btnNovoCadastro").html("Abrir Cadastro");
		limpaCampos("formParoquias");
	}

}

function salvarDados(){
	
	var form = "formParoquias";
	
	if( validaCampos(form) == false ){
		return;
	}
	
	var dataFormJSON = $("#" + form).find(':input').filter(function () {
         return $.trim(this.value).length > 0
	}).serializeJSON();
	
	$.ajax({
		  url: "paroquia",
		  method: "POST",
		  data: JSON.stringify(dataFormJSON),
		  contentType: 'application/json; charset=utf-8',
		  success: function(data) {
			  toastr.success("O registro foi salvo com sucesso!");
			  buscaParoquias(0);
			  abreForm();

		  },
		  error: function(data) {
			  toastr.error("Erro ao salvar o registro!");
			  abreForm();
		  }
	});

}

function buscaParoquias(pagina){

	let queryBusca = $("#queryBusca").val();

	$.ajax({
		url: "paroquia/" + pagina + "?q=" + queryBusca,
		method: "GET",
		contentType: 'application/json; charset=utf-8',
		success: function(data) {
			montaLista(data);
		}
	});
}

var paroquiasList;

function montaLista(data){
	let paroquias;
	if(data && data.content && data.content.length){
		let content = data.content
		paroquiasList = content

		paroquias = '<div class="portlet box blue">' +
			'<div class="portlet-title">' +
			'<div class="caption">' +
			'<i class="fa fa-picture"></i>Lista de Paróquias' +
			'</div>' +
			'</div>' +
			'<div class="portlet-body">' +
			'<div class="table-responsive">' +
			'<table class="table table-condensed table-hover table-striped">' +
			'<thead>' +
			'<tr>' +
			'<th><div align="center">Nome</div> </th>' +
			'<th><div align="center">Telefone</div> </th>' +
			'<th><div align="center">Região</div> </th>' +
			'<th><div align="center">Coordenador</div> </th>' +
			'</tr>' +
			'</thead>' +
			'<tbody>';

		$.each(content, function (key, value) {
				let userName = value.user.name ? value.user.name : '-';
				paroquias += 			'<tr>'+
											'<td align="center">' + value.name + '</td>' +
											'<td align="center">' + value.phone + '</td>' +
											'<td align="center">' + value.regiao.name + '</td>' +
											'<td align="center">' + userName + '</td>' +
											'<td align="center">' +
												'<a style="cursor:pointer;" data-original-title="Editar" class="btn btn-icon-only blue tooltips" onClick="carregaCampos('+ key +');">' +
													'<i class="fa fa-edit"></i>' +
												'</a>' +
												'<a style="cursor:pointer;" data-original-title="Excluir" class="btn btn-icon-only blue tooltips" onClick="deleteParoquia('+ value.id +');">' +
													'<i class="fa fa-remove"></i>' +
												'</a>' +
											'</td>' +
										'</tr>';
			});

			paroquias += 			'</tbody>'+
								'</table>'+
							'</div>' +
						'</div>'
					'</div>';

		if (data.pageable) {
			montaPaginacao(data.totalPages, data.number)
		}

	} else {
		paroquias = '<div class="alert alert-danger">' +
						'Nenhum Resultado Encontrado.' +
				   '</div>';
	}
	$("#divListaParoquias").html(paroquias);

	$(".tooltips").tooltip()
}

function carregaCampos(key){

	if(!$("#divCadastro").is(":visible")){
		abreForm();
	}

	limpaCampos("formParoquias");

	var data = paroquiasList[key]
	$.each(data, function(id, valor) {
	    $("#" + id).val(valor);
	    
	    if(id == "regiao"){
			$("#regiaoId").val(data.regiao.id);
			$('#select2-regiaoId-container').html(
				$( '#regiaoId option:selected' ).text()
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

function deleteParoquia(idParoquia){

	bootbox.confirm({
	    title: 'ATENÇÃO!',
	    message: 'Deseja Realmente Excluir esta Paróquia ?',
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
					url: "paroquia/" + idParoquia,
					method: "DELETE",
					contentType: 'application/json; charset=utf-8',
					success: function (data) {
						toastr.success("Registro excluído com sucesso!");
						buscaParoquias(0);
					},
					error: function (data) {
						toastr.success("Erro ao excluir o registro!");
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

		buscaParoquias(num -1);
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
	
//	$('.multiSelect').each(function () {
//		$(this).html('');
//	});

	$('.campoObrigratorio').each(function () {
		$(this).removeClass('has-error');
		$(this).removeClass('has-success');
	});


	$("#" + form + " .fa").removeClass("fa-warning");
	$("#" + form + " .fa").removeClass("fa-check");
	$("#" + form + " .alert-danger").hide();
}

$(function($){
	$("#cep").change(function(){
		$.ajax({
			url: 'https://viacep.com.br/ws/'+$(this).val()+'/json/unicode/',
			dataType: 'json',
			success: function(resposta){
			    if(resposta.logradouro){
				$("#address").val(resposta.logradouro+ ', ' + resposta.bairro + ' - ' + resposta.localidade + ' - ' + resposta.uf);
				}
			}
		});
	});
});


