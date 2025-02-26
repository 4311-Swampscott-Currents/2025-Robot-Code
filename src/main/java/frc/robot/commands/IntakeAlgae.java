package frc.robot.commands;

// import com.google.flatbuffers.Constants;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;

public class IntakeAlgae extends Command {

  int timer;
  boolean isPartiallyIntake;

  private final Intake m_intake;

  // Called when the command is initially scheduled.
  public IntakeAlgae(Intake intake) {
    m_intake = intake;
    isPartiallyIntake = false;
    addRequirements(m_intake);
  }

  @Override
  public void initialize() {

    // m_intake.armExitBrake();
    m_intake.intakeWheelsSpinIn();

    timer = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    // if (RobotContainer.intakeEncoder.get() > Constants.intakeInitalMaxAngle) {
    //   isPartiallyIntake = true;
    // }
    // if (isPartiallyIntake) {
    //   m_intake.armEnterBrake();

    //   m_intake.raiseIntake();
    // }

    timer++;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_intake.stopIntakeArm();
    m_intake.armEnterBrake();
    m_intake.stopWheels();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    if (m_intake.getIntakeWheelsVolt() > Constants.maxIntakeVolt) {

      return true;
    }
    // if (Robot.intakePos >= Constants.intakeFinalMaxAngle) {
    //   return true;
    // }
    if (timer > 500) {
      return true;
    }

    return false;
  }
}
