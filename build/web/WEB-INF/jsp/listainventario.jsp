<%-- 
    Document   : listainventario
    Created on : 12/01/2021, 11:28:13 PM
    Author     : PSSPERU-TI - Omar Benites
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%
Date date = new Date();
DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <style>
           .blue{color: blue;font-weight: bold}
           .red{color: red;font-weight: bold}
           .green{color: green;font-weight: bold}
        </style>
        <!-- Tell the browser to be responsive to screen width -->
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <!-- Favicon icon -->
        <link rel="icon" type="image/png" sizes="16x16" href="resources/assets/images/favicon.png" />
        <title>Copia automática de archivos Unibanca</title>
        <!-- Custom CSS -->
        <link rel="stylesheet" type="text/css" href="resources/assets/libs/select2/dist/css/select2.min.css" />
        <link rel="stylesheet" type="text/css" href="resources/assets/libs/jquery-minicolors/jquery.minicolors.css" />
        <link rel="stylesheet" type="text/css" href="resources/assets/libs/bootstrap-datepicker/dist/css/bootstrap-datepicker.min.css" />
        <link rel="stylesheet" type="text/css" href="resources/assets/libs/quill/dist/quill.snow.css" />
        <link href="resources/dist/css/style.min.css" rel="stylesheet"/>
        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->        
    </head>
    <body>    
        <div class="row">
            <div class="col-md-12">
                <div class="card">
                    <table class="table table-striped">
                        <thead class="thead thead-dark">
                            <tr>
                                <th colspan="6"><h1>Tabla de archivos Unibanca</h1></th>
                                <th colspan="4"><h2>Fecha Proc.&nbsp;&nbsp;&nbsp;<%= dateFormat.format(date) %></h2></th></tr>
                            <tr>
                             <th>Descripción</th>
                             <th>File Unibanca</th>
                             <th>File en IBS</th>
                             <th>Longitud</th>
                             <th>Fecha proceso</th>
                             <th>Hora proceso</th>
                             <th>Usuario</th>
                             <th>Proceso</th>
                             <th colspan="1">Estado</th>
                             <th><button class="btn btn-outline-purple" onclick="cerrar()">Cerrar</button></th>
                            </tr>
                        </thead>
                        <tbody>
                          <c:forEach var="inv" items="${listainv}">
                            <tr>
                            <td>${inv.descripcion}</td>
                            <td>${inv.nombreorigen}</td>
                            <td>${inv.nombreato}</td>
                            <td>${inv.longreg}</td>
                            <td>${inv.fecharegistro}</td>
                            <td>${inv.horaregistro}</td>
                            <td>${inv.usuarireg}</td>
                            <td>${inv.programreg}</td>
                                <c:choose>
                                   <c:when test="${inv.estadoreg == 'P'}">
                                    <td class="blue">Procesado</td>
                                   </c:when>
                                   <c:when test="${inv.estadoreg == 'A'}">
                                    <td class="green">Pendiente</td>
                                   </c:when>
                                   <c:otherwise>
                                    <td class="red">Error</td>
                                   </c:otherwise>
                                </c:choose>
                            <td>
                                <form id="frmconsultalog">
                                    <input type="hidden" name="log" id="log" value="${inv.logproceso}"/>
                                    <input type="hidden" name="dtt" id="dtt" value="${inv.nombreato}"/>
                                    <button class="btn btn-success" onclick="consultadetlog(event, log.value, dtt.value)">Ver log</button>
                                </form>
                            </td>
                            </tr>
                          </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>