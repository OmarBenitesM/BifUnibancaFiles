<%-- 
    Document   : procmanual
    Created on : 27/01/2021, 01:22:35 PM
    Author     : PSSPERU-TI
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
Date date = new Date();
DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Proceso Eventual</title>
        <style>
           .blue{color: blue;font-weight: bold}
           .red{color: red;font-weight: bold}
           .green{color: green;font-weight: bold}
        </style>
    </head>    
    <body>
      <form name="f1">
        <div class="row">
            <div class="col-md-12">
                <div class="card">
                    <table class="table table-striped">
                        <thead class="thead thead-dark">
                            <tr>
                                <th colspan="6"><h1>Tabla de archivos Unibanca</h1></th>
                                <th colspan="5"><h2>Fecha Proc.&nbsp;&nbsp;&nbsp;<%= dateFormat.format(date) %></h2></th>
                            </tr>
                            <tr>
                                <th><button class="btn btn-outline-purple" onclick="ejecutarCopia(event)">Procesar</button></th>
                                <th><button class="btn btn-outline-purple" id="close" onclick="cerrar()">Cerrar</button></th>
                                <th colspan="9"></th>
                            </tr>
                            <tr>
                                <th style="text-align: center"><input type="checkbox" id="all" onclick="seleccionar()"> Todos Seleccionar</th>
                             <th>Descripción</th>
                             <th>File Unibanca</th>
                             <th>File en IBS</th>
                             <th>Longitud</th>
                             <th>Fecha proceso</th>
                             <th>Hora proceso</th>
                             <th>Usuario</th>
                             <th>Proceso</th>
                             <th colspan="2">Estado</th>                             
                            </tr>
                        </thead>
                        <tbody>
                          <c:forEach var="inv" items="${listainv}">
                            <c:set var="cnt" value="${cnt + 1}" />
                            <tr>
                             <td style="text-align: center; vertical-align: middle;">
                               <input type="checkbox" name="ch${cnt}" value="${inv.nombreorigen}-${inv.nombreato}_${inv.longreg}" />
                            </td>
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
    </form>
    </body>
</html>
