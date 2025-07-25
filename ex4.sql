SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

CREATE DATABASE IF NOT EXISTS `ex4` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `ex4`;

DROP TABLE IF EXISTS `cart_item`;
CREATE TABLE `cart_item` (
  `quantity` int(11) NOT NULL,
  `id` bigint(20) NOT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  `user_account_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `cart_item_seq`;
CREATE TABLE `cart_item_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `cart_item_seq` (`next_val`) VALUES
(1),
(1);

DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` bigint(20) NOT NULL,
  `category_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `category` (`id`, `category_name`) VALUES
(3, 'Baby'),
(1, 'Home'),
(2, 'Kitchen'),
(4, 'Office');

DROP TABLE IF EXISTS `category_seq`;
CREATE TABLE `category_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `category_seq` (`next_val`) VALUES
(201),
(201);

DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `total_payment` double NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `user_account_id` bigint(20) DEFAULT NULL,
  `city` varchar(255) NOT NULL,
  `country` varchar(255) NOT NULL,
  `name_on_card` varchar(255) NOT NULL,
  `street_address` varchar(255) NOT NULL,
  `zip_code` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `orders` (`total_payment`, `created_at`, `id`, `user_account_id`, `city`, `country`, `name_on_card`, `street_address`, `zip_code`) VALUES
(100, '2025-07-12 23:01:53.000000', 1, 2, 'Bet Shemesh', 'Israel', 'Shalom Kovan', 'נחל צאלים 3', '9909018'),
(70, '2025-07-13 10:11:55.000000', 2, 102, 'Bet Shemesh', 'Israel', 'david', 'נחל צאלים 3', '9909018'),
(70, '2025-07-14 19:09:38.000000', 52, 102, 'Bet Shemesh', 'Israel', 'Shalom Kovan', 'נחל צאלים 3', '9909018');

DROP TABLE IF EXISTS `orders_seq`;
CREATE TABLE `orders_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `orders_seq` (`next_val`) VALUES
(151),
(151);

DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item` (
  `quantity` int(11) NOT NULL,
  `id` bigint(20) NOT NULL,
  `order_id` bigint(20) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `order_item` (`quantity`, `id`, `order_id`, `product_id`) VALUES
(2, 1, 1, 1),
(2, 2, 1, 3),
(2, 52, 2, 3),
(2, 102, 52, 3);

DROP TABLE IF EXISTS `order_item_seq`;
CREATE TABLE `order_item_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `order_item_seq` (`next_val`) VALUES
(201),
(201);

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `inventory` int(11) NOT NULL,
  `price` double NOT NULL,
  `category_id` bigint(20) DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `product_description` varchar(255) NOT NULL,
  `product_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `product` (`inventory`, `price`, `category_id`, `id`, `image_url`, `product_description`, `product_name`) VALUES
(18, 15, 3, 1, 'babypowder.jpg', 'Johnson\'s Baby Powder with Naturally Derived Cornstarch, Aloe & Vitamin E for Delicate Skin, Hypoallergenic, Free of Parabens, Phthalates & Dyes for Gentle Baby Skin Care, 15 oz', 'Johnson\'s Baby Powder'),
(0, 15, 3, 2, 'diapers.jpg', 'Huggies Size Newborn Diapers, Little Snugglers Baby Diapers, Size Newborn (up to 10 lbs), 84 Count, Packaging May Vary', 'Huggies Size Newborn Diapers'),
(4, 35, 3, 3, '41OnJlULZtL._SX300_SY300_QL70_FMwebp_.webp', 'Infantino Music & Lights 3-in-1 Discovery Seat & Booster, Toddler Booster Seat for Dining Table or Kitchen Chair, Baby Activity Center & Snack Tray, Teal', 'Infantino Music & Lights 3-in-1'),
(10, 299, 2, 4, '71rZ3wq5WCL.__AC_SX300_SY300_QL70_FMwebp_.webp', 'Iceman Slush-Ease Slushie Machine, Frozen Drink Maker & Slushy Machine with 5 Presets + Custom Option, Easy-Dispensing Lever for Frozen Margaritas, Frappes, and Slushies - Black', 'Iceman Slush-Ease Slushie Machine');

DROP TABLE IF EXISTS `product_seq`;
CREATE TABLE `product_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `product_seq` (`next_val`) VALUES
(151),
(151);

DROP TABLE IF EXISTS `review`;
CREATE TABLE `review` (
  `rating` int(11) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  `user_account_id` bigint(20) DEFAULT NULL,
  `comment_title` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `product_review` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `review` (`rating`, `created_at`, `id`, `product_id`, `user_account_id`, `comment_title`, `name`, `product_review`) VALUES
(5, '2025-07-15 15:05:01.000000', 52, 1, 2, 'Great Product', 'Yossi Kovan', 'This product is great!!!');

DROP TABLE IF EXISTS `review_seq`;
CREATE TABLE `review_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `review_seq` (`next_val`) VALUES
(151),
(151);

DROP TABLE IF EXISTS `user_account`;
CREATE TABLE `user_account` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `user_account` (`id`, `email`, `password`, `role`, `username`) VALUES
(1, 'admin@gmail.com', '$2a$10$98R.v7FFBiXEBSRObUk4VeWO6ONIvLBWS0xoJsFb1YNH31Kw7Boa2', 'ADMIN', 'admin'),
(2, 'yossikovan@gmail.com', '$2a$10$2JKeOkD41sTSeUwm3fUQDOpnk1z22blbPCL0Pix5iuzf48YlhsxSm', 'USER', 'yossi kovan'),
(102, 'david@gmail.com', '$2a$10$MqXJiCs5wUPSdhQMnX5i/.IEo6Z7JaBFWvdmO17bPiVJaQ4ManudC', 'USER', 'david');

DROP TABLE IF EXISTS `user_account_seq`;
CREATE TABLE `user_account_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `user_account_seq` (`next_val`) VALUES
(201),
(201);


ALTER TABLE `cart_item`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKjcyd5wv4igqnw413rgxbfu4nv` (`product_id`),
  ADD KEY `FKoxxqgjd26t5x7kdwysf360mxu` (`user_account_id`);

ALTER TABLE `category`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKlroeo5fvfdeg4hpicn4lw7x9b` (`category_name`);

ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKn00mfbuqbp2jr2y3x8v1as64u` (`user_account_id`);

ALTER TABLE `order_item`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKt4dc2r9nbvbujrljv3e23iibt` (`order_id`),
  ADD KEY `FK551losx9j75ss5d6bfsqvijna` (`product_id`);

ALTER TABLE `product`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK1mtsbur82frn64de7balymq9s` (`category_id`);

ALTER TABLE `review`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKiyof1sindb9qiqr9o8npj8klt` (`product_id`),
  ADD KEY `FKjoxnyifll0956fekd244bn3dy` (`user_account_id`);

ALTER TABLE `user_account`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKhl02wv5hym99ys465woijmfib` (`email`),
  ADD UNIQUE KEY `UKcastjbvpeeus0r8lbpehiu0e4` (`username`);


ALTER TABLE `cart_item`
  ADD CONSTRAINT `FKjcyd5wv4igqnw413rgxbfu4nv` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  ADD CONSTRAINT `FKoxxqgjd26t5x7kdwysf360mxu` FOREIGN KEY (`user_account_id`) REFERENCES `user_account` (`id`);

ALTER TABLE `orders`
  ADD CONSTRAINT `FKn00mfbuqbp2jr2y3x8v1as64u` FOREIGN KEY (`user_account_id`) REFERENCES `user_account` (`id`);

ALTER TABLE `order_item`
  ADD CONSTRAINT `FK551losx9j75ss5d6bfsqvijna` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  ADD CONSTRAINT `FKt4dc2r9nbvbujrljv3e23iibt` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`);

ALTER TABLE `product`
  ADD CONSTRAINT `FK1mtsbur82frn64de7balymq9s` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`);

ALTER TABLE `review`
  ADD CONSTRAINT `FKiyof1sindb9qiqr9o8npj8klt` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  ADD CONSTRAINT `FKjoxnyifll0956fekd244bn3dy` FOREIGN KEY (`user_account_id`) REFERENCES `user_account` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
