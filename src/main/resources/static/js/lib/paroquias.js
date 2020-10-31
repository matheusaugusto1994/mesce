
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
	
//	var dataFormJSON = $("#" + form).find(':input').filter(function () {
//          return $.trim(this.value).length > 0
//	}).serializeJSON();
	
	var supervisor = new Array();
	$.each($("#supervisor").val(), function(key, s){
		supervisor.push({"usuario": {"id":s}});
	});
	
	var dataFormJSON = {
		"id" : ($("#id").val() == "" ? null : $("#id").val()),
		"nome" : $("#nome").val(),
		"endereco" : $("#endereco").val(),
		"telefone" : $("#telefone").val(),
		"regiao": {
			"id": $("#regiao").val()
		},
		"supervisor": supervisor
	};
	
	$.ajax({
		  url: "paroquia/save",
		  method: "POST",
		  data: JSON.stringify(dataFormJSON),
		  contentType: 'application/json; charset=utf-8',
		  success: function(data) {
			  var retorno = data.split("|");
			  
			  if(retorno[0]=="success"){
				  toastr.success(retorno[1]);
				  buscaParoquias(1);
				  abreForm();
			  }else{
				  toastr.error(retorno[1]);
			  }
			  
		  },
		  error: function(data) {
			  toastr.error("Ocorreu um Erro ao Salvar.");
			  abreForm();
		  }
	});

}

function buscaDados(){
	buscaParoquias(1);
}

function buscaParoquias(pagina){

	var queryBusca = $("#queryBusca").val();

	var request = $.ajax({
		url: "paroquia/" + pagina + "?q=" + queryBusca,
		method: "GET",
		contentType: 'application/json; charset=utf-8',
		success: function(data) {
			montaLista(data.dados);
			montaPaginacao(data.total, data.pagina);
			paroquiasList = data.dados;
		}});
	
}

var paroquiasList;

function montaLista(data){
	if(data.length >0){
		var obj = data;
		var paroquias = '<div class="portlet box blue">' +
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
												'<th><div align="center">Supervisor</div> </th>' +
											'</tr>' +
										'</thead>'+
										'<tbody>';
			$.each(data, function (key, value) {
				var supervisor = "";
				$.each(value.supervisor, function (key, s) {
					supervisor += (s.usuario.pessoa == null ? s.usuario.usuario : s.usuario.pessoa.nome) + ", ";
				});
				supervisor = (supervisor != "" ? supervisor.substr(0, supervisor.length -2) : '-');
					paroquias += 			'<tr>'+
												'<td align="center">' + value.nome + '</td>' +
												'<td align="center">' + (value.telefone == null ? '-' : value.telefone)  + '</td>' +
												'<td align="center">' + value.regiao.nome + '</td>' +
												'<td align="center">' + supervisor +  '</td>' +
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
		}else{
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
			$("#" + id).val(data.regiao.id);
			$('#select2-' + id + '-container').html(
				$( '#' + id + ' option:selected' ).text()
			);
	    }

	    if(id == "supervisor"){
	    	var selectedValues = new Array();
			$.each(data.supervisor, function(key, s) {
				selectedValues.push(s.usuario.id);
				$( '#' + id).select2().select2('val',selectedValues);
			});
			
//	    	$("#" + id).val(data.supervisor.id);
//			$('#select2-' + id + '-container').html(
//				$( '#' + id + ' option:selected' ).text()
//			);
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
		    	var json =  idParoquia;

				var request = $.ajax({
					  url: "paroquia/delete",
					  method: "POST",
					  data: JSON.stringify(json),
					  contentType: 'application/json; charset=utf-8',
				});
				
				request.success(function(data) {
					var retorno = data.split("|");
					if(retorno[0]=="success"){
						  toastr.success(retorno[1]);
						  buscaParoquias(1);
					  }else{
						  toastr.error(retorno[1]);
					  }
			  	});
				
				request.error(function(data) {
					toastr.error("Ocorreu um erro ao Excluir");
				});
	    	}
	    }
	});

}


function montaPaginacao(data,pagina){
	var paginacao;
	var paginaAnterior;
	var proximaPagina;
	var limite = 10;
	var totPaginas = parseInt(data)/limite;

	if(Math.round(totPaginas) < totPaginas){
		totPaginas = totPaginas + 1;
		totPaginas = Math.round(totPaginas);
	}else{
		totPaginas = Math.round(totPaginas);
	}

	if(pagina == 0){
		pagina=1;
	}

	var cont=1

	$('.divPaginacao').bootpag({
	    total: totPaginas,
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

		if(cont > 1){return};

		buscaParoquias(num);
		//topPage();
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

