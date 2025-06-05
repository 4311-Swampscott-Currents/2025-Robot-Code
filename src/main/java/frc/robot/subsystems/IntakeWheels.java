// THIS CLASS IS NOT USED YET!!!
package frc.robot.subsystems;

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

public class IntakeWheels extends SubsystemBase {
  private final SparkMaxConfig intakeWheelsconfig = new SparkMaxConfig();

  private final SparkMax intakeWheels =
      new SparkMax(Constants.intakeWheelsMotorID, MotorType.kBrushless);
  private final RelativeEncoder intakeWheelsEncoder;

  public IntakeWheels() {

    intakeWheels.configure(
        intakeWheelsconfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    intakeWheelsEncoder = intakeWheels.getEncoder();
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

  public Command intakeWheelsStopCommand() {
    return this.runOnce(() -> intakeWheels.set(0));
    // return this.runOnce(() -> intakeWheels.stopMotor());
  }

  public double getIntakeWheelsVolt() {
    return intakeWheels.getAppliedOutput();
  }

  public double getIntakeWheelVelocity() {
    return intakeWheelsEncoder.getVelocity();
  }

  public boolean areWheelsStop() {
    return Math.abs(getIntakeWheelVelocity()) < 0.1;
  }

  public void stopWheels() {
    // intakeWheels.setVoltage(0);
    intakeWheels.stopMotor();
  }
}
