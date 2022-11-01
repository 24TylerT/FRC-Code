package frc.robot;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PS4Controller;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import com.ctre.phoenix.CANCoder;

public class SwerveModule {
    TalonSRX mRotor, mThrottle;
    CANCoder mRotorEncoder;

    public SwerveModule(int rotorID, int throttleID, int CANCoderID, boolean throttleReversed){
        mRotor = new TalonSRX(rotorID);
        mThrottle = new TalonSRX(throttleID);

        mRotorEncoder = new CANCoder(CANCoderID);

        mThrottle.setInverted(throttleReversed);
    }


}
