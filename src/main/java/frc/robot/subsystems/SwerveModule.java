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


public class SwerveModule {
    //useful variables
    private WPI_TalonFX mRotor, mThrottle;
    private CANCoder mRotorEncoder;

    //swervemodule constructor
    public SwerveModule(int rotorID, int throttleID, int CANCoderID, boolean throttleReversed, double rotorOffsetAngle){
        mRotor = new WPI_TalonFX(rotorID);
        mThrottle = new WPI_TalonFX(throttleID);

        mRotorEncoder = new CANCoder(CANCoderID);

        mRotor.configFactoryDefault();
        mThrottle.configFactoryDefault();
        mRotorEncoder.configFactoryDefault();

        mRotorEncoder.configAbsoluteSensorRange(AbsoluteSensorRange.Signed_PlusMinus180);
        mRotorEncoder.configMagnetOffset(rotorOffsetAngle);
        mRotorEncoder.configSensorDirection(false);
        mRotorEncoder.configSensorInitializationStrategy(SensorInitializationStrategy.BootToAbsolutePosition);

        mRotor.setInverted(TalonFXInvertType.Clockwise);
        mThrottle.setInverted(throttleReversed);

        mRotor.enableVoltageCompensation(true);
        mThrottle.enableVoltageCompensation(true);
        mRotor.configVoltageCompSaturation(SwerveConstants.kVoltageCompensation);
        mThrottle.configVoltageCompSaturation(SwerveConstants.kVoltageCompensation);

        mRotor.setNeutralMode(NeutralMode.Brake);
        mThrottle.setNeutralMode(NeutralMode.Brake);
    }

    public void setSpeed(double speed){
        mThrottle.set(ControlMode.PercentOutput, speed);
    }

}
