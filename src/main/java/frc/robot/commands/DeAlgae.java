// package frc.robot.commands;

// import edu.wpi.first.wpilibj2.command.Command;
// import frc.robot.subsystems.Intake;

// public class DeAlgae extends Command {

//   int timer;
//   public static double position;
//   public static boolean raising;
//   private final Intake m_intake;
//   private final double finalPos;

//   // Called when the command is initially scheduled.

//   public DeAlgae(Intake intake, double maxPos) {
//     m_intake = intake;
//     finalPos = maxPos;
//     addRequirements(m_intake);
//   }

//   @Override
//   public void initialize() {

//     if (m_intake.getDeAlgaeUp()) {
//       m_intake.deAlgae_mDown();
//       raising = false;
//       m_intake.setDeAlgaeUp(false);
//     } else {
//       m_intake.deAlgae_mUp();
//       raising = true;
//       m_intake.setDeAlgaeUp(true);
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
//     m_intake.stopDeAlgae_m();
//   }

//   // Returns true when the command should end.
//   @Override
//   public boolean isFinished() {
//     if (raising && m_intake.getDeAlgaePos() > finalPos) {
//       return true;
//     } else if (!raising && m_intake.getDeAlgaePos() <= 0) {
//       return true;
//     }

//     return false;
//   }
// }
