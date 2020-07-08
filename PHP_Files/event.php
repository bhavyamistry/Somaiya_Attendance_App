

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


	$stmt=$conn->prepare("Select title,type,date,description,images from news;");


	$stmt->execute();

	$stmt->bind_result($title,$type,$date,$description,$images);

	$event=array();
	$event2=array();


	while ($stmt->fetch()) {

		$temp=array();
		$temp1=array();



		

		$temp['title']=$title;
		$temp['type']=$type;
		$temp['date']=$date;
		$temp['description']=$description;
		$temp['images']=$images;


     /* $temp1=explode(',',$temp['images']);

print_r($temp1);*/

// 		$temp2=array();
// $i=1;
// for ($x = 0; $x <= count($temp1); $x++) {
// 		$temp2['id']=$i;
// 		$temp2['image']=$temp1[$x];
//         print_r($temp2);
// 	    // echo "---";
// 		// array_push($event2,$temp2);
// 		// break;
//        $i=$i+1;

// }



		array_push($event,$temp);
		/*array_push($event2,$temp1);*/
	   	

	}	

	echo json_encode($event);
	// echo json_encode($event2);




}