
function getConta(){
	$.get( "http://localhost:8080/meuorcamento/api/conta/all", function( data ) {
	      console.log(data);
	      $(data).each(function(index){
	    	  
	    	  var d = this.dataPagamento
	    	  $('#inserir-lista')
	    	  		.append('<tr>')
	    	  		.append('<th id="lista-inp-id" scope="row">' + this.id + '</th>')
	    	  		.append('<td id="lista-inp-nome">' + this.nome + '</td')
	    	  		.append('<td id="lista-inp-valor">' + this.valor + '</td')
	    	  		.append('<td id="lista-inp-dtapagamento">' + d.dayOfMonth + '/' + d.monthValue + '/' + d.year + '</td')
	    	  		.append('<td id="lista-inp-estado">' + getEstado(this.estado) + '</td')
	    	  		.append('</tr>')

	      })
		});
}

function getEstado(estado){
	var temp = '';
	  if(estado){
		  temp = 'concluido'
	  }else{
		  temp = 'aguardando'
	  }
	  return temp
}