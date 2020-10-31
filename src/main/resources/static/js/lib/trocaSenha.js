function trocaSenha() {
	$.fancybox.open({
		src  : '/usuario/trocaSenha', 
		type : 'iframe' 
//		opts : {} // Object containing item options (optional)
	  });
}

function salvaSenha(){
	
	var novaSenha = $("#novaSenha").val();
	var confirmaNovaSenha = $("#confirmaNovaSenha").val();

	
	if(novaSenha == ""){
		toastr.error("Informe a Senha.");
		return;
	}

	if(confirmaNovaSenha == ""){
		toastr.error("Confirme sua Nova Senha.");
		return;
	}
	
	if(novaSenha != confirmaNovaSenha){
		toastr.error("As Senhas Digitadas não Conferem");
		return;
	}
	
	$.ajax({
		  url: "/usuario/trocaSenha/alterar",
		  method: "POST",
		  data: novaSenha,
		  contentType: 'application/json; charset=utf-8',
		  success: function(data) {
			  toastr.success("Senha Alterada com sucesso!");
			  parent.$.fancybox.close();
		  },
		  error: function(data) {
			  toastr.error("Ocorreu um problema para alterar a senha!");
		  }
	});
	
}