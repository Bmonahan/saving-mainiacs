<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <link href="UserProfile.css" rel="stylesheet" media="all" />
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <div id ="banner">
    	<h1><strong><span style="color: #fff;">Bangor </span></strong><span style="color: #939598;">Community</span></h1>
    	<div class = "bannerRight" style="background-color: none;">
    		<?php
    			$user = "helpfulguy78";
				$pass = "helpfulguy78";
				$url = 'https://abnet.ddns.net/mucoftware/remote/get_user.php?user='.$user.'&password='.$pass.'';
				$jsondata = file_get_contents($url);
				$obj = json_decode($jsondata,true);
				echo "<h3>Welcome, </h3>";
				echo "<h3>".$obj["results"][0]['UserName']."!</h3>";
			?>
    		<!-- <h3>Hello </h3> -->
    	</div>
    	<div id="logout" align="center">
    		<form action="index.php">
				<input type="submit" name="logout" value="Logout">
			</form>
    	</div>
    </div>
    <div id="contain">
		<body>
			<main>
				<div id="bod">
					<div id="donationLeftUser">
						<div id ="chart">
							<?php
								$user = "helpfulguy78";
								$pass = "helpfulguy78";
								$url = 'https://abnet.ddns.net/mucoftware/remote/get_user_donationrate.php?user='.$user.'&password='.$pass.'';
								$jsondata = file_get_contents($url);
								$obj = json_decode($jsondata,true);
								$CharityOne	= $obj['results'][0]['CharityName'];
								$PercentOne = $obj['results'][0]['Percent'];
								$CharityTwo	= $obj['results'][1]['CharityName'];
								$PercentTwo = $obj['results'][1]['Percent'];
								$CharityThr	= $obj['results'][2]['CharityName'];
								$PercentThr = $obj['results'][2]['Percent'];
								$CharityFour = $obj['results'][3]['CharityName'];
								$PercentFour = $obj['results'][3]['Percent'];
								$CharityFive = $obj['results'][4]['CharityName'];
								$PercentFive = $obj['results'][4]['Percent'];
							?>
							<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
							<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
							    <script>
							      	google.charts.load('current', {'packages':['corechart']});
							      	google.charts.setOnLoadCallback(drawChart);
							      	var one = <?php echo json_encode($CharityOne); ?>;
							      	var two = <?php echo json_encode($CharityTwo); ?>;
							      	var thr = <?php echo json_encode($CharityThr); ?>;
							      	var four = <?php echo json_encode($CharityFour); ?>;
							      	var five = <?php echo json_encode($CharityFive); ?>;
							      	//var perc = Number(<?php echo json_encode($Percent); ?>);
							      	var oneH = Number(<?php echo json_encode($PercentOne); ?>);
							      	var twoH = Number(<?php echo json_encode($PercentTwo); ?>);
							      	var threeH = Number(<?php echo json_encode($PercentThr); ?>);
							      	var fourH = Number(<?php echo json_encode($PercentFour); ?>);
							      	var fiveH = Number(<?php echo json_encode($PercentFive); ?>);
							    	function drawChart() {

							        var data = google.visualization.arrayToDataTable([
							          ['Task', 'Percent'],
							          [ one,  oneH],
							          [two,     twoH],
							          [thr,  threeH],
							          [four, fourH],
							          [five,    fiveH]
							        ]);

							        var options = {
							        'legend':'none',
							        'backgroundColor':'transparent',
							        chartArea:{right: 120,width:'100%',height:'100%'},
							          colors: ['#064F94', '#054582', '#053B6F', '#04315D', '#03284A']
							        };

							        var chart = new google.visualization.PieChart(document.getElementById('piechart'));

							        chart.draw(data, options);
							      }
							  </script>
							  <div id="piechart" style="width: 500px; height: 75%;"></div>
						</div>
						<div id="usedChar">
							<?php
								$user = "helpfulguy78";
								$pass = "helpfulguy78";
								$json_string = file_get_contents('https://abnet.ddns.net/mucoftware/remote/get_user_donationrate.php?user='.$user.'&password='.$pass.'');
								$array = json_decode($json_string, true);
								echo "<h3>Donation Rates:</h3>";
								$n = 0;
								foreach ($array as $key => $jsons) {
									foreach ($jsons as $key => $value) {
										echo '<h5>'.$array["results"][$n]['CharityName'].': '.$array["results"][$n]['Percent'].'%</h5>';
									$n++;
								} 
								}
							?>
						</div>
						<div id ="charityList">
						<h1>Active Quests:</h1>
							<?php
								$user = "helpfulguy78";
								$pass = "helpfulguy78";
								$json_string = file_get_contents("https://abnet.ddns.net/mucoftware/remote/get_user_active_quests.php?user=".$user."&password=".$pass."");
								$array = json_decode($json_string, true);

								echo '<table>';
								echo '<tr><th>Reward Amount</th><th>Accepted (Date)</th></tr>';

								$n = 0;

								foreach ($array as $key => $jsons) {
									foreach ($jsons as $key => $value) {
										echo '<tr>';
										echo '<td>'.$array["results"][$n]['RewardAmount'].'</td>';
										echo '<td>'.$array["results"][$n]['AcceptDate'].'</td>';
										echo '<td>'.$array["results"][$n]['ActiveID'].'</td>';
										echo '</tr>';

									$n++;
								} 
								}
								echo '</table>';
							?>
						</div>
					</div>
					<center>
					<div id="profileUser">
						<div id="pic">
							<?php
								$url = 'https://abnet.ddns.net/mucoftware/remote/get_user_picture.php?userid=1';
								$jsondata = file_get_contents($url);
								$obj = json_decode($jsondata,true);
								echo '<img class =img-circle src="' . $obj["type"] . ',' . $obj["data"] .'"/>';
// 							?>						
						</div>
						<div id="coinsCurrent">
							<div class="userRank">
							<h2>Rank: </h2>
							<h4>Since 1/2/2012</h4>
							<br>
							<?php
								$user = "helpfulguy78";
								$url = 'https://abnet.ddns.net/mucoftware/remote/get_user_rank.php';
								$jsondata = file_get_contents($url);
								$array = json_decode($jsondata, true);
								$n = 0;

								foreach ($array as $key => $jsons) {
									foreach ($jsons as $key => $value) {
									if($array["results"][$n]["UserName"]==$user)
										echo '<h1>'.$array["results"][$n]['Rank'].'</h1>';
									$n++;
								} 
								}
								
							?>
							</div>
							<div class ="coins">
								<h2>Coins Today: </h2>
								<h4>Since 1/2/2012</h4>
								<br>
									<?php
										$user = "helpfulguy78";
										$pass = "helpfulguy78";
										$url = 'https://abnet.ddns.net/mucoftware/remote/get_user.php?user='.$user.'&password='.$pass.'';
										$jsondata = file_get_contents($url);
										$obj = json_decode($jsondata,true);
										echo "<h1>".$obj["results"][0]['Coins']."</h1>";
									?>
							</div>
							<div class ="steps">
								<h2>Steps Today: </h2>
								<h4>Since 1/2/2012</h4>
								<br>

									<?php
										$user = "helpfulguy78";
										$pass = "helpfulguy78";
										$url = 'https://abnet.ddns.net/mucoftware/remote/get_user.php?user='.$user.'&password='.$pass.'';
										$jsondata = file_get_contents($url);
										$obj = json_decode($jsondata,true);
										echo "<h1>".$obj["results"][0]['DaySteps']."</h1>";
									?>
						</div>
						</div>
					</div>
					</center>
					<div id="coinTotalUser">
						<div id ="totalCoinsEarned">
						<h2>Total Coins Earned: </h2>
						<h4>Since 1/2/2012</h4>
						<br>
							<?php
								$user = "helpfulguy78";
								$pass = "helpfulguy78";
								$url = 'https://abnet.ddns.net/mucoftware/remote/get_user.php?user='.$user.'&password='.$pass.'';
								$jsondata = file_get_contents($url);
								$obj = json_decode($jsondata,true);
								echo "<h1>".$obj["results"][0]['TotalCoins']."</h1>";
							?>
						</div>
						<div id ="totalSteps">
						<h2>Total Steps Taken: </h2>
						<h4>Since 1/2/2012</h4>
						<br>
						<br>
						<br>
							<?php
								$url = 'https://abnet.ddns.net/mucoftware/remote/get_user.php?user=helpfulguy78&password=helpfulguy78';
								$jsondata = file_get_contents($url);
								$obj = json_decode($jsondata,true);
								echo "<h1>".$obj["results"][0]['TotalSteps']."</h1>";
							?>
						</div>
						<div id="totalDonations">
						<h2>Total Donations: </h2>
						<h4>Since 1/2/2012</h4>
						<br>
							<?php
								$user = "helpfulguy78";
								$pass = "helpfulguy78";
								$url = 'https://abnet.ddns.net/mucoftware/remote/get_user.php?user='.$user.'&password='.$pass.'';
								$jsondata = file_get_contents($url);
								$obj = json_decode($jsondata,true);
								echo "<h1>$".($obj["results"][0]['TotalCoins']*.189)."</h1>";
							?>
							
						</div>
					</div>
				 </div>
				 <div id = "selector">
					<div id= "selectorSpace">
					<div id="selectionArea">
						<div id="percent" align="center">
						<!---DONATOIN PERCENTAGES---------------------------------->
						<h1>Donation Percentages</h1>
							<form action="https://abnet.ddns.net/mucoftware/remote/update_user_donations.php?user=<?php echo $_GET['user'];?>&password=<?php echo $_GET['password'];?>&c1=<?php echo $_GET['c1'];?>&c2=<?php echo $_GET['c2'];?>&c3=3&c4=4&c5=5&p1=<?php echo $_GET['p1'];?>&p2=<?php echo $_GET['p2'];?>&p3=10&p4=15&p5=15" method="GET">
							<input type="hidden"value="helpfulguy78"name="user"> 
							<input type="hidden"value="helpfulguy78"name="password"> 
								<?php
									$json_string = file_get_contents("https://abnet.ddns.net/mucoftware/remote/get_charity_list.php");
									$array = json_decode($json_string, true);
									echo "<select name ='c1'>";
									$n = 0;
									foreach ($array as $key => $jsons) {
										foreach ($jsons as $key => $value) {
											//echo "<option>".$array["results"][$n]['CharityName'].'</option>';
											echo '<option value="'.$array["results"][$n]['CharityID'].'">'.$array["results"][$n]['CharityName'].'</option>';
											//echo "<option>".$var."</option>";
										$n++;
									} 
									}
									echo "</select>";									
									echo "<select name ='c2'>";
									$n = 0;
									foreach ($array as $key => $jsons) {
										foreach ($jsons as $key => $value) {
											echo '<option value="'.$array["results"][$n]['CharityID'].'">'.$array["results"][$n]['CharityName'].'</option>';
											//echo "<option>".$var."</option>";
										$n++;
									} 
									}
									echo "</select>";
									
									echo "<select name ='c3'>";
									$n = 0;
									foreach ($array as $key => $jsons) {
										foreach ($jsons as $key => $value) {
											echo '<option value="'.$array["results"][$n]['CharityID'].'">'.$array["results"][$n]['CharityName'].'</option>';
											//echo "<option>".$var."</option>";
										$n++;
									} 
									}
									echo "</select>";									
									echo "<select name ='c4'>";
									$n = 0;
									foreach ($array as $key => $jsons) {
										foreach ($jsons as $key => $value) {
											echo '<option value="'.$array["results"][$n]['CharityID'].'">'.$array["results"][$n]['CharityName'].'</option>';
											//echo "<option>".$var."</option>";
										$n++;
									} 
									}
									echo "</select>";
									
									echo "<select name ='c5'>";
									$n = 0;
									foreach ($array as $key => $jsons) {
										foreach ($jsons as $key => $value) {
											echo '<option value="'.$array["results"][$n]['CharityID'].'">'.$array["results"][$n]['CharityName'].'</option>';
											//echo "<option>".$var."</option>";
										$n++;
									} 
									}
									echo "</select>";
									echo "<label for='one'>1:</label>";
									echo "<input type='number' name ='p1'value ='0' id='one'placeholder='Percent'>";
									echo "<label for='two'>2:</label>";
									echo "<input type='number' name ='p2'value ='0' id='two' placeholder='Percent'>";
									echo "<label for='three'>3:</label>";
									echo "<input type='number' name ='p3'value ='0' id='three' placeholder='Percent'>";
									echo "<label for='four'>4:</label>";
									echo "<input type='number' name ='p4'value ='0' id='four'placeholder='Percent'>";
									echo "<label for='five'>5:</label>";
									echo "<input type='number' name ='p5'value ='0' id='five'placeholder='Percent'>";
								?>
								<input type="submit" name="Change">
							</form>
						</div>
					</div>
					<div id="allQuests" align="center">
						<div id="addQ">
							<h1>Add Quests</h1>
							<form action='https://abnet.ddns.net/mucoftware/remote/accept_quest.php?user=<?php echo $_GET['user'];?>&password=<?php echo $_GET['password'];?>&questid=<?php echo $_GET['allQ'];?>' method="GET">
								<input type ="text" name="user"value="helpfulguy78" readonly>
								<input type ="password" name="password"value="helpfulguy78" readonly>
								<?php
									$json_string = file_get_contents("https://abnet.ddns.net/mucoftware/remote/get_all_quests.php");
									$array = json_decode($json_string, true);
							
									echo '<select name="questid">';
									$n = 0;

									foreach ($array as $key => $jsons) {
										foreach ($jsons as $key => $value) {
											//echo '<option>'.$array["results"][$n]['QuestName'].", Payment: ".$array["results"][$n]['Payment'].'</option>';
											 echo '<option value="'.$array["results"][$n]['QuestID'].'">'.$array["results"][$n]['QuestName'].", Payment: ".$array["results"][$n]['Payment'].'</option>';
										$n++;
									} 
									}
									echo '</select>';
								?>
								<input type="submit" name="AddQuest" value="Add" style="width: 50%;">
							</form>
						</div>
						<!-- 
<div id="updatePic">
							<h1> Update Pic</h1>
							<form action="https://abnet.ddns.net/mucoftware/remote/update_user_picture.php?user=<?php echo $_GET['user'];?>&password=<?php echo $_GET['password'];?>&picture=<?php echo $_GET['picture'];?>" method="GET">
								<input type="hidden"value="helpfulguy78"name="user">
								<input type="hidden"value="helpfulguy78"name="password">
								<input type="hidden" value="" name="picture">
								<input type="file"name="picture">
								<input type="submit"name="Change"value="Change">
								<?php
									// $url=$_GET['picture'];
// 									$img = file_get_contents($url);
// 									$imgE = base64_encode($img);
// 									echo $imgE;
								?>

							</form>
						</div>
 -->
					</div>
					<div id="comDel">
						<div id="compQuests" align="center">
						<h1>Completed Quest</h1>
						<form action='https://abnet.ddns.net/mucoftware/remote/complete_quest.php?user=<?php echo $_GET['user'];?>&password=<?php echo $_GET['password'];?>&activequestid=<?php echo $_GET['activequestid'];?>' method="GET">
							<input type ="text" name="user"value="helpfulguy78" readonly>
							<input type ="password" name="password"value="helpfulguy78" readonly>
							<?php
								$user = "helpfulguy78";
								$pass = "helpfulguy78";
								$json_string = file_get_contents("https://abnet.ddns.net/mucoftware/remote/get_user_active_quests.php?user=".$user."&password=".$pass."");
								$array = json_decode($json_string, true);
							
								echo '<select name="activequestid">';
								$n = 0;

								foreach ($array as $key => $jsons) {
									foreach ($jsons as $key => $value) {
										//echo '<option>'.$array["results"][$n]['QuestID'].", Payment: ".$array["results"][$n]['Payment'].'</option>';
										 echo '<option value="'.$array["results"][$n]['ActiveID'].'">'.$array["results"][$n]['ActiveID'].", Payment: ".$array["results"][$n]['RewardAmount'].'</option>';
									$n++;
								} 
								}
								echo '</select>';
							?>
							<input type="submit" name="compuQuest" value="Complete" style="width: 50%;">
						</form>
						</div>
						
						<div id="delQ" align="center">
							<h1>Remove Quest</h1>
							<form action='https://abnet.ddns.net/mucoftware/remote/leave_quest.php?user=<?php echo $_GET['user'];?>&password=<?php echo $_GET['password'];?>&activequestid=<?php echo $_GET['activequestid'];?>' method="GET">
							<input type ="text" name="user"value="helpfulguy78" readonly>
							<input type ="password" name="password"value="helpfulguy78" readonly>
							<?php
								$user = "helpfulguy78";
								$pass = "helpfulguy78";
								$json_string = file_get_contents("https://abnet.ddns.net/mucoftware/remote/get_user_active_quests.php?user=".$user."&password=".$pass."");
								$array = json_decode($json_string, true);
							
								echo '<select name="activequestid">';
								$n = 0;

								foreach ($array as $key => $jsons) {
									foreach ($jsons as $key => $value) {
										//echo '<option>'.$array["results"][$n]['QuestID'].", Payment: ".$array["results"][$n]['Payment'].'</option>';
										 echo '<option value="'.$array["results"][$n]['ActiveID'].'">'.$array["results"][$n]['ActiveID'].", Payment: ".$array["results"][$n]['RewardAmount'].'</option>';
									$n++;
								} 
								}
								echo '</select>';
							?>
							<input type="submit" name="compuQuest" value="Remove" style="width: 50%;">
						</form>
						</div>
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
		</ul>
		<br>
		<p><a href="http://www.bangor.com/Utility/Copyright.aspx">Copyright © 2017 Bangor Savings Bank</a> <span>Member FDIC</span> <span>Equal Housing Lender</span></p>
	</div>
</footer>

</html>