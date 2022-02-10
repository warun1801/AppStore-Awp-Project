<%-- 
    Document   : index
    Created on : Jun 10, 2020, 10:47:02 AM
    Author     : HP
--%>

<%@page import="java.util.List"%>
<%@page import="com.AppStore.domain.Application"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head> 
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">
        <!-- Bootstrap core CSS -->
        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <!-- Custom styles for this template -->
        <link href="css/shop-homepage.css" rel="stylesheet">
        
        <title>Zenith</title>
    </head>
    <body>
        <jsp:include page="/header.jsp"/>
        <div class="container">
            <div class="row">
              <div class="col-lg-3">
                <h1 class="my-4">Categories:</h1>
                <div class="list-group">
                  <a href="#" class="list-group-item">Apps</a>
                  <a href="#" class="list-group-item">Games</a>
                  <a href="#" class="list-group-item">Beta Program</a>
                </div>
              </div>
              <!-- /.col-lg-3 -->
              <%List<Application> item = (List<Application>)request.getAttribute("appList");%>
              <div class="col-lg-9">
                <div id="carouselExampleIndicators" class="carousel slide my-4" data-ride="carousel">
                  <ol class="carousel-indicators">
                    <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
                    <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
                    <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
                  </ol>
                  <div class="carousel-inner" role="listbox">
                    <div class="carousel-item active">
                      <img class="d-block img-fluid" src="http://placehold.it/900x350" alt="First slide">
                    </div>
                    <div class="carousel-item">
                      <img class="d-block img-fluid" src="http://placehold.it/900x350" alt="Second slide">
                    </div>
                    <div class="carousel-item">
                      <img class="d-block img-fluid" src="http://placehold.it/900x350" alt="Third slide">
                    </div>
                  </div>
                  <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                  </a>
                  <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                  </a>
                </div>
                  
                <div class="row">
                    <c:set var="item" value='${requestScope["appList"]}'/>
                    <c:forEach var="app" items="${item}">
                    <div class="col-lg-4 col-md-6 mb-4">
                      <div class="card h-100">
                        <a href="#"><img class="card-img-top" src="${app.getLogo()}" alt="${app.getName()}"></a>
                        <div class="card-body">
                          <h4 class="card-title">
                              <a href="#"><c:out value="${app.getName()}"/></a>
                          </h4>
                          <h5>Version = <c:out value="${app.getVersion()}"/></h5>
                          <p class="card-text"><c:out value="${app.getDescription()}"/></p>
                        </div>
                        <div class="card-footer">
                            <small class="text-muted">Rating:<c:out value="${app.getRating()}"/></small>
                        </div>
                      </div>
                    </div>
                    </c:forEach>
                </div>
              </div>
            </div>
        </div>
        
        <jsp:include page="/footer.jsp"/>
    </body>
</html>
