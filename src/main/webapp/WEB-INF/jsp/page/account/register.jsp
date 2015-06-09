<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:page title="Register">
    <h3>New to Web Analytics? Register new account and start using application!</h3>

    <form action="" method="post" class="basic-grey">
        <h1>Contact Form
            <span>Please fill all the texts in the fields.</span>
        </h1>
        <label>
            <span>Login:</span>
            <input id="login" type="text" name="login" placeholder="Your Login" value="${registerForm['login']}"/>
            <span>${registerErrors['login']}</span>
        </label>
        <label>
            <span>Email:</span>
            <input id="email" type="email" name="email" placeholder="Valid Email Address" value="${registerForm['email']}"/>
        </label>
        <label>
            <span>Password:</span>
            <input id="password" type="password" name="password" placeholder="Password"/>
            <span>${registerErrors['password']}</span>
        </label>
        <label>
            <span>Confirm Password:</span>
            <input id="repeat_password" type="password" name="rePassword" placeholder="Confirm password"/>
        </label>
        <label>
            <span>&nbsp;</span>
            <input type="submit" class="button" value="Proceed"/>
        </label>
    </form>
</tags:page>