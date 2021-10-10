-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 10-10-2021 a las 02:48:59
-- Versión del servidor: 10.4.21-MariaDB
-- Versión de PHP: 8.0.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `veontecdb`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tblcompras`
--

CREATE TABLE `tblcompras` (
  `compID` int(11) NOT NULL,
  `compProducto` int(11) NOT NULL DEFAULT 0,
  `compVendedor` int(11) NOT NULL DEFAULT 0,
  `compComprador` int(11) NOT NULL,
  `compTitulo` varchar(60) COLLATE utf8_bin NOT NULL,
  `compCantidad` int(11) NOT NULL,
  `compPrecio` double NOT NULL,
  `compFecha` varchar(60) COLLATE utf8_bin NOT NULL,
  `compEstado` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tblimages`
--

CREATE TABLE `tblimages` (
  `imagID` int(11) NOT NULL,
  `imagUsuario` int(11) NOT NULL,
  `imagProducto` int(11) NOT NULL,
  `imagMedia` longblob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tblpreguntas`
--

CREATE TABLE `tblpreguntas` (
  `pregID` int(11) NOT NULL,
  `pregProducto` int(11) NOT NULL,
  `pregVendedor` int(11) NOT NULL,
  `pregComprador` int(11) NOT NULL,
  `pregPregunta` varchar(120) COLLATE utf8_bin NOT NULL,
  `pregFecha` varchar(60) COLLATE utf8_bin NOT NULL,
  `pregEstado` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tblproductos`
--

CREATE TABLE `tblproductos` (
  `prodID` int(11) NOT NULL,
  `prodTitulo` varchar(60) COLLATE utf8_bin NOT NULL,
  `prodDescripcion` mediumtext COLLATE utf8_bin NOT NULL,
  `prodCategoria` varchar(60) COLLATE utf8_bin NOT NULL DEFAULT 'Vacio',
  `prodPrecio` double NOT NULL,
  `prodStock` int(11) NOT NULL,
  `prodTipo` int(11) NOT NULL,
  `prodEnlace` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `prodUsuario` int(11) NOT NULL,
  `prodMedia` longblob DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tblrespuestas`
--

CREATE TABLE `tblrespuestas` (
  `respID` int(11) NOT NULL,
  `respPregunta` int(11) NOT NULL,
  `respProducto` int(11) NOT NULL,
  `respVendedor` int(11) NOT NULL,
  `respComprador` int(11) NOT NULL,
  `respRespuesta` varchar(160) COLLATE utf8_bin NOT NULL,
  `respFecha` varchar(60) COLLATE utf8_bin NOT NULL,
  `respEstado` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tblusuarios`
--

CREATE TABLE `tblusuarios` (
  `usuaID` int(11) NOT NULL,
  `usuaNombre` varchar(150) COLLATE utf8_bin NOT NULL,
  `usuaCorreo` varchar(60) COLLATE utf8_bin NOT NULL,
  `usuaPassword` varchar(250) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Volcado de datos para la tabla `tblusuarios`
--

INSERT INTO `tblusuarios` (`usuaID`, `usuaNombre`, `usuaCorreo`, `usuaPassword`) VALUES
(4, 'Boris P.', 'boris@example.com', '$argon2i$v=19$m=65536,t=10,p=1$5x55s5ZnNLAtQhRuwjF9Vw$NN6/HCv3Tp2nIKwOsFOtavu/jxVsHHiB1IA4c/1LDqo'),
(5, 'Victor J.', 'victor@example.com', '$argon2i$v=19$m=65536,t=10,p=1$3dCNxLxLU+KHLClgYQvBnw$OsqbgwSi98IuCn3rd8L1QtJugLsjWgb982zuPBIYOsg'),
(6, 'Esteba de Leon Jimenez Laces', 'esteba@example.com', '$argon2i$v=19$m=65536,t=10,p=1$f9pUeXwoWEHHSzkU/0GDXw$HXGGQPfqiDZzDHr8MC8zJqORf5q7asMyoIX/C65KhpM'),
(7, 'Esteban', 'esteban@example.com', '$argon2i$v=19$m=65536,t=10,p=1$pffJE/n2js7O33Jm6vn9BA$I1AltgSLBjx84tqAl0ENlFADN2YyZW3bRhYBgt8mkAA');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tblventas`
--

CREATE TABLE `tblventas` (
  `ventID` int(11) NOT NULL,
  `ventProducto` int(11) NOT NULL DEFAULT 0,
  `ventVendedor` int(11) NOT NULL,
  `ventComprador` int(11) DEFAULT 0,
  `ventTitulo` varchar(60) COLLATE utf8_bin NOT NULL,
  `ventCantidad` int(11) NOT NULL,
  `ventPrecio` double NOT NULL,
  `ventFecha` varchar(60) COLLATE utf8_bin NOT NULL,
  `ventEstado` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Volcado de datos para la tabla `tblventas`
--

INSERT INTO `tblventas` (`ventID`, `ventProducto`, `ventVendedor`, `ventComprador`, `ventTitulo`, `ventCantidad`, `ventPrecio`, `ventFecha`, `ventEstado`) VALUES
(3, 39, 5, 4, 'Balon EUFA', 3, 399.9, '29/08/2021', 0);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `tblcompras`
--
ALTER TABLE `tblcompras`
  ADD PRIMARY KEY (`compID`),
  ADD KEY `fk_tblusua_tblcomp_comprador` (`compComprador`);

--
-- Indices de la tabla `tblimages`
--
ALTER TABLE `tblimages`
  ADD PRIMARY KEY (`imagID`);

--
-- Indices de la tabla `tblpreguntas`
--
ALTER TABLE `tblpreguntas`
  ADD PRIMARY KEY (`pregID`),
  ADD KEY `fk_tblprod_tblpreg_producto` (`pregProducto`),
  ADD KEY `fk_tblusua_tblpreg_comprador` (`pregComprador`),
  ADD KEY `fk_tblusua_tblpreg_vendedor` (`pregVendedor`);

--
-- Indices de la tabla `tblproductos`
--
ALTER TABLE `tblproductos`
  ADD PRIMARY KEY (`prodID`),
  ADD KEY `fk_tblprod_tblusua` (`prodUsuario`);

--
-- Indices de la tabla `tblrespuestas`
--
ALTER TABLE `tblrespuestas`
  ADD PRIMARY KEY (`respID`),
  ADD KEY `fk_tblpreg_tblresp_pregunta` (`respPregunta`),
  ADD KEY `fk_tblprod_tblresp_producto` (`respProducto`),
  ADD KEY `fk_tblusua_tblresp_comprador` (`respComprador`),
  ADD KEY `fk_tblusua_tblresp_vendedor` (`respVendedor`);

--
-- Indices de la tabla `tblusuarios`
--
ALTER TABLE `tblusuarios`
  ADD PRIMARY KEY (`usuaID`);

--
-- Indices de la tabla `tblventas`
--
ALTER TABLE `tblventas`
  ADD PRIMARY KEY (`ventID`),
  ADD KEY `fk_tblusua_tblvent_vendedor` (`ventVendedor`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `tblcompras`
--
ALTER TABLE `tblcompras`
  MODIFY `compID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `tblimages`
--
ALTER TABLE `tblimages`
  MODIFY `imagID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `tblpreguntas`
--
ALTER TABLE `tblpreguntas`
  MODIFY `pregID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT de la tabla `tblproductos`
--
ALTER TABLE `tblproductos`
  MODIFY `prodID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46;

--
-- AUTO_INCREMENT de la tabla `tblrespuestas`
--
ALTER TABLE `tblrespuestas`
  MODIFY `respID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT de la tabla `tblusuarios`
--
ALTER TABLE `tblusuarios`
  MODIFY `usuaID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `tblventas`
--
ALTER TABLE `tblventas`
  MODIFY `ventID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `tblcompras`
--
ALTER TABLE `tblcompras`
  ADD CONSTRAINT `fk_tblusua_tblcomp_comprador` FOREIGN KEY (`compComprador`) REFERENCES `tblusuarios` (`usuaID`) ON DELETE CASCADE;

--
-- Filtros para la tabla `tblpreguntas`
--
ALTER TABLE `tblpreguntas`
  ADD CONSTRAINT `fk_tblprod_tblpreg_producto` FOREIGN KEY (`pregProducto`) REFERENCES `tblproductos` (`prodID`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_tblusua_tblpreg_comprador` FOREIGN KEY (`pregComprador`) REFERENCES `tblusuarios` (`usuaID`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_tblusua_tblpreg_vendedor` FOREIGN KEY (`pregVendedor`) REFERENCES `tblusuarios` (`usuaID`) ON DELETE CASCADE;

--
-- Filtros para la tabla `tblproductos`
--
ALTER TABLE `tblproductos`
  ADD CONSTRAINT `fk_tblprod_tblusua` FOREIGN KEY (`prodUsuario`) REFERENCES `tblusuarios` (`usuaID`) ON DELETE CASCADE;

--
-- Filtros para la tabla `tblrespuestas`
--
ALTER TABLE `tblrespuestas`
  ADD CONSTRAINT `fk_tblpreg_tblresp_pregunta` FOREIGN KEY (`respPregunta`) REFERENCES `tblpreguntas` (`pregID`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_tblprod_tblresp_producto` FOREIGN KEY (`respProducto`) REFERENCES `tblproductos` (`prodID`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_tblusua_tblresp_comprador` FOREIGN KEY (`respComprador`) REFERENCES `tblusuarios` (`usuaID`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_tblusua_tblresp_vendedor` FOREIGN KEY (`respVendedor`) REFERENCES `tblusuarios` (`usuaID`) ON DELETE CASCADE;

--
-- Filtros para la tabla `tblventas`
--
ALTER TABLE `tblventas`
  ADD CONSTRAINT `fk_tblusua_tblvent_vendedor` FOREIGN KEY (`ventVendedor`) REFERENCES `tblusuarios` (`usuaID`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
