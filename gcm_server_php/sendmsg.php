<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 include_once './db_functions.php';
 
 $db = new DB_Functions();
 
if (isset($_GET["phone"]) && isset($_GET["message"])) {
	$phone = $_GET["phone"];
    $message = array("message"=>$_GET["message"]);
	$result = $db -> sendGCMPhone($phone, $message);
    echo json_encode($result, JSON_UNESCAPED_UNICODE);
} else if(isset($_GET["email"]) && isset($_GET["message"])) {
	$phone = $_GET["email"];
    $message = array("message"=>$_GET["message"]);
    $result = $db -> sendGCMEmail($phone, $message);
    echo json_encode($result, JSON_UNESCAPED_UNICODE);
} 
?>
