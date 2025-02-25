package frc.robot.subsystems;

import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimberMotor extends SubsystemBase {

  private final TalonFX climber = new TalonFX(Constants.climberMotorID);

  // static final DutyCycleOut climberRequest = new DutyCycleOut(0.0);
  SoftwareLimitSwitchConfigs climberLimitConfig = new SoftwareLimitSwitchConfigs();

  public ClimberMotor() {

    climber.setPosition(0);
    // slot0Configs.kV = 0.12;
    // slot0Configs.kP = 0.11;
    // slot0Configs.kI = 0.5;
    // slot0Configs.kD = 0.001;

    // apply all configs, 50 ms total timeout
    // climber.getConfigurator().apply(talonFXConfigs, 0.050);

    climberLimitConfig.ForwardSoftLimitEnable = true;
    climberLimitConfig.ForwardSoftLimitThreshold = Constants.CLIMBER_MAX_ANGLE_UP;
    climberLimitConfig.ReverseSoftLimitEnable = true;
    climberLimitConfig.ReverseSoftLimitThreshold = Constants.CLIMBER_MIN_ANGLE_DOWN;
    climber.getConfigurator().apply(climberLimitConfig);
  }

  public void enterBrake() {
    climber.setNeutralMode(NeutralModeValue.Brake);
  }

  public void extiBrake() {
    climber.setNeutralMode(NeutralModeValue.Coast);
  }

  public Command lowerClimber() {
    return this.runOnce(() -> climber.set(Constants.climberMotorSpeed));
  }

  public Command raiseClimber() {
    return this.runOnce(() -> climber.set(-Constants.climberMotorSpeed));
  }

  public void stopClimber() {
    climber.stopMotor();
  }
}
