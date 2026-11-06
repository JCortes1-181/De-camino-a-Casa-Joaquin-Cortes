# [De Camino a Casa]

<div align="center">
 
  <img src=" <img width="350" height="350" alt="a-cartoon-dog-with-a-collar-on-a-blue-background-png" src="https://github.com/user-attachments/assets/39917582-2f7f-4739-b90a-55d724950dca" />" alt="Logo" width="120" height="120">

  <p><strong>Breve descripción de una o dos líneas sobre lo que hace tu aplicación Android.</strong></p>

  <!-- Badges opcionales (estado, versión, licencia) -->
  <img src="https://shields.io" alt="Platform">
  <img src="https://shields.io" alt="Language">
  <img src="https://shields.io" alt="License">
</div>

---

## Capturas de pantalla

| Pantalla Principal | Detalle | Configuración |
| :---: | :---: | :---: |
| <img src="art/screenshot1.png" width="220" /> | <img src="art/screenshot2.png" width="220" /> | <img src="art/screenshot3.png" width="220" /> |

---

## Características principales

* **UI Moderna:** Desarrollada 100% con [Jetpack Compose](https://android.com) y Material Design 3.
* **Arquitectura:** Patrón MVVM / MVI limpio y modular.
* **Inyección de Dependencias:** [Hilt](https://dagger.dev) para la gestión de dependencias.
* **Asincronismo:** Uso de Corrutinas y `StateFlow`.
* **Persistencia local:** Room Database / DataStore.
* **Red:** Retrofit / Ktor Client con OkHttp.

---

## Stack Tecnológico y Herramientas

* **Lenguaje:** [Kotlin](https://kotlinlang.org)
* **UI:** Jetpack Compose, ConstraintLayout, Accompanist
* **Arquitectura:** ViewModel, StateFlow, Navigation Compose
* **DI:** Hilt
* **Pruebas (Testing):** JUnit, MockK, Turbine, Espresso, Compose Testing
* **Calidad de código:** Detekt, Ktlint

---

## Estructura del Proyecto

```text
app/
├── data/
│   ├── local/       # Base de datos Room, SharedPreferences
│   ├── remote/      # Endpoints de API y DTOs
│   └── repository/  # Implementaciones de repositorios
├── domain/
│   ├── model/       # Modelos de negocio
│   ├── repository/  # Interfaces de repositorios
│   └── usecase/     # Casos de uso / Lógica de negocio
└── ui/
    ├── components/  # Componentes reutilizables de Compose
    ├── theme/       # Colores, tipografías y formas
    └── screens/     # Pantallas y ViewModels asociados
```

---

## Cómo compilar y ejecutar

1. **Clona el repositorio:**
   ```bash
   git clone [https://github.com](https://github.com/JCortes1-181/De-camino-a-Casa-Joaquin-Cortes/tree/master)
   ```
2. **Abre el proyecto** en [Android Studio](https://android.com) (Electric Eel o superior recomendado).
3. Sincroniza el proyecto con los archivos Gradle (`File -> Sync Project with Gradle Files`).
4. Ejecuta la aplicación en un emulador o dispositivo físico con Android 8.0 (API 26) o superior.

---

## Pruebas

Para ejecutar las **pruebas unitarias**:
```bash
./gradlew test
```

Para ejecutar las **pruebas de instrumentación (UI)**:
```bash
./gradlew connectedAndroidTest
```

---

## Licencia

Este proyecto está bajo la Licencia **MIT**. Consulta el archivo [LICENSE](LICENSE) para más detalles.

---

## Autor

* **Joaquín Cortes** - [@JCortes1-181](https://github.com)



