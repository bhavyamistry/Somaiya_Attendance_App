<?php
	define('DB_HOST', 'localhost');
	define('DB_USER','root');
	define('DB_PASS','');
	define('DB_NAME', 'college');
	
	$conn = new mysqli(DB_HOST,DB_USER,DB_PASS,DB_NAME);

	if(mysqli_connect_errno())
	{
		die('Unable to conect to database'.mysqli_connect_error());
	}
?>