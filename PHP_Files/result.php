<?php
    include("db_connect.php");

    $sem = $_POST["sem"];
    $roll = $_POST["roll_no"];
    // $semester = "sem3";
    // $roll = "2220170253";
    //$roll = "2220170240";
    //$sem = "sem6";

    $non_elective_subject_fetch_query = "SELECT subject_name FROM subject WHERE sem='$sem' AND marks='1' AND sub_type='1' AND is_elective='0' ";
    $elective_subject_fetch_query = "SELECT subject.subject_name,studentdetail.elective1 FROM subject,studentdetail WHERE subject.sem='$sem' AND subject.marks='1' AND subject.sub_type='1' AND subject.is_elective='1' AND studentdetail.S_id='$roll' AND subject.sub_name = studentdetail.elective1";

    $result_query_tt1 = "SELECT termtest.marks,subject.subject_name FROM termtest,subject WHERE termtest.s_id='$roll' AND termtest.term='1' AND termtest.sub_name=subject.u_key ";

    $result_query_tt2 = "SELECT termtest.marks,subject.subject_name FROM termtest,subject WHERE termtest.s_id='$roll' AND termtest.term='2' AND termtest.sub_name=subject.u_key ";

    $non_elective_subject_details = $conn->query($non_elective_subject_fetch_query);
    $elective_subject_details = $conn->query($elective_subject_fetch_query);
    $subjects = array();

    $result_detail_tt1 = $conn->query($result_query_tt1);
    $result_detail_tt2 = $conn->query($result_query_tt2);
    $result_tt1 = array();
    $result_tt2 = array();
    if($non_elective_subject_details->num_rows > 0) {
        while ($row = $non_elective_subject_details->fetch_assoc()) {
            array_push($subjects, $row['subject_name']);
        }
    }
    if($elective_subject_details->num_rows > 0) {
        while($row = $elective_subject_details->fetch_assoc()) {
            array_push($subjects,$row['subject_name']);
        }
    }
    if($result_detail_tt1->num_rows > 0) {
        while($row = $result_detail_tt1->fetch_assoc()) {
            $temp = array();
            $temp["subject"] = $row['subject_name'];
            $temp["marks"] = $row['marks'];
            array_push($result_tt1,$temp);
        }
    }
    if($result_detail_tt2->num_rows > 0) {
        while($row = $result_detail_tt2->fetch_assoc()) {
            $temp = array();
            $temp["subject"] = $row['subject_name'];
            $temp["marks"] = $row['marks'];
            array_push($result_tt2,$temp);
        }
    }
    // print_r($subjects);
    // print_r($result_tt1);
    $response = array();
    $i = 0;
    $marking1 = array(sizeof($subjects));
    $marking2 = array(sizeof($subjects));
    $sz_of_2 = sizeof($result_tt2);
	// echo $result_tt1[0]["subject"];
    while($i<sizeof($subjects))
    {
        // echo "<br>i:".$i;
        $j = 0;
        while($j<sizeof($result_tt1))
        {
           // echo "<br>j:".$j;
           if($result_tt1[$j]["subject"]==$subjects[$i])
           {
            $marking1[$i] = $result_tt1[$j]["marks"];
           }
           else
           {
            $marking1[$i] = "-";
           }
           $j++; 
        }
        $j = 0;
		if($sz_of_2>0)
		{
			while($j<sizeof($result_tt2))
         {
           // echo "<br>j:".$j;
           if($result_tt2[$j]["subject"]==$subjects[$i])
           {
            $marking2[$i] = $result_tt2[$j]["marks"];
           }
           else
           {
            $marking2[$i] = "-";
           }
           $j++; 
         }
		}
		else
		{
			$marking2[$i] = "-";
		}
				
        
        $i++;
    }
    // print_r($marking1);
    // print_r($marking2);
    array_push($response, $subjects);
    array_push($response, $marking1);
    array_push($response, $marking2);
    echo json_encode($response);
    
    mysqli_close($conn);
?>