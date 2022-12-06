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
import edu.wpi.first.math.kinematics.SwerveModuleState;


public class SwerveModule {
    //useful variables
    private WPI_TalonFX mRotor, mThrottle;
    private CANCoder mRotorEncoder;
    private PIDController mPIDController;

    //swervemodule constructor
    public SwerveModule(int rotorID, int throttleID, int CANCoderID, boolean throttleReversed, double rotorOffsetAngle){
        mRotor = new WPI_TalonFX(rotorID);
        mThrottle = new WPI_TalonFX(throttleID);

        mRotorEncoder = new CANCoder(CANCoderID);

        mPIDController = new PIDController(SwerveConstants.kP, SwerveConstants.kI, SwerveConstants.kD);

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

        mPIDController.enableContinuousInput(-180, 180);
    }

    

    public void jogRotor(double percent){
        mRotor.set(ControlMode.PercentOutput, percent);
    }
    public void jogThrottle(double percent){
        mThrottle.set(ControlMode.PercentOutput, percent);
    }
    public void setState(SwerveModuleState state){
        SwerveModuleState newState = SwerveModuleState.optimize(state, mRotorEncoder.getPosition());
        double rotorOutput = mPIDController.calculate(
            mRotorEncoder.getPosition(),
            newState.angle.getDegrees()
        );
        mRotor.set(rotorOutput);
        mThrottle.set(newState.speedMetersPerSecond);
    }

    public SwerveModuleState getState(){
        SwerveModuleState currentState = new SwerveModuleState(
            //not sure if this is right - 1st param should be in meters per second but
            //mThrottle.getSelectedSensorVelocity might be some other unit of measure
            mThrottle.getSelectedSensorVelocity(),
            mRotorEncoder.getSelectedSensorPosition()
        );
        return currentState;
    }

}
