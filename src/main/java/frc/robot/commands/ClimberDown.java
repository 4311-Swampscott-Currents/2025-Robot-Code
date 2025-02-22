package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimberMotor;

public class ClimberDown extends Command {
  // Code runs, but motor does not turn...
  int timer;
  public static double position;
  public static boolean raising;
  // public static ClimberMotor climber;

  // Called when the command is initially scheduled.

  @Override
  public void initialize() {

    // in init function, set slot 0 gains
    // climber = new ClimberMotor();
    ClimberMotor.configureClimber();
    // if (ClimberMotor.getClimberPosition() == 0) {
    //   raising = true;
    // }
    timer = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    ClimberMotor.lowerClimber();
    // ClimberMotor.climber.set(0.2);
    System.out.println("spinning " + timer);

    // position = ClimberMotor.climber.getPosition().getValueAsDouble();
    // should raise to 90 degrees if it is flat
    // if (raising) {
    //   System.out.println("raising");
    //   ClimberMotor.raiseClimber();
    //   // raising = true;
    // }
    // // should go down to 0 degrees if it is at 90 degree angle
    // else if (!raising) {
    //   System.out.println("lowering");
    //   ClimberMotor.lowerClimber();
    // raising = false;
    // }

    timer++;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    ClimberMotor.stopClimber();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (timer > 500) {
      return true;
    }
    // if (raising) {
    //   System.out.println("Finished raising");
    //   return ClimberMotor.getClimberPosition() >= 90;
    // }
    // if (!raising) {
    //   System.out.println("Finished lowering");
    //   System.out.println(ClimberMotor.getClimberPosition());
    //   return ClimberMotor.getClimberPosition() <= 0;
    // }
    return false;
  }
}
