package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Intake;

public class IntakeUpToPos extends Command {

  int timer;
  public static double finalPosition;
  public static boolean raising;
  private final Intake m_intake;
  // public static ClimberMotor climber;

  // Called when the command is initially scheduled.

  public IntakeUpToPos(Intake intake, double finalAngle) {
    m_intake = intake;
    finalPosition = finalAngle;
    addRequirements(m_intake);
  }

  @Override
  public void initialize() {

    m_intake.raiseIntake();
    timer = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    timer++;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_intake.stopIntakeArm();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    if (Robot.intakePos > finalPosition) {
      return true;
    }

    // if (timer > 1000) {
    //   return true;
    // }

    return false;
  }
}
