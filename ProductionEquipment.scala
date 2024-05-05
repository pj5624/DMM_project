trait ProductionEquipment {
  var efficiencyRate: Double
  val capacityFactor: Double
  def inputSources: Set[EnergySource]
  def outputForms: Set[EnergyForm]
  def produceEnergy(inputEnergy: Double): Double
  def equipmentType: String
  def adjustEfficiency(): Unit
}

case class GeneratorSet(inputSources: Set[EnergySource], outputForms: Set[EnergyForm], var efficiencyRate: Double, val capacityFactor: Double) extends ProductionEquipment {
  def produceEnergy(inputEnergy: Double): Double = inputEnergy * efficiencyRate * capacityFactor
  def equipmentType: String = "Generator Set"
  def adjustEfficiency(): Unit = { efficiencyRate *= 0.99 }
}

case class SolarPanel(inputSources: Set[EnergySource], outputForms: Set[EnergyForm], var efficiencyRate: Double, val capacityFactor: Double) extends ProductionEquipment {
  def produceEnergy(inputEnergy: Double): Double = inputEnergy * efficiencyRate * capacityFactor
  def equipmentType: String = "Photovoltaic Solar Panel"
  def adjustEfficiency(): Unit = { efficiencyRate *= 0.995 }
}
