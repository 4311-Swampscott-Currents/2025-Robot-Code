package frc.robot.subsystems.drive;

import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimberMotor extends SubsystemBase {

  public static final TalonFX climber = new TalonFX(Constants.climberMotorID);
  // public static Angle position;

  // TalonFXConfiguration talonFXConfigs = new TalonFXConfiguration();

  // set slot 0 gains and leave every other config factory-default
  // Slot0Configs slot0Configs = talonFXConfigs.Slot0;

  static final DutyCycleOut climberRequest = new DutyCycleOut(0.0);
  SoftwareLimitSwitchConfigs limitConfig = new SoftwareLimitSwitchConfigs();

  public ClimberMotor() {

    // climber = new TalonFX(climberMotorID);
    // position.magnitude();
    // climber.setPosition(position);
    // slot0Configs.kV = 0.12;
    // slot0Configs.kP = 0.11;
    // slot0Configs.kI = 0.5;
    // slot0Configs.kD = 0.001;

    // apply all configs, 50 ms total timeout
    // climber.getConfigurator().apply(talonFXConfigs, 0.050);

    limitConfig.ForwardSoftLimitEnable = true;
    limitConfig.ForwardSoftLimitThreshold = Constants.INTAKE_MAX_ANGLE_UP;
    limitConfig.ReverseSoftLimitEnable = true;
    limitConfig.ReverseSoftLimitThreshold = Constants.INTAKE_MIN_ANGLE_DOWN;
    climber.getConfigurator().apply(limitConfig);
  }

  public static void enterBrake() {
    climber.setNeutralMode(NeutralModeValue.Brake);
  }

  public static void extiBrake() {
    climber.setNeutralMode(NeutralModeValue.Coast);
  }

  public static Angle getClimberPosition() {
    return climber.getPosition().getValue();
  }

  public static void configureClimber() {
    //   var slot0Configs = new Slot0Configs();
    //   slot0Configs.kP = 2.4; // An error of 1 rotation results in 2.4 V output
    //   slot0Configs.kI = 0; // no output for integrated error
    //   slot0Configs.kD = 0.1; // A velocity of 1 rps results in 0.1 V output

    //   climber.getConfigurator().apply(slot0Configs);
    enterBrake();
    climber.setPosition(0);
  }

  public static void lowerClimber() {
    climber.setControl(climberRequest.withOutput(-Constants.climberMotorSpeed));
    // position = climber.getPosition().getValue();
    // climber.setPosition(0);
  }

  public static void raiseClimber() {
    climber.setControl(climberRequest.withOutput(Constants.climberMotorSpeed));
    // position = climber.getPosition().getValue();
    // climber.setPosition(90);
  }

  //   public static void setClimberPos(double position)
  //   {
  //     final PositionVoltage m_request = new PositionVoltage(0).withSlot(0);

  //     // set position to 10 rotations
  //     climber.setControl(m_request.withPosition(position));
  //   }

  public static void stopClimber() {
    climber.stopMotor();
  }
}
