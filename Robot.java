// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Encoder;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */

  private Spark leftMotor1 = new Spark(0);
  private Spark leftMotor2 = new Spark(1);
  private Spark rightMotor1 = new Spark(2);
  private Spark rightMotor2 = new Spark(3);

  private Encoder motorEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k2X); //Pins 0 and 1

  private Joystick joy1 = new Joystick(0);

  private int robotStartTime;
  private int chargeDistance = 60.69; //UPDATE
  private int blockDistance = 224;    //UPDATE

  private final int MOVE_POWER = 0.6;


  @Override
  public void robotInit() {
    leftMotor1.setInverted(false);
    leftMotor2.setInverted(false);
    rightMotor1.setInverted(true);
    rightMotor2.setInverted(true);
  }

  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {
    encoder.setDistancePerPulse(4f/256f); //distance of 4 for every 256 pulses
    encoder.setMinRate(10);               //consider "stopped" when rate is below 10
  }

  @Override
  public void autonomousPeriodic() {
    if (encoder.getDistance() <= blockDistance) {
      move();
    } else {
      moveReverse();
    }
  }

  private void move() {
    leftMotor1.set(MOVE_POWER);
    leftMotor2.set(MOVE_POWER);
    rightMotor1.set(MOVE_POWER);
    rightMotor2.set(MOVE_POWER);
  }

  private void moveReverse() {
    leftMotor1.set(-MOVE_POWER);
    leftMotor2.set(-MOVE_POWER);
    rightMotor1.set(-MOVE_POWER);
    rightMotor2.set(-MOVE_POWER);
  }

  @Override
  public void teleopInit() {
    
  }

  @Override
  public void teleopPeriodic() {

    //up is NEGATIVE , down is POSITIVE
    //
    double speed = -joy1.getRawAxis(1)*0.6;    //Slows down by 60 percent
    double turn =  joy1.getRawAxis(4)*0.3;     //Slows down by 30 percent for better controllability

    double left = speed + turn;
    double right = speed - turn;

    leftMotor1.set(left);
    leftMotor2.set(left);
    rightMotor1.set(right);
    rightMotor2.set(right);
    
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
  
}
