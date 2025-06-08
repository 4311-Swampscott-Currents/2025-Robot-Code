// package frc.robot.commands;

// import edu.wpi.first.wpilibj2.command.Command;
// import frc.robot.subsystems.DeAlgaeArm;

// public class DeAlgae extends Command {

//   int timer;
//   public static double position;
//   public static boolean raising;
//   private final DeAlgaeArm deAlgaeArm;
//   private final double finalPos;

//   // Called when the command is initially scheduled.

//   public DeAlgae(DeAlgaeArm deAlgaeArm_m, double maxPos) {
//     deAlgaeArm = deAlgaeArm_m;
//     finalPos = maxPos;
//     addRequirements(deAlgaeArm);
//   }

//   @Override
//   public void initialize() {

//     if (deAlgaeArm.getDeAlgaeUp()) {
//       deAlgaeArm.deAlgae_mDown();
//       raising = false;
//       deAlgaeArm.setDeAlgaeUp(false);
//     } else {
//       deAlgaeArm.deAlgae_mUp();
//       raising = true;
//       deAlgaeArm.setDeAlgaeUp(true);
//     }

//     // if m_intake.

//     // timer = 0;
//   }

//   // Called every time the scheduler runs while the command is scheduled.
//   @Override
//   public void execute() {

//     timer++;
//   }

//   // Called once the command ends or is interrupted.
//   @Override
//   public void end(boolean interrupted) {
//     deAlgaeArm.stopDeAlgae_m();
//   }

//   // Returns true when the command should end.
//   @Override
//   public boolean isFinished() {
//     if (raising && deAlgaeArm.getDeAlgaePos() > finalPos) {
//       return true;
//     } else if (!raising && deAlgaeArm.getDeAlgaePos() <= 0) {
//       return true;
//     }

//     return false;
//   }
// }
