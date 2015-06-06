<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:page title="Register">
	<h3>New to myanalitics? Register new account and start using application</h3>
	<form action="" method="post">
	<table class="table1 registerTable">
		<tr>
			<th>Your login</th>
			<td><input class="inputLenght1" type="text" name="login" placeholder="login here"/></td>
		</tr>
		<tr>
			<th>Your password</th>
			<td><input class="inputLenght1" type="password" name="password" placeholder="password here"/></td>
		</tr>
		<tr>
			<th>Your password again</th>
			<td><input class="inputLenght1" type="password" name="pwdSecond" placeholder="confirm password"/></td>
		</tr>
		<tr>
			<th></th>
			<td><input type="submit" value="proceed"/></td>
		</tr>
	</table>
	</form>
</tags:page>