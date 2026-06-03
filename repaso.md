# Repaso para sustentación – Sistema de Gestión de Proveedores

> **Autores:** Jesús Ruiz y Noah Padilla
> Documento de estudio: explica **todo el código** capa por capa, el *por qué*
> de cada decisión y qué decir al exponer.

---

## Índice

1. [Idea general del proyecto](#1-idea-general)
2. [¿Qué es la arquitectura por capas?](#2-arquitectura-por-capas)
3. [Las entidades y sus relaciones](#3-entidades-y-relaciones)
4. [Capa Enums](#4-capa-enums)
5. [Capa Domain](#5-capa-domain)
6. [Capa DTO](#6-capa-dto)
7. [Capa Repository](#7-capa-repository)
8. [Capa Service](#8-capa-service)
9. [Capa Controller](#9-capa-controller)
10. [Capa View + Main](#10-capa-view--main)
11. [El recorrido completo de un dato (ejemplo)](#11-recorrido-completo)
12. [Pruebas unitarias](#12-pruebas-unitarias)
13. [Conceptos de Java que debes dominar](#13-conceptos-clave)
14. [Posibles preguntas del profesor](#14-posibles-preguntas)

---

## 1. Idea general

La aplicación administra la información de un **proveedor** y de las entidades
que lo rodean. Por consola se puede **crear, consultar, actualizar y eliminar**
(CRUD) registros de tres entidades:

- **Supplier (Proveedor)** → entidad principal (6 atributos).
- **Enterprise (Empresa)** → entidad asociada (5 atributos).
- **Order (Pedido)** → entidad por composición (5 atributos).

Todo está organizado en **capas**, de modo que cada clase tiene una sola
responsabilidad. Esto se llama **separación de responsabilidades** (en inglés,
*Separation of Concerns*).

**Frase para exponer:**
> "Construimos una aplicación CRUD por consola con arquitectura en capas, donde
> cada capa depende solo de la capa inmediatamente inferior, lo que hace el
> código ordenado, reutilizable y fácil de probar."

---

## 2. Arquitectura por capas

Imagina una pila de cajas; cada una habla solo con la de abajo:

```
Vista (View)        →  habla con el usuario (entrada/salida)
   ↓
Controlador         →  valida los datos y los convierte
   ↓
Servicio (Service)  →  reglas de negocio
   ↓
Repositorio         →  guarda/recupera datos (en memoria)
   ↓
Dominio (Domain)    →  los objetos del problema
```

Y de forma transversal:
- **DTO** (`ResultDTO<T>`): "sobre" que lleva el resultado de ida y vuelta.
- **Enums** (`ClientType`, `RegexPattern`): valores fijos y patrones de validación.

**¿Por qué es bueno?**
- Si cambias la forma de guardar (por ejemplo, a una base de datos), solo tocas
  el **Repository**.
- Si cambias la pantalla (de consola a interfaz gráfica), solo tocas la **View**.
- Cada capa se puede **probar por separado**.

---

## 3. Entidades y relaciones

| Entidad | Rol | Atributos |
|---------|-----|-----------|
| `Supplier` | Principal | idSupplier, age, name, isActive, comission, **orders** |
| `Enterprise` | Asociada | idEnterprise, name, addres, numberOrders, **suppliers** |
| `Order` | Composición | clientType, nameClient, idSupplier, addressClient, order |

### Tipos de relación (¡importante para la sustentación!)

- **Composición** `Supplier ◆── Order`: el pedido **pertenece** al proveedor.
  Si el proveedor desaparece, sus pedidos no tienen sentido. Se modela con
  `Map<Integer, Order> orders` **dentro** de `Supplier`.
- **Agregación** `Enterprise ◇── Supplier`: la empresa **agrupa** proveedores,
  pero un proveedor puede existir sin la empresa. Se modela con
  `Map<Integer, Supplier> suppliers` dentro de `Enterprise`.
- **Dependencia/uso** `Order ──▷ ClientType`: el pedido usa la enumeración.

> **Truco para recordar:** composición = "parte de" (vida ligada);
> agregación = "tiene un" (vida independiente).

Las colecciones se manejan como **atributos** (`Map`) porque la entidad asignada
fue el proveedor, que gestiona sus pedidos.

---

## 4. Capa Enums

Un `enum` es un tipo con un conjunto **fijo y conocido** de valores.

### 4.1 `ClientType`

```java
public enum ClientType {
    BIG_ENTERPRISE,
    ENTERPRISE,
    SMALL_BUSINESS_OWNER;

    public static boolean isValid(String value) {
        if (value == null) {
            return false;
        }
        for (ClientType type : values()) {
            if (type.name().equalsIgnoreCase(value.trim())) {
                return true;
            }
        }
        return false;
    }
}
```

- Define los **tres tipos de cliente** posibles. Así un pedido nunca puede tener
  un tipo "inventado".
- `values()` devuelve un arreglo con todas las constantes del enum.
- `name()` devuelve el nombre de la constante como texto (ej. `"ENTERPRISE"`).
- `equalsIgnoreCase` compara ignorando mayúsculas/minúsculas.
- `isValid(...)` es un método **estático** (se llama con `ClientType.isValid(x)`)
  que comprueba si lo que escribió el usuario coincide con algún valor válido.

### 4.2 `RegexPattern`

```java
public enum RegexPattern {
    INTEGER("^\\d+$"),
    DECIMAL("^\\d+(\\.\\d+)?$"),
    NAME("^[a-zA-ZÁÉÍÓÚáéíóúÑñ ]+$"),
    ADDRESS("^[a-zA-Z0-9ÁÉÍÓÚáéíóúÑñ #.,°-]+$"),
    BOOLEAN("^(true|false)$"),
    TEXT("^.+$");

    private final String pattern;

    RegexPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }

    public boolean matches(String value) {
        return value != null && value.matches(this.pattern);
    }
}
```

- Es un **enum con atributo**: cada constante guarda su expresión regular.
- El **constructor** del enum es privado por naturaleza; se ejecuta una vez por
  constante asignándole su patrón.
- `matches(value)` aplica `String.matches(regex)`, que devuelve `true` si el
  texto **completo** coincide con el patrón.

**Significado de las regex:**
- `^\d+$` → uno o más dígitos (entero positivo).
- `^\d+(\.\d+)?$` → entero con parte decimal opcional.
- `^[a-zA-ZÁÉÍÓÚáéíóúÑñ ]+$` → solo letras (con tildes/ñ) y espacios.
- `^(true|false)$` → exactamente `true` o `false`.
- `^.+$` → cualquier texto con al menos un carácter.

> **Ventaja:** centralizar las regex en un enum evita repetir cadenas y facilita
> el mantenimiento.

---

## 5. Capa Domain

Son las clases que representan los objetos del mundo real. Solo tienen
**atributos, constructores, getters/setters y `toString()`**. No tienen lógica.

### 5.1 `Supplier` (entidad principal, 6 atributos)

```java
public class Supplier {
    private int idSupplier;
    private int age;
    private String name;
    private boolean isActive;
    private float comission;
    private Map<Integer, Order> orders;   // composición
```

- Los atributos son `private` → **encapsulamiento**: nadie los toca
  directamente, solo a través de getters/setters.
- `orders` es la colección de pedidos (composición). Se inicializa vacía en el
  constructor para evitar `NullPointerException`.

```java
    public Supplier(int idSupplier, int age, String name,
                    boolean isActive, float comission) {
        this.idSupplier = idSupplier;
        this.age = age;
        this.name = name;
        this.isActive = isActive;
        this.comission = comission;
        this.orders = new HashMap<>();
    }
```

- `this.x = x` distingue el **atributo** (`this.x`) del **parámetro** (`x`).

```java
    @Override
    public String toString() {
        return "Supplier [idSupplier=" + idSupplier + ", ... , orders="
            + (orders == null ? 0 : orders.size()) + "]";
    }
```

- `toString()` define cómo se "imprime" el objeto. Para la colección mostramos
  solo el **tamaño** (cuántos pedidos tiene) para no saturar la consola.
- El operador ternario `condición ? a : b` evita un `NullPointerException` si la
  colección fuera nula.

### 5.2 `Enterprise` (entidad asociada, 5 atributos)

Igual estructura. Su colección `Map<Integer, Supplier> suppliers` representa la
**agregación**: la empresa agrupa proveedores.

### 5.3 `Order` (entidad por composición, 5 atributos)

```java
public class Order {
    private ClientType clientType;   // usa el enum
    private String nameClient;
    private int idSupplier;          // referencia al proveedor dueño
    private String addressClient;
    private String order;
```

- `clientType` es del tipo enum `ClientType`, no un `String`.
- `idSupplier` indica a **qué proveedor** pertenece el pedido.

---

## 6. Capa DTO

**DTO = Data Transfer Object** (objeto para transportar datos entre capas).

```java
public class ResultDTO<T> {
    private boolean successful;
    private String message;
    private T data;
    private List<String> listMessageError;

    public ResultDTO() {
        this.listMessageError = new ArrayList<>();
        this.successful = true;
    }

    public void addError(String errorMessage) {
        this.successful = false;
        this.listMessageError.add(errorMessage);
    }
    // getters y setters...
}
```

- `<T>` es un **tipo genérico**: el mismo DTO sirve para `Supplier`, `Enterprise`
  u `Order` (`ResultDTO<Supplier>`, `ResultDTO<Order>`, etc.). Evita duplicar la
  clase tres veces.
- Campos:
  - `successful`: ¿la operación salió bien?
  - `message`: mensaje de éxito.
  - `data`: la entidad resultante (el dato útil).
  - `listMessageError`: lista de errores de validación.
- `addError(...)` es un método de conveniencia: agrega un error **y** marca el
  resultado como fallido en una sola línea.

> **Frase para exponer:** "El `ResultDTO` es como un sobre que viaja del
> controlador a la vista: dentro lleva si todo salió bien, el dato y, si falló,
> la lista de errores. Lo hicimos genérico con `<T>` para reutilizarlo en las
> tres entidades."

---

## 7. Capa Repository

Guarda y recupera objetos. Aquí usamos un `HashMap` en **memoria** (no base de
datos). **No tiene reglas de negocio.**

```java
public class SupplierRepository {
    private Map<Integer, Supplier> mapSuppliers;

    public SupplierRepository() {
        this.mapSuppliers = new HashMap<>();
    }

    public void addUpdateSupplier(Supplier supplier) {
        mapSuppliers.put(supplier.getIdSupplier(), supplier);
    }

    public Supplier findById(int idSupplier) {
        return mapSuppliers.get(idSupplier);
    }

    public List<Supplier> findAll() {
        return new ArrayList<>(mapSuppliers.values());
    }

    public boolean deleteById(int idSupplier) {
        return mapSuppliers.remove(idSupplier) != null;
    }

    public boolean existsById(int idSupplier) {
        return mapSuppliers.containsKey(idSupplier);
    }
}
```

- `Map<Integer, Supplier>`: la **clave** es el id; el **valor** es el proveedor.
- `put(clave, valor)`: inserta o reemplaza (sirve para crear y actualizar).
- `get(clave)`: devuelve el valor o `null` si no existe.
- `values()`: devuelve todos los valores; los envolvemos en un `ArrayList`.
- `remove(clave)`: borra y devuelve lo borrado; si era `null`, no existía.
- `containsKey(clave)`: ¿existe ese id?

`EnterpriseRepository` y `OrderRepository` son idénticos en estructura.
`OrderRepository` recibe el id como parámetro aparte (`addUpdateOrder(int id, Order o)`)
porque la entidad `Order` no guarda su propio id (solo 5 atributos).

---

## 8. Capa Service

Contiene la **lógica de negocio**: las reglas. Habla con el repositorio.

```java
public class SupplierService {
    private SupplierRepository repository;

    public SupplierService() {
        this.repository = new SupplierRepository();
    }

    public boolean addSupplier(Supplier supplier) {
        if (repository.existsById(supplier.getIdSupplier())) {
            return false;                     // regla: no duplicar ids
        }
        repository.addUpdateSupplier(supplier);
        return true;
    }

    public boolean updateSupplier(Supplier supplier) {
        Supplier current = repository.findById(supplier.getIdSupplier());
        if (Objects.isNull(current)) {
            return false;                     // regla: no se actualiza lo que no existe
        }
        if (Objects.isNull(supplier.getName()) || supplier.getName().isBlank()) {
            supplier.setName(current.getName());   // conserva el valor anterior
        }
        supplier.setOrders(current.getOrders());   // no se pierden los pedidos
        repository.addUpdateSupplier(supplier);
        return true;
    }
    // findById, findAll, deleteById delegan en el repositorio
}
```

**Reglas implementadas:**
1. **No crear ids duplicados** (`addSupplier` revisa con `existsById`).
2. **No actualizar registros inexistentes** (`updateSupplier` revisa `findById`).
3. **Conservar valores anteriores** si el usuario deja un campo vacío
   (`isBlank()` detecta cadenas vacías o de solo espacios).
4. **No perder la colección** (`orders`/`suppliers`) al actualizar.

`EnterpriseService` y `OrderService` siguen el mismo patrón. `OrderService`
conserva además `clientType` si llega `null`.

> **Diferencia clave Service vs Repository:** el repositorio solo guarda; el
> servicio decide **cuándo y cómo** guardar (las reglas).

---

## 9. Capa Controller

Es el **puente** entre la vista (texto) y el servicio (objetos). Su trabajo:
1. **Validar** lo que escribió el usuario (con regex y enums).
2. **Convertir** el texto a objetos del dominio (`Integer.parseInt`, etc.).
3. **Llamar** al servicio.
4. **Devolver** un `ResultDTO`.

### 9.1 `BaseController` (clase abstracta, herencia)

```java
public abstract class BaseController {

    protected boolean validateRequired(String value, String label, ResultDTO<?> result) {
        if (value == null || value.trim().isEmpty()) {
            result.addError("El campo '" + label + "' no puede estar vacío.");
            return false;
        }
        return true;
    }

    protected boolean validatePattern(RegexPattern pattern, String value,
                                      String label, ResultDTO<?> result) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }
        if (!pattern.matches(value.trim())) {
            result.addError("El campo '" + label + "' tiene un formato inválido.");
            return false;
        }
        return true;
    }
}
```

- Es **abstracta**: no se instancia sola; sirve de base.
- Los tres controladores **heredan** (`extends BaseController`) estos dos métodos
  de validación → **no repetimos código** (principio DRY).
- `ResultDTO<?>` (comodín) permite recibir un DTO de cualquier tipo, porque la
  validación solo agrega errores, no toca `data`.
- `protected`: visible para las subclases.

### 9.2 `SupplierController` (ejemplo de creación)

```java
public ResultDTO<Supplier> addSupplier(String id, String age, String name,
        String isActive, String comission) {
    ResultDTO<Supplier> result = new ResultDTO<>();

    // 1) ¿Están todos los campos?
    validateRequired(id, "id", result);
    validateRequired(age, "edad", result);
    validateRequired(name, "nombre", result);
    validateRequired(isActive, "activo", result);
    validateRequired(comission, "comisión", result);
    if (!result.isSuccessful()) {
        return result;     // si falta algo, paramos
    }

    // 2) ¿Tienen el formato correcto? (regex)
    validatePattern(RegexPattern.INTEGER, id, "id", result);
    validatePattern(RegexPattern.INTEGER, age, "edad", result);
    validatePattern(RegexPattern.NAME, name, "nombre", result);
    validatePattern(RegexPattern.BOOLEAN, isActive, "activo", result);
    validatePattern(RegexPattern.DECIMAL, comission, "comisión", result);
    if (!result.isSuccessful()) {
        return result;
    }

    // 3) Convertir texto → objeto y llamar al servicio
    Supplier supplier = new Supplier(Integer.parseInt(id), Integer.parseInt(age),
            name, Boolean.parseBoolean(isActive), Float.parseFloat(comission));

    if (!service.addSupplier(supplier)) {
        result.addError("Ya existe un proveedor con ese id.");
        return result;
    }
    result.setData(supplier);
    result.setMessage("El proveedor fue creado correctamente.");
    return result;
}
```

**Patrón de validación en dos fases:**
1. Primero se comprueba que **ningún campo esté vacío**.
2. Luego se comprueba el **formato** con regex.
3. Solo si todo pasa, se convierte y se guarda.

- `Integer.parseInt("5")` → 5 ; `Boolean.parseBoolean("true")` → true ;
  `Float.parseFloat("10.5")` → 10.5f. Por eso primero validamos con regex: para
  que esas conversiones nunca fallen.

### 9.3 `OrderController` y la validación del enum

```java
private boolean validateClientType(String clientType, ResultDTO<?> result) {
    if (!ClientType.isValid(clientType)) {
        result.addError("El 'tipo de cliente' no es válido. Use: BIG_ENTERPRISE, "
                + "ENTERPRISE o SMALL_BUSINESS_OWNER.");
        return false;
    }
    return true;
}
```

Y al crear: `ClientType.valueOf(clientType.trim().toUpperCase())` convierte el
texto al valor del enum (por eso antes validamos con `isValid`).

### 9.4 Actualización (campos opcionales)

En `update...` el id es obligatorio, pero los demás campos son **opcionales**:
si el usuario deja uno vacío, se conserva el valor actual (esa lógica final la
remata el `Service`). Solo se valida el formato de los campos que **sí** se
escribieron.

---

## 10. Capa View + Main

### 10.1 `Main`

```java
public class Main {
    public static void main(String[] args) {
        MainView mainView = new MainView();
        mainView.runApp();
    }
}
```

Solo arranca la aplicación. El `main` es el punto de entrada de todo programa Java.

### 10.2 `MainView` (menú raíz)

```java
public void runApp() {
    StringBuilder menu = new StringBuilder();
    menu.append("\n===== SISTEMA DE GESTIÓN DE PROVEEDORES =====");
    menu.append("\n[1]. Administración de proveedores");
    menu.append("\n[2]. Administración de empresas");
    menu.append("\n[3]. Administración de pedidos");
    menu.append("\n[4]. Salir");

    boolean flag = true;
    do {
        System.out.println(menu.toString());
        String strOption = sc.nextLine();
        if (!strOption.matches("^\\d$")) {     // un solo dígito
            System.out.println("Opción incorrecta...");
            continue;
        }
        int op = Integer.parseInt(strOption);
        switch (op) {
            case 1 -> supplierView.menu();
            case 2 -> enterpriseView.menu();
            case 3 -> orderView.menu();
            case 4 -> { flag = false; System.out.println("¡Hasta pronto!"); }
            default -> System.out.println("Opción incorrecta.");
        }
    } while (flag);
}
```

- `Scanner` (`sc.nextLine()`) lee lo que el usuario escribe.
- `StringBuilder` arma el texto del menú eficientemente.
- El `do-while` repite el menú hasta que se elige salir.
- `switch` con **flechas** (`case 1 -> ...`) es la sintaxis moderna de Java
  (no necesita `break`).
- `strOption.matches("^\\d$")` valida que se ingrese **un** dígito antes de
  convertir, evitando errores.

### 10.3 `SupplierView` (menú CRUD de proveedores)

Cada vista tiene su `menu()` con las 5 operaciones y métodos privados
(`create`, `listAll`, `findById`, `update`, `delete`).

```java
private void create() {
    System.out.println("* Id (numérico): ");
    String id = scanner.nextLine();
    // ... pide los demás campos ...
    ResultDTO<Supplier> result = controller.addSupplier(id, age, name, isActive, comission);
    printResult(result);   // muestra éxito o errores
}

private void listAll() {
    List<Supplier> suppliers = controller.listSuppliers();
    if (suppliers.isEmpty()) { System.out.println("No hay registros."); return; }
    suppliers.forEach(System.out::println);     // imprime cada proveedor
}
```

- La vista **nunca valida ni guarda**: solo pide datos y muestra resultados.
- `forEach(System.out::println)` recorre la lista e imprime cada elemento
  (usa una **referencia a método**, atajo de las expresiones lambda).
- En la actualización, primero se busca el registro y se muestran sus valores
  actuales entre paréntesis, para que el usuario sepa qué puede conservar.

`EnterpriseView` y `OrderView` son análogas. `OrderView` además muestra las
opciones válidas del enum con el método `clientTypeOptions()`.

---

## 11. Recorrido completo

**Ejemplo: crear un proveedor con id "5".**

1. **Vista** (`SupplierView.create`): pide los datos y llama
   `controller.addSupplier("5", "30", "Carlos", "true", "10.5")`.
2. **Controller** (`SupplierController`): valida que no estén vacíos → valida
   formato con regex → convierte texto a objeto `Supplier` → llama
   `service.addSupplier(supplier)`.
3. **Service** (`SupplierService`): pregunta al repositorio si el id ya existe
   (`existsById`). No existe → ordena guardar (`addUpdateSupplier`).
4. **Repository** (`SupplierRepository`): hace `mapSuppliers.put(5, supplier)`.
5. **Vuelta:** el servicio devuelve `true` → el controlador arma un `ResultDTO`
   exitoso con el mensaje → la vista imprime *"El proveedor fue creado correctamente."*

Si algo falla (campo vacío, formato malo o id repetido), el `ResultDTO` regresa
con `successful = false` y la **lista de errores**, que la vista imprime.

---

## 12. Pruebas unitarias

Usamos **JUnit 5**. Una prueba unitaria comprueba que un método hace lo esperado.

```java
class SupplierServiceTest {
    private SupplierService service;

    @BeforeEach
    void setUp() {
        service = new SupplierService();   // servicio limpio antes de cada test
    }

    @Test
    @DisplayName("Agregar un proveedor con id duplicado debe fallar")
    void testAddDuplicatedSupplier() {
        service.addSupplier(new Supplier(1, 30, "Carlos", true, 10.5f));
        assertFalse(service.addSupplier(new Supplier(1, 40, "Otro", false, 5f)));
        assertEquals(1, service.findAll().size());
    }
}
```

- `@BeforeEach`: se ejecuta antes de **cada** prueba (deja todo limpio).
- `@Test`: marca un método como prueba.
- `@DisplayName`: nombre legible de la prueba.
- **Aserciones:**
  - `assertTrue/assertFalse`: comprueba booleanos.
  - `assertEquals(esperado, real)`: comprueba igualdad.
  - `assertNull/assertNotNull`: comprueba nulos.

**Qué probamos (21 pruebas en total):**
- Crear correctamente y evitar duplicados.
- Buscar por id (existente e inexistente).
- Actualizar conservando valores vacíos y fallar si no existe.
- Eliminar correctamente.
- En el controlador: que la **validación regex** rechace ids no numéricos,
  nombres con números y campos vacíos.

Todas pasan: **21 tests successful**.

---

## 13. Conceptos clave

| Concepto | Dónde aparece | Explicación corta |
|----------|---------------|-------------------|
| **Encapsulamiento** | atributos `private` + getters/setters | Proteger los datos. |
| **Herencia** | `extends BaseController` | Reutilizar validaciones. |
| **Polimorfismo** | `@Override toString()` | Redefinir un método heredado. |
| **Genéricos** | `ResultDTO<T>` | Una clase para varios tipos. |
| **Colecciones** | `HashMap`, `ArrayList` | Estructuras para guardar datos. |
| **Enums** | `ClientType`, `RegexPattern` | Conjunto fijo de valores. |
| **Expresiones regulares** | `RegexPattern`, menús | Validar formato de texto. |
| **Clase abstracta** | `BaseController` | Base que no se instancia. |
| **Lambdas / referencias a método** | `forEach(System.out::println)` | Recorrer e imprimir. |
| **Operador ternario** | vistas y `toString` | `condición ? a : b`. |
| **switch con flechas** | menús | Sintaxis moderna sin `break`. |

---

## 14. Posibles preguntas

**¿Por qué usar capas y no todo en una clase?**
Para separar responsabilidades: cada capa hace una sola cosa, el código es más
ordenado, reutilizable y se puede probar por partes.

**¿Diferencia entre composición y agregación?**
Composición = la parte no vive sin el todo (Supplier–Order). Agregación = la
parte puede vivir sin el todo (Enterprise–Supplier).

**¿Por qué el `ResultDTO` es genérico?**
Para no escribir tres DTOs casi iguales; con `<T>` el mismo sirve para las tres
entidades.

**¿Dónde están las validaciones y por qué ahí?**
En el **controlador**, porque es el primer punto que recibe el texto del usuario;
así los datos llegan limpios al servicio y al repositorio.

**¿Por qué el repositorio usa `Map`?**
Porque permite buscar, insertar y borrar por id de forma directa y eficiente.

**¿Qué pasa si el usuario deja un campo vacío al actualizar?**
Se conserva el valor anterior (lo resuelve el servicio comprobando `isBlank()`).

**¿Por qué validar con regex antes de `parseInt`?**
Porque `Integer.parseInt("abc")` lanzaría una excepción; la regex garantiza que
el texto sea numérico antes de convertirlo.

**¿Cómo se relacionan las tres entidades?**
`Enterprise` agrupa `Supplier` (agregación), `Supplier` contiene `Order`
(composición), y `Order` usa el enum `ClientType`.

---

> **Cierre para exponer:**
> "En resumen, aplicamos arquitectura en capas con responsabilidades bien
> separadas, validación robusta con expresiones regulares y enums, un DTO
> genérico para comunicar resultados, y pruebas unitarias que verifican la
> lógica de negocio. El modelo respeta la composición (proveedor–pedido) y la
> agregación (empresa–proveedor) pedidas en la guía."
