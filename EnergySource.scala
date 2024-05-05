trait EnergySource {
  def typeOfSource: String
  def isRenewable: Boolean
}

case object Gas extends EnergySource {
  def typeOfSource: String = "Fossil Fuel"
  def isRenewable: Boolean = false
}

case object Water extends EnergySource {
  def typeOfSource: String = "Hydro"
  def isRenewable: Boolean = true
}

case object Sunlight extends EnergySource {
  def typeOfSource: String = "Solar"
  def isRenewable: Boolean = true
}
