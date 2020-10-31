
function buscaDados(idRegiao){

	var json =  idRegiao;

	var request = $.ajax({
		  url: "organograma/montaDados",
		  method: "POST",
		  data: JSON.stringify(json),
		  contentType: 'application/json; charset=utf-8',
	});
	
	request.success(function(data) {
		montaOrganograma(data);
  	});
	
	request.error(function(data) {
		toastr.error("Ocorreu um erro");
	});

}

function montaOrganograma(data){
	var source = [];
	
	var coordenador = data.regiao.coordenador ? (data.regiao.coordenador.pessoa ? data.regiao.coordenador.pessoa.nome : data.regiao.coordenador.usuario) : "-";
	var vice = data.regiao.vice ? (data.regiao.vice.pessoa ? data.regiao.vice.pessoa.nome : data.regiao.vice.usuario) : "-";

	//O nó root será a região, entao começo por ele
	source.push({ id: "regiao_" + data.regiao.id, parentId: null, titulo: data.regiao.nome, responsavel: "Coordenador: " + coordenador,  responsavel2: "Vice: " + vice });
	//Percorro todas as paroquias da regiao e adiciono no nó
	$.each(data.paroquias, function (key, paroquias) {
		var paroquia = paroquias.paroquia, supervisor = "";
		$.each(paroquia.supervisor, function (key, s) {
			supervisor += (s.usuario.pessoa ? s.usuario.pessoa.nome : s.usuario.usuario) + ', ';
		});
		supervisor = supervisor != "" ? supervisor.substr(0, supervisor.length -2) : '-';
		source.push({id: "paroquia_" + paroquia.id,  parentId: "regiao_" + data.regiao.id, titulo: paroquia.nome, responsavel: "Supervisor: " + supervisor,  responsavel2: ""});
		
		//Percorro todas as pastorais
		$.each(paroquias.pastorais, function (key, pastorais) {
			coordenador = pastorais.pastoral.coordenador ? (pastorais.pastoral.coordenador.pessoa ? pastorais.pastoral.coordenador.pessoa.nome : pastorais.pastoral.coordenador.usuario) : "-";
			vice = pastorais.pastoral.vice ? (pastorais.pastoral.vice.pessoa ? pastorais.pastoral.vice.pessoa.nome : pastorais.pastoral.vice.usuario) : "-";
			
			source.push({id: "paroquia_"+ paroquias.paroquia.id +"_pastoral_" + pastorais.pastoral.id,  parentId: "paroquia_" + paroquias.paroquia.id, titulo: pastorais.pastoral.nome, responsavel: "Coordenador: " + coordenador,  responsavel2: "Vice: " + vice});
			
			//Percorro todas os Nucleos
			$.each(pastorais.nucleos, function (key, nucleos) {
				coordenador = nucleos.nucleo.coordenador ? (nucleos.nucleo.coordenador.pessoa ? nucleos.nucleo.coordenador.pessoa.nome : nucleos.nucleo.coordenador.usuario) : "-";
				vice = nucleos.nucleo.vice ? (nucleos.nucleo.vice.pessoa ? nucleos.nucleo.vice.pessoa.nome : nucleos.nucleo.vice.usuario) : "-";
				
				source.push({id: "pastoral_"+ pastorais.pastoral.id +"_nucleo_" + nucleos.nucleo.id,  parentId: "paroquia_"+ paroquias.paroquia.id +"_pastoral_" + pastorais.pastoral.id, titulo: nucleos.nucleo.nome, responsavel: "Coordenador: " + coordenador,  responsavel2: "Vice: " + vice });
				
				//Percorro todas as pessoas
				$.each(nucleos.pessoaNucleo, function (key, pessoaNucleo) {
					source.push({id: "nucleo_"+ nucleos.nucleo.id +"_pessoa_" + pessoaNucleo.pessoa.id,  parentId: "pastoral_"+ pastorais.pastoral.id +"_nucleo_" + nucleos.nucleo.id, titulo: pessoaNucleo.pessoa.nome, responsavel: "",  responsavel2: "" });
				});
			});
		});
	});
		
	$("#organograma").show();
	var orgChart = new getOrgChart(document.getElementById("organograma"),{
		theme: "sara",
		linkType: "M",
		primaryFields: ["titulo", "responsavel", "responsavel2"],
		photoFields: ["image"],
		gridView: true,
		enableSearch: true,
		enablePrint: true,
		enableDetailsView: false,
		enableEdit: false,
		expandToLevel: 3,
		dataSource: source
	});
	
	$( ".get-text" ).mouseenter( handlerIn ).mouseleave( handlerOut );
	
	function handlerIn(){
		var textValue = this.textContent;
		toastr.info(textValue)
	}
	
	function handlerOut(){
		toastr.remove();
	}
	
}

