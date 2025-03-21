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

import edu.wpi.first.wpilibj.RobotBase;

/**
 * This class defines the runtime mode used by AdvantageKit. The mode is always "real" when running
 * on a roboRIO. Change the value of "simMode" to switch between "sim" (physics sim) and "replay"
 * (log replay from a file).
 */
public final class Constants {
  public static final Mode simMode = Mode.SIM;
  public static final Mode currentMode = RobotBase.isReal() ? Mode.REAL : simMode;

  public static enum Mode {
    /** Running on a real robot. */
    REAL,

    /** Running a physics simulator. */
    SIM,

    /** Replaying from a log file. */
    REPLAY
  }

  // motor ids
  public static final int climberMotorID = 15;
  public static final int intakeWheelsMotorID = 16;
  public static final int intakeLiftMotorID = 14;
  public static final int intakeLiftMotor2ID = 17;
  public static final int deAlgae_mID = 18;

  // encoder ids
  // public static final int climberEncoderID = 0;
  public static final int intakeEncoderID = 0;

  // motor speeds
  public static final double climberMotorSpeed = 1;
  public static final double intakeWheelSpeed = 0.4;
  public static final double intakeWheelOutSpeed = 0.3;
  public static final double intakeMotorSpeed = 0.25;
  public static final double intakeWheelSpeedAfterIntake = 0.2;
  public static final double deAlgae_mSpeed = 0.2;

  // gear ratios
  public static final double climberGearRatio = 125;

  // encoder off sets
  public static final double climberEncoderOffset = 0;
  public static final double intakeEncoderOffset = 311;
  // extrema for motor angles
  public static final double CLIMBER_MAX_ANGLE_UP = 270;
  public static final double CLIMBER_MIN_ANGLE_DOWN = -90;

  public static final double intakeMinAngle = 5;
  public static final double intakeInitalMaxAngle = 45;
  public static final double intakeFinalMaxAngle = 85;

  public static final double maxIntakeVolt = 12;

  // intake PID controls
  // private static final double kP = 0.1;
}
