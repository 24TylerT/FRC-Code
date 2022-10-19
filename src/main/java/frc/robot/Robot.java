//import edu.wpi.first.wpilibj.smartdashboard.SendableChooser; 
package frc.robot;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PS4Controller;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Robot extends TimedRobot {
  // Declare objects below here
  TalonSRX motor1;
  TalonSRX motor2;
  TalonSRX motor3;
  PS4Controller joy;
  double speedex;
  double speedclim;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    motor1 = new TalonSRX(5);
    motor2 = new TalonSRX(3);
    motor3 = new TalonSRX(4);
    joy = new PS4Controller(0);
    speedex = 0;
    speedclim = 0;
  }

  /**
   * This function is run once each time the robot enters autonomous mode.
   */
  @Override
  public void autonomousInit() {
    
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    
  }

  /**
   * This function is called once each time the robot enters teleoperated mode.
   */
  @Override
  public void teleopInit() {
    motor1.setSensorPhase(false);
    motor1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 1);
    motor2.setInverted(true);
    motor3.setInverted(false);
    motor3.follow(motor2);
  }

  /**
   * This function is called periodically during teleoperated mode.
   * This is where the code for manual control goes
   */
  @Override
  public void teleopPeriodic() {
    speedex = -joy.getLeftY()/5;
    motor1.set(ControlMode.PercentOutput, speedex);
    speedclim = joy.getRightY()/6;
    motor2.set(ControlMode.PercentOutput, speedclim);
    System.out.println("Sensor Position: " + motor1.getSelectedSensorPosition());
    System.out.println("Out %: " + motor1.getMotorOutputPercent());
  }
}