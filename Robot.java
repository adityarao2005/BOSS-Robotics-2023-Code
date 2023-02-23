package frc.robot;

//import com.ctre.phoenix.motorcontrol.ControlMode;

//import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
//import com.ctre.phoenix.motorcontrol.can.VictorSP;
//import com.revrobotics.CANSparkMax;
//import com.revrobotics.CANSparkMax.IdleMode;
//import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Servo;
//import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
//import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
  
  
    //declare speed controlls for gyroscope
  
    double leftSlow = 0.24;
    double rightSlow = -0.24;
    double rotateSpeed = 0.35;
    double rotateSpeedSlow = 0.25;

  
   // Inputs
    AnalogGyro gyro = new AnalogGyro(0); // ANA Ch. 0
  
    Spark m_frontLeft = new Spark(1);
    Spark m_rearLeft = new Spark(2);
    MotorControllerGroup m_left = new MotorControllerGroup(m_frontLeft, m_rearLeft);

    Spark m_frontRight = new Spark(3);
    Spark m_rearRight = new Spark(4);
    MotorControllerGroup m_right = new MotorControllerGroup(m_frontRight, m_rearRight);
    DifferentialDrive m_drive = new DifferentialDrive(m_left, m_right);

    public void robotInit() {
        m_left.setInverted(true); // if you want to invert the entire side you can do so here
      
      CameraServer.getInstance().startAutomaticCapture("USB Camera 1", 0);
      gyro.reset();
    }
public void teleopPeriodic() {
    // Tank drive with a given left and right rates
    myDrive.tankDrive(-leftStick.getY(), -rightStick.getY());

    // Arcade drive with a given forward and turn rate
    myDrive.arcadeDrive(-driveStick.getY(), driveStick.getX());

    // Curvature drive with a given forward and turn rate, as well as a button for turning in-place.
    myDrive.curvatureDrive(-driveStick.getY(), driveStick.getX(), driveStick.getButton(1));
  
  
    // Previewing Gyro Output:
    System.out.println(Math.round(gyro.getAngle()));

   // Setting a Servo as a Gimbal
    servoCam.set(0.5 - gyro.getAngle()/175);
}
//Encoder Code:
Encoder encoder = new Encoder(0, 1);

// Initialize motor controllers and drive
Spark left1 = new Spark(0);
Spark left2 = new Spark(1);

Spark right1 = new Spark(2);
Spark right2 = new Spark(3);

MotorControllerGroup leftMotors = new MotorControllerGroup(left1, left2);
MotorControllerGroup rightMotors = new MotorControllerGroup(right1, right2);

DifferentialDrive drive = new DifferentialDrive(leftMotors, rightMotors);

@Override
public void robotInit() {
    // Configures the encoder's distance-per-pulse
    // The robot moves forward 1 foot per encoder rotation
    // There are 256 pulses per encoder rotation
    encoder.setDistancePerPulse(1./256.);
}
  
@Override
 public void autonomousInit() {
  gyro.reset();
 }
  
@Override
public void autonomousPeriodic() {
    // Drives forward at half speed until the robot has moved 5 feet, then stops:
    if(encoder.getDistance() < 5) {
        drive.tankDrive(0.5, 0.5);
    } else {
        drive.tankDrive(0, 0);
    }
  
  
  //Sample Code for gyroscope to balance the robot
    if (Math.abs(gyro.getAngle()) <= 3) {
      MotorControllerGroup m_left.set(leftSlow - (gyro.getAngle()) / 15);
      MotorControllerGroup m_right.set(rightSlow - (gyro.getAngle()) / 15);
    } else if (Math.abs(gyro.getAngle()) < 10) {
      if (gyro.getAngle() > 0) {
      MotorControllerGroup m_left.set(leftSlow);
      MotorControllerGroup m_right.set(1.1 * rightSlow);
      } else if (gyro.getAngle() < 0) {
      MotorControllerGroup m_left.set(1.1 * leftSlow);
      MotorControllerGroup m_right.set(rightSlow);
      }
    } else
      if (gyro.getAngle() > 0) {
      while (gyro.getAngle() > 10 && isAutonomous()) {
        MotorControllerGroup m_left.set(-rotateSpeed);
        MotorControllerGroup m_right.set(-rotateSpeed);
      }
      while (gyro.getAngle() > 0 && isAutonomous()) {
      MotorControllerGroup m_left.set(-rotateSpeedSlow);
      MotorControllerGroup m_right.set(-rotateSpeedSlow);
      }
      while (gyro.getAngle() < 0 && isAutonomous()) {
      MotorControllerGroup m_left.set(rotateSpeedSlow);
      MotorControllerGroup m_right.set(rotateSpeedSlow);
      }
    } else {
      while (gyro.getAngle() < -10 && isAutonomous()) {
      MotorControllerGroup m_left.set(rotateSpeed);
      MotorControllerGroup m_right.set(rotateSpeed);
      }
      while (gyro.getAngle() < 0 && isAutonomous()) {
      MotorControllerGroup m_left.set(rotateSpeedSlow);
      MotorControllerGroup m_right.set(rotateSpeedSlow);
      }
      while (gyro.getAngle() > 0 && isAutonomous()) {
      MotorControllerGroup m_left.set(-rotateSpeedSlow);
      MotorControllerGroup m_right.set(-rotateSpeedSlow);
      }
    }
}
