function buscaRegioesETotais(){
	$.ajax({
		  url: "home/buscaRegioesETotais",
		  method: "POST",
		  contentType: 'application/json; charset=utf-8',
		  async:false,
		  success: function(data) {
			  montaCardTotalizador(data);
			  montaCardRegioes(data);
		  },
		  error: function(data) {
			  toastr.error("Ocorreu um Erro.");
			  return 0;
		  }
	});
}

function montaCardTotalizador(data){
	var totais = data.totalizador;
	if (totais) {
		setTextSpan("totRegioes", totais.regioes);
		setTextSpan("totParoquias", totais.paroquias);
		setTextSpan("totPastorais", totais.pastorais);
		setTextSpan("totNucleos", totais.nucloes);
		setTextSpan("totPessoas", totais.pessoas);
		setTextSpan("totUsuarios", totais.usuarios);
	}
}

function setTextSpan(element, data) {
	$("#" + element).attr("data-value", data);
	$('#' + element).counterUp();
}

function montaCardRegioes(data){
	var cards = "";
	var count =1;
	regioes = [];
	$.each(data.regioes, function (key, value) {
		regioes[value.regiao.id] = value;
		
		if(count ==1){
			cards += '<div class="row">';
		} 
		 
		 cards +=   	'<div class="col-lg-4 col-md-4 col-sm-6" >' + 
							'<div id="card" class="weater" style="cursor: pointer;" onClick="abreOpcoesRegiao('+ value.regiao.id +');">' + 
								'<div class="city-selected" style="background: '+ value.regiao.cor +';">' + 
									'<article>' + 
										'<div class="info">' + 
											'<div class="temp" style="color: '+ value.regiao.labelCor + ';">'+ value.regiao.nome +'</div>' +
										'</div>' +
									'</article>' + 
								'</div>' +
								'<div class="days" style="background: '+ value.regiao.cor +';">' +
									'<div class="row row-no-gutter">' +
										'<div class="col-md-3 col-sm-3 col-xs-3">' +
											'<div class="day">' +
												'<span style="color: '+ value.regiao.labelCor + ';">'+ value.totParoquias +'</span>' +
												'<h1 style="color: '+ value.regiao.labelCor + ';">Paróquias</h1>' +
											'</div>' +
										'</div>' +
										'<div class="col-md-3 col-sm-3 col-xs-3">' +
											'<div class="day">' +
												'<span style="color: '+ value.regiao.labelCor + ';">'+ value.totPastorais +'</span>' +
												'<h1 style="color: '+ value.regiao.labelCor + ';">Pastorais</h1>' +
											'</div>' +
										'</div>'+
										'<div class="col-md-3 col-sm-3 col-xs-3">' +
											'<div class="day">' +
												'<span style="color: '+ value.regiao.labelCor + ';">'+ value.totNucleos +'</span>' +
												'<h1 style="color: '+ value.regiao.labelCor + ';">Núcleos</h1>' +
											'</div>' +
										'</div>' +
										'<div class="col-md-3 col-sm-3 col-xs-3">' +
											'<div class="day">' +
												'<span style="color: '+ value.regiao.labelCor + ';">'+ value.totPessoas +'</span>' +
												'<h1 style="color: '+ value.regiao.labelCor + ';">Pessoas</h1>' +
											'</div>' +
										'</div>' +
									'</div>' +
								'</div>' +
							'</div>' +
						'</div>';
		 
		 if(count ==3){
			 cards +=	'</div>' +
			 			 '<div class="col-md-12">&nbsp;</div>';
			 count =0;
		 }					
		 
		 count +=1;
	});
	
	$("#cards").html(cards);
}

function abreOpcoesTotais(tpCard){
	$.ajax({
	  url: "relatorio/" + tpCard,
	  method: "POST",
	  data: JSON.stringify(0),
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
	if(data.rows && data.rows.length >0){
		var obj = data,
		    headerTh = '';
		$.each(data.header, function (key, header) {
			headerTh += '<th><div align="center">' + header + '</div> </th>';
		});
		
		var regioes = '<div class="portlet box blue">' +
							'<div class="portlet-title">' +
								'<div class="caption">' + 
									'<i class="fa fa-picture"></i>' + data.title +  
								'</div>' + 
							'</div>' +
							'<div class="portlet-body">' +
								'<div class="table-responsive">' +
									'<table class="table table-condensed table-hover table-striped">' + 
										'<thead>' + 
											'<tr>' + headerTh + '</tr>' +
										'</thead>'+
										'<tbody>';
			$.each(data.rows, function (key, row) {
				regioes += 			'<tr>';
				$.each(row.elements, function (key, value) {
					regioes += 			    '<td align="center">' + value + '</td>';
				});						
				regioes +=			'</tr>';
			});						
				regioes += 			'</tbody>'+
									'</table>'+
								'</div>' + 
							'</div>'  
						'</div>';
		}else{
			regioes = '<div class="alert alert-danger">' + 
		    				'Nenhum Resultado Encontrado.' +
						   '</div>';
		}
			
		return regioes;                       
}

function clearChildren(element){
	while (element.firstChild) {
		element.removeChild(element.firstChild);
	}
	return element;
}

function abreOpcoesRegiao(idRegiao){
	var regiao = regioes[idRegiao];
	
	if (regiao) {
		var detalhes =
			'<h3 class="page-title">'+regiao.regiao.nome+'</h3>'+
			'<div class=" col-lg-3 col-md-3 col-sm-6 col-xs-12"> '+
            '    <a class="dashboard-stat dashboard-stat-v2 card-color-custom" style="cursor: pointer;" onClick="listaPorRegiao(1, '+idRegiao+')"> '+
            '        <div class="visual" /> '+
            '        <div class="details"> '+
            '            <div class="number"> '+
            '                <span data-counter="counterup" data-value="1349">'+regiao.totParoquias+'</span> '+
            '            </div> '+
            '            <div class="desc"> Paróquias </div> '+
            '        </div> '+
            '    </a> '+
            '</div> '+
            '<div class=" col-lg-3 col-md-3 col-sm-6 col-xs-12"> '+
            '    <a class="dashboard-stat dashboard-stat-v2 card-color-custom" style="cursor: pointer;" onClick="listaPorRegiao(2, '+idRegiao+')"> '+
            '        <div class="visual" /> '+
            '        <div class="details"> '+
            '            <div class="number"> '+
            '                <span data-counter="counterup" data-value="1349">'+regiao.totPastorais+'</span> '+
            '            </div> '+
            '            <div class="desc"> Pastorais </div> '+
            '        </div> '+
            '    </a> '+
            '</div> '+
            '<div class=" col-lg-3 col-md-3 col-sm-6 col-xs-12"> '+
            '    <a class="dashboard-stat dashboard-stat-v2 card-color-custom" style="cursor: pointer;" onClick="listaPorRegiao(3, '+idRegiao+')"> '+
            '        <div class="visual" /> '+
            '        <div class="details"> '+
            '            <div class="number"> '+
            '                <span data-counter="counterup" data-value="1349">'+regiao.totNucleos+'</span> '+
            '            </div> '+
            '            <div class="desc"> Núcleos </div> '+
            '        </div> '+
            '    </a> '+
            '</div> '+
            '<div class=" col-lg-3 col-md-3 col-sm-6 col-xs-12"> '+
            '    <a class="dashboard-stat dashboard-stat-v2 card-color-custom" style="cursor: pointer;" onClick="listaPorRegiao(4, '+idRegiao+')"> '+
            '        <div class="visual" /> '+
            '        <div class="details"> '+
            '            <div class="number"> '+
            '                <span data-counter="counterup" data-value="1349">'+regiao.totPessoas+'</span> '+
            '            </div> '+
            '            <div class="desc"> Pessoas </div> '+
            '        </div> '+
            '    </a> '+
            '</div> ' +
            '<div id="detalhesPorRegiao" style="display: inline-block; width: 100%"></div>';
		
		$("#detalhesRelatorio").html(detalhes);
		$(".tooltips").tooltip()
		
		abreFechaRelatorio();
	}
}

function listaPorRegiao(tpCard, id){
	if (tpCard == 1)
		tpCard = "paroquias";
	else if (tpCard == 2)
		tpCard = "pastorais";
	else if (tpCard == 3)
		tpCard = "nucleos";
	else if (tpCard == 4)
		tpCard = "pessoas";
	
	$.ajax({
	  url: "relatorio/" + tpCard,
	  data: JSON.stringify(id),
	  contentType: 'application/json; charset=utf-8',
	  method: "POST",
	  async:false,
	  success: function(data) {
		  $("#detalhesPorRegiao").html(montaLista(data));
	  },
	  error: function(data) {
		  toastr.error("Ocorreu um Erro.");
		  return 0;
	  }
	});
}

function abreFechaRelatorio() {
	if( $("#pageHome").attr("style") == "display: none;" ){
		$("#relatorio").hide();
		$("#pageHome").show(700);
	} else {
		$("#relatorio").show();
		$("#pageHome").hide(700);
	}
}