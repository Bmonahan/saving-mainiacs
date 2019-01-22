<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <link href="CreateUser.css" type="text/css" rel="stylesheet" media="all" />
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
   <div id ="banner">
    	<h1><strong><span style="color: #fff;">Bangor </span></strong><span style="color: #939598;">Community</span></h1>
    </div>
    <div id="contain">
    <body>
		<main>
			<div id="bod">
			<div id="create">
			<center>
			<div id="form">
				<form id="cQ"action="https://abnet.ddns.net/mucoftware/remote/insert_charity.php?charityname=<?php echo $_GET['charityname'];?>&address=<?php echo $_GET['address'];?>&phone=<?php echo $_GET['phone'];?>&description=<?php echo $_GET['description'];?>&charitylogin=<?php echo $_GET['charitylogin'];?>&password=password&email=<?php echo $_GET['email'];?>"method="GET">
<!-- 
				<form action="https://abnet.ddns.net/mucoftware/remote/insert_charity.php?user=<?php echo $_GET['user'];?>&password=<?php echo $_GET['userpassword'];?>&fullname=<?php echo $_GET['fullname'];?>&email=<?php echo $_GET['email'];?>" method="GET">
 -->
					<h2>CREATE A CHARITY</h2>
					<input type="text" name="charityname" placeholder="Charity Name...."><br>
					<input type="text" name="address" placeholder="Address...."><br>
					<input type="text" name="description" placeholder="Description...."><br>
					<input type="tel" name="phone" placeholder="Phone Number...."><br>
					<input type="text" name="charitylogin" placeholder="Username...."><br>
					<input type="password" name="password" placeholder="Password...."><br>
					<input type="email" name="email" placeholder="Email...."><br>
					<input type="submit" name="login" value="Create"><br>

				</form>
				<div id="message" style="display: block;position: relative; color: red;"></div>
				<div id="response"></div>
				<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
				<script>
					$(function() {
						$("#cQ").on("submit", function(e) {
							e.preventDefault();
							$.ajax({
								url: $(this).attr("action"),
								type: 'POST',
								data: $(this).serialize(),
								beforeSend: function() {
									//$("#message").html("Added!");
									alert('Charity Created!')
								},
								success: function(data) {
									$("#message").hide();
									$("#response").html(data);
								}
							});
						});
					});
				</script>
				</div>
			</center>
			</div>
		  </div>
		</main>
    </body>
    </div>
    <footer>
	<p style="height: 26px; padding-bottom: 0px; margin-bottom: 10px;">
        <strong>Bangor Support: 1.877.226.4671</strong> <span class="eh"><a href="mailto:bangorsupport@bangor.com" onclick="PopEmail(this.href); return false;">bangorsupport@bangor.com</a></span>
        <a href="https://www.facebook.com/bangorsavingsbank" onclick="Departure(this.href, 'general'); return false;" style="height: 25px; position: relative; top: 4px;"><img src="fb-icons-foot.png" width="25px" height="25px"></a>
        <a href="https://twitter.com/bangorsavings" onclick="Departure(this.href, 'general'); return false;" style="height: 25px; position: relative; top: 4px; left: 12px;"><img src="tw-icons-foot.png" width="31px" height="25px"></a>
        <a href="https://www.youtube.com/channel/UC6hwOwRZiNJGfBnmLgMxSAA" onclick="Departure(this.href, 'general'); return false;" style="height: 25px; position: relative; top: 4px; left: 12px;"><img src="yt-logo-foot.png" height="25px" width="60px"></a>
        <a href="https://www.linkedin.com/company/bangor-savings-bank" onclick="Departure(this.href, 'general'); return false;" style="height: 25px; position: relative; top: 4px; left: 12px;"><img src="li-icons-foot.png" height="25px" width="28px"></a>        
      </p>
      <br>
	<div id="footer">
		<ul>
			<li>
				<a href="http://www.bangor.com/About-Us/Career-Opportunities.aspx" target="_self">Careers</a>
			</li>
			<li>
				<a href="http://www.bangor.com/About-Us/Contact-Us.aspx" target="_self">Contact Us</a>
			</li>
			<li>
				<a href="help.html" >Help</a>
			</li>
		</ul>
		<br>
		<p><a href="http://www.bangor.com/Utility/Copyright.aspx">Copyright © 2017 Bangor Savings Bank</a> <span>Member FDIC</span> <span>Equal Housing Lender</span></p>
	</div>
</footer>
</html>
