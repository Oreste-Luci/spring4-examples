<%@include file="includes/header.jsp"%>

<div class="panel panel-default">

    <div class="panel-heading">
        <h3 class="panel-title">Please signup</h3>
    </div>

    <div class="panel-body">

        <form:form modelAttribute="signupForm" role="form">

            <div class="form-group">
                <form:label path="email">Email address</form:label>
                <form:input path="email" type="email" class="form-control" placeholder="Enter email" />
                <p class="help-block">Enter a unique email address. It will also be your login id.</p>
            </div>

            <div class="form-group">
                <form:label path="name">Name</form:label>
                <form:input path="name" class="form-control" placeholder="Enter name" />
                <p class="help-block">Enter your display name.</p>
            </div>

            <div class="form-group">
                <form:label path="password">Password</form:label>
                <form:password path="password" class="form-control" placeholder="Password" />
            </div>

            <button type="submit" class="btn btn-default">Submit</button>

        </form:form>
    </div>
</div>

<%@include file="includes/footer.jsp"%>
