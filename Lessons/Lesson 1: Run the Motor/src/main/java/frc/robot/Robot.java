// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.Watts;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import dev.doglog.DogLog;
import dev.doglog.DogLogOptions;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * This is the structure (function names) of timed robot, the benefits of this structure is that you can code relatively extremely fast compared to the command based, but avoiding use it while coding large projects like whole robot test code and espcially avoiding use timed base for compition code.
 */
public class Robot extends TimedRobot {
  public SparkMax LeftMotor, RightMotor;
  public RelativeEncoder LeftEncoder, RightEncoder;
  
  private SparkMaxConfig LeftConfig, RightConfig;
  public XboxController controller;

  /**
   * This is the initialization of the `Robot` class, different from robotInit, this function is like python __init__ function, it's a part of java language structure.
   * In this function, everything will run once, just like arduino `setup()` function.
   */
  public Robot() {
    LeftMotor = new SparkMax(10, MotorType.kBrushless); //The device ID can set in the RHC, for 2026 firmwares, it needs RHC 2 to program it. For CAN IDs, the ID cannot duplicate in a same bus, but if you have two buses like one from rio and one from canivore, IDs are duplicatable, but need to be aware the identification.
    RightMotor = new SparkMax(11, MotorType.kBrushless); //Since SparkMax can control both brushed and brushless motor, it needs to specify what motor it is controlling, so just input the right value.

    LeftEncoder = LeftMotor.getEncoder(); //The encoder provides both position and velocity data, for brushless motors, it has built-in encoders (the JST connected wires). SparkMax also supports external encoders like REV Through Bore Encoder by connecting from the top holes (yes it needs the convertion board, the convertion board has two types, absoulte and relative, don't buy the wrong type).
    RightEncoder = RightMotor.getEncoder();

    LeftConfig = new SparkMaxConfig(); //let's talk about configuration in Config.java, just skip it here
    RightConfig = new SparkMaxConfig();

    LeftConfig
      .idleMode(IdleMode.kBrake)
      .inverted(false)
      .smartCurrentLimit(40)
      .voltageCompensation(12);
    LeftConfig.encoder
      .positionConversionFactor(10.71)
      .velocityConversionFactor(10.71/60);
    
    RightConfig
      .idleMode(IdleMode.kBrake)
      .inverted(true)
      .smartCurrentLimit(40)
      .voltageCompensation(12);
    RightConfig.encoder
      .positionConversionFactor(10.71)
      .velocityConversionFactor(10.71/60);
    
    /**
     * config: the configuration you want to upload to the motor
     * kResetSafeParameters: controls whether reset the parameters into factory default(except CAN ID)
     * kPersistParameters: controls whether apply the new configuration.
     */
    LeftMotor.configure(LeftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    RightMotor.configure(RightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    
    DogLog.setOptions(new DogLogOptions()
      .withCaptureConsole(true)
      .withNtPublish(true)
      .withCaptureDs(true)
      .withLogExtras(true));
    DogLog.setEnabled(true);
  }

  /**
   * What will the program do periodically(20ms)
   */
  @Override
  public void robotPeriodic() {
    DogLog.log("LeftMotor/Velocity", LeftEncoder.getVelocity());
    DogLog.log("LeftMotor/Position", LeftEncoder.getVelocity());
    DogLog.log("LeftMotor/Power", LeftMotor.getBusVoltage()*LeftMotor.getOutputCurrent(), Watts);
    
    DogLog.log("RightMotor/Velocity", RightEncoder.getVelocity());
    DogLog.log("RightMotor/Position", RightEncoder.getVelocity());
    DogLog.log("RightMotor/Power", RightMotor.getBusVoltage()*RightMotor.getOutputCurrent(), Watts);
  }

  /**
   * What will robot do before truly disables the robot (like stopping the vision or other software stuffs)
   * for motors like SparkMax or TalonFX, they have FRC lock which means it only moves when DS/FMS is enabled the robot
   */
  @Override
  public void disabledInit() {}

  /**
   * What will the robot do while disable, maybe sing some song or just do nothing
   */
  @Override
  public void disabledPeriodic() {}

  /**
   * what will robot do when it exits the disable mode such as refecthing the vision position or whatever
   */
  @Override
  public void disabledExit() {}

  /**
   * as disabledInit but doing before auto.
   */
  @Override
  public void autonomousInit() {
  }

  /**
   * you know what i'm talking about, right? */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
    if(controller.getAButton()){
      LeftMotor.set(controller.getLeftY());
      RightMotor.set(controller.getRightY());
    }else{
    LeftMotor.set(controller.getLeftY()*0.5); // speed limit for human driving.
    RightMotor.set(controller.getRightY()*0.5);
    }
  }

  @Override
  public void teleopExit() {}

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void testExit() {}
}
