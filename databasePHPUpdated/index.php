<!DOCTYPE html>
<?php
    require_once 'server_fns.php';
    require_once("DBManager.php");
   // gen_data();
    $logonSuccess = false;
    $logonCharitySuccess = false;
    // verify user's credentials
    if (server_post() ){    
        $logonSuccess = (DBManager::getInstance()->verify_user_credentials(user_post(), password_post()));
        if ($logonSuccess == true && isset($_POST['login'])) {
       		echo "login success";
            session_start();
            set_user(get_post("username"));
            set_session_val("userid", DBManager::getInstance()->get_id_by_username(get_session_val("username")));
            header('Location: UserProfile.php');
            exit;
        }
        
        $logonCharitySuccess = (DBManager::getInstance()->verify_charity_credentials(get_post("charity_login"), get_post("charity_password")));
        if ($logonCharitySuccess == true) {
            session_start();
            set_user(get_post("charity_login"));
            set_session_val('charityid', DBManager::getInstance()->get_id_by_charity(get_post("charity_login")));
            header('Location: CharityProfile.php');
            exit;
        }
        
    }
?>
<html lang="en">
<head>
<title>Bangor Marathon Savings</title>
<meta charset="utf-8">
<link rel="stylesheet" href="index.css">
</head>  

<div id="wrapper">
	<body>
		<main>
		<div id ="mainPic">
	
		</div>
			<div id="loginMain">
				<div id="loginLeft">
					<h1><bold><span style="color:#064F94;">Bangor </span></bold><span style="color: #939598;">Community</span></h1>
					<!-- <img src="ricky.gif" alt="This will display an animated GIF" /> -->
					<div id="userLeader">
						<?php
							// $json_string = file_get_contents("https://abnet.ddns.net/mucoftware/remote/get_all_quests.php");
							$json_string = file_get_contents("https://abnet.ddns.net/mucoftware/remote/get_user_rank.php");
							$array = json_decode($json_string, true);

							echo '<table>';
							echo '<tr><th>User</th><th>Donations</th></tr>';

							$n = 0;

							foreach ($array as $key => $jsons) {
								foreach ($jsons as $key => $value) {
									echo '<tr>';
									echo '<td>'.$array["results"][$n]['UserName'].'</td>';
									echo '<td>'.$array["results"][$n]['SumQuantity'].'</td>';
									echo '</tr>';

								$n++;
							} 
							}
							echo '</table>';
						?>
					</div>
					<div id="charityLeader">
						<?php
							// $json_string = file_get_contents("https://abnet.ddns.net/mucoftware/remote/get_all_quests.php");
							$json_string = file_get_contents("https://abnet.ddns.net/mucoftware/remote/get_charity_rank.php");
							$array = json_decode($json_string, true);
							echo '<center>';
							echo '<table>';
							echo '<tr><th>Charity</th><th>Donations</th></tr>';

							$n = 0;

							foreach ($array as $key => $jsons) {
								foreach ($jsons as $key => $value) {
									echo '<tr>';
									echo '<td>'.$array["results"][$n]['CharityName'].'</td>';
									echo '<td>'.$array["results"][$n]['SUMQuantity'].'</td>';
									echo '</tr>';

								$n++;
							} 
							}
							echo '</table>';
							echo '</center>';
						?>
					</div>

				</div>
				<div id="loginRightTop">
					<div id="form">
						<form action="index.php" method="post">
							<h2>User Login</h2>
							<input type="text" name="username" placeholder="Username...."><br>
							<input type="password" name="userpassword" placeholder="Password...."><br>
							<input type="submit" name="login" value="Login"><br>

							<?php
								if ($_SERVER["REQUEST_METHOD"] == "POST") { 
									if (!$logonSuccess) {
										echo "Invalid name and/or password";
									}
								}
							?>            
						</form>
					</div>
					<div id = "registerUser">
						Register User <a href="CreateUser.php">Now</a>
					</div>
				</div>
				<div id="loginRightBottom">
					<div id="form">
						<form action="index.php" method="post">
							<h2>Charity Login</h2>
							<input type="text" name="user" placeholder="Chairty...."><br>
							<input type="password" name="userpassword" placeholder="Password...."><br>
							<input type="submit" name="loginChar" value="Login"><br>

							<?php
								if ($_SERVER["REQUEST_METHOD"] == "POST") { 
									if (!$logonSuccess) {
										echo "Invalid name and/or password";
									}
								}
							?>            
						</form>
					</div>
					<div id = "registerUser">
						Register Charity <a href="CreateCharity.php">Now</a>
					</div>
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
			<li>
				<a href="http://keywordsuggest.org/gallery/865277.html">Image Credits</a>
			</li>
		</ul>
		<br>
		<p><a href="http://www.bangor.com/Utility/Copyright.aspx">Copyright © 2017 Bangor Savings Bank</a> <span>Member FDIC</span> <span>Equal Housing Lender</span></p>
	</div>
</footer>
