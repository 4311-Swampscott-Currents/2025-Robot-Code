package frc.robot.subsystems;

import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  private final SparkMaxConfig intakeWheelsconfig = new SparkMaxConfig();

  private final SparkMax intakeWheels =
      new SparkMax(Constants.intakeWheelsMotorID, MotorType.kBrushed);

  private final TalonFX intakeArm = new TalonFX(Constants.intakeLiftMotorID);

  SoftwareLimitSwitchConfigs intakeLimitConfig = new SoftwareLimitSwitchConfigs();

  public Intake() {
    armEnterBrake();
    wheelsEnterBrake();

    intakeLimitConfig.ForwardSoftLimitEnable = true;
    intakeLimitConfig.ForwardSoftLimitThreshold = Constants.intakeFinalMaxAngle;
    intakeLimitConfig.ReverseSoftLimitEnable = true;
    intakeLimitConfig.ReverseSoftLimitThreshold = Constants.intakeMinAngle;
    intakeArm.getConfigurator().apply(intakeLimitConfig);
  }

  public void setArmPos(double armEncoderPos) {
    intakeArm.setPosition(armEncoderPos);
  }

  public void armEnterBrake() {
    intakeArm.setNeutralMode(NeutralModeValue.Brake);
  }

  public void armExitBrake() {
    intakeArm.setNeutralMode(NeutralModeValue.Coast);
  }

  public void wheelsEnterBrake() {
    intakeWheelsconfig.idleMode(SparkBaseConfig.IdleMode.kBrake);
    intakeWheels.configure(
        intakeWheelsconfig,
        SparkBase.ResetMode.kResetSafeParameters,
        SparkBase.PersistMode.kPersistParameters);
  }

  public void wheelsExitBrake() {
    intakeWheelsconfig.idleMode(SparkBaseConfig.IdleMode.kCoast);
    intakeWheels.configure(
        intakeWheelsconfig,
        SparkBase.ResetMode.kResetSafeParameters,
        SparkBase.PersistMode.kPersistParameters);
  }

  public void lowerIntake() {
    intakeArm.set(-Constants.intakeMotorSpeed);
  }

  public void raiseIntake() {
    intakeArm.set(Constants.intakeMotorSpeed);
  }

  public void intakeWheelsSpinIn() {
    intakeWheels.set(Constants.intakeWheelSpeed);
  }

  public void intakeWheelsSpinOut() {
    intakeWheels.set(-Constants.intakeWheelSpeed);
  }

  // public void turnIntakeArmToPos(double newPos, double currentPos) {
  //   while(newPos != currentPos) {
  //     if(newPos > currentPos) {
  //       intakeArm.set(Constants.intakeMotorSpeed);
  //     } else {
  //       intakeArm.set(-Constants.intakeMotorSpeed);
  //     }
  //   }
  // }

  public void stopIntakeArm() {
    intakeArm.stopMotor();
  }

  public void stopWheels() {
    intakeWheels.stopMotor();
  }

  public void configureIntake() {
    //   var slot0Configs = new Slot0Configs();
    //   slot0Configs.kP = 2.4; // An error of 1 rotation results in 2.4 V output
    //   slot0Configs.kI = 0; // no output for integrated error
    //   slot0Configs.kD = 0.1; // A velocity of 1 rps results in 0.1 V output

    //   climber.getConfigurator().apply(slot0Configs);
    armEnterBrake();
  }
}
