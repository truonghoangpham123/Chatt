-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jul 30, 2015 at 06:15 AM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `chattapp`
--

-- --------------------------------------------------------

--
-- Table structure for table `gcm_users`
--

CREATE TABLE IF NOT EXISTS `gcm_users` (
  `id` int(11) DEFAULT NULL,
  `online` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `gcm_regid` text COLLATE utf8_unicode_ci,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `pass` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `phone` varchar(12) COLLATE utf8_unicode_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `gcm_users`
--

INSERT INTO `gcm_users` (`id`, `online`, `gcm_regid`, `name`, `pass`, `email`, `phone`, `created_at`) VALUES
(NULL, 'false', 'APA91bEqXh3q_8r7IAqp_J1MM25oXg84X6tcd3GGKogscYAUwwvaPEzU-mXMvg0rWZX5d85_limW8LkLREOfaZVem2hpfel3j7Mim9ggDWjuNmerlpox-jKX0wN3awBLHwMfKzpv_1W0', 'admin', '202cb962ac59075b964b07152d234b70', 'admin@gmail.com', '123', '2015-07-28 11:03:39'),
(NULL, 'false', 'APA91bEIrjMeFjg8Yz7YZW_bnWgargj-2hfIdckFOENzwfPEGK-IGigiMcq04i3bycL5bj3UTqEel-DnakAcB0m0w_EXT-o0-mtLlcd6FrM9mtfhjfFzXg2Plsqb464bW6dX7Uujicex', 'device', '202cb962ac59075b964b07152d234b70', 'device@gmail.com', '1234', '2015-07-28 11:03:54');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
