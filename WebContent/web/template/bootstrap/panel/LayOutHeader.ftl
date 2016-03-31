   <header class="header">
   
            <div class="logo">
                <!-- Add the class icon to your logo image or logo icon to add the margining -->
               <img name='logoImg'  src='${logoheader}' alt='EEPlat' class='img-responsive center-block' style='max-height:45px' border='0'/>
            </div>
            <nav class="navbar navbar-static-top" role="navigation">
                <!-- Sidebar toggle button-->
                <a href="#" class="navbar-btn sidebar-toggle" data-toggle="offcanvas" role="button">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </a>
                
               <div  style="display:inline;padding-left:5px;cursor:pointer">
                    <a  style="color:white;font-size:25px;" onclick="ep.reloaMain()"><i class="fa fa-home" style="padding-top:11px;"></i></a>
                </div>
                
                <div class="navbar-right">
                    <ul class="nav navbar-nav">
                    	<!--Project Begin-->
						<li class="dropdown notifications-menu">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown">
								<i class="fa fa-list"></i> <b>${title!}</b>
							</a>
							
						    ${appdropdown}
						</li>
		 				<!-- projects  end-->
		 				
		 				 <!-- user profile  begin-->
						<li class="dropdown user-menu user">
						
		                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                <i class="glyphicon glyphicon-user"></i>
                                <span>	<small>Welcome,</small>
									${(user.name)?if_exists}
                                <i class="caret"></i></span>
                            </a>
                            
                            
                              <ul class="dropdown-menu">
                                <!-- User image -->
                                <li class="user-header bg-light-blue">
                                    <img src="web/bootstrap/assets/img/empty.gif" class="img-circle" alt="User Image" />
                                    <p>
                                       ${(user.name)?if_exists}</small>
                                    </p>
                                </li>
                              
                                <!-- Menu Footer-->
                                <li class="user-footer">
								   <#if isdev >
                                    <div class="pull-left">
											<a class="btn btn-default btn-flat" target='_blank' href="console.pml?isApp=true">
												<i class="ion code"></i>
												Setup
											</a>
	                                </div>
	                               </#if>	
    
                                    <div class="pull-right">
                                      	<a class="btn btn-default btn-flat" href="web/default/logoff.jsp">
												<i class="ion log-out"></i>
												Sign out
											</a>
                                    </div>
                                </li>
                            </ul>
                            
                            
                            <!--

							 <ul class="dropdown-menu">
								 <li>
								   <ul class="menu">
		
										<li>
										
										</li>
									</ul>
								</li>	
							</ul>
							
							-->
							
						</li>
						 <!-- user profile  end-->
					</ul><!--/.navbar-nav-->
				</div><!--/.navbar-right-->
			</nav><!--/.navbar-static-top-->
    </header>
     <script>				  
      if ($(window).width() <= 992) {
        $(".logo").hide();
    }
 </script>   
    