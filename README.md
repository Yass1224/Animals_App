# 🐾 Animals App

Aplicación Android desarrollada con **Jetpack Compose** que consume la [Animals API](https://animals.juanfrausto.com/api/) para mostrar información sobre animales y sus hábitats naturales.

## 📱 Pantallas

| Pantalla | Descripción |
|---|---|
| Lista de Animales | Muestra todos los animales con imagen circular y nombre |
| Lista de Ambientes | Muestra todos los hábitats con imagen y nombre |
| Detalle de Animal | Nombre, imagen, descripción, hechos interesantes y galería (carrusel) |
| Detalle de Ambiente | Nombre, imagen, descripción y lista horizontal de animales del ambiente |

## 🛠️ Tecnologías

- **Jetpack Compose** — UI declarativa
- **Navigation Compose** — Navegación entre pantallas
- **Retrofit + Gson** — Consumo de API REST
- **Coil** — Carga asíncrona de imágenes
- **ViewModel + StateFlow** — Manejo de estado reactivo
- **Material 3** — Componentes y theming

## 🚀 Cómo correr la aplicación

### Requisitos previos
- Android Studio Hedgehog (2023.1.1) o superior
- JDK 11+
- Android SDK 24+
- Conexión a internet (para consumir la API)

### Pasos

1. Clona el repositorio:
   ```bash
   git clone https://github.com/TU_USUARIO/AnimalsApp.git
   cd AnimalsApp
   ```

2. Abre el proyecto en **Android Studio**

3. Espera a que Gradle sincronice las dependencias

4. Conecta un dispositivo físico o inicia un emulador (API 24+)

5. Presiona **Run** ▶️ o usa el atajo `Shift + F10`

## 🌐 API

- **Base URL:** `https://animals.juanfrausto.com/api/`
- `GET /animals` — Lista de todos los animales
- `GET /animals/{id}` — Detalle de un animal
- `GET /animals?enviromentId={id}` — Animales por ambiente
- `GET /enviroment` — Lista de ambientes
- `GET /enviroment/{id}` — Detalle de un ambiente

## 🎨 Diseño

La app usa un tema oscuro con paleta verde oscuro y amarillo neón, inspirada en el diseño propuesto del examen.

- Fondo: `#1A2D1A`
- Tarjetas: `#2C4A2C`
- Acento: `#D4E84A`
