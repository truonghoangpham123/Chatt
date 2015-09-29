<?php

// response json
$json = array();

/**
 * Registering a user device
 * Store reg id in users table
 */
if (isset($_POST["name"]) && isset($_POST["pass"]) && isset($_POST["email"]) && isset($_POST["phone"]) && isset($_POST["regid"])) {
    $name = $_POST["name"];
    $pass = $_POST["pass"];
    $email = $_POST["email"];
    $phone = $_POST["phone"];
    $gcm_regid = $_POST["regid"]; // GCM Registration ID
    // Store user details in db
    include_once './db_functions.php';
    include_once './GCM.php';

    $db = new DB_Functions();
    $result = $db -> register($name, $pass, $email, $phone, $gcm_regid);
    echo json_encode($result, JSON_UNESCAPED_UNICODE);
} else {
	$result = array("status"=>false, "alert"=>"Lỗi đăng ký hệ thống");
    echo json_encode($result, JSON_UNESCAPED_UNICODE);
}
?>