package frc.robot.subsystems;

import com.ctre.phoenix6.configs.OpenLoopRampsConfigs;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {

  private final SparkMax intakeWheels =
      new SparkMax(Constants.intakeWheelsMotorID, MotorType.kBrushed);

  public final TalonFX intakeArm = new TalonFX(Constants.intakeLiftMotorID);

  public static final OpenLoopRampsConfigs configIntakeLift =
      new OpenLoopRampsConfigs().withDutyCycleOpenLoopRampPeriod(0.5);

  // static final DutyCycleOut intakeRequest = new DutyCycleOut(0.0);

  public void armEnterBrake() {
    intakeArm.setNeutralMode(NeutralModeValue.Brake);
  }

  public void armExtiBrake() {
    intakeArm.setNeutralMode(NeutralModeValue.Coast);
  }

  public void configureIntake() {
    //   var slot0Configs = new Slot0Configs();
    //   slot0Configs.kP = 2.4; // An error of 1 rotation results in 2.4 V output
    //   slot0Configs.kI = 0; // no output for integrated error
    //   slot0Configs.kD = 0.1; // A velocity of 1 rps results in 0.1 V output

    //   climber.getConfigurator().apply(slot0Configs);
    armEnterBrake();
  }

  public void lowerIntake() {
    intakeArm.set(-Constants.intakeMotorSpeed);
    // position = climber.getPosition().getValue();
    // climber.setPosition(0);
  }

  public void raiseIntake() {
    intakeArm.set(Constants.intakeMotorSpeed);
    // position = climber.getPosition().getValue();
    // climber.setPosition(90);
  }

  //   public static void setClimberPos(double position)
  //   {
  //     final PositionVoltage m_request = new PositionVoltage(0).withSlot(0);

  //     // set position to 10 rotations
  //     climber.setControl(m_request.withPosition(position));
  //   }

  public void stopIntake() {
    intakeArm.stopMotor();
  }
}
