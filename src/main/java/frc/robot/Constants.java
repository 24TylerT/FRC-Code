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

//all constants MUST start with K
public class Constants {
    //constants to use
    public class SwerveConstants{
        public final static double kVoltageCompensation = 12.0;
        public final static double kP = 1.0;
        public final static double kI = 0.0;
        public final static double kD = 10.0;
        public final static int kRotorID = 0;
        public final static int kRotorEncoderID = 0;
        public final static int kThrottleID = 0;
        

    }
}

