var regioesList;

function abreForm() {

    if ($("#divCadastro").attr("style") == "display: none;") {
        $("#divCadastro").show(700);
        $("#btnNovoCadastro").html("Fechar");

    } else {
        $("#divCadastro").hide(700);
        $("#btnNovoCadastro").html("Abrir Cadastro");
        limpaCampos("formRegioes");
    }
}

function salvarDados() {
    var form = "formRegioes";

    if (validaCampos(form) == false) {
        return;
    }

    var dataFormJSON = $("#" + form).find(':input').filter(function () {
        return $.trim(this.value).length > 0
    }).serializeJSON();

    $.ajax({
        url: "regiao",
        method: "POST",
        data: JSON.stringify(dataFormJSON),
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            toastr.success(data);
            montaLista(data)
            limpaCampos(form);
            abreForm();
        },
        error: function (data) {
            toastr.error(data);
            abreForm();
        }
    });
}

function buscaRegioes(pagina) {

    var queryBusca = $("#queryBusca").val();

    $.ajax({
        url: "regiao/" + pagina + "?q=" + queryBusca,
        method: "GET",
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            montaLista(data);
        }
    })

}

function buscaUsuarios() {

    var json = {
        "pagina": 0,
        "filtro": ""
    }

    var request = $.ajax({
        url: "usuario/list",
        data: JSON.stringify(json),
        method: "POST",
        contentType: 'application/json; charset=utf-8',
    });

    request.success(function (data) {
        montaCombo("coordenador", data.dados);
    });
}

function montaLista(data) {
	if(data.content && data.content.length) {
		let content = data.content
        regioesList = content;

        var regioes = '<div class="portlet box blue">' +
			'<div class="portlet-title">' +
            '<div class="caption">' +
            '<i class="fa fa-picture"></i>Lista de Regiões' +
            '</div>' +
            '</div>' +
            '<div class="portlet-body">' +
            '<div class="table-responsive">' +
            '<table class="table table-condensed table-hover table-striped">' +
            '<thead>' +
            '<tr>' +
            '<th><div align="center">Nome</div> </th>' +
            '<th><div align="center">Coordenador</div> </th>' +
            '<th><div align="center">Ações</div> </th>' +
            '</tr>' +
            '</thead>' +
            '<tbody>';

        $.each(content, function (key, value) {
            var userName = value.userName ? value.userName : '-';

            regioes += '<tr>' +
                '<td align="center">' + value.name + '</td>' +
                '<td align="center">' + userName + '</td>' +
                '<td align="center">' +
                '<a style="cursor:pointer;" data-original-title="Editar" class="btn btn-icon-only blue tooltips" onClick="carregaCampos(' + key + ');">' +
                '<i class="fa fa-edit"></i>' +
                '</a>' +
                '<a style="cursor:pointer;" data-original-title="Excluir" class="btn btn-icon-only blue tooltips" onClick="deleteRegiao(' + value.id + ');">' +
                '<i class="fa fa-remove"></i>' +
                '</a>' +
                '</td>' +
                '</tr>';
        });
        regioes += '</tbody>' +
            '</table>' +
            '</div>' +
            '</div>'
        '</div>';

		if (data.pageable){
			montaPaginacao(data.totalPages, data.pageable.pageNumber)
		}
    } else {
        regioes = '<div class="alert alert-danger">' +
            'Nenhum Resultado Encontrado.' +
            '</div>';
    }

    $("#divListaRegioes").html(regioes);

    $(".tooltips").tooltip()
}

function montaCombo(idCombo, dados) {
    var select = document.getElementById(idCombo);

    $.each(dados, function (i, d) {
        var element = document.createElement("option");
        element.textContent = d.usuario;
        element.value = d.id;
        select.appendChild(element);
    });
}

function carregaCampos(key) {

    if (!$("#divCadastro").is(":visible")) {
        abreForm();
    }

    const data = regioesList[key];

    $.each(data, function (id, valor) {
        $("#" + id).val(valor);

        if (id == "coordenador") {
            $("#" + id).val(data.userId);
            $('#select2-' + id + '-container').html(
                $('#' + id + ' option:selected').text()
            );
//	    	$.each(data.coordenador, function (key, value) {
//	    		if(key == "id"){
//	    			$("#" + id).val(value);
//	    			
//	    			
//
//	    		}
//	    	});
        }
    });
}

function deleteRegiao(idRegiao) {

    bootbox.confirm({
        title: 'ATENÇÃO!',
        message: 'Deseja Realmente Excluir esta Região ?',
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
        callback: function (result) {
            if (result) {
                var json = idRegiao;

                var request = $.ajax({
                    url: "regiao/delete",
                    method: "POST",
                    data: JSON.stringify(json),
                    contentType: 'application/json; charset=utf-8',
                });

                request.success(function (data) {
                    toastr.success(data);
                    buscaRegioes(1)
                });

                request.error(function (data) {
                    toastr.error(data);
                })
            }
        }
    });

}

function montaPaginacao(data, pagina) {
    var paginacao;
    var paginaAnterior;
    var proximaPagina;
    var limite = 10;
    var totPaginas = parseInt(data) / limite;

    if (Math.round(totPaginas) < totPaginas) {
        totPaginas = totPaginas + 1;
        totPaginas = Math.round(totPaginas);
    } else {
        totPaginas = Math.round(totPaginas);
    }

    if (pagina == 0) {
        pagina = 1;
    }

    var cont = 1

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
    }).on("page", function (event, num) {

        if (cont > 1) {
            return
        }
        ;

        buscaRegioes(num);
        //topPage();
        cont += 1;
    });

}

function topPage() {
    $("#back-to-top").click();
}

function validaCampos(form) {

    $("#" + form).submit();

    var valid = true;

    $('.campoObrigratorio').each(function () {
        if ($(this).hasClass('has-error')) {
            valid = false;
        }
    });

    return valid;

}

function limpaCampos(form) {

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
