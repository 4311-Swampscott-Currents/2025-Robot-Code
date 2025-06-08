// package frc.robot.subsystems;

// import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;
// import com.ctre.phoenix6.configs.TalonFXConfiguration;
// import com.ctre.phoenix6.controls.VoltageOut;
// import com.ctre.phoenix6.hardware.TalonFX;
// import com.ctre.phoenix6.signals.NeutralModeValue;
// import com.revrobotics.spark.SparkLowLevel.MotorType;
// import com.revrobotics.spark.SparkMax;
// import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import frc.robot.Constants;

// public class DeAlgaeArm extends SubsystemBase {
//   private final TalonFX deAlgae_m = new TalonFX(Constants.deAlgae_mID);
//   private final SparkMax deAlgaeWheel = new SparkMax(Constants.deAlgae_WheelId,
// MotorType.kBrushed);
//   private boolean deAlgae_mUp; // true if up, false if down

//   public DeAlgaeArm() {

//     deAlgae_mUp = false;
//     deAlgae_m.setPosition(0);

//     TalonFXConfiguration deAlgaeMConfig = new TalonFXConfiguration();
//     SoftwareLimitSwitchConfigs deAlgaeLimitConfig = deAlgaeMConfig.SoftwareLimitSwitch;
//     deAlgaeLimitConfig.ForwardSoftLimitEnable = true;
//     deAlgaeLimitConfig.ForwardSoftLimitThreshold = Constants.maxDeAlgae;
//     deAlgaeLimitConfig.ReverseSoftLimitEnable = true;
//     deAlgaeLimitConfig.ReverseSoftLimitThreshold = Constants.minDeAlgae;

//     deAlgae_m.getConfigurator().apply(deAlgaeMConfig);
//   }

//   public void deAlgae_mEnterBrake() {
//     deAlgae_m.setNeutralMode(NeutralModeValue.Brake);
//   }

//   public void deAlgae_mExitBrake() {
//     deAlgae_m.setNeutralMode(NeutralModeValue.Coast);
//   }

//   public Command stopDeAlgae_mCommand() {
//     return this.runOnce(() -> deAlgae_m.setControl(new VoltageOut(0)));
//   }

//   public Command deAlgae_mUpCommand() {

//     return this.runOnce(() -> deAlgae_m.setControl(new VoltageOut(-Constants.deAlgae_mSpeed)));
//   }

//   public Command deAlgae_mDownCommand() {
//     return this.runOnce(() -> deAlgae_m.setControl(new VoltageOut(Constants.deAlgae_mSpeed)));
//   }

//   public void stopDeAlgae_m() {
//     deAlgae_m.setControl(new VoltageOut(0));
//   }

//   public void deAlgae_mUp() {

//     deAlgae_m.setControl(new VoltageOut(Constants.deAlgae_mSpeed));
//   }

//   public void deAlgae_mDown() {
//     deAlgae_m.setControl(new VoltageOut(-Constants.deAlgae_mSpeed));
//   }

//   public void setDeAlgaeUp(boolean up) {
//     deAlgae_mUp = up;
//   }

//   public boolean getDeAlgaeUp() {
//     return deAlgae_mUp;
//   }

//   public double getDeAlgaePos() {
//     return deAlgae_m.getPosition().getValueAsDouble();
//   }

//   public Command spinDeAlgaeWheel() {
//     return this.runOnce(() -> deAlgaeWheel.set(Constants.deAlgaeWheelSpeed));
//   }

//   public Command stopDeAlgaeWheel() {
//     return this.runOnce(() -> deAlgaeWheel.set(0));
//   }
// }
