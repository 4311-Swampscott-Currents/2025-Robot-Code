// Copyright 2021-2025 FRC 6328
// http://github.com/Mechanical-Advantage
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// version 3 as published by the Free Software Foundation or
// available in the root directory of this project.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.

package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.commands.DriveCommands;
import frc.robot.commands.IntakeUpToPos;
import frc.robot.commands.LowerIntakeCrocker;
import frc.robot.commands.LowerIntakeToPos;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.ClimberMotor;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.GyroIO;
import frc.robot.subsystems.drive.GyroIOPigeon2;
import frc.robot.subsystems.drive.ModuleIO;
import frc.robot.subsystems.drive.ModuleIOSim;
import frc.robot.subsystems.drive.ModuleIOTalonFX;
import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // Subsystems
  private final Drive drive;

  // Controller
  private final CommandXboxController controller = new CommandXboxController(0);

  // Dashboard inputs
  private final LoggedDashboardChooser<Command> autoChooser;
  // private final Camera
  // (Command)PathPlannerAuto forward;

  static final NetworkTableInstance ntInstance = NetworkTableInstance.getDefault();
  static final NetworkTable limelightTable = ntInstance.getTable("limelight");
  public static final ClimberMotor climber_m = new ClimberMotor();
  public static final Intake intake_m = new Intake();

  // static final ClimberUp climberLift = new ClimberUp();
  // static final ClimberDown climberlower = new ClimberDown();
  // static final IntakeUp liftIntake = new IntakeUp();
  public static final DutyCycleEncoder intakeEncoder =
      new DutyCycleEncoder(Constants.intakeEncoderID);

  // simple proportional turning control with Limelight.
  // "proportional control" is a control algorithm in which the output is proportional to the error.
  // in this case, we are going to return an angular velocity that is proportional to the
  // "tx" value from the Limelight.
  double limelight_aim_proportional() {
    // kP (constant of proportionality)
    // this is a hand-tuned number that determines the aggressiveness of our proportional control
    // loop
    // if it is too high, the robot will oscillate around.
    // if it is too low, the robot will never reach its target
    // if the robot never turns in the correct direction, kP should be inverted.
    double kP = .035;

    // tx ranges from (-hfov/2) to (hfov/2) in degrees. If your target is on the rightmost edge of
    // your limelight 3 feed, tx should return roughly 31 degrees.
    double targetingAngularVelocity = LimelightHelpers.getTX("limelight") * kP;

    // invert since tx is positive when the target is to the right of the crosshair
    targetingAngularVelocity *= -1.0;

    return targetingAngularVelocity;
  }

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

    intakeEncoder.setInverted(false);

    // NetworkTableInstance ntInstance = NetworkTableInstance.getDefault();
    // NetworkTable limelightTable = ntInstance.getTable("limelight");
    // public static ClimberMotor climber; = new ClimberMotor();

    // climber_m.extiBrake();
    // intake_m.armEnterBrake();

    switch (Constants.currentMode) {
      case REAL:
        // Real robot, instantiate hardware IO implementations
        drive =
            new Drive(
                new GyroIOPigeon2(),
                new ModuleIOTalonFX(TunerConstants.FrontLeft),
                new ModuleIOTalonFX(TunerConstants.FrontRight),
                new ModuleIOTalonFX(TunerConstants.BackLeft),
                new ModuleIOTalonFX(TunerConstants.BackRight));
        break;

      case SIM:
        // Sim robot, instantiate physics sim IO implementations
        drive =
            new Drive(
                new GyroIO() {},
                new ModuleIOSim(TunerConstants.FrontLeft),
                new ModuleIOSim(TunerConstants.FrontRight),
                new ModuleIOSim(TunerConstants.BackLeft),
                new ModuleIOSim(TunerConstants.BackRight));
        break;

      default:
        // Replayed robot, disable IO implementations
        drive =
            new Drive(
                new GyroIO() {},
                new ModuleIO() {},
                new ModuleIO() {},
                new ModuleIO() {},
                new ModuleIO() {});
        break;
    }

    // ClimbUp climbCommand = new ClimbUp();

    NamedCommands.registerCommand("Shoot", intake_m.intakeWheelsSpinInAfterIntakeCommand());
    NamedCommands.registerCommand("Stop Shoot", intake_m.intakeWheelsStopCommand());
    NamedCommands.registerCommand("Lower Intake", new LowerIntakeToPos(intake_m, 60));
    NamedCommands.registerCommand("Raise Intake", new IntakeUpToPos(intake_m, 75));
    // NamedCommands.registerCommand("null", getAutonomousCommand());

    // Set up auto routines
    autoChooser = new LoggedDashboardChooser<>("Auto Choices", AutoBuilder.buildAutoChooser());

    // autoChooser.addOption("Forward", new PathPlannerAuto("Forward"));

    // Set up SysId routines
    autoChooser.addOption(
        "Drive Wheel Radius Characterization", DriveCommands.wheelRadiusCharacterization(drive));
    autoChooser.addOption(
        "Drive Simple FF Characterization", DriveCommands.feedforwardCharacterization(drive));
    autoChooser.addOption(
        "Drive SysId (Quasistatic Forward)",
        drive.sysIdQuasistatic(SysIdRoutine.Direction.kForward));
    autoChooser.addOption(
        "Drive SysId (Quasistatic Reverse)",
        drive.sysIdQuasistatic(SysIdRoutine.Direction.kReverse));
    autoChooser.addOption(
        "Drive SysId (Dynamic Forward)", drive.sysIdDynamic(SysIdRoutine.Direction.kForward));
    autoChooser.addOption(
        "Drive SysId (Dynamic Reverse)", drive.sysIdDynamic(SysIdRoutine.Direction.kReverse));

    // Configure the button bindings
    // forward = new PathPlannerAuto("Forward");
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // Default command, normal field-relative drive
    drive.setDefaultCommand(
        DriveCommands.joystickDrive(
            drive,
            () -> -controller.getLeftY(),
            () -> -controller.getLeftX(),
            () -> -controller.getRightX()));

    // Lock to 0° when A button is held
    controller
        .a()
        .whileTrue(
            DriveCommands.joystickDriveAtAngle(
                drive,
                () -> -controller.getLeftY(),
                () -> -controller.getLeftX(),
                () -> new Rotation2d()));

    // Switch to X pattern when X button is pressed
    controller.x().onTrue(Commands.runOnce(drive::stopWithX, drive));

    // Aiden Work for Visial servo go here
    // controller.y().onTrue()

    // Reset gyro to 0° when LB button is pressed
    controller
        .rightBumper()
        .onTrue(
            Commands.runOnce(
                    () ->
                        drive.setPose(
                            new Pose2d(drive.getPose().getTranslation(), new Rotation2d())),
                    drive)
                .ignoringDisable(true));
    // runs climb up command when b is pressed
    controller.b().onTrue(climber_m.lowerClimber()).onFalse(climber_m.stopClimber());
    controller.y().onTrue(climber_m.raiseClimber()).onFalse(climber_m.stopClimber());

    // controller
    //     .leftTrigger()
    //     .onTrue(intake_m.intakeWheelsSpinInCommand())
    //     .onFalse(intake_m.intakeWheelsStopCommand());
    // controller
    //     .leftBumper()
    //     .onTrue(intake_m.intakeWheelsSpinOutCommand())
    //     .onFalse(intake_m.intakeWheelsStopCommand());

    // controller
    //     .leftTrigger()
    //     .onTrue(new LowerIntakeToPos(intake_m, 25))
    //     .onFalse(new IntakeUpToPos(intake_m, 90));

    controller
        .leftTrigger()
        .onTrue(intake_m.intakeWheelsSpinInCommand().andThen(new LowerIntakeToPos(intake_m, 40)))
        .onFalse(
            intake_m
                .intakeWheelsSpinInAfterIntakeCommand()
                .andThen(new IntakeUpToPos(intake_m, 80)));
    controller
        .leftBumper()
        .onTrue(new LowerIntakeCrocker(intake_m, 65).andThen(intake_m.intakeWheelsSpinOutCommand()))
        .onFalse(intake_m.intakeWheelsStopCommand().andThen(new IntakeUpToPos(intake_m, 80)));
    // Crocker stuff
    controller.povUp().onTrue(intake_m.intakeUp()).onFalse(intake_m.intakeStop());

    controller.povDown().onTrue(intake_m.intakeDown()).onFalse(intake_m.intakeStop());

    // end of Crocker Stuff

    // controller.povLeft().onTrue(intake_m.intakeWheelsStopCommand());
    // controller.y().onTrue(climber_m.raiseClimber()).onFalse(climber_m.stopClimber());

    // controller.rightBumper().onTrue(new IntakeUpToPos(intake_m, Constants.intakeFinalMaxAngle));
    // controller.rightTrigger().onTrue(new LowerIntakeToPos(intake_m, Constants.intakeMinAngle));

    // controller
    //     .rightTrigger()
    //     .onTrue(intake_m.turnArmUsingMotionMagic(15))
    //     .onFalse(intake_m.stopIntakeArmCommand());

    // controller.rightTrigger().onTrue(intake_m.stopIntakeArmCommand());
    // controller.y().whileTrue(climber_m.raiseClimber());
    // //controller
    //     .leftTrigger()
    //     .onTrue(
    //         new LowerIntakeToPos(intake_m, Constants.intakeMinAngle)
    //             .andThen(
    //                 new IntakeAlgae(intake_m)
    //                     .andThen(new IntakeUpToPos(intake_m, Constants.intakeFinalMaxAngle))));
    // runs climb down command when x is pressed
    // controller.y().onTrue(new IntakeUp(intake_m));

  }
  // }

  //     should turn to certain angle
  /*
       controller
           .y()
           .whileTrue(
               DriveCommands.joystickDrive(
                   drive,
                   () -> -controller.getLeftY(),
                   () -> -controller.getLeftX(),
                   () -> (Drive.getRotation2().getDegrees())));
    }
  */

  //   * Use this to pass the autonomous command to the main {@link Robot} class.
  //   *
  //  * @return the command to run in autonomous

  public Command getAutonomousCommand() {
    //     PathPlannerAuto forward = new PathPlannerAuto("Forward");
    return autoChooser.get();
    //     return new PathPlannerAuto("Forward");
  }
}
