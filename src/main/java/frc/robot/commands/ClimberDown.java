package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimberMotor;

public class ClimberDown extends Command {
  // Code runs, but motor does not turn...
  int timer;
  public static double rotationCounter;
  private final ClimberMotor m_climber;
  // public static ClimberMotor climber;

  // Called when the command is initially scheduled.

  public ClimberDown(ClimberMotor climber) {
    m_climber = climber;

    addRequirements(m_climber);
  }

  @Override
  public void initialize() {

    rotationCounter = 0;
    m_climber.lowerClimber();
    timer = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    // rotationCounter += RobotContainer.climberEncoder.get();

    System.out.println("spinning " + timer);

    timer++;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    m_climber.stopClimber();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // if (Robot.climberPos < 0) {
    //   return true;
    // }
    if (timer > 500) {
      return true;
    }

    return false;
  }
}
