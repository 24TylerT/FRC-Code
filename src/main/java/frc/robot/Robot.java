//import edu.wpi.first.wpilibj.smartdashboard.SendableChooser; 
package frc.robot;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PS4Controller;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import com.ctre.phoenix.CANCoder;

public class Robot extends TimedRobot {
  // Declare objects below here
  TalonSRX motor1;
  TalonSRX motor2;
  TalonSRX motor3;
  PS4Controller joy;
  double speedEx, speedClim, targetPosEx, percentTrue;
  boolean moveBool1, moveBool2;
  Faults faults;
  

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    //initialize physical objects
    motor1 = new TalonSRX(5);
    motor2 = new TalonSRX(3);
    motor3 = new TalonSRX(4);
    joy = new PS4Controller(0);
    //initialize variables
    speedEx = 0;
    speedClim = 0;
    targetPosEx = 0;
    moveBool1 = false;
    moveBool2 = false;
    percentTrue = 1;
    faults = new Faults();

    //motor config for MotionMagic
    //don't know what SCurve means but just set to 0 for now
    motor1.configMotionSCurveStrength(0);
    //cruisevelo is top speed, accel is accelelration per sec
    motor1.configMotionCruiseVelocity(800);
		motor1.configMotionAcceleration(800);
    //kF is main thing, 1024 units divided by target peak velocity
    //kF is like a speed multiplier for motion acceleration
    //works best when kF is 1024/peak velo for that reason
    motor1.config_kF(0, 1.28);
    //proportional whatever
    //helps motion magic reach the exact target position when
    //detecting movement mistakes
    //start from 1, increase until closed loop error starts 
    //oscillating, then dial back slightly
    motor1.config_kP(0, 2.1);
    //BE CAREFUL WITH kI! can increase too fast and make your robot
    //destroy itself since kI can snowball and compensate motor speed to 100%
    motor1.config_kI(0, 0.001);
    //baytun sayd ignore kD!!!!
    motor1.config_kD(0, 20);
    motor1.config_IntegralZone(0, 50);
    //zeroes sensor value
    motor1.setSelectedSensorPosition(0);
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
    //phases sensor of motor1
    motor1.setSensorPhase(true);
    //inverts motor2 but not motor3
    motor2.setInverted(true);
    motor3.setInverted(false);
    //sets motor3 to follow motor2
    motor3.follow(motor2);
  }

  /**
   * This function is called periodically during teleoperated mode.
   * This is where the code for manual control goes
   */
  @Override
  public void teleopPeriodic() {
    //updates climb arm variables and sets motor2 to certain speed
    //motor3 automatically follows
    speedClim = joy.getRightY()/6;
    motor2.set(ControlMode.PercentOutput, speedClim);

    //triangle is Y
    //pressing y switches mode from percent output to motionmagic
    targetPosEx = 2000;
    speedEx = joy.getLeftY()/5;
    if(joy.getTriangleButtonPressed()){
      percentTrue = percentTrue*-1;
      motor1.set(ControlMode.MotionMagic, targetPosEx);
    }
    if(percentTrue = 1){
      motor1.set(ControlMode.PercentOutput, speedEx)
    }

    //cross is B
    //when X is pressed and motor pos is more than -2000 move motor to -2000 or less.
    //does not activate if motor pos is already -2000 or less
    if (joy.getCrossButtonPressed()){
      moveBool1 = true;
    }
    if (moveBool1 && motor1.getSelectedSensorPosition()>-2000){
      motor1.set(ControlMode.PercentOutput, -0.2);
    } else {
      moveBool1 = false;
    }

    //r1 button is RB
    //sets motor1 sensor value to zero
    if(joy.getR1Button()){
      motor1.setSelectedSensorPosition(0);
    }

    //gets the problems with the sensors or something idk
    motor1.getFaults(faults);

    //circle is X
    //prints useful info to console when O is pressed
    if (joy.getCircleButton()) {
      System.out.println("Out of Phase: "+faults.SensorOutOfPhase);
      System.out.println("Sensor Position: " + motor1.getSelectedSensorPosition());
      System.out.println("Sensor Velocity: " + motor1.getSelectedSensorVelocity());
      System.out.println("Out %: " + motor1.getMotorOutputPercent());
    }
  }
}