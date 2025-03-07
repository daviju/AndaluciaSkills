-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         10.4.32-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.7.0.6850
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para andaluciaskills
CREATE DATABASE IF NOT EXISTS `andaluciaskills` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci */;
USE `andaluciaskills`;

-- Volcando estructura para tabla andaluciaskills.especialidades
CREATE TABLE IF NOT EXISTS `especialidades` (
  `codigo` varchar(4) NOT NULL,
  `id_especialidad` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  PRIMARY KEY (`id_especialidad`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- Volcando datos para la tabla andaluciaskills.especialidades: ~12 rows (aproximadamente)
INSERT INTO `especialidades` (`codigo`, `id_especialidad`, `nombre`) VALUES
	('DAW', 1, 'Desarrollo de Aplicaciones Web'),
	('DAM', 2, 'Desarrollo de Aplicaciones Multiplataforma'),
	('ASIR', 3, 'Administración de Sistemas Informáticos en Red'),
	('SMR', 4, 'Sistemas Microinformáticos y Redes'),
	('ELEC', 5, 'Sistemas Electrotécnicos y Automatizados'),
	('COC', 6, 'Cocina y Gastronomía'),
	('AUTO', 8, 'Automoción'),
	('MARK', 9, 'Marketing y Publicidad'),
	('GEFO', 10, 'Gestión Forestal'),
	('DC', 11, 'Dirección de Cocina'),
	('GVEC', 12, 'Gestión de Ventas y Espacios Comerciales'),
	('ADE', 14, 'Administración y Finanzas');

-- Volcando estructura para tabla andaluciaskills.evaluacion
CREATE TABLE IF NOT EXISTS `evaluacion` (
  `id_evaluacion` int(11) NOT NULL AUTO_INCREMENT,
  `nota_final` double DEFAULT NULL,
  `participante_id_participante` int(11) DEFAULT NULL,
  `prueba_id_prueba` int(11) DEFAULT NULL,
  `user_id_user` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_evaluacion`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- Volcando datos para la tabla andaluciaskills.evaluacion: ~7 rows (aproximadamente)
INSERT INTO `evaluacion` (`id_evaluacion`, `nota_final`, `participante_id_participante`, `prueba_id_prueba`, `user_id_user`) VALUES
	(1, 10, 5, 7, 2),
	(2, 10, 5, 8, 2),
	(3, 6, 7, 3, 4),
	(4, 8, 7, 4, 4),
	(5, 4, 2, 9, 3),
	(6, 8.5, 1, 10, 4),
	(7, 6, 1, 4, 4);

-- Volcando estructura para tabla andaluciaskills.evaluacion_items
CREATE TABLE IF NOT EXISTS `evaluacion_items` (
  `evaluacion_id_evaluacion` int(11) DEFAULT NULL,
  `id_evaluacion_item` int(11) NOT NULL AUTO_INCREMENT,
  `item_id_item` int(11) DEFAULT NULL,
  `prueba_id_prueba` int(11) DEFAULT NULL,
  `valoracion` double DEFAULT NULL,
  PRIMARY KEY (`id_evaluacion_item`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- Volcando datos para la tabla andaluciaskills.evaluacion_items: ~19 rows (aproximadamente)
INSERT INTO `evaluacion_items` (`evaluacion_id_evaluacion`, `id_evaluacion_item`, `item_id_item`, `prueba_id_prueba`, `valoracion`) VALUES
	(1, 1, 20, 7, 10),
	(2, 2, 21, 8, 10),
	(3, 3, 11, 3, 1),
	(3, 4, 12, 3, 2),
	(3, 5, 13, 3, 3),
	(4, 6, 14, 4, 2),
	(4, 7, 15, 4, 2),
	(4, 8, 16, 4, 2),
	(4, 9, 17, 4, 1),
	(4, 10, 18, 4, 1),
	(5, 11, 22, 9, 2),
	(5, 12, 23, 9, 1),
	(5, 13, 24, 9, 1),
	(6, 14, 25, 10, 1.5),
	(6, 15, 26, 10, 5),
	(6, 16, 27, 10, 2),
	(7, 17, 14, 4, 2),
	(7, 18, 15, 4, 2),
	(7, 19, 16, 4, 2),
	(7, 20, 17, 4, 0),
	(7, 21, 18, 4, 0);

-- Volcando estructura para tabla andaluciaskills.items
CREATE TABLE IF NOT EXISTS `items` (
  `grados_consecucion` int(11) DEFAULT NULL,
  `id_item` int(11) NOT NULL AUTO_INCREMENT,
  `peso` int(11) DEFAULT NULL,
  `prueba_id_prueba` int(11) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_item`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- Volcando datos para la tabla andaluciaskills.items: ~23 rows (aproximadamente)
INSERT INTO `items` (`grados_consecucion`, `id_item`, `peso`, `prueba_id_prueba`, `descripcion`) VALUES
	(30, 1, 30, 1, 'Carrito'),
	(20, 2, 20, 1, 'Personalización de Kebabs'),
	(10, 3, 10, 1, 'Relación entre ingredientes y alergenos'),
	(10, 4, 10, 1, 'Diferentes vistas dependiendo del rol del usuario'),
	(30, 5, 30, 1, 'Creación, modificación y eliminación de los ingredientes y alergenos'),
	(50, 6, 50, 2, 'Montaje placa base'),
	(20, 7, 20, 2, 'Cuidado de los componentes'),
	(10, 8, 10, 2, 'Gestión del cableado'),
	(10, 9, 10, 2, 'Montaje del panel frontal'),
	(10, 10, 10, 2, 'Conexión del cableado'),
	(10, 11, 10, 3, 'Cableado protegido por su funda'),
	(40, 12, 40, 3, 'Aparejamiento de los cables'),
	(50, 13, 50, 3, 'Funcionamiento del cable'),
	(20, 14, 20, 4, 'Tipología de Anillo'),
	(20, 15, 20, 4, 'Tipología de Red'),
	(20, 16, 20, 4, 'Tipología de Árbol'),
	(20, 17, 20, 4, 'Tipología de Bus'),
	(20, 18, 20, 4, 'Tipología de Estrella'),
	(100, 20, 100, 7, 'Esta bueno o no coño'),
	(100, 21, 100, 8, 'Le gusta a tu hermana'),
	(50, 22, 50, 9, 'Item1'),
	(25, 23, 25, 9, 'Item2asdaw'),
	(25, 24, 25, 9, 'asdawdsdawd'),
	(20, 25, 20, 10, 'Legibilidad del codigo'),
	(60, 26, 60, 10, 'Funcionalidad del script'),
	(20, 27, 20, 10, 'Buenas practicas en el codigo');

-- Volcando estructura para tabla andaluciaskills.participantes
CREATE TABLE IF NOT EXISTS `participantes` (
  `especialidad_id_especialidad` int(11) NOT NULL,
  `id_participante` int(11) NOT NULL AUTO_INCREMENT,
  `apellidos` varchar(255) NOT NULL,
  `centro` varchar(255) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  PRIMARY KEY (`id_participante`),
  KEY `FK2sbwsdo6kev5bi9mdphik6sby` (`especialidad_id_especialidad`),
  CONSTRAINT `FK2sbwsdo6kev5bi9mdphik6sby` FOREIGN KEY (`especialidad_id_especialidad`) REFERENCES `especialidades` (`id_especialidad`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- Volcando datos para la tabla andaluciaskills.participantes: ~7 rows (aproximadamente)
INSERT INTO `participantes` (`especialidad_id_especialidad`, `id_participante`, `apellidos`, `centro`, `nombre`) VALUES
	(4, 1, 'Fernandez Rivera', 'IES El Goloso', 'Javier'),
	(1, 2, 'Pancorbo Eliche', 'IES Las Fuentezuelas', 'Adrián'),
	(8, 4, 'Pérez Ruiz', 'IES Peña del Águila', 'Jose Santiago'),
	(6, 5, 'El Jornalero', 'IES San Juan Bosco', 'Jose Manuel'),
	(5, 6, 'Doble con queso porderriba cerveza y patatas delus', 'IES Brontosaurio', 'Hamburguesa Brontosauria'),
	(4, 7, 'Jándula De la Torre', 'IES Las Fuentezuelas', 'David');

-- Volcando estructura para tabla andaluciaskills.prueba
CREATE TABLE IF NOT EXISTS `prueba` (
  `especialidad_id_especialidad` int(11) DEFAULT NULL,
  `id_prueba` int(11) NOT NULL AUTO_INCREMENT,
  `puntuacion_maxima` int(11) DEFAULT NULL,
  `enunciado` varchar(255) NOT NULL,
  PRIMARY KEY (`id_prueba`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- Volcando datos para la tabla andaluciaskills.prueba: ~8 rows (aproximadamente)
INSERT INTO `prueba` (`especialidad_id_especialidad`, `id_prueba`, `puntuacion_maxima`, `enunciado`) VALUES
	(6, 1, 50, 'Creación de una tienda de Kebabs con carrito y personalización de productos'),
	(4, 2, 80, 'Prueba de ensamblaje de un Ordenador'),
	(4, 3, 20, 'Creación de cables de red'),
	(4, 4, 10, 'Tipologías de Redes en PacketTracer'),
	(6, 7, 100, 'Platico chulo de lentejas'),
	(6, 8, 100, 'Pollito con Kui'),
	(1, 9, 100, 'Prueba de ensamblaje de un Ordenador'),
	(4, 10, 80, 'Creación de un script en JS');

-- Volcando estructura para tabla andaluciaskills.users
CREATE TABLE IF NOT EXISTS `users` (
  `especialidad_id_especialidad` int(11) DEFAULT NULL,
  `id_user` int(11) NOT NULL AUTO_INCREMENT,
  `apellidos` varchar(255) NOT NULL,
  `dni` varchar(255) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `UK6aphui3g30h49muho4c91n0yl` (`dni`),
  UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`),
  KEY `FKpkl0ecc367jrcfr55ed8xsgpd` (`especialidad_id_especialidad`),
  CONSTRAINT `FKpkl0ecc367jrcfr55ed8xsgpd` FOREIGN KEY (`especialidad_id_especialidad`) REFERENCES `especialidades` (`id_especialidad`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- Volcando datos para la tabla andaluciaskills.users: ~5 rows (aproximadamente)
INSERT INTO `users` (`especialidad_id_especialidad`, `id_user`, `apellidos`, `dni`, `nombre`, `password`, `role`, `username`) VALUES
	(1, 1, 'Lopez Justicia', '77645571D', 'David', '$2a$12$KQw2eu6zzf2UHyL/mVtzCOqrb0XuI41/TxIzDUuQPb4KSiHO0ebqS', 'ROLE_ADMIN', 'daviju'),
	(6, 2, 'Molina Martinez', '77649399L', 'Belen', '$2a$12$Ur37SW8v5HZ67Gqpi5GDHukIqDJCH59.50ayChXP2JsKcxtV97LR6', 'ROLE_EXPERTO', 'bemoma'),
	(1, 3, 'Pérez Millán', '54594771Q', 'Alejandro', '$2a$10$sYJWqJMIY1/V5ZbLO8/8fObqsu15Vm3p1cONG4uOSRNSlhhT.oH86', 'ROLE_EXPERTO', 'Axle'),
	(4, 4, 'López Justicia', '77374041V', 'Javier', '$2a$10$NBW0rTJqWXcDgyWknLgBgO3mjCpv4sGRhvGd3LZQ5QewX6/qICkBe', 'ROLE_EXPERTO', 'Justi14'),
	(14, 6, 'Guitierrez Ruiz', '12573154C', 'Marcos', '$2a$10$A2w2ReWZLrSaOSesIdU3b.dB4CWOnWXtzoPgwXMtoTVFIMsLNPLr2', 'ROLE_EXPERTO', 'MacOS');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
