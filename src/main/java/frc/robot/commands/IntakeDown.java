package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.drive.Intake;

public class IntakeDown extends Command {
  // Code runs, but motor does not turn...
  int timer;
  public static double position;
  public static boolean raising;
  // public static ClimberMotor climber;

  // Called when the command is initially scheduled.

  @Override
  public void initialize() {

    Intake.configureIntake();
    timer = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    Intake.lowerIntake();

    timer++;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Intake.stopIntake();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    if (Robot.intakePos < 0) {
      return true;
    }
    if (timer > 500) {
      return true;
    }

    return false;
  }
}
