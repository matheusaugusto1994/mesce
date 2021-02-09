function buscaContadores(){
	$.ajax({
		url: "report/counts",
		method: "GET",
		contentType: 'application/json; charset=utf-8',
		success: function(data) {
			montaCardTotalizador(data);
		},
		error: function(data) {
			toastr.error("Ocorreu um Erro.");
			return 0;
		}
	});
}

function montaCardTotalizador(data){
	if (data) {
		setTextSpan("totRegioes", data.totRegioes);
		setTextSpan("totParoquias", data.totParoquias);
		setTextSpan("totSetores", data.totSetores);
		setTextSpan("totPessoas", data.totPessoas);
	}
}

function setTextSpan(element, data) {
	$("#" + element).attr("data-value", data);
	$('#' + element).counterUp();
}

function abreOpcoesTotais(tpCard){
	$.ajax({
	  url: "report/general/" + tpCard,
	  method: "GET",
	  contentType: 'application/json; charset=utf-8',
	  async:false,
	  success: function(data) {
		  $("#detalhesRelatorio").html(montaLista(data));
		  $(".tooltips").tooltip() ;
		  abreFechaRelatorio();
	  },
	  error: function(data) {
		  toastr.error("Ocorreu um Erro.");
		  return 0;
	  }
	});
}

function montaLista(data){
	let table

	if (data) {
		let headerTh = '';

		$.each(data.header, function (key, header) {
			headerTh += '<th><div align="center">' + header + '</div> </th>';
		});
		
		table = '<div class="portlet box blue">' +
							'<div class="portlet-title">' +
								'<div class="caption">' + 
									'<i class="fa fa-picture"></i>Relatório Geral' +
								'</div>' + 
							'</div>' +
							'<div class="portlet-body">' +
								'<div class="table-responsive">' +
									'<table class="table table-condensed table-hover table-striped">' + 
										'<thead>' + 
											'<tr>' + headerTh + '</tr>' +
										'</thead>'+
										'<tbody>';

		$.each(data, function (key, value) {
			table += 			'<tr>' +
				                    '<td align="center">' + value.name + '</td>' +
			                    '</tr>';
		});

		table += 			'</tbody>'+
							'</table>'+
						'</div>' +
					'</div>'
				'</div>';
	} else {
		table = '<div class="alert alert-danger">' +
			        'Nenhum Resultado Encontrado.' +
			    '</div>';
	}

	return table;
}

function clearChildren(element){
	while (element.firstChild) {
		element.removeChild(element.firstChild);
	}
	return element;
}

function abreFechaRelatorio() {
	if( $("#pageHome").attr("style") == "display: none;" ){
		$("#relatorio").hide();
		$("#pageHome").show(700);
		$("#brasao").show(700);
	} else {
		$("#relatorio").show();
		$("#pageHome").hide(700);
		$("#brasao").hide();
	}
}