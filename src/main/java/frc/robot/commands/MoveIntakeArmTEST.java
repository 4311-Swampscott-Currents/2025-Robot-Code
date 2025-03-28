package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.Intake;

public class MoveIntakeArmTEST extends Command {

  int timer;
  public double finalPosition;
  public static boolean raising;
  private final Intake m_intake;
  private double distanceLeft;
  private double totalDistanceToMove;
  private double appliedVoltage;

  // Called when the command is initially scheduled.
  public MoveIntakeArmTEST(Intake intake, double finalAngle) {
    m_intake = intake;
    // calculating the distance to move
    finalPosition = finalAngle;
    totalDistanceToMove = Math.abs(finalPosition - Robot.intakePos);
    // calculating the distance left to move
    distanceLeft = finalPosition - Robot.intakePos;
    // calculating the applied voltage
    if(totalDistanceToMove == 0)
    {
        appliedVoltage = 0;
    }
    else
    {
        appliedVoltage = 16 * Constants.intakeMotorSpeed * distanceLeft / totalDistanceToMove;
    }
    addRequirements(m_intake);
  }

  @Override
  public void initialize() {

    m_intake.moveIntakeArm(appliedVoltage);
    timer = 0;
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    distanceLeft = finalPosition - Robot.intakePos;
    appliedVoltage = 16 * Constants.intakeMotorSpeed * distanceLeft / totalDistanceToMove;
    m_intake.moveIntakeArm(appliedVoltage);

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

    if (Math.abs(distanceLeft) < 1) {
      return true;
    }

    // if (timer > 1000) {
    //   return true;
    // }

    return false;
  }
}
