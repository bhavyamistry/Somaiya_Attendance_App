

<?php
error_reporting(E_ERROR | E_PARSE);
$db_name="college";
$mysql_username="root";
$mysql_password="";
$server_name="localhost";


$conn=mysqli_connect($server_name,$mysql_username,$mysql_password,$db_name);


if (mysqli_connect_errno()){

	die ("Unable to connect" . mysqli_connect_error());


}
else{


	$stmt=$conn->prepare("Select faculty_name,title, date ,images from notification;");


	$stmt->execute();

	$stmt->bind_result($faculty_name,$title,$date,$images);

	$notification=array();


	while ($stmt->fetch()) {

		$temp=array();
		

		$temp['faculty_name']=$faculty_name;
		$temp['title']=$title;
		$temp['date']=$date;
		$temp['images']=$images;



		array_push($notification,$temp);

	}	


	echo json_encode($notification);




}