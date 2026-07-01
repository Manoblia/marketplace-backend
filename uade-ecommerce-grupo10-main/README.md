# 🛒 Marketplace Backend

Backend del proyecto **Marketplace**, desarrollado con **Spring Boot** como Trabajo Práctico Integrador.

---

# 🚀 Tecnologías utilizadas

- Java 23
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- Spring Data JPA
- MySQL
- Maven
- Lombok

---

# 📋 Funcionalidades

## 👤 Autenticación

- Registro de usuarios
- Inicio de sesión con JWT
- Autorización mediante roles
- Endpoints protegidos

---

## 🛍️ Gestión de productos

- Crear productos
- Editar productos
- Eliminar productos
- Obtener listado de productos
- Obtener detalle de un producto

Cada producto incluye:

- Marca
- Categoría
- Precio
- Descuento
- Variantes por talle
- Stock
- Imágenes

---

## 🏷️ Gestión de marcas

- Crear marcas
- Editar marcas
- Eliminar marcas
- Listar marcas

---

## 📂 Gestión de categorías

- Crear categorías
- Editar categorías
- Eliminar categorías
- Listar categorías

---

## 🖼️ Gestión de imágenes

- Subida de imágenes
- Asociación de imágenes a productos
- Eliminación de imágenes
- Visualización de imágenes

---

## 🛒 Carrito

- Agregar productos
- Modificar cantidades
- Eliminar productos
- Vaciar carrito

---

## 📦 Pedidos

- Crear pedidos
- Consultar pedidos del usuario
- Confirmación de compra

---

# 🔐 Roles del sistema

## Usuario

Puede:

- Registrarse
- Iniciar sesión
- Comprar productos
- Administrar su carrito
- Administrar favoritos
- Realizar pedidos

---

## Vendedor

Además de las funciones del usuario puede:

- Administrar productos
- Administrar imágenes
- Administrar marcas
- Administrar categorías

---

# 📁 Estructura del proyecto

```
src
│
├── config
├── controllers
├── dto
├── entity
├── exceptions
├── repository
├── security
├── services
│   ├── interfaces
│   └── implementations
└── MarketplaceApplication.java
```

---

# ⚙️ Instalación

Clonar el repositorio:

```bash
git clone https://github.com/Manoblia/marketplace-backend.git
```

Abrir el proyecto con IntelliJ IDEA o Visual Studio Code.

Instalar las dependencias con Maven.

Configurar la conexión a MySQL en el archivo:

```
application.properties
```

Ejecutar la aplicación:

Windows

```bash
mvnw.cmd spring-boot:run
```

Linux / macOS

```bash
./mvnw spring-boot:run
```

---

# 🌐 Frontend

Este backend es consumido por:

https://github.com/Manoblia/marketplace-frontend

---

# 🔐 Seguridad

La autenticación se realiza mediante **JWT (JSON Web Token)**.

Los endpoints administrativos requieren permisos de **ROLE_VENDEDOR**.

Spring Security protege todos los recursos privados de la aplicación.

---

# 👥 Integrantes

- Martín Amadeo Noblia
- Santiago Gil Ishikawa
- Adrián Boado

---

# 👨‍💻 Trabajo Práctico Integrador

Proyecto desarrollado utilizando **Spring Boot**, **Spring Security**, **JWT** y **MySQL** para implementar una API REST de comercio electrónico integrada con un frontend desarrollado en React.