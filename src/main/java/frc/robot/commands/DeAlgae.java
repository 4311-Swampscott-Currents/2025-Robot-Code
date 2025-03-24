package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class DeAlgae extends Command {

  int timer;
  public static double position;
  public static boolean raising;
  private final Intake m_intake;

  // Called when the command is initially scheduled.

  public DeAlgae(Intake intake) {
    m_intake = intake;
    addRequirements(m_intake);
  }

  @Override
  public void initialize() {

    // if m_intake.

    // timer = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    timer++;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    return false;
  }
}
