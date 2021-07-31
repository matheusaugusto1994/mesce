function buscaPessoa(){
	let pessoaId = $("#pessoa").val();

	$.ajax({
		url: "pessoa/paroquia/" + pessoaId,
		method: "GET",
		contentType: 'application/json; charset=utf-8',
		success: function(data) {
			montaDivPessoa(data);
		},
		error: function (data) {
			toastr.error("Ocorreu um erro ao buscar o cadastro da pessoa!" + data);
		}
	});
}

function montaDivPessoa(data) {
	if(data.pessoa){
		limpaCampos();
		$("#pessoa").val(data.pessoa.id);
		$('#select2-pessoa-container').html(
			$( '#pessoa option:selected' ).text()
		)

		$("#spanNomePessoa").html(data.pessoa.name);
		$("#spanRole").html("");
		$("#id").val(data.pessoa.id);

		if(data.pessoa.photoPath != null){
			carregaImagem(data.pessoa.id);
		}else{
			document.getElementById("imgPessoa").src = "../img/profile.jpg";
		}

		$("#divError").hide();
		$("#divAcoesPessoa").show();

		carregaInfo(data);
		carregaCombos(data);
	}else{
		$("#divAcoesPessoa").hide();
		$("#divError").show();
	}
		
}

function carregaImagem(id){
	$.ajax({
		type : 'GET',
		url : 'pessoa/' + id + '/photo',
		success : function(response) {
			document.getElementById("imgPessoa").src = response;
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			toastr.error("A foto não foi encontrada!");
		}
	});
}

// function buscaRegioes(){
// 	$.ajax({
// 		url: "regiao/all",
// 		method: "GET",
// 		contentType: 'application/json; charset=utf-8',
// 		success: function (data) {
// 			$('#regioes').children('option:not(:first)').remove();
//
// 			$.each(data, function (key, value) {
// 				$("#regioes").append($("<option>").attr("value", value.id).text(value.name));
// 			});
// 		}
// 	})
// }

function buscaParoquias(regiaoId, paroquiaId){
	if (!regiaoId) {
		return
	}

	$.ajax({
		url: "paroquia/regiao/" + regiaoId,
		method: "GET",
		contentType: 'application/json; charset=utf-8',
		success: function (data) {
			$('#paroquia').children('option:not(:first)').remove();

			$.each(data, function (key, value) {
				$("#paroquia").append($("<option>").attr("value", value.id).text(value.name));
			});

			$("#paroquia").attr("disabled",false);

			if (paroquiaId) {
				carregaParoqia(paroquiaId);
			}
		}
	});
}

function carregaCombos(data){
	if (data.paroquia) {
		let idRegiao = data.paroquia.regiao.id;
		$("#regioes").val(idRegiao);
		$('#select2-regioes-container').html(
				$( '#regioes option:selected' ).text()
		);
		
		buscaParoquias(idRegiao, data.paroquia.id);
		
		$("#paroquia").attr("disabled",true);
	}
}

function carregaParoqia(paroquiaId) {
	$("#paroquia").val(paroquiaId);
	$('#select2-paroquia-container').html(
		$( '#paroquia option:selected' ).text()
	);

	// $("#regioes").attr("disabled",true);
}

function associaParoquia(paroquiaId){
	let json = {
			"pessoa" : $("#pessoa").val(),
			"paroquia" : paroquiaId
	}

	$.ajax({
		url: "/pessoa/paroquia",
		method: "POST",
		data: JSON.stringify(json),
		contentType: 'application/json',
		success: function(data) {
			toastr.success("O ministro foi associado com sucesso!");
		},
		error: function(data) {
			toastr.error("Erro ao associar o ministro!");
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

function limpaCampos(){
	$("#pessoa").val('');

	$('.select2-selection__rendered').each(function () {
		if ( $(this).attr("id") != "select2-pessoa-container" )
			$(this).html('Selecione');
	});

	$("#regioes").attr("disabled",false);
	$("#paroquia").attr("disabled",false);
}

function carregaInfo(data) {
	$("#nome").text(data.pessoa.name);
	$("#endereco").text(data.pessoa.address + data.pessoa.numberAddress);
	$("#email").text(data.pessoa.email);
	$("#telefone").text(data.pessoa.phone);
}