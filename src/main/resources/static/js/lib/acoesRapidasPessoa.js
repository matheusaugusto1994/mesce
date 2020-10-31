function abreAcoes(acao){
	
	$(".acoes").hide();
	$("#div_" + acao).show();
	
	$(".navPessoas >li").each(function(){
		$(this).removeClass("active");
	});
	
	$("#li_" + acao).addClass("active");
	
}

function buscaPessoa(){
	var numCracha = $("#cracha").val();
	var pessoaId = $("#pessoa").val();
	
	if(numCracha == "" && pessoaId == ""){
		toastr.error("Informe o número do Cracha!");
		return;
	}
	
	var json =  numCracha ? numCracha : pessoaId,
		url = numCracha ? "pessoa/buscaPessoaPeloCracha" : "pessoa/buscaPessoaPeloId";

	var request = $.ajax({
		  url: url,
		  method: "POST",
		  data: JSON.stringify(json),
		  contentType: 'application/json; charset=utf-8',
	});
	
	request.success(function(data) {
		montaDivPessoa(data);
		
	});
	
	request.error(function(data) {
		toastr.error("Ocorreu um erro!" + data);
	});
	        
}

function montaDivPessoa(infoPessoa){
	if(infoPessoa && infoPessoa.pessoa){
		var pessoa = infoPessoa.pessoa; 
		$("#pessoa").val(pessoa.id);
		$('#select2-pessoa-container').html(
			$( '#pessoa option:selected' ).text()
		)
		$("#cracha").val(pessoa.cracha);
		
		$("#spanNomePessoa").html(pessoa.nome);	
		$("#spanRole").html("");
		$("#id").val(pessoa.id);
		verificaUsuarioPessoa(pessoa);
		if(pessoa.fotoPath != null){
			carregaImagem(pessoa.fotoPath);
		}else{
			document.getElementById("imgPessoa").src = "../img/profile.jpg";
		}
		$("#divError").hide();
		$("#divAcoesPessoa").show();
		limpaCampos("formRelacaoNucleos");
		limpaCampos("formReciclagem");
		
		abreAcoes('info');
		carregaInfo(infoPessoa);
		if (infoPessoa.hasEdit) {
			$("#div_delete_block_user").show();
			carregaNucleosPessoa();
			carregaDadosReciclagem(pessoa);
			$("#li_relacaoNucleos").show();
			$("#li_reciclagem").show();
		} else {
			$("#div_delete_block_user").hide();
			$("#li_relacaoNucleos").hide();
			$("#li_reciclagem").hide();
		}
	}else{
		$("#divAcoesPessoa").hide();
		$("#divError").show();
	}
		
}

function verificaUsuarioPessoa(pessoa){
	
	var json = pessoa.id;
	
	$.ajax({
		  url: "usuario/verificaUsuarioPessoa",
		  method: "POST",
		  data: JSON.stringify(json),
		  contentType: 'application/json; charset=utf-8',
		  success: function(data) {
			  if(data != ""){
				  $("#spanAcessoBloqueado").show();
				  $("#spanRole").html(data.roles[0].descricao);
				  $("#idUsuario").val(data.usuario.id);
				  if(data.usuario.acessoBloqueado == "S"){
					  $('#acessoBloqueado').bootstrapSwitch('state', true, true);
				  }else{
					  $('#acessoBloqueado').bootstrapSwitch('state', false, true);
				  }
				  
			  }else{
				  $("#spanAcessoBloqueado").hide();
				  $("#idUsuario").val("");
			  }
		  },
		  error: function(data) {
			  toastr.error("Ocorreu um Erro ao Salvar.");
		  }
	});
	
}

function carregaDadosReciclagem(data){
	
	$("#dtEntrada").val(formataDataPTBR(data.dtEntrada));
	$("#dtTreinamento").val(formataDataPTBR(data.dtTreinamento));
	$("#dtReciclagem").val(formataDataPTBR(data.dtReciclagem));
	$("#dtSaida").val(formataDataPTBR(data.dtSaida));
	
}

function carregaImagem(path){
	$.ajax({
        type : 'GET',
        url : 'pessoa/listImage?path=' + path,
        success : function(response) {
        	document.getElementById("imgPessoa").src = response;
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
        	toastr.error("A foto não foi encontrada!");
        }
    });
}

function buscaRegioes(){
	request = $.ajax({
		url: "regiao/listAll",
		method: "POST",
		async: false,
		contentType: 'application/json; charset=utf-8',
		success: function(data){
			montaComboRegioes(data) ==true
		}
	});
}

function montaComboRegioes(data){

	//Remove todos os options menos o primeiro
	$('#regiao').children('option:not(:first)').remove();

	$.each(data, function (key, value) {
        $("#regiao").append($("<option>").attr("value", value.id).text(value.nome));
	});
}

function buscaParoquias(idRegiao){

	if(!validaCombo('paroquia', idRegiao))
		return;

	var json = {"id": idRegiao};

	var request = $.ajax({
		  url: "paroquia/buscaParoquiasPorRegiao",
		  method: "POST",
		  data: JSON.stringify(json),
		  dataType : 'json',
		  async: false,
		  contentType: 'application/json; charset=utf-8',
	});

	request.done(function(data) {
		montaComboParoquias(data) ==true
  	});

}

function montaComboParoquias(data){

	//Remove todos os options menos o primeiro
	$('#paroquia').children('option:not(:first)').remove();

	$.each(data, function (key, value) {
        $("#paroquia").append($("<option>").attr("value", value.id).text(value.nome));
	});

	$("#paroquia").attr("disabled",false);
}

function buscaPastorais(idParoquia){

	if(!validaCombo('pastoral', idParoquia))
		return;

	var json = {"id": idParoquia};

	var request = $.ajax({
		  url: "pastoral/buscaPastoraisPorParoquia",
		  method: "POST",
		  data: JSON.stringify(json),
		  dataType : 'json',
		  async: false,
		  contentType: 'application/json; charset=utf-8',
	});

	request.done(function(data) {
		montaComboPastorais(data) ==true
  	});

}

function montaComboPastorais(data){

	//Remove todos os options menos o primeiro
	$('#pastoral').children('option:not(:first)').remove();

	$.each(data, function (key, value) {
        $("#pastoral").append($("<option>").attr("value", value.id).text(value.nome));
	});

	$("#pastoral").attr("disabled",false);

}


function buscaNucleos(idPastoral){

	if(!validaCombo('nucleo', idPastoral))
		return;

	var json = {"id": idPastoral};

	var request = $.ajax({
		  url: "nucleo/buscaNucleosPorPastoral",
		  method: "POST",
		  data: JSON.stringify(json),
		  dataType : 'json',
		  async: false,
		  contentType: 'application/json; charset=utf-8',
	});

	request.done(function(data) {
		montaComboNucleos(data);
  	});

}

function montaComboNucleos(data){
	//Remove todos os options menos o primeiro
//	$('#nucleo').children('option:not(:first)').remove();
	$('#nucleo').children('option').remove();

	$.each(data, function (key, value) {
        $("#nucleo").append($("<option>").attr("value", value.id).text(value.nome));
	});

	$("#nucleo").attr("disabled",false);
}

function carregaNucleosPessoa(){
	
	var idPessoa = $("#id").val();
	
	var json = idPessoa;
	
	$.ajax({
		  url: "pessoaNucleo/buscaNucleosPessoa",
		  method: "POST",
		  data: JSON.stringify(json),
		  method: "POST",
		  contentType: 'application/json; charset=utf-8',
		  success: function(data) {
			  carregaCamposNucleo(data)
		  },
		  error: function(data) {
			  toastr.error(data);
		  }
	});
}

function carregaCamposNucleo(data){

	if(data==""){
		return;
	}
	if (!data.regiaoId)
		buscaRegioes();
	else {
		var idRegiao = data.regiaoId;
		$("#regiao").val(idRegiao);
		$('#select2-regiao-container').html(
				$( '#regiao option:selected' ).text()
		);
		
		buscaParoquias(idRegiao);
		
		var idParoquia = data.paroquiaId;
		$("#paroquia").val(idParoquia);
		$('#select2-paroquia-container').html(
				$( '#paroquia option:selected' ).text()
		);
		
		buscaPastorais(idParoquia);
		
		var idPatoral = data.pastoralId;
		$("#pastoral").val(idPatoral);
		$('#select2-pastoral-container').html(
				$( '#pastoral option:selected' ).text()
		);

		buscaNucleos(idPatoral);
		
		var selectedValues = new Array();
		$.each(data.nucleoIds, function(id, nucleoId) {
			selectedValues.push(nucleoId);
			$("#nucleo").select2().select2('val',selectedValues);
		});
		$("#regiao").attr("disabled",true);
	}
	$("#paroquia").attr("disabled",true);
	$("#pastoral").attr("disabled",true);	
}

function salvarDadosReciclagem(){

	var dataFormJSON = $("#formReciclagem").find(':input').filter(function () {
        return $.trim(this.value).length > 0
	}).serializeJSON(
			{
			  customTypes: {
				data: function(str) { // value is always a string
			      return new Date(formataDataEn(str));
			    }
			  }
			}
	);
	
	$.ajax({
		  url: "pessoa/saveDadosReciclagem",
		  method: "POST",
		  data: JSON.stringify(dataFormJSON),
		  contentType: 'application/json; charset=utf-8',
		  success: function(data) {
			  toastr.success(data);
		  },
		  error: function(data) {
			  toastr.error("Ocorreu um Erro ao Salvar.");
		  }
	});
	
}

function salvaAcessoBloqueado(){
	
	var idUsuario= $("#idUsuario").val();
	
	if($("#acessoBloqueado").is(":checked")){
		var acessoBloqueadoSN = "S";
	}else{
		var acessoBloqueadoSN = "N";
	}
	
	var json = {
				"id":idUsuario,
				"acessoBloqueado":acessoBloqueadoSN
	};

	$.ajax({
		  url: "usuario/salveAcessoBloqueado",
		  method: "POST",
		  data: JSON.stringify(json),
		  contentType: 'application/json; charset=utf-8',
		  success: function(data) {
			  toastr.success(data);
		  },
		  error: function(data) {
			  toastr.error("Ocorreu um Erro ao Salvar.");
		  }
	});
	
}

function salvaNucleo(idNucleo){
	
	var idPessoa = $("#id").val();
	
	var json = {
			"pessoa" : {"id":idPessoa},
			"nucleo" : {"id":idNucleo}
	}
	
	$.ajax({
		  url: "pessoaNucleo/saveNucleo",
		  method: "POST",
		  data: JSON.stringify(json),
		  method: "POST",
		  contentType: 'application/json; charset=utf-8',
		  success: function(data) {
			  toastr.success(data);
		  },
		  error: function(data) {
			  toastr.error(data);
		  }
	});
	
}

function removeNucleo(idNucleo){
	
	var idPessoa = $("#id").val();
	
	var json = {
			"pessoa" : {"id":idPessoa},
			"nucleo" : {"id":idNucleo}
	}
	
	$.ajax({
		url: "pessoaNucleo/removeNucleo",
		method: "POST",
		data: JSON.stringify(json),
		method: "POST",
		contentType: 'application/json; charset=utf-8',
		success: function(data) {
			toastr.success(data);
		},
		error: function(data) {
			toastr.error(data);
		}
	});
	
}

function exluirPessoa(){
	
	var idPessoa = $("#id").val();
	
	bootbox.confirm({
	    title: 'ATENÇÃO!',
	    message: 'Deseja Realmente Excluir esta Pessoa ?',
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
	    		var json =  idPessoa;

				var request = $.ajax({
					  url: "pessoa/delete",
					  method: "POST",
					  data: JSON.stringify(json),
					  contentType: 'application/json; charset=utf-8',
				});
				
				request.success(function(data) {
					var retorno = data.split("|");
					  
					  if(retorno[0]=="success"){
						  toastr.success(retorno[1]);
						  location.reload();
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

function limpaCampos(form){

	$("#" + form)[0].reset();
	$("#pessoa").val('');

	$('.select2-selection__rendered').each(function () {
		if ( $(this).attr("id") != "select2-pessoa-container" )
			$(this).html('Selecione');
	});

	$("#regiao").attr("disabled",false);
	$("#paroquia").attr("disabled",false);
	$("#pastoral").attr("disabled",false);
	
	$('.multiSelect').each(function () {
		$(this).html('');
	});

	$('.campoObrigratorio').each(function () {
		$(this).removeClass('has-error');
		$(this).removeClass('has-success');
	});

	$("#" + form + " .fa").removeClass("fa-warning");
	$("#" + form + " .fa").removeClass("fa-check");
	$("#" + form + " .alert-danger").hide();
}

function formataDataEn(data){

	var dataSplit = data.split("/");
	var dataEn = dataSplit[2] + "-" + dataSplit[1] + "-" + dataSplit[0];

	return dataEn;
}

function limpaCracha(){
	$("#cracha").val("");
}

function carregaInfo(infoPessoa) {
	$("#nome").text(infoPessoa.pessoa.nome);
	$("#endereco").text(infoPessoa.pessoa.endereco);
	$("#email").text(infoPessoa.pessoa.email);
	$("#telefone").text(infoPessoa.pessoa.telefone);
	$("#dtNascimento").text(formataDataPTBR(infoPessoa.pessoa.dtNascimento));
	$("#numCracha").text(infoPessoa.pessoa.cracha);
	$("#spanDtEntrada").text(formataDataPTBR(infoPessoa.pessoa.dtEntrada));
	$("#spanDtTreinamento").text(formataDataPTBR(infoPessoa.pessoa.dtTreinamento));
	$("#spanDtReciclagem").text(formataDataPTBR(infoPessoa.pessoa.dtReciclagem));
	$("#spanDtSaida").text(formataDataPTBR(infoPessoa.pessoa.dtSaida));
	$("#funcao").text(infoPessoa.funcao);
	$("#usuario").text(infoPessoa.usuario);
	$("#regiaoLabel").text(infoPessoa.regiao);
	$("#paroquiaLabel").text(infoPessoa.paroquia);
	$("#pastoralLabel").text(infoPessoa.pastoral);
	$("#nucleoLabel").text(infoPessoa.nucleo);
}

function formataDataPTBR(data){

	if (!data)
		return;
	
	data = new Date(data);
	var dd = data.getUTCDate();
    var mm = data.getUTCMonth()+1; //January is 0!

    var yyyy = data.getUTCFullYear();
    if(dd<10){
        dd='0'+dd
    }
    if(mm<10){
        mm='0'+mm
    }
    var data = dd+'/'+mm+'/'+yyyy;

    return data;
}

function validaCombo(element, id){
	if (!id || id == ""){

		let combos = ["nucleo"];
		if (element == "paroquia")
			combos = ["paroquia", "pastoral", "nucleo"];
		else if (element == "pastoral")
			combos = ["pastoral", "nucleo"];

		combos.forEach(function (c) {			
			$("#select2-" + c + "-container").html('Selecione');
			$('#' + c).children('option:not(:first)').remove();
			$('#' + c).attr("disabled",true);
		});
		return false;
	}
	return true;
}

$(function(){
	$('#nucleo').on('select2:unselecting', function (evt) {
		var idNucleo = evt.params.args.data.id;
		removeNucleo(idNucleo);
	});

	$('#nucleo').on('select2:unselect', function (evt) {
		if($('#nucleo').val()==null){
			$("#regiao").attr("disabled",false);
			$("#paroquia").attr("disabled",false);
			$("#pastoral").attr("disabled",false);
		}
	});

	$('#nucleo').on('select2:selecting', function (evt) {
		var idNucleo = evt.params.args.data.id;
		salvaNucleo(idNucleo);
		$("#regiao").attr("disabled",true);
		$("#paroquia").attr("disabled",true);
		$("#pastoral").attr("disabled",true);
	});


	$('#acessoBloqueado').on('switchChange.bootstrapSwitch', function () {
		salvaAcessoBloqueado();
	});
	
});