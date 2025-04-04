package frc.robot.subsystems;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.revrobotics.RelativeEncoder;
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
  private boolean intakeUp;
  private boolean deAlgae_mUp;

  private final SparkMaxConfig intakeWheelsconfig = new SparkMaxConfig();

  private final SparkMax intakeWheels =
      new SparkMax(Constants.intakeWheelsMotorID, MotorType.kBrushless);
  private final RelativeEncoder intakeWheelsEncoder;

  private final TalonFX intakeArm = new TalonFX(Constants.intakeLiftMotorID);
  private final TalonFX intakeArm_M2 = new TalonFX(Constants.intakeLiftMotor2ID);

  // private final TalonFX deAlgae_m = new TalonFX(Constants.deAlgae_mID);
  // private final Follower intakeArm_M2 = new Follower(Constants.intakeLiftMotorID, true);

  // in init function
  TalonFXConfiguration talonFXConfigs = new TalonFXConfiguration();

  // set slot 0 gains
  Slot0Configs slot0Configs = talonFXConfigs.Slot0;

  // set Motion Magic settings
  MotionMagicConfigs motionMagicConfigs = talonFXConfigs.MotionMagic;

  // software limits
  SoftwareLimitSwitchConfigs intakeLimitConfig_L = talonFXConfigs.SoftwareLimitSwitch;
  SoftwareLimitSwitchConfigs intakeLimitConfig_R = new SoftwareLimitSwitchConfigs();

  // create a Motion Magic request, voltage output
  final MotionMagicVoltage m_request = new MotionMagicVoltage(0);
  VoltageOut turnSpeedUp = new VoltageOut(Constants.intakeMotorSpeed * 16);
  VoltageOut turnSpeedDown = new VoltageOut(Constants.intakeMotorSpeed * -16);

  public Intake() {

    // deAlgae_m.setPosition(0);

    intakeWheelsEncoder = intakeWheels.getEncoder();

    intakeArm_M2.setControl(new Follower(Constants.intakeLiftMotorID, true));

    armEnterBrake();
    intakeUp = true;
    deAlgae_mUp = false;
    // wheelsEnterBrake();

    intakeLimitConfig_L.ForwardSoftLimitEnable = true;
    intakeLimitConfig_L.ForwardSoftLimitThreshold = Constants.intakeFinalMaxAngle;
    intakeLimitConfig_L.ReverseSoftLimitEnable = true;
    intakeLimitConfig_L.ReverseSoftLimitThreshold = Constants.intakeMinAngle;

    intakeLimitConfig_R.ForwardSoftLimitEnable = true;
    intakeLimitConfig_R.ForwardSoftLimitThreshold = Constants.intakeFinalMaxAngle;
    intakeLimitConfig_R.ReverseSoftLimitEnable = true;
    intakeLimitConfig_R.ReverseSoftLimitThreshold = Constants.intakeMinAngle;

    slot0Configs.kS = 0; // Add 0 V output to overcome static friction
    slot0Configs.kV = 0.12; // A velocity target of 1 rps results in 0.12 V output
    slot0Configs.kA = 0; // An acceleration of   rps/s requires 0. V output
    slot0Configs.kP = 0.2; // A position error of   rotations results in 12 V output
    slot0Configs.kI = 0; // no output for integrated error
    slot0Configs.kD = 0; // A velocity error
    slot0Configs.kG = 1;

    slot0Configs.GravityType = GravityTypeValue.Arm_Cosine;

    motionMagicConfigs.MotionMagicCruiseVelocity = 85; // Target cruise velocity of   rps
    motionMagicConfigs.MotionMagicAcceleration =
        850; // Target acceleration of   rps/s (0.5 seconds)
    motionMagicConfigs.MotionMagicJerk = 10000; // Target jerk of   rps/s/s (0.1 seconds)

    intakeArm.getConfigurator().apply(talonFXConfigs);

    // intakeArm.getConfigurator().apply(intakeLimitConfig_L );
    intakeArm_M2.getConfigurator().apply(intakeLimitConfig_R);

    m_request.Slot = 0;
    // m_request.LimitForwardMotion = true;
    // m_request.LimitReverseMotion = true;

    // intakeArm.getConfigurator().
  }

  // public boolean isIntakeUp()
  // {
  //   return intakeUp;
  // }

  // public void setIntakeUp(boolean intake)
  // {

  // }

  public Command turnArmUsingMotionMagic(double position) {
    // set target position to position rotations
    return this.runOnce(() -> intakeArm.setControl(m_request.withPosition(position)));
  }

  public void setArmPos(double armEncoderPos) {
    intakeArm.setPosition(armEncoderPos);
    intakeArm_M2.setPosition(armEncoderPos);
  }

  // public void deAlgae_mEnterBrake() {
  //   deAlgae_m.setNeutralMode(NeutralModeValue.Brake);
  // }

  // public void deAlgae_mExitBrake() {
  //   deAlgae_m.setNeutralMode(NeutralModeValue.Coast);
  // }

  public void armEnterBrake() {
    intakeArm.setNeutralMode(NeutralModeValue.Brake);
    intakeArm_M2.setNeutralMode(NeutralModeValue.Brake);
    // intakeArm.set
  }

  public void armExitBrake() {
    intakeArm.setNeutralMode(NeutralModeValue.Coast);
    intakeArm_M2.setNeutralMode(NeutralModeValue.Coast);
  }

  public void wheelsEnterBrake() {
    intakeWheelsconfig.idleMode(IdleMode.kBrake);
    intakeWheels.configure(
        intakeWheelsconfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  public void wheelsExitBrake() {
    intakeWheelsconfig.idleMode(IdleMode.kCoast);
    intakeWheels.configure(
        intakeWheelsconfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  public void moveIntakeArm(double speed) {
    intakeArm.setControl(new VoltageOut(speed));
  }

  public void lowerIntake() {
    intakeArm.setControl(turnSpeedDown);
    // intakeArm.set(-Constants.intakeMotorSpeed);
  }

  public void raiseIntake() {
    intakeArm.setControl(turnSpeedUp);
    // intakeArm.set(Constants.intakeMotorSpeed * 0.75);
  }

  public void intakeWheelsSpin(double m_speed, boolean spinIn) {
    if (!spinIn) {
      m_speed *= -1;
    }
    intakeWheels.set(m_speed);
  }

  public Command intakeWheelsSpinCommand(final double m_speed, boolean spinIn) {
    if (!spinIn) {
      return this.runOnce(() -> intakeWheels.set((-m_speed)));
    }
    return this.runOnce(() -> intakeWheels.set((m_speed)));
  }

  public Command intakeWheelsShootOutCoral() {
    return this.runOnce(() -> intakeWheels.set(Constants.intakeWheelSpeedCoral * 1.25));
  }

  // public Command intakeWheelsSpinOutCommand() {
  //   return this.runOnce(() -> intakeWheels.set(-Constants.intakeWheelOutSpeed));
  // }

  public Command intakeWheelsStopCommand() {
    return this.runOnce(() -> intakeWheels.set(0));
    // return this.runOnce(() -> intakeWheels.stopMotor());
  }
  // Crocker additions
  public Command intakeUp() {
    return this.runOnce(() -> intakeArm.setControl(turnSpeedUp));
    // return this.runOnce(() -> intakeArm.set(Constants.intakeMotorSpeed));
  }

  public Command intakeDown() {
    return this.runOnce(() -> intakeArm.setControl(turnSpeedDown));
    // return this.runOnce(() -> intakeArm.set(-Constants.intakeMotorSpeed));
  }

  public Command intakeStop() {
    return this.runOnce(() -> intakeArm.setControl(new VoltageOut(0)));
    // return this.runOnce(() -> intakeArm.stopMotor());
  }
  // end of Crocker
  public double getIntakeWheelsVolt() {
    return intakeWheels.getAppliedOutput();
  }

  public double getIntakeWheelVelocity() {
    return intakeWheelsEncoder.getVelocity();
  }

  public boolean areWheelsStop() {
    return Math.abs(getIntakeWheelVelocity()) < 0.1;
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
    intakeArm.setControl(new VoltageOut(0));
    // intakeArm.stopMotor();
  }

  public Command stopIntakeArmCommand() {
    return this.runOnce(() -> intakeArm.setControl(new VoltageOut(0)));
  }

  public void stopWheels() {
    // intakeWheels.setVoltage(0);
    intakeWheels.stopMotor();
  }

  // public Command stopDeAlgae_mCommand() {
  //   return this.runOnce(() -> deAlgae_m.stopMotor());
  // }

  // public Command deAlgae_mUpCommand() {

  //   return this.runOnce(() -> deAlgae_m.set(-Constants.deAlgae_mSpeed));
  // }

  // public Command deAlgae_mDownCommand() {
  //   return this.runOnce(() -> deAlgae_m.set(Constants.deAlgae_mSpeed));
  // }

  // public void stopDeAlgae_m() {
  //   deAlgae_m.stopMotor();
  // }

  // public void deAlgae_mUp() {

  //   deAlgae_m.set(Constants.deAlgae_mSpeed);
  // }

  // public void deAlgae_mDown() {
  //   deAlgae_m.set(-Constants.deAlgae_mSpeed);
  // }

  public void setDeAlgaeUp(boolean up) {
    deAlgae_mUp = up;
  }

  public boolean getDeAlgaeUp() {
    return deAlgae_mUp;
  }

  // public double getDeAlgaePos() {
  //   return deAlgae_m.getPosition().getValueAsDouble();
  // }
}

// public void intakeWheelsSpinIn() {
  //   intakeWheels.set(Constants.intakeWheelSpeed);
  // }

  // public void intakeWheelsSpinOut() {
  //   intakeWheels.set(-Constants.intakeWheelSpeed);
  // }

  // public Command intakeWheelsSpinInCommand() {
  //   return this.runOnce(() -> intakeWheels.set(Constants.intakeWheelSpeed));
  // }

  // public Command intakeWheelsSpinInAfterIntakeCommand() {
  //   return this.runOnce(() -> intakeWheels.set(Constants.intakeWheelSpeedAfterIntake));
  // }
