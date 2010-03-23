<div id="cont${endereco.enderecoId}" class="left top5px pbottom5px" style="width: 264px; border-bottom: 1px dashed #B3B2B2;">
 	<div class="left farial  fontazul bold font12px txtleft cursor">$endereco.enderecoDescricao #if($endereco.enderecoBairro)- $endereco.enderecoBairro #end</div><br class="clear" />
 	<div class="left farial fontcinza font10px txtleft">$endereco.enderecoCidade, $endereco.enderecoEstado</div>#if($endereco.enderecoCEP)<div class="left left10px farial fontcinza font10px txtleft">CEP.: $endereco.enderecoCEP</div>#end				 	
 	#if($endereco.telefones && $endereco.telefones!="")<br class="clear" /><div class="left farial fontcinza font10px txtleft">$endereco.telefones</div>#end
 	#if($endereco.enderecoEmail)<br class="clear" /><div class="left farial fontcinza font10px txtleft">$endereco.enderecoEmail</div>#end
 	#if($endereco.enderecoWebSite)<br class="clear" /><div class="left farial fontcinza font10px txtleft">$endereco.enderecoWebSite</div>#end
 </div>					 
<br class="clear" />