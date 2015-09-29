<?php

// response json
$json = array();

/**
 * Registering a user device
 * Store reg id in users table
 */
if (isset($_POST["pass"]) && isset($_POST["name"])) {
    $pass = $_POST["pass"];
    $name = $_POST["name"];
    // Store user details in db
    include_once './db_functions.php';

    $db = new DB_Functions();
    $result = $db -> turnon($pass, $name);
    echo json_encode($result, JSON_UNESCAPED_UNICODE);
} else {
	$result = array("status"=>false, "alert"=>"Trạng thái Online hệ thống");
	echo json_encode($result, JSON_UNESCAPED_UNICODE);
}
?>