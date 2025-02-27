package frc.robot.subsystems;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  private final SparkMaxConfig intakeWheelsconfig = new SparkMaxConfig();

  private final SparkMax intakeWheels =
      new SparkMax(Constants.intakeWheelsMotorID, MotorType.kBrushless);

  private final TalonFX intakeArm = new TalonFX(Constants.intakeLiftMotorID);

  SoftwareLimitSwitchConfigs intakeLimitConfig = new SoftwareLimitSwitchConfigs();

  // in init function
  TalonFXConfiguration talonFXConfigs = new TalonFXConfiguration();

  // set slot 0 gains
  Slot0Configs slot0Configs = talonFXConfigs.Slot0;

  // set Motion Magic settings
  MotionMagicConfigs motionMagicConfigs = talonFXConfigs.MotionMagic;

  // create a Motion Magic request, voltage output
  final MotionMagicVoltage m_request = new MotionMagicVoltage(0);

  public Intake() {
    // armEnterBrake();
    // wheelsEnterBrake();

    intakeLimitConfig.ForwardSoftLimitEnable = true;
    intakeLimitConfig.ForwardSoftLimitThreshold = Constants.intakeFinalMaxAngle;
    intakeLimitConfig.ReverseSoftLimitEnable = true;
    intakeLimitConfig.ReverseSoftLimitThreshold = Constants.intakeMinAngle;
    intakeArm.getConfigurator().apply(intakeLimitConfig);

    slot0Configs.kS = 0.25; // Add 0.25 V output to overcome static friction
    slot0Configs.kV = 0.12; // A velocity target of 1 rps results in 0.12 V output
    slot0Configs.kA = 0.01; // An acceleration of 1 rps/s requires 0.01 V output
    slot0Configs.kP = 4.8; // A position error of 2.5 rotations results in 12 V output
    slot0Configs.kI = 0; // no output for integrated error
    slot0Configs.kD = 0.1; // A velocity error of 1 rps results in 0.1 V output

    motionMagicConfigs.MotionMagicCruiseVelocity = 30; // Target cruise velocity of 30 rps
    motionMagicConfigs.MotionMagicAcceleration =
        60; // Target acceleration of 60 rps/s (0.5 seconds)
    motionMagicConfigs.MotionMagicJerk = 600; // Target jerk of 600 rps/s/s (0.1 seconds)

    intakeArm.getConfigurator().apply(talonFXConfigs);
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
    // intakeWheelsconfig.idleMode(SparkBaseConfig.IdleMode.kBrake);
    // intakeWheels.configure(
    //     intakeWheelsconfig,
    //     SparkBase.ResetMode.kResetSafeParameters,
    //     SparkBase.PersistMode.kPersistParameters);
    intakeWheelsconfig.idleMode(IdleMode.kBrake);
    intakeWheels.configure(
        intakeWheelsconfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  public void wheelsExitBrake() {
    // intakeWheelsconfig.idleMode(SparkBaseConfig.IdleMode.kCoast);
    // intakeWheels.configure(
    //     intakeWheelsconfig,
    //     SparkBase.ResetMode.kResetSafeParameters,
    //     SparkBase.PersistMode.kPersistParameters);
    intakeWheelsconfig.idleMode(IdleMode.kCoast);
    intakeWheels.configure(
        intakeWheelsconfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
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

  public Command intakeWheelsSpinInCommand() {
    return this.runOnce(() -> intakeWheels.set(-Constants.intakeWheelSpeed));
  }

  public Command intakeWheelsSpinOutCommand() {
    return this.runOnce(() -> intakeWheels.set(Constants.intakeWheelOutSpeed));
  }

  public Command intakeWheelsStopCommand() {
    return this.runOnce(() -> intakeWheels.stopMotor());
  }

  public double getIntakeWheelsVolt() {
    return intakeWheels.getAppliedOutput();
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

  public Command stopIntakeArmCommand() {
    return this.runOnce(() -> intakeArm.stopMotor());
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

  public Command turnArmUsingMotionMagic(double position) {
    // set target position to position rotations
    return this.runOnce(() -> intakeArm.setControl(m_request.withPosition(position)));
  }
}
