<%-- 
    Document   : listainventario
    Created on : 12/01/2021, 11:28:13 PM
    Author     : PSSPERU-TI - Omar Benites
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <!-- Tell the browser to be responsive to screen width -->
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <!-- Favicon icon -->
        <link rel="icon" type="image/png" sizes="16x16" href="resources/assets/images/favicon.png" />
        <title>Copia automática de archivos Unibanca</title>
        <!-- Custom CSS -->
        <link rel="stylesheet" type="text/css" href="resources/assets/libs/select2/dist/css/select2.min.css" />
        <link rel="stylesheet" type="text/css" href="resources/assets/libs/bootstrap/dist/css/bootstrap.min.css" />
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
    <!-- ============================================================== -->
    <!-- Preloader - style you can find in spinners.css -->
    <!-- ============================================================== -->
    <div class="preloader">
       <div class="lds-ripple">
         <div class="lds-pos"></div>
         <div class="lds-pos"></div>
       </div>
    </div>
    <!-- ============================================================== -->
    <!-- Main wrapper - style you can find in pages.scss -->
    <!-- ============================================================== -->
    <div id="main-wrapper">
        <!-- ============================================================== -->
        <!-- Topbar header - style you can find in pages.scss -->
        <!-- ============================================================== -->
        <header class="topbar" data-navbarbg="skin5">
            <nav class="navbar top-navbar navbar-expand-md navbar-dark">
                <div class="navbar-header" data-logobg="skin5">
                    <!-- This is for the sidebar toggle which is visible on mobile only -->
                    <a class="nav-toggler waves-effect waves-light d-block d-md-none" href="javascript:void(0)"><i class="ti-menu ti-close"></i></a>
                    <!-- ============================================================== -->
                    <!-- Logo -->
                    <!-- ============================================================== -->
                    <a class="navbar-brand" href="index.html">
                        <!-- Logo icon -->
                        <b class="logo-icon p-l-10">
                            <!--You can put here icon as well // <i class="wi wi-sunset"></i> //-->
                            <!-- Dark Logo icon -->
                            <img src="resources/assets/images/banbif.png" alt="homepage" class="light-logo" />
                           
                        </b>
                        <!--End Logo icon -->

                        <!-- Logo icon -->
                        <!-- <b class="logo-icon"> -->
                            <!--You can put here icon as well // <i class="wi wi-sunset"></i> //-->
                            <!-- Dark Logo icon -->
                            <!-- <img src="assets/images/logo-text.png" alt="homepage" class="light-logo" /> -->
                            
                        <!-- </b> -->
                        <!--End Logo icon -->
                    </a>
                    <!-- ============================================================== -->
                    <!-- End Logo -->
                    <!-- ============================================================== -->
                    <!-- ============================================================== -->
                    <!-- Toggle which is visible on mobile only -->
                    <!-- ============================================================== -->
                    <a class="topbartoggler d-block d-md-none waves-effect waves-light" href="javascript:void(0)" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"><i class="ti-more"></i></a>
                </div>
                <!-- ============================================================== -->
                <!-- End Logo -->
                <!-- ============================================================== -->
                <div class="navbar-collapse collapse" id="navbarSupportedContent" data-navbarbg="skin5">
                    <!-- ============================================================== -->
                    <!-- toggle and nav items -->
                    <!-- ============================================================== -->
                    <ul class="navbar-nav float-left mr-auto">
                        <li class="nav-item d-none d-md-block"><a class="nav-link sidebartoggler waves-effect waves-light" href="javascript:void(0)" data-sidebartype="mini-sidebar"><i class="mdi mdi-menu font-24"></i></a></li>
                        <!-- ============================================================== -->
                        <!-- create new -->
                        <!-- ============================================================== -->
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                             <span class="d-none d-md-block">Transferencias  <i class="fa fa-angle-down"></i></span>
                             <span class="d-block d-md-none"><i class="fa fa-plus"></i></span>   
                            </a>
                            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <a class="dropdown-item" href="#" id="automatico" onclick="iniciaProceso(event,'A')">Automático</a>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item" href="#" id="eventual" onclick="iniciaProceso(event,'E')">Eventual</a>
                            <!--    <div id="content" class="hide alert alert-warning" onclick="iniciarCopia('E')"></div> -->
                            </div>
                        </li>
                        <!-- ============================================================== -->
                        <!-- Search -->
                        <!-- ============================================================== -->
                        <li class="nav-item search-box"> <a class="nav-link waves-effect waves-dark" href="javascript:void(0)"><i class="ti-search"></i></a>
                            <form class="app-search position-absolute">
                                <input type="text" class="form-control" placeholder="Search &amp; enter" /> <a class="srh-btn"><i class="ti-close"></i></a>
                            </form>
                        </li>
                    </ul>
                    <!-- ============================================================== -->
                    <!-- Right side toggle and nav items -->
                    <!-- ============================================================== -->
                    <ul class="navbar-nav float-right">
                        <!-- ============================================================== -->
                        <!-- Comment -->
                        <!-- ============================================================== -->
                        <!-- User profile and search -->
                        <!-- ============================================================== -->
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle text-muted waves-effect waves-dark pro-pic" href="" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><img src="resources/assets/images/users/1.jpg" alt="user" class="rounded-circle" width="31" /></a>
                            <div class="dropdown-menu dropdown-menu-right user-dd animated">
                                <a class="dropdown-item" href="javascript:void(0)"><i class="fa fa-power-off m-r-5 m-l-5"></i> Logout</a>
                                <div class="dropdown-divider"></div>
                            </div>
                        </li>
                        <!-- ============================================================== -->
                        <!-- User profile and search -->
                        <!-- ============================================================== -->
                    </ul>
                </div>
            </nav>
        </header>
        <!-- ============================================================== -->
        <!-- End Topbar header -->
        <!-- ============================================================== -->
        <!-- ============================================================== -->
        <!-- Left Sidebar - style you can find in sidebar.scss  -->
        <!-- ============================================================== -->
        <aside class="left-sidebar" data-sidebarbg="skin5">
            <!-- Sidebar scroll-->
            <div class="scroll-sidebar">
                <!-- Sidebar navigation-->
                <nav class="sidebar-nav">
                    <ul id="sidebarnav" class="p-t-30">
                        <li class="sidebar-item"> <a class="sidebar-link has-arrow waves-effect waves-dark" href="javascript:void(0)" aria-expanded="false"><i class="mdi mdi-receipt"></i><span class="hide-menu">Consultas</span></a>
                            <ul aria-expanded="false" class="collapse  first-level">
                                <li class="sidebar-item"><a href=v_factura class="sidebar-link"><i class="mdi mdi-note-outline"></i><span class="hide-menu">Logs Generados </span></a></li>
                            </ul>
                        </li>
                        <li class="sidebar-item"> <a class="sidebar-link has-arrow waves-effect waves-dark" href="javascript:void(0)" aria-expanded="false"><i class="mdi mdi-receipt"></i><span class="hide-menu">Mantenedores </span></a>
                            <ul aria-expanded="false" class="collapse  first-level">
                                <li class="sidebar-item"><a href=v_factura class="sidebar-link"><i class="mdi mdi-note-outline"></i><span class="hide-menu">Configuraciones (.dtt; .tto) </span></a></li>
                            </ul>
                        </li>                        
                        
                        <li class="sidebar-item"> <a class="sidebar-link has-arrow waves-effect waves-dark" href="javascript:void(0)" aria-expanded="false"><i class="mdi mdi-account-key"></i><span class="hide-menu">Authentication </span></a>
                            <ul aria-expanded="false" class="collapse  first-level">
                                <li class="sidebar-item"><a href="authentication-login.html" class="sidebar-link"><i class="mdi mdi-all-inclusive"></i><span class="hide-menu"> Login </span></a></li>
                                <li class="sidebar-item"><a href="authentication-register.html" class="sidebar-link"><i class="mdi mdi-all-inclusive"></i><span class="hide-menu"> Register </span></a></li>
                            </ul>
                        </li>
                    </ul>
                </nav>
                <!-- End Sidebar navigation -->
            </div>
            <!-- End Sidebar scroll-->
        </aside>
        <!-- ============================================================== -->
        <!-- End Left Sidebar - style you can find in sidebar.scss  -->
        <!-- ============================================================== -->
        <!-- ============================================================== -->
        <!-- Page wrapper  -->
        <!-- ============================================================== -->
        <div class="page-wrapper">
            <!-- ============================================================== -->
            <!-- Bread crumb and right sidebar toggle -->
            <!-- ============================================================== -->

            <!-- ============================================================== -->
            <!-- End Bread crumb and right sidebar toggle -->
            <!-- ============================================================== -->
            <!-- ============================================================== -->
            <!-- Container fluid  -->
            <!-- ============================================================== -->            
            <div class="container-fluid" id='inventario'>

            </div>
            <!-- ============================================================== -->
            <!-- End Container fluid  -->
            <!-- ============================================================== -->
            <!-- ============================================================== -->
            <!-- footer -->
            <!-- ============================================================== -->
 
            <!-- ============================================================== -->
            <!-- End footer -->
            <!-- ============================================================== -->
        </div>
        <!-- ============================================================== -->
        <!-- End Page wrapper  -->
        <!-- ============================================================== -->
    </div>
    <button id="btnModalMsj" hidden="hidden" data-toggle="modal" data-target="#modalMsjPnl"></button>
    <div class="modal fade" id="modalMsjPnl">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button class="close" area-hidden="true" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title"><label>Mensaje</label></h4>
                </div>
                <div class="modal-body">
                    <h5><span id="msj"></span></h5>
                </div>
                <div class="modal-footer">
                    
                </div>
            </div>
        </div>
    </div>
    <!-- ============================================================== -->
    <!-- End Wrapper -->
    <!-- ============================================================== -->
    <!-- ============================================================== -->
    <!-- All Jquery -->
    <!-- ============================================================== -->
    <script src="resources/assets/libs/jquery/dist/jquery.min.js"></script>    
    <script>
        function iniciaProceso(e, tipoproc){
            e.preventDefault();
            var parametros="tipoproc="+tipoproc;
            $.ajax({
                url: 'unlFilesBase',
                data: parametros,
                type: 'POST',
                datatype: 'json',
                success: function(json){
                    var rpta = json.rpta;
                    var fecctrl = json.fecyhor;
                    if(rpta){
                        iniciarCopia(tipoproc,fecctrl);
                    }else{
                        if(!rpta){
                           var det = "Hubo error al descargar archivos base de trabajo\n 'Verificar c:\\unibanca\\dia\\'" +
                                     "\n ó verificar archivos CNTRLCNT y OPCTRLF tengan la misma fecha de proceso.";
                           $('#msj').html(det);
                           $('#btnModalMsj').click();
                        }
                    }                        
                },
                error: function(xhr,status){
                    alert('Disculpe, existió un problema, verifique si el log tiene información');
                },
                complete: function(xhr,status){

                }
            });
        }
      
        function iniciarCopia(tipoproc, fecctrl){
            var xmlhttp;
            var nodoMostrarJsp = document.getElementById('inventario');
            xmlhttp = new XMLHttpRequest();
            xmlhttp.onreadystatechange = function(){
                nodoMostrarJsp.innerHTML = "<div class='lds-ripple'><div class='lds-pos'></div><div class='lds-pos'></div></div></div>";
                if(xmlhttp.readyState===4 && xmlhttp.status===200){
                   nodoMostrarJsp.innerHTML = xmlhttp.responseText;
                }
            };
            if(tipoproc === 'A'){
               xmlhttp.open("GET","listainventario.htm?fecctrl="+fecctrl,true);
            }
            if(tipoproc === 'E'){
               xmlhttp.open("GET","procmanual.htm",true);
            }
            xmlhttp.send();
           }
           
        function cerrar(){
           var nodoMostrarJsp = document.getElementById('inventario');
           nodoMostrarJsp.innerHTML = "";
        }
        function consultadetlog(e, nomlog, nomato){
            e.preventDefault();
            var parametros="nomlog="+nomlog+"&"+"nomato="+nomato;
            $.ajax({
                url: 'leerdetalle',
                data: parametros,
                type: 'POST',
                datatype: 'json',
                success: function(json){
                    var rpta = json.rpta;
                    var nomfile = json.nomato;
                    var det = json.detalles;
                    det = det.replace(/[|]/g,'<br>');
                    if(rpta){
                        $('#msj').html(det);
                        $('#btnModalMsj').click();
                    }                        
                },
                error: function(xhr,status){
                    alert('Disculpe, existió un problema, verifique si el log tiene información');
                },
                complete: function(xhr,status){

                }
            });
        }
        function seleccionar(){
            var isChecked = document. getElementById('all'). checked;
            if(isChecked){
                seleccionar_todo();
             }else{
                 deseleccionar_todo();
             }
        }
        
        function ejecutarCopia(e){
           e.preventDefault();
           var data = [];
           for (i=0;i<document.f1.elements.length;i++){
             if(document.f1.elements[i].type === "checkbox")
                 if(document.f1.elements[i].checked){
                   data.push(document.f1.elements[i].value);
                 }
           }               
           if(data.length>0){               
               let conf = confirm("¿Desea procesar la copia de archivos seleccionados?");
               if(conf){
                  var parametros = data.toString();
                  parametros = parametros.replace(/,/g,"&");
                  alert(parametros);
                  $.ajax({                       
                      url: 'procmanual',
                      data: parametros,
                      type: 'POST',
                      datatype: 'json',
                      success: function(json){
                          var rpta = json.rpta;
                          if(rpta){
                              $('#close').click();
                              $('#eventual').click();
                          }
                      },
                      error: function(xhr, status){
                          alert("Disculpe, error al obtener información, revisar log del server WEB");
                      },
                      complete: function(xhr,status){
                          
                      }
                  });
               }
           }else{
               alert("No ha seleccionado Archivo Unibanca");
           }
        }
        
        function seleccionar_todo(){
           for (i=0;i<document.f1.elements.length;i++)
            if(document.f1.elements[i].type === "checkbox")
            document.f1.elements[i].checked=1;
           }
           
        function deseleccionar_todo(){
           for (i=0;i<document.f1.elements.length;i++)
             if(document.f1.elements[i].type === "checkbox")
             document.f1.elements[i].checked=0;
        }
    </script>
    <!-- slimscrollbar scrollbar JavaScript -->
    <script src="resources/assets/libs/perfect-scrollbar/dist/perfect-scrollbar.jquery.min.js"></script>
    <script src="resources/assets/extra-libs/sparkline/sparkline.js"></script>
    <!-- Bootstrap tether Core JavaScript -->
    <script src="resources/assets/libs/popper.js/dist/umd/popper.min.js"></script>
    <script src="resources/assets/libs/bootstrap/dist/js/bootstrap.min.js"></script>
    <!--Wave Effects -->
    <script src="resources/dist/js/waves.js"></script>
    <!--Menu sidebar -->
    <script src="resources/dist/js/sidebarmenu.js"></script>
    <!--Custom JavaScript -->
    <script src="resources/dist/js/custom.min.js"></script>
    <!-- This Page JS -->
    <script src="resources/assets/libs/inputmask/dist/min/jquery.inputmask.bundle.min.js"></script>
    <script src="resources/dist/js/pages/mask/mask.init.js"></script>
    <script src="resources/assets/libs/select2/dist/js/select2.full.min.js"></script>
    <script src="resources/assets/libs/select2/dist/js/select2.min.js"></script>
    <script src="resources/assets/libs/jquery-asColor/dist/jquery-asColor.min.js"></script>
    <script src="resources/assets/libs/jquery-asGradient/dist/jquery-asGradient.js"></script>
    <script src="resources/assets/libs/jquery-asColorPicker/dist/jquery-asColorPicker.min.js"></script>
    <script src="resources/assets/libs/jquery-minicolors/jquery.minicolors.min.js"></script>
    <script src="resources/assets/libs/bootstrap-datepicker/dist/js/bootstrap-datepicker.min.js"></script>
    </body>
</html>