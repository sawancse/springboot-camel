-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema emart
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema emart
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `emart` ;
USE `emart` ;

-- -----------------------------------------------------
-- Table `emart`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `emart`.`User` (
  `UserId` INT NOT NULL,
  `Name` VARCHAR(45) NULL,
  `Email` VARCHAR(45) NULL,
  `Mobile` VARCHAR(45) NULL,
  `Password` VARCHAR(45) NULL,
  `Status` VARCHAR(45) NULL,
  `UserType` VARCHAR(45) NULL,
  `Expiry` INT NULL,
  `PermanentAddress` VARCHAR(45) NULL,
  `PermanentPinCode` INT NULL,
  `DeliveryAddress` VARCHAR(45) NULL,
  `DeliveryPinCode` INT NULL,
  `UserIdProofType` VARCHAR(45) NULL,
  `Country` VARCHAR(45) NULL,
  `City` VARCHAR(45) NULL,
  `VendorShopPinCode` VARCHAR(45) NULL,
  `CreationDate` DATETIME NULL,
  `lastModificationDate` DATETIME NULL,
  `User_UserId` INT NOT NULL,
  PRIMARY KEY (`UserId`, `User_UserId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `emart`.`UserPermission`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `emart`.`UserPermission` (
  `UserIdPermission` INT NOT NULL,
  `Email` VARCHAR(45) NULL,
  `UserType` VARCHAR(45) NULL,
  `UserPages` VARCHAR(45) NULL,
  `Permission` VARCHAR(45) NULL,
  `Status` VARCHAR(45) NULL,
  `CreationDate` DATETIME NULL,
  `lastModificationDate` DATETIME NULL,
  PRIMARY KEY (`UserIdPermission`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `emart`.`Location`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `emart`.`Location` (
  `Country` VARCHAR(45) NULL,
  `State` VARCHAR(45) NULL,
  `City` VARCHAR(45) NULL,
  `Area` VARCHAR(45) NULL,
  `Apartment` VARCHAR(45) NULL,
  `Pin` VARCHAR(45) NULL,
  `ZoneType` VARCHAR(45) NULL,
  `GovRestrictedAreaType` VARCHAR(45) NULL)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `emart`.`Product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `emart`.`Product` (
  `ProductId` BIGINT(8) NOT NULL,
  `ProductIdentifier` VARCHAR(45) NULL,
  `VendorId` VARCHAR(45) NULL,
  `VendorProductCategoryId` VARCHAR(45) NULL,
  `ProductName` VARCHAR(45) NULL,
  `BrandName` VARCHAR(45) NULL,
  `ProductType` VARCHAR(45) NULL,
  `Brand` VARCHAR(45) NULL,
  `Rate` INT NULL,
  `Price` FLOAT NULL,
  `Discount` INT NULL,
  `Size` INT NULL,
  `Weight` INT NULL,
  `Color` VARCHAR(45) NULL,
  `Quantity` INT NULL,
  `ProductExpiry` DATETIME NULL,
  `isVegitarian` INT NULL,
  `RemainingQuantity` INT NULL,
  `ShippingFee` INT NULL,
  `productImage` BLOB NULL,
  `productImageSize` VARCHAR(45) NULL,
  `Availability` VARCHAR(45) NULL,
  `KeywordToSearch` VARCHAR(45) NULL,
  `CreationDate` DATETIME NULL,
  `lastModificationDate` DATETIME NULL,
  `ProductDescription` VARCHAR(45) NULL,
  PRIMARY KEY (`ProductId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `emart`.`Vendor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `emart`.`Vendor` (
  `VendorId` INT NOT NULL,
  `VendorEmail` VARCHAR(45) NULL,
  `VendorAccountHolderName` VARCHAR(45) NULL,
  `VendorShopName` VARCHAR(45) NULL,
  `VendorLicense` VARCHAR(45) NULL,
  `VendorPinCode` INT NULL,
  `LicenseStatus` VARCHAR(45) NULL,
  `VendorType` VARCHAR(45) NULL,
  `VendorBusinessName` VARCHAR(45) NULL,
  `VendorBusinessAddress` VARCHAR(45) NULL,
  `AccountType` VARCHAR(45) NULL,
  `AccountNumber` VARCHAR(45) NULL,
  `ReEnterAccountNumber` VARCHAR(45) NULL,
  `IFSC Code` VARCHAR(45) NULL,
  `PanNumber` VARCHAR(45) NULL,
  `GSTNumber` VARCHAR(45) NULL,
  `ProductTaxCode` VARCHAR(45) NULL,
  `CreationDate` DATETIME NULL,
  `lastModificationDate` DATETIME NULL,
  PRIMARY KEY (`VendorId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `emart`.`VendorProductCatalog`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `emart`.`VendorProductCatalog` (
  `VendorId` INT NOT NULL,
  `VendorProductCategoryId` VARCHAR(45) NULL,
  `pageName` VARCHAR(45) NULL,
  `bannerUploadedImage` BLOB NULL,
  `bannerUploadedImageSize` INT NULL,
  PRIMARY KEY (`VendorId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `emart`.`ProductCatalog`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `emart`.`ProductCatalog` (
  `VendorCategoryId` INT NOT NULL,
  `VendorType` VARCHAR(45) NULL,
  `VendorCategory` VARCHAR(45) NULL,
  `VendorCategoryStatus` VARCHAR(45) NULL,
  `VendorSubCategory1` VARCHAR(45) NULL,
  `VendorSubCategory2` VARCHAR(45) NULL,
  `VendorSubCategory3` VARCHAR(45) NULL,
  `VendorBrandName` VARCHAR(45) NULL,
  `VendorSubCategoryPopularSearch` VARCHAR(45) NULL,
  PRIMARY KEY (`VendorCategoryId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `emart`.`CartItem`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `emart`.`CartItem` (
  `CartProductId` INT NOT NULL,
  `ProductId` INT NULL,
  `VendorId` VARCHAR(45) NULL,
  `ProductCategory` VARCHAR(45) NULL,
  `ProductName` VARCHAR(45) NULL,
  `Price` FLOAT NULL,
  `Quantity` INT NULL,
  `SavedForLaterItems` VARCHAR(45) NULL,
  `CurrencyCode` VARCHAR(45) NULL,
  `ProductTotal` FLOAT NULL,
  `CreationDate` DATETIME NULL,
  `lastModificationDate` DATETIME NULL,
  PRIMARY KEY (`CartProductId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `emart`.`PurchasedHistory`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `emart`.`PurchasedHistory` (
  `CartProductId` VARCHAR(45) NOT NULL,
  `ProductId` INT NULL,
  `VendorId` INT NULL,
  `ProductCategory` VARCHAR(45) NULL,
  `ProductName` VARCHAR(45) NULL,
  `Price` FLOAT NULL,
  `Quantity` INT NULL,
  `Status` VARCHAR(45) NULL,
  `CurrencyCode` VARCHAR(45) NULL,
  `ProductTotal` VARCHAR(45) NULL,
  `ProductRating` VARCHAR(45) NULL,
  `CreationDate` DATETIME NULL,
  `lastModificationDate` DATETIME NULL,
  PRIMARY KEY (`CartProductId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `emart`.`Notification`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `emart`.`Notification` (
  `NotificationId` INT NOT NULL,
  `OrderId` INT NULL,
  `Email` VARCHAR(45) NULL,
  `NotificationType` VARCHAR(45) NULL,
  `Otp` INT NULL,
  `Message` VARCHAR(45) NULL,
  `Expiry` INT NULL,
  `CreationDate` DATETIME NULL,
  `lastModifiedDate` DATETIME NULL,
  PRIMARY KEY (`NotificationId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `emart`.`PaymentHistory`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `emart`.`PaymentHistory` (
  `PaymentId` INT NOT NULL,
  `OrderId` INT NULL,
  `VendorId` INT NULL,
  `TotalAmount` INT NULL,
  `Discount` INT NULL,
  `PaymentMethod` VARCHAR(45) NULL,
  `CardNo` VARCHAR(45) NULL,
  `CardExpiry` VARCHAR(45) NULL,
  `CardHolderName` VARCHAR(45) NULL,
  `SGST` VARCHAR(45) NULL,
  `CGST` VARCHAR(45) NULL,
  `PaymentResponseId` VARCHAR(45) NULL,
  `PaymentResponseStatus` VARCHAR(45) NULL,
  PRIMARY KEY (`PaymentId`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
