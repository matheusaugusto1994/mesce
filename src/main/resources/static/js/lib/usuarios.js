
function abreForm(){
	limpaCampos("formUsuarios");
	if( $("#divCadastro").attr("style") == "display: none;" ){
		$("#divCadastro").show(700);
		$("#btnNovoCadastro").html("Fechar");

	}else{
		$("#divCadastro").hide(700);
		$("#btnNovoCadastro").html("Abrir Cadastro");
		
	}

}

function salvarDados(){
	
	var form = "formUsuarios";
	var senha = $("#senha").val();
	var confirmaSenha = $("#confirmarSenha").val();
	
	if(senha != confirmaSenha){
		toastr.error("As Senhas Digitadas não Conferem");
		return;
	}
	
	if( validaCampos(form) == false ){
		return;
	}
	
	var usuario = $("#" + form).find(':input').filter(function () {
          return $.trim(this.value).length > 0
	}).serializeJSON();
	
	var dataFormJSON = new Object();
	dataFormJSON.usuario = usuario;
	dataFormJSON.roles = new Array();
	$.each($("#role").val(), function (key, value) {
		dataFormJSON.roles.push({"id" : value})		
	});
	
	$.ajax({
		  url: "usuario/save",
		  method: "POST",
		  data: JSON.stringify(dataFormJSON),
		  contentType: 'application/json; charset=utf-8',
		  success: function(data) {
			  
		  	  var retorno = data.split("|");
			  
			  if(retorno[0]=="success"){
				  toastr.success(retorno[1]);
				  buscaUsuarios(1);
				  abreForm();
			  }else{
				  toastr.error(retorno[1]);
			  }
			  
		  },
		  error: function(data) {
			  toastr.error(data);
			  abreForm();
		  }
	});

}

function buscaDados(){
	buscaUsuarios(1);
}

function buscaUsuarios(pagina){

	var queryBusca = $("#queryBusca").val();
	
	var json = {
		"pagina" : pagina,
		"filtro" : queryBusca
	}

	var request = $.ajax({
		  url: "usuario/list",
		  data: JSON.stringify(json),
		  method: "POST",
		  contentType: 'application/json; charset=utf-8',
	});
	
	request.success(function(data) {
		montaLista(data.dados);
		montaPaginacao(data.total, data.pagina);
		usuariosList = data.dados;
  	});
	
}

var usuariosList;

function montaLista(data){
	if(data.length >0){
		var obj = data;
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
			$.each(data, function (key, value) {
					usuarios += 			'<tr>'+
												'<td align="center">' + ((value.usuario.pessoa == null) ? " - " : value.usuario.pessoa.nome) + '</td>' +
												'<td align="center">' + value.usuario.usuario + '</td>' +
												'<td align="center">' + rolesToStringWithComaSeparated(value.roles) +  '</td>' +
												'<td align="center">' +  
													'<a style="cursor:pointer;" data-original-title="Editar" class="btn btn-icon-only blue tooltips" onClick="carregaCampos('+ key +');">' + 
	                                                	'<i class="fa fa-edit"></i>' + 
	                                                '</a>' +
													'<a style="cursor:pointer;" data-original-title="Excluir" class="btn btn-icon-only blue tooltips" onClick="deleteUsuario('+ value.usuario.id +');">' + 
	                                                	'<i class="fa fa-remove"></i>' + 
	                                                '</a>' +
												'</td>' +
											'</tr>';
			});						
				usuarios += 			'</tbody>'+
									'</table>'+
								'</div>' + 
							'</div>'  
						'</div>';
		}else{
			usuarios = '<div class="alert alert-danger">' + 
		    				'Nenhum Resultado Encontrado.' +
						   '</div>';
		}
		$("#divListaUsuarios").html(usuarios);
		
		$(".tooltips").tooltip()                        
}

function rolesToStringWithComaSeparated(list) {
	var result = "";
	$.each(list, function (key, value) {
		if (key == 0)
			result = value.descricao;
		else
			result += ', ' + value.descricao;
	});
	return result;
}

function carregaCampos(key){

	if(!$("#divCadastro").is(":visible")){
		abreForm();
	}

	limpaCampos("formUsuarios");

	var data = usuariosList[key].usuario;
	$.each(data, function(id, valor) {
	    $("#" + id).val(valor);
	    
	    if(id == "senha"){
			$("#" + id).val(valor);
			$("#confirmarSenha").val(valor);
	    }

	    if(id == "pessoa"){
	    	if(data.pessoa == null){
	    		idPessoa="";
	    	}else{
	    		idPessoa = data.pessoa.id;
	    	}
			$("#" + id).val(idPessoa);
			$('#select2-' + id + '-container').html(
				$( '#' + id + ' option:selected' ).text()
			);
	    }

	    if(id == "acessoBloqueado"){
	    	$("#" + id).val(valor);
	    	$('#select2-' + id + '-container').html(
	    			$( '#' + id + ' option:selected' ).text()
	    	);
	    }

	});
	
	var roles = usuariosList[key].roles;
	var selectedValues = new Array();
	$.each(roles, function(id, valor) {
		selectedValues.push(valor.id);
		$("#role").select2().select2('val',selectedValues);
	});
	$(".select2-container").attr('style', 'width: 100%');
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
	    		var json =  idUsuario;
	    		
	    		var request = $.ajax({
	    			url: "usuario/delete",
	    			method: "POST",
	    			data: JSON.stringify(json),
	    			contentType: 'application/json; charset=utf-8',
	    		});
	    		
	    		request.success(function(data) {
	    			var retorno = data.split("|");
	    			
	    			if(retorno[0]=="success"){
	    				toastr.success(retorno[1]);
	    				buscaUsuarios(1);
	    			}else{
	    				toastr.error(retorno[1]);
	    			}
	    		});
	    		
	    		request.error(function(data) {
	    			toastr.error(data);
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

		buscaUsuarios(num);
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
	$('#fotoPath').val('');

	$('.select2-selection__rendered').each(function () {
		$(this).html('Selecione');
	});
	
	$("#acessoBloqueado").val("N");
	$('#select2-acessoBloqueado-container').html(
			$( '#acessoBloqueado option:selected' ).text()
	);
	
	$('.campoObrigratorio').each(function () {
		$(this).removeClass('has-error');
		$(this).removeClass('has-success');
	});


	$("#" + form + " .fa").removeClass("fa-warning");
	$("#" + form + " .fa").removeClass("fa-check");
	$("#" + form + " .alert-danger").hide();
}
