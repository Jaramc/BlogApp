## Descripción del proyecto
El proyecto está raelizado en [Spring](), mediante [Java]() y [MySQL](), la idea principal es hacer un sistema de Blogs, donde los usuarios pueden registrarse, publicar posts y comnetar en los mismos.

## Estructura
El proyecto se encuentra estructurado de la siguiente manera:

```
                                    └── 📁Config
                                        └── SwaggerConfig.class
                                    └── 📁Controllers
                                        └── AuthController.class
                                        └── CommentController.class
                                        └── PostsController.class
                                        └── UsersController.class
                                    └── DemoApplication.class
                                    └── 📁Jwt
                                        └── JwtAuthenticationFilter.class
                                    └── 📁Models
                                        └── Comment$CommentBuilder.class
                                        └── Comment.class
                                        └── Post$PostBuilder.class
                                        └── Post.class
                                        └── User$UserBuilder.class
                                        └── User.class
                                    └── 📁Properties
                                        └── ApplicationProperties.class
                                        └── SecurityProperty.class
                                    └── 📁Repositories
                                        └── CommentRepository.class
                                        └── PostRepository.class
                                        └── UserRepository.class
                                    └── 📁Requests
                                        └── CommentRequest.class
                                        └── LoginRequest.class
                                        └── PostRequest.class
                                        └── RegisterRequest.class
                                    └── 📁Responses
                                        └── AuthResponse.class
                                        └── CommentResponse.class
                                        └── PostResponse.class
                                        └── UserResponse.class
                                    └── 📁Services
                                        └── AuthService.class
                                        └── CommentService.class
                                        └── JwtService.class
                                        └── PostService.class
                                        └── UserService.class
                                    └── 📁Utils
                                        └── Role.class
```

## Controllers
Los controladores son los que se encargan de recibir las peticiones HTTP, para ello definimos diferentes rutas, se dividen en 4 controladores, `Auth`, `User`, `Post`, `Comment`. Estos controladores se encargan de manejar el uso de la petición.
Si la petición es correcta, se llama al servicio, que se encarga de manipular la base de datos y en general. 

### Rutas 
>[!TIP]
>Recomiendo usar un cliente HTTP como [Postman]() para realizar las peticiones, sin embargo [Swagger]() ya viene incluido en la app, en caso de no poderse.

>[!NOTE]
>Las siguientes rutas son para el controlador `AuthController`

>[!IMPORTANT]
>Todas las rutas empiezan con `/api/v1/**` a excepción de las de Auth, ya que son rutas públicas, todas las demás rutas están protejidas por JWT.

**AuthController Register**
```
http://localhost:{port}/auth/register
```
Ruta para registrar un usuario, recibe como parametros:
```
    String username;
    String password;
    String email;
    String firstName;
    String lastName;
```
returns `token`
El Token JWT es el que va a validar si la sesión iniciada es correcta y el usuario existe, si todo sale correctamente, se le hace un `hash` a la password del usuario y se inserta en la base de datos.

**AuthController Login**
```
http://localhost:{port}/auth/login
```
Ruta para iniciar sesión, recibe como parametros:
```
    String username;
    String password;
```
returns `token` Se genera el token que se debe pasar como header en la petición, de esta manera se puede validar que el usuario se encuentra correctamente con la sesión iniciada y se le da acceso a las demás rutas. 
>[!NOTE]
>Las siguientes rutas son para el controlador `PostController`

**PostController**
**Crear Post**
```
http://localhost:{port}/api/v1/posts/create
```
Recibe como parametros:
```
    private String title;
    private String content;
```
returns `Post Model Object`

**Obtener Todos**
```
http://localhost:{port}/api/v1/posts
```
returns `Post Model Collection`

**Actualizar Post**
```
http://localhost:{port}/api/v1/posts/update/{postId}
```
Recibe como parametor el postId y un request body:
```
    private String title;
    private String content;
```

**Eliminar Post**
```
http://localhost:{port}/api/v1/posts/dekete/{postId}
```
Recibe como parametro el postId,
returns `null`

>[!NOTE]
>Las siguientes rutas son para el controlador `Commment Controller`

**Comment Controller**

**Crear Comentario**
```
http://localhost:{port}/api/v1/comment/create
```
Recibe como parametros:
```
    private String comment;
```
returns `Comment Model Object`

**Obtener Todos**
Recibe como parametro postId, para especificar que comentarios de que Post se buscan obtener.
```
http://localhost:{port}/api/v1/comments/{postId}
```
returns `Comments Model Collection` de un Post en especifico.

**Actualizar Comentario**
```
http://localhost:{port}/api/v1/comments/update/{commentId}
```
Recibe como parametor el commentId y un request body:
```
    private String comment;
```

**Eliminar Post**
Recibe como parametro el commentId, para asi eliminar el comentario en especifico.
```
http://localhost:{port}/api/v1/comments/dekete/{commentId}
```
returns `null`

## Servicios
Como antes se ha explicado, cada controlador tiene un servicio que se encarga de manipular la base de datos, utilizando un modelo de inyección de dependencias, se inyecta cada clase de tipo servicio en su respectivo controlador.
Tenemos entonces que el servicio de `JWT` se utiliza para la autenticación del usuario, más no se usa en un controlador como tal.

## Modelos
Los modelos son `User`, `Post`, `Comment`, los cuales mantienen una relación:
```
User 1 -> N Post
User 1 -> N Comment
Post 1 -> N Comment
Comment 1 - 1 Post
```
Relacionando los modelos de esta manera se logra obtener fácilmente el autor del post, el autor del comentario, los comentarios del post, los post de un usuario, etc.

## Setup

Para inicializar el poryecto localmente es altamente recomendado utilizar un servicio local de base de datos, personalmente recomiendo utilizar [Docker](), el cual facilita muchho el tema de montar la aplicación, si no es el caso, se puede usar un servicio en la nube. O 
metodos más tradicionales como [XAMPP]()

>[!DANGER]
>En el archivo `application.properties` es importante modificar las credenciales de base de datos antes de iniciar el proyecto.

## Run
Una vez seteadas las credenciales de base de datos, solamente hay que darle a correr al proyecto una vez se hayan resuelto todas las dependencias necesarias mendiante `Gradle`.
