<?php
header('Content-Type: text/html; charset=utf-8');
class DB_Functions {

    private $db;

    //put your code here
    // constructor
    function __construct() {
        include_once './db_connect.php';
		include_once './GCM.php';
        // connecting to database
        $this->db = new DB_Connect();
        $this->db->connect();
    } 

    // destructor
    function __destruct() {
        
    }
    /**
     * Đăng ký tài khoản
     */
    public function register($name,  $pass, $email, $phone, $gcm_regid){
        $result = mysqli_query($this->db->connect(),"INSERT INTO gcm_users(name, pass, email, phone, gcm_regid, created_at) VALUES('$name', '$pass', '$email', '$phone', '$gcm_regid', NOW())");
        // check for successful store
		if(!$result){
			return array("status"=>false, "alert"=>"Tài khoản đã được sử dụng");
		} else {
			return array("status"=>true, "alert"=>"Đăng ký thành công");
		}
    }
    /**
     * Đăng nhập hệ thống
     */
    public function login($pass, $username){
		$query =  "SELECT pass, name, gcm_regid FROM gcm_users WHERE pass = '$pass' AND name = '$username'";
		$result = mysqli_query($this->db->connect(),$query);
        if(!$result){
			return array("status"=>false, "alert"=>"Đăng nhập tài khoản thất bại", "regID"=>"");
        }else{
			$values = mysqli_fetch_array($result);
			if(!$values){
				return array("status"=>false, "alert"=>"Đăng nhập tài khoản thất bại", "regID"=>"");
			} else {
				$query =  "UPDATE gcm_users SET online = 'true'  WHERE pass = '$pass' AND name = '$username'";
				mysqli_query($this->db->connect(),$query);
				return array("status"=>true, "alert"=>"Đăng nhập tài khoản thành công", "regID"=>$values[0]);
			}
			
        }
    }
     /**
     * Thay doi trang thai online
     */
    public function turnon($pass, $username){
		$query =  "SELECT pass, name, gcm_regid FROM gcm_users WHERE pass = '$pass' AND name = '$username'";
		$result = mysqli_query($this->db->connect(),$query);
        if(!$result){
			return array("status"=>false, "alert"=>"Trạng thái Online tài khoản thất bại");
        }else{
			$values = mysqli_fetch_array($result);
			if(!$values){
				return array("status"=>false, "alert"=>"Trạng thái Online tài khoản thất bại");
			} else {
				$query =  "UPDATE gcm_users SET online = 'true'  WHERE pass = '$pass' AND name = '$username'";
				mysqli_query($this->db->connect(),$query);
				return array("status"=>true, "alert"=>"Trạng thái Online tài khoản thành công");
			}
			
        }
    }
      
    /**
     * Đăng xuất hệ thống
     */
    public function logout($pass, $username){
		$query =  "SELECT pass, name, gcm_regid FROM gcm_users WHERE pass = '$pass' AND name = '$username'";
		$result = mysqli_query($this->db->connect(),$query);
        if(!$result){
			return array("status"=>false, "alert"=>"Đăng xuất tài khoản thất bại");
        }else{
			$values = mysqli_fetch_array($result);
			if(!$values){
				return array("status"=>false, "alert"=>"Đăng xuất tài khoản thất bại");
			} else {
				$query =  "UPDATE gcm_users SET online = 'false'  WHERE pass = '$pass' AND name = '$username'";
				mysqli_query($this->db->connect(),$query);
				return array("status"=>true, "alert"=>"Đăng xuất tài khoản thành công");
			}
			
        }
    }
    
    public function sendGCMEmail($email, $message){
        $result = mysqli_query($this->db->connect(),"SELECT gcm_regid FROM gcm_users WHERE email = '$email'");
		if(!$result){
			return array("status"=>false, "alert"=>"Email không nằm trong hệ thống");
		} else {
			$values = mysqli_fetch_array($result);
			$gcm = new GCM();
			$gcm->send_notification($values['gcm_regid'], $message);
			return array("status"=>true, "alert"=>"Đã gửi tin");
		}
    }
    
    public function sendGCMPhone($phone, $message){
        $result = mysqli_query($this->db->connect(),"SELECT gcm_regid FROM gcm_users WHERE phone = '$phone'");
		if(!$result){
			return array("status"=>false, "alert"=>"Phone không nằm trong hệ thống");
		} else {
			$values = mysqli_fetch_array($result);
			$gcm = new GCM();
			array("result"=>$gcm->send_notification($values['gcm_regid'], $message));
			$result = array("status"=>true,"alert"=>"Đã gửi tin");
		}
    }
	/**
     * 
     */
     public function getAllFriends(){
		$result = mysqli_query($this->db->connect(),"SELECT name, email, phone, online ,gcm_regid FROM gcm_users");  
        $i=0;
        while($row = mysqli_fetch_array($result,MYSQLI_ASSOC)) {
            $rows[$i] = $row;
            $i++;
        }   
		return array("status"=>true, "alert"=>"Lấy dữ liệu thành công", "data"=>$rows);
     }
     
     
    /**
     * Storing new user
     * returns user details
     */
    public function storeUser($name,  $pass, $email, $phone, $gcm_regid) {
        // insert user into database
        $result = mysqli_query($this->db->connect(),"INSERT INTO gcm_users(name, pass, email, phone, gcm_regid, created_at) VALUES('$name', '$pass', '$email', '$phone', '$gcm_regid', NOW())");
        // check for successful store
        if ($result) {
            // get user details
            $id = mysqli_insert_id($this->db->connect()); // last inserted id
            $result = mysqli_query($this->db->connect(),"SELECT * FROM gcm_users WHERE id = $id") or die(mysql_error());
            // return user details
            if (mysqli_num_rows($result) > 0) {
                return mysqli_fetch_array($result);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    

    /**
     * Get user by email and password
     */
    public function getUserByEmail($email) {
        $result = mysqli_query($this->db->connect(),"SELECT * FROM gcm_users WHERE email = '$email' LIMIT 1");
        return $result;
    }

    /**
     * Getting all users
     */
    public function getAllUsers() {
        $result = mysqli_query($this->db->connect(),"select * FROM gcm_users");
        return $result;
    }

    /**
     * Check user is existed or not
     */
    public function isUserExisted($email) {
        $result = mysqli_query($this->db->connect(),"SELECT email from gcm_users WHERE email = '$email'");
        $no_of_rows = mysqli_num_rows($result);
        if ($no_of_rows > 0) {
            // user existed
            return true;
        } else {
            // user not existed
            return false;
        }
    }

}

?>