<!doctype html>
<html lang="en">

<head>
  @@include('meta.html')
  <title>Mashed Tomatoes</title>
  @@include('style.html')
</head>

<body>
  @@include('header.html')
  <div class="container my-3 vertical-center">
    <div class="container" id="contents-container">
      <div class="m-5">
        <h1>Login</h1>
      </div>
      <div class="row">
        <a href="index.html" class="login-body center">
          <img src="img/logo.png" class="login-logo">
        </a>
      </div>
      <div class="row">
        <div class="login-body form-group my-3 w-25">
          <label class="hi" for="usr">Email Address:</label>
          <input type="text" class="form-control" id="usr">
        </div>
      </div>
      <div class="row">
        <div class="login-body form-group my-3 w-25">
          <label for="pwd">Password:</label>
          <input type="password" class="form-control" id="pwd">
        </div>
      </div>
      <div class="row">
        <a href="index.html" class="login-body login-page-btn btn btn-warning my-4">LOGIN</a>
      </div>
      <div class="row">
        <a class="login-body login-forgot-recover my-4" href="recover.html">Forgot your password?</a>
      </div>
      <div class="row mb-5">
        <span class="login-body login-forgot-recover">Don't have a account?&nbsp
          <a class="my-4" href="register.html"> Sign up here</a>
        </span>
      </div>
    </div>
  </div>
  @@include('footer.html') @@include('script.html')
</body>

</html>