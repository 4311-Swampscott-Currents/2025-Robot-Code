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
  SoftwareLimitSwitchConfigs limitConfig = new SoftwareLimitSwitchConfigs();

  public ClimberMotor() {

    // climber = new TalonFX(climberMotorID);
    // position.magnitude();
    climber.setPosition(0);
    // slot0Configs.kV = 0.12;
    // slot0Configs.kP = 0.11;
    // slot0Configs.kI = 0.5;
    // slot0Configs.kD = 0.001;

    // apply all configs, 50 ms total timeout
    // climber.getConfigurator().apply(talonFXConfigs, 0.050);

    limitConfig.ForwardSoftLimitEnable = true;
    limitConfig.ForwardSoftLimitThreshold = Constants.CLIMBER_MAX_ANGLE_UP;
    limitConfig.ReverseSoftLimitEnable = true;
    limitConfig.ReverseSoftLimitThreshold = Constants.CLIMBER_MIN_ANGLE_DOWN;
    climber.getConfigurator().apply(limitConfig);
  }

  public void enterBrake() {
    climber.setNeutralMode(NeutralModeValue.Brake);
  }

  public void extiBrake() {
    climber.setNeutralMode(NeutralModeValue.Coast);
  }

  public void configureClimber() {
    //   var slot0Configs = new Slot0Configs();
    //   slot0Configs.kP = 2.4; // An error of 1 rotation results in 2.4 V output
    //   slot0Configs.kI = 0; // no output for integrated error
    //   slot0Configs.kD = 0.1; // A velocity of 1 rps results in 0.1 V output

    //   climber.getConfigurator().apply(slot0Configs);
    enterBrake();
    // climber.setPosition(0);
  }

  public Command lowerClimber() {
    return this.runOnce(() -> climber.set(Constants.climberMotorSpeed));

    // position = climber.getPosition().getValue();
    // climber.setPosition(0);
  }

  // position = climber.getPosition().getValue();
  // climber.setPosition(90);

  //   public static void setClimberPos(double position)
  //   {
  //     final PositionVoltage m_request = new PositionVoltage(0).withSlot(0);

  //     // set position to 10 rotations
  //     climber.setControl(m_request.withPosition(position));
  //   }

  public void stopClimber() {
    climber.stopMotor();
  }
}
