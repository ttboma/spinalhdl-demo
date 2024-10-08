package hdlbits.circuits

import spinal.core._
import spinal.core.sim._
import hdlbits.Config

object VerilogHdlBitsDff8p extends App {
  Config
    .spinal("Dff8p.v") // set the output file name
    .generateVerilog(HdlBitsDff8p())
}

// https://hdlbits.01xz.net/wiki/Dff8p
case class HdlBitsDff8p() extends Component {
  val io = new Bundle {
    val clk =
      in Bool () // NOTE: How to write A dual-edge triggered flip-flop in SpinalHDL? For example: <https://hdlbits.01xz.net/wiki/Dualedge>
    val reset = in Bool () // active high synchronous reset
    val d = in Bits (8 bits)
    val q = out Bits (8 bits)
  }

  // Configure the clock domain
  val myClockDomain = ClockDomain(
    clock = io.clk,
    reset = io.reset,
    config = ClockDomainConfig(
      clockEdge = FALLING,
      resetKind = SYNC,
      resetActiveLevel = HIGH
    )
  )

  // Define an Area which use myClockDomain
  val myArea = new ClockingArea(myClockDomain) {
    val qNext = Reg(Bits(8 bits)) init (B"8'h34")
    qNext := io.d
    io.q := qNext
  }
}

object HdlBitsDff8p {
  def apply(): HdlBitsDff8p = {
    val rtl = new HdlBitsDff8p()
    setNames(rtl)
    rtl
  }

  private def setNames(mod: HdlBitsDff8p) {
    mod.setDefinitionName("top_module")
    mod.io.elements.foreach { case (name, signal) =>
      signal.setName(name)
    }
  }
}
