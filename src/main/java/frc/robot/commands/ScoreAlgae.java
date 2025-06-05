package frc.robot.commands;

// import com.google.flatbuffers.Constants;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeArm;

public class ScoreAlgae extends Command {

  int timer;

  private final IntakeArm m_intake;

  // Called when the command is initially scheduled.
  public ScoreAlgae(IntakeArm intake) {
    m_intake = intake;
    addRequirements(m_intake);
  }

  @Override
  public void initialize() {

    // m_intake.intakeWheelsSpinOut();

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
    m_intake.stopWheels();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (timer > 500) {
      return true;
    }

    return false;
  }
}
