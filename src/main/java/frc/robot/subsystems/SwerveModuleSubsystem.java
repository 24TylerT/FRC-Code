package frc.robot;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.math.controller.PIDController;

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


public class SwerveModuleSubsystem extends SubsystemBase {
    private SwerveModule mModule;

    public SwerveModuleSubsystmem(){
        mModule = new SwerveModule(
            SwerveConstants.kRotorID,
            SwerveConstants.kThrottleID,
            SwerveConstants.kRotorEncoderID,
            false,
            0.0
        );
    }

    @Override
    public void periodic() {

    }

    public SwerveModule getModule(){
        return mModule;
    }
}
