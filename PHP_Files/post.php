<?php
$db_name="college";
$mysql_username="root";
$mysql_password="";
$server_name="localhost";

$conn=mysqli_connect($server_name,$mysql_username,$mysql_password,$db_name);
if($conn)
{
						$user_name = $_POST["username"];
						$user_pass = $_POST["password"];


							$mysql_qry="select * from password_parent where S_id='$user_name';";


							$result = mysqli_query($conn ,$mysql_qry);
							$row=mysqli_fetch_assoc($result);
							$id= $row['S_id'];
							$pass=$row['P_password'];
							$password=mysqli_real_escape_string($conn,$_POST['password']);

							if (password_verify($password,$pass))

							{

									//Start of SQL QUERY TO FETCH Roll no , batch,sem 
							$sql="select M_name,L_name,roll, batch,current_sem from studentdetail where S_id='$user_name';";
							

							$sql1="select F_name from password_parent where S_id='$user_name';";
								$response=mysqli_query($conn,$sql);
								$result=array();
						

								$response1=mysqli_query($conn,$sql1);
								$result=array();


								$result['login']=array();	

								if (mysqli_num_rows($response)===1 and  mysqli_num_rows($response1)===1)
								{
									

									$row=mysqli_fetch_assoc($response);

									$index['L_name']=$row['L_name'];
									$index['M_name']=$row['M_name'];
									$index['roll']=$row['roll'];
									$index['batch']=$row['batch'];
									$index['sem']=$row['current_sem'];
								

									

									$row=mysqli_fetch_assoc($response1);
									$index['F_name']=$row['F_name'];

									array_push($result['login'],$index);

									$result['success']=1;


									echo json_encode($result);
									mysqli_close($conn);

								}

								else
								{
									$result['success']=0;
								

									echo json_encode($result);

									mysqli_close($conn);
								}
									
								echo "Login Successfull !";
							}


								





							else {

							/*//Start of SQL QUERY TO FETCH Roll no , batch,sem 
							$sql="select roll, batch,current_sem from studentdetail where S_id='2220170240';";
							

							$sql1="select F_name from password_parent where S_id='2220170240';";
								$response=mysqli_query($conn,$sql);
								$result=array();
						

								$response1=mysqli_query($conn,$sql1);
								$result=array();


								$result['login']=array();	

								if (mysqli_num_rows($response)===1 and  mysqli_num_rows($response1)===1)
								{
									

									$row=mysqli_fetch_assoc($response);

									$index['roll']=$row['roll'];
									$index['batch']=$row['batch'];
									$index['sem']=$row['current_sem'];
								

									

									$row=mysqli_fetch_assoc($response1);
									$index['F_name']=$row['F_name'];

									array_push($result['login'],$index);

									$result['success']="1";


									echo json_encode($result);
									mysqli_close($conn);

								}

								else
								{
									$result['success']="0";
								

									echo json_encode($result);

									mysqli_close($conn);
								}
									
								echo "Login Successfull !";*/
								echo "Unable to Login!";
							}

}





																				/*

																				2220170158	9819229891

																				2220170240 9869365885



																				*/

									else{
									echo "connection fails";
									}


?>
