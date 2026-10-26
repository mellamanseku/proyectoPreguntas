# Pruebas de funcionamiento

1. Instalación nueva: iniciar y comprobar que el menú tiene exactamente tres botones y que el listado empieza con cinco preguntas.
2. Normal: responder FALSE a la pregunta del unicornio. Deben aparecer error y botón FALSE rojo, TRUE verde. Repetir TRUE: correcto verde. Cada respuesta debe sumar un intento.
3. NEXT: se borra el mensaje y los colores. Desde la quinta pregunta, volver a la primera.
4. PREV: desde la primera, ir a la última. RANDOM: mostrar una pregunta válida (puede ser la misma, porque es aleatorio).
5. Estadísticas: comprobar aciertos, fallos, clics y porcentajes. Sin respuestas, ambos porcentajes deben ser cero. Con tres aciertos y un fallo, 75 % y 25 %.
6. Cerrar el proceso y volver a abrirlo: deben mantenerse estadísticas y preguntas añadidas.
7. Examen: anotar estadísticas antes de entrar. NEXT comienza deshabilitado, TRUE/FALSE se deshabilitan tras responder y NEXT se activa. No aparecen PREV ni RANDOM.
8. Examen perfecto: responder TRUE, TRUE, TRUE, FALSE, TRUE a las cinco preguntas iniciales. Terminar: diálogo con 5 de 5, trofeo y texto motivador.
9. Examen aprobado: con las cinco iniciales, lograr tres aciertos. El diálogo muestra la imagen de aprobado. Con dos aciertos, el de suspenso. Las pruebas unitarias comprueban también el límite exacto del 50 %.
10. Volver de un examen: las estadísticas generales deben conservar los valores que tenían antes de pulsar Examen, salvo clics posteriores en otras pantallas.
11. Añadir: guardar una pregunta y comprobar el Toast y la lista. Abrir el juego, localizarla y reiniciar la app para comprobar la persistencia. Las preguntas anteriores siguen existiendo.
12. Validación: intentar guardar un enunciado vacío, uno con |, uno con saltos de línea y uno mayor de 300 caracteres. Deben rechazarse sin escribir en el fichero.
13. Simple: no aparece ninguna imagen en la pantalla de juego. La barra avanza con NEXT y vuelve al inicio al completar una vuelta.
14. Rotación: el índice, la selección y los aciertos de examen se conservan con rememberSaveable. El campo de inserción utiliza rememberTextFieldState.
15. Pantalla pequeña: comprobar desplazamiento y acceso a botones con el teclado abierto.
