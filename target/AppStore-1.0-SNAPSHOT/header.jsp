<%-- 
    Document   : header
    Created on : Jun 10, 2020, 10:43:59 AM
    Author     : HP
--%>

<!-- Navigation -->
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
    <div class="container">
      <a class="navbar-brand" href="">Zenith</a>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarResponsive">
        <ul class="navbar-nav ml-auto">
          <li class="nav-item active">
            <a class="nav-link" href="">Home
              <span class="sr-only">(current)</span>
            </a>
          </li>
          <c:if test='${!sessionScope["loggedIn"]}'>
            <li class="nav-item">
              <a class="nav-link" href="#">Login</a>
            </li>
          </c:if>
          <c:if test='${sessionScope["loggedIn"]}'>
            <li class="nav-item">
              <a class="nav-link" href="#">My downloads</a>
            </li>
          </c:if>
          <li class="nav-item">
            <a class="nav-link" href="#">Contact</a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
