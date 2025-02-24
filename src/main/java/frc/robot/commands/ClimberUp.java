package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimberMotor;

public class ClimberUp extends Command {

  int timer;
  public static double position;
  public static boolean raising;
  private final ClimberMotor m_climber;
  // public static ClimberMotor climber;

  // Called when the command is initially scheduled.

  public ClimberUp(ClimberMotor climber) {
    m_climber = climber;
    addRequirements(m_climber);
  }

  @Override
  public void initialize() {

    // in init function, set slot 0 gains
    // climber = new ClimberMotor();
    m_climber.raiseClimber();
    // if (ClimberMotor.getClimberPosition() == 0) {
    //   raising = true;
    // }
    timer = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    // ClimberMotor.climber.set(0.2);
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
    // if (Robot.climberPos > 90) {
    //   return true;
    // }
    if (timer > 500) {
      return true;
    }

    return false;
  }
}
