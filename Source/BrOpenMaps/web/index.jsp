<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>BrOpenMaps</title>
	<style type="text/css">
		<!--
			@import url("css/layout.css");
		-->
		body{color:#006699; font-family: Arial, sans-serif; background-color:#FFFFFF;}
		td{font-family: Arial, sans-serif}
	</style>
</head>
<body>
<form action="mobile/busca" method="post">
	<table>
		<tr>
			<td colspan="2" align="center"><img src="imagens/BrOpenMaps.png" width="83" height="35"/></td>
		</tr>
		<tr>
			<td>
				O que:
			</td>
			<td>
				<input type="text" name="q" value=""/>
			</td>
		</tr>
		<tr>
			<td>
				Cidade:
			</td>
			<td>
				<input type="text" name="cidade" value=""/>
			</td>
		</tr>
		<tr>
			<td>
				Estado:
			</td>
			<td>			
				<select name="estado">
					<option value="">Estado</option>
					<option value="ac">Acre</option>
					<option value="al">Alagoas</option>
					<option value="am">Amazonas</option>
					<option value="ap">Amapá</option>
					<option value="ba">Bahia</option>
					<option value="ce">Ceará</option>
					<option value="df">Distrito Federal</option>
					<option value="es">Espírito Santo</option>
					<option value="go">Goias</option>
					<option value="ma">Maranhão</option>
					<option value="mg">Minas Gerais</option>
					<option value="ms">Mato Grosso do Sul</option>
					<option value="mt">Mato Grosso</option>
					<option value="pa">Pará</option>
					<option value="pb">Paraíba</option>
					<option value="pe">Pernambuco</option>
					<option value="pi">Piauí</option>
					<option value="pr">Paraná</option>
					<option value="rj">Rio de Janeiro</option>
					<option value="rn">Rio Grande do Norte</option>
					<option value="ro">Rondônia</option>
					<option value="rr">Roraima</option>
					<option value="rs">Rio Grande do Sul</option>
					<option value="sc">Santa Catarina</option>
					<option value="se">Sergipe</option>
					<option value="sp">São Paulo</option>
					<option value="to">Tocantins</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>
				<input type="submit" value="Localize"/>
			</td>
		</tr>
	</table>
</form>
</body>
</html>