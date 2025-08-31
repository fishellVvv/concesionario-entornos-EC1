# Sistema de Gestión de un Concesionario de Motos

Aplicación de consola para gestionar un concesionario de motos. Permite administrar clientes y catálogo de motos, registrar ventas con **una o varias motos por venta** y llevar **control de stock**.

---

## Requisitos
- **JDK 17** o superior
- **Maven 3.9+**
- (Opcional) IntelliJ IDEA / VS Code / Eclipse

---

## Instrucciones de uso

### 1) Compilar y ejecutar tests
```bash
# Linux/Mac
./mvnw clean test
# Windows
.\mvnw.cmd clean test
```

### 2) Ejecutar la aplicación
- **Desde IntelliJ/IDE**: Ejecuta la clase `concesionario.Main`.
- **Desde terminal**:
  ```bash
  # Compila (sin tests) y genera los .class en target/classes
  ./mvnw -DskipTests compile        # Windows: .\mvnw.cmd -DskipTests compile
  # Lanza el programa
  java -cp target/classes concesionario.Main
  ```

### 3) Navegación por menús
- **Gestión de clientes**
    - Alta de Cliente
    - Baja de Cliente
    - Modificar Cliente
    - Búsqueda por DNI
    - Listado de Clientes
    - Listado de Clientes (ordenado por nombre)
- **Gestión de motos**
    - Alta de moto
    - Búsqueda por Marca/modelo
    - Listado de Motos
    - Listado de Motos (ordenado por marca)
    - Reponer stock
- **Gestión de ventas**
    - Registrar venta
    - Listado de ventas
    - Búsqueda por cliente
    - Mostrar Totales Ventas

---

## Estructura del proyecto
```css
concesionario-entornos-EC1/
├─ pom.xml
├─ README.md
├─ docs/  # Javadoc HTML (index.html)
└─ src/
   └─ main/java/
      └─ concesionario/
         ├─ Main.java
         ├─ modelo/
         │  ├─ Cliente.java
         │  ├─ Moto.java
         │  └─ Venta.java
         └─ servicio/
            └─ Datos.java
```

---

## Documentación Javadoc
La documentación generada está en /docs/index.html

→ [Documentación Javadoc](./docs/index.html)

---

## Autoría y licencia
Autor: **Víctor García Vigil (fishellVvv)**  
Licencia: MIT. Puedes usar y modificar este proyecto con libertad.
