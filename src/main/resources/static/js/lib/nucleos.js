
function abreForm(){

	if( $("#divCadastro").attr("style") == "display: none;" ){
		$("#divCadastro").show(700);
		$("#btnNovoCadastro").html("Fechar");

	}else{
		$("#divCadastro").hide(700);
		$("#btnNovoCadastro").html("Abrir Cadastro");
		limpaCampos("formNucleos");
	}

}

function salvarDados(){
	
	var form = "formNucleos";
	
	if( validaCampos(form) == false ){
		return;
	}
	
	var dataFormJSON = $("#" + form).find(':input').filter(function () {
          return $.trim(this.value).length > 0
	}).serializeJSON();
	
	$.ajax({
		  url: "nucleo/save",
		  method: "POST",
		  data: JSON.stringify(dataFormJSON),
		  contentType: 'application/json; charset=utf-8',
		  success: function(data) {
			  var retorno = data.split("|");
			  
			  if(retorno[0]=="success"){
				  toastr.success(retorno[1]);
				  buscaNucleos(1);
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
	buscaNucleos(1);
}

function buscaNucleos(pagina){

	var queryBusca = $("#queryBusca").val();
	
	var json = {
		"pagina" : pagina,
		"filtro" : queryBusca
	}

	var request = $.ajax({
		  url: "nucleo/list",
		  data: JSON.stringify(json),
		  method: "POST",
		  contentType: 'application/json; charset=utf-8',
	});
	
	request.success(function(data) {
		montaLista(data.dados);
		montaPaginacao(data.total, data.pagina);
		nucleosList = data.dados;
  	});
	
}

var nucleosList;

function montaLista(data){
	if(data.length >0){
		var obj = data;
		var nucleos = '<div class="portlet box blue">' +
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
												'<th><div align="center">Vice-Coordenador</div> </th>' +
											'</tr>' +
										'</thead>'+
										'<tbody>';
			$.each(data, function (key, value) {
					if(value.vice != null){
						var vice = ((value.vice.pessoa == null) ? value.vice.usuario : value.vice.pessoa.nome);
					}else{
						var vice = "-";
					}
					nucleos += 			'<tr>'+
												'<td align="center">' + value.nome + '</td>' +
												'<td align="center">' + value.pastoral.nome + '</td>' +
												'<td align="center">' + ((value.coordenador.pessoa == null) ? value.coordenador.usuario : value.coordenador.pessoa.nome) +  '</td>' +
												'<td align="center">' + vice +  '</td>' +
												'<td align="center">' +  
													'<a style="cursor:pointer;" data-original-title="Editar" class="btn btn-icon-only blue tooltips" onClick="carregaCampos('+ key +');">' + 
	                                                	'<i class="fa fa-edit"></i>' + 
	                                                '</a>' +
													'<a style="cursor:pointer;" data-original-title="Excluir" class="btn btn-icon-only blue tooltips" onClick="deleteNucleo('+ value.id +');">' + 
	                                                	'<i class="fa fa-remove"></i>' + 
	                                                '</a>' +
												'</td>' +
											'</tr>';
			});						
				nucleos += 			'</tbody>'+
									'</table>'+
								'</div>' + 
							'</div>'  
						'</div>';
		}else{
			nucleos = '<div class="alert alert-danger">' + 
            				'Nenhum Resultado Encontrado.' +
       				   '</div>';
		}
		$("#divListaNucleos").html(nucleos);
		
		$(".tooltips").tooltip()                        
}

function carregaCampos(key){

	if(!$("#divCadastro").is(":visible")){
		abreForm();
	}

	limpaCampos("formNucleos");

	var data = nucleosList[key]
	$.each(data, function(id, valor) {
	    $("#" + id).val(valor);
	    
	    if(id == "pastoral"){
			$("#" + id).val(data.pastoral.id);
			$('#select2-' + id + '-container').html(
				$( '#' + id + ' option:selected' ).text()
			);
	    }

	    if(id == "coordenador"){
	    	if(data.coordenador == null){
	    		var idCoordenador ="";
	    	}else{
	    		var idCoordenador =data.coordenador.id;
	    	}
	    		
	    	$("#" + id).val(idCoordenador);
			$('#select2-' + id + '-container').html(
				$( '#' + id + ' option:selected' ).text()
			);
	    }

	    if(id == "vice"){
	    	if(data.vice == null){
	    		var idVice ="";
	    	}else{
	    		var idVice =data.vice.id;
	    	}
	    	$("#" + id).val(idVice);
	    	$('#select2-' + id + '-container').html(
	    			$( '#' + id + ' option:selected' ).text()
	    	);
	    }

	});
}

function deleteNucleo(idNucleo){

	bootbox.confirm({
	    title: 'ATENÇÃO!',
	    message: 'Deseja Realmente Excluir esta Núcleo ?',
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
		    	var json =  idNucleo;

				var request = $.ajax({
					  url: "nucleo/delete",
					  method: "POST",
					  data: JSON.stringify(json),
					  contentType: 'application/json; charset=utf-8',
				});
				
				request.success(function(data) {
					var retorno = data.split("|");
					if(retorno[0]=="success"){
						  toastr.success(retorno[1]);
						  buscaNucleos(1);
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

		buscaNucleos(num);
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

	$('.campoObrigratorio').each(function () {
		$(this).removeClass('has-error');
		$(this).removeClass('has-success');
	});


	$("#" + form + " .fa").removeClass("fa-warning");
	$("#" + form + " .fa").removeClass("fa-check");
	$("#" + form + " .alert-danger").hide();
}

