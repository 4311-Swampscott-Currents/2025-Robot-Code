package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class IntakeUp extends Command {
  // Code runs, but motor does not turn...
  int timer;
  public static double position;
  public static boolean raising;
  private final Intake m_intake;
  // public static ClimberMotor climber;

  // Called when the command is initially scheduled.

  public IntakeUp(Intake intake) {
    m_intake = intake;
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
    m_intake.stopIntake();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    // if (Robot.intakePos > 90) {
    //   return true;
    // }
    if (timer > 500) {
      return true;
    }

    return false;
  }
}
