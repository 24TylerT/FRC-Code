//import edu.wpi.first.wpilibj.smartdashboard.SendableChooser; 
package frc.robot;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PS4Controller;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.*;

import frc.robot.Constants;
import frc.robot.Constants.SwerveConstants;

public class ModuleTestBench extends TimedRobot {
  // Declare objects below here
  TalonFX steering;
  TalonFX throttle;
  PS4Controller joy;
  double speedEx, speedClim, targetPosEx, percentTrue, targetPosTh;
  boolean moveBool1, moveBool2;
  Faults faults;
  

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    //initialize physical objects
    steering = new TalonFX(1);
    throttle = new TalonFX(0);
    joy = new PS4Controller(0);
    //initialize variables
    speedEx = 0;
    speedClim = 0;
    targetPosEx = 2000;
    targetPosTh = 2000;
    moveBool1 = false;
    moveBool2 = false;
    percentTrue = 1;
    faults = new Faults();

    //motor config for MotionMagic
    //don't know what SCurve means but just set to 0 for now
    steering.configMotionSCurveStrength(0);
    //cruisevelo is top speed, accel is accelelration per sec
    //both should be around the same for a 1 second acceleration to maximum velocity
    steering.configMotionCruiseVelocity(2000);
		steering.configMotionAcceleration(2000);
    //kF is main thing, 1024 units divided by target peak velocity
    //kF is like a speed multiplier/divider/factor for motion acceleration
    //works best when kF is 1024/peak velo for that reason
    steering.config_kF(0, 0.512);
    //proportional whatever 
    //helps motion magic reach the exact target position when
    //detecting movement mistakes
    //start from 1, increase until closed loop error starts 
    //oscillating, then dial back slightly
    steering.config_kP(0, 0.55);
    //BE CAREFUL WITH kI! can increase too fast and make your robot
    //destroy itself since kI can snowball and compensate motor speed to 100%
    //start with kI set to 0.001
    steering.config_kI(0, 0.007);
    //baytun sayd ignore kD!!!!
    //set to 20 by default for now
    steering.config_kD(0, 5.5);
    //prevents extreme buildup for integrals
    steering.config_IntegralZone(0, 50);
    //zeroes sensor value
    //steering.setSelectedSensorPosition(0);


    throttle.configMotionSCurveStrength(0);
    throttle.configMotionAcceleration(4000);
    throttle.configMotionCruiseVelocity(4000);
    throttle.config_kF(0, 0.256);
    throttle.config_kP(0, 0.55);
    throttle.config_kI(0, 0);
    throttle.config_kD(0, 5.5);
    throttle.config_IntegralZone(0, 50);
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
    //phases sensor of steering
    steering.setSensorPhase(false);
    //inverts throttle but not motor3
    throttle.setInverted(false);
    //sets motor3 to follow throttle
  }

  /**
   * This function is called periodically during teleoperated mode.
   * This is where the code for manual control goes
   */
  @Override
  public void teleopPeriodic() {
    //updates climb arm variables and sets throttle to certain speed
    //motor3 automatically follows
    speedClim = joy.getRightY()/6;
    throttle.set(ControlMode.PercentOutput, speedClim);

    //triangle is Y
    //pressing y switches mode from percent output to motionmagic
    //control motionmagic with left stick as well
    speedEx = -joy.getLeftY()*0.25;
    
    if(joy.getTriangleButtonPressed()){
      percentTrue = percentTrue*-1;
    }
    if(percentTrue == 1){
      steering.set(ControlMode.PercentOutput, speedEx);
    } else {
      //targetPosEx = targetPosEx - joy.getLeftY()*70;
      steering.set(ControlMode.MotionMagic, targetPosEx);
    }

    //cross is B
    //when X is pressed and motor pos is more than -2000 move motor to -2000 or less.
    //does not activate if motor pos is already -2000 or less
    if (joy.getCrossButtonPressed()){
      moveBool1 = true;
    }
    if (moveBool1 && steering.getSelectedSensorPosition()>-2000){
      steering.set(ControlMode.PercentOutput, -0.2);
    } else {
      moveBool1 = false;
    }

    //r1 button is RB
    //sets steering sensor value to zero
    if(joy.getR1Button()){
      steering.setSelectedSensorPosition(0);
    }

    //l1 button is LB
    if(joy.getL1Button()){
      throttle.set(ControlMode.MotionMagic, targetPosTh);;
    }

    //gets the problems with the sensors or something idk
    steering.getFaults(faults);

    //circle is X
    //prints useful info to console when X is pressed
    if (joy.getCircleButton()) {
      System.out.println("Out of Phase: "+faults.SensorOutOfPhase);
      System.out.println("St Sensor Position: " + steering.getSelectedSensorPosition());
      System.out.println("St Sensor Velocity: " + steering.getSelectedSensorVelocity());
      System.out.println("St Out %: " + steering.getMotorOutputPercent());
      System.out.println("St Target Position: "+targetPosEx);
      System.out.println("Th Sensor Position: " + throttle.getSelectedSensorPosition());
      System.out.println("Th Sensor Velocity: " + throttle.getSelectedSensorVelocity());
      System.out.println("Th Out %: " + throttle.getMotorOutputPercent());
      System.out.println("Th Target Position: "+targetPosTh);
    }
  }
}