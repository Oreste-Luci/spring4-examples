<%@include file="includes/header.jsp"%>

<div class="panel panel-primary">

    <div class="panel-heading">
        <h3 class="panel-title">Please sign in</h3>
    </div>

    <div class="panel-body">

        <c:if test="${param.error != null}">
            <div class="alert alert-danger">
                Invalid username and password.
            </div>
        </c:if>

        <c:if test="${param.logout != null}">
            <div class="alert alert-danger">
                You have been logged out.
            </div>
        </c:if>

        <form:form role="form" method="post">

            <div class="form-group">
                <label for="username">Email address</label>
                <input id="username" name="username" type="email" class="form-control" placeholder="Enter email" />
                <p class="help-block">Enter your email address.</p>
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" class="form-control" placeholder="Password" />
                <form:errors cssClass="error" path="password" />
            </div>

            <div class="form-group">
                <div class="checkbox">
                    <label>
                        <input name="_spring_security_remember_me" type="checkbox"> Remember me
                    </label>
                </div>
            </div>

            <button type="submit" class="btn btn-primary">Sign In</button>

        </form:form>
    </div>
</div>

<%@include file="includes/footer.jsp"%>