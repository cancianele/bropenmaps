	#set($c = 1)
	<div>
	#foreach( $est in $ests)
	  <div>
			<div>
				${c}. $est.estabelecimentoNome
			</div>
			<div>
				#if($est.estabelecimentoDescricao && $est.estabelecimentoDescricao!='')
					<div>
						$est.estabelecimentoDescricao
					</div><br class="clear"/>
				#end
				<div>
					$est.primeiroEndereco.enderecoDescricao
				</div><br class="clear"/>
				<div>
					$est.primeiroEnderecoCidade - $est.primeiroEnderecoEstado
				</div><br class="clear"/>
				#if($est.primeiroTelefone && $est.primeiroTelefone!='')
					<div>
						<a href="wtai://wp/mc;${est.primeiroTelefoneMobile}">$est.primeiroTelefone</a>
					</div><br class="clear"/>
				#end
			</div>
	  </div>
	  <br class="clear"/>
	#set($c = $c + 1)
	#end
</div>
<br class="clear"/>