object Main {
  def main(args: Array[String]): Unit = {
    // Define a sample container
    val container = List(1, 2, 3, 4, 5)

    // Map: Transform each element in the container by adding 1
    val mappedContainer = container.map(_ + 1)
    println("Mapped container: " + mappedContainer) // Output: Mapped container: List(2, 3, 4, 5, 6)

    // FlatMap: Double each element and flatten the result
    val flatMappedContainer = container.flatMap(x => List(x, x))
    println("FlatMapped container: " + flatMappedContainer) // Output: FlatMapped container: List(1, 1, 2, 2, 3, 3, 4, 4, 5, 5)

    // Reduce: Sum all elements in the container
    val sumResult = container.reduce(_ + _)
    println("Sum of elements: " + sumResult) // Output: Sum of elements: 15

    // Scala's sum function: Sum all elements in the container
    val scalaSum = container.sum
    println("Scala's sum: " + scalaSum) // Output: Scala's sum: 15

    // Multiplication using reduce
    val product = container.reduce(_ * _)
    println("Product of elements: " + product) // Output: Product of elements: 120
  }




}