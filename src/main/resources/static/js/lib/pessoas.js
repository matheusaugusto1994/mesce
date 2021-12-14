function abreForm(){

	if( $("#divCadastro").attr("style") == "display: none;" ){
		$("#divCadastro").show(700);
		$("#btnNovoCadastro").html("Fechar");
		upload();

	}else{
		$("#divCadastro").hide(700);
		$("#btnNovoCadastro").html("Abrir Cadastro");
		clear("formPessoas");
	}

}

function formataDataEn(data){

	var dataSplit = data.split("/");
	var dataEn = dataSplit[2] + "-" + dataSplit[1] + "-" + dataSplit[0];

	return dataEn;
}

function save(){
	let form = "formPessoas";

	if( validaCampos(form) === false ){
		return;
	}

	waitingDialog.show('Salvando Informações');

	let dataFormJSON = $("#" + form).find(':input').filter(function () {
          return $.trim(this.value).length > 0
	}).serializeJSON(
			{
			  customTypes: {
				data: function(str) { // value is always a string
			      return formataDataEn(str);
			    }
			  }
			}
	);

    $.ajax({
		  url: "pessoa",
		  method: "POST",
		  data: JSON.stringify(dataFormJSON),
		  contentType: 'application/json',
		  success: function(data) {
			  toastr.success("O registro foi salvo com sucesso!");
			  buscaPessoas(0)
			  abreForm();
			  waitingDialog.hide();
		  },
		  error: function(data) {
			  toastr.error("Erro ao salvar o registro!");
			  waitingDialog.hide();
			  abreForm();
		  }
	});
}

function buscaPessoas(pagina){
	
	var queryBusca = $("#queryBusca").val();

	$.ajax({
		url: "pessoa/all/" + pagina + "?q=" + queryBusca,
		method: "GET",
		contentType: 'application/json; charset=utf-8',
		success: function(data) {
			montaLista(data);
		}
	});
}

var pessoasList;

function montaLista(data){
	if(data && data.content){
		let content = data.content

		pessoasList = content;
		var pessoas = '<div class="portlet box blue">' +
							'<div class="portlet-title">' +
								'<div class="caption">' + 
									'<i class="fa fa-picture"></i>Lista de Pessoas' + 
								'</div>' + 
							'</div>' +
							'<div class="portlet-body">' +
								'<div class="table-responsive">' +
									'<table class="table table-condensed table-hover table-striped">' + 
										'<thead>' + 
											'<tr>' +
												'<th><div align="center">Nome</div> </th>' +
												'<th><div align="center">Telefone</div> </th>' +
												'<th><div align="center">WhatsApp</div> </th>' +
												'<th><div align="center">Email</div> </th>' +
											'</tr>' +
										'</thead>'+
										'<tbody>';

			$.each(content, function (key, value) {
					pessoas += 			'<tr>'+
												'<td align="center">' + value.name + '</td>' +
												'<td align="center">' + (value.phone ? value.phone : '-') + '</td>' +
												'<td align="center">' + (value.whatsapp ? value.whatsapp : '-') + '</td>' +
												'<td align="center">' + (value.email ? value.email : '-') +  '</td>' +
												'<td align="center">' +
													'<a style="cursor:pointer;" data-original-title="Editar" class="btn btn-icon-only blue tooltips" onClick="carregaCampos('+ key +');">' + 
	                                                	'<i class="fa fa-edit"></i>' + 
	                                                '</a>' +
													'<a style="cursor:pointer;" data-original-title="Excluir" class="btn btn-icon-only blue tooltips" onClick="deletePessoa('+ value.id +');">' + 
	                                                	'<i class="fa fa-remove"></i>' + 
	                                                '</a>' +
												'</td>' +
											'</tr>';
			});

			pessoas += 			'</tbody>'+
								'</table>'+
							'</div>' +
						'</div>'
					'</div>';

		if (data.pageable){
			montaPaginacao(data.totalPages, data.number)
		}
	} else {
		pessoas = '<div class="alert alert-danger">' +
						'Nenhum Resultado Encontrado.' +
				   '</div>';
	}

	$("#divListaPessoas").html(pessoas);

	$(".tooltips").tooltip()
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

function carregaCampos(key){

	if(!$("#divCadastro").is(":visible")){
		abreForm();
	}

	clear("formPessoas");

	const data = pessoasList[key];

	$.each(data, function(id, valor) {
	    $("#" + id).val(valor);

	    if(id == "photoPath"){
	    	if(valor == null){
	    		$('#divFileInput').removeClass('fileinput-exists');
	    		$('#divFileInput').addClass('fileinput-new');
	    	}else{
	    		$('#divFileInput').removeClass('fileinput-new');
	    		$('#divFileInput').addClass('fileinput-exists');
		    	$('#divFilePreview').html('<img id="imgPessoa" style="max-height: 300px;">');

		    	carregaImagem(data.id);
	    	}
	    }

	    if(id == "roles"){
	    	if(data.roles[0] != undefined){
		    	$("#" + id).val(data.roles[0].id);
	    	}else{
	    		$("#" + id).val("");
	    	}
	    	$('#select2-' + id + '-container').html(
				$( '#' + id + ' option:selected' ).text()
			);
	    }


	});
}

function carregaImagem(id){
	$.ajax({
        type : 'GET',
        url : 'pessoa/' + id + '/photo',
        success : function(response) {
        	document.getElementById("imgPessoa").src = response;
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            console.info("Status: " + XMLHttpRequest);
            alert("Status: " + textStatus);
            alert("Error: " + errorThrown);
        }
    });
}

function deletePessoa(idPessoa){

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
				const request = $.ajax({
					url: "pessoa/" + idPessoa,
					method: "DELETE",
					contentType: 'application/json; charset=utf-8',
				});

				request.success(function(data) {
					toastr.success("Registro excluído com sucesso!");
					buscaPessoas(0);
	    		});
	    		
	    		request.error(function(data) {
	    			toastr.error(data);
	    		});
	    	}
	    }
	});
}


function montaPaginacao(totalPaginas, pagina){
	let cont = 1;

	if (pagina === 0) {
		pagina = 1
	} else {
		pagina++
	}

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

		if(cont > 1){return}

		buscaPessoas(num -1);
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

function clear(form){

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

	//$('#divFilePreview').html('<img src="http://www.placehold.it/200x150/EFEFEF/AAAAAA&amp;text=sem+imagem" alt="" /> ');
	$('#divFileInput').removeClass('fileinput-exists');
	$('#divFileInput').addClass('fileinput-new');
	upload();


	$("#" + form + " .fa").removeClass("fa-warning");
	$("#" + form + " .fa").removeClass("fa-check");
	$("#" + form + " .alert-danger").hide();
}

function upload(){
	$('#divFilePreview').html("");

	let $uploadCrop;

	function readFile(input) {
		if (input.files && input.files[0]) {
            let reader = new FileReader();

            reader.onload = function (e) {
            	$uploadCrop.croppie('bind', {
            		url: e.target.result
            	});
            	$('.divFilePreview').addClass('ready');
            }

            reader.readAsDataURL(input.files[0]);
			return true;
		}
	}

	$uploadCrop = $('#divFilePreview').croppie({
		url: '../../img/profile.jpg' ,
		viewport: {
			width: 230,
			height: 230,
			type: 'square'
		},
		boundary: {
			width: 250,
			height: 250
		},
		exif: true
	});

    getResultFoto();

	$('#photoPessoa').on('change', function () { readFile(this); });

	$('#divFilePreview').mouseup(function() {
		getResultFoto();
	});

	$('.page-container').mouseover(function() {
		getResultFoto();
	});


	function getResultFoto(){

		if ($('#photoPessoa').val() != "") {
			$uploadCrop.croppie('result', {
				type: 'canvas',
				size: 'viewport'
			}).then(function (resp) {
				$("#photoData").val(resp);
			});
		}else{
			$('#photoData').val('');
		}

	}

}

var waitingDialog = waitingDialog || (function ($) {
	'use strict';

	// Creating modal dialog's DOM
	var $dialog = $(
		'<div class="modal fade" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true" style="padding-top:15%; overflow-y:visible;">' +
		'<div class="modal-dialog modal-m">' +
		'<div class="modal-content">' +
			'<div class="modal-header"><h3 style="margin:0;"></h3></div>' +
			'<div class="modal-body">' +
				'<div class="progress progress-striped active" style="margin-bottom:0;"><div class="progress-bar" style="width: 100%"></div></div>' +
			'</div>' +
		'</div></div></div>');

	return {
		/**
		 * Opens our dialog
		 * @param message Custom message
		 * @param options Custom options:
		 * 				  options.dialogSize - bootstrap postfix for dialog size, e.g. "sm", "m";
		 * 				  options.progressType - bootstrap postfix for progress bar type, e.g. "success", "warning".
		 */
		show: function (message, options) {
			// Assigning defaults
			if (typeof options === 'undefined') {
				options = {};
			}
			if (typeof message === 'undefined') {
				message = 'Loading';
			}
			var settings = $.extend({
				dialogSize: 'm',
				progressType: '',
				onHide: null // This callback runs after the dialog was hidden
			}, options);

			// Configuring dialog
			$dialog.find('.modal-dialog').attr('class', 'modal-dialog').addClass('modal-' + settings.dialogSize);
			$dialog.find('.progress-bar').attr('class', 'progress-bar');
			if (settings.progressType) {
				$dialog.find('.progress-bar').addClass('progress-bar-' + settings.progressType);
			}
			$dialog.find('h3').text(message);
						// Adding callbacks
			if (typeof settings.onHide === 'function') {
				$dialog.off('hidden.bs.modal').on('hidden.bs.modal', function (e) {
					settings.onHide.call($dialog);
				});
			}
			// Opening dialog
			$dialog.modal();
		},
		/**
		 * Closes dialog
		 */
		hide: function () {
			$dialog.modal('hide');
		}
	};

})(jQuery);

$(function($){
	$("#postalCode").change(function(){
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
