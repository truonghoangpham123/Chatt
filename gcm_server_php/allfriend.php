<?php

// response json
$json = array();

/**
 * Registering a user device
 * Store reg id in users table
 */

    // Store user details in db
    include_once './db_functions.php';

    $db = new DB_Functions();
    $result = $db -> getAllFriends();
    echo json_encode($result, JSON_UNESCAPED_UNICODE);

?>