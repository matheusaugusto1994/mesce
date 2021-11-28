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

		$.each(data.headers, function (key, header) {
			headerTh += '<th><div align="center">' + header + '</div> </th>';
		});
		
		table = '<div class="portlet box blue">' +
							'<div class="portlet-title">' +
								'<div class="caption">' + 
									'<i class="fa fa-picture"></i>' + data.reportName +
								'</div>' + 
							'</div>' +
							'<div class="portlet-body">' +
								'<div class="table-responsive">' +
									'<table class="table table-condensed table-hover table-striped">' + 
										'<thead>' + 
											'<tr>' + headerTh + '</tr>' +
										'</thead>'+
										'<tbody>';



		$.each(data.content, function (key, value) {
			// table += 			'<tr>' +
			// 	                    '<td align="center">' + value.name + '</td>' +
			//                     '</tr>';
			let content = ""
			Object.getOwnPropertyNames(value).forEach(function (val, i, arr) {
				if (val !== "link") {
					content += '<td align="center">' + value[val] + '</td>';
				}
			})

			if (value["link"] != null) {
				let func = "abreOpcoesTotais('" + value['link'] + "')"
				content += '<td align="center">' +
						'<a style="cursor:pointer;" data-original-title="Filtrar" class="btn btn-icon-only blue tooltips" onClick=' + func + '>' +
							'<i class="fa fa-search"></i>' +
						'</a>' +
					'</td>'
			}

			table += '<tr>' + content + '</tr>';
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

function abreFechaRelatorio(backPage) {
	if(backPage && $("#pageHome").attr("style") == "display: none;"){
		$("#relatorio").hide();
		$("#pageHome").show(700);
		$("#brasao").show(700);
	} else {
		$("#relatorio").show();
		$("#pageHome").hide(700);
		$("#brasao").hide();
	}
}