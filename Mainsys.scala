import scala.io.Source
import java.io.{File, PrintWriter, FileWriter}
import scala.util.{Try, Success, Failure}

import scala.util.Random

case class SensorData(time: String, solar: Double, wind: Double, hydro: Double)

trait SensorInterface {
  def readData(): SensorData
  def controlSettings(sensorData: SensorData): Unit
}


object MockSensorInterface extends SensorInterface {
  val fileName = "src/energy_data.csv"
  val outfileName = "src/output.csv"
  val solarThreshold = 100.0
  val windThreshold = 120.0
  val hydroThreshold = 100.0

  override def readData(): SensorData = {
    val data = SensorData(java.time.LocalTime.now.toString, Random.nextDouble() * 100, Random.nextDouble() * 200, Random.nextDouble() * 300)
    storeData(data)
    data
  }

  private def storeData(data: SensorData): Unit = {
    val writer = new PrintWriter(new FileWriter(new File(outfileName), true)) // Append mode
    try {
      writer.append(s"${data.time},${data.solar},${data.wind},${data.hydro}\n")
    } finally {
      writer.close()
    }
  }

  override def controlSettings(sensorData: SensorData): Unit = {
    // Existing control logic
    if (sensorData.solar < 50) println("Adjusting solar panel angle for optimal exposure.")
    if (sensorData.wind > 180) println("Reducing turbine speed to prevent damage.")
    if (sensorData.hydro < 100) println("Increasing water flow for hydro turbines.")
  }

  def detectIssues(sensorData: SensorData): Unit = {
    if (sensorData.solar < solarThreshold) alert("Low solar energy output detected.")
    if (sensorData.wind > windThreshold) alert("Wind speed too high, potential turbine damage.")
    if (sensorData.hydro < hydroThreshold) alert("Low hydro energy output detected.")
  }

  private def alert(message: String): Unit = {
    println(s"ALERT: $message")
  }
}



object DataStore {
  val outfileName = "src/output.csv"
  val fileName = "src/energy_data.csv"
  def readData(): Try[List[Array[String]]] = Try {
    val bufferedSource = Source.fromFile(fileName)
    try {
      bufferedSource.getLines().drop(1).map(_.split(",")).toList
    } finally {
      bufferedSource.close()
    }
  }

  def writeData(data: List[Array[String]]): Try[Unit] = Try {
    val writer = new PrintWriter(new File(outfileName))
    try {
      data.foreach(line => writer.write(line.mkString(",") + "\n"))
    } finally {
      writer.close()
    }
  }
}


object DataAnalysis {
  def mean(data: Seq[Double]): Double = if (data.isEmpty) 0.0 else data.sum / data.length

  def median(data: Seq[Double]): Double = {
    if (data.isEmpty) 0.0 else {
      val sorted = data.sorted
      val length = sorted.length
      if (length % 2 == 0) (sorted(length / 2 - 1) + sorted(length / 2)) / 2.0 else sorted(length / 2)
    }
  }

  def mode(data: Seq[Double]): Double = {
    if (data.isEmpty) 0.0 else {
      data.groupBy(identity).maxBy(_._2.size)._1
    }
  }

  def range(data: Seq[Double]): Double = if (data.isEmpty) 0.0 else data.max - data.min

  def midrange(data: Seq[Double]): Double = if (data.isEmpty) 0.0 else (data.max + data.min) / 2
}


object EnergyPlantMonitor {
  def main(args: Array[String]): Unit = {
    println("Starting Energy Monitoring System...")
    val data = MockSensorInterface.readData()
    MockSensorInterface.detectIssues(data)


    println("Data Loaded Successfully")
    println(s"Collected data: Time: ${data.time}\nSolar: ${data.solar}, Wind: ${data.wind}, Hydro: ${data.hydro}")
    DataStore.readData() match {
      case Success(data) =>
        val numericalData = data.map(_.tail.map(_.toDouble))

        println(s"Mean: ${DataAnalysis.mean(numericalData.flatten)}")
        println(s"Median: ${DataAnalysis.median(numericalData.flatten)}")
        println(s"Mode: ${DataAnalysis.mode(numericalData.flatten)}")
        println(s"Range: ${DataAnalysis.range(numericalData.flatten)}")
        println(s"Midrange: ${DataAnalysis.midrange(numericalData.flatten)}")


        numericalData.map(_ => MockSensorInterface.readData()).foreach(MockSensorInterface.controlSettings)

      case Failure(ex) =>
        println(s"Failed to read data: ${ex.getMessage}")

    }
  }

}


