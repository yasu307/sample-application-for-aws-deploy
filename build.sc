import mill._
import $ivy.`com.lihaoyi::mill-contrib-playlib:`,  mill.playlib._

object sampleapplicationforecrdeployment extends PlayModule with SingleModule {

  def scalaVersion = "2.13.3"
  def playVersion = "2.7.5"
  def twirlVersion = "1.5.1"

  object test extends PlayTests
}
