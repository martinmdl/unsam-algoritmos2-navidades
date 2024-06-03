## Parcial *Navidades*
[**Enunciado**](https://docs.google.com/document/d/1hswq2ZZCRL-SWZNavJ3oW93JwBYxcBUfBbgTsgipPvY/edit)

## Diagrama de clases

<img src="" height="500" />

## Ideas de diseño

### Punto 1
- Se me hizo clara la elección del patrón Strategy ya que permite definir diferente comportamientos de una clase frente a un mismo mensaje sin usar condicionales.

### Punto 2
- Evalué la posibilidad de aplicar el patrón Observer en la parte dinámica del condicional, pero no existía una relación de un-a-muchos, sino de uno-a-uno. Fue por eso que descarté esa opción y opte por el Template Method, que además de permitir evitar la repetición de código debido a una parte fija del algoritmo, es mucho mas simple. 

### Punto 3.1
- En esta instancia entedí de la consigna que las Personas tienen mas de una preferencia, entonces el método "encontrarRegaloAdecuado" devuelve el primer Regalo que "esAdecuado" para cualquier preferencia.

### Punto 3.3.2
- Por el enunciado parece que el flete tuviera su propia API para obtener datos especificos. Por comodidad yo convine que el informe se consetruyera con String. Esto me permitió resolver el hecho de que cada regalo tuviera un código especifico, usando "lateinit" y sobrescribiendo el dato en cada clase.

### Punto 3.3.3
- Agregué un método que limpia la colección de Preferencias antes de agregarle "Interesada" porque la consigna hace ver nuevamente la Preferencia es única. Al no quedar claro si es única o múltiple, elijo dejar el set con un solo objeto: mutableSetOf<Preferencia>(Interesada(5000.0)) 
