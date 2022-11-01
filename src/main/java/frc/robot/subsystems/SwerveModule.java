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


public class SwerveModule {
    //useful variables
    WPI_TalonFX mRotor, mThrottle;
    CANCoder mRotorEncoder;

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

        mRotor.enableVoltageCompSaturation(true);
        mThrottle.enableVoltageCompSaturation(true);
        mRotor.configVoltageCompSaturation(Constants.kVoltageCompensation);
        mThrottle.configVoltageCompSaturation(Constants.kVoltageCompensation);

        mRotor.setNeutralMode(NeutralMode.Brake);
        mThrottle.setNeutralMode(NeutralMode.Brake);
    }


}
