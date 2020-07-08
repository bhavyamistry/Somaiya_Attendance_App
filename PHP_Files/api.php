<?php
	include("db_connect.php");
    // $roll = $_POST["roll_no"]
    // $roll = $_POST["roll_no"];
    // $sem = $_POST["sem"];
    $roll = "2220170158";
    $sem = "sem6";
    $sql2 = "SELECT subject.subject_name,attendance.Subject,COUNT(attendance.Subject) AS total FROM attendance,subject WHERE attendance.Subject = subject.sub_name AND attendance.sem='$sem' AND subject.sub_type='1' AND subject.is_elective='0' GROUP BY attendance.Subject";
    $sql3 = "SELECT subject.subject_name,COUNT(attendance.studentid) AS TotalAT FROM attendance,subject WHERE find_in_set('$roll',attendance.studentid) <> 0 AND attendance.Subject = subject.sub_name AND attendance.sem='$sem' AND subject.sub_type='1' AND subject.is_elective='0' GROUP BY attendance.Subject";
    $sql4 = "SELECT subject.subject_name,pracsatt.Subject,studentdetail.P_batch,COUNT(pracsatt.Subject) AS total FROM pracsatt,subject,studentdetail WHERE pracsatt.Subject = subject.sub_name AND pracsatt.sem='$sem' AND subject.sub_type='0' AND studentdetail.S_id='$roll' AND studentdetail.P_batch = pracsatt.batch  GROUP BY pracsatt.Subject";
    $sql5 = "SELECT subject.subject_name,studentdetail.P_batch,COUNT(pracsatt.studentid) AS TotalAT FROM pracsatt,subject,studentdetail WHERE find_in_set('$roll',pracsatt.studentid) <> 0 AND studentdetail.S_id='$roll' AND studentdetail.P_batch = pracsatt.batch AND pracsatt.Subject = subject.sub_name AND pracsatt.sem='$sem' AND subject.sub_type='0' GROUP BY pracsatt.Subject";
    $elective_subject = "SELECT subject.subject_name,attendance.Subject,COUNT(attendance.Subject) AS total FROM attendance,subject,studentdetail WHERE attendance.Subject = subject.sub_name AND attendance.sem='$sem' AND subject.sub_type='1' AND subject.is_elective='1' AND studentdetail.S_id='$roll' AND subject.sub_name = studentdetail.elective1";
    $elective_subject_att ="SELECT subject.subject_name,COUNT(attendance.studentid) AS TotalAT FROM attendance,subject,studentdetail WHERE find_in_set('$roll',attendance.studentid) <> 0 AND attendance.Subject = subject.sub_name AND attendance.sem='$sem' AND subject.sub_type='1' AND subject.is_elective='1' AND studentdetail.S_id='$roll' AND subject.sub_name = studentdetail.elective1"; 
	$result2 = $conn->query($sql2);
	$result3 = $conn->query($sql3);
	$result4 = $conn->query($sql4);
	$result5 = $conn->query($sql5);
	$result_elective = $conn->query($elective_subject);
	$result_elective_att = $conn->query($elective_subject_att);
	$total_lecture = array();
	$response = array();
	$e_r = array();
	$e_r_att = array();
	$total_response = array();
	if ($result2->num_rows > 0) {
    // output data of each row
   	 while($row = $result2->fetch_assoc()) {
        $temp = array();
		$temp['total_Subjects'] = $row['subject_name'];
		$temp['total_lec'] = $row['total'];
        // echo "Subject:".$row['subject_name']."\t\tSubject:".$row['Subject']."\t\t\tTotal lectures:".$row['total']."<br>";
        array_push($total_lecture,$temp);
     }
	} 
	else {
    	echo "0 results";
	}

	$total_practical = array();
	if ($result4->num_rows > 0) {
    // output data of each row
   	 while($row = $result4->fetch_assoc()) {
        $temp = array();
		$temp['total_Subjects_pracs'] = $row['subject_name'];
		$temp['total_pracs'] = $row['total'];
        // echo "Subject:".$row['subject_name']."\t\tSubject:".$row['Subject']."\t\t\tTotal lectures:".$row['total']."<br>";
        array_push($total_practical,$temp);
     }
	}
	else {
    	echo "0 results";
	}

	// echo "<br><br>";
	$attend_lecture = array();
	if ($result3->num_rows > 0) {
    // output data of each row
   	 while($row = $result3->fetch_assoc()) {
        $temp = array();
		$temp['attend_Subjects'] = $row['subject_name'];
		$temp['attend_lec'] = $row['TotalAT'];
        // echo "Subject:".$row['subject_name']."\t\tTotal lectures Attended:".$row['TotalAT']."<br>";
        array_push($attend_lecture,$temp);
     }
	} 
	else {
    	echo "0 results";
	}
	$attend_pracs = array();
	if ($result5->num_rows > 0) {
    // output data of each row
   	 while($row = $result5->fetch_assoc()) {
        $temp = array();
		$temp['attend_Subjects'] = $row['subject_name'];
		$temp['attend_pracs'] = $row['TotalAT'];
        // echo "Subject:".$row['subject_name']."\t\tTotal lectures Attended:".$row['TotalAT']."<br>";
        array_push($attend_pracs,$temp);
     }
	} 
	else {
    	echo "0 results";
	}
	// echo sizeof($total_practical);
	if ($result_elective->num_rows > 0) {
    // output data of each row
   	 while($row = $result_elective->fetch_assoc()) {
        $temp = array();
        $temp2 = array();
        $temp["total_Subjects"] = $row['subject_name'];
        $temp['total_lec'] = $row['total'];
        $temp2['attend_Subjects'] = $row['subject_name'];
        if ($result_elective_att->num_rows > 0) {
	   	 while($row2 = $result_elective_att->fetch_assoc()){ 
			$temp2['attend_lec'] = $row2['TotalAT'];
	     }
		}
        array_push($total_lecture,$temp);
        array_push($attend_lecture, $temp2);
     }
	}
	else {
    	echo "0 results";
	}
	// print_r($total_lecture);
	// print_r($attend_lecture);
	// print_r($e_r);
	$i = 0;
	$flag = 1;
	while($i!=sizeof($total_lecture))
	{
		// echo "string";
		$temp2 = array();
		$attend = 0;
		$a = $total_lecture[$i]["total_Subjects"];
		$temp2["subjects"] = $total_lecture[$i]["total_Subjects"];
		$temp2["total_lec"] = $total_lecture[$i]["total_lec"];
		$j = 0;
		// echo "<br>a:".$a;
		while ($j!=sizeof($attend_lecture)) {
			$b = $attend_lecture[$j]["attend_Subjects"];
			// echo "<br>b:".$b;
			if($a==$b)
			{
				$temp2["attend_lec"] = $attend_lecture[$j]["attend_lec"];
				break;
			}
			else
			{
				$temp2["attend_lec"] = 0;
			}
			$j += 1;	
		}
		$i += 1;
		array_push($response,$temp2);
	}
	
	array_push($total_response, $response);

	$k = 0;
	$response2 = array();

	while($k!=sizeof($total_practical))
	{
		$temp3 = array();
		$attend = 0;
		$a = $total_practical[$k]["total_Subjects_pracs"];
		$temp3["subjects_pracs"] = $total_practical[$k]["total_Subjects_pracs"];
		$temp3["total_practical"] = $total_practical[$k]["total_pracs"];
		$j = 0;
		// echo "<br>a:".$a;
		while ($j!=sizeof($attend_pracs)) {
			$b = $attend_pracs[$j]["attend_Subjects"];
			// echo "<br>b:".$b;
			if($a==$b)
			{
				$temp3["attend_pracs"] = $attend_pracs[$j]["attend_pracs"];
				break;
			}
			else
			{
				$temp3["attend_pracs"] = 0;
			}
			$j += 1;	
		}
		$k += 1;
		array_push($response2,$temp3);
	}
	array_push($total_response, $response2);	
	echo json_encode($total_response);
	// echo json_encode($response2);
	// echo json_encode($total_lecture);
	// echo json_encode($attend_lecture);


	$conn->close();
	
?>