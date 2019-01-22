<!DOCTYPE html>
<html>
    <head>
        <link href="Charity.css" rel="stylesheet" media="all" />
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <div id ="banner">
    	<h1><strong><span style="color: #fff;">Bangor </span></strong><span style="color: #939598;">Community</span></h1>
    	<div class = "bannerRight">
    		<?php
				$url = 'https://abnet.ddns.net/mucoftware/remote/get_charity_list.php';
				$jsondata = file_get_contents($url);
				$obj = json_decode($jsondata,true);
				echo "<h3>Welcome, ".$obj["results"][9]['CharityName']."!</h3>";
			?>
			<form action="index.php" align ="center">
				<input type="submit" name="logout" value="Logout">
			</form>
    	</div>
    </div>
    <div id="contain">
		<body>
			<main>
				<div id="bod">
					<div id ="charityLeft">
						<div id="image">
							<div id="pic">
								<?php
									$url = 'https://abnet.ddns.net/mucoftware/remote/get_charity_picture.php?charityid=10';
									$jsondata = file_get_contents($url);
									$obj = json_decode($jsondata,true);
									echo '<img class =img-circle src="' . $obj["type"] . ',' . $obj["data"] .'"/>';
								?>
							</div>
							<div id="allotedCoins">
								<h2>Coins Allocated: </h2>
								<h4>Since 1/2/2012</h4>
									<?php
										$url = 'https://abnet.ddns.net/mucoftware/remote/get_charity_list.php';
										$jsondata = file_get_contents($url);
										$obj = json_decode($jsondata,true);
										echo "<h1>".$obj["results"][9]['QuestBank']."</h1>";
									?>
							</div>
						</div>
						<div id ="map">
							<?php
								$url = 'https://abnet.ddns.net/mucoftware/remote/get_charity_list.php';
								$jsondata = file_get_contents($url);
								$obj = json_decode($jsondata,true);
								$lat = $obj["results"][9]['Latitude'];
								$long = $obj["results"][9]['Longitude'];
							?>
							<script>
								function myMap() {
								var la = Number(<?php echo json_encode($lat); ?>);
								var lo = Number(<?php echo json_encode($long); ?>);
									var myLatLng = {lat: la, lng: lo};
								  var mapCanvas = document.getElementById("map");
								  var mapOptions = {
									center: new google.maps.LatLng(la, lo), zoom: 12
								  };
								  var map = new google.maps.Map(mapCanvas, mapOptions);
								  var marker = new google.maps.Marker({
									  position: myLatLng,
									  map: map,
									  title: 'Hello World!'
								});
								}
							</script>
							<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCSJ3ssf9LK1A3ZKPR44QXzUtEeEV1b0u4&callback=myMap"></script>
						</div>
						<div id="coinTotalChar">
						
						</div>
					</div>
					<div id="charityQuests">
						<div id="charQTable">
						<?php
							// $json_string = file_get_contents("https://abnet.ddns.net/mucoftware/remote/get_all_quests.php");
							$json_string = file_get_contents("https://abnet.ddns.net/mucoftware/remote/get_quests.php?charityid=10");
							$array = json_decode($json_string, true);
							if($array["success"]==1){
							echo '<h1>Active Quests</h1>';
							echo '<table>';
							echo '<tr><th>Quantity</th><th>Name</th><th>Drop Off Location</th><th>Coin Payment</th></tr>';

							$n = 0;

							foreach ($array as $key => $jsons) {
								foreach ($jsons as $key => $value) {
									echo '<tr>';
									echo '<td>'.$array["results"][$n]['Quantity'].'</td>';
									echo '<td>'.$array["results"][$n]['QuestName'].'</td>';
									echo '<td>'.$array["results"][$n]['DropOffLocation'].'</td>';
									echo '<td>'.$array["results"][$n]['Payment'].'</td>';
									echo '</tr>';

								$n++;
							} 
							}
							echo '</table>';
						}	
							else
							{
								echo '<h1>Active Quests</h1>';
								echo '<table>';
								echo '<tr><th>Quantity</th><th>Name</th><th>Drop Off Location</th><th>Coin Payment</th></tr>';

								for($x =0;$x<5;$x++)
								{
									echo '<tr>';
									echo '<td>0</td>';
									echo '<td>N/A</td>';
									echo '<td>N/A</td>';
									echo '<td>N/A</td>';
									echo '</tr>';
								}
								echo '</table>';
							}
						?>
						</div>
						<div id="charQTable2">
						<?php
							$json_string = file_get_contents("https://abnet.ddns.net/mucoftware/remote/get_all_quests.php");
							$array = json_decode($json_string, true);
							if($array["success"]==1){
								echo '<h1>All Quests</h1>';
								echo '<table>';
								echo '<tr><th>Quantity</th><th>Name</th><th>Drop Off Location</th><th>Coin Payment</th></tr>';

								$n = 0;

								foreach ($array as $key => $jsons) {
									foreach ($jsons as $key => $value) {
									if($array['results'][$n]['CharityID']!=10)
									{
										echo '<tr>';
										echo '<td>'.$array["results"][$n]['Quantity'].'</td>';
										echo '<td>'.$array["results"][$n]['QuestName'].'</td>';
										echo '<td>'.$array["results"][$n]['DropOffLocation'].'</td>';
										echo '<td>'.$array["results"][$n]['Payment'].'</td>';
										echo '</tr>';
									}
									$n++;
								} 
								}
								echo '</table>';
							}
							else
							{
								echo '<h1>Active Quests</h1>';
								echo '<table>';
								echo '<tr><th>Quantity</th><th>Name</th><th>Drop Off Location</th><th>Coin Payment</th></tr>';

								for($x =0;$x<5;$x++)
								{
									echo '<tr>';
									echo '<td>0</td>';
									echo '<td>N/A</td>';
									echo '<td>N/A</td>';
									echo '<td>N/A</td>';
									echo '</tr>';
								}
								echo '</table>';
							}
						?>
						</div>
					</div>
					<div id="charityDonations">
						<div id="left">
							<h2>Total Donations: </h2>
							<h4>Since 1/2/2012</h4>
							<br>
							<br>
							<br>
								<?php
									$url = 'https://abnet.ddns.net/mucoftware/remote/get_charity_rank.php';
									$jsondata = file_get_contents($url);
									$array = json_decode($jsondata,true);
									// echo "<h1>".$obj["results"][0]['Coins']."</h1>";
									$n = 0;

									foreach ($array as $key => $jsons) {
										foreach ($jsons as $key => $value) {
											$tmp = $array['results'][$n]['CharityID'];
											$k = $n;
											if($tmp==10)
											{
												echo "<h1>".$array["results"][$k]['SUMQuantity']."</h1>";
											}
										$n++;
									} 
									}
								?>
						</div>
						<div id="mid">

						</div>
						<div id="right">
							<h2>Rank: </h2>
							<h4>Since 1/2/2012</h4>
							<br>
							<br>
							<br>
								<?php
									$urlRank = "https://abnet.ddns.net/mucoftware/remote/get_charity_rank.php";
									$jsonRank = file_get_contents($urlRank);
									$array = json_decode($jsonRank,true);
									$charID =10;
									$n = 0;

									foreach ($array as $key => $jsons) {
										foreach ($jsons as $key => $value) {
											if($array['results'][$n]['CharityID']==10)
											{
												echo "<h1>".$array["results"][$n]['Rank']."</h1>";
											}
										$n++;
									} 
									}
									echo "<h1> /".$n."</h1>";
								?>
						</div>
					</div>
				</div>
				<div id ="selector">
					<div id="selectorSpace">
						<div id="createQ" align="center">
						<h1>Create A Quest</h1>
							<form action="createQuest.php">
								<input type ="submit" name="Create">
							</form>
						</div>
						<div id="acceptQ">
						
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