-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 25 Mar 2021, 00:30
-- Wersja serwera: 10.1.40-MariaDB
-- Wersja PHP: 7.3.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `message_broker`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `message`
--

CREATE TABLE `message` (
  `message_id` int(11) NOT NULL,
  `message` text COLLATE utf8mb4_polish_ci NOT NULL,
  `expiration_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `subject_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;

--
-- Zrzut danych tabeli `message`
--

INSERT INTO `message` (`message_id`, `message`, `expiration_date`, `subject_id`) VALUES
(1, 'test message', '2022-12-03 00:00:00', 1),
(2, 'test_message', '2021-03-24 00:00:00', 1),
(3, 'test_message', '2021-03-24 00:00:00', 1),
(4, 'test_message', '2021-03-24 00:00:00', 1),
(5, 'test_message', '2021-03-24 21:54:00', 1),
(6, 'test_message', '2021-03-24 21:54:00', 1),
(7, 'test_message', '2021-03-24 20:55:00', 1),
(8, 'test_message_21:56', '2021-03-24 20:56:00', 1),
(9, 'test_message_21:56', '2021-03-24 20:58:00', 1),
(10, 'test-message', '2021-03-24 22:30:00', 1),
(14, 'test-message-music', '2021-03-24 22:30:00', 3),
(15, 'test-message-stocks', '2021-03-24 22:30:00', 4);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `subject`
--

CREATE TABLE `subject` (
  `subject_id` int(11) NOT NULL,
  `subject_type` text COLLATE utf8mb4_polish_ci NOT NULL,
  `id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;

--
-- Zrzut danych tabeli `subject`
--

INSERT INTO `subject` (`subject_id`, `subject_type`, `id`) VALUES
(0, 'AVIATION', NULL),
(1, 'AUTOMOTIVE', NULL),
(3, 'MUSIC', NULL),
(4, 'STOCKS', NULL);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` text COLLATE utf8mb4_polish_ci NOT NULL,
  `address` text COLLATE utf8mb4_polish_ci NOT NULL,
  `passwd` text COLLATE utf8mb4_polish_ci NOT NULL,
  `type` text COLLATE utf8mb4_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;

--
-- Zrzut danych tabeli `user`
--

INSERT INTO `user` (`id`, `username`, `address`, `passwd`, `type`) VALUES
(1, 'Admin1', '169.254.169.149', '12345', 'ADMIN'),
(3, 'Admin2', '169.254.169.149', '$2a$10$L6IzSidMtYj57XpCChWmpOrTRYf7RUQrbH9bfR4EMM074aitGsXH.', 'ADMIN'),
(4, 'User1', '169.254.169.149', '$2a$10$G4F7Sa7auyawTsCAuGnC1OBsA2QoIDwq7pBfHkZr/LNydfmCHHt/S', 'USER'),
(5, 'Admin_test', '169.254.169.149', '$2a$10$BSbLb.xD6CCjh8s9jr1oY.wFQfVyCZaNV2puMawsC5lqpZoPUzL8.', 'ADMIN');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `user_subjects`
--

CREATE TABLE `user_subjects` (
  `id` int(11) NOT NULL,
  `subject_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;

--
-- Zrzut danych tabeli `user_subjects`
--

INSERT INTO `user_subjects` (`id`, `subject_id`) VALUES
(3, 1),
(3, 4);

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `message`
--
ALTER TABLE `message`
  ADD PRIMARY KEY (`message_id`),
  ADD KEY `subject_id` (`subject_id`);

--
-- Indeksy dla tabeli `subject`
--
ALTER TABLE `subject`
  ADD PRIMARY KEY (`subject_id`),
  ADD KEY `FK36alnkpouvs4xjnq8iq5mmref` (`id`);

--
-- Indeksy dla tabeli `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `user_subjects`
--
ALTER TABLE `user_subjects`
  ADD KEY `id` (`id`),
  ADD KEY `subject_id_user` (`subject_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT dla tabeli `message`
--
ALTER TABLE `message`
  MODIFY `message_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT dla tabeli `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `message`
--
ALTER TABLE `message`
  ADD CONSTRAINT `subject_id` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`subject_id`);

--
-- Ograniczenia dla tabeli `subject`
--
ALTER TABLE `subject`
  ADD CONSTRAINT `FK36alnkpouvs4xjnq8iq5mmref` FOREIGN KEY (`id`) REFERENCES `user` (`id`);

--
-- Ograniczenia dla tabeli `user_subjects`
--
ALTER TABLE `user_subjects`
  ADD CONSTRAINT `id` FOREIGN KEY (`id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `subject_id_user` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`subject_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
