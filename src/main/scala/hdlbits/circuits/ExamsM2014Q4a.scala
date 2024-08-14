package hdlbits.circuits

import spinal.core._
import spinal.core.sim._
import hdlbits.Config

object VerilogHdlBitsExamsM2014Q4a extends App {
  Config
    .spinal("ExamsM2014Q4a.v") // set the output file name
    .generateVerilog(HdlBitsExamsM2014Q4a().setDefinitionName("top_module"))
}

// https://hdlbits.01xz.net/wiki/Exams/m2014_q4a
case class HdlBitsExamsM2014Q4a() extends Component {
  val io = new Bundle {
    val d, ena = in Bool ()
    val q = out Bool ()
  }

  // NOTE: SpinalHDL will check that no combinatorial signal will infer a latch in synthesis. In other words, that no combinatorial are partialy assigned.
  //       So when we need to infer a latch, we need to use a blackbox, probably.

  // Instantiate the D Latch blackbox
  val dLatch = new HdlBitsDLatch()

  dLatch.io.D := io.d
  dLatch.io.enable := io.ena
  io.q := dLatch.io.Q

  // Remove io_ prefix
  noIoPrefix()
}

class HdlBitsDLatch extends BlackBox {
  val io = new Bundle {
    val D = in Bool ()
    val enable = in Bool ()
    val Q = out Bool ()
  }

  // Remove io_ prefix
  noIoPrefix()

  addRTLPath("ExamsM2014Q4a.v")
}
